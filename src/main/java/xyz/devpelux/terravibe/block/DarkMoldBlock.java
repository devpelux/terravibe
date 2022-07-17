package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.particle.TerravibeParticleTypes;

/** Dark mold that can always spread. */
public class DarkMoldBlock extends MoldBlock {
    /** Identifier of the block. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "dark_mold");

    /** Settings of the block. */
    public static final Settings SETTINGS;

    /** Initializes a new {@link DarkMoldBlock}. */
    public DarkMoldBlock(Settings settings) {
        super(settings);
    }

    /** Gets the age property. */
    @Override
    public @Nullable IntProperty getAgeProperty() {
        return Properties.AGE_1;
    }

    /** Gets the max age. */
    @Override
    public int getMaxAge() {
        return Properties.AGE_1_MAX;
    }

    /** Gets the time to grow. */
    @Override
    public int getGrowingTime() {
        return 20;
    }

    /** Gets the required light to grow. */
    @Override
    public int getMinLightToGrow() {
        return 2;
    }

    /** Gets the max light to grow. */
    @Override
    public int getMaxLightToGrow() {
        return 11;
    }

    /** Gets the maximum light to plant the mold. */
    @Override
    public int getMaxLightToPlant() {
        return 12;
    }

    /** Gets a value indicating if the mold can randomly spread into the world. */
    @Override
    public boolean canSpread(BlockState state, World world, BlockPos pos, Random random) {
        return true;
    }

    /** Gets the time to spread the mold. */
    @Override
    public int getSpreadingTime() {
        return 20;
    }

    /** Gets the number of spores to spread from the block every tick. */
    @Override
    public int getSporesPerTick() {
        return 1;
    }

    /** Gets the number of spores to spread in the air per tick. */
    @Override
    public int getWanderingSporesPerTick() {
        return 4;
    }

    /** Gets the spore particle type. */
    @Override
    public @NotNull DefaultParticleType getSporeParticleType() {
        return TerravibeParticleTypes.DARK_MOLD_SPORE;
    }

    static {
        SETTINGS = FabricBlockSettings.copyOf(Blocks.GRASS);
    }
}
