package xyz.devpelux.terravibe.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.devpelux.terravibe.block.TerravibeBlocks;
import xyz.devpelux.terravibe.core.CorkStrippableBlockRegistry;
import xyz.devpelux.terravibe.core.ModInfo;

/** List of all the items. */
public class TerravibeItems {
    private TerravibeItems() {}

    //Objects

    /** Item of the jar block. */
    public static final Item JAR;

    /** Item of the mortar block. */
    public static final Item MORTAR;

    /** Item of the shredder block. */
    public static final Item SHREDDER;

    /** Item of the tray block. */
    public static final Item TRAY;

    /** Item of the tun block. */
    public static final Item TUN;

    //Closed jars

    /** Item of the empty closed jar block. */
    public static final Item CLOSED_JAR_EMPTY;

    /** Item of the filled closed jar block. */
    public static final Item CLOSED_JAR_FILLED;

    //Cork objects

    /** Material obtained from stripping crimson stems. */
    public static final Item CRIMSON_CORK;

    /** Plug obtained from crimson cork. */
    public static final Item CRIMSON_CORK_PLUG;

    /** Material obtained from stripping dark oak logs. */
    public static final Item DARK_OAK_CORK;

    /** Plug obtained from dark oak cork. */
    public static final Item DARK_OAK_CORK_PLUG;

    /** Material obtained from stripping oak logs. */
    public static final Item OAK_CORK;

    /** Plug obtained from oak cork. */
    public static final Item OAK_CORK_PLUG;

    /** Material obtained from stripping warped stems. */
    public static final Item WARPED_CORK;

    /** Plug obtained from warped cork. */
    public static final Item WARPED_CORK_PLUG;

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

    /** Birch mold. */
    public static final Item BIRCH_MOLD;

    /** Dust of birch mold. */
    public static final Item BIRCH_MOLD_DUST;

    /** Dark mold. */
    public static final Item DARK_MOLD;

    /** Dust of dark mold. */
    public static final Item DARK_MOLD_DUST;

    /** Glowing dark mold. */
    public static final Item GLOWING_DARK_MOLD;

    /** Dust of glowing dark mold. */
    public static final Item GLOWING_DARK_MOLD_DUST;

    //Fruits of trees

    /** Fruit used in salads, stews and various recipes. */
    public static final Item OLIVES;

    /** Fruit produced by opuntia cactus. */
    public static final Item PRICKLY_PEAR;

    //Items from the earth

    /** Salt crystals. */
    public static final Item SALT_CRYSTALS;

    //Crafted foods

    /** Sweet potato baked. */
    public static final Item BAKED_SWEET_POTATO;

    /** Cheese. */
    public static final Item CHEESE;

    /** Flakes of cheese. */
    public static final Item CHEESE_FLAKES;

    /** Cheese with mold. */
    public static final Item GORGONZOLA;

    /** Flakes of gorgonzola. */
    public static final Item GORGONZOLA_FLAKES;

    /** Mozzarella. */
    public static final Item MOZZARELLA;

    /** Flakes of mozzarella. */
    public static final Item MOZZARELLA_FLAKES;

    /** Salad with tomato, lettuce, mozzarella. */
    public static final Item SALAD;

    /** Salad with tomato, lettuce. */
    public static final Item SALAD_SIMPLE;

    /** Salad with tomato, lettuce, mozzarella, onion. */
    public static final Item SALAD_MIXED;

    /** Salad with tomato, lettuce, mozzarella, onion, corn grains. */
    public static final Item SALAD_RICH;

    /** Salad with tomato, lettuce, mozzarella, onion, corn grains, olives. */
    public static final Item SALAD_FULL;

    /** Basic pizza plus all the cheese variants. */
    public static final Item PIZZA_FOUR_CHEESE;

    /** Slice of pizza four cheese. */
    public static final Item PIZZA_SLICE_FOUR_CHEESE;

    /** Basic pizza with tomato sauce, mozzarella, and basil. */
    public static final Item PIZZA_MARGHERITA;

    /** Slice of pizza margherita. */
    public static final Item PIZZA_SLICE_MARGHERITA;

    /** Pottage with vegetables. */
    public static final Item POTTAGE;

    //Crafted

    /** Dust of birch mold burned. */
    public static final Item BURNED_BIRCH_MOLD_DUST;

    /** Dust of dark mold burned. */
    public static final Item BURNED_DARK_MOLD_DUST;

    /** Dust of glowing dark mold burned. */
    public static final Item BURNED_GLOWING_DARK_MOLD_DUST;

    /** Salt. */
    public static final Item SALT;

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
        CompostingChanceRegistry.INSTANCE.add(BIRCH_MOLD, 0.2f);
        CompostingChanceRegistry.INSTANCE.add(BIRCH_MOLD_DUST, 0.04f);
        CompostingChanceRegistry.INSTANCE.add(BURNED_BIRCH_MOLD_DUST, 0.01f);
        CompostingChanceRegistry.INSTANCE.add(BURNED_DARK_MOLD_DUST, 0.01f);
        CompostingChanceRegistry.INSTANCE.add(BURNED_GLOWING_DARK_MOLD_DUST, 0.01f);
        CompostingChanceRegistry.INSTANCE.add(CHEESE, 0.65f);
        CompostingChanceRegistry.INSTANCE.add(CHEESE_FLAKES, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(CORN, 0.6f);
        CompostingChanceRegistry.INSTANCE.add(CORN_GRAINS, 0.05f);
        CompostingChanceRegistry.INSTANCE.add(CRIMSON_CORK, 0.2f);
        CompostingChanceRegistry.INSTANCE.add(DARK_SWEET_BERRIES, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(DARK_MOLD, 0.2f);
        CompostingChanceRegistry.INSTANCE.add(DARK_MOLD_DUST, 0.04f);
        CompostingChanceRegistry.INSTANCE.add(DARK_OAK_CORK, 0.2f);
        CompostingChanceRegistry.INSTANCE.add(EGGPLANT, 0.65f);
        CompostingChanceRegistry.INSTANCE.add(EGGPLANT_SEEDS, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(GLOWING_DARK_MOLD, 0.2f);
        CompostingChanceRegistry.INSTANCE.add(GLOWING_DARK_MOLD_DUST, 0.04f);
        CompostingChanceRegistry.INSTANCE.add(GORGONZOLA, 0.65f);
        CompostingChanceRegistry.INSTANCE.add(GORGONZOLA_FLAKES, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(KALE, 0.65f);
        CompostingChanceRegistry.INSTANCE.add(KALE_SEEDS, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(LETTUCE_LEAVES, 0.5f);
        CompostingChanceRegistry.INSTANCE.add(LETTUCE_SEEDS, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(MOZZARELLA, 0.5f);
        CompostingChanceRegistry.INSTANCE.add(MOZZARELLA_FLAKES, 0.2f);
        CompostingChanceRegistry.INSTANCE.add(NIGHTLOCK_BERRIES, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(OAK_CORK, 0.2f);
        CompostingChanceRegistry.INSTANCE.add(OLIVES, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(ONION, 0.65f);
        CompostingChanceRegistry.INSTANCE.add(ONION_SEEDS, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(Items.POISONOUS_POTATO, 0.7f);
        CompostingChanceRegistry.INSTANCE.add(OPUNTIA, 0.5f);
        CompostingChanceRegistry.INSTANCE.add(FLOWERING_OPUNTIA, 0.6f);
        CompostingChanceRegistry.INSTANCE.add(PRICKLY_PEAR, 0.65f);
        CompostingChanceRegistry.INSTANCE.add(PIZZA_FOUR_CHEESE, 1f);
        CompostingChanceRegistry.INSTANCE.add(PIZZA_MARGHERITA, 1f);
        CompostingChanceRegistry.INSTANCE.add(PIZZA_SLICE_FOUR_CHEESE, 0.65f);
        CompostingChanceRegistry.INSTANCE.add(PIZZA_SLICE_MARGHERITA, 0.65f);
        CompostingChanceRegistry.INSTANCE.add(RED_SWEET_POTATO, 0.8f);
        CompostingChanceRegistry.INSTANCE.add(RICE, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(RICE_GRAINS, 0.05f);
        CompostingChanceRegistry.INSTANCE.add(SWEET_POTATO, 0.65f);
        CompostingChanceRegistry.INSTANCE.add(SWEET_POTATO_BUDS, 0.4f);
        CompostingChanceRegistry.INSTANCE.add(TOMATO, 0.65f);
        CompostingChanceRegistry.INSTANCE.add(TOMATO_SEEDS, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(WARPED_CORK, 0.2f);

        CorkStrippableBlockRegistry.register(Blocks.CRIMSON_STEM, CRIMSON_CORK);
        CorkStrippableBlockRegistry.register(Blocks.CRIMSON_HYPHAE, CRIMSON_CORK);
        CorkStrippableBlockRegistry.register(Blocks.DARK_OAK_LOG, DARK_OAK_CORK);
        CorkStrippableBlockRegistry.register(Blocks.DARK_OAK_WOOD, DARK_OAK_CORK);
        CorkStrippableBlockRegistry.register(Blocks.OAK_LOG, OAK_CORK);
        CorkStrippableBlockRegistry.register(Blocks.OAK_WOOD, OAK_CORK);
        CorkStrippableBlockRegistry.register(Blocks.WARPED_STEM, WARPED_CORK);
        CorkStrippableBlockRegistry.register(Blocks.WARPED_HYPHAE, WARPED_CORK);
    }

    /** Loads all the color providers for the items. */
    @Environment(EnvType.CLIENT)
    public static void loadColorProviders() {
        ColorProviderRegistry.ITEM.register((ItemColorProvider) CRIMSON_CORK_PLUG, CRIMSON_CORK_PLUG);
        ColorProviderRegistry.ITEM.register((ItemColorProvider) DARK_OAK_CORK_PLUG, DARK_OAK_CORK_PLUG);
        ColorProviderRegistry.ITEM.register((ItemColorProvider) OAK_CORK_PLUG, OAK_CORK_PLUG);
        ColorProviderRegistry.ITEM.register((ItemColorProvider) TOMATO_SAUCE_BOTTLE, TOMATO_SAUCE_BOTTLE);
        ColorProviderRegistry.ITEM.register((ItemColorProvider) WARPED_CORK_PLUG, WARPED_CORK_PLUG);
        ColorProviderRegistry.ITEM.register((ItemColorProvider) CLOSED_JAR_EMPTY, CLOSED_JAR_EMPTY);
        ColorProviderRegistry.ITEM.register((ItemColorProvider) CLOSED_JAR_FILLED, CLOSED_JAR_FILLED);
    }

    /** Registers the specified item with the specified id. */
    private static <T extends Item> T register(String path, T item) {
        return Registry.register(Registry.ITEM, new Identifier(ModInfo.MOD_ID, path) , item);
    }

    static {
        //Items loading order corresponds to the order of the creative tab "Terravibe".
        JAR = register("jar", new AliasedBlockItem(TerravibeBlocks.JAR, Settings.stack64()));
        DARK_OAK_CORK_PLUG = register("dark_oak_cork_plug", new ColoredItem(Settings.stack64(), (s, i) -> i == 1 ? 0x4f3218 : -1));
        OAK_CORK_PLUG = register("oak_cork_plug", new ColoredItem(Settings.stack64(), (s, i) -> i == 1 ? 0xb8945f : -1));
        CRIMSON_CORK_PLUG = register("crimson_cork_plug", new ColoredItem(Settings.stack64(), (s, i) -> i == 1 ? 0x7e3a56 : -1));
        WARPED_CORK_PLUG = register("warped_cork_plug", new ColoredItem(Settings.stack64(), (s, i) -> i == 1 ? 0x398382 : -1));

        MORTAR = register("mortar", new AliasedBlockItem(TerravibeBlocks.MORTAR, Settings.stack64()));
        SHREDDER = register("shredder", new AliasedBlockItem(TerravibeBlocks.SHREDDER, Settings.stack64()));
        TRAY = register("tray", new AliasedBlockItem(TerravibeBlocks.TRAY, Settings.stack64()));
        TUN = register("tun", new AliasedBlockItem(TerravibeBlocks.TUN, Settings.stack64()));

        DARK_SWEET_BERRIES = register("dark_sweet_berries", new AliasedBlockItem(TerravibeBlocks.DARK_SWEET_BERRY_BUSH,
                Settings.stack64().food(TerravibeFoodComponents.DARK_SWEET_BERRIES)));
        NIGHTLOCK_BERRIES = register("nightlock_berries", new NightlockBerriesItem(TerravibeBlocks.NIGHTLOCK_BERRY_BUSH,
                Settings.stack64().food(TerravibeFoodComponents.NIGHTLOCK_BERRIES)));
        BASIL = register("basil", new AliasedBlockItem(TerravibeBlocks.BASIL_HERB, Settings.stack64()));
        BEANS = register("beans", new AliasedBlockItem(TerravibeBlocks.BEANS_CROP, Settings.stack64().food(TerravibeFoodComponents.BEANS)));
        CORN = register("corn", new Item(Settings.stack64()));
        CORN_GRAINS = register("corn_grains", new AliasedBlockItem(TerravibeBlocks.CORN_CROP, Settings.stack64()));
        EGGPLANT = register("eggplant", new Item(Settings.stack64().food(TerravibeFoodComponents.EGGPLANT)));
        EGGPLANT_SEEDS = register("eggplant_seeds", new AliasedBlockItem(TerravibeBlocks.EGGPLANT_CROP, Settings.stack64()));
        KALE = register("kale", new Item(Settings.stack64().food(TerravibeFoodComponents.KALE)));
        KALE_SEEDS = register("kale_seeds", new AliasedBlockItem(TerravibeBlocks.KALE_CROP, Settings.stack64()));
        LETTUCE_LEAVES = register("lettuce_leaves", new Item(Settings.stack64().food(TerravibeFoodComponents.LETTUCE_LEAVES)));
        LETTUCE_SEEDS = register("lettuce_seeds", new AliasedBlockItem(TerravibeBlocks.LETTUCE_CROP, Settings.stack64()));
        ONION = register("onion", new Item(Settings.stack64().food(TerravibeFoodComponents.ONION)));
        ONION_SEEDS = register("onion_seeds", new AliasedBlockItem(TerravibeBlocks.ONION_CROP, Settings.stack64()));
        RICE = register("rice", new Item(Settings.stack64()));
        RICE_GRAINS = register("rice_grains", new AliasedBlockItem(TerravibeBlocks.RICE_CROP, Settings.stack64()));
        SWEET_POTATO_BUDS = register("sweet_potato_buds", new AliasedBlockItem(TerravibeBlocks.SWEET_POTATO_CROP,
                Settings.stack64Hided().food(TerravibeFoodComponents.SWEET_POTATO_BUDS)));
        SWEET_POTATO = register("sweet_potato", new AliasedBlockItem(TerravibeBlocks.SWEET_POTATO_CROP,
                Settings.stack64().food(TerravibeFoodComponents.SWEET_POTATO)));
        RED_SWEET_POTATO = register("red_sweet_potato", new Item(Settings.stack64().food(TerravibeFoodComponents.RED_SWEET_POTATO)));
        TOMATO = register("tomato", new Item(Settings.stack64().food(TerravibeFoodComponents.TOMATO)));
        TOMATO_SEEDS = register("tomato_seeds", new AliasedBlockItem(TerravibeBlocks.TOMATO_CROP, Settings.stack64()));

        BIRCH_MOLD = register("birch_mold", new Item(Settings.stack64()));
        BIRCH_MOLD_DUST = register("birch_mold_dust", new AliasedBlockItem(TerravibeBlocks.BIRCH_MOLD, Settings.stack64()));
        DARK_MOLD = register("dark_mold", new Item(Settings.stack64()));
        DARK_MOLD_DUST = register("dark_mold_dust", new AliasedBlockItem(TerravibeBlocks.DARK_MOLD, Settings.stack64()));
        GLOWING_DARK_MOLD = register("glowing_dark_mold", new Item(Settings.stack64()));
        GLOWING_DARK_MOLD_DUST = register("glowing_dark_mold_dust", new AliasedBlockItem(TerravibeBlocks.GLOWING_DARK_MOLD, Settings.stack64()));

        OLIVES = register("olives", new Item(Settings.stack64()));
        PRICKLY_PEAR = register("prickly_pear", new Item(Settings.stack64().food(TerravibeFoodComponents.PRICKLY_PEAR)));

        SALT_CRYSTALS = register("salt_crystals", new Item(Settings.stack64()));

        BAKED_SWEET_POTATO = register("baked_sweet_potato", new Item(Settings.stack64().food(TerravibeFoodComponents.BAKED_SWEET_POTATO)));
        CHEESE = register("cheese", new Item(Settings.stack64().food(TerravibeFoodComponents.CHEESE)));
        CHEESE_FLAKES = register("cheese_flakes", new Item(Settings.stack64().food(TerravibeFoodComponents.CHEESE_FLAKES)));
        GORGONZOLA = register("gorgonzola", new Item(Settings.stack64().food(TerravibeFoodComponents.GORGONZOLA)));
        GORGONZOLA_FLAKES = register("gorgonzola_flakes", new Item(Settings.stack64().food(TerravibeFoodComponents.GORGONZOLA_FLAKES)));
        MOZZARELLA = register("mozzarella", new Item(Settings.stack64().food(TerravibeFoodComponents.MOZZARELLA)));
        MOZZARELLA_FLAKES = register("mozzarella_flakes", new Item(Settings.stack64().food(TerravibeFoodComponents.MOZZARELLA_FLAKES)));
        SALAD = register("salad", new StewItem(Settings.stack16().food(TerravibeFoodComponents.SALAD)));
        SALAD_SIMPLE = register("salad_simple", new StewItem(Settings.stack16().food(TerravibeFoodComponents.SALAD_SIMPLE)));
        SALAD_MIXED = register("salad_mixed", new StewItem(Settings.stack16().food(TerravibeFoodComponents.SALAD_MIXED)));
        SALAD_RICH = register("salad_rich", new StewItem(Settings.stack16().food(TerravibeFoodComponents.SALAD_RICH)));
        SALAD_FULL = register("salad_full", new StewItem(Settings.stack16().food(TerravibeFoodComponents.SALAD_FULL)));
        POTTAGE = register("pottage", new StewItem(Settings.stack16().food(TerravibeFoodComponents.POTTAGE)));
        PIZZA_FOUR_CHEESE = register("pizza_four_cheese", new AliasedBlockItem(TerravibeBlocks.PIZZA_FOUR_CHEESE, Settings.stack1()));
        PIZZA_MARGHERITA = register("pizza_margherita", new AliasedBlockItem(TerravibeBlocks.PIZZA_MARGHERITA, Settings.stack1()));
        PIZZA_SLICE_FOUR_CHEESE = register("pizza_slice_four_cheese", new Item(
                Settings.stack4().food(TerravibeFoodComponents.PIZZA_SLICE_FOUR_CHEESE)));
        PIZZA_SLICE_MARGHERITA = register("pizza_slice_margherita", new Item(
                Settings.stack4().food(TerravibeFoodComponents.PIZZA_SLICE_MARGHERITA)));

        BURNED_BIRCH_MOLD_DUST = register("burned_birch_mold_dust", new Item(Settings.stack64()));
        BURNED_DARK_MOLD_DUST = register("burned_dark_mold_dust", new Item(Settings.stack64()));
        BURNED_GLOWING_DARK_MOLD_DUST = register("burned_glowing_dark_mold_dust", new Item(Settings.stack64()));
        SALT = register("salt", new Item(Settings.stack64()));
        TOMATO_SAUCE_BOTTLE = register("tomato_sauce_bottle", new ColoredItem(
                Settings.stack16().recipeRemainder(Items.GLASS_BOTTLE), (s, i) -> i == 1 ? 0xf61815 : -1));

        OPUNTIA = register("opuntia", new AliasedBlockItem(TerravibeBlocks.OPUNTIA, Settings.stack64()));
        FLOWERING_OPUNTIA = register("flowering_opuntia", new AliasedBlockItem(TerravibeBlocks.FLOWERING_OPUNTIA, Settings.stack64()));

        FLOODED_MUD = register("flooded_mud", new AliasedBlockItem(TerravibeBlocks.FLOODED_MUD, Settings.stack64()));

        DARK_OAK_CORK = register("dark_oak_cork", new Item(Settings.stack64()));
        OAK_CORK = register("oak_cork", new Item(Settings.stack64()));
        CRIMSON_CORK = register("crimson_cork", new Item(Settings.stack64()));
        WARPED_CORK = register("warped_cork", new Item(Settings.stack64()));

        CLOSED_JAR_EMPTY = register("closed_jar_empty", new ClosedJarItem(Settings.stack16Hided()));
        CLOSED_JAR_FILLED = register("closed_jar_filled", new ClosedJarItem(Settings.stack16Hided()));
    }


    /** Generic item settings. */
    private static class Settings {
        /** Generates generic item settings, with stack of 1. */
        private static Item.Settings stack1() {
            return new FabricItemSettings().maxCount(1).group(TerravibeItemGroups.TERRAVIBE);
        }

        /** Generates generic item settings, with stack of 4. */
        private static Item.Settings stack4() {
            return new FabricItemSettings().maxCount(4).group(TerravibeItemGroups.TERRAVIBE);
        }

        /** Generates generic item settings, with stack of 16. */
        private static Item.Settings stack16() {
            return stack16Hided().group(TerravibeItemGroups.TERRAVIBE);
        }

        /** Generates generic item settings, with stack of 16, without any group. */
        private static Item.Settings stack16Hided() {
            return new FabricItemSettings().maxCount(16);
        }

        /** Generates generic item settings, with stack of 64. */
        private static Item.Settings stack64() {
            return stack64Hided().group(TerravibeItemGroups.TERRAVIBE);
        }

        /** Generates generic item settings, with stack of 64, without any group. */
        private static Item.Settings stack64Hided() {
            return new FabricItemSettings().maxCount(64);
        }
    }
}
