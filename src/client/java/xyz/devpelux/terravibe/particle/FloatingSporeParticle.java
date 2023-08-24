package xyz.devpelux.terravibe.particle;

import net.minecraft.client.particle.ParticleGroup;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * A floating spore particle.
 */
public class FloatingSporeParticle extends GlowingDustParticle {
	/**
	 * Contains some common settings.
	 */
	private ParticleGroup group = null;

	/**
	 * Initializes a new instance.
	 */
	FloatingSporeParticle(ClientWorld world, SpriteProvider sprite, double x, double y, double z, double vX, double vY, double vZ) {
		super(world, sprite, x, y - 0.125d, z, vX, vY, vZ);
	}

	/**
	 * Gets the particle type.
	 */
	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
	}

	/**
	 * Gets the particle group.
	 */
	@Override
	public Optional<ParticleGroup> getGroup() {
		return Optional.ofNullable(group);
	}

	/**
	 * Sets the particle group.
	 */
	public void setGroup(@Nullable ParticleGroup group) {
		this.group = group;
	}
}
