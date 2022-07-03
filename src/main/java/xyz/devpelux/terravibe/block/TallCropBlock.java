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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Generic crop that can be 2 blocks tall. */
public abstract class TallCropBlock extends CropBlock {
    /** Specifies which of the half side the block is in a two-blocks-tall crop. */
    public static final EnumProperty<DoubleBlockHalf> HALF;

    /** Initializes a new {@link TallCropBlock}. */
    public TallCropBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(HALF, DoubleBlockHalf.LOWER).with(getAgeProperty(), 0));
    }

    /** Registers the properties of the block. */
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(HALF);
    }

    /** Gets the half value for the specified block state. */
    protected DoubleBlockHalf getHalf(@NotNull BlockState state) {
        return state.get(HALF);
    }

    /** Gets a value indicating if the specified block state is a lower block. */
    protected boolean isLower(@NotNull BlockState state) {
        return getHalf(state) == DoubleBlockHalf.LOWER;
    }

    /** Gets a value indicating if the position at 1 block down is a block of the same type, with lower state. */
    protected boolean hasLower(@NotNull WorldView world, @NotNull BlockPos pos) {
        BlockState down = world.getBlockState(pos.down());
        return down.isOf(this) && isLower(down);
    }

    /** Gets the age when the block must have an upper and lower block. */
    public abstract int getAgeForUpper();

    /** Gets the placement state of the block. */
    @Nullable
    @Override
    public BlockState getPlacementState(@NotNull ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        //If there is enough space for the two blocks (upper and lower) then gets the default placement state.
        //Else gets null, so the block cannot be placed.
        return pos.getY() < world.getTopY() - 1 && world.getBlockState(pos.up()).canReplace(ctx) ? super.getPlacementState(ctx) : null;
    }

    /** Gets a value indicating if the block can be placed at the specified position. */
    @Override
    public boolean canPlaceAt(@NotNull BlockState state, WorldView world, BlockPos pos) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            /* If the block is the upper block, it can be placed only if in the down
               position there is a block of the same type but with the lower state. */
            return hasLower(world, pos);
        } else {
            //For the lower block, applies the same rules of a generic crop.
            return super.canPlaceAt(state, world, pos);
        }
    }

    /** Gets the required block state basing on the neighbor blocks. */
    @Override
    public BlockState getStateForNeighborUpdate(@NotNull BlockState state, @NotNull Direction direction, BlockState neighborState,
                                                WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf half = getHalf(state);
        if ((direction.getAxis() == Direction.Axis.Y && half == DoubleBlockHalf.LOWER == (direction == Direction.UP))
                && (!neighborState.isOf(this) || getHalf(neighborState) == half)) {
            //If one of the two half is replaced with a wrong block, the other half is removed.
            //This also removes the lower block if a block is placed above it, removing the needed space to grow.
            return Blocks.AIR.getDefaultState();
        }
        else if (half == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos)) {
            //If the block below the lower block is replaced with an unsupported block, removes the lower block.
            return Blocks.AIR.getDefaultState();
        }

        //For other generic cases, applies the same rules of a generic crop.
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    /** Gets a property indicating if the block reacts with the ticking system. */
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
    public void randomTick(BlockState state, @NotNull ServerWorld world, BlockPos pos, Random random) {
        if (world.getBaseLightLevel(pos, 0) >= 9) {
            int age = getAge(state);
            if (age < getMaxAge()) {
                //Gets the moisture from the neighbors, then tries to grow.
                //The higher the moisture (max 5), the more likely it is to grow.
                float moisture = getAvailableMoisture(this, world, pos);
                if (random.nextInt((int)(25.0F / moisture) + 1) == 0) {
                    int nextAge = age + 1;
                    //Sets the new age to the lower block, then, if the age is enough to have the upper block, places the upper block with the same age.
                    world.setBlockState(pos, withAge(nextAge), 2);
                    if (nextAge >= getAgeForUpper()) {
                        world.setBlockState(pos.up(), withAge(nextAge).with(HALF, DoubleBlockHalf.UPPER), 2);
                    }
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
        if (isLower(state)) {
            //The block is the lower block.
            //Sets the new age to the lower block, then, if the age is enough to have the upper block, places the upper block with the same age.
            world.setBlockState(pos, withAge(nextAge), 2); //Lower
            if (nextAge >= getAgeForUpper()) {
                world.setBlockState(pos.up(), withAge(nextAge).with(HALF, DoubleBlockHalf.UPPER), 2); //Upper
            }
        }
        else {
            //The block is the upper block.
            //Sets the new age to both the upper and lower blocks.
            world.setBlockState(pos.down(), withAge(nextAge), 2); //Lower
            world.setBlockState(pos, withAge(nextAge).with(HALF, DoubleBlockHalf.UPPER), 2); //Upper
        }
    }

    /**
     * Executed at the block breaking.
     * Prevents dropping the plant "drops" when the plant is broken in creative mode.
     */
    @Override
    public void onBreak(@NotNull World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient) {
            if (player.isCreative()) {
                if (!isLower(state) && hasLower(world, pos)) {
                    //Replaces the lower block with air without drop anything.
                    BlockPos downPos = pos.down();
                    world.setBlockState(downPos, Blocks.AIR.getDefaultState(), 35);
                    world.syncWorldEvent(player, 2001, downPos, Block.getRawIdFromState(world.getBlockState(downPos)));
                }
            } else {
                //Drops the specified stacks in the loot table.
                dropStacks(state, world, pos, null, player, player.getMainHandStack());
            }
        }

        super.onBreak(world, pos, state, player);
    }

    /** Executed after the block is broken. */
    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        super.afterBreak(world, player, pos, Blocks.AIR.getDefaultState(), blockEntity, stack);
    }

    /** Gets the outline shape of the block. */
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return isLower(state) ? getLowerOutlineShape(state, world, pos, context) : getUpperOutlineShape(state, world, pos, context);
    }

    /** Gets the outline shape of the lower block. */
    @SuppressWarnings("unused")
    public VoxelShape getLowerOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.fullCube();
    }

    /** Gets the outline shape of the upper block. */
    @SuppressWarnings("unused")
    public VoxelShape getUpperOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.fullCube();
    }

    /** Gets the rendering seed. */
    @SuppressWarnings("deprecation")
    @Override
    public long getRenderingSeed(@NotNull BlockState state, @NotNull BlockPos pos) {
        return MathHelper.hashCode(pos.getX(), pos.down(state.get(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
    }

    static {
        HALF = Properties.DOUBLE_BLOCK_HALF;
    }
}
