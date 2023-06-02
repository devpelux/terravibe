package xyz.devpelux.terravibe.tags;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
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

	static {
		WARM_OCEAN = TagKey.of(RegistryKeys.BIOME, new Identifier("c", "warm_ocean"));
	}
}
