package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

/**
 * Food made from milk coagulation and eventually other ingredients.
 */
public class CheeseWheelBlock extends HorizontalFacingBlock {
	/**
	 * Settings of the block.
	 */
	public static final Settings SETTINGS;

	/**
	 * Voxel shape of the block.
	 */
	private static final VoxelShape VOXEL_SHAPE;

	/**
	 * Initializes a new {@link CheeseWheelBlock}.
	 */
	public CheeseWheelBlock(Settings settings) {
		super(settings);
		setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH));
	}

	/**
	 * Initializes a new {@link CheeseWheelBlock} with default settings.
	 */
	public static CheeseWheelBlock of() {
		return new CheeseWheelBlock(SETTINGS);
	}

	/**
	 * Registers the properties of the block.
	 */
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(FACING);
	}

	/**
	 * Gets the placement state of the block.
	 */
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		//When the player places the block, gets the opposite direction.
		return getDefaultState().with(FACING, ctx.getPlayerFacing());
	}

	/**
	 * Gets a value indicating if is possible to pass through the block.
	 */
	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		return false;
	}

	/**
	 * Gets the outline shape of the block.
	 */
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VOXEL_SHAPE;
	}

	static {
		SETTINGS = FabricBlockSettings.copyOf(Blocks.CAKE);
		VOXEL_SHAPE = Block.createCuboidShape(2, 0, 2, 14, 4, 14);
	}
}
