package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.util.registry.Registry;
import xyz.devpelux.terravibe.block.BlockList;

/** List of all the items. */
public class ItemList {
    /** Grinder item: Grinds an item to obtain another item. */
    public static final GrinderItem GRINDER =
            Registry.register(Registry.ITEM, GrinderItem.ID,
                    new GrinderItem(BlockList.GRINDER, new FabricItemSettings().maxCount(64).group(ItemGroupList.TERRAVIBE)));

    /** Loads all the items. */
    public static void load() {}
}
