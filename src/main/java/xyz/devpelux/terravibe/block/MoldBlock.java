package xyz.devpelux.terravibe.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
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
import xyz.devpelux.terravibe.tags.TerravibeBlockTags;

/** Mold that grows in darkness (with a minimum light). */
public abstract class MoldBlock extends PlantBlock {
    /** Voxel shapes of the block. */
    private static final VoxelShape[] AGE_TO_SHAPE;

    /** Max height from the current position to spread the mold. */
    public static final int SPREADING_HEIGHT = 8;

    /** Max radius from the current position to spread the mold. */
    public static final int SPREADING_RADIUS = 16;

    /** Max height from the current position to spread the wandering spores. */
    public static final int SPORE_SPREADING_HEIGHT = 4;

    /** Max radius from the current position to spread the wandering spores. */
    public static final int SPORE_SPREADING_RADIUS = 8;

    /** Initializes a new {@link MoldBlock}. */
    public MoldBlock(Settings settings) {
        super(settings);
        BlockState defaultState = getStateManager().getDefaultState();
        setDefaultState(getAgeProperty() != null ? defaultState.with(getAgeProperty(), 0) : defaultState);
    }

    /** Registers the properties of the block. */
    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        if (getAgeProperty() != null) builder.add(getAgeProperty());
    }

    /** Gets the age of the mold. */
    protected int getAge(@NotNull BlockState state) {
        return getAgeProperty() != null ? state.get(getAgeProperty()) : 0;
    }

    /** Gets a value indicating if the mold is fully grown. */
    protected boolean isFullyGrown(@NotNull BlockState state) {
        return getAgeProperty() == null || getAge(state) >= getMaxAge();
    }

    /** Gets the age property. */
    public abstract @Nullable IntProperty getAgeProperty();

    /** Gets the max age. */
    public abstract int getMaxAge();

    /** Gets the time to grow. */
    public abstract int getGrowingTime();

    /** Gets the max light to grow. */
    public abstract int getMaxLightToGrow();

    /** Gets the max light to plant. */
    public abstract int getMaxLightToPlant();

    /** Gets a value indicating if the mold can randomly spread into the world. */
    public abstract boolean canSpread(BlockState state, World world, BlockPos pos, Random random);

    /** Gets the time to spread the mold. */
    public abstract int getSpreadingTime();

    /** Gets the number of spores to spread from the block every tick. */
    public abstract int getSporesPerTick();

    /** Gets the number of spores to spread in the air per tick. */
    public abstract int getWanderingSporesPerTick();

    /** Gets the spore particle type. */
    public abstract @NotNull DefaultParticleType getSporeParticleType();

    /** Gets a value indicating if the mold can be planted on top of the specified block. */
    @Override
    protected boolean canPlantOnTop(@NotNull BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(TerravibeBlockTags.MOLD_INFESTABLE);
    }

    /** Gets a value indicating if the block can be placed at the specified position. */
    @Override
    public boolean canPlaceAt(BlockState state, @NotNull WorldView world, @NotNull BlockPos pos) {
        return world.getBaseLightLevel(pos, 0) <= getMaxLightToPlant() && super.canPlaceAt(state, world, pos);
    }

    /** Gets the required block state basing on the neighbor blocks. */
    @Override
    public BlockState getStateForNeighborUpdate(@NotNull BlockState state, @NotNull Direction direction, BlockState neighborState,
                                                WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN) {
            //If the floor is changed, check if it can be planted on the new floor.
            if (!canPlantOnTop(neighborState, world, pos)) return Blocks.AIR.getDefaultState();
        }
        return state;
    }

    /** Gets a value indicating if the block reacts with the ticking system. */
    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    /** Executed every tick randomly. */
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        //Natural growing.
        if (getAgeProperty() != null) growingTick(state, world, pos, random);
        //Spreading.
        if (canSpread(state, world, pos, random)) spreadingTick(state, world, pos, random);
    }

    /**
     * Executed every tick.
     * Handles the natural growing.
     */
    public void growingTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!isFullyGrown(state) && random.nextInt(getGrowingTime()) == 0) {
            if (world.getBaseLightLevel(pos, 0) <= getMaxLightToGrow()) {
                //Increases the age by 1.
                BlockState nextGrowState = state.with(getAgeProperty(), getAge(state) + 1);
                world.setBlockState(pos, nextGrowState, 2);
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(nextGrowState));
            }
        }
    }

    /**
     * Executed every tick.
     * Handles the spreading into the world.
     */
    public void spreadingTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (isFullyGrown(state) && random.nextInt(getSpreadingTime()) == 0) {
            //Gets a random position.
            int posX = pos.getX() + random.nextBetween(-SPREADING_RADIUS, SPREADING_RADIUS);
            int posZ = pos.getZ() + random.nextBetween(-SPREADING_RADIUS, SPREADING_RADIUS);
            int posYBottom = Math.max(pos.getY() - SPREADING_HEIGHT, world.getBottomY());
            int posYTop = Math.min(pos.getY() + SPREADING_HEIGHT, world.getTopY());
            int posY = random.nextBetween(posYBottom, posYTop);

            //2 Iterators, 1 will go up and 1 will go down.
            BlockPos.Mutable spreadPosUp = new BlockPos.Mutable(posX, posY, posZ);
            BlockPos.Mutable spreadPosDown = new BlockPos.Mutable(posX, posY, posZ);

            //Mold to place.
            BlockState mold = getDefaultState();

            //Search a valid placement in a column of max 20 blocks between posYTop and posYBottom.
            while (spreadPosDown.getY() > posYBottom || spreadPosUp.getY() < posYTop) {
                //Up iterator.
                BlockState spreadPosUpState = world.getBlockState(spreadPosUp);
                if (spreadPosUpState.isAir() || spreadPosUpState.isIn(TerravibeBlockTags.MOLD_REPLACEABLE)) {
                    if (mold.canPlaceAt(world, spreadPosUp)) {
                        world.setBlockState(spreadPosUp, mold);
                        return;
                    }
                }

                //Down iterator.
                BlockState spreadPosDownState = world.getBlockState(spreadPosDown);
                if (spreadPosDownState.isAir() || spreadPosDownState.isIn(TerravibeBlockTags.MOLD_REPLACEABLE)) {
                    if (mold.canPlaceAt(world, spreadPosDown)) {
                        world.setBlockState(spreadPosDown, mold);
                        return;
                    }
                }

                //Moves the iterators up and down.
                if (spreadPosUp.getY() < posYTop) spreadPosUp.move(Direction.UP);
                if (spreadPosDown.getY() > posYBottom) spreadPosDown.move(Direction.DOWN);
            }
        }
    }

    /** Executed every tick randomly to handle effects. */
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (canSpread(state, world, pos, random) && isFullyGrown(state)) {
            BlockPos.Mutable randomPos = new BlockPos.Mutable();

            //Generates the spores from the block.
            for(int i = 0; i < getSporesPerTick(); ++i) {
                //Spawns a particle in a random position in the block.
                double x = pos.getX() + random.nextDouble();
                double y = pos.getY() + (random.nextDouble() * 0.4d);
                double z = pos.getZ() + random.nextDouble();
                world.addParticle(getSporeParticleType(), x, y, z, 0d, 0.3d, 0d);
            }

            //Generates the wandering air spores.
            for(int i = 0; i < getWanderingSporesPerTick(); ++i) {
                //Select a random spawn position between a radius of 10.
                int posX = pos.getX() + random.nextBetween(-SPORE_SPREADING_RADIUS, SPORE_SPREADING_RADIUS);
                int posY = pos.getY() + random.nextInt(SPORE_SPREADING_HEIGHT);
                int posZ = pos.getZ() + random.nextBetween(-SPORE_SPREADING_RADIUS, SPORE_SPREADING_RADIUS);
                randomPos.set(posX, posY, posZ);

                //If the position has air, then spawns a particle in a random position in the block.
                if (!world.getBlockState(randomPos).isFullCube(world, randomPos)) {
                    double x = randomPos.getX() + random.nextDouble();
                    double y = randomPos.getY() + random.nextDouble();
                    double z = randomPos.getZ() + random.nextDouble();
                    world.addParticle(getSporeParticleType(), x, y, z, 0d, 0d, 0d);
                }
            }
        }
    }

    /** Gets the outline shape of the block. */
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[getAge(state)];
    }

    static {
        AGE_TO_SHAPE = new VoxelShape[]{
                Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D),
                Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 11.0D, 14.0D)
        };
    }
}
