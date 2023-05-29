package xyz.devpelux.terravibe.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemConvertible;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import xyz.devpelux.terravibe.item.TerravibeItems;

import java.util.Optional;

/**
 * Crop of the eggplant.
 */
public class EggplantCropBlock extends FruitCropBlock {
	/**
	 * Voxel shapes of the block.
	 */
	private static final VoxelShape[] AGE_TO_SHAPE;

	/**
	 * Initializes a new {@link EggplantCropBlock}.
	 */
	public EggplantCropBlock(Settings settings) {
		super(settings);
	}

	/**
	 * Gets the age when the plant is fully grown, and is ready to make flowers, then fruits.
	 */
	@Override
	public int getPreFloweringAge() {
		return 5;
	}

	/**
	 * Gets the seeds item of the block.
	 */
	@Override
	public ItemConvertible getSeedsItem() {
		return TerravibeItems.EGGPLANT_SEEDS;
	}

	/**
	 * Gets the fruit item of the block.
	 */
	@Override
	public ItemConvertible getFruitItem() {
		return TerravibeItems.EGGPLANT;
	}

	/**
	 * Gets the pick sound.
	 */
	@Override
	public Optional<SoundEvent> getPickSound() {
		return Optional.of(SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES);
	}

	/**
	 * Gets the amount of the fruit item to pick from the plant.
	 */
	@Override
	protected int getFruitItemAmount(Random random, BlockState state) {
		return random.nextBetween(1, 2);
	}

	/**
	 * Gets the outline shape of the block.
	 */
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return AGE_TO_SHAPE[getAge(state)];
	}

	static {
		AGE_TO_SHAPE = new VoxelShape[]{
				Block.createCuboidShape(0.0D, -1.0D, 0.0D, 16.0D, 1.0D, 16.0D),
				Block.createCuboidShape(0.0D, -1.0D, 0.0D, 16.0D, 3.0D, 16.0D),
				Block.createCuboidShape(0.0D, -1.0D, 0.0D, 16.0D, 3.0D, 16.0D),
				Block.createCuboidShape(0.0D, -1.0D, 0.0D, 16.0D, 7.0D, 16.0D),
				Block.createCuboidShape(0.0D, -1.0D, 0.0D, 16.0D, 12.0D, 16.0D),
				Block.createCuboidShape(0.0D, -1.0D, 0.0D, 16.0D, 15.0D, 16.0D),
				Block.createCuboidShape(0.0D, -1.0D, 0.0D, 16.0D, 15.0D, 16.0D),
				Block.createCuboidShape(0.0D, -1.0D, 0.0D, 16.0D, 15.0D, 16.0D)
		};
	}
}
