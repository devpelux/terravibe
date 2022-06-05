package xyz.devpelux.terravibe.item;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.devpelux.terravibe.block.BlockList;
import xyz.devpelux.terravibe.core.Util;

/** List of all the items. */
public class ItemList {
    /** Mortar item: Crushes an item to obtain another item. */
    public static final MortarItem MORTAR;

    /** Red sweet potato item: Red sweet potato, a rare mutation of the sweet potato. */
    public static final RedSweetPotatoItem RED_SWEET_POTATO;

    /** Sweet potato bud item: Bud of sweet potato. */
    public static final SweetPotatoBudItem SWEET_POTATO_BUD;

    /** Sweet potato item: Sweet potato, a mutation of the potato. */
    public static final SweetPotatoItem SWEET_POTATO;

    /** Loads all the items. */
    public static void load() {
        Util.registerCompostableItem(RedSweetPotatoItem.COMPOSTING_CHANCE, RED_SWEET_POTATO);
        Util.registerCompostableItem(SweetPotatoItem.COMPOSTING_CHANCE, SWEET_POTATO);
        Util.registerCompostableItem(SweetPotatoBudItem.COMPOSTING_CHANCE, SWEET_POTATO_BUD);
    }

    /** Registers the specified item with the specified id. */
    private static <T extends Item> T register(Identifier id, T item) {
        return Registry.register(Registry.ITEM, id, item);
    }

    static {
        MORTAR = register(MortarItem.ID, new MortarItem(BlockList.MORTAR, MortarItem.getSettings()));
        RED_SWEET_POTATO = register(RedSweetPotatoItem.ID, new RedSweetPotatoItem(BlockList.SWEET_POTATO_CROP, RedSweetPotatoItem.getSettings()));
        SWEET_POTATO_BUD = register(SweetPotatoBudItem.ID, new SweetPotatoBudItem(BlockList.SWEET_POTATO_CROP, SweetPotatoBudItem.getSettings()));
        SWEET_POTATO = register(SweetPotatoItem.ID, new SweetPotatoItem(BlockList.SWEET_POTATO_CROP, SweetPotatoItem.getSettings()));
    }
}
