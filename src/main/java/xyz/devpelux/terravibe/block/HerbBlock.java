package xyz.devpelux.terravibe.block;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

/**
 * A herb with two stages that can be planted everywhere (in dirt).
 */
public class HerbBlock extends PlantBlock implements Fertilizable {
	/**
	 * Age of the herb.
	 */
	public static final IntProperty AGE;

	/**
	 * Required light to grow.
	 */
	public static final int MIN_LIGHT_TO_GROW = 7;

	/**
	 * Voxel shapes of the block.
	 */
	private static final VoxelShape[] AGE_TO_SHAPE;

	/**
	 * Time to grow.
	 */
	private final int growingTime;

	/**
	 * Initializes a new instance.
	 */
	public HerbBlock(Settings settings, int growingTime) {
		super(settings);
		this.growingTime = growingTime;
		setDefaultState(getDefaultState().with(AGE, 0));
	}

	/**
	 * Registers the properties of the block.
	 */
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(AGE);
	}

	/**
	 * Gets the age of the bush.
	 */
	protected int getAge(BlockState state) {
		return state.get(AGE);
	}

	/**
	 * Gets a value indicating if the block reacts with the ticking system.
	 */
	@Override
	public boolean hasRandomTicks(BlockState state) {
		return getAge(state) == 0;
	}

	/**
	 * Executed every tick.
	 * Handles the natural growing.
	 */
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (getAge(state) == 0 && random.nextInt(growingTime) == 0
				&& world.getBaseLightLevel(pos, 0) >= MIN_LIGHT_TO_GROW) {
			//Sets the age to 1.
			BlockState nextGrowState = state.with(AGE, 1);
			world.setBlockState(pos, nextGrowState, 2);
			world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(nextGrowState));
		}
	}

	/**
	 * Gets a value indicating if the plant can be fertilized with bonemeal.
	 */
	@Override
	public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
		return getAge(state) == 0;
	}

	/**
	 * Gets a value indicating if the plant can grow if bonemealed.
	 */
	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return true;
	}

	/**
	 * Executed when the plant is bonemealed.
	 */
	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		//Sets the age to 1.
		world.setBlockState(pos, state.with(AGE, 1), 2);
	}

	/**
	 * Gets the outline shape of the block.
	 */
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return AGE_TO_SHAPE[getAge(state)];
	}

	static {
		AGE = Properties.AGE_1;
		AGE_TO_SHAPE = new VoxelShape[]{
				Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
				Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D)
		};
	}
}
