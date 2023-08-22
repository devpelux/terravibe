package xyz.devpelux.terravibe.block;

import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import xyz.devpelux.terravibe.advancement.TerravibeCriteria;
import xyz.devpelux.terravibe.item.TerravibeItems;
import xyz.devpelux.terravibe.tags.TerravibeItemTags;

import java.util.Map;

/**
 * A cauldron that contains milk.
 */
public final class MilkCauldronBlock extends AbstractCauldronBlock {
	/**
	 * Fermenting time to the next stage.
	 */
	public static final int FERMENTING_TIME = 60;

	/**
	 * Content of the cauldron.
	 */
	public static final EnumProperty<Content> CONTENT;

	/**
	 * Fill the cauldron with milk.
	 */
	public static final CauldronBehavior FILL_WITH_MILK;

	/**
	 * Outline shape when the cauldron is filled with solid or partially solid content.
	 */
	private static final VoxelShape SOLID_FILLED_CAULDRON_OUTLINE_SHAPE;

	/**
	 * Behavior map for milk cauldron.
	 */
	private static final Map<Item, CauldronBehavior> MILK_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();

	/**
	 * Initializes a new {@link MilkCauldronBlock}.
	 */
	public MilkCauldronBlock(Settings settings) {
		super(settings, MILK_CAULDRON_BEHAVIOR);
		setDefaultState(getDefaultState().with(CONTENT, Content.Milk));
	}

	/**
	 * Loads all the cauldron behaviors.
	 */
	public static void registerBehaviors() {
		//Fill with milk.
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(Items.MILK_BUCKET, FILL_WITH_MILK);
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.MILK_BUCKET, FILL_WITH_MILK);
		CauldronBehavior.LAVA_CAULDRON_BEHAVIOR.put(Items.MILK_BUCKET, FILL_WITH_MILK);
		CauldronBehavior.POWDER_SNOW_CAULDRON_BEHAVIOR.put(Items.MILK_BUCKET, FILL_WITH_MILK);

		//Milk cauldron refill.
		MILK_CAULDRON_BEHAVIOR.put(Items.WATER_BUCKET, (state, world, pos, player, hand, stack) -> {
			return refillConditionally(state, world, pos, player, hand, stack, CauldronBehavior.FILL_WITH_WATER);
		});
		MILK_CAULDRON_BEHAVIOR.put(Items.LAVA_BUCKET, (state, world, pos, player, hand, stack) -> {
			return refillConditionally(state, world, pos, player, hand, stack, CauldronBehavior.FILL_WITH_LAVA);
		});
		MILK_CAULDRON_BEHAVIOR.put(Items.POWDER_SNOW_BUCKET, (state, world, pos, player, hand, stack) -> {
			return refillConditionally(state, world, pos, player, hand, stack, CauldronBehavior.FILL_WITH_POWDER_SNOW);
		});

		//Milk cauldron emptying.
		MILK_CAULDRON_BEHAVIOR.put(Items.BUCKET, (state, world, pos, player, hand, stack) -> {
			return CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack,
					new ItemStack(Items.MILK_BUCKET), MilkCauldronBlock::canEmpty, SoundEvents.ITEM_BUCKET_FILL);
		});
	}

	/**
	 * Registers the properties of the block.
	 */
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(CONTENT);
	}

	/**
	 * Gets a value indicating if the cauldron is full.
	 */
	@Override
	public boolean isFull(BlockState state) {
		return true;
	}

	/**
	 * Executed when the block is used.
	 * Insert ingredients, or interacts with the cauldron.
	 */
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		//Gets the stack and the content.
		ItemStack ingredient = player.getStackInHand(hand);
		Content content = state.get(CONTENT);
		Content recipeResult = getRecipeResult(content, ingredient);

		if (recipeResult != content) {
			//If, after adding the ingredient, the content should be different, changes the content.
			if (!world.isClient()) {
				//Converts the content to the result
				world.setBlockState(pos, state.with(CONTENT, recipeResult));

				//Updates the player statistics.
				player.incrementStat(Stats.USE_CAULDRON);
				player.incrementStat(Stats.USED.getOrCreateStat(ingredient.getItem()));

				//Consumes the item used, if the player is not in creative mode.
				if (!player.getAbilities().creativeMode) {
					ItemStack drop = ingredient.getRecipeRemainder();
					ingredient.decrement(1);
					player.getInventory().offerOrDrop(drop);
				}

				//Plays the put sound.
				world.playSound(null, pos, getPutSound(recipeResult), SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
		} else if (ingredient.isEmpty()) {
			//If the interaction is with an empty inventory slot, takes the result.
			ItemStack result = getResult(content);
			if (!result.isEmpty()) {
				if (player instanceof ServerPlayerEntity serverPlayer) {
					TerravibeCriteria.MAKE_DAIRY_PRODUCTS.trigger(serverPlayer);
				}
				return CauldronBehavior.emptyCauldron(state, world, pos, player, hand, ingredient, result, s -> true, getPickSound(content));
			}
		}

		return super.onUse(state, world, pos, player, hand, hit);
	}

	/**
	 * Gets a value indicating if the block reacts with the ticking system.
	 */
	@Override
	public boolean hasRandomTicks(BlockState state) {
		Content content = state.get(CONTENT);
		return content != getNextStage(content);
	}

	/**
	 * Executed every tick randomly.
	 * Handles the milk fermentation.
	 */
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (random.nextInt(FERMENTING_TIME) == 0) {
			//Pass to the next stage basing on the current stage.
			world.setBlockState(pos, state.with(CONTENT, getNextStage(state.get(CONTENT))));
		}
	}

	/**
	 * Executed when an entity enter the cauldron.
	 * Extinguishes fire.
	 */
	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (!world.isClient() && entity.isOnFire() && this.isEntityTouchingFluid(state, pos, entity)) {
			entity.extinguish();
		}
	}

	/**
	 * Gets the fluid height.
	 */
	@Override
	protected double getFluidHeight(BlockState state) {
		return switch (state.get(CONTENT)) {
			case Milk, AcidMilk, AcidMilkWithSalt, AcidMilkWithSaltAndMold -> 0.9375;
			case Mozzarella -> 0.75;
			case Cheese, Gorgonzola -> 0.5625;
		};
	}

	/**
	 * Gets the comparator output basing on the block state.
	 */
	@Override
	public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return switch (state.get(CONTENT)) {
			case Milk -> 4;
			case AcidMilk, AcidMilkWithSalt, AcidMilkWithSaltAndMold -> 3;
			case Mozzarella -> 2;
			case Cheese, Gorgonzola -> 1;
		};
	}

	/**
	 * Gets the outline shape of the block.
	 */
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return switch (state.get(CONTENT)) {
			case Mozzarella, Cheese, Gorgonzola -> SOLID_FILLED_CAULDRON_OUTLINE_SHAPE;
			default -> super.getOutlineShape(state, world, pos, context);
		};
	}

	/**
	 * Gets the next milk stage basing on the current content.
	 */
	private Content getNextStage(Content content) {
		return switch (content) {
			case AcidMilk -> Content.Mozzarella;
			case AcidMilkWithSalt -> Content.Cheese;
			case AcidMilkWithSaltAndMold -> Content.Gorgonzola;
			default -> content;
		};
	}

	/**
	 * Gets the recipe result basing on the current content and the ingredient added.
	 */
	private Content getRecipeResult(Content content, ItemStack ingredient) {
		return switch (content) {
			case Milk -> ingredient.isIn(TerravibeItemTags.MILK_COAGULANTS) ? Content.AcidMilk : content;
			case AcidMilk -> ingredient.isOf(TerravibeItems.SALT) ? Content.AcidMilkWithSalt : content;
			case AcidMilkWithSalt ->
					ingredient.isIn(TerravibeItemTags.EDIBLE_MOLDS) ? Content.AcidMilkWithSaltAndMold : content;
			default -> content;
		};
	}

	/**
	 * Gets the result basing on the current content.
	 */
	private ItemStack getResult(Content content) {
		return switch (content) {
			case Mozzarella -> new ItemStack(TerravibeItems.MOZZARELLA, 8);
			case Cheese -> new ItemStack(TerravibeItems.CHEESE_WHEEL, 1);
			case Gorgonzola -> new ItemStack(TerravibeItems.GORGONZOLA_WHEEL, 1);
			default -> ItemStack.EMPTY;
		};
	}

	/**
	 * Gets the pick sound basing on the current content.
	 */
	private SoundEvent getPickSound(Content content) {
		return switch (content) {
			case Mozzarella -> SoundEvents.BLOCK_HONEY_BLOCK_BREAK;
			case Cheese, Gorgonzola -> SoundEvents.BLOCK_WOOL_BREAK;
			default -> SoundEvents.ITEM_BUCKET_FILL;
		};
	}

	/**
	 * Gets the put sound basing on the recipe result.
	 */
	private SoundEvent getPutSound(Content recipeResult) {
		return switch (recipeResult) {
			case AcidMilk -> SoundEvents.ITEM_BOTTLE_EMPTY;
			case AcidMilkWithSalt -> SoundEvents.BLOCK_SAND_BREAK;
			case AcidMilkWithSaltAndMold -> SoundEvents.BLOCK_MOSS_PLACE;
			default -> SoundEvents.ITEM_BUCKET_EMPTY;
		};
	}

	/**
	 * Gets a value indicating if the cauldron can be emptied.
	 */
	private static boolean canEmpty(BlockState state) {
		return state.get(CONTENT) == Content.Milk;
	}

	/**
	 * Refills the cauldron with another fluid only if it can be emptied.
	 */
	private static ActionResult refillConditionally(BlockState state, World world, BlockPos pos, PlayerEntity player,
	                                                Hand hand, ItemStack stack, CauldronBehavior behavior) {
		return canEmpty(state) ? behavior.interact(state, world, pos, player, hand, stack) : ActionResult.PASS;
	}

	static {
		CONTENT = EnumProperty.of("content", Content.class);
		SOLID_FILLED_CAULDRON_OUTLINE_SHAPE = VoxelShapes.combineAndSimplify(OUTLINE_SHAPE,
				Block.createCuboidShape(2, 4, 2, 14, 9, 14), BooleanBiFunction.OR);
		FILL_WITH_MILK = (state, world, pos, player, hand, stack) -> {
			return CauldronBehavior.fillCauldron(world, pos, player, hand, stack,
					TerravibeBlocks.MILK_CAULDRON.getDefaultState(), SoundEvents.ITEM_BUCKET_EMPTY);
		};
	}


	/**
	 * Represents the milk type contained in a {@link MilkCauldronBlock}.
	 */
	public enum Content implements StringIdentifiable {
		/**
		 * The cauldron contains milk.
		 */
		Milk("milk"),

		/**
		 * The cauldron contains acid milk.
		 */
		AcidMilk("acid_milk"),

		/**
		 * The cauldron contains acid milk with salt.
		 */
		AcidMilkWithSalt("acid_milk_with_salt"),

		/**
		 * The cauldron contains acid milk with salt and mold.
		 */
		AcidMilkWithSaltAndMold("acid_milk_with_salt_and_mold"),

		/**
		 * The cauldron contains mozzarella.
		 */
		Mozzarella("mozzarella"),

		/**
		 * The cauldron contains cheese.
		 */
		Cheese("cheese"),

		/**
		 * The cauldron contains gorgonzola.
		 */
		Gorgonzola("gorgonzola");

		/**
		 * Name representing the value.
		 */
		private final String name;

		/**
		 * Initializes a new value with the name specified.
		 */
		Content(String name) {
			this.name = name;
		}

		/**
		 * Returns the string representation of this instance.
		 */
		@Override
		public String asString() {
			return this.name;
		}

		/**
		 * Returns the string representation of this instance.
		 */
		@Override
		public String toString() {
			return asString();
		}
	}
}
