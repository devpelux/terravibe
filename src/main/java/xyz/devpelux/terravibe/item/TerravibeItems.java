package xyz.devpelux.terravibe.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.devpelux.terravibe.block.TerravibeBlocks;
import xyz.devpelux.terravibe.block.TunBlock;
import xyz.devpelux.terravibe.core.Util;

/** List of all the items. */
public class TerravibeItems {
    /** Mortar item: Crushes an item to obtain another item. */
    public static final MortarItem MORTAR;

    /** Shredder item: Shreds a bunch of items to obtain something. */
    public static final ShredderItem SHREDDER;

    /** Tun item: Container for "non-lava" fluids. */
    public static final TunItem TUN;

    /** Tray item: Tray used to make salt. */
    public static final TrayItem TRAY;

    /** Blueberries item: Little berry with a blue-purplish color mostly used for jams. */
    public static final BlueBerriesItem BLUE_BERRIES;

    /** Kale item: Vegetable mostly used to make soups. */
    public static final KaleItem KALE;

    /** Kale seeds item: Seeds of kale. */
    public static final KaleSeedsItem KALE_SEEDS;

    /** Lettuce leaves item: Vegetable that is mainly used to make salad. */
    public static final LettuceLeavesItem LETTUCE_LEAVES;

    /** Lettuce seeds item: Seeds of lettuce. */
    public static final LettuceSeedsItem LETTUCE_SEEDS;

    /** Nightlock berries item: Little poisonous berry with a black color. */
    public static final NightlockBerriesItem NIGHTLOCK_BERRIES;

    /** Olives item: Typical fruit used to make oil. */
    public static final OlivesItem OLIVES;

    /** Onion item: Vegetable that is the most widely cultivated species of the genus Allium. */
    public static final OnionItem ONION;

    /** Onion seeds item: Seeds of onion. */
    public static final OnionSeedsItem ONION_SEEDS;

    /** Red sweet potato item: Red sweet potato, a rare mutation of the sweet potato. */
    public static final RedSweetPotatoItem RED_SWEET_POTATO;

    /** Sweet potato item: Sweet potato, a mutation of the potato. */
    public static final SweetPotatoItem SWEET_POTATO;

    /** Sweet potato bud item: Buds of sweet potato. */
    public static final SweetPotatoBudsItem SWEET_POTATO_BUDS;

    /** Tomato item: Edible berry of the plant Solanum lycopersicum, commonly known as the tomato plant. */
    public static final TomatoItem TOMATO;

    /** Tomato seeds item: Seeds of tomato. */
    public static final TomatoSeedsItem TOMATO_SEEDS;

    /** Baked sweet potato item: Sweet potato, but baked. */
    public static final BakedSweetPotatoItem BAKED_SWEET_POTATO;

    /** Oil bottle item: Bottle that contains oil. */
    public static final OilBottleItem OIL_BOTTLE;

    /** Salt item: Salt. */
    public static final SaltItem SALT;

    /** Salt crystals item: Salt crystals. */
    public static final SaltCrystalsItem SALT_CRYSTALS;

    /** Tomato sauce bottle item: Bottle that contains tomato sauce. */
    public static final TomatoSauceBottleItem TOMATO_SAUCE_BOTTLE;

    /** Loads all the items. */
    public static void load() {
        Util.registerCompostableItem(BakedSweetPotatoItem.COMPOSTING_CHANCE, BAKED_SWEET_POTATO);
        Util.registerCompostableItem(BlueBerriesItem.COMPOSTING_CHANCE, BLUE_BERRIES);
        Util.registerCompostableItem(KaleItem.COMPOSTING_CHANCE, KALE);
        Util.registerCompostableItem(KaleSeedsItem.COMPOSTING_CHANCE, KALE_SEEDS);
        Util.registerCompostableItem(LettuceLeavesItem.COMPOSTING_CHANCE, LETTUCE_LEAVES);
        Util.registerCompostableItem(LettuceSeedsItem.COMPOSTING_CHANCE, LETTUCE_SEEDS);
        Util.registerCompostableItem(NightlockBerriesItem.COMPOSTING_CHANCE, NIGHTLOCK_BERRIES);
        Util.registerCompostableItem(OlivesItem.COMPOSTING_CHANCE, OLIVES);
        Util.registerCompostableItem(OnionItem.COMPOSTING_CHANCE, ONION);
        Util.registerCompostableItem(OnionSeedsItem.COMPOSTING_CHANCE, ONION_SEEDS);
        Util.registerCompostableItem(RedSweetPotatoItem.COMPOSTING_CHANCE, Items.POISONOUS_POTATO);
        Util.registerCompostableItem(RedSweetPotatoItem.COMPOSTING_CHANCE, RED_SWEET_POTATO);
        Util.registerCompostableItem(SweetPotatoItem.COMPOSTING_CHANCE, SWEET_POTATO);
        Util.registerCompostableItem(SweetPotatoBudsItem.COMPOSTING_CHANCE, SWEET_POTATO_BUDS);
        Util.registerCompostableItem(TomatoItem.COMPOSTING_CHANCE, TOMATO);
        Util.registerCompostableItem(TomatoSeedsItem.COMPOSTING_CHANCE, TOMATO_SEEDS);

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
        MORTAR = register(MortarItem.ID, new MortarItem(TerravibeBlocks.MORTAR, MortarItem.getSettings()));
        SHREDDER = register(ShredderItem.ID, new ShredderItem(TerravibeBlocks.SHREDDER, ShredderItem.getSettings()));
        TUN = register(TunItem.ID, new TunItem(TerravibeBlocks.TUN, TunItem.getSettings()));
        TRAY = register(TrayItem.ID, new TrayItem(TerravibeBlocks.TRAY, TrayItem.getSettings()));

        BLUE_BERRIES = register(BlueBerriesItem.ID, new BlueBerriesItem(TerravibeBlocks.BLUE_BERRY_BUSH, BlueBerriesItem.getSettings()));
        KALE = register(KaleItem.ID, new KaleItem(KaleItem.getSettings()));
        KALE_SEEDS = register(KaleSeedsItem.ID, new KaleSeedsItem(TerravibeBlocks.KALE_CROP, KaleSeedsItem.getSettings()));
        LETTUCE_LEAVES = register(LettuceLeavesItem.ID, new LettuceLeavesItem(LettuceLeavesItem.getSettings()));
        LETTUCE_SEEDS = register(LettuceSeedsItem.ID, new LettuceSeedsItem(TerravibeBlocks.LETTUCE_CROP, LettuceSeedsItem.getSettings()));
        NIGHTLOCK_BERRIES = register(NightlockBerriesItem.ID, new NightlockBerriesItem(TerravibeBlocks.NIGHTLOCK_BERRY_BUSH, NightlockBerriesItem.getSettings()));
        OLIVES = register(OlivesItem.ID, new OlivesItem(OlivesItem.getSettings()));
        ONION = register(OnionItem.ID, new OnionItem(OnionItem.getSettings()));
        ONION_SEEDS = register(OnionSeedsItem.ID, new OnionSeedsItem(TerravibeBlocks.ONION_CROP, OnionSeedsItem.getSettings()));
        RED_SWEET_POTATO = register(RedSweetPotatoItem.ID, new RedSweetPotatoItem(RedSweetPotatoItem.getSettings()));
        SWEET_POTATO_BUDS = register(SweetPotatoBudsItem.ID, new SweetPotatoBudsItem(TerravibeBlocks.SWEET_POTATO_CROP, SweetPotatoBudsItem.getSettings()));
        SWEET_POTATO = register(SweetPotatoItem.ID, new SweetPotatoItem(TerravibeBlocks.SWEET_POTATO_CROP, SweetPotatoItem.getSettings()));
        TOMATO = register(TomatoItem.ID, new TomatoItem(TomatoItem.getSettings()));
        TOMATO_SEEDS = register(TomatoSeedsItem.ID, new TomatoSeedsItem(TerravibeBlocks.TOMATO_CROP, TomatoSeedsItem.getSettings()));
        BAKED_SWEET_POTATO = register(BakedSweetPotatoItem.ID, new BakedSweetPotatoItem(BakedSweetPotatoItem.getSettings()));
        SALT = register(SaltItem.ID, new SaltItem(SaltItem.getSettings()));
        SALT_CRYSTALS = register(SaltCrystalsItem.ID, new SaltCrystalsItem(SaltCrystalsItem.getSettings()));

        OIL_BOTTLE = register(OilBottleItem.ID, new OilBottleItem(OilBottleItem.getSettings()));
        TOMATO_SAUCE_BOTTLE = register(TomatoSauceBottleItem.ID, new TomatoSauceBottleItem(TomatoSauceBottleItem.getSettings()));
    }
}
