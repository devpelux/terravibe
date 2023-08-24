package xyz.devpelux.terravibe.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import xyz.devpelux.terravibe.item.TerravibeItems;

/**
 * Crop of the corn.
 */
public class CornCropBlock extends TallCropBlock {
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
	public CornCropBlock(Settings settings) {
		super(settings);
	}

	/**
	 * Gets the seeds item of the block.
	 */
	@Override
	public ItemConvertible getSeedsItem() {
		return TerravibeItems.CORN_GRAINS;
	}

	/**
	 * Gets the age when the block must have an upper and lower block.
	 */
	@Override
	public int getAgeForUpper() {
		return 4;
	}

	/**
	 * Gets a value indicating if the crop can be planted on top of the specified block.
	 */
	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND);
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
				Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 6.0, 16.0),
				Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 8.0, 16.0),
				Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 10.0, 16.0),
				Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 15.0, 16.0),
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
				Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 6.0, 16.0),
				Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 9.0, 16.0),
				Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 11.0, 16.0),
				Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 11.0, 16.0)
		};
	}
}
