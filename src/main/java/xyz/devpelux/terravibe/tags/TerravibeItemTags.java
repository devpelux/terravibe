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
		MILK_COAGULANTS = TagKey.of(RegistryKeys.ITEM, new Identifier("c", "milk_coagulants"));
		MOLDS = TagKey.of(RegistryKeys.ITEM, new Identifier("c", "molds"));
	}
}
