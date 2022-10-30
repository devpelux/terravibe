package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

/**
 * A mud block partially excavated.
 */
public class ExcavatedMudBlock extends Block implements FluidFillable {
	/**
	 * Settings of the block.
	 */
	public static final Settings SETTINGS;

	/**
	 * Waterlogged state.
	 */
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

	/**
	 * Outline shape of the block.
	 */
	private static final VoxelShape OUTLINE_SHAPE;

	/**
	 * Collision shape of the block.
	 */
	private static final VoxelShape COLLISION_SHAPE;

	/**
	 * Initializes a new {@link ExcavatedMudBlock}.
	 */
	public ExcavatedMudBlock(Settings settings) {
		super(settings);
		setDefaultState(getStateManager().getDefaultState().with(WATERLOGGED, false));
	}

	/**
	 * Initializes a new {@link ExcavatedMudBlock} with default settings.
	 */
	public static ExcavatedMudBlock of() {
		return new ExcavatedMudBlock(SETTINGS);
	}

	/**
	 * Registers the properties of the block.
	 */
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(WATERLOGGED);
	}

	/**
	 * Gets a value indicating if the block is waterlogged.
	 */
	protected boolean isWaterlogged(BlockState state) {
		return state.get(WATERLOGGED);
	}

	/**
	 * Executed when the block is replaced by another block in the specified position.
	 */
	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		super.onStateReplaced(state, world, pos, newState, moved);
		if (isWaterlogged(state) && newState.isOf(Blocks.WATER)) {
			//If the block is removed and remains only water, removes the water too.
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
	}

	/**
	 * Executed when the block replaces another block in the specified position.
	 */
	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		super.onBlockAdded(state, world, pos, oldState, notify);
		if (!world.getDimension().ultrawarm()) {
			//When the block is added, if the dimension is non ultra-warm, and the block is not waterlogged.
			//Sets the waterlogged version, then starts the fluid ticking.
			world.setBlockState(pos, state.with(WATERLOGGED, true));

			//Starts the fluid ticking.
			world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}
	}

	/**
	 * Gets a value indicating if the block can be placed at the current position.
	 */
	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		BlockState blockState = world.getBlockState(pos.up());
		return !blockState.getMaterial().isSolid() || blockState.getBlock() instanceof FenceGateBlock
				|| blockState.getBlock() instanceof PistonExtensionBlock;
	}

	/**
	 * Executed when a neighbor block is updated.
	 * Updates the current excavation shape basing on the neighbor blocks.
	 */
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
	                                            WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (isWaterlogged(state)) {
			if (neighborState.isOf(Blocks.SPONGE)) {
				//The block is waterlogged and the neighbor is a sponge.
				//Replaces the block with mud, and the sponge with wet sponge.
				world.setBlockState(neighborPos, Blocks.WET_SPONGE.getDefaultState(), 2);
				return Blocks.MUD.getDefaultState();
			}
			if (state.canPlaceAt(world, pos)) {
				//The block is waterlogged and can still be placed in the position.
				//Starts the fluid ticking.
				world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
				return state;
			}
		} else if (state.canPlaceAt(world, pos)) {
			//The block is not waterlogged, but can still be placed in the position.
			//It will not have interactions with sponge, and simply remains unchanged.
			return state;
		}
		//The block state cannot be placed in the position, so it is replaced with mud.
		return Blocks.MUD.getDefaultState();
	}

	/**
	 * Gets the placement state of the block.
	 */
	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockState state = getDefaultState();
		World world = ctx.getWorld();
		BlockPos pos = ctx.getBlockPos();

		if (state.canPlaceAt(world, pos)) {
			if (!world.getDimension().ultrawarm()) {
				//If the state can be placed in the position and the dimension is non ultra-warm.
				//Returns a waterlogged version.
				return state.with(WATERLOGGED, true);
			}
			//Else returns the default state.
			return state;
		}

		//If the state cannot be placed in the position, returns the mud state.
		return Blocks.MUD.getDefaultState();
	}

	/**
	 * Gets the current fluid state that submerges the block.
	 */
	@Override
	public FluidState getFluidState(BlockState state) {
		return isWaterlogged(state) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

	/**
	 * Gets a value indicating if is possible to pass through the block.
	 */
	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		return false;
	}

	/**
	 * Gets the ambient occlusion light level.
	 */
	@Override
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
		return 0.2F;
	}

	/**
	 * Gets a value indicating if is possible to fill the block with the specified fluid.
	 */
	@Override
	public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
		return false;
	}

	/**
	 * Tries to fill the block with the specified fluid.
	 */
	@Override
	public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
		return false;
	}

	/**
	 * Gets the outline shape of the block.
	 */
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return OUTLINE_SHAPE;
	}

	/**
	 * Gets the sides shape of the block.
	 */
	public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
		return OUTLINE_SHAPE;
	}

	/**
	 * Gets the camera collision shape of the block.
	 */
	public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return OUTLINE_SHAPE;
	}

	/**
	 * Gets the collision shape of the block.
	 */
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return COLLISION_SHAPE;
	}

	static {
		SETTINGS = FabricBlockSettings.copyOf(Blocks.DIRT)
				.sounds(BlockSoundGroup.MUD)
				.mapColor(MapColor.TERRACOTTA_CYAN);
		OUTLINE_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 11, 16);
		COLLISION_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 10, 16);
	}
}
