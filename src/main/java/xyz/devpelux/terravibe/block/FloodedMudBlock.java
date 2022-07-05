package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.core.Util;

/** A mud block excavated and flooded with water. */
public class FloodedMudBlock extends Block implements Waterloggable {
    /** Identifier of the block. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "flooded_mud");

    /** Settings of the block. */
    public static final Settings SETTINGS;

    /** Voxel shapes of the block. */
    private static final VoxelShape[] VOXEL_SHAPES;

    /** North excavation. */
    public static final BooleanProperty NORTH = Properties.NORTH;

    /** East excavation. */
    public static final BooleanProperty EAST = Properties.EAST;

    /** South excavation. */
    public static final BooleanProperty SOUTH = Properties.SOUTH;

    /** West excavation. */
    public static final BooleanProperty WEST = Properties.WEST;

    /** Waterlogged state. */
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    /** Initializes a new {@link FloodedMudBlock}. */
    public FloodedMudBlock(Settings settings) {
        super(settings);
        BlockState defaultState = getStateManager().getDefaultState()
                .with(NORTH, false)
                .with(EAST, false)
                .with(SOUTH, false)
                .with(WEST, false)
                .with(WATERLOGGED, true);
        setDefaultState(defaultState);
    }

    /** Registers the properties of the block. */
    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(NORTH, EAST, SOUTH, WEST, WATERLOGGED);
    }

    /** Gets the excavation sound. */
    protected SoundEvent getExcavationSound() {
        return SoundEvents.ITEM_SHOVEL_FLATTEN;
    }

    /**
     * Executed when the block is used.
     * De-excavates the block by converting to mud.
     */
    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, @NotNull PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getStackInHand(hand).getItem() instanceof ShovelItem) {
            if (!world.isClient) {
                //Converts the block to mud.
                world.setBlockState(pos, Blocks.MUD.getDefaultState());
                //Plays the excavation sound.
                player.playSound(getExcavationSound(), SoundCategory.BLOCKS, 1f, 1f);
            }

            //Client: SUCCESS / Server: CONSUME
            return ActionResult.success(world.isClient());
        }

        return ActionResult.PASS;
    }

    /**
     * Executed at the block breaking.
     * Removes the water.
     */
    @Override
    public void onBreak(@NotNull World world, BlockPos pos, @NotNull BlockState state, PlayerEntity player) {
        world.setBlockState(pos, state.with(WATERLOGGED, false));
        super.onBreak(world, pos, state, player);
    }

    /** Gets a value indicating if the block can be placed at the current position. */
    @SuppressWarnings("deprecation")
    @Override
    public boolean canPlaceAt(BlockState state, @NotNull WorldView world, @NotNull BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.up());
        return !blockState.getMaterial().isSolid() || blockState.getBlock() instanceof FenceGateBlock || blockState.getBlock() instanceof PistonExtensionBlock;
    }

    /** Gets the required block state basing on the neighbor blocks. */
    protected BlockState getStateFromNeighbors(@NotNull WorldAccess world, @NotNull BlockPos pos) {
        if (getDefaultState().canPlaceAt(world, pos)) {
            boolean north = world.getBlockState(pos.north()).isOf(TerravibeBlocks.FLOODED_MUD);
            boolean east = world.getBlockState(pos.east()).isOf(TerravibeBlocks.FLOODED_MUD);
            boolean south = world.getBlockState(pos.south()).isOf(TerravibeBlocks.FLOODED_MUD);
            boolean west = world.getBlockState(pos.west()).isOf(TerravibeBlocks.FLOODED_MUD);
            return getDefaultState().with(NORTH, north).with(EAST, east).with(SOUTH, south).with(WEST, west);
        }
        return Blocks.MUD.getDefaultState();
    }

    /**
     * Executed when a neighbor block is updated.
     * Updates the current excavation shape basing on the neighbor blocks.
     */
    @SuppressWarnings("deprecation")
    @Override
    public BlockState getStateForNeighborUpdate(@NotNull BlockState state, Direction direction, BlockState neighborState,
                                                @NotNull WorldAccess world, @NotNull BlockPos pos, BlockPos neighborPos) {
        //Gets the updated state with the required excavation shape basing on the neighbor blocks.
        BlockState updatedState = getStateFromNeighbors(world, pos);

        if (updatedState.isOf(TerravibeBlocks.FLOODED_MUD)) {
            //Starts the fluid ticking for the block, if waterlogged.
            if (state.get(WATERLOGGED)) {
                world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
            }
        }

        return updatedState;
    }

    /** Gets the placement state of the block. */
    @Nullable
    @Override
    public BlockState getPlacementState(@NotNull ItemPlacementContext ctx) {
        return getStateFromNeighbors(ctx.getWorld(), ctx.getBlockPos());
    }

    /** Gets the current fluid state that submerges the block. */
    @SuppressWarnings("deprecation")
    @Override
    public FluidState getFluidState(@NotNull BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    /** Gets a value indicating if the block can be filled with the specified fluid. */
    @Override
    public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        return false;
    }

    /** Try to fill the block with the specified fluid. */
    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        return false;
    }

    /** Try to drain the fluid that fills the block. */
    @Override
    public ItemStack tryDrainFluid(WorldAccess world, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }

    /** Gets a value indicating if is possible to pass through the block. */
    @SuppressWarnings("deprecation")
    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    /** Gets the ambient occlusion light level. */
    @SuppressWarnings("deprecation")
    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 0.2F;
    }

    /** Gets the outline shape of the block. */
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getVoxelShape(state);
    }

    /** Gets the voxel shape of the block basing on its state. */
    private static VoxelShape getVoxelShape(@NotNull BlockState state) {
        //Removes the shapes basing on the excavated side.
        int north = state.get(NORTH) ? 0b1110 : 0b1111;
        int east = state.get(EAST) ? 0b1101 : 0b1111;
        int south = state.get(SOUTH) ? 0b1011 : 0b1111;
        int west = state.get(WEST) ? 0b0111 : 0b1111;

        int shapeIdx = 0b1111;
        shapeIdx &= north;
        shapeIdx &= east;
        shapeIdx &= south;
        shapeIdx &= west;

        //Then gets the corresponding shape.
        return VOXEL_SHAPES[shapeIdx];
    }

    static {
        SETTINGS = FabricBlockSettings.copyOf(Blocks.DIRT)
                .sounds(BlockSoundGroup.MUD)
                .mapColor(MapColor.TERRACOTTA_CYAN);

        VoxelShape bottom = Block.createCuboidShape(0, 0, 0, 16, 11, 16);
        VoxelShape north = Block.createCuboidShape(0, 11, 0, 16, 16, 2);
        VoxelShape east = Block.createCuboidShape(14, 11, 0, 16, 16, 16);
        VoxelShape south = Block.createCuboidShape(0, 11, 14, 16, 16, 16);
        VoxelShape west = Block.createCuboidShape(0, 11, 0, 2, 16, 16);

        /*
         * The shapes are a combination of:
         * Bottom = 0000
         * North  = 0001
         * East   = 0010
         * South  = 0100
         * West   = 1000
         */

        VOXEL_SHAPES = new VoxelShape[]{
                Util.combineVoxelShapes(bottom),
                Util.combineVoxelShapes(bottom, north),
                Util.combineVoxelShapes(bottom, east),
                Util.combineVoxelShapes(bottom, east, north),
                Util.combineVoxelShapes(bottom, south),
                Util.combineVoxelShapes(bottom, south, north),
                Util.combineVoxelShapes(bottom, south, east),
                Util.combineVoxelShapes(bottom, south, east, north),
                Util.combineVoxelShapes(bottom, west),
                Util.combineVoxelShapes(bottom, west, north),
                Util.combineVoxelShapes(bottom, west, east),
                Util.combineVoxelShapes(bottom, west, east, north),
                Util.combineVoxelShapes(bottom, west, south),
                Util.combineVoxelShapes(bottom, west, south, north),
                Util.combineVoxelShapes(bottom, west, south, east),
                Util.combineVoxelShapes(bottom, west, south, east, north)
        };
    }
}
