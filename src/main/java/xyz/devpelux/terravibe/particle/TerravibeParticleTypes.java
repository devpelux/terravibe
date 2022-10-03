package xyz.devpelux.terravibe.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.devpelux.terravibe.core.Terravibe;

/**
 * List of all the particle types.
 */
public final class TerravibeParticleTypes {
	/**
	 * Birch mold spore particle.
	 */
	public static final DefaultParticleType BIRCH_MOLD_SPORE;

	/**
	 * Dark mold spore particle.
	 */
	public static final DefaultParticleType DARK_MOLD_SPORE;

	/**
	 * Glowing dark mold spore particle.
	 */
	public static final DefaultParticleType GLOWING_DARK_MOLD_SPORE;

	/**
	 * Thistle pollen particle.
	 */
	public static final DefaultParticleType THISTLE_POLLEN;

	private TerravibeParticleTypes() {
	}

	/**
	 * Loads all the tags.
	 */
	public static void load() {
	}

	/**
	 * Loads all the particle factories.
	 */
	@Environment(EnvType.CLIENT)
	public static void loadFactories() {
		ParticleFactoryRegistry.getInstance().register(BIRCH_MOLD_SPORE, FloatingSporeParticleFactories.BirchMoldSporeFactory::new);
		ParticleFactoryRegistry.getInstance().register(DARK_MOLD_SPORE, FloatingSporeParticleFactories.DarkMoldSporeFactory::new);
		ParticleFactoryRegistry.getInstance().register(GLOWING_DARK_MOLD_SPORE, FloatingSporeParticleFactories.GlowingDarkMoldSporeFactory::new);
		ParticleFactoryRegistry.getInstance().register(THISTLE_POLLEN, FloatingSporeParticleFactories.ThistlePollenFactory::new);
	}

	/**
	 * Registers a new particle type.
	 */
	private static DefaultParticleType register(String path, boolean alwaysShow) {
		return Registry.register(Registry.PARTICLE_TYPE, new Identifier(Terravibe.ID, path), FabricParticleTypes.simple(alwaysShow));
	}

	static {
		BIRCH_MOLD_SPORE = register("birch_mold_spore", false);
		DARK_MOLD_SPORE = register("dark_mold_spore", false);
		GLOWING_DARK_MOLD_SPORE = register("glowing_dark_mold_spore", false);
		THISTLE_POLLEN = register("thistle_pollen", false);
	}
}
