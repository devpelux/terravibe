package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;

@SuppressWarnings("UnstableApiUsage")
public final class TerravibeItemInventory {
	private TerravibeItemInventory() { }

	/**
	 * Registers all item group modifiers.
	 */
	public static void registerItemGroupModifiers() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(TerravibeItemInventory::addFoodAndDrinks);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(TerravibeItemInventory::addFunctionals);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(TerravibeItemInventory::addIngredients);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(TerravibeItemInventory::addNaturals);
	}

	/**
	 * Adds items to the food and drinks tab.
	 */
	private static void addFoodAndDrinks(FabricItemGroupEntries entries) {
		//Berries
		entries.addAfter(Items.GLOW_BERRIES,
				TerravibeItems.DARK_SWEET_BERRIES,
				TerravibeItems.NIGHTLOCK_BERRIES,
				TerravibeItems.NIGHTSHADE_FERN_BLUEBERRIES);

		//Fruits
		entries.addBefore(Items.CHORUS_FRUIT,
				TerravibeItems.PRICKLY_PEAR);

		//Sweet potato buds
		entries.addAfter(Items.POISONOUS_POTATO,
				TerravibeItems.SWEET_POTATO_BUDS);

		//Vegetables and derivatives
		entries.addAfter(Items.BEETROOT,
				TerravibeItems.BEANS,
				TerravibeItems.EGGPLANT,
				TerravibeItems.KALE,
				TerravibeItems.LETTUCE_LEAVES,
				TerravibeItems.ONION,
				TerravibeItems.SWEET_POTATO,
				TerravibeItems.BAKED_SWEET_POTATO,
				TerravibeItems.RED_SWEET_POTATO,
				TerravibeItems.TOMATO,
				TerravibeItems.THISTLE_LEAVES);

		//Algae
		entries.addBefore(Items.DRIED_KELP,
				TerravibeItems.GILLYWEED);

		//Milk derivatives
		entries.addAfter(Items.PUMPKIN_PIE,
				TerravibeItems.MOZZARELLA,
				TerravibeItems.MOZZARELLA_FLAKES,
				TerravibeItems.CHEESE_WHEEL,
				TerravibeItems.CHEESE,
				TerravibeItems.CHEESE_FLAKES,
				TerravibeItems.GORGONZOLA_WHEEL,
				TerravibeItems.GORGONZOLA,
				TerravibeItems.GORGONZOLA_FLAKES);

		//Pizzas
		entries.addAfter(Items.PUMPKIN_PIE,
				TerravibeItems.PIZZA_MARGHERITA,
				TerravibeItems.PIZZA_SLICE_MARGHERITA,
				TerravibeItems.PIZZA_FOUR_CHEESE,
				TerravibeItems.PIZZA_SLICE_FOUR_CHEESE);

		//Bottles
		entries.addAfter(Items.SPIDER_EYE,
				TerravibeItems.TOMATO_SAUCE_BOTTLE);

		//Salads
		entries.addBefore(Items.MUSHROOM_STEW,
				TerravibeItems.SALAD_SIMPLE,
				TerravibeItems.SALAD,
				TerravibeItems.SALAD_MIXED,
				TerravibeItems.SALAD_RICH,
				TerravibeItems.SALAD_FULL,
				TerravibeItems.POTTAGE);
	}

	/**
	 * Adds items to the functionals tab.
	 */
	private static void addFunctionals(FabricItemGroupEntries entries) {
		//Functionals
		entries.addAfter(Items.LOOM,
				TerravibeItems.SHREDDER,
				TerravibeItems.TRAY);

		//Containers and plugs
		entries.addAfter(Items.PINK_SHULKER_BOX,
				TerravibeItems.JAR,
				TerravibeItems.OAK_CORK_PLUG,
				TerravibeItems.DARK_OAK_CORK_PLUG,
				TerravibeItems.CRIMSON_CORK_PLUG,
				TerravibeItems.WARPED_CORK_PLUG,
				TerravibeItems.TUN);
	}

	/**
	 * Adds items to the ingredients tab.
	 */
	private static void addIngredients(FabricItemGroupEntries entries) {
		//Food ingredients
		entries.addAfter(Items.EGG,
				TerravibeItems.SALT_CRYSTALS,
				TerravibeItems.SALT,
				TerravibeItems.THISTLE_STAMENS,
				TerravibeItems.BIRCH_MOLD_DUST,
				TerravibeItems.DARK_MOLD_DUST);
	}

	/**
	 * Adds items to the naturals tab.
	 */
	private static void addNaturals(FabricItemGroupEntries entries) {
		//Excavated mud
		entries.addAfter(Items.MUD,
				TerravibeItems.EXCAVATED_MUD);

		//Corks
		entries.addAfter(Items.SHROOMLIGHT,
				TerravibeItems.OAK_CORK,
				TerravibeItems.DARK_OAK_CORK,
				TerravibeItems.CRIMSON_CORK,
				TerravibeItems.WARPED_CORK);

		//Flowers
		entries.addAfter(Items.LILY_OF_THE_VALLEY,
				TerravibeItems.THISTLE);

		//Plantable leaves
		entries.addAfter(Items.SUGAR_CANE,
				TerravibeItems.BASIL);

		//Cactus
		entries.addAfter(Items.CACTUS,
				TerravibeItems.OPUNTIA,
				TerravibeItems.FLOWERING_OPUNTIA);

		//Molds and derivatives
		entries.addAfter(Items.LARGE_FERN,
				TerravibeItems.BIRCH_MOLD,
				TerravibeItems.BIRCH_MOLD_DUST,
				TerravibeItems.BURNED_BIRCH_MOLD_DUST,
				TerravibeItems.DARK_MOLD,
				TerravibeItems.DARK_MOLD_DUST,
				TerravibeItems.BURNED_DARK_MOLD_DUST,
				TerravibeItems.GLOWING_DARK_MOLD,
				TerravibeItems.GLOWING_DARK_MOLD_DUST,
				TerravibeItems.BURNED_GLOWING_DARK_MOLD_DUST);

		//Seeds
		entries.addAfter(Items.BEETROOT_SEEDS,
				TerravibeItems.CORN_GRAINS,
				TerravibeItems.EGGPLANT_SEEDS,
				TerravibeItems.KALE_SEEDS,
				TerravibeItems.LETTUCE_SEEDS,
				TerravibeItems.ONION_SEEDS,
				TerravibeItems.RICE_GRAINS,
				TerravibeItems.TOMATO_SEEDS,
				TerravibeItems.GILLYWEED_SEEDS,
				TerravibeItems.NIGHTSHADE_FERN_SEEDS);

		//Berries
		entries.addAfter(Items.SWEET_BERRIES,
				TerravibeItems.DARK_SWEET_BERRIES,
				TerravibeItems.NIGHTLOCK_BERRIES,
				TerravibeItems.NIGHTSHADE_FERN_BLUEBERRIES);

		//Non-plantable and non-consumable plant drops
		entries.addAfter(Items.NETHER_WART,
				TerravibeItems.CORN,
				TerravibeItems.RICE,
				TerravibeItems.OLIVES);
	}
}
