package xyz.devpelux.terravibe.block;

import net.minecraft.block.BlockState;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.particle.TerravibeParticleTypes;

/**
 * Dark mold that can always spread.
 */
public class DarkMoldBlock extends MoldBlock {
	/**
	 * Initializes a new instance.
	 */
	public DarkMoldBlock(Settings settings) {
		super(settings);
	}

	/**
	 * Gets the age property.
	 */
	@Nullable
	@Override
	public IntProperty getAgeProperty() {
		return Properties.AGE_1;
	}

	/**
	 * Gets the max age.
	 */
	@Override
	public int getMaxAge() {
		return Properties.AGE_1_MAX;
	}

	/**
	 * Gets the min light to grow.
	 */
	@Override
	public int getMinLightToGrow() {
		return 1;
	}

	/**
	 * Gets the min light to plant.
	 */
	@Override
	public int getMinLightToPlant() {
		return 1;
	}

	/**
	 * Gets the max light to grow.
	 */
	@Override
	public int getMaxLightToGrow() {
		return 7;
	}

	/**
	 * Gets the maximum light to plant the mold.
	 */
	@Override
	public int getMaxLightToPlant() {
		return 8;
	}

	/**
	 * Gets the time to grow.
	 */
	@Override
	public int getGrowingTime() {
		return 32;
	}

	/**
	 * Gets the time to spread the mold.
	 */
	@Override
	public int getSpreadingTime() {
		return 48;
	}

	/**
	 * Gets the time to spread the mold in the neighbor blocks.
	 */
	@Override
	public int getNeighborSpreadingTime() {
		return 32;
	}

	/**
	 * Gets the number of spores to spread from the block every tick.
	 */
	@Override
	public int getSporesPerTick(Random random) {
		return 1;
	}

	/**
	 * Gets the number of spores to spread in the air per tick.
	 */
	@Override
	public int getWanderingSporesPerTick(Random random) {
		return 2;
	}

	/**
	 * Gets the spore particle type.
	 */
	@Override
	public DefaultParticleType getSporeParticleType() {
		return TerravibeParticleTypes.DARK_MOLD_SPORE;
	}

	/**
	 * Gets a value indicating if the mold can randomly spread into the world.
	 */
	@Override
	protected boolean canSpread(BlockState state, World world, BlockPos pos, Random random) {
		return isFullyGrown(state);
	}
}
