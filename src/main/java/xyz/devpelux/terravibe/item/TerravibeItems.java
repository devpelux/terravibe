package xyz.devpelux.terravibe.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import xyz.devpelux.terravibe.block.ContainerBlockData;
import xyz.devpelux.terravibe.block.MilkCauldronBlock;
import xyz.devpelux.terravibe.block.TerravibeBlocks;
import xyz.devpelux.terravibe.core.CorkStrippableBlockRegistry;
import xyz.devpelux.terravibe.core.Terravibe;

/**
 * List of all the items.
 */
public final class TerravibeItems {
	/**
	 * Item used as icon for the "Terravibe" mod.
	 */
	public static final Item TERRAVIBE;

	//Objects

	/**
	 * Item of the jar block.
	 */
	public static final Item JAR;

	/**
	 * Item of the empty closed jar block.
	 */
	public static final Item CLOSED_JAR_EMPTY;

	/**
	 * Item of the filled closed jar block.
	 */
	public static final Item CLOSED_JAR_FILLED;

	/**
	 * Item of the mortar block.
	 */
	public static final Item MORTAR;

	/**
	 * Item of the shredder block.
	 */
	public static final Item SHREDDER;

	/**
	 * Item of the tray block.
	 */
	public static final Item TRAY;

	/**
	 * Item of the tun block.
	 */
	public static final Item TUN;

	//Items dropped from plants

	/**
	 * Basil spice.
	 */
	public static final Item BASIL;

	/**
	 * Little edible seeds.
	 */
	public static final Item BEANS;

	/**
	 * Birch mold.
	 */
	public static final Item BIRCH_MOLD;

	/**
	 * Dark mold.
	 */
	public static final Item DARK_MOLD;

	/**
	 * Glowing dark mold.
	 */
	public static final Item GLOWING_DARK_MOLD;

	/**
	 * Fruit composed by little yellow seeds (or grains).
	 */
	public static final Item CORN;

	/**
	 * Some grains of corn.
	 */
	public static final Item CORN_GRAINS;

	/**
	 * Little berry with a purplish color, mutation of the sweet berries.
	 */
	public static final Item DARK_SWEET_BERRIES;

	/**
	 * Little poisonous berry with a black color.
	 */
	public static final Item NIGHTLOCK_BERRIES;

	/**
	 * Edible purple berry, spongy, absorbent, typically used as a vegetable in cooking.
	 */
	public static final Item EGGPLANT;

	/**
	 * Seeds of eggplant.
	 */
	public static final Item EGGPLANT_SEEDS;

	/**
	 * Vegetable mostly used to make soups.
	 */
	public static final Item KALE;

	/**
	 * Seeds of kale.
	 */
	public static final Item KALE_SEEDS;

	/**
	 * Vegetable that is mainly used to make salad.
	 */
	public static final Item LETTUCE_LEAVES;

	/**
	 * Seeds of lettuce.
	 */
	public static final Item LETTUCE_SEEDS;

	/**
	 * Vegetable that is the most widely cultivated species of the genus Allium.
	 */
	public static final Item ONION;

	/**
	 * Seeds of onion.
	 */
	public static final Item ONION_SEEDS;

	/**
	 * Little white seed, one of the most consumed foods in the world.
	 */
	public static final Item RICE;

	/**
	 * Some grains of rice.
	 */
	public static final Item RICE_GRAINS;

	/**
	 * Sweet potato, a mutation of the potato.
	 */
	public static final Item SWEET_POTATO;

	/**
	 * Buds of sweet potato.
	 */
	public static final Item SWEET_POTATO_BUDS;

	/**
	 * Red sweet potato, a rare mutation of the sweet potato.
	 */
	public static final Item RED_SWEET_POTATO;

	/**
	 * Edible plant with purple flowers.
	 */
	public static final Item THISTLE;

	/**
	 * Edible red berry, commonly used to make sauces or for salad.
	 */
	public static final Item TOMATO;

	/**
	 * Seeds of tomato.
	 */
	public static final Item TOMATO_SEEDS;

	//Ancient plants with their seeds and drops

	/**
	 * An extinct poisonous alga that gives you water breathing.
	 */
	public static final Item GILLYWEED;

	/**
	 * Seeds of Gillyweed algae.
	 */
	public static final Item GILLYWEED_SEEDS;

	/**
	 * Ancient dirty seeds of Gillyweed algae.
	 */
	public static final Item ANCIENT_GILLYWEED_SEEDS;

	/**
	 * An extinct blueberry that gives you night vision, but with some side effects.
	 */
	public static final Item NIGHTSHADE_FERN_BLUEBERRIES;

	/**
	 * Seeds of the nightshade fern.
	 */
	public static final Item NIGHTSHADE_FERN_SEEDS;

	/**
	 * Ancient dirty seeds of the nightshade fern.
	 */
	public static final Item ANCIENT_NIGHTSHADE_FERN_SEEDS;

	//Items dropped from trees

	/**
	 * Material obtained from stripping crimson stems.
	 */
	public static final Item CRIMSON_CORK;

	/**
	 * Material obtained from stripping dark oak logs.
	 */
	public static final Item DARK_OAK_CORK;

	/**
	 * Material obtained from stripping oak logs.
	 */
	public static final Item OAK_CORK;

	/**
	 * Material obtained from stripping warped stems.
	 */
	public static final Item WARPED_CORK;

	/**
	 * Fruit used in salads, stews and various recipes.
	 */
	public static final Item OLIVES;

	/**
	 * Fruit produced by opuntia cactus.
	 */
	public static final Item PRICKLY_PEAR;

	//Tree blocks

	/**
	 * Item of the main block of the opuntia.
	 */
	public static final Item OPUNTIA;

	/**
	 * Item of the flowering block of the opuntia.
	 */
	public static final Item FLOWERING_OPUNTIA;

	//Food crafted items

	/**
	 * Sweet potato baked.
	 */
	public static final Item BAKED_SWEET_POTATO;

	/**
	 * Cheese.
	 */
	public static final Item CHEESE;

	/**
	 * Flakes of cheese.
	 */
	public static final Item CHEESE_FLAKES;

	/**
	 * Cheese with mold.
	 */
	public static final Item GORGONZOLA;

	/**
	 * Flakes of gorgonzola.
	 */
	public static final Item GORGONZOLA_FLAKES;

	/**
	 * Mozzarella.
	 */
	public static final Item MOZZARELLA;

	/**
	 * Flakes of mozzarella.
	 */
	public static final Item MOZZARELLA_FLAKES;

	/**
	 * Slice of pizza four cheese.
	 */
	public static final Item PIZZA_SLICE_FOUR_CHEESE;

	/**
	 * Slice of pizza margherita.
	 */
	public static final Item PIZZA_SLICE_MARGHERITA;

	/**
	 * Pottage with vegetables.
	 */
	public static final Item POTTAGE;

	/**
	 * Salad with tomato, lettuce.
	 */
	public static final Item SALAD_SIMPLE;

	/**
	 * Salad with tomato, lettuce, mozzarella.
	 */
	public static final Item SALAD;

	/**
	 * Salad with tomato, lettuce, mozzarella, onion.
	 */
	public static final Item SALAD_MIXED;

	/**
	 * Salad with tomato, lettuce, mozzarella, onion, corn grains.
	 */
	public static final Item SALAD_RICH;

	/**
	 * Salad with tomato, lettuce, mozzarella, onion, corn grains, olives.
	 */
	public static final Item SALAD_FULL;

	/**
	 * Leaves of thistle.
	 */
	public static final Item THISTLE_LEAVES;

	//Non-food crafted items

	/**
	 * Dust of birch mold.
	 */
	public static final Item BIRCH_MOLD_DUST;

	/**
	 * Dust of dark mold.
	 */
	public static final Item DARK_MOLD_DUST;

	/**
	 * Dust of glowing dark mold.
	 */
	public static final Item GLOWING_DARK_MOLD_DUST;

	/**
	 * Dust of birch mold burned.
	 */
	public static final Item BURNED_BIRCH_MOLD_DUST;

	/**
	 * Dust of dark mold burned.
	 */
	public static final Item BURNED_DARK_MOLD_DUST;

	/**
	 * Dust of glowing dark mold burned.
	 */
	public static final Item BURNED_GLOWING_DARK_MOLD_DUST;

	/**
	 * Cheese wheel.
	 */
	public static final Item CHEESE_WHEEL;

	/**
	 * Gorgonzola wheel.
	 */
	public static final Item GORGONZOLA_WHEEL;

	/**
	 * Basic pizza plus all the cheese variants.
	 */
	public static final Item PIZZA_FOUR_CHEESE;

	/**
	 * Basic pizza with tomato sauce, mozzarella, and basil.
	 */
	public static final Item PIZZA_MARGHERITA;

	/**
	 * Plug obtained from crimson cork.
	 */
	public static final Item CRIMSON_CORK_PLUG;

	/**
	 * Plug obtained from dark oak cork.
	 */
	public static final Item DARK_OAK_CORK_PLUG;

	/**
	 * Plug obtained from oak cork.
	 */
	public static final Item OAK_CORK_PLUG;

	/**
	 * Plug obtained from warped cork.
	 */
	public static final Item WARPED_CORK_PLUG;

	/**
	 * Salt.
	 */
	public static final Item SALT;

	/**
	 * Salt crystals.
	 */
	public static final Item SALT_CRYSTALS;

	/**
	 * Stamens of thistle, used to coagulate milk.
	 */
	public static final Item THISTLE_STAMENS;

	//Bottles

	/**
	 * Bottle that contains tomato sauce.
	 */
	public static final Item TOMATO_SAUCE_BOTTLE;

	//Terrain blocks

	/**
	 * Item of the excavated mud block.
	 */
	public static final Item EXCAVATED_MUD;

	private TerravibeItems() {
	}

	/**
	 * Loads all the items.
	 */
	public static void load() {
		CompostingChanceRegistry.INSTANCE.add(ANCIENT_GILLYWEED_SEEDS, 0.3f);
		CompostingChanceRegistry.INSTANCE.add(ANCIENT_NIGHTSHADE_FERN_SEEDS, 0.3f);
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
		CompostingChanceRegistry.INSTANCE.add(CHEESE_WHEEL, 1f);
		CompostingChanceRegistry.INSTANCE.add(CORN, 0.6f);
		CompostingChanceRegistry.INSTANCE.add(CORN_GRAINS, 0.1f);
		CompostingChanceRegistry.INSTANCE.add(CRIMSON_CORK, 0.2f);
		CompostingChanceRegistry.INSTANCE.add(DARK_SWEET_BERRIES, 0.3f);
		CompostingChanceRegistry.INSTANCE.add(DARK_MOLD, 0.2f);
		CompostingChanceRegistry.INSTANCE.add(DARK_MOLD_DUST, 0.04f);
		CompostingChanceRegistry.INSTANCE.add(DARK_OAK_CORK, 0.2f);
		CompostingChanceRegistry.INSTANCE.add(EGGPLANT, 0.65f);
		CompostingChanceRegistry.INSTANCE.add(EGGPLANT_SEEDS, 0.3f);
		CompostingChanceRegistry.INSTANCE.add(GILLYWEED, 0.5f);
		CompostingChanceRegistry.INSTANCE.add(GILLYWEED_SEEDS, 0.3f);
		CompostingChanceRegistry.INSTANCE.add(GLOWING_DARK_MOLD, 0.2f);
		CompostingChanceRegistry.INSTANCE.add(GLOWING_DARK_MOLD_DUST, 0.04f);
		CompostingChanceRegistry.INSTANCE.add(GORGONZOLA, 0.65f);
		CompostingChanceRegistry.INSTANCE.add(GORGONZOLA_FLAKES, 0.3f);
		CompostingChanceRegistry.INSTANCE.add(GORGONZOLA_WHEEL, 1f);
		CompostingChanceRegistry.INSTANCE.add(KALE, 0.65f);
		CompostingChanceRegistry.INSTANCE.add(KALE_SEEDS, 0.3f);
		CompostingChanceRegistry.INSTANCE.add(LETTUCE_LEAVES, 0.5f);
		CompostingChanceRegistry.INSTANCE.add(LETTUCE_SEEDS, 0.3f);
		CompostingChanceRegistry.INSTANCE.add(MOZZARELLA, 0.5f);
		CompostingChanceRegistry.INSTANCE.add(MOZZARELLA_FLAKES, 0.2f);
		CompostingChanceRegistry.INSTANCE.add(NIGHTLOCK_BERRIES, 0.3f);
		CompostingChanceRegistry.INSTANCE.add(NIGHTSHADE_FERN_BLUEBERRIES, 0.3f);
		CompostingChanceRegistry.INSTANCE.add(NIGHTSHADE_FERN_SEEDS, 0.3f);
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
		CompostingChanceRegistry.INSTANCE.add(PIZZA_SLICE_FOUR_CHEESE, 0.4f);
		CompostingChanceRegistry.INSTANCE.add(PIZZA_SLICE_MARGHERITA, 0.4f);
		CompostingChanceRegistry.INSTANCE.add(RED_SWEET_POTATO, 0.8f);
		CompostingChanceRegistry.INSTANCE.add(RICE, 0.3f);
		CompostingChanceRegistry.INSTANCE.add(RICE_GRAINS, 0.05f);
		CompostingChanceRegistry.INSTANCE.add(SWEET_POTATO, 0.65f);
		CompostingChanceRegistry.INSTANCE.add(SWEET_POTATO_BUDS, 0.2f);
		CompostingChanceRegistry.INSTANCE.add(THISTLE, 0.4f);
		CompostingChanceRegistry.INSTANCE.add(THISTLE_LEAVES, 0.2f);
		CompostingChanceRegistry.INSTANCE.add(THISTLE_STAMENS, 0.2f);
		CompostingChanceRegistry.INSTANCE.add(TOMATO, 0.65f);
		CompostingChanceRegistry.INSTANCE.add(TOMATO_SEEDS, 0.3f);
		CompostingChanceRegistry.INSTANCE.add(WARPED_CORK, 0.2f);

		CorkStrippableBlockRegistry.register(Blocks.CRIMSON_STEM, CRIMSON_CORK, 0.1f);
		CorkStrippableBlockRegistry.register(Blocks.CRIMSON_HYPHAE, CRIMSON_CORK, 0.1f);
		CorkStrippableBlockRegistry.register(Blocks.DARK_OAK_LOG, DARK_OAK_CORK, 0.1f);
		CorkStrippableBlockRegistry.register(Blocks.DARK_OAK_WOOD, DARK_OAK_CORK, 0.1f);
		CorkStrippableBlockRegistry.register(Blocks.OAK_LOG, OAK_CORK, 0.2f);
		CorkStrippableBlockRegistry.register(Blocks.OAK_WOOD, OAK_CORK, 0.2f);
		CorkStrippableBlockRegistry.register(Blocks.WARPED_STEM, WARPED_CORK, 0.2f);
		CorkStrippableBlockRegistry.register(Blocks.WARPED_HYPHAE, WARPED_CORK, 0.2f);

		MilkCauldronBlock.registerBehaviors();
		ContainerBlockData.load();
	}

	/**
	 * Loads all the client side things related to the items.
	 */
	@Environment(EnvType.CLIENT)
	public static void loadClient() {
		ColorProviderRegistry.ITEM.register((ItemColorProvider) ANCIENT_NIGHTSHADE_FERN_SEEDS, ANCIENT_NIGHTSHADE_FERN_SEEDS);
		ColorProviderRegistry.ITEM.register((ItemColorProvider) ANCIENT_GILLYWEED_SEEDS, ANCIENT_GILLYWEED_SEEDS);
		ColorProviderRegistry.ITEM.register((ItemColorProvider) CRIMSON_CORK_PLUG, CRIMSON_CORK_PLUG);
		ColorProviderRegistry.ITEM.register((ItemColorProvider) DARK_OAK_CORK_PLUG, DARK_OAK_CORK_PLUG);
		ColorProviderRegistry.ITEM.register((ItemColorProvider) OAK_CORK_PLUG, OAK_CORK_PLUG);
		ColorProviderRegistry.ITEM.register((ItemColorProvider) TOMATO_SAUCE_BOTTLE, TOMATO_SAUCE_BOTTLE);
		ColorProviderRegistry.ITEM.register((ItemColorProvider) WARPED_CORK_PLUG, WARPED_CORK_PLUG);
		ColorProviderRegistry.ITEM.register((ItemColorProvider) CLOSED_JAR_EMPTY, CLOSED_JAR_EMPTY);
		ColorProviderRegistry.ITEM.register((ItemColorProvider) CLOSED_JAR_FILLED, CLOSED_JAR_FILLED);
	}

	/**
	 * Registers the specified item with the specified id.
	 */
	private static <T extends Item> T register(String id, T item) {
		return Registry.register(Registries.ITEM, Terravibe.identified(id), item);
	}

	static {
		TERRAVIBE = register("terravibe", new Item(TerravibeItemSettings.terravibe()));

		//Objects
		JAR = register("jar", new AliasedBlockItem(TerravibeBlocks.JAR, TerravibeItemSettings.jar()));
		CLOSED_JAR_EMPTY = register("closed_jar_empty", new ClosedJarItem(TerravibeItemSettings.closed_jar_empty()));
		CLOSED_JAR_FILLED = register("closed_jar_filled", new ClosedJarItem(TerravibeItemSettings.closed_jar_filled()));
		MORTAR = register("mortar", new AliasedBlockItem(TerravibeBlocks.MORTAR, TerravibeItemSettings.mortar()));
		SHREDDER = register("shredder", new AliasedBlockItem(TerravibeBlocks.SHREDDER, TerravibeItemSettings.shredder()));
		TRAY = register("tray", new AliasedBlockItem(TerravibeBlocks.TRAY, TerravibeItemSettings.tray()));
		TUN = register("tun", new AliasedBlockItem(TerravibeBlocks.TUN, TerravibeItemSettings.tun()));

		//Items dropped from plants
		BASIL = register("basil", new AliasedBlockItem(TerravibeBlocks.BASIL_HERB, TerravibeItemSettings.basil()));
		BEANS = register("beans", new AliasedBlockItem(TerravibeBlocks.BEANS_CROP, TerravibeItemSettings.beans()));
		BIRCH_MOLD = register("birch_mold", new Item(TerravibeItemSettings.birch_mold()));
		DARK_MOLD = register("dark_mold", new Item(TerravibeItemSettings.dark_mold()));
		GLOWING_DARK_MOLD = register("glowing_dark_mold", new Item(TerravibeItemSettings.glowing_dark_mold()));
		CORN = register("corn", new Item(TerravibeItemSettings.corn()));
		CORN_GRAINS = register("corn_grains", new AliasedBlockItem(TerravibeBlocks.CORN_CROP, TerravibeItemSettings.corn_grains()));
		DARK_SWEET_BERRIES = register("dark_sweet_berries", new AliasedBlockItem(TerravibeBlocks.DARK_SWEET_BERRY_BUSH, TerravibeItemSettings.dark_sweet_berries()));
		NIGHTLOCK_BERRIES = register("nightlock_berries", new NightlockBerriesItem(TerravibeBlocks.NIGHTLOCK_BERRY_BUSH, TerravibeItemSettings.nightlock_berries()));
		EGGPLANT = register("eggplant", new Item(TerravibeItemSettings.eggplant()));
		EGGPLANT_SEEDS = register("eggplant_seeds", new AliasedBlockItem(TerravibeBlocks.EGGPLANT_CROP, TerravibeItemSettings.eggplant_seeds()));
		KALE = register("kale", new Item(TerravibeItemSettings.kale()));
		KALE_SEEDS = register("kale_seeds", new AliasedBlockItem(TerravibeBlocks.KALE_CROP, TerravibeItemSettings.kale_seeds()));
		LETTUCE_LEAVES = register("lettuce_leaves", new Item(TerravibeItemSettings.lettuce_leaves()));
		LETTUCE_SEEDS = register("lettuce_seeds", new AliasedBlockItem(TerravibeBlocks.LETTUCE_CROP, TerravibeItemSettings.lettuce_seeds()));
		ONION = register("onion", new Item(TerravibeItemSettings.onion()));
		ONION_SEEDS = register("onion_seeds", new AliasedBlockItem(TerravibeBlocks.ONION_CROP, TerravibeItemSettings.onion_seeds()));
		RICE = register("rice", new Item(TerravibeItemSettings.rice()));
		RICE_GRAINS = register("rice_grains", new AliasedBlockItem(TerravibeBlocks.RICE_CROP, TerravibeItemSettings.rice_grains()));
		SWEET_POTATO = register("sweet_potato", new AliasedBlockItem(TerravibeBlocks.SWEET_POTATO_CROP, TerravibeItemSettings.sweet_potato()));
		SWEET_POTATO_BUDS = register("sweet_potato_buds", new DuplicatedAliasedBlockItem(TerravibeBlocks.SWEET_POTATO_CROP, TerravibeItemSettings.sweet_potato_buds()));
		RED_SWEET_POTATO = register("red_sweet_potato", new Item(TerravibeItemSettings.red_sweet_potato()));
		THISTLE = register("thistle", new AliasedBlockItem(TerravibeBlocks.THISTLE_PLANT, TerravibeItemSettings.thistle()));
		TOMATO = register("tomato", new Item(TerravibeItemSettings.tomato()));
		TOMATO_SEEDS = register("tomato_seeds", new AliasedBlockItem(TerravibeBlocks.TOMATO_CROP, TerravibeItemSettings.tomato_seeds()));

		//Ancient plants with their seeds and drops
		GILLYWEED = register("gillyweed", new SideEffectFoodItem(TerravibeItemSettings.gillyweed()));
		GILLYWEED_SEEDS = register("gillyweed_seeds", new AliasedBlockItem(TerravibeBlocks.GILLYWEED_ALGA, TerravibeItemSettings.gillyweed_seeds()));
		ANCIENT_GILLYWEED_SEEDS = register("ancient_gillyweed_seeds", new AncientSeedItem(TerravibeItemSettings.ancient_gillyweed_seeds()));
		NIGHTSHADE_FERN_BLUEBERRIES = register("nightshade_fern_blueberries", new SideEffectFoodItem(TerravibeItemSettings.nightshade_fern_blueberries()));
		NIGHTSHADE_FERN_SEEDS = register("nightshade_fern_seeds", new AliasedBlockItem(TerravibeBlocks.NIGHTSHADE_FERN, TerravibeItemSettings.nightshade_fern_seeds()));
		ANCIENT_NIGHTSHADE_FERN_SEEDS = register("ancient_nightshade_fern_seeds", new AncientSeedItem(TerravibeItemSettings.ancient_nightshade_fern_seeds()));

		//Items dropped from trees
		CRIMSON_CORK = register("crimson_cork", new Item(TerravibeItemSettings.crimson_cork()));
		DARK_OAK_CORK = register("dark_oak_cork", new Item(TerravibeItemSettings.dark_oak_cork()));
		OAK_CORK = register("oak_cork", new Item(TerravibeItemSettings.oak_cork()));
		WARPED_CORK = register("warped_cork", new Item(TerravibeItemSettings.warped_cork()));
		OLIVES = register("olives", new Item(TerravibeItemSettings.olives()));
		PRICKLY_PEAR = register("prickly_pear", new Item(TerravibeItemSettings.prickly_pear()));

		//Tree blocks
		OPUNTIA = register("opuntia", new AliasedBlockItem(TerravibeBlocks.OPUNTIA, TerravibeItemSettings.opuntia()));
		FLOWERING_OPUNTIA = register("flowering_opuntia", new AliasedBlockItem(TerravibeBlocks.FLOWERING_OPUNTIA, TerravibeItemSettings.flowering_opuntia()));

		//Food crafted items
		BAKED_SWEET_POTATO = register("baked_sweet_potato", new Item(TerravibeItemSettings.baked_sweet_potato()));
		CHEESE = register("cheese", new Item(TerravibeItemSettings.cheese()));
		CHEESE_FLAKES = register("cheese_flakes", new Item(TerravibeItemSettings.cheese_flakes()));
		GORGONZOLA = register("gorgonzola", new Item(TerravibeItemSettings.gorgonzola()));
		GORGONZOLA_FLAKES = register("gorgonzola_flakes", new Item(TerravibeItemSettings.gorgonzola_flakes()));
		MOZZARELLA = register("mozzarella", new Item(TerravibeItemSettings.mozzarella()));
		MOZZARELLA_FLAKES = register("mozzarella_flakes", new Item(TerravibeItemSettings.mozzarella_flakes()));
		PIZZA_SLICE_FOUR_CHEESE = register("pizza_slice_four_cheese", new Item(TerravibeItemSettings.pizza_slice_four_cheese()));
		PIZZA_SLICE_MARGHERITA = register("pizza_slice_margherita", new Item(TerravibeItemSettings.pizza_slice_margherita()));
		POTTAGE = register("pottage", new StewItem(TerravibeItemSettings.pottage()));
		SALAD_SIMPLE = register("salad_simple", new StewItem(TerravibeItemSettings.salad_simple()));
		SALAD = register("salad", new StewItem(TerravibeItemSettings.salad()));
		SALAD_MIXED = register("salad_mixed", new StewItem(TerravibeItemSettings.salad_mixed()));
		SALAD_RICH = register("salad_rich", new StewItem(TerravibeItemSettings.salad_rich()));
		SALAD_FULL = register("salad_full", new StewItem(TerravibeItemSettings.salad_full()));
		THISTLE_LEAVES = register("thistle_leaves", new Item(TerravibeItemSettings.thistle_leaves()));

		//Non-food crafted items
		BIRCH_MOLD_DUST = register("birch_mold_dust", new AliasedBlockItem(TerravibeBlocks.BIRCH_MOLD, TerravibeItemSettings.birch_mold_dust()));
		DARK_MOLD_DUST = register("dark_mold_dust", new AliasedBlockItem(TerravibeBlocks.DARK_MOLD, TerravibeItemSettings.dark_mold_dust()));
		GLOWING_DARK_MOLD_DUST = register("glowing_dark_mold_dust", new AliasedBlockItem(TerravibeBlocks.GLOWING_DARK_MOLD, TerravibeItemSettings.glowing_dark_mold_dust()));
		BURNED_BIRCH_MOLD_DUST = register("burned_birch_mold_dust", new Item(TerravibeItemSettings.burned_birch_mold_dust()));
		BURNED_DARK_MOLD_DUST = register("burned_dark_mold_dust", new Item(TerravibeItemSettings.burned_dark_mold_dust()));
		BURNED_GLOWING_DARK_MOLD_DUST = register("burned_glowing_dark_mold_dust", new Item(TerravibeItemSettings.burned_glowing_dark_mold_dust()));
		CHEESE_WHEEL = register("cheese_wheel", new AliasedBlockItem(TerravibeBlocks.CHEESE_WHEEL, TerravibeItemSettings.cheese_wheel()));
		GORGONZOLA_WHEEL = register("gorgonzola_wheel", new AliasedBlockItem(TerravibeBlocks.GORGONZOLA_WHEEL, TerravibeItemSettings.gorgonzola_wheel()));
		PIZZA_FOUR_CHEESE = register("pizza_four_cheese", new AliasedBlockItem(TerravibeBlocks.PIZZA_FOUR_CHEESE, TerravibeItemSettings.pizza_four_cheese()));
		PIZZA_MARGHERITA = register("pizza_margherita", new AliasedBlockItem(TerravibeBlocks.PIZZA_MARGHERITA, TerravibeItemSettings.pizza_margherita()));
		CRIMSON_CORK_PLUG = register("crimson_cork_plug", new ColoredItem(TerravibeItemSettings.crimson_cork_plug()));
		DARK_OAK_CORK_PLUG = register("dark_oak_cork_plug", new ColoredItem(TerravibeItemSettings.dark_oak_cork_plug()));
		OAK_CORK_PLUG = register("oak_cork_plug", new ColoredItem(TerravibeItemSettings.oak_cork_plug()));
		WARPED_CORK_PLUG = register("warped_cork_plug", new ColoredItem(TerravibeItemSettings.warped_cork_plug()));
		SALT = register("salt", new Item(TerravibeItemSettings.salt()));
		SALT_CRYSTALS = register("salt_crystals", new Item(TerravibeItemSettings.salt_crystals()));
		THISTLE_STAMENS = register("thistle_stamens", new Item(TerravibeItemSettings.thistle_stamens()));

		//Bottles
		TOMATO_SAUCE_BOTTLE = register("tomato_sauce_bottle", new ColoredItem(TerravibeItemSettings.tomato_sauce_bottle()));

		//Terrain blocks
		EXCAVATED_MUD = register("excavated_mud", new AliasedBlockItem(TerravibeBlocks.EXCAVATED_MUD, TerravibeItemSettings.excavated_mud()));
	}
}
