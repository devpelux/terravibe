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
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import xyz.devpelux.terravibe.item.TerravibeItems;

import java.util.Optional;

/**
 * Crop of the tomato.
 */
public class TomatoCropBlock extends TallFruitCropBlock {
	/**
	 * Voxel shapes of the lower block the crop.
	 */
	private static final VoxelShape[] LOWER_AGE_TO_SHAPE;

	/**
	 * Voxel shapes of the upper block the crop.
	 */
	private static final VoxelShape[] UPPER_AGE_TO_SHAPE;

	/**
	 * Initializes a new instance.
	 */
	public TomatoCropBlock(Settings settings) {
		super(settings);
	}

	/**
	 * Gets the age when the block must have an upper and lower block.
	 */
	@Override
	public int getAgeForUpper() {
		return 4;
	}

	/**
	 * Gets the age when the plant is fully grown, and is ready to make flowers, then fruits.
	 */
	@Override
	public int getPreFloweringAge() {
		return 5;
	}

	/**
	 * Gets the fruit item of the block.
	 */
	@Override
	public ItemConvertible getFruitItem() {
		return TerravibeItems.TOMATO;
	}

	/**
	 * Gets the seeds item of the block.
	 */
	@Override
	public ItemConvertible getSeedsItem() {
		return TerravibeItems.TOMATO_SEEDS;
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
		return random.nextBetween(1, 4);
	}

	/**
	 * Gets the outline shape of the lower block.
	 */
	@Override
	public VoxelShape getLowerOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return LOWER_AGE_TO_SHAPE[getAge(state)];
	}

	/**
	 * Gets the outline shape of the upper block.
	 */
	@Override
	public VoxelShape getUpperOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return UPPER_AGE_TO_SHAPE[getAge(state)];
	}

	static {
		LOWER_AGE_TO_SHAPE = new VoxelShape[]{
				Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 1.0, 16.0),
				Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 4.0, 16.0),
				Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 8.0, 16.0),
				Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 13.0, 16.0),
				Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 15.0, 16.0),
				Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 15.0, 16.0),
				Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 15.0, 16.0),
				Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 15.0, 16.0)
		};
		UPPER_AGE_TO_SHAPE = new VoxelShape[]{
				VoxelShapes.empty(),
				VoxelShapes.empty(),
				VoxelShapes.empty(),
				VoxelShapes.empty(),
				Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 3.0, 16.0),
				Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 8.0, 16.0),
				Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 8.0, 16.0),
				Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 8.0, 16.0)
		};
	}
}
