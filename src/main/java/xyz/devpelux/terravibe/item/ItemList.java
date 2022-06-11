package xyz.devpelux.terravibe.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.devpelux.terravibe.block.BlockList;
import xyz.devpelux.terravibe.block.TunBlock;
import xyz.devpelux.terravibe.core.Util;

/** List of all the items. */
public class ItemList {
    /** Mortar item: Crushes an item to obtain another item. */
    public static final MortarItem MORTAR;

    /** Shredder item: Shreds a bunch of items to obtain something. */
    public static final ShredderItem SHREDDER;

    /** Tun item: Container for "non-lava" fluids. */
    public static final TunItem TUN;

    /** Olives item: Typical fruit used to make oil. */
    public static final OlivesItem OLIVES;

    /** Onion item: Vegetable that is the most widely cultivated species of the genus Allium. */
    public static final OnionItem ONION;

    /** Onion seeds item: Seeds of onion. */
    public static final OnionSeeds ONION_SEEDS;

    /** Red sweet potato item: Red sweet potato, a rare mutation of the sweet potato. */
    public static final RedSweetPotatoItem RED_SWEET_POTATO;

    /** Sweet potato item: Sweet potato, a mutation of the potato. */
    public static final SweetPotatoItem SWEET_POTATO;

    /** Sweet potato bud item: Bud of sweet potato. */
    public static final SweetPotatoBudItem SWEET_POTATO_BUD;

    /** Tomato item: Edible berry of the plant Solanum lycopersicum, commonly known as the tomato plant. */
    public static final TomatoItem TOMATO;

    /** Tomato seeds item: Seeds of tomato. */
    public static final TomatoSeeds TOMATO_SEEDS;

    /** Baked sweet potato item: Sweet potato, but baked. */
    public static final BakedSweetPotatoItem BAKED_SWEET_POTATO;

    /** Oil bottle item: Bottle that contains oil. */
    public static final OilBottleItem OIL_BOTTLE;

    /** Tomato sauce bottle item: Bottle that contains tomato sauce. */
    public static final TomatoSauceBottleItem TOMATO_SAUCE_BOTTLE;

    /** Loads all the items. */
    public static void load() {
        Util.registerCompostableItem(BakedSweetPotatoItem.COMPOSTING_CHANCE, BAKED_SWEET_POTATO);
        Util.registerCompostableItem(OlivesItem.COMPOSTING_CHANCE, OLIVES);
        Util.registerCompostableItem(OnionItem.COMPOSTING_CHANCE, ONION);
        Util.registerCompostableItem(OnionSeeds.COMPOSTING_CHANCE, ONION_SEEDS);
        Util.registerCompostableItem(RedSweetPotatoItem.COMPOSTING_CHANCE, RED_SWEET_POTATO);
        Util.registerCompostableItem(SweetPotatoItem.COMPOSTING_CHANCE, SWEET_POTATO);
        Util.registerCompostableItem(SweetPotatoBudItem.COMPOSTING_CHANCE, SWEET_POTATO_BUD);
        Util.registerCompostableItem(TomatoItem.COMPOSTING_CHANCE, TOMATO);
        Util.registerCompostableItem(TomatoSeeds.COMPOSTING_CHANCE, TOMATO_SEEDS);

        TunBlock.registerContainable(Items.HONEY_BOTTLE, (s, w, p, i) -> 0x976018);
        TunBlock.registerContainable(OIL_BOTTLE, OilBottleItem::getFluidColorForTun);
        TunBlock.registerContainable(TOMATO_SAUCE_BOTTLE, TomatoSauceBottleItem::getFluidColorForTun);
    }

    /** Loads all the color providers for the items. */
    @Environment(EnvType.CLIENT)
    public static void loadColorProviders() {
        ColorProviderRegistry.ITEM.register(OilBottleItem::getOverlayColor, OIL_BOTTLE);
        ColorProviderRegistry.ITEM.register(TomatoSauceBottleItem::getOverlayColor, TOMATO_SAUCE_BOTTLE);
    }

    /** Registers the specified item with the specified id. */
    private static <T extends Item> T register(Identifier id, T item) {
        return Registry.register(Registry.ITEM, id, item);
    }

    static {
        MORTAR = register(MortarItem.ID, new MortarItem(BlockList.MORTAR, MortarItem.getSettings()));
        SHREDDER = register(ShredderItem.ID, new ShredderItem(BlockList.SHREDDER, ShredderItem.getSettings()));
        TUN = register(TunItem.ID, new TunItem(BlockList.TUN, TunItem.getSettings()));
        OLIVES = register(OlivesItem.ID, new OlivesItem(OlivesItem.getSettings()));
        ONION = register(OnionItem.ID, new OnionItem(OnionItem.getSettings()));
        ONION_SEEDS = register(OnionSeeds.ID, new OnionSeeds(BlockList.ONION_CROP, OnionSeeds.getSettings()));
        RED_SWEET_POTATO = register(RedSweetPotatoItem.ID, new RedSweetPotatoItem(BlockList.SWEET_POTATO_CROP, RedSweetPotatoItem.getSettings()));
        SWEET_POTATO_BUD = register(SweetPotatoBudItem.ID, new SweetPotatoBudItem(BlockList.SWEET_POTATO_CROP, SweetPotatoBudItem.getSettings()));
        SWEET_POTATO = register(SweetPotatoItem.ID, new SweetPotatoItem(BlockList.SWEET_POTATO_CROP, SweetPotatoItem.getSettings()));
        TOMATO = register(TomatoItem.ID, new TomatoItem(TomatoItem.getSettings()));
        TOMATO_SEEDS = register(TomatoSeeds.ID, new TomatoSeeds(BlockList.TOMATO_CROP, TomatoSeeds.getSettings()));
        BAKED_SWEET_POTATO = register(BakedSweetPotatoItem.ID, new BakedSweetPotatoItem(BakedSweetPotatoItem.getSettings()));
        OIL_BOTTLE = register(OilBottleItem.ID, new OilBottleItem(OilBottleItem.getSettings()));
        TOMATO_SAUCE_BOTTLE = register(TomatoSauceBottleItem.ID, new TomatoSauceBottleItem(TomatoSauceBottleItem.getSettings()));
    }
}
