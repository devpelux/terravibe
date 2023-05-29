package xyz.devpelux.terravibe.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.blockentity.ContainerBlockEntity;

import java.util.Objects;

/**
 * Generic container for fluids.
 */
public abstract class ContainerBlock extends BlockWithEntity {
	/**
	 * Empty content.
	 */
	public static final String CONTENT_EMPTY = "minecraft:empty";

	/**
	 * Represents the texture used for the content.
	 */
	public static final EnumProperty<ContentTexture> CONTENT_TEXTURE;

	/**
	 * Initializes a new {@link ContainerBlock}.
	 */
	public ContainerBlock(Settings settings) {
		super(settings);
		//Gets the default state with the level.
		BlockState defaultState = getDefaultState().with(getLevelProperty(), 0);
		//Sets the default state, after set the content texture property, if is specified to use it.
		setDefaultState(useDefaultContentTexture() ? defaultState.with(CONTENT_TEXTURE, ContentTexture.Fluid) : defaultState);
	}

	/**
	 * Gets the content.
	 */
	public static String getContent(ContainerBlockEntity container) {
		NbtCompound value = container.getValue("Content");
		return value.getString("Id");
	}

	/**
	 * Sets the content.
	 */
	public static void setContent(ContainerBlockEntity container, String content) {
		NbtCompound value = new NbtCompound();
		value.putString("Id", Objects.requireNonNullElse(content, CONTENT_EMPTY));
		container.setValue("Content", value);
	}

	/**
	 * Gets the container block entity.
	 */
	@Nullable
	public static ContainerBlockEntity getContainerEntity(BlockView world, BlockPos pos) {
		if (world == null || pos == null) return null;
		return world.getBlockEntity(pos) instanceof ContainerBlockEntity container ? container : null;
	}

	/**
	 * Sets the specified container at the specified position maintaining his block entity.
	 * The block must be a {@link ContainerBlock}, otherwise this method will do nothing.
	 */
	public static BlockState setContainer(BlockState state, World world, BlockPos pos) {
		if (state.getBlock() instanceof ContainerBlock) {
			ContainerBlockEntity container = getContainerEntity(world, pos);
			world.setBlockState(pos, state);
			if (container != null) world.addBlockEntity(container);
			return state;
		}
		return world.getBlockState(pos);
	}

	/**
	 * Registers the properties of the block.
	 */
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		//Adds the level property.
		builder.add(getLevelProperty());
		//Adds the content texture property if is specified to use it.
		if (useDefaultContentTexture()) builder.add(CONTENT_TEXTURE);
	}

	/**
	 * Gets the default state adjusted from nbt container data.
	 */
	public BlockState withContainerData(NbtCompound nbt) {
		//Gets the default state with the level.
		BlockState state = getDefaultState().with(getLevelProperty(), nbt.getInt("Level"));

		//Sets the content texture, if is specified to use the default property.
		if (useDefaultContentTexture()) {
			ContentTexture contentTexture = Objects.requireNonNullElse(ContentTexture.byName(nbt.getString("ContentTexture")), ContentTexture.Fluid);
			state = state.with(CONTENT_TEXTURE, contentTexture);
		}

		return state;
	}

	/**
	 * Gets a value indicating if to use the default content textures.
	 */
	public boolean useDefaultContentTexture() {
		return true;
	}

	/**
	 * Gets the level property.
	 */
	public abstract IntProperty getLevelProperty();

	/**
	 * Gets the max level.
	 */
	public abstract int getMaxLevel();

	/**
	 * Gets the level of the container from its block state.
	 */
	protected int getLevel(BlockState state) {
		return state.get(getLevelProperty());
	}

	/**
	 * Gets the behavior for the content and input specified.
	 */
	@Nullable
	protected abstract ContainerBehavior getBehavior(String content, Item input);

	/**
	 * Gets the container data as nbt.
	 */
	protected NbtCompound getContainerData(BlockState state, BlockView world, BlockPos pos) {
		NbtCompound nbt = new NbtCompound();
		ContainerBlockEntity container = getContainerEntity(world, pos);
		if (container != null) {
			//Adds a copy of the content.
			NbtCompound content = container.getContent().copy();
			nbt.put("ContentData", content);
		}
		//Adds other container data.
		saveToNbt(state, world, pos, nbt);
		return nbt;
	}

	/**
	 * Save container data to nbt.
	 */
	protected void saveToNbt(BlockState state, BlockView world, BlockPos pos, NbtCompound nbt) {
		//Saves the block id and the level.
		nbt.putString("Block", Registries.BLOCK.getId(this).toString());
		nbt.putInt("Level", getLevel(state));

		//Saves the content texture if is specified to use the default property.
		if (useDefaultContentTexture()) {
			nbt.putString("ContentTexture", state.get(CONTENT_TEXTURE).asString());
		}
	}

	/**
	 * Executed when the block is used.
	 * Interacts with the block entity.
	 */
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ContainerBlockEntity container = getContainerEntity(world, pos);
		if (container != null) {
			return onUseBlockEntity(state, world, pos, player, container);
		}
		return ActionResult.PASS;
	}

	/**
	 * Executed when the block entity is used.
	 * Inserts or extracts the content.
	 */
	public ActionResult onUseBlockEntity(BlockState state, World world, BlockPos pos, PlayerEntity player, ContainerBlockEntity container) {
		//Gets the stack in the main hand and the content nbt.
		ItemStack inHand = player.getStackInHand(Hand.MAIN_HAND);

		//Gets the interaction for the current content, and the current item in hand.
		ContainerBehavior behavior = getBehavior(getContent(container), inHand.getItem());

		if (behavior != null) {
			//Gets the level property, the current level, and the max level.
			IntProperty level = getLevelProperty();
			int currentLevel = getLevel(state);
			int maxLevel = getMaxLevel();

			//Gets the result of the interaction.
			return behavior.interact(state, world, pos, player, inHand, container, level, currentLevel, maxLevel);
		}

		return ActionResult.PASS;
	}

	/**
	 * Creates the block entity for the block.
	 */
	@Override
	public final BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		ContainerBlockEntity container = new ContainerBlockEntity(pos, state);
		setContent(container, CONTENT_EMPTY);
		return container;
	}

	/**
	 * Gets the render type of the block.
	 */
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	/**
	 * Gets a value indicating if the block gives an output to comparators.
	 */
	@Override
	public boolean hasComparatorOutput(BlockState state) {
		return true;
	}

	/**
	 * Gets the comparator output value.
	 */
	@Override
	public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return (int) ((float) getLevel(state) / (float) getMaxLevel() * 15f);
	}

	static {
		CONTENT_TEXTURE = EnumProperty.of("content_texture", ContentTexture.class);
	}


	/**
	 * Represents the texture used for the content.
	 */
	public enum ContentTexture implements StringIdentifiable {
		/**
		 * Water-like texture.
		 */
		Fluid("fluid"),

		/**
		 * Water-like texture without animation.
		 */
		DenseFluid("dense_fluid");

		/**
		 * Codec for converting from string.
		 */
		private static final Codec<ContentTexture> CODEC = StringIdentifiable.createCodec(ContentTexture::values);

		/**
		 * Name representing the value.
		 */
		private final String name;

		/**
		 * Initializes a new value with the name specified.
		 */
		ContentTexture(String name) {
			this.name = name;
		}

		/**
		 * Returns the value representing the string specified.
		 */
		@Nullable
		public static ContentTexture byName(@Nullable String name) {
			return CODEC.byId(name);
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


	/**
	 * Defines an interaction of the player with a container.
	 */
	@FunctionalInterface
	public interface ContainerBehavior {
		/**
		 * Executed when the player interacts with the container with an item.
		 */
		ActionResult interact(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack,
		                      ContainerBlockEntity container, IntProperty level, int currentLevel, int maxLevel);
	}
}
