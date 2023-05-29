package xyz.devpelux.terravibe.tags;

import net.fabricmc.fabric.impl.tag.convention.TagRegistration;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

/**
 * List of all the biome tags.
 */
public final class TerravibeBiomeTags {
	/**
	 * {@code c:warm_ocean}<p/>
	 * Identifies a warm ocean biome.
	 */
	public static final TagKey<Biome> WARM_OCEAN;

	private TerravibeBiomeTags() {
	}

	/**
	 * Loads all the tags.
	 */
	public static void load() {
	}

	static {
		WARM_OCEAN = TagRegistration.BIOME_TAG_REGISTRATION.registerCommon("warm_ocean");
	}
}
