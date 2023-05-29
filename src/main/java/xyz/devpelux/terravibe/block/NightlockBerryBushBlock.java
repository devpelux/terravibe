package xyz.devpelux.terravibe.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import xyz.devpelux.terravibe.item.TerravibeItems;

import java.util.Optional;

/**
 * Bush of nightlock berries, without thorns.
 */
public class NightlockBerryBushBlock extends BerryBushBlock {
	/**
	 * Movement slow amount.
	 */
	public static final Vec3d MOVEMENT_SLOWING_AMOUNT = new Vec3d(0.8, 0.75, 0.8);

	/**
	 * Initializes a new {@link NightlockBerryBushBlock}.
	 */
	public NightlockBerryBushBlock(Settings settings) {
		super(settings);
	}

	/**
	 * Gets the age property.
	 */
	@Override
	public IntProperty getAgeProperty() {
		return Properties.AGE_3;
	}

	/**
	 * Gets the max age.
	 */
	@Override
	public int getMaxAge() {
		return Properties.AGE_3_MAX;
	}

	/**
	 * Gets the age when the bush has fruits.
	 */
	@Override
	public int getMatureAge() {
		return 2;
	}

	/**
	 * Gets the time to grow.
	 */
	@Override
	public int getGrowingTime() {
		return 10;
	}

	/**
	 * Gets the required light to grow.
	 */
	@Override
	public int getMinLightToGrow() {
		return 1;
	}

	/**
	 * Gets the pick sound.
	 */
	@Override
	public Optional<SoundEvent> getPickSound() {
		return Optional.of(SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES);
	}

	/**
	 * Gets the fruit item to pick from the plant.
	 */
	@Override
	public ItemConvertible getFruitItem() {
		return TerravibeItems.NIGHTLOCK_BERRIES;
	}

	/**
	 * Gets the amount of the fruit item to pick from the plant.
	 */
	@Override
	protected int getFruitItemAmount(Random random, BlockState state) {
		int randomAmount = random.nextBetween(1, 2);
		int bonus = isFullyGrown(state) ? 1 : 0;

		return randomAmount + bonus;
	}

	/**
	 * Gets the thorns damage caused by the bush. (0 = no thorns)
	 */
	@Override
	protected float getThornsDamage(BlockState state, World world, BlockPos pos, Entity entity, Vec3d entityMovement) {
		return 0F;
	}

	/**
	 * Gets the movement slowing amount when inside the bush.
	 */
	@Override
	protected Vec3d getSlowingAmount(BlockState state, World world, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE) {
			return MOVEMENT_SLOWING_AMOUNT;
		}
		return Vec3d.ZERO;
	}
}
