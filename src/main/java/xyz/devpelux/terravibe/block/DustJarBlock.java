package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.core.Util;
import xyz.devpelux.terravibe.item.TerravibeItems;
import xyz.devpelux.terravibe.particle.TerravibeParticleTypes;

/** A jar for dusts. */
public class DustJarBlock extends JarBlock {
    /** Identifier of the block. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "dust_jar");

    /** Settings of the block. */
    public static final Settings SETTINGS;

    /** Dust consuming time. */
    public static final int CONSUMING_TIME = 48;

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

    /** Initializes a new {@link DustJarBlock}. */
    public DustJarBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(LEVEL, 0).with(CLOSED, false).with(DUST, Dust.Unspecified));
    }

    /** Registers the properties of the block. */
    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(DUST);
    }

    /** Gets the default state with the specified dust. */
    public static BlockState withDust(Dust dust) {
        return TerravibeBlocks.DUST_JAR.getDefaultState().with(DUST, dust);
    }

    /**
     * Executed at the block breaking.
     * Drops the contained.
     */
    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        //Drops the contained in server world, if the player is not in creative mode.
        if (!world.isClient() && !player.getAbilities().creativeMode) {
            ItemStack contained = getContained(world, pos);
            if (!contained.isEmpty()) {
                dropStack(world, pos, Util.copyStack(contained, getLevel(world, pos)));
            }
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
        if (random.nextInt(CONSUMING_TIME) == 0) {
            int level = Math.max(getLevel(world, pos) - 1, 0);
            setLevel(world, pos, level);
            if (level == 0) {
                setContained(world, pos, ItemStack.EMPTY);
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

                //Generates the dust from the block.
                for(int i = 0; i < DUST_PER_TICK; ++i) {
                    int time = MAX_LEVEL - state.get(LEVEL);
                    if (time == 0 || random.nextInt(time) == 0) {
                        //Spawns a particle in a random position in the block.
                        double x = pos.getX() + (0.4d + (random.nextDouble() * 0.4d));
                        double y = pos.getY() + (0.4d + (random.nextDouble() * 0.4d));
                        double z = pos.getZ() + (0.4d + (random.nextDouble() * 0.4d));
                        world.addParticle(particle, x, y, z, 0d, 0.3d, 0d);
                    }
                }

                //Generates the wandering air dust.
                for(int i = 0; i < state.get(LEVEL); ++i) {
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
        switch (dust) {
            case BurnedBirchMoldDust -> {
                return TerravibeParticleTypes.BIRCH_MOLD_SPORE;
            }
            case BurnedDarkMoldDust -> {
                return TerravibeParticleTypes.DARK_MOLD_SPORE;
            }
            case BurnedGlowingDarkMoldDust -> {
                return TerravibeParticleTypes.GLOWING_DARK_MOLD_SPORE;
            }
            default -> {
                return null;
            }
        }
    }

    /** Gets the luminance for the current block state. */
    private static int getLuminance(@NotNull BlockState state) {
        return state.get(DUST) == Dust.BurnedGlowingDarkMoldDust ? LUMINANCE_PER_LEVEL * state.get(LEVEL) : 0;
    }

    static {
        SETTINGS = FabricBlockSettings.copyOf(TerravibeBlocks.JAR)
                .luminance(DustJarBlock::getLuminance);
        DUST = EnumProperty.of("dust", Dust.class);
    }


    /** Represents the dust type contained in a {@link DustJarBlock}. */
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

        /** Gets the dust type of the corresponding item. */
        public static Dust fromItem(Item item) {
            if (item == TerravibeItems.BURNED_BIRCH_MOLD_DUST) return BurnedBirchMoldDust;
            else if (item == TerravibeItems.BURNED_DARK_MOLD_DUST) return BurnedDarkMoldDust;
            else if (item == TerravibeItems.BURNED_GLOWING_DARK_MOLD_DUST) return BurnedGlowingDarkMoldDust;
            else return Unspecified;
        }
    }
}
