package xyz.devpelux.terravibe.item;

import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import xyz.devpelux.terravibe.block.TerravibeBlocks;
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
	 * Item of the shredder block.
	 */
	public static final Item SHREDDER;

	/**
	 * Item of the tray block.
	 */
	public static final Item TRAY;

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
	public static final Item GILLYWEED_SEEDS_ANCIENT;

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
	public static final Item NIGHTSHADE_FERN_SEEDS_ANCIENT;

	//Items dropped from trees

	/**
	 * Material obtained from stripping some logs (light variant).
	 */
	public static final Item CORK;

	/**
	 * Material obtained from stripping some logs (dark variant).
	 */
	public static final Item DARK_CORK;

	/**
	 * Fruit used in juices and to make cheese.
	 */
	public static final Item LEMON;

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
	 * Cheese with mold.
	 */
	public static final Item GORGONZOLA;

	/**
	 * Mozzarella.
	 */
	public static final Item MOZZARELLA;

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
	 * Plug obtained from cork.
	 */
	public static final Item CORK_PLUG;

	/**
	 * Plug obtained from dark cork.
	 */
	public static final Item DARK_CORK_PLUG;

	/**
	 * Salt.
	 */
	public static final Item SALT;

	/**
	 * Salt crystals.
	 */
	public static final Item SALT_CRYSTALS;

	//Bottles

	/**
	 * Bottle of lemon juice.
	 */
	public static final Item LEMON_JUICE;

	/**
	 * Bottle of oil.
	 */
	public static final Item OIL;

	/**
	 * Bottle of tomato sauce.
	 */
	public static final Item TOMATO_SAUCE;

	//Terrain blocks

	/**
	 * Item of the excavated mud block.
	 */
	public static final Item EXCAVATED_MUD;

	/**
	 * Registers the specified item with the specified id.
	 */
	private static <T extends Item> T register(String id, T item) {
		return Registry.register(Registries.ITEM, Terravibe.identified(id), item);
	}

	static {
		TERRAVIBE = register("terravibe", new Item(TerravibeItemSettings.terravibe()));

		BAKED_SWEET_POTATO = register("baked_sweet_potato", new Item(TerravibeItemSettings.baked_sweet_potato()));
		BASIL = register("basil", new AliasedBlockItem(TerravibeBlocks.BASIL_HERB, TerravibeItemSettings.basil()));
		BEANS = register("beans", new AliasedBlockItem(TerravibeBlocks.BEANS_CROP, TerravibeItemSettings.beans()));
		BIRCH_MOLD = register("birch_mold", new Item(TerravibeItemSettings.birch_mold()));
		BIRCH_MOLD_DUST = register("birch_mold_dust", new AliasedBlockItem(TerravibeBlocks.BIRCH_MOLD, TerravibeItemSettings.birch_mold_dust()));
		BURNED_BIRCH_MOLD_DUST = register("burned_birch_mold_dust", new Item(TerravibeItemSettings.burned_birch_mold_dust()));
		BURNED_DARK_MOLD_DUST = register("burned_dark_mold_dust", new Item(TerravibeItemSettings.burned_dark_mold_dust()));
		BURNED_GLOWING_DARK_MOLD_DUST = register("burned_glowing_dark_mold_dust", new Item(TerravibeItemSettings.burned_glowing_dark_mold_dust()));
		CHEESE = register("cheese", new Item(TerravibeItemSettings.cheese()));
		CHEESE_WHEEL = register("cheese_wheel", new AliasedBlockItem(TerravibeBlocks.CHEESE_WHEEL, TerravibeItemSettings.cheese_wheel()));
		CORN = register("corn", new Item(TerravibeItemSettings.corn()));
		CORN_GRAINS = register("corn_grains", new AliasedBlockItem(TerravibeBlocks.CORN_CROP, TerravibeItemSettings.corn_grains()));
		CORK = register("cork", new Item(TerravibeItemSettings.cork()));
		CORK_PLUG = register("cork_plug", new Item(TerravibeItemSettings.cork_plug()));
		DARK_CORK = register("dark_cork", new Item(TerravibeItemSettings.dark_cork()));
		DARK_CORK_PLUG = register("dark_cork_plug", new Item(TerravibeItemSettings.dark_cork_plug()));
		DARK_MOLD = register("dark_mold", new Item(TerravibeItemSettings.dark_mold()));
		DARK_MOLD_DUST = register("dark_mold_dust", new AliasedBlockItem(TerravibeBlocks.DARK_MOLD, TerravibeItemSettings.dark_mold_dust()));
		DARK_SWEET_BERRIES = register("dark_sweet_berries", new AliasedBlockItem(TerravibeBlocks.DARK_SWEET_BERRY_BUSH, TerravibeItemSettings.dark_sweet_berries()));
		EGGPLANT = register("eggplant", new Item(TerravibeItemSettings.eggplant()));
		EGGPLANT_SEEDS = register("eggplant_seeds", new AliasedBlockItem(TerravibeBlocks.EGGPLANT_CROP, TerravibeItemSettings.eggplant_seeds()));
		EXCAVATED_MUD = register("excavated_mud", new AliasedBlockItem(TerravibeBlocks.EXCAVATED_MUD, TerravibeItemSettings.excavated_mud()));
		FLOWERING_OPUNTIA = register("flowering_opuntia", new AliasedBlockItem(TerravibeBlocks.FLOWERING_OPUNTIA, TerravibeItemSettings.flowering_opuntia()));
		GILLYWEED = register("gillyweed", new SideEffectFoodItem(TerravibeItemSettings.gillyweed(), TerravibeFoodEffects.GILLYWEED_SIDE_EFFECTS));
		GILLYWEED_SEEDS = register("gillyweed_seeds", new AliasedBlockItem(TerravibeBlocks.GILLYWEED_ALGA, TerravibeItemSettings.gillyweed_seeds()));
		GILLYWEED_SEEDS_ANCIENT = register("gillyweed_seeds_ancient", new AncientSeedItem(TerravibeItemSettings.ancient_gillyweed_seeds(), 5));
		GLOWING_DARK_MOLD = register("glowing_dark_mold", new Item(TerravibeItemSettings.glowing_dark_mold()));
		GLOWING_DARK_MOLD_DUST = register("glowing_dark_mold_dust", new AliasedBlockItem(TerravibeBlocks.GLOWING_DARK_MOLD, TerravibeItemSettings.glowing_dark_mold_dust()));
		GORGONZOLA = register("gorgonzola", new Item(TerravibeItemSettings.gorgonzola()));
		GORGONZOLA_WHEEL = register("gorgonzola_wheel", new AliasedBlockItem(TerravibeBlocks.GORGONZOLA_WHEEL, TerravibeItemSettings.gorgonzola_wheel()));
		KALE = register("kale", new Item(TerravibeItemSettings.kale()));
		KALE_SEEDS = register("kale_seeds", new AliasedBlockItem(TerravibeBlocks.KALE_CROP, TerravibeItemSettings.kale_seeds()));
		LEMON = register("lemon", new Item(TerravibeItemSettings.lemon()));
		LEMON_JUICE = register("lemon_juice", new JuiceItem(TerravibeItemSettings.lemon_juice()));
		LETTUCE_LEAVES = register("lettuce_leaves", new Item(TerravibeItemSettings.lettuce_leaves()));
		LETTUCE_SEEDS = register("lettuce_seeds", new AliasedBlockItem(TerravibeBlocks.LETTUCE_CROP, TerravibeItemSettings.lettuce_seeds()));
		MOZZARELLA = register("mozzarella", new Item(TerravibeItemSettings.mozzarella()));
		NIGHTLOCK_BERRIES = register("nightlock_berries", new NightlockBerriesItem(TerravibeBlocks.NIGHTLOCK_BERRY_BUSH, TerravibeItemSettings.nightlock_berries()));
		NIGHTSHADE_FERN_BLUEBERRIES = register("nightshade_fern_blueberries", new SideEffectFoodItem(TerravibeItemSettings.nightshade_fern_blueberries(), TerravibeFoodEffects.NIGHTSHADE_FERN_BLUEBERRIES_SIDE_EFFECTS));
		NIGHTSHADE_FERN_SEEDS = register("nightshade_fern_seeds", new AliasedBlockItem(TerravibeBlocks.NIGHTSHADE_FERN, TerravibeItemSettings.nightshade_fern_seeds()));
		NIGHTSHADE_FERN_SEEDS_ANCIENT = register("nightshade_fern_seeds_ancient", new AncientSeedItem(TerravibeItemSettings.ancient_nightshade_fern_seeds(), 3));
		OIL = register("oil", new Item(TerravibeItemSettings.oil()));
		OLIVES = register("olives", new Item(TerravibeItemSettings.olives()));
		ONION = register("onion", new Item(TerravibeItemSettings.onion()));
		ONION_SEEDS = register("onion_seeds", new AliasedBlockItem(TerravibeBlocks.ONION_CROP, TerravibeItemSettings.onion_seeds()));
		OPUNTIA = register("opuntia", new AliasedBlockItem(TerravibeBlocks.OPUNTIA, TerravibeItemSettings.opuntia()));
		PIZZA_FOUR_CHEESE = register("pizza_four_cheese", new AliasedBlockItem(TerravibeBlocks.PIZZA_FOUR_CHEESE, TerravibeItemSettings.pizza_four_cheese()));
		PIZZA_MARGHERITA = register("pizza_margherita", new AliasedBlockItem(TerravibeBlocks.PIZZA_MARGHERITA, TerravibeItemSettings.pizza_margherita()));
		PIZZA_SLICE_FOUR_CHEESE = register("pizza_slice_four_cheese", new Item(TerravibeItemSettings.pizza_slice_four_cheese()));
		PIZZA_SLICE_MARGHERITA = register("pizza_slice_margherita", new Item(TerravibeItemSettings.pizza_slice_margherita()));
		POTTAGE = register("pottage", new StewItem(TerravibeItemSettings.pottage()));
		PRICKLY_PEAR = register("prickly_pear", new Item(TerravibeItemSettings.prickly_pear()));
		RED_SWEET_POTATO = register("red_sweet_potato", new Item(TerravibeItemSettings.red_sweet_potato()));
		RICE = register("rice", new Item(TerravibeItemSettings.rice()));
		RICE_GRAINS = register("rice_grains", new AliasedBlockItem(TerravibeBlocks.RICE_CROP, TerravibeItemSettings.rice_grains()));
		SALAD = register("salad", new StewItem(TerravibeItemSettings.salad()));
		SALAD_FULL = register("salad_full", new StewItem(TerravibeItemSettings.salad_full()));
		SALAD_MIXED = register("salad_mixed", new StewItem(TerravibeItemSettings.salad_mixed()));
		SALAD_RICH = register("salad_rich", new StewItem(TerravibeItemSettings.salad_rich()));
		SALAD_SIMPLE = register("salad_simple", new StewItem(TerravibeItemSettings.salad_simple()));
		SALT = register("salt", new Item(TerravibeItemSettings.salt()));
		SALT_CRYSTALS = register("salt_crystals", new Item(TerravibeItemSettings.salt_crystals()));
		SHREDDER = register("shredder", new AliasedBlockItem(TerravibeBlocks.SHREDDER, TerravibeItemSettings.shredder()));
		SWEET_POTATO = register("sweet_potato", new AliasedBlockItem(TerravibeBlocks.SWEET_POTATO_CROP, TerravibeItemSettings.sweet_potato()));
		SWEET_POTATO_BUDS = register("sweet_potato_buds", new DuplicatedAliasedBlockItem(TerravibeBlocks.SWEET_POTATO_CROP, TerravibeItemSettings.sweet_potato_buds()));
		TOMATO = register("tomato", new Item(TerravibeItemSettings.tomato()));
		TOMATO_SAUCE = register("tomato_sauce", new Item(TerravibeItemSettings.tomato_sauce()));
		TOMATO_SEEDS = register("tomato_seeds", new AliasedBlockItem(TerravibeBlocks.TOMATO_CROP, TerravibeItemSettings.tomato_seeds()));
		TRAY = register("tray", new AliasedBlockItem(TerravibeBlocks.TRAY, TerravibeItemSettings.tray()));
	}
}
