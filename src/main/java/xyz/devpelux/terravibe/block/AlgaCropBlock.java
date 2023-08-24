package xyz.devpelux.terravibe.block;

import net.minecraft.block.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

/**
 * A crop that must be planted underwater and grows underwater.
 */
public abstract class AlgaCropBlock extends CropBlock implements FluidFillable {
	/**
	 * Initializes a new instance.
	 */
	public AlgaCropBlock(Settings settings) {
		super(settings);
	}

	/**
	 * Registers the properties of the block.
	 */
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(getAgeProperty());
	}

	/**
	 * Gets the time to grow.
	 */
	public abstract int getGrowingTime();

	/**
	 * Gets the required light to grow.
	 */
	public abstract int getMinLightToGrow();

	/**
	 * Gets a value indicating if the mold can be planted on top of the specified block.
	 */
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return floor.isOf(Blocks.SAND) || floor.isOf(Blocks.RED_SAND);
	}

	/**
	 * Gets a value indicating if the block can be placed at the specified position.
	 */
	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		FluidState fluidState = world.getFluidState(pos);
		BlockPos floorPos = pos.down();

		//The block can be placed only underwater.
		return fluidState.isIn(FluidTags.WATER) && fluidState.isStill()
				&& canPlantOnTop(world.getBlockState(floorPos), world, floorPos);
	}

	/**
	 * Executed when a neighbor block is updated.
	 * Enables the fluid ticking if the block is not air.
	 */
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		BlockState blockState = super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
		if (!blockState.isAir()) {
			//Starts the fluid ticking.
			world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}

		return blockState;
	}

	/**
	 * Executed every tick.
	 * Handles the natural growing.
	 */
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (world.getBaseLightLevel(pos, 0) >= getMinLightToGrow()) {
			int age = getAge(state);
			if (age < getMaxAge() && random.nextInt(getGrowingTime()) == 0) {
				BlockState nextGrowState = state.with(getAgeProperty(), age + 1);
				world.setBlockState(pos, nextGrowState, 2);
				world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(nextGrowState));
			}
		}
	}

	/**
	 * Gets the current fluid state that submerges the block.
	 */
	@Override
	public FluidState getFluidState(BlockState state) {
		return Fluids.WATER.getStill(false);
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
}
