package xyz.devpelux.terravibe.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.devpelux.terravibe.block.TerravibeBlocks;
import xyz.devpelux.terravibe.block.TunBlock;

/** List of all the items. */
public class TerravibeItems {
    private TerravibeItems() {}

    //Objects

    /** Item of the mortar block. */
    public static final MortarItem MORTAR;

    /** Item of the shredder block. */
    public static final ShredderItem SHREDDER;

    /** Item of the tray block. */
    public static final TrayItem TRAY;

    /** Item of the tun block. */
    public static final TunItem TUN;

    //Fruits and seeds of plants

    /** Basil spice. */
    public static final BasilItem BASIL;

    /** Little edible seeds. */
    public static final BeansItem BEANS;

    /** Fruit composed by little yellow seeds (or grains). */
    public static final CornItem CORN;

    /** Some grains of corn. */
    public static final CornGrainsItem CORN_GRAINS;

    /** Little berry with a purplish color, mutation of the sweet berries. */
    public static final DarkSweetBerriesItem DARK_SWEET_BERRIES;

    /** Edible purple berry, spongy, absorbent, typically used as a vegetable in cooking. */
    public static final EggplantItem EGGPLANT;

    /** Seeds of eggplant. */
    public static final EggplantSeedsItem EGGPLANT_SEEDS;

    /** Vegetable mostly used to make soups. */
    public static final KaleItem KALE;

    /** Seeds of kale. */
    public static final KaleSeedsItem KALE_SEEDS;

    /** Vegetable that is mainly used to make salad. */
    public static final LettuceLeavesItem LETTUCE_LEAVES;

    /** Seeds of lettuce. */
    public static final LettuceSeedsItem LETTUCE_SEEDS;

    /** Little poisonous berry with a black color. */
    public static final NightlockBerriesItem NIGHTLOCK_BERRIES;

    /** Vegetable that is the most widely cultivated species of the genus Allium. */
    public static final OnionItem ONION;

    /** Seeds of onion. */
    public static final OnionSeedsItem ONION_SEEDS;

    /** Red sweet potato, a rare mutation of the sweet potato. */
    public static final RedSweetPotatoItem RED_SWEET_POTATO;

    /** Little white seed, one of the most consumed foods in the world. */
    public static final RiceItem RICE;

    /** Some grains of rice. */
    public static final RiceGrainsItem RICE_GRAINS;

    /** Sweet potato, a mutation of the potato. */
    public static final SweetPotatoItem SWEET_POTATO;

    /** Buds of sweet potato. */
    public static final SweetPotatoBudsItem SWEET_POTATO_BUDS;

    /** Edible red berry, commonly used to make sauces or for salad. */
    public static final TomatoItem TOMATO;

    /** Seeds of tomato. */
    public static final TomatoSeedsItem TOMATO_SEEDS;

    //Fruits of trees

    /** Typical fruit used to make oil. */
    public static final OlivesItem OLIVES;

    /** Fruit produced by opuntia cactus. */
    public static final PricklyPearItem PRICKLY_PEAR;

    //Items from the earth

    /** Salt crystals. */
    public static final SaltCrystalsItem SALT_CRYSTALS;

    //Cooked foods

    /** Sweet potato, but baked. */
    public static final BakedSweetPotatoItem BAKED_SWEET_POTATO;

    //Crafted

    /** Salt. */
    public static final SaltItem SALT;

    /** Bottle that contains oil. */
    public static final OilBottleItem OIL_BOTTLE;

    /** Bottle that contains tomato sauce. */
    public static final TomatoSauceBottleItem TOMATO_SAUCE_BOTTLE;

    //Tree blocks

    /** Item of the main block of the opuntia. */
    public static final OpuntiaItem OPUNTIA;

    /** Item of the flowering block of the opuntia. */
    public static final FloweringOpuntiaItem FLOWERING_OPUNTIA;

    //Terrain blocks

    /** Item of the flooded mud block. */
    public static final FloodedMudItem FLOODED_MUD;

    /** Loads all the items. */
    public static void load() {
        CompostingChanceRegistry.INSTANCE.add(BAKED_SWEET_POTATO, BakedSweetPotatoItem.COMPOSTING_CHANCE);
        CompostingChanceRegistry.INSTANCE.add(BASIL, BasilItem.COMPOSTING_CHANCE);
        CompostingChanceRegistry.INSTANCE.add(BEANS, BeansItem.COMPOSTING_CHANCE);
        CompostingChanceRegistry.INSTANCE.add(CORN, CornItem.COMPOSTING_CHANCE);
        CompostingChanceRegistry.INSTANCE.add(CORN_GRAINS, CornGrainsItem.COMPOSTING_CHANCE);
        CompostingChanceRegistry.INSTANCE.add(DARK_SWEET_BERRIES, DarkSweetBerriesItem.COMPOSTING_CHANCE);
        CompostingChanceRegistry.INSTANCE.add(EGGPLANT, EggplantItem.COMPOSTING_CHANCE);
        CompostingChanceRegistry.INSTANCE.add(EGGPLANT_SEEDS, EggplantSeedsItem.COMPOSTING_CHANCE);
        CompostingChanceRegistry.INSTANCE.add(KALE, KaleItem.COMPOSTING_CHANCE);
        CompostingChanceRegistry.INSTANCE.add(KALE_SEEDS, KaleSeedsItem.COMPOSTING_CHANCE);
        CompostingChanceRegistry.INSTANCE.add(LETTUCE_LEAVES, LettuceLeavesItem.COMPOSTING_CHANCE);
        CompostingChanceRegistry.INSTANCE.add(LETTUCE_SEEDS, LettuceSeedsItem.COMPOSTING_CHANCE);
        CompostingChanceRegistry.INSTANCE.add(NIGHTLOCK_BERRIES, NightlockBerriesItem.COMPOSTING_CHANCE);
        CompostingChanceRegistry.INSTANCE.add(OLIVES, OlivesItem.COMPOSTING_CHANCE);
        CompostingChanceRegistry.INSTANCE.add(ONION, OnionItem.COMPOSTING_CHANCE);
        CompostingChanceRegistry.INSTANCE.add(ONION_SEEDS, OnionSeedsItem.COMPOSTING_CHANCE);
        CompostingChanceRegistry.INSTANCE.add(Items.POISONOUS_POTATO, 0.7f);
        CompostingChanceRegistry.INSTANCE.add(OPUNTIA, OpuntiaItem.COMPOSTING_CHANCE);
        CompostingChanceRegistry.INSTANCE.add(FLOWERING_OPUNTIA, FloweringOpuntiaItem.COMPOSTING_CHANCE);
        CompostingChanceRegistry.INSTANCE.add(PRICKLY_PEAR, PricklyPearItem.COMPOSTING_CHANCE);
        CompostingChanceRegistry.INSTANCE.add(RED_SWEET_POTATO, RedSweetPotatoItem.COMPOSTING_CHANCE);
        CompostingChanceRegistry.INSTANCE.add(RICE, RiceItem.COMPOSTING_CHANCE);
        CompostingChanceRegistry.INSTANCE.add(RICE_GRAINS, RiceGrainsItem.COMPOSTING_CHANCE);
        CompostingChanceRegistry.INSTANCE.add(SWEET_POTATO, SweetPotatoItem.COMPOSTING_CHANCE);
        CompostingChanceRegistry.INSTANCE.add(SWEET_POTATO_BUDS, SweetPotatoBudsItem.COMPOSTING_CHANCE);
        CompostingChanceRegistry.INSTANCE.add(TOMATO, TomatoItem.COMPOSTING_CHANCE);
        CompostingChanceRegistry.INSTANCE.add(TOMATO_SEEDS, TomatoSeedsItem.COMPOSTING_CHANCE);

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
        //Items loading order corresponds to the order of the creative tab "Terravibe".

        MORTAR = register(MortarItem.ID, new MortarItem(TerravibeBlocks.MORTAR, MortarItem.SETTINGS));
        SHREDDER = register(ShredderItem.ID, new ShredderItem(TerravibeBlocks.SHREDDER, ShredderItem.SETTINGS));
        TRAY = register(TrayItem.ID, new TrayItem(TerravibeBlocks.TRAY, TrayItem.SETTINGS));
        TUN = register(TunItem.ID, new TunItem(TerravibeBlocks.TUN, TunItem.SETTINGS));

        DARK_SWEET_BERRIES = register(DarkSweetBerriesItem.ID, new DarkSweetBerriesItem(TerravibeBlocks.DARK_SWEET_BERRY_BUSH, DarkSweetBerriesItem.SETTINGS));
        NIGHTLOCK_BERRIES = register(NightlockBerriesItem.ID, new NightlockBerriesItem(TerravibeBlocks.NIGHTLOCK_BERRY_BUSH, NightlockBerriesItem.SETTINGS));
        BASIL = register(BasilItem.ID, new BasilItem(TerravibeBlocks.BASIL_HERB, BasilItem.SETTINGS));
        BEANS = register(BeansItem.ID, new BeansItem(TerravibeBlocks.BEANS_CROP, BeansItem.SETTINGS));
        CORN = register(CornItem.ID, new CornItem(CornItem.SETTINGS));
        CORN_GRAINS = register(CornGrainsItem.ID, new CornGrainsItem(TerravibeBlocks.CORN_CROP, CornGrainsItem.SETTINGS));
        EGGPLANT = register(EggplantItem.ID, new EggplantItem(EggplantItem.SETTINGS));
        EGGPLANT_SEEDS = register(EggplantSeedsItem.ID, new EggplantSeedsItem(TerravibeBlocks.EGGPLANT_CROP, EggplantSeedsItem.SETTINGS));
        KALE = register(KaleItem.ID, new KaleItem(KaleItem.SETTINGS));
        KALE_SEEDS = register(KaleSeedsItem.ID, new KaleSeedsItem(TerravibeBlocks.KALE_CROP, KaleSeedsItem.SETTINGS));
        LETTUCE_LEAVES = register(LettuceLeavesItem.ID, new LettuceLeavesItem(LettuceLeavesItem.SETTINGS));
        LETTUCE_SEEDS = register(LettuceSeedsItem.ID, new LettuceSeedsItem(TerravibeBlocks.LETTUCE_CROP, LettuceSeedsItem.SETTINGS));
        ONION = register(OnionItem.ID, new OnionItem(OnionItem.SETTINGS));
        ONION_SEEDS = register(OnionSeedsItem.ID, new OnionSeedsItem(TerravibeBlocks.ONION_CROP, OnionSeedsItem.SETTINGS));
        RICE = register(RiceItem.ID, new RiceItem(RiceItem.SETTINGS));
        RICE_GRAINS = register(RiceGrainsItem.ID, new RiceGrainsItem(TerravibeBlocks.RICE_CROP, RiceGrainsItem.SETTINGS));
        SWEET_POTATO_BUDS = register(SweetPotatoBudsItem.ID, new SweetPotatoBudsItem(TerravibeBlocks.SWEET_POTATO_CROP, SweetPotatoBudsItem.SETTINGS));
        SWEET_POTATO = register(SweetPotatoItem.ID, new SweetPotatoItem(TerravibeBlocks.SWEET_POTATO_CROP, SweetPotatoItem.SETTINGS));
        RED_SWEET_POTATO = register(RedSweetPotatoItem.ID, new RedSweetPotatoItem(RedSweetPotatoItem.SETTINGS));
        TOMATO = register(TomatoItem.ID, new TomatoItem(TomatoItem.SETTINGS));
        TOMATO_SEEDS = register(TomatoSeedsItem.ID, new TomatoSeedsItem(TerravibeBlocks.TOMATO_CROP, TomatoSeedsItem.SETTINGS));

        OLIVES = register(OlivesItem.ID, new OlivesItem(OlivesItem.SETTINGS));
        PRICKLY_PEAR = register(PricklyPearItem.ID, new PricklyPearItem(PricklyPearItem.SETTINGS));

        SALT_CRYSTALS = register(SaltCrystalsItem.ID, new SaltCrystalsItem(SaltCrystalsItem.SETTINGS));

        BAKED_SWEET_POTATO = register(BakedSweetPotatoItem.ID, new BakedSweetPotatoItem(BakedSweetPotatoItem.SETTINGS));

        SALT = register(SaltItem.ID, new SaltItem(SaltItem.SETTINGS));
        OIL_BOTTLE = register(OilBottleItem.ID, new OilBottleItem(OilBottleItem.SETTINGS));
        TOMATO_SAUCE_BOTTLE = register(TomatoSauceBottleItem.ID, new TomatoSauceBottleItem(TomatoSauceBottleItem.SETTINGS));

        OPUNTIA = register(OpuntiaItem.ID, new OpuntiaItem(TerravibeBlocks.OPUNTIA, OpuntiaItem.SETTINGS));
        FLOWERING_OPUNTIA = register(FloweringOpuntiaItem.ID, new FloweringOpuntiaItem(TerravibeBlocks.FLOWERING_OPUNTIA, FloweringOpuntiaItem.SETTINGS));

        FLOODED_MUD = register(FloodedMudItem.ID, new FloodedMudItem(TerravibeBlocks.FLOODED_MUD, FloodedMudItem.SETTINGS));
    }
}
