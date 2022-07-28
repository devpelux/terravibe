package xyz.devpelux.terravibe.tags;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/** List of all the item tags. */
public class TerravibeItemTags {
    private TerravibeItemTags() {}

    /**
     * {@code c:jar_plugs}<p/>
     * Identifies all the cork plugs.
     */
    public static final TagKey<Item> JAR_PLUGS;

    /** Loads all the tags. */
    public static void load() {}

    /** Registers a new tag with the specified identifier. */
    private static TagKey<Item> of(Identifier id) {
        return TagKey.of(Registry.ITEM_KEY, id);
    }

    static {
        JAR_PLUGS = of(new Identifier("c", "jar_plugs"));
    }
}
