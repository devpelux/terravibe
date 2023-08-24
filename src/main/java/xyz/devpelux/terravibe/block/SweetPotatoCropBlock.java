package xyz.devpelux.terravibe.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.item.TerravibeItems;

/**
 * Crop of the sweet potato.
 */
public class SweetPotatoCropBlock extends CropBlock {
	/**
	 * Voxel shapes of the block.
	 */
	private static final VoxelShape[] AGE_TO_SHAPE;

	/**
	 * Initializes a new instance.
	 */
	public SweetPotatoCropBlock(Settings settings) {
		super(settings);
	}

	/**
	 * Gets the seeds item of the block.
	 */
	@Override
	public ItemConvertible getSeedsItem() {
		return TerravibeItems.SWEET_POTATO;
	}

	/**
	 * Gets the placement state of the block.
	 */
	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		if (ctx.getStack().isOf(TerravibeItems.SWEET_POTATO)) {
			//Crops planted with sweet potatoes have an initial age of 1.
			return withAge(1);
		}
		return super.getPlacementState(ctx);
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
				Block.createCuboidShape(0.0D, -1.0D, 0.0D, 16.0D, 2.0D, 16.0D),
				Block.createCuboidShape(0.0D, -1.0D, 0.0D, 16.0D, 4.0D, 16.0D),
				Block.createCuboidShape(0.0D, -1.0D, 0.0D, 16.0D, 7.0D, 16.0D),
				Block.createCuboidShape(0.0D, -1.0D, 0.0D, 16.0D, 7.0D, 16.0D),
				Block.createCuboidShape(0.0D, -1.0D, 0.0D, 16.0D, 11.0D, 16.0D),
				Block.createCuboidShape(0.0D, -1.0D, 0.0D, 16.0D, 11.0D, 16.0D),
				Block.createCuboidShape(0.0D, -1.0D, 0.0D, 16.0D, 11.0D, 16.0D),
				Block.createCuboidShape(0.0D, -1.0D, 0.0D, 16.0D, 15.0D, 16.0D)
		};
	}
}
