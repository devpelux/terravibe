package xyz.devpelux.terravibe.tags;

import net.fabricmc.fabric.impl.tag.convention.TagRegistration;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.TagKey;

/**
 * List of all the fluid tags.
 */
public final class TerravibeFluidTags {
	/**
	 * {@code c:cleaner}<p/>
	 * Identifies a fluid that can clean dirty items.
	 */
	public static final TagKey<Fluid> CLEANER;

	static {
		CLEANER = TagRegistration.FLUID_TAG_REGISTRATION.registerCommon("cleaner");
	}
}
