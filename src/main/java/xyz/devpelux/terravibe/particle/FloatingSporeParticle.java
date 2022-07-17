package xyz.devpelux.terravibe.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleGroup;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import xyz.devpelux.terravibe.core.ModInfo;

import java.util.Optional;

/** A floating spore particle. */
public class FloatingSporeParticle extends GlowingDustParticle {
    /** Contains some common settings. */
    private static final ParticleGroup FLOATING_SPORE = new ParticleGroup(1000);

    /** Initializes a new {@link FloatingSporeParticle}. */
    FloatingSporeParticle(ClientWorld world, SpriteProvider sprite, double x, double y, double z, double vX, double vY, double vZ) {
        super(world, sprite, x, y - 0.125d, z, vX, vY, vZ);
    }

    /** Gets the particle type. */
    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    /** Gets the particle group. */
    @Override
    public Optional<ParticleGroup> getGroup() {
        return Optional.of(FLOATING_SPORE);
    }

    /** Factory for the mold spore particle. */
    @Environment(EnvType.CLIENT)
    public static class DarkMoldSporeFactory implements ParticleFactory<DefaultParticleType> {
        /** Identifier of the particle. */
        public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "dark_mold_spore");

        /** Sprite provider. */
        private final SpriteProvider spriteProvider;

        /** Initializes a new {@link DarkMoldSporeFactory}. */
        public DarkMoldSporeFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        /** Creates the particle. */
        @Override
        public Particle createParticle(DefaultParticleType particle, ClientWorld world, double x, double y, double z, double vX, double vY, double vZ) {
            FloatingSporeParticle spore = new FloatingSporeParticle(world, this.spriteProvider, x, y, z, vX + 0d, vY -0.8d, vZ + 0d);
            spore.maxAge = world.random.nextBetween(400, 800);
            spore.scale *= world.random.nextFloat() * 0.4f + 0.4f; //0.4 <-> 0.8
            spore.setWind(4, 40, 0.001d);
            spore.setColor(0.094f, 0.129f, 0.106f); //0x18211b
            return spore;
        }
    }
}
