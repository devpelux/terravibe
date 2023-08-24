package xyz.devpelux.terravibe.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

/**
 * Crop that can be 2 blocks tall.
 */
public abstract class TallCropBlock extends CropBlock {
	/**
	 * Specifies which of the half side the block is in a two-blocks-tall crop.
	 */
	public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;

	/**
	 * Initializes a new instance.
	 */
	public TallCropBlock(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState().with(HALF, DoubleBlockHalf.LOWER));
	}

	/**
	 * Registers the properties of the block.
	 */
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(HALF);
	}

	/**
	 * Gets the default state with the specified half.
	 */
	protected BlockState withHalf(DoubleBlockHalf half) {
		return getDefaultState().with(HALF, half);
	}

	/**
	 * Gets the default state with the specified half and age.
	 */
	protected BlockState withHalfAndAge(DoubleBlockHalf half, int age) {
		return withHalf(half).with(getAgeProperty(), age);
	}

	/**
	 * Gets the half value for the specified block state.
	 */
	protected DoubleBlockHalf getHalf(BlockState state) {
		return state.get(HALF);
	}

	/**
	 * Gets a value indicating if the specified block state is a lower block.
	 */
	protected boolean isLower(BlockState state) {
		return getHalf(state) == DoubleBlockHalf.LOWER;
	}

	/**
	 * Gets a value indicating if the position at 1 block down is a block of the same type, with lower state.
	 */
	protected boolean hasLower(WorldView world, BlockPos pos) {
		BlockState down = world.getBlockState(pos.down());
		return down.isOf(this) && isLower(down);
	}

	/**
	 * Gets the age when the block must have an upper and lower block.
	 */
	public abstract int getAgeForUpper();

	/**
	 * Gets the placement state of the block.
	 */
	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		World world = ctx.getWorld();
		BlockPos pos = ctx.getBlockPos();
		//If there is enough space for the two blocks (upper and lower) then gets the default placement state.
		//Else gets null, so the block cannot be placed.
		return pos.getY() < world.getTopY() - 1 && world.getBlockState(pos.up()).canReplace(ctx) ? super.getPlacementState(ctx) : null;
	}

	/**
	 * Gets a value indicating if the block can be placed at the specified position.
	 */
	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            /* If the block is the upper block, it can be placed only if in the down
               position there is a block of the same type but with the lower state. */
			return hasLower(world, pos);
		} else {
			//For the lower block, applies the same rules of a generic crop.
			return super.canPlaceAt(state, world, pos);
		}
	}

	/**
	 * Gets the required block state basing on the neighbor blocks.
	 */
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
	                                            WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		DoubleBlockHalf half = getHalf(state);
		if (direction.getAxis() == Direction.Axis.Y) {
			if (half == DoubleBlockHalf.LOWER && direction == Direction.UP) {
				//Case 1: Block lower and neighbor upper.
				if (neighborState.isOf(this)) {
					/*
					 * Case 1.1: The upper is the same block.
					 * Checks the block properties.
					 * For "half", if the upper is not the upper, removes the lower block.
					 * For "age", copies the neighbor age in case has been updated.
					 */
					return getHalf(neighborState) != DoubleBlockHalf.UPPER
							? Blocks.AIR.getDefaultState() : state.with(getAgeProperty(), getAge(neighborState));
				} else {
					/*
					 * Case 1.2: The upper is a different block.
					 * If the block (that is the lower), has an age enough to have the upper block,
					 * but does not have an upper block, removes the lower block.
					 * If the block has not an age enough to have the upper block,
					 * checks if the upper is air, else removes the lower block,
					 * ensuring that there is enough space to grow.
					 */
					if (getAge(state) >= getAgeForUpper() || !neighborState.isOf(Blocks.AIR)) {
						return Blocks.AIR.getDefaultState();
					}
				}
			} else if (half == DoubleBlockHalf.UPPER && direction == Direction.DOWN) {
				//Case 2: Block upper and neighbor lower.
				if (neighborState.isOf(this)) {
					/*
					 * Case 2.1: The lower is the same block.
					 * Checks the block properties.
					 * For "half", if the lower is not the lower, removes the upper block.
					 * For "age", copies the neighbor age in case has been updated.
					 */
					return getHalf(neighborState) != DoubleBlockHalf.LOWER
							? Blocks.AIR.getDefaultState() : state.with(getAgeProperty(), getAge(neighborState));
				} else {
					//Case 2.2: The lower is a different block. Simply removes the upper block.
					return Blocks.AIR.getDefaultState();
				}
			}
		}

		//Case generic: checks the canPlaceAt.
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	/**
	 * Updates the plant age.
	 */
	protected void updateAge(BlockState state, World world, BlockPos pos, int newAge) {
		updateAge(state, world, pos, newAge, null);
	}

	/**
	 * Updates the plant age, specifying the player that causes the update.
	 * If the player is not null, a block change event is emitted by the player.
	 */
	protected void updateAge(BlockState state, World world, BlockPos pos, int newAge, @Nullable PlayerEntity player) {
		//Gets the upper and lower block positions.
		boolean isLower = isLower(state);
		BlockPos upperPos = isLower ? pos.up() : pos;
		BlockPos lowerPos = isLower ? pos : pos.down();

		if (newAge >= getAgeForUpper()) {
			//The age is enough to have both the upper and lower block.
			//Adds or updates the upper block, then, the lower is auto updated.
			updateBlock(withHalfAndAge(DoubleBlockHalf.UPPER, newAge), world, upperPos, player);
		} else {
			//The age is not enough to have both the upper and lower block.
			//Updates the lower block, then, removes the upper.
			updateBlock(withHalfAndAge(DoubleBlockHalf.LOWER, newAge), world, lowerPos, player);
			updateBlock(Blocks.AIR.getDefaultState(), world, upperPos, player);
		}
	}

	/**
	 * Updates the specified block, specifying the player that causes the update.
	 * If the player is not null, a block change event is emitted by the player.
	 */
	private void updateBlock(BlockState newState, World world, BlockPos pos, @Nullable PlayerEntity player) {
		world.setBlockState(pos, newState, 2);
		if (player != null) world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, newState));
	}

	/**
	 * Gets a value indicating if the block reacts with the ticking system.
	 */
	@Override
	public boolean hasRandomTicks(BlockState state) {
		//Only the lower block, if is not mature, can handle ticks. (The "root" of the plant)
		return isLower(state) && !isMature(state);
	}

	/**
	 * Executed every tick.
	 * Handles the natural growing.
	 */
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (world.getBaseLightLevel(pos, 0) >= 9) {
			int age = getAge(state);
			if (age < getMaxAge()) {
				//Gets the moisture from the neighbors, then tries to grow.
				//The higher the moisture (max 5), the more likely it is to grow.
				float moisture = getAvailableMoisture(this, world, pos);
				if (random.nextInt((int) (25.0F / moisture) + 1) == 0) {
					int nextAge = age + 1;

					//Sets the new age to the plant.
					updateAge(state, world, pos, nextAge);
				}
			}
		}
	}

	/**
	 * Executed when the plant is bonemealed.
	 * Grows the plant with a random amount. (By default between 2 and 5)
	 */
	@Override
	public void applyGrowth(World world, BlockPos pos, BlockState state) {
		int nextAge = Math.min(getAge(state) + getGrowthAmount(world), getMaxAge());

		//Sets the new age to the plant.
		updateAge(state, world, pos, nextAge);
	}

	/**
	 * Gets the growth amount to apply when the plant is bonemealed.
	 */
	@Override
	protected int getGrowthAmount(World world) {
		return MathHelper.nextInt(world.getRandom(), 1, 3);
	}

	/**
	 * Executed at the block breaking.
	 * Prevents dropping the plant "drops" when the plant is broken in creative mode.
	 */
	@Override
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!world.isClient) {
			if (player.isCreative()) {
				/* If the upper block was broken, prevents the lower to be broken by neighbor updates
				   removing it directly, if exists. (replacing it with an empty block or fluid) */
				if (!isLower(state)) {
					BlockPos lowerPos = pos.down();
					BlockState lowerState = world.getBlockState(lowerPos);
					if (lowerState.isOf(this) && isLower(lowerState)) {
						world.setBlockState(lowerPos, lowerState.getFluidState().getBlockState(), 35);
						world.syncWorldEvent(player, 2001, lowerPos, Block.getRawIdFromState(lowerState));
					}
				}
			} else {
				//Drops the specified stacks in the loot table.
				dropStacks(state, world, pos, null, player, player.getMainHandStack());
			}
		}

		super.onBreak(world, pos, state, player);
	}

	/**
	 * Executed after the block is broken.
	 */
	@Override
	public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
		//Prevents duplicated drops (the drops are handled in the onBreak).
		super.afterBreak(world, player, pos, Blocks.AIR.getDefaultState(), blockEntity, stack);
	}

	/**
	 * Gets the outline shape of the block.
	 */
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return isLower(state) ? getLowerOutlineShape(state, world, pos, context) : getUpperOutlineShape(state, world, pos, context);
	}

	/**
	 * Gets the outline shape of the lower block.
	 */
	public VoxelShape getLowerOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VoxelShapes.fullCube();
	}

	/**
	 * Gets the outline shape of the upper block.
	 */
	public VoxelShape getUpperOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VoxelShapes.fullCube();
	}

	/**
	 * Gets the rendering seed.
	 */
	@Override
	public long getRenderingSeed(BlockState state, BlockPos pos) {
		//The upper has the same rendering seed of the lower.
		return MathHelper.hashCode(pos.getX(), pos.down(getHalf(state) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
	}
}
