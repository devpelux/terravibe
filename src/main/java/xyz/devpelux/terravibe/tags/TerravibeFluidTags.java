package xyz.devpelux.terravibe.tags;

import net.minecraft.fluid.Fluid;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

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
		CLEANER = TagKey.of(RegistryKeys.FLUID, new Identifier("c", "cleaner"));
	}
}
