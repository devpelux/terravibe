package xyz.devpelux.terravibe.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import xyz.devpelux.terravibe.item.TerravibeItems;

/**
 * Crop of the rice.
 */
public class RiceCropBlock extends FloodedCropBlock {
	/**
	 * Voxel shapes of the crop.
	 */
	private static final VoxelShape[] AGE_TO_SHAPE;

	/**
	 * Initializes a new instance.
	 */
	public RiceCropBlock(Settings settings) {
		super(settings);
	}

	/**
	 * Gets the time to grow.
	 */
	@Override
	public int getGrowingTime() {
		return 7;
	}

	/**
	 * Gets the required light to grow.
	 */
	@Override
	public int getMinLightToGrow() {
		return 9;
	}

	/**
	 * Gets the seeds item of the block.
	 */
	@Override
	public ItemConvertible getSeedsItem() {
		return TerravibeItems.RICE_GRAINS;
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
				Block.createCuboidShape(0.0, -5.0, 0.0, 16.0, -3.0, 16.0),
				Block.createCuboidShape(0.0, -5.0, 0.0, 16.0, 0.0, 16.0),
				Block.createCuboidShape(0.0, -5.0, 0.0, 16.0, 2.0, 16.0),
				Block.createCuboidShape(0.0, -5.0, 0.0, 16.0, 5.0, 16.0),
				Block.createCuboidShape(0.0, -5.0, 0.0, 16.0, 9.0, 16.0),
				Block.createCuboidShape(0.0, -5.0, 0.0, 16.0, 14.0, 16.0),
				Block.createCuboidShape(0.0, -5.0, 0.0, 16.0, 15.0, 16.0),
				Block.createCuboidShape(0.0, -5.0, 0.0, 16.0, 16.0, 16.0)
		};
	}
}
