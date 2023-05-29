package xyz.devpelux.terravibe.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import xyz.devpelux.terravibe.recipe.CrushingRecipe;
import xyz.devpelux.terravibe.recipe.TerravibeRecipeTypes;

import java.util.List;
import java.util.Optional;

/**
 * Crushes an item to obtain other items.
 */
public class MortarBlock extends Block {
	/**
	 * Outline shape of the block.
	 */
	private static final VoxelShape OUTLINE_SHAPE;

	/**
	 * Initializes a new {@link MortarBlock}.
	 */
	public MortarBlock(Settings settings) {
		super(settings);
	}

	/**
	 * Gets the crush sound.
	 */
	public Optional<SoundEvent> getCrushSound() {
		return Optional.of(SoundEvents.BLOCK_ROOTED_DIRT_BREAK);
	}

	/**
	 * Executed when the block is used.
	 * Tries to crush the item in hand to obtain another item.
	 */
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		//Checks if exists a crushing recipe for the item in hand.
		Optional<CrushingRecipe> match = world.getRecipeManager()
				.getFirstMatch(TerravibeRecipeTypes.CRUSHING, new SimpleInventory(player.getStackInHand(hand)), world);

		//Gets the recipe if exists.
		if (match.isPresent()) {
			CrushingRecipe recipe = match.get();

			//This is server side.
			if (!world.isClient()) {
				//Gets the item from the player hand.
				if (!player.getAbilities().creativeMode) {
					//The item is consumed only if the player is not in creative mode.
					player.getStackInHand(hand).decrement(1);
				}

				//Gets a random count of the output item to return.
				List<ItemStack> outputs = recipe.multiCraft(player.getInventory(), world.random);
				for (ItemStack output : outputs) {
					player.getInventory().offerOrDrop(output);
				}

				//Plays the crush sound.
				getCrushSound().ifPresent(sound -> player.playSound(sound, SoundCategory.BLOCKS, 1f, 1f));
			}

			//Client: SUCCESS / Server: CONSUME
			return ActionResult.success(world.isClient());
		} else return ActionResult.PASS;
	}

	/**
	 * Gets the outline shape of the block.
	 */
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return OUTLINE_SHAPE;
	}

	static {
		OUTLINE_SHAPE = Block.createCuboidShape(5, 0, 5, 11, 10, 11);
	}
}
