package xyz.devpelux.terravibe.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.devpelux.terravibe.block.TerravibeBlocks;
import xyz.devpelux.terravibe.block.TunBlock;
import xyz.devpelux.terravibe.core.ModInfo;

/** List of all the items. */
public class TerravibeItems {
    private TerravibeItems() {}

    //Item settings

    /** Generic item settings, with stack of 16. */
    private static final Item.Settings STACK16;

    /** Generic item settings, with stack of 64. */
    private static final Item.Settings STACK64;

    /** Generic item settings, with stack of 64 without any group. */
    private static final Item.Settings STACK64_HIDED;

    //Objects

    /** Item of the mortar block. */
    public static final Item MORTAR;

    /** Item of the shredder block. */
    public static final Item SHREDDER;

    /** Item of the tray block. */
    public static final Item TRAY;

    /** Item of the tun block. */
    public static final Item TUN;

    //Fruits and seeds of plants

    /** Basil spice. */
    public static final Item BASIL;

    /** Little edible seeds. */
    public static final Item BEANS;

    /** Fruit composed by little yellow seeds (or grains). */
    public static final Item CORN;

    /** Some grains of corn. */
    public static final Item CORN_GRAINS;

    /** Little berry with a purplish color, mutation of the sweet berries. */
    public static final Item DARK_SWEET_BERRIES;

    /** Edible purple berry, spongy, absorbent, typically used as a vegetable in cooking. */
    public static final Item EGGPLANT;

    /** Seeds of eggplant. */
    public static final Item EGGPLANT_SEEDS;

    /** Vegetable mostly used to make soups. */
    public static final Item KALE;

    /** Seeds of kale. */
    public static final Item KALE_SEEDS;

    /** Vegetable that is mainly used to make salad. */
    public static final Item LETTUCE_LEAVES;

    /** Seeds of lettuce. */
    public static final Item LETTUCE_SEEDS;

    /** Little poisonous berry with a black color. */
    public static final Item NIGHTLOCK_BERRIES;

    /** Vegetable that is the most widely cultivated species of the genus Allium. */
    public static final Item ONION;

    /** Seeds of onion. */
    public static final Item ONION_SEEDS;

    /** Red sweet potato, a rare mutation of the sweet potato. */
    public static final Item RED_SWEET_POTATO;

    /** Little white seed, one of the most consumed foods in the world. */
    public static final Item RICE;

    /** Some grains of rice. */
    public static final Item RICE_GRAINS;

    /** Sweet potato, a mutation of the potato. */
    public static final Item SWEET_POTATO;

    /** Buds of sweet potato. */
    public static final Item SWEET_POTATO_BUDS;

    /** Edible red berry, commonly used to make sauces or for salad. */
    public static final Item TOMATO;

    /** Seeds of tomato. */
    public static final Item TOMATO_SEEDS;

    //Fruits and seeds of other plants

    /** Dark mold. */
    public static final Item DARK_MOLD;

    /** Dust of dark mold. */
    public static final Item DARK_MOLD_DUST;

    //Fruits of trees

    /** Typical fruit used to make oil. */
    public static final Item OLIVES;

    /** Fruit produced by opuntia cactus. */
    public static final Item PRICKLY_PEAR;

    //Items from the earth

    /** Salt crystals. */
    public static final Item SALT_CRYSTALS;

    //Cooked foods

    /** Sweet potato, but baked. */
    public static final Item BAKED_SWEET_POTATO;

    //Crafted

    /** Salt. */
    public static final Item SALT;

    /** Bottle that contains oil. */
    public static final Item OIL_BOTTLE;

    /** Bottle that contains tomato sauce. */
    public static final Item TOMATO_SAUCE_BOTTLE;

    //Tree blocks

    /** Item of the main block of the opuntia. */
    public static final Item OPUNTIA;

    /** Item of the flowering block of the opuntia. */
    public static final Item FLOWERING_OPUNTIA;

    //Terrain blocks

    /** Item of the flooded mud block. */
    public static final Item FLOODED_MUD;

    /** Loads all the items. */
    public static void load() {
        CompostingChanceRegistry.INSTANCE.add(BAKED_SWEET_POTATO, 0.85f);
        CompostingChanceRegistry.INSTANCE.add(BASIL, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(BEANS, 0.7f);
        CompostingChanceRegistry.INSTANCE.add(CORN, 0.6f);
        CompostingChanceRegistry.INSTANCE.add(CORN_GRAINS, 0.05f);
        CompostingChanceRegistry.INSTANCE.add(DARK_SWEET_BERRIES, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(EGGPLANT, 0.65f);
        CompostingChanceRegistry.INSTANCE.add(EGGPLANT_SEEDS, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(KALE, 0.65f);
        CompostingChanceRegistry.INSTANCE.add(KALE_SEEDS, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(LETTUCE_LEAVES, 0.5f);
        CompostingChanceRegistry.INSTANCE.add(LETTUCE_SEEDS, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(NIGHTLOCK_BERRIES, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(OLIVES, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(ONION, 0.65f);
        CompostingChanceRegistry.INSTANCE.add(ONION_SEEDS, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(Items.POISONOUS_POTATO, 0.7f);
        CompostingChanceRegistry.INSTANCE.add(OPUNTIA, 0.5f);
        CompostingChanceRegistry.INSTANCE.add(FLOWERING_OPUNTIA, 0.6f);
        CompostingChanceRegistry.INSTANCE.add(PRICKLY_PEAR, 0.65f);
        CompostingChanceRegistry.INSTANCE.add(RED_SWEET_POTATO, 0.8f);
        CompostingChanceRegistry.INSTANCE.add(RICE, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(RICE_GRAINS, 0.05f);
        CompostingChanceRegistry.INSTANCE.add(DARK_MOLD, 0.2f);
        CompostingChanceRegistry.INSTANCE.add(DARK_MOLD_DUST, 0.04f);
        CompostingChanceRegistry.INSTANCE.add(SWEET_POTATO, 0.65f);
        CompostingChanceRegistry.INSTANCE.add(SWEET_POTATO_BUDS, 0.4f);
        CompostingChanceRegistry.INSTANCE.add(TOMATO, 0.65f);
        CompostingChanceRegistry.INSTANCE.add(TOMATO_SEEDS, 0.3f);

        TunBlock.registerContainable(Items.HONEY_BOTTLE, (s, w, p, i) -> 0x976018);
        TunBlock.registerContainable(OIL_BOTTLE, (s, w, p, i) -> 0x808000);
        TunBlock.registerContainable(TOMATO_SAUCE_BOTTLE, (s, w, p, i) -> 0xf61815);
    }

    /** Loads all the color providers for the items. */
    @Environment(EnvType.CLIENT)
    public static void loadColorProviders() {
        ColorProviderRegistry.ITEM.register((s, i) -> i == 1 ? 0x808000 : -1, OIL_BOTTLE);
        ColorProviderRegistry.ITEM.register((s, i) -> i == 1 ? 0xf61815 : -1, TOMATO_SAUCE_BOTTLE);
    }

    /** Registers the specified item with the specified id. */
    private static <T extends Item> T register(String path, T item) {
        return Registry.register(Registry.ITEM, new Identifier(ModInfo.MOD_ID, path) , item);
    }

    static {
        //Item settings.
        STACK16 = new FabricItemSettings().maxCount(16).group(TerravibeItemGroups.TERRAVIBE);
        STACK64 = new FabricItemSettings().maxCount(64).group(TerravibeItemGroups.TERRAVIBE);
        STACK64_HIDED = new FabricItemSettings().maxCount(64);

        //Items loading order corresponds to the order of the creative tab "Terravibe".
        MORTAR = register("mortar", new AliasedBlockItem(TerravibeBlocks.MORTAR, STACK64));
        SHREDDER = register("shredder", new AliasedBlockItem(TerravibeBlocks.SHREDDER, STACK64));
        TRAY = register("tray", new AliasedBlockItem(TerravibeBlocks.TRAY, STACK64));
        TUN = register("tun", new AliasedBlockItem(TerravibeBlocks.TUN, STACK64));

        DARK_SWEET_BERRIES = register("dark_sweet_berries", new AliasedBlockItem(TerravibeBlocks.DARK_SWEET_BERRY_BUSH,
                STACK64.food(TerravibeFoodComponents.DARK_SWEET_BERRIES)));
        NIGHTLOCK_BERRIES = register("nightlock_berries", new NightlockBerriesItem(TerravibeBlocks.NIGHTLOCK_BERRY_BUSH,
                STACK64.food(TerravibeFoodComponents.NIGHTLOCK_BERRIES)));
        BASIL = register("basil", new AliasedBlockItem(TerravibeBlocks.BASIL_HERB, STACK64));
        BEANS = register("beans", new AliasedBlockItem(TerravibeBlocks.BEANS_CROP, STACK64.food(TerravibeFoodComponents.BEANS)));
        CORN = register("corn", new Item(STACK64));
        CORN_GRAINS = register("corn_grains", new AliasedBlockItem(TerravibeBlocks.CORN_CROP, STACK64));
        EGGPLANT = register("eggplant", new Item(STACK64.food(TerravibeFoodComponents.EGGPLANT)));
        EGGPLANT_SEEDS = register("eggplant_seeds", new AliasedBlockItem(TerravibeBlocks.EGGPLANT_CROP, STACK64));
        KALE = register("kale", new Item(STACK64.food(TerravibeFoodComponents.KALE)));
        KALE_SEEDS = register("kale_seeds", new AliasedBlockItem(TerravibeBlocks.KALE_CROP, STACK64));
        LETTUCE_LEAVES = register("lettuce_leaves", new Item(STACK64.food(TerravibeFoodComponents.LETTUCE_LEAVES)));
        LETTUCE_SEEDS = register("lettuce_seeds", new AliasedBlockItem(TerravibeBlocks.LETTUCE_CROP, STACK64));
        ONION = register("onion", new Item(STACK64.food(TerravibeFoodComponents.ONION)));
        ONION_SEEDS = register("onion_seeds", new AliasedBlockItem(TerravibeBlocks.ONION_CROP, STACK64));
        RICE = register("rice", new Item(STACK64));
        RICE_GRAINS = register("rice_grains", new AliasedBlockItem(TerravibeBlocks.RICE_CROP, STACK64));
        SWEET_POTATO_BUDS = register("sweet_potato_buds", new AliasedBlockItem(TerravibeBlocks.SWEET_POTATO_CROP,
                STACK64_HIDED.food(TerravibeFoodComponents.SWEET_POTATO_BUDS)));
        SWEET_POTATO = register("sweet_potato", new AliasedBlockItem(TerravibeBlocks.SWEET_POTATO_CROP,
                STACK64.food(TerravibeFoodComponents.SWEET_POTATO)));
        RED_SWEET_POTATO = register("red_sweet_potato", new Item(STACK64.food(TerravibeFoodComponents.RED_SWEET_POTATO)));
        TOMATO = register("tomato", new Item(STACK64.food(TerravibeFoodComponents.TOMATO)));
        TOMATO_SEEDS = register("tomato_seeds", new AliasedBlockItem(TerravibeBlocks.TOMATO_CROP, STACK64));

        DARK_MOLD = register("dark_mold", new Item(STACK64));
        DARK_MOLD_DUST = register("dark_mold_dust", new AliasedBlockItem(TerravibeBlocks.DARK_MOLD, STACK64));

        OLIVES = register("olives", new Item(STACK64));
        PRICKLY_PEAR = register("prickly_pear", new Item(STACK64.food(TerravibeFoodComponents.PRICKLY_PEAR)));

        SALT_CRYSTALS = register("salt_crystals", new Item(STACK64));

        BAKED_SWEET_POTATO = register("baked_sweet_potato", new Item(STACK64.food(TerravibeFoodComponents.BAKED_SWEET_POTATO)));

        SALT = register("salt", new Item(STACK64));
        OIL_BOTTLE = register("oil_bottle", new Item(STACK16));
        TOMATO_SAUCE_BOTTLE = register("tomato_sauce_bottle", new Item(STACK16));

        OPUNTIA = register("opuntia", new AliasedBlockItem(TerravibeBlocks.OPUNTIA, STACK64));
        FLOWERING_OPUNTIA = register("flowering_opuntia", new AliasedBlockItem(TerravibeBlocks.FLOWERING_OPUNTIA, STACK64));

        FLOODED_MUD = register("flooded_mud", new AliasedBlockItem(TerravibeBlocks.FLOODED_MUD, STACK64));
    }
}
