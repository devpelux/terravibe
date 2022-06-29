package xyz.devpelux.terravibe.block;

import net.minecraft.block.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
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
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/** Generic crop that can be planted partially underwater. */
public abstract class FloodedCropBlock extends PlantBlock implements Fertilizable {
    /** Max age. */
    public static final int MAX_AGE = 7;

    /** Age of the crop. */
    public static final IntProperty AGE = Properties.AGE_7;

    /** Voxel shapes of the crop. */
    private static final VoxelShape[] AGE_TO_SHAPE;

    /** Growing time. */
    public static final int GROWING_TIME = 6;

    /** List of all flooded fertile blocks that can be used to plant flooded crops. */
    private static final Set<Block> FLOODED_FERTILE_BLOCKS = new HashSet<>();

    /** Initializes a new {@link FloodedCropBlock}. */
    public FloodedCropBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(getAgeProperty(), 0));
    }

    /** Registers a flooded fertile block that can be used to plant flooded crops. */
    public static void registerFloodedFertileBlock(Block block) {
        FLOODED_FERTILE_BLOCKS.add(block);
    }

    /** Gets a value indicating if the specified block is a flooded fertile block. */
    public static boolean isBlockFloodedFertile(Block block) {
        return FLOODED_FERTILE_BLOCKS.contains(block);
    }

    /** Registers the properties of the crop. */
    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    /** Gets the age property. */
    public IntProperty getAgeProperty() {
        return AGE;
    }

    /** Gets the max age. */
    public int getMaxAge() {
        return MAX_AGE;
    }

    /** Gets the age of the crop. */
    protected int getAge(@NotNull BlockState state) {
        return state.get(getAgeProperty());
    }

    /** Gets a value indicating if the crop is mature. */
    public boolean isMature(@NotNull BlockState state) {
        return state.get(getAgeProperty()) >= getMaxAge();
    }

    /** Gets the default state with the specified age. */
    public BlockState withAge(int age) {
        return getDefaultState().with(getAgeProperty(), age);
    }

    /** Gets the stack to pick from the crop. */
    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(this.getSeedsItem());
    }

    /** Gets the seeds item of the crop. */
    protected abstract ItemConvertible getSeedsItem();

    /** Gets the required light to grow. */
    public abstract int getMinLightToGrow();

    /** Gets a value indicating if the crop can be placed at the current position. */
    @Override
    public boolean canPlaceAt(BlockState state, @NotNull WorldView world, BlockPos pos) {
        return (world.getBaseLightLevel(pos, 0) >= getMinLightToGrow() || world.isSkyVisible(pos)) && super.canPlaceAt(state, world, pos);
    }

    /** Gets a value indicating if the crop can be planted on top of the specified block. */
    @Override
    protected boolean canPlantOnTop(@NotNull BlockState floor, BlockView world, BlockPos pos) {
        return isBlockFloodedFertile(floor.getBlock());
    }

    /** Gets a property indicating if the crop reacts with the ticking system. */
    @Override
    public boolean hasRandomTicks(BlockState state) {
        return !this.isMature(state);
    }

    /**
     * Executed every tick.
     * Handles the natural growing.
     */
    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, @NotNull ServerWorld world, BlockPos pos, Random random) {
        if (!isMature(state) && random.nextInt(GROWING_TIME) == 0
                && world.getBaseLightLevel(pos.up(), 0) >= getMinLightToGrow()) {
            //Increases the age by 1.
            world.setBlockState(pos, withAge(getAge(state) + 1), 2);
        }
    }

    /** Gets a value indicating if the plant can be fertilized with bonemeal. */
    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return !this.isMature(state);
    }

    /** Gets a value indicating if the plant can grow if bonemealed. */
    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    /** Executed when the plant is bonemealed. */
    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        this.applyGrowth(world, pos, state);
    }

    /** Grows the plant with bonemeal. */
    public void applyGrowth(World world, BlockPos pos, BlockState state) {
        int age = Math.min(getMaxAge(), getAge(state) + getGrowthAmount(world));
        world.setBlockState(pos, this.withAge(age), 2);
    }

    /** Gets plant growth speed with bonemeal. */
    protected int getGrowthAmount(@NotNull World world) {
        return world.getRandom().nextBetween(2, 5);
    }

    /** Gets the ray-cast shape of the block. */
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getRaycastShape(@NotNull BlockState state, BlockView world, BlockPos pos) {
        return getOutlineShape(state, world, pos, ShapeContext.absent());
    }

    /** Gets the outline shape of the block. */
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(@NotNull BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[state.get(getAgeProperty())];
    }

    static {
        AGE_TO_SHAPE = new VoxelShape[]{
                Block.createCuboidShape(0.0, -5.0, 0.0, 16.0, -3.0, 16.0),
                Block.createCuboidShape(0.0, -5.0, 0.0, 16.0, 0.0, 16.0),
                Block.createCuboidShape(0.0, -5.0, 0.0, 16.0, 2.0, 16.0),
                Block.createCuboidShape(0.0, -5.0, 0.0, 16.0, 5.0, 16.0),
                Block.createCuboidShape(0.0, -5.0, 0.0, 16.0, 9.0, 16.0),
                Block.createCuboidShape(0.0, -5.0, 0.0, 16.0, 14.0, 16.0),
                Block.createCuboidShape(0.0, -5.0, 0.0, 16.0, 15.0, 16.0),
                Block.createCuboidShape(0.0, -5.0, 0.0, 16.0, 16.0, 16.0)
        };
    }
}
