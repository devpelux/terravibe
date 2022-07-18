package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.item.TerravibeItems;

import java.util.Optional;

/** Flowering block of the opuntia. */
public class FloweringOpuntiaBlock extends FacingBlock {
    /** Identifier of the block. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "flowering_opuntia");

    /** Settings of the block. */
    public static final Settings SETTINGS = FabricBlockSettings.copyOf(Blocks.CACTUS);

    /** Voxel shapes of the block (age | facing). */
    private static final VoxelShape[][] SHAPES;

    /** Cascade breaking delay. */
    public static final int BREAKING_DELAY = 1;

    /** Flowering opuntia chance. */
    public static final float CHANCE = 0.5f;

    /** Sterility chance. */
    public static final float STERILITY_CHANCE = 0.70f;

    /** Growing time. */
    public static final int GROWING_TIME = 15;

    /** Max age. */
    public static final int MAX_AGE = Properties.AGE_3_MAX;

    /** Age of the block. */
    public static final IntProperty AGE = Properties.AGE_3;

    /** Specifies if the block is sterile and will not produce prickly pears. */
    public static final BooleanProperty STERILE;

    /** Initializes a new {@link FloweringOpuntiaBlock}. */
    public FloweringOpuntiaBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.DOWN).with(AGE, 0).with(STERILE, false));
    }

    /** Registers the properties of the block. */
    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING, AGE, STERILE);
    }

    /** Gets the age of specified block state. */
    public static int getAge(@NotNull BlockState state) {
        return state.get(AGE);
    }

    /** Gets a value indicating if specified block state is sterile. */
    public static boolean isSterile(@NotNull BlockState state) {
        return state.get(STERILE);
    }

    /** Applies a random sterility to the block. */
    public static BlockState applyRandomSterility(@NotNull BlockState state, @NotNull Random random) {
        return state.with(STERILE, random.nextFloat() < STERILITY_CHANCE);
    }

    /** Gets the facing of specified block state. */
    public static Direction getFacing(@NotNull BlockState state) {
        return state.get(FACING);
    }

    /** Gets the default state with the specified facing. */
    public static BlockState withFacing(Direction facing) {
        return TerravibeBlocks.FLOWERING_OPUNTIA.getDefaultState().with(FACING, facing);
    }

    /** Gets the age when the plant is fully grown, and is ready to make flowers, then fruits. */
    public int getPreFloweringAge() {
        return 1;
    }

    /** Gets the fruit item of the block. */
    public ItemConvertible getFruitItem() {
        return TerravibeItems.PRICKLY_PEAR;
    }

    /** Gets the amount of the fruit item to pick from the plant. */
    public int getFruitItemAmount(@NotNull Random random, @NotNull BlockState state) {
        return random.nextBetween(2, 4);
    }

    /** Gets the pick sound. */
    public Optional<SoundEvent> getPickSound() {
        return Optional.of(SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES);
    }

    /** Gets the block state when the block is placed. */
    @Nullable
    @Override
    public BlockState getPlacementState(@NotNull ItemPlacementContext ctx) {
        //Gets the direction of the placed block basing on the hit side of the target block.
        Direction facing = ctx.getSide().getOpposite();

        return getDefaultState().with(FACING, facing);
    }

    /** Gets a value indicating if the plant can be planted on top of the specified block. */
    protected boolean canPlantOn(@NotNull BlockState target) {
        return target.isOf(TerravibeBlocks.OPUNTIA) && OpuntiaBlock.getAge(target) > 0;
    }

    /** Gets a value indicating if the block can be placed at the current position. */
    @Override
    public boolean canPlaceAt(BlockState state, @NotNull WorldView world, @NotNull BlockPos pos) {
        return canPlantOn(world.getBlockState(pos.mutableCopy().move(getFacing(state))));
    }

    /**
     * Executed when the plant is used.
     * Gets the drops, then reset the plant age to the flowering age.
     */
    @Override
    public ActionResult onUse(@NotNull BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.get(AGE) == MAX_AGE) {
            //Drops the specified fruit amount if the age is the max age.
            int nDrops = getFruitItemAmount(world.random, state);
            dropStack(world, pos, new ItemStack(getFruitItem(), nDrops));

            //Plays the pick sound.
            getPickSound().ifPresent(sound -> world.playSound(null, pos, sound, SoundCategory.BLOCKS,
                    1.0F, 0.8F + world.random.nextFloat() * 0.4F));

            //Reset the plant age to the flowering age.
            BlockState blockState = state.with(AGE, getPreFloweringAge());
            world.setBlockState(pos, blockState, 2);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, blockState));
            return ActionResult.success(world.isClient);
        }
        return super.onUse(state, world, pos, player, hand, hit);
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
     * Schedules a new block tick that will check if the block can stay at the current position.
     */
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState,
                                                @NotNull WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!world.isClient()) {
            world.createAndScheduleBlockTick(pos, this, BREAKING_DELAY);
        }
        return state;
    }

    /**
     * Executed when a tick is scheduled for the block.
     * Breaks the block if it cannot stay at the current position.
     */
    @Override
    public void scheduledTick(@NotNull BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.canPlaceAt(world, pos)) {
            world.breakBlock(pos, true);
        }
    }

    /** Gets a property indicating if the block reacts with the ticking system. */
    @Override
    public boolean hasRandomTicks(BlockState state) {
        return getAge(state) < MAX_AGE;
    }

    /**
     * Executed every tick.
     * Handles the natural growing.
     */
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, @NotNull Random random) {
        if (!isSterile(state) || getAge(state) < getPreFloweringAge()) {
            //Tries to grow the block.
            if (random.nextInt(GROWING_TIME) == 0) {
                world.setBlockState(pos, state.with(AGE, getAge(state) + 1));
            }
        }
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
        STERILE = BooleanProperty.of("sterile");

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
                        Block.createCuboidShape(3, -1, 3, 13, 11, 13),
                        Block.createCuboidShape(3, 5, 3, 13, 17, 13),
                        Block.createCuboidShape(3, 3, -1, 13, 13, 11),
                        Block.createCuboidShape(3, 3, 5, 13, 13, 17),
                        Block.createCuboidShape(-1, 3, 3, 11, 13, 13),
                        Block.createCuboidShape(5, 3, 3, 17, 13, 13)
                },
                {
                        Block.createCuboidShape(3, -1, 3, 13, 11, 13),
                        Block.createCuboidShape(3, 5, 3, 13, 17, 13),
                        Block.createCuboidShape(3, 3, -1, 13, 13, 11),
                        Block.createCuboidShape(3, 3, 5, 13, 13, 17),
                        Block.createCuboidShape(-1, 3, 3, 11, 13, 13),
                        Block.createCuboidShape(5, 3, 3, 17, 13, 13)
                },
                {
                        Block.createCuboidShape(3, -1, 3, 13, 11, 13),
                        Block.createCuboidShape(3, 5, 3, 13, 17, 13),
                        Block.createCuboidShape(3, 3, -1, 13, 13, 11),
                        Block.createCuboidShape(3, 3, 5, 13, 13, 17),
                        Block.createCuboidShape(-1, 3, 3, 11, 13, 13),
                        Block.createCuboidShape(5, 3, 3, 17, 13, 13)
                }
        };
    }
}
