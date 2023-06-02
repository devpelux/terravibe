package xyz.devpelux.terravibe.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleGroup;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

/**
 * Factories of {@link FloatingSporeParticle} particles.
 */
public final class FloatingSporeParticleFactories {
	private FloatingSporeParticleFactories() { }

	/**
	 * Factory for the birch mold spore particle.
	 */
	@Environment(EnvType.CLIENT)
	public static class BirchMoldSporeFactory implements ParticleFactory<DefaultParticleType> {
		/**
		 * Contains some common settings.
		 */
		private static final ParticleGroup GROUP = new ParticleGroup(1000);

		/**
		 * Sprite provider.
		 */
		private final SpriteProvider spriteProvider;

		/**
		 * Initializes a new {@link DarkMoldSporeFactory}.
		 */
		public BirchMoldSporeFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		/**
		 * Creates the particle.
		 */
		@Override
		public Particle createParticle(DefaultParticleType particle, ClientWorld world, double x, double y, double z, double vX, double vY, double vZ) {
			FloatingSporeParticle spore = new FloatingSporeParticle(world, this.spriteProvider, x, y, z, vX + 0d, vY - 0.8d, vZ + 0d);
			spore.setGroup(GROUP);
			spore.setMaxAge(world.random.nextBetween(600, 1200));
			spore.setScale(spore.getScale() * (world.random.nextFloat() * 0.4f + 0.4f)); //0.4 <-> 0.8
			spore.setWind(5, 20, 0.001d);
			spore.setColor(0.82f, 0.82f, 0.78f); //0xd1d1c7
			spore.setLuminescence(0.1f, 0.1f, 0);
			return spore;
		}
	}

	/**
	 * Factory for the dark mold spore particle.
	 */
	@Environment(EnvType.CLIENT)
	public static class DarkMoldSporeFactory implements ParticleFactory<DefaultParticleType> {
		/**
		 * Contains some common settings.
		 */
		private static final ParticleGroup GROUP = new ParticleGroup(1000);

		/**
		 * Sprite provider.
		 */
		private final SpriteProvider spriteProvider;

		/**
		 * Initializes a new {@link DarkMoldSporeFactory}.
		 */
		public DarkMoldSporeFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		/**
		 * Creates the particle.
		 */
		@Override
		public Particle createParticle(DefaultParticleType particle, ClientWorld world, double x, double y, double z, double vX, double vY, double vZ) {
			FloatingSporeParticle spore = new FloatingSporeParticle(world, this.spriteProvider, x, y, z, vX + 0d, vY - 0.8d, vZ + 0d);
			spore.setGroup(GROUP);
			spore.setMaxAge(world.random.nextBetween(400, 800));
			spore.setScale(spore.getScale() * (world.random.nextFloat() * 0.4f + 0.4f)); //0.4 <-> 0.8
			spore.setWind(5, 20, 0.001d);
			spore.setColor(0.094f, 0.129f, 0.106f); //0x18211b
			return spore;
		}
	}

	/**
	 * Factory for the glowing dark mold spore particle.
	 */
	@Environment(EnvType.CLIENT)
	public static class GlowingDarkMoldSporeFactory implements ParticleFactory<DefaultParticleType> {
		/**
		 * Contains some common settings.
		 */
		private static final ParticleGroup GROUP = new ParticleGroup(800);

		/**
		 * Sprite provider.
		 */
		private final SpriteProvider spriteProvider;

		/**
		 * Initializes a new {@link DarkMoldSporeFactory}.
		 */
		public GlowingDarkMoldSporeFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		/**
		 * Creates the particle.
		 */
		@Override
		public Particle createParticle(DefaultParticleType particle, ClientWorld world, double x, double y, double z, double vX, double vY, double vZ) {
			FloatingSporeParticle spore = new FloatingSporeParticle(world, this.spriteProvider, x, y, z, vX + 0d, vY - 0.8d, vZ + 0d);
			spore.setGroup(GROUP);
			spore.setMaxAge(world.random.nextBetween(600, 1200));
			spore.setScale(spore.getScale() * (world.random.nextFloat() * 0.4f + 0.4f)); //0.4 <-> 0.8
			spore.setWind(5, 10, 0.001d);
			spore.setColor(0f, 0.573f, 0.584f); //0x009295
			spore.setLuminescence(0.6f, 0.8f, 200);
			return spore;
		}
	}
}
