package xyz.devpelux.terravibe.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

import static xyz.devpelux.terravibe.particle.TerravibeParticleTypes.*;

/**
 * Particle configurations.
 */
public final class TerravibeParticleConfigsClient {
	/**
	 * Registers all the particle factories to the corresponding particle types.
	 */
	@Environment(EnvType.CLIENT)
	public static void registerParticleFactories() {
		ParticleFactoryRegistry.getInstance().register(BIRCH_MOLD_SPORE, FloatingSporeParticleFactories.BirchMoldSporeFactory::new);
		ParticleFactoryRegistry.getInstance().register(DARK_MOLD_SPORE, FloatingSporeParticleFactories.DarkMoldSporeFactory::new);
		ParticleFactoryRegistry.getInstance().register(GLOWING_DARK_MOLD_SPORE, FloatingSporeParticleFactories.GlowingDarkMoldSporeFactory::new);
	}
}
