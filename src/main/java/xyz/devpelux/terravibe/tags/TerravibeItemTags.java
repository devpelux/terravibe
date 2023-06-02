package xyz.devpelux.terravibe.tags;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

/**
 * List of all the item tags.
 */
public final class TerravibeItemTags {
	/**
	 * {@code c:edible_molds}<p/>
	 * Identifies all the edible molds.
	 */
	public static final TagKey<Item> EDIBLE_MOLDS;

	/**
	 * {@code c:jar_plugs}<p/>
	 * Identifies all the cork plugs.
	 */
	public static final TagKey<Item> JAR_PLUGS;

	/**
	 * {@code c:milk_coagulants}<p/>
	 * Identifies all the milk coagulants.
	 */
	public static final TagKey<Item> MILK_COAGULANTS;

	/**
	 * {@code c:molds}<p/>
	 * Identifies all the molds.
	 */
	public static final TagKey<Item> MOLDS;

	static {
		EDIBLE_MOLDS = TagKey.of(RegistryKeys.ITEM, new Identifier("c", "edible_molds"));
		JAR_PLUGS = TagKey.of(RegistryKeys.ITEM, new Identifier("c", "jar_plugs"));
		MILK_COAGULANTS = TagKey.of(RegistryKeys.ITEM, new Identifier("c", "milk_coagulants"));
		MOLDS = TagKey.of(RegistryKeys.ITEM, new Identifier("c", "molds"));
	}
}
