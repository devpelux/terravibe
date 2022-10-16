package xyz.devpelux.terravibe.entity;

import net.minecraft.entity.damage.DamageSource;

/**
 * List of all the damage sources.
 */
public final class TerravibeDamageSources {
	/**
	 * Entity was killed by nightlock.
	 */
	public static final DamageSource NIGHTLOCK = new DamageSource("nightlock").setBypassesArmor();

	/**
	 * Entity was killed by nightlock in creative mode.
	 */
	public static final DamageSource NIGHTLOCK_CREATIVE = new DamageSource("nightlock_creative").setBypassesArmor().setOutOfWorld();

	private TerravibeDamageSources() {
	}
}