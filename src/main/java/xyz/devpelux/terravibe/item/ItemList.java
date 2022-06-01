package xyz.devpelux.terravibe.item;

import net.minecraft.util.registry.Registry;
import xyz.devpelux.terravibe.block.BlockList;
import xyz.devpelux.terravibe.core.Util;

/** List of all the items. */
public class ItemList {
    /** Grinder item: Grinds an item to obtain another item. */
    public static final GrinderItem GRINDER =
            Registry.register(Registry.ITEM, GrinderItem.ID, new GrinderItem(BlockList.GRINDER, GrinderItem.getSettings()));

    /** Red sweet potato item: Red sweet potato, a rare mutation of the sweet potato. */
    public static final RedSweetPotatoItem RED_SWEET_POTATO =
            Registry.register(Registry.ITEM, RedSweetPotatoItem.ID, new RedSweetPotatoItem(BlockList.SWEET_POTATO_CROP, RedSweetPotatoItem.getSettings()));

    /** Sweet potato bud item: Bud of sweet potato. */
    public static final SweetPotatoBudItem SWEET_POTATO_BUD =
            Registry.register(Registry.ITEM, SweetPotatoBudItem.ID, new SweetPotatoBudItem(BlockList.SWEET_POTATO_CROP, SweetPotatoBudItem.getSettings()));

    /** Sweet potato item: Sweet potato, a mutation of the potato. */
    public static final SweetPotatoItem SWEET_POTATO =
            Registry.register(Registry.ITEM, SweetPotatoItem.ID, new SweetPotatoItem(BlockList.SWEET_POTATO_CROP, SweetPotatoItem.getSettings()));

    /** Loads all the items. */
    public static void load() {
        Util.registerCompostableItem(RedSweetPotatoItem.COMPOSTING_CHANCE, RED_SWEET_POTATO);
        Util.registerCompostableItem(SweetPotatoItem.COMPOSTING_CHANCE, SWEET_POTATO);
        Util.registerCompostableItem(SweetPotatoBudItem.COMPOSTING_CHANCE, SWEET_POTATO_BUD);
    }
}
