package xyz.devpelux.terravibe.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.devpelux.terravibe.block.BlockList;
import xyz.devpelux.terravibe.core.Util;

/** List of all the items. */
public class ItemList {
    /** Mortar item: Crushes an item to obtain another item. */
    public static final MortarItem MORTAR;

    /** Shredder item: Shreds a bunch of items to obtain something. */
    public static final ShredderItem SHREDDER;

    /** Red sweet potato item: Red sweet potato, a rare mutation of the sweet potato. */
    public static final RedSweetPotatoItem RED_SWEET_POTATO;

    /** Sweet potato bud item: Bud of sweet potato. */
    public static final SweetPotatoBudItem SWEET_POTATO_BUD;

    /** Sweet potato item: Sweet potato, a mutation of the potato. */
    public static final SweetPotatoItem SWEET_POTATO;

    /** Olives item: Typical fruit used to make oil. */
    public static final OlivesItem OLIVES;

    /** Oil bottle item: Bottle that contains oil. */
    public static final OilBottleItem OIL_BOTTLE;

    /** Loads all the items. */
    public static void load() {
        Util.registerCompostableItem(RedSweetPotatoItem.COMPOSTING_CHANCE, RED_SWEET_POTATO);
        Util.registerCompostableItem(SweetPotatoItem.COMPOSTING_CHANCE, SWEET_POTATO);
        Util.registerCompostableItem(SweetPotatoBudItem.COMPOSTING_CHANCE, SWEET_POTATO_BUD);
        Util.registerCompostableItem(OlivesItem.COMPOSTING_CHANCE, OLIVES);
    }

    /** Loads all the color providers for the items. */
    @Environment(EnvType.CLIENT)
    public static void loadColorProviders() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 1 ? 0x808000 : -1, OIL_BOTTLE);
    }

    /** Registers the specified item with the specified id. */
    private static <T extends Item> T register(Identifier id, T item) {
        return Registry.register(Registry.ITEM, id, item);
    }

    static {
        MORTAR = register(MortarItem.ID, new MortarItem(BlockList.MORTAR, MortarItem.getSettings()));
        SHREDDER = register(ShredderItem.ID, new ShredderItem(BlockList.SHREDDER, ShredderItem.getSettings()));
        RED_SWEET_POTATO = register(RedSweetPotatoItem.ID, new RedSweetPotatoItem(BlockList.SWEET_POTATO_CROP, RedSweetPotatoItem.getSettings()));
        SWEET_POTATO_BUD = register(SweetPotatoBudItem.ID, new SweetPotatoBudItem(BlockList.SWEET_POTATO_CROP, SweetPotatoBudItem.getSettings()));
        SWEET_POTATO = register(SweetPotatoItem.ID, new SweetPotatoItem(BlockList.SWEET_POTATO_CROP, SweetPotatoItem.getSettings()));
        OLIVES = register(OlivesItem.ID, new OlivesItem(OlivesItem.getSettings()));
        OIL_BOTTLE = register(OilBottleItem.ID, new OilBottleItem(OilBottleItem.getSettings()));
    }
}
