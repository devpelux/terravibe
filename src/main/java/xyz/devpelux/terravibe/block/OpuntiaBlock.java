package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.core.ModInfo;

import java.util.Optional;

/** Main block of the opuntia. */
public class OpuntiaBlock extends FacingBlock {
    /** Identifier of the block. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "opuntia");

    /** Settings of the block. */
    public static final Settings SETTINGS = FabricBlockSettings.copyOf(Blocks.CACTUS);

    /** Voxel shapes of the block (age | facing). */
    private static final VoxelShape[][] SHAPES;

    /** Face selector array. */
    private static final Direction[] FACES;

    /** Cascade breaking delay. */
    public static final int BREAKING_DELAY = 1;

    /** Growing time. */
    public static final int GROWING_TIME = 15;

    /** Aging time (Time to pass from age 1 to age 2). */
    public static final int AGING_TIME = 300;

    /** Plant growing time. */
    public static final int PLANT_GROWING_TIME = 15;

    /** Max distance. */
    public static final int MAX_DISTANCE = 5;

    /** Max age. */
    public static final int MAX_AGE = 2;

    /** Distance of the block from the root (0 = root, 7 = too distant). */
    public static final IntProperty DISTANCE;

    /** Age of the block. */
    public static final IntProperty AGE;

    /** Initializes a new {@link OpuntiaBlock}. */
    public OpuntiaBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.DOWN).with(DISTANCE, 0).with(AGE, 0));
    }

    /** Registers the properties of the block. */
    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING, DISTANCE, AGE);
    }

    /** Gets the distance from the root of specified block state. */
    public static int getDistance(@NotNull BlockState state) {
        return state.get(DISTANCE);
    }

    /** Gets the age of specified block state. */
    public static int getAge(@NotNull BlockState state) {
        return state.get(AGE);
    }

    /** Gets the facing of specified block state. */
    public static Direction getFacing(@NotNull BlockState state) {
        return state.get(FACING);
    }

    /** Gets the default state with the specified facing. */
    public BlockState withFacing(Direction facing) {
        return getDefaultState().with(FACING, facing);
    }

    /** Gets the block state when the block is placed. */
    @Nullable
    @Override
    public BlockState getPlacementState(@NotNull ItemPlacementContext ctx) {
        //Gets the direction of the placed block basing on the hit side of the target block.
        Direction facing = ctx.getSide().getOpposite();
        //Calculates the distance of the block from the root.
        int distance = calculateDistance(ctx.getWorld(), ctx.getBlockPos(), facing);

        return withFacing(facing).with(DISTANCE, distance);
    }

    /** Gets a value indicating if the plant can be planted on top of the specified block. */
    protected boolean canPlantOn(@NotNull BlockState target) {
        return target.isOf(Blocks.SAND) || target.isOf(Blocks.RED_SAND);
    }

    /** Gets a value indicating if the block can be placed at the current position. */
    @Override
    public boolean canPlaceAt(BlockState state, @NotNull WorldView world, BlockPos pos) {
        return getDistance(state) < MAX_DISTANCE;
    }

    /**
     * Executed when an entity collides with the cactus.
     * Applies the thorns damage.
     */
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, @NotNull Entity entity) {
        entity.damage(DamageSource.CACTUS, 1.0F);
    }

    /**
     * Executed when a neighbor block is updated.
     * Calculates the distance from the root of the block.
     * Schedules a tick if block is too distant from the root.
     *
     * If a block is too distant, is broken, then this causes another neighbor update, and so on...
     * This will break in cascade all the blocks connected to the broken block.
     */
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState,
                                                @NotNull WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        //Calculates the distance of the block.
        int distance = calculateDistance(world, pos, getFacing(state));
        if (distance < MAX_DISTANCE) {
            //If the distance is not too much, sets the block distance.
            return state.with(DISTANCE, distance);
        }
        else {
            //If the block is too distant, schedules a new block tick.
            //So if at the block scheduled tick, the distance remains too much, the block is broken.
            if (!world.isClient()) {
                world.createAndScheduleBlockTick(pos, this, BREAKING_DELAY);
            }
            return state;
        }
    }

    /**
     * Executed when a tick is scheduled for the block.
     * Breaks the block if is too distant from the root.
     */
    @Override
    public void scheduledTick(@NotNull BlockState state, ServerWorld world, BlockPos pos, Random random) {
        //Calculates the distance of the block, and if the block is too distant from the root, is broken.
        int distance = calculateDistance(world, pos, getFacing(state));
        if (distance >= MAX_DISTANCE) {
            world.breakBlock(pos, true);
        }
    }

    /** Gets a property indicating if the block reacts with the ticking system. */
    @Override
    public boolean hasRandomTicks(BlockState state) {
        return getAge(state) < MAX_AGE;
    }

    /** Executed every tick. */
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, @NotNull Random random) {
        //Tries to grow the block.
        growingBlockTick(state, world, pos, random);
        //Gets the updated state, then tries to grow the entire plant if the age is 1.
        state = world.getBlockState(pos);
        if (getAge(state) == 1) growingPlantTick(state, world, pos, random);
    }

    /** Executed when a block can grow. */
    protected void growingBlockTick(BlockState state, ServerWorld world, BlockPos pos, @NotNull Random random) {
        int growing_time = getAge(state) < 1 ? GROWING_TIME : AGING_TIME;
        if (random.nextInt(growing_time) == 0) {
            //Grows the block by increasing the age.
            world.setBlockState(pos, state.with(AGE, getAge(state) + 1));
        }
    }

    /** Executed when a block can generate other blocks to grow the entire plant. */
    protected void growingPlantTick(BlockState state, ServerWorld world, BlockPos pos, @NotNull Random random) {
        if (random.nextInt(PLANT_GROWING_TIME) == 0) {
            //Select a random face that will be the position for a new block.
            Direction face = Util.getRandom(FACES, random);
            BlockPos newPos = pos.mutableCopy().move(face);

            //Select randomly to place a new main block or a new flowering block.
            BlockState newState;
            if (random.nextFloat() < FloweringOpuntiaBlock.CHANCE) {
                //Facing towards the generator block.
                newState = TerravibeBlocks.FLOWERING_OPUNTIA.withFacing(face.getOpposite());
                //Applies a random sterility.
                newState = FloweringOpuntiaBlock.withRandomSterility(newState, random);
            }
            else {
                //Facing towards the generator block, distanced the generator distance + 1.
                newState = withFacing(face.getOpposite()).with(DISTANCE, getDistance(state) + 1);
            }

            //Places the new block, if there is space, and it can be placed.
            if (world.isAir(newPos) && newState.canPlaceAt(world, newPos)) {
                world.setBlockState(newPos, newState);
            }
        }
    }

    /**
     * Calculates the distance from the root.
     * If the distance is 0 the block is the root.
     * If the distance is 7 the block is too much distant from the root.
     */
    protected int calculateDistance(@NotNull BlockView world, @NotNull BlockPos pos, Direction facing) {
        //Gets the parent block state, basing on the facing.
        BlockPos parentPos = pos.mutableCopy().move(facing);
        BlockState parent = world.getBlockState(parentPos);

        if (parent.isOf(this) && getAge(parent) > 0) {
            //If the parent is an opuntia with age 1+, then gets the distance from that block + 1.
            return Math.min(getDistance(parent) + 1, MAX_DISTANCE);
        }
        else if (facing == Direction.DOWN && canPlantOn(parent)) {
            //If the parent is a block in which the opuntia can be planted, and the facing is down.
            //Then the block is the root, so the distance is zero.
            return 0;
        }
        //If the block is not the root and is not connected to another opuntia.
        //It means that is not connected to the root (distance 7).
        return MAX_DISTANCE;
    }

    /** Rotates the block in the specified facing. */
    @Override
    public BlockState rotate(@NotNull BlockState state, @NotNull BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    /** Mirrors the block in the specified facing. */
    @Override
    public BlockState mirror(@NotNull BlockState state, @NotNull BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    /** Gets a value indicating if is possible to pass through the block. */
    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    /** Gets the path node type. */
    @Override
    public Optional<PathNodeType> getPathNodeType() {
        return Optional.of(PathNodeType.DAMAGE_CACTUS);
    }

    /** Gets the outline shape of the block. */
    @Override
    public VoxelShape getOutlineShape(@NotNull BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES[getAge(state)][getFacing(state).getId()];
    }

    static {
        DISTANCE = IntProperty.of("distance", 0, MAX_DISTANCE);
        AGE = Properties.AGE_2;

        //age | facing
        SHAPES = new VoxelShape[][] {
                {
                        Block.createCuboidShape(4, -1, 4, 12, 9, 12),
                        Block.createCuboidShape(4, 7, 4, 12, 17, 12),
                        Block.createCuboidShape(4, 4, -1, 12, 12, 9),
                        Block.createCuboidShape(4, 4, 7, 12, 12, 17),
                        Block.createCuboidShape(-1, 4, 4, 9, 12, 12),
                        Block.createCuboidShape(7, 4, 4, 17, 12, 12)
                },
                {
                        Block.createCuboidShape(1, -1, 1, 15, 15, 15),
                        Block.createCuboidShape(1, 1, 1, 15, 17, 15),
                        Block.createCuboidShape(1, 1, -1, 15, 15, 15),
                        Block.createCuboidShape(1, 1, 1, 15, 15, 17),
                        Block.createCuboidShape(-1, 1, 1, 15, 15, 15),
                        Block.createCuboidShape(1, 1, 1, 17, 15, 15)
                },
                {
                        Block.createCuboidShape(1, -1, 1, 15, 15, 15),
                        Block.createCuboidShape(1, 1, 1, 15, 17, 15),
                        Block.createCuboidShape(1, 1, -1, 15, 15, 15),
                        Block.createCuboidShape(1, 1, 1, 15, 15, 17),
                        Block.createCuboidShape(-1, 1, 1, 15, 15, 15),
                        Block.createCuboidShape(1, 1, 1, 17, 15, 15)
                }
        };

        //There are more up and down faces, so there are more odds to select up or down.
        FACES = new Direction[] {
                Direction.NORTH,
                Direction.EAST,
                Direction.SOUTH,
                Direction.WEST,
                Direction.DOWN,
                Direction.DOWN,
                Direction.UP,
                Direction.UP,
                Direction.UP,
                Direction.UP,
                Direction.UP,
                Direction.UP,
                Direction.UP,
                Direction.UP
        };
    }
}
