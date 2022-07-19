package xyz.devpelux.terravibe.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/** List of all the particle types. */
public class TerravibeParticleTypes {
    private TerravibeParticleTypes() {}

    /** Dark mold spore particle. */
    public static final DefaultParticleType DARK_MOLD_SPORE;

    /** Glowing dark mold spore particle. */
    public static final DefaultParticleType GLOWING_DARK_MOLD_SPORE;

    /** Loads all the tags. */
    public static void load() {}

    /** Loads all the particle factories. */
    @Environment(EnvType.CLIENT)
    public static void loadFactories() {
        ParticleFactoryRegistry.getInstance().register(DARK_MOLD_SPORE, FloatingSporeParticle.DarkMoldSporeFactory::new);
        ParticleFactoryRegistry.getInstance().register(GLOWING_DARK_MOLD_SPORE, FloatingSporeParticle.GlowingDarkMoldSporeFactory::new);
    }

    /** Registers a new particle type. */
    private static DefaultParticleType register(Identifier id, boolean alwaysShow) {
        return Registry.register(Registry.PARTICLE_TYPE, id, FabricParticleTypes.simple(alwaysShow));
    }

    static {
        DARK_MOLD_SPORE = register(FloatingSporeParticle.DarkMoldSporeFactory.ID, false);
        GLOWING_DARK_MOLD_SPORE = register(FloatingSporeParticle.GlowingDarkMoldSporeFactory.ID, false);
    }
}
