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
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.item.TerravibeItems;
import xyz.devpelux.terravibe.particle.TerravibeParticleTypes;

import java.util.Objects;

/** A jar for dusts. */
public final class DustJarBlock extends JarBlock {
    /** Settings of the block. */
    public static final Settings SETTINGS;

    /** Dust consuming time. */
    public static final int CONSUMING_TIME = 144;

    /** Max height from the current position to spread the wandering dust. */
    public static final int DUST_SPREADING_HEIGHT = 4;

    /** Max radius from the current position to spread the wandering dust. */
    public static final int DUST_SPREADING_RADIUS = 4;

    /** Number of dust particles to spread from the block every tick. */
    public static final int DUST_PER_TICK = 1;

    /** Luminance per level. */
    public static final int LUMINANCE_PER_LEVEL = 4;

    /** Dust contained in the block. */
    public static final EnumProperty<Dust> DUST;

    /** Identifier of the block. */
    private static final Identifier ID;

    /** Initializes a new {@link DustJarBlock} with default settings. */
    public static DustJarBlock of() {
        return new DustJarBlock(SETTINGS);
    }

    /** Initializes a new {@link DustJarBlock}. */
    public DustJarBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(LEVEL, 0).with(CLOSED, false).with(DUST, Dust.Unspecified));
    }

    /** Gets the default state with the specified dust. */
    public static BlockState withDust(Dust dust) {
        return TerravibeBlocks.DUST_JAR.getDefaultState().with(DUST, dust);
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
     * Consumes the dust.
     */
    @Override
    public void randomTick(@NotNull BlockState state, ServerWorld world, BlockPos pos, @NotNull Random random) {
        //Consumes the dust.
        int level = getLevel(state);
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
    }

    /** Executed every tick randomly to handle effects. */
    @Override
    public void randomDisplayTick(@NotNull BlockState state, World world, BlockPos pos, Random random) {
        if (!state.get(CLOSED)) {
            //Particle to spawn.
            DefaultParticleType particle = getDustParticleType(state.get(DUST));

            if (particle != null) {
                BlockPos.Mutable randomPos = new BlockPos.Mutable();
                int level = getLevel(state);

                //Generates the dust from the block.
                for(int i = 0; i < DUST_PER_TICK; ++i) {
                    int time = MAX_LEVEL - level;
                    if (time == 0 || random.nextInt(time) == 0) {
                        //Spawns a particle in a random position in the block.
                        double x = pos.getX() + (0.4d + (random.nextDouble() * 0.4d));
                        double y = pos.getY() + (0.4d + (random.nextDouble() * 0.4d));
                        double z = pos.getZ() + (0.4d + (random.nextDouble() * 0.4d));
                        world.addParticle(particle, x, y, z, 0d, 0.3d, 0d);
                    }
                }

                //Generates the wandering air dust.
                for(int i = 0; i < level; ++i) {
                    //Select a random spawn position between a radius of 10.
                    int posX = pos.getX() + random.nextBetween(-DUST_SPREADING_RADIUS, DUST_SPREADING_RADIUS);
                    int posY = pos.getY() + random.nextInt(DUST_SPREADING_HEIGHT);
                    int posZ = pos.getZ() + random.nextBetween(-DUST_SPREADING_RADIUS, DUST_SPREADING_RADIUS);
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

    /** Gets the dust particle type of the corresponding dust. */
    private @Nullable DefaultParticleType getDustParticleType(@NotNull Dust dust) {
        return switch (dust) {
            case BurnedBirchMoldDust -> TerravibeParticleTypes.BIRCH_MOLD_SPORE;
            case BurnedDarkMoldDust -> TerravibeParticleTypes.DARK_MOLD_SPORE;
            case BurnedGlowingDarkMoldDust -> TerravibeParticleTypes.GLOWING_DARK_MOLD_SPORE;
            default -> null;
        };
    }

    /** Gets the luminance for the current block state. */
    private static int getLuminance(@NotNull BlockState state) {
        return state.get(DUST) == Dust.BurnedGlowingDarkMoldDust ? LUMINANCE_PER_LEVEL * state.get(LEVEL) : 0;
    }

    static {
        SETTINGS = FabricBlockSettings.copyOf(Blocks.FLOWER_POT)
                .luminance(DustJarBlock::getLuminance);
        DUST = EnumProperty.of("dust", Dust.class);
        ID = new Identifier(ModInfo.MOD_ID, "dust_jar");
    }


    /**
     * Represents the dust type contained in a {@link DustJarBlock}.
     */
    public enum Dust implements StringIdentifiable {
        /** The block contains unspecified dust. */
        Unspecified("unspecified"),

        /** The block contains burned birch mold dust. */
        BurnedBirchMoldDust("burned_birch_mold_dust"),

        /** The block contains burned dark mold dust. */
        BurnedDarkMoldDust("burned_dark_mold_dust"),

        /** The block contains burned glowing dark mold dust. */
        BurnedGlowingDarkMoldDust("burned_glowing_dark_mold_dust");

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
                case BurnedBirchMoldDust -> TerravibeItems.BURNED_BIRCH_MOLD_DUST;
                case BurnedDarkMoldDust -> TerravibeItems.BURNED_DARK_MOLD_DUST;
                case BurnedGlowingDarkMoldDust -> TerravibeItems.BURNED_GLOWING_DARK_MOLD_DUST;
                default -> Items.AIR;
            };
        }
    }
}
