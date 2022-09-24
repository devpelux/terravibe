package xyz.devpelux.terravibe.particle;

import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

/** A glowing particle affected by wind. */
public abstract class GlowingDustParticle extends SpriteBillboardParticle {
    /** Blinking interval in ticks. */
    private int blinkInterval = 0;

    /** Min luminescence between 0 and 1. */
    private float minLuminescence = 0f;

    /** Max luminescence between 0 and 1. */
    private float maxLuminescence = 0f;

    /** Time passed from when the blinking has started. */
    private int blinkTicks = 0;

    /** Wind strength. */
    private double windStrength = 0d;

    /** Min wind duration in ticks. */
    private int minWindDuration = 0;

    /** Max wind duration in ticks. */
    private int maxWindDuration = 0;

    /** Time passed from when the wind has started. */
    private int windTicks = 0;

    /** Current wind duration in ticks. */
    private int windEndTicks = 0;

    /** Current wind strength. */
    private Vec3d wind = Vec3d.ZERO;

    /** Initializes a new {@link GlowingDustParticle} specifying the sprite and position. */
    @SuppressWarnings("unused")
    protected GlowingDustParticle(ClientWorld world, SpriteProvider sprite, double x, double y, double z) {
        super(world, x, y, z);
        setBoundingBoxSpacing(0.01f, 0.01f);
        setSprite(sprite);
        collidesWithWorld = false;
        velocityMultiplier = 1f;
        gravityStrength = 0.001f;
    }

    /** Initializes a new {@link GlowingDustParticle} specifying the sprite, position, and initial speed. */
    protected GlowingDustParticle(ClientWorld world, SpriteProvider sprite, double x, double y, double z, double vX, double vY, double vZ) {
        super(world, x, y, z, vX, vY, vZ);
        setBoundingBoxSpacing(0.01f, 0.01f);
        setSprite(sprite);
        collidesWithWorld = false;
        velocityMultiplier = 1f;
        gravityStrength = 0.001f;
    }

    /** Gets the scale of the particle. */
    public float getScale() {
        return scale;
    }

    /** Sets the scale of the particle. */
    public void setScale(float scale) {
        this.scale = scale;
    }

    /** Accelerates the particle. */
    public void accelerate(@NotNull Vec3d acceleration) {
        velocityX += acceleration.x;
        velocityY += acceleration.y;
        velocityZ += acceleration.z;
    }

    /** Gets the brightness of the particle. */
    @Override
    public int getBrightness(float tickDelta) {
        if (maxLuminescence > 0) {
            int brightness = super.getBrightness(tickDelta);
            int sky = brightness >> 16 & 255;
            int block = brightness & 255;

            block += (int)(getLuminescence(tickDelta) * 240f);
            if (block > 240) {
                block = 240;
            }

            return block | sky << 16;
        }
        return super.getBrightness(tickDelta);
    }

    /** Gets the luminescence of the particle between 0 and 1. */
    private float getLuminescence(float tickDelta) {
        if (blinkInterval > 0) {
            float time = Math.min(blinkTicks + tickDelta, blinkInterval);
            float maxTime = blinkInterval;
            //The time passed in a range between 0 and 2PI.
            float piTime = time / maxTime * MathHelper.PI * 2f;

            return MathHelper.map(MathHelper.sin(piTime), -1f, 1f, minLuminescence, maxLuminescence);
        }
        return maxLuminescence;
    }

    /** Executed every tick. */
    @Override
    public void tick() {
        //Updates the blink tick if there is a blinking interval.
        if (blinkInterval > 0) {
            blinkTicks++;
            if (blinkTicks >= blinkInterval) {
                blinkTicks = 0;
            }
        }

        //Updates the wind tick and applies the wins if there is a wind.
        if (maxWindDuration > 0) {
            windTicks++;
            if (windTicks >= windEndTicks) {
                windTicks = 0;
                windEndTicks = random.nextBetween(minWindDuration, maxWindDuration);
                double windX = Math.random() * windStrength * 2d - windStrength;
                double windY = Math.random() * windStrength * 2d - windStrength;
                double windZ = Math.random() * windStrength * 2d - windStrength;
                wind = new Vec3d(windX, windY, windZ);
            }
            accelerate(wind);
        }

        super.tick();
    }

    /** Sets the luminescence parameters. */
    public void setLuminescence(float minLuminescence, float maxLuminescence, int blinkInterval) {
        //The luminescence must be a value between 0 and 1.
        this.maxLuminescence = MathHelper.clamp(maxLuminescence, 0f, 1f);
        this.minLuminescence = MathHelper.clamp(minLuminescence, 0f, this.maxLuminescence);

        if (blinkInterval > 0 && this.minLuminescence != this.maxLuminescence) {
            //If there is a variation of luminescence then sets the interval.
            this.blinkInterval = blinkInterval;
        }
        else {
            //Otherwise sets the interval to 0 and resets the current blink tick.
            this.blinkInterval = 0;
            this.blinkTicks = 0;
        }
    }

    /** Sets the wind parameters. */
    public void setWind(int minDuration, int maxDuration, double strength) {
        if (strength > 0d && maxDuration > 0) {
            //Sets the parameters if the wind has a strength and duration.
            this.windStrength = strength;
            this.minWindDuration = MathHelper.clamp(minDuration, 0, maxDuration);
            this.maxWindDuration = maxDuration;
        }
        else {
            //Otherwise the wind is removed.
            this.windStrength = 0d;
            this.minWindDuration = 0;
            this.maxWindDuration = 0;
            this.windTicks = 0;
            this.windEndTicks = 0;
            this.wind = Vec3d.ZERO;
        }
    }
}
