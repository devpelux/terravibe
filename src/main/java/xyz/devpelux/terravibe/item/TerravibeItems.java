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

    /** Tray item: Tray used to make salt. */
    public static final TrayItem TRAY;

    /** Tun item: Container for "non-lava" fluids. */
    public static final TunItem TUN;

    /** Flooded mud item: A mud block excavated and flooded with water. */
    public static final FloodedMudItem FLOODED_MUD;

    /** Beans item: Little edible seeds. */
    public static final BeansItem BEANS;

    /** Dark sweet berries item: Little berry with a purplish color, mutation of the sweet berries. */
    public static final DarkSweetBerriesItem DARK_SWEET_BERRIES;

    /** Eggplant item: Edible purple berry, spongy, absorbent, typically used as a vegetable in cooking. */
    public static final EggplantItem EGGPLANT;

    /** Eggplant seeds item: Seeds of eggplant. */
    public static final EggplantSeedsItem EGGPLANT_SEEDS;

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

    /** Rice item: Little white seed, one of the most consumed foods in the world. */
    public static final RiceItem RICE;

    /** Sweet potato item: Sweet potato, a mutation of the potato. */
    public static final SweetPotatoItem SWEET_POTATO;

    /** Sweet potato bud item: Buds of sweet potato. */
    public static final SweetPotatoBudsItem SWEET_POTATO_BUDS;

    /** Tomato item: Edible red berry, commonly used to make sauces or for salad. */
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
        Util.registerCompostable(BakedSweetPotatoItem.COMPOSTING_CHANCE, BAKED_SWEET_POTATO);
        Util.registerCompostable(BeansItem.COMPOSTING_CHANCE, BEANS);
        Util.registerCompostable(DarkSweetBerriesItem.COMPOSTING_CHANCE, DARK_SWEET_BERRIES);
        Util.registerCompostable(EggplantItem.COMPOSTING_CHANCE, EGGPLANT);
        Util.registerCompostable(EggplantSeedsItem.COMPOSTING_CHANCE, EGGPLANT_SEEDS);
        Util.registerCompostable(KaleItem.COMPOSTING_CHANCE, KALE);
        Util.registerCompostable(KaleSeedsItem.COMPOSTING_CHANCE, KALE_SEEDS);
        Util.registerCompostable(LettuceLeavesItem.COMPOSTING_CHANCE, LETTUCE_LEAVES);
        Util.registerCompostable(LettuceSeedsItem.COMPOSTING_CHANCE, LETTUCE_SEEDS);
        Util.registerCompostable(NightlockBerriesItem.COMPOSTING_CHANCE, NIGHTLOCK_BERRIES);
        Util.registerCompostable(OlivesItem.COMPOSTING_CHANCE, OLIVES);
        Util.registerCompostable(OnionItem.COMPOSTING_CHANCE, ONION);
        Util.registerCompostable(OnionSeedsItem.COMPOSTING_CHANCE, ONION_SEEDS);
        Util.registerCompostable(RedSweetPotatoItem.COMPOSTING_CHANCE, Items.POISONOUS_POTATO);
        Util.registerCompostable(RedSweetPotatoItem.COMPOSTING_CHANCE, RED_SWEET_POTATO);
        Util.registerCompostable(RiceItem.COMPOSTING_CHANCE, RICE);
        Util.registerCompostable(SweetPotatoItem.COMPOSTING_CHANCE, SWEET_POTATO);
        Util.registerCompostable(SweetPotatoBudsItem.COMPOSTING_CHANCE, SWEET_POTATO_BUDS);
        Util.registerCompostable(TomatoItem.COMPOSTING_CHANCE, TOMATO);
        Util.registerCompostable(TomatoSeedsItem.COMPOSTING_CHANCE, TOMATO_SEEDS);

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
        TRAY = register(TrayItem.ID, new TrayItem(TerravibeBlocks.TRAY, TrayItem.getSettings()));
        TUN = register(TunItem.ID, new TunItem(TerravibeBlocks.TUN, TunItem.getSettings()));

        FLOODED_MUD = register(FloodedMudItem.ID, new FloodedMudItem(TerravibeBlocks.FLOODED_MUD, FloodedMudItem.getSettings()));

        DARK_SWEET_BERRIES = register(DarkSweetBerriesItem.ID, new DarkSweetBerriesItem(TerravibeBlocks.DARK_SWEET_BERRY_BUSH, DarkSweetBerriesItem.getSettings()));
        NIGHTLOCK_BERRIES = register(NightlockBerriesItem.ID, new NightlockBerriesItem(TerravibeBlocks.NIGHTLOCK_BERRY_BUSH, NightlockBerriesItem.getSettings()));
        BEANS = register(BeansItem.ID, new BeansItem(TerravibeBlocks.BEANS_CROP, BeansItem.getSettings()));
        EGGPLANT = register(EggplantItem.ID, new EggplantItem(EggplantItem.getSettings()));
        EGGPLANT_SEEDS = register(EggplantSeedsItem.ID, new EggplantSeedsItem(TerravibeBlocks.EGGPLANT_CROP, EggplantSeedsItem.getSettings()));
        KALE = register(KaleItem.ID, new KaleItem(KaleItem.getSettings()));
        KALE_SEEDS = register(KaleSeedsItem.ID, new KaleSeedsItem(TerravibeBlocks.KALE_CROP, KaleSeedsItem.getSettings()));
        LETTUCE_LEAVES = register(LettuceLeavesItem.ID, new LettuceLeavesItem(LettuceLeavesItem.getSettings()));
        LETTUCE_SEEDS = register(LettuceSeedsItem.ID, new LettuceSeedsItem(TerravibeBlocks.LETTUCE_CROP, LettuceSeedsItem.getSettings()));
        ONION = register(OnionItem.ID, new OnionItem(OnionItem.getSettings()));
        ONION_SEEDS = register(OnionSeedsItem.ID, new OnionSeedsItem(TerravibeBlocks.ONION_CROP, OnionSeedsItem.getSettings()));
        RED_SWEET_POTATO = register(RedSweetPotatoItem.ID, new RedSweetPotatoItem(RedSweetPotatoItem.getSettings()));
        RICE = register(RiceItem.ID, new RiceItem(TerravibeBlocks.RICE_CROP, RiceItem.getSettings()));
        SWEET_POTATO_BUDS = register(SweetPotatoBudsItem.ID, new SweetPotatoBudsItem(TerravibeBlocks.SWEET_POTATO_CROP, SweetPotatoBudsItem.getSettings()));
        SWEET_POTATO = register(SweetPotatoItem.ID, new SweetPotatoItem(TerravibeBlocks.SWEET_POTATO_CROP, SweetPotatoItem.getSettings()));
        TOMATO = register(TomatoItem.ID, new TomatoItem(TomatoItem.getSettings()));
        TOMATO_SEEDS = register(TomatoSeedsItem.ID, new TomatoSeedsItem(TerravibeBlocks.TOMATO_CROP, TomatoSeedsItem.getSettings()));

        OLIVES = register(OlivesItem.ID, new OlivesItem(OlivesItem.getSettings()));

        SALT_CRYSTALS = register(SaltCrystalsItem.ID, new SaltCrystalsItem(SaltCrystalsItem.getSettings()));
        SALT = register(SaltItem.ID, new SaltItem(SaltItem.getSettings()));

        BAKED_SWEET_POTATO = register(BakedSweetPotatoItem.ID, new BakedSweetPotatoItem(BakedSweetPotatoItem.getSettings()));

        OIL_BOTTLE = register(OilBottleItem.ID, new OilBottleItem(OilBottleItem.getSettings()));
        TOMATO_SAUCE_BOTTLE = register(TomatoSauceBottleItem.ID, new TomatoSauceBottleItem(TomatoSauceBottleItem.getSettings()));
    }
}
