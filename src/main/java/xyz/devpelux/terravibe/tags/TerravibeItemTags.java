package xyz.devpelux.terravibe.tags;

import net.fabricmc.fabric.impl.tag.convention.TagRegistration;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;

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
		EDIBLE_MOLDS = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("edible_molds");
		JAR_PLUGS = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("jar_plugs");
		MILK_COAGULANTS = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("milk_coagulants");
		MOLDS = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("molds");
	}
}
