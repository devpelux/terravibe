package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.core.Terravibe;
import xyz.devpelux.terravibe.item.TerravibeItems;
import xyz.devpelux.terravibe.particle.TerravibeParticleTypes;
import xyz.devpelux.terravibe.tags.TerravibeBlockTags;

import java.util.Objects;

/** A jar for mold dusts. */
public final class MoldDustJarBlock extends JarBlock {
    /** Settings of the block. */
    public static final Settings SETTINGS;

    /** Dust consuming time. */
    public static final int CONSUMING_TIME = 144;

    /** Mold spreading time. */
    public static final int SPREADING_TIME = 48;

    /** Max height from the current position to spread the mold. */
    public static final int SPREADING_HEIGHT = MoldBlock.SPREADING_HEIGHT / 2;

    /** Max radius from the current position to spread the mold. */
    public static final int SPREADING_RADIUS = MoldBlock.SPREADING_RADIUS / 2;

    /** Max height from the current position to spread the wandering spores. */
    public static final int SPORE_SPREADING_HEIGHT = MoldBlock.SPORE_SPREADING_HEIGHT / 2;

    /** Max radius from the current position to spread the wandering spores. */
    public static final int SPORE_SPREADING_RADIUS = MoldBlock.SPORE_SPREADING_RADIUS / 2;

    /** Number of spores to spread from the block every tick. */
    public static final int SPORES_PER_TICK = 1;

    /** Luminance per level. */
    public static final int LUMINANCE_PER_LEVEL = 2;

    /** Dust contained in the block. */
    public static final EnumProperty<Dust> DUST;

    /** Identifier of the block. */
    private static final Identifier ID;

    /** Initializes a new {@link MoldDustJarBlock} with default settings. */
    public static MoldDustJarBlock of() {
        return new MoldDustJarBlock(SETTINGS);
    }

    /** Initializes a new {@link MoldDustJarBlock}. */
    public MoldDustJarBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(LEVEL, 0).with(CLOSED, false).with(DUST, Dust.Unspecified));
    }

    /** Gets the default state with the specified dust. */
    public static BlockState withDust(Dust dust) {
        return TerravibeBlocks.MOLD_DUST_JAR.getDefaultState().with(DUST, dust);
    }

    /** Registers the properties of the block. */
    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(DUST);
    }

    /** Gets the identifier of the block. */
    @Override
    public Identifier getId() {
        return ID;
    }

    /** Gets the block state from nbt container data. */
    @Override
    public BlockState getStateFromContainerData(@NotNull NbtCompound nbt) {
        String dust = nbt.getString("Dust");
        return super.getStateFromContainerData(nbt).with(DUST, Objects.requireNonNullElse(Dust.byName(dust), Dust.Unspecified));
    }

    /** Save extra container data to nbt. */
    @Override
    protected void saveToNbt(@NotNull BlockState state, @NotNull BlockView world, @NotNull BlockPos pos, @NotNull NbtCompound nbt) {
        super.saveToNbt(state, world, pos, nbt);
        nbt.putString("Dust", state.get(DUST).asString());
    }

    /**
     * Executed at the block breaking when opened.
     * Drops the contained.
     */
    @Override
    public void onBreakOpened(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreakOpened(world, pos, state, player);
        //Drops the content in server world, if the player is not in creative mode.
        Dust dust = state.get(DUST);
        if (!world.isClient() && !player.getAbilities().creativeMode && dust != Dust.Unspecified) {
            ItemStack contained = new ItemStack(dust.toItem(), getLevel(state));
            dropStack(world, pos, contained);
        }
    }

    /** Gets a value indicating if the block reacts with the ticking system. */
    @Override
    public boolean hasRandomTicks(@NotNull BlockState state) {
        return !state.get(CLOSED);
    }

    /**
     * Executed every tick randomly.
     * Spreads the mold of the corresponding mold dust into the world.
     */
    @Override
    public void randomTick(@NotNull BlockState state, ServerWorld world, BlockPos pos, @NotNull Random random) {
        int level = getLevel(state);

        //Consumes the dust.
        int consumingTime = CONSUMING_TIME * (MAX_LEVEL - level + 1);
        if (random.nextInt(consumingTime) == 0) {
            int newLevel = Math.max(level - 1, 0);
            if (newLevel > 0) {
                world.setBlockState(pos, state.with(getLevelProperty(), newLevel));
            }
            else {
                world.setBlockState(pos, TerravibeBlocks.JAR.getDefaultState());
            }
        }

        //Mold to place.
        BlockState mold = getMoldState(state.get(DUST));

        //Tries to place the mold.
        if (mold != null) {
            int time = SPREADING_TIME * (MAX_LEVEL - level + 1);
            if (random.nextInt(time) == 0) {
                //Gets a random position.
                int posX = pos.getX() + random.nextBetween(-SPREADING_RADIUS, SPREADING_RADIUS);
                int posZ = pos.getZ() + random.nextBetween(-SPREADING_RADIUS, SPREADING_RADIUS);
                int posYBottom = Math.max(pos.getY() - SPREADING_HEIGHT, world.getBottomY());
                int posYTop = Math.min(pos.getY() + SPREADING_HEIGHT, world.getTopY());
                int posY = random.nextBetween(posYBottom, posYTop);

                //2 Iterators, 1 will go up and 1 will go down.
                BlockPos.Mutable spreadPosUp = new BlockPos.Mutable(posX, posY, posZ);
                BlockPos.Mutable spreadPosDown = new BlockPos.Mutable(posX, posY, posZ);

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
    }

    /** Executed every tick randomly to handle effects. */
    @Override
    public void randomDisplayTick(@NotNull BlockState state, World world, BlockPos pos, Random random) {
        if (!state.get(CLOSED)) {
            //Particle to spawn.
            DefaultParticleType particle = getMoldSporeParticleType(state.get(DUST));

            if (particle != null) {
                BlockPos.Mutable randomPos = new BlockPos.Mutable();
                int level = getLevel(state);

                //Generates the spores from the block.
                for(int i = 0; i < SPORES_PER_TICK; ++i) {
                    int time = MAX_LEVEL - level;
                    if (time == 0 || random.nextInt(time) == 0) {
                        //Spawns a particle in a random position in the block.
                        double x = pos.getX() + (0.4d + (random.nextDouble() * 0.4d));
                        double y = pos.getY() + (0.4d + (random.nextDouble() * 0.4d));
                        double z = pos.getZ() + (0.4d + (random.nextDouble() * 0.4d));
                        world.addParticle(particle, x, y, z, 0d, 0.3d, 0d);
                    }
                }

                //Generates the wandering air spores.
                for(int i = 0; i < level; ++i) {
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
                        world.addParticle(particle, x, y, z, 0d, 0d, 0d);
                    }
                }
            }
        }
    }

    /** Gets the mold block state of the corresponding mold dust. */
    private @Nullable BlockState getMoldState(@NotNull Dust dust) {
        return switch (dust) {
            case BirchMoldDust -> TerravibeBlocks.BIRCH_MOLD.getDefaultState();
            case DarkMoldDust -> TerravibeBlocks.DARK_MOLD.getDefaultState();
            case GlowingDarkMoldDust -> TerravibeBlocks.GLOWING_DARK_MOLD.getDefaultState();
            default -> null;
        };
    }

    /** Gets the mold spore particle type of the corresponding mold dust. */
    private @Nullable DefaultParticleType getMoldSporeParticleType(@NotNull Dust dust) {
        return switch (dust) {
            case BirchMoldDust -> TerravibeParticleTypes.BIRCH_MOLD_SPORE;
            case DarkMoldDust -> TerravibeParticleTypes.DARK_MOLD_SPORE;
            case GlowingDarkMoldDust -> TerravibeParticleTypes.GLOWING_DARK_MOLD_SPORE;
            default -> null;
        };
    }

    /** Gets the luminance for the current block state. */
    private static int getLuminance(@NotNull BlockState state) {
        return state.get(DUST) == Dust.GlowingDarkMoldDust ? LUMINANCE_PER_LEVEL * state.get(LEVEL) : 0;
    }

    static {
        SETTINGS = FabricBlockSettings.copyOf(Blocks.FLOWER_POT)
                .luminance(MoldDustJarBlock::getLuminance);
        DUST = EnumProperty.of("dust", Dust.class);
        ID = new Identifier(Terravibe.ID, "mold_dust_jar");
    }


    /**
     * Represents the dust type contained in a {@link MoldDustJarBlock}.
     */
    public enum Dust implements StringIdentifiable {
        /** The block contains unspecified dust. */
        Unspecified("unspecified"),

        /** The block contains birch mold dust. */
        BirchMoldDust("birch_mold_dust"),

        /** The block contains dark mold dust. */
        DarkMoldDust("dark_mold_dust"),

        /** The block contains glowing dark mold dust. */
        GlowingDarkMoldDust("glowing_dark_mold_dust");

        /** Name representing the value. */
        private final String name;

        /** Codec for converting from string. */
        private static final Codec<Dust> CODEC = StringIdentifiable.createCodec(Dust::values);

        /** Initializes a new value with the name specified. */
        Dust(String name) {
            this.name = name;
        }

        /** Returns the string representation of this instance. */
        @Override
        public @NotNull String asString() {
            return this.name;
        }

        /** Returns the string representation of this instance. */
        @Override
        public @NotNull String toString() {
            return asString();
        }

        /** Returns the dust value representing the string specified. */
        @Nullable
        public static Dust byName(@Nullable String name) {
            return CODEC.byId(name);
        }

        /** Returns the item representing this instance. */
        public @NotNull Item toItem() {
            return switch (this) {
                case BirchMoldDust -> TerravibeItems.BIRCH_MOLD_DUST;
                case DarkMoldDust -> TerravibeItems.DARK_MOLD_DUST;
                case GlowingDarkMoldDust -> TerravibeItems.GLOWING_DARK_MOLD_DUST;
                default -> Items.AIR;
            };
        }
    }
}
