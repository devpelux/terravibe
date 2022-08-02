package xyz.devpelux.terravibe.tags;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;

import net.fabricmc.fabric.impl.tag.convention.TagRegistration;

/** List of all the item tags. */
public class TerravibeItemTags {
    private TerravibeItemTags() {}

    /**
     * {@code c:jar_plugs}<p/>
     * Identifies all the cork plugs.
     */
    public static final TagKey<Item> JAR_PLUGS;

    /**
     * {@code c:molds}<p/>
     * Identifies all the molds.
     */
    public static final TagKey<Item> MOLDS;

    /** Loads all the tags. */
    public static void load() {}

    static {
        JAR_PLUGS = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("jar_plugs");
        MOLDS = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("molds");
    }
}
