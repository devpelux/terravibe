package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Items;

/**
 * List of all the item settings generators.
 */
public final class TerravibeItemSettings {
	private TerravibeItemSettings() {
	}

	public static FabricItemSettings baked_sweet_potato() {
		return stackOf(64, true).food(TerravibeFoodComponents.BAKED_SWEET_POTATO);
	}

	public static FabricItemSettings basil() {
		return stackOf(64, true);
	}

	public static FabricItemSettings beans() {
		return stackOf(64, true).food(TerravibeFoodComponents.BEANS);
	}

	public static FabricItemSettings birch_mold() {
		return stackOf(64, true);
	}

	public static FabricItemSettings birch_mold_dust() {
		return stackOf(64, true);
	}

	public static FabricItemSettings burned_birch_mold_dust() {
		return stackOf(64, true);
	}

	public static FabricItemSettings burned_dark_mold_dust() {
		return stackOf(64, true);
	}

	public static FabricItemSettings burned_glowing_dark_mold_dust() {
		return stackOf(64, true);
	}

	public static FabricItemSettings cheese() {
		return stackOf(64, true).food(TerravibeFoodComponents.CHEESE);
	}

	public static FabricItemSettings cheese_flakes() {
		return stackOf(64, true).food(TerravibeFoodComponents.CHEESE_FLAKES);
	}

	public static FabricItemSettings cheese_wheel() {
		return stackOf(16, true);
	}

	public static FabricItemSettings closed_jar_empty() {
		return stackOf(16, false);
	}

	public static FabricItemSettings closed_jar_filled() {
		return stackOf(16, false);
	}

	public static FabricItemSettings corn() {
		return stackOf(64, true);
	}

	public static FabricItemSettings corn_grains() {
		return stackOf(64, true);
	}

	public static FabricItemSettings crimson_cork() {
		return stackOf(64, true);
	}

	public static FabricItemSettings crimson_cork_plug() {
		return stackOf(64, true);
	}

	public static FabricItemSettings dark_mold() {
		return stackOf(64, true);
	}

	public static FabricItemSettings dark_mold_dust() {
		return stackOf(64, true);
	}

	public static FabricItemSettings dark_oak_cork() {
		return stackOf(64, true);
	}

	public static FabricItemSettings dark_oak_cork_plug() {
		return stackOf(64, true);
	}

	public static FabricItemSettings dark_sweet_berries() {
		return stackOf(64, true).food(TerravibeFoodComponents.DARK_SWEET_BERRIES);
	}

	public static FabricItemSettings eggplant() {
		return stackOf(64, true).food(TerravibeFoodComponents.EGGPLANT);
	}

	public static FabricItemSettings eggplant_seeds() {
		return stackOf(64, true);
	}

	public static FabricItemSettings flooded_mud() {
		return stackOf(64, true);
	}

	public static FabricItemSettings flowering_opuntia() {
		return stackOf(64, true);
	}

	public static FabricItemSettings glowing_dark_mold() {
		return stackOf(64, true);
	}

	public static FabricItemSettings glowing_dark_mold_dust() {
		return stackOf(64, true);
	}

	public static FabricItemSettings gorgonzola() {
		return stackOf(64, true).food(TerravibeFoodComponents.GORGONZOLA);
	}

	public static FabricItemSettings gorgonzola_flakes() {
		return stackOf(64, true).food(TerravibeFoodComponents.GORGONZOLA_FLAKES);
	}

	public static FabricItemSettings gorgonzola_wheel() {
		return stackOf(16, true);
	}

	public static FabricItemSettings jar() {
		return stackOf(64, true);
	}

	public static FabricItemSettings kale() {
		return stackOf(64, true).food(TerravibeFoodComponents.KALE);
	}

	public static FabricItemSettings kale_seeds() {
		return stackOf(64, true);
	}

	public static FabricItemSettings lettuce_leaves() {
		return stackOf(64, true).food(TerravibeFoodComponents.LETTUCE_LEAVES);
	}

	public static FabricItemSettings lettuce_seeds() {
		return stackOf(64, true);
	}

	public static FabricItemSettings mortar() {
		return stackOf(64, true);
	}

	public static FabricItemSettings mozzarella() {
		return stackOf(64, true).food(TerravibeFoodComponents.MOZZARELLA);
	}

	public static FabricItemSettings mozzarella_flakes() {
		return stackOf(64, true).food(TerravibeFoodComponents.MOZZARELLA_FLAKES);
	}

	public static FabricItemSettings nightlock_berries() {
		return stackOf(64, true).food(TerravibeFoodComponents.NIGHTLOCK_BERRIES);
	}

	public static FabricItemSettings oak_cork() {
		return stackOf(64, true);
	}

	public static FabricItemSettings oak_cork_plug() {
		return stackOf(64, true);
	}

	public static FabricItemSettings olives() {
		return stackOf(64, true);
	}

	public static FabricItemSettings onion() {
		return stackOf(64, true).food(TerravibeFoodComponents.ONION);
	}

	public static FabricItemSettings onion_seeds() {
		return stackOf(64, true);
	}

	public static FabricItemSettings opuntia() {
		return stackOf(64, true);
	}

	public static FabricItemSettings pizza_four_cheese() {
		return stackOf(1, true);
	}

	public static FabricItemSettings pizza_margherita() {
		return stackOf(1, true);
	}

	public static FabricItemSettings pizza_slice_four_cheese() {
		return stackOf(4, true).food(TerravibeFoodComponents.PIZZA_SLICE_FOUR_CHEESE);
	}

	public static FabricItemSettings pizza_slice_margherita() {
		return stackOf(4, true).food(TerravibeFoodComponents.PIZZA_SLICE_MARGHERITA);
	}

	public static FabricItemSettings pottage() {
		return stackOf(16, true).food(TerravibeFoodComponents.POTTAGE);
	}

	public static FabricItemSettings prickly_pear() {
		return stackOf(64, true).food(TerravibeFoodComponents.PRICKLY_PEAR);
	}

	public static FabricItemSettings red_sweet_potato() {
		return stackOf(64, true).food(TerravibeFoodComponents.RED_SWEET_POTATO);
	}

	public static FabricItemSettings rice() {
		return stackOf(64, true);
	}

	public static FabricItemSettings rice_grains() {
		return stackOf(64, true);
	}

	public static FabricItemSettings salad() {
		return stackOf(16, true).food(TerravibeFoodComponents.SALAD);
	}

	public static FabricItemSettings salad_full() {
		return stackOf(16, true).food(TerravibeFoodComponents.SALAD_FULL);
	}

	public static FabricItemSettings salad_mixed() {
		return stackOf(16, true).food(TerravibeFoodComponents.SALAD_MIXED);
	}

	public static FabricItemSettings salad_rich() {
		return stackOf(16, true).food(TerravibeFoodComponents.SALAD_RICH);
	}

	public static FabricItemSettings salad_simple() {
		return stackOf(16, true).food(TerravibeFoodComponents.SALAD_SIMPLE);
	}

	public static FabricItemSettings salt() {
		return stackOf(64, true);
	}

	public static FabricItemSettings salt_crystals() {
		return stackOf(64, true);
	}

	public static FabricItemSettings shredder() {
		return stackOf(64, true);
	}

	public static FabricItemSettings sweet_potato() {
		return stackOf(64, true).food(TerravibeFoodComponents.SWEET_POTATO);
	}

	public static FabricItemSettings sweet_potato_buds() {
		return stackOf(64, false).food(TerravibeFoodComponents.SWEET_POTATO_BUDS);
	}

	public static FabricItemSettings thistle() {
		return stackOf(64, true);
	}

	public static FabricItemSettings thistle_leaves() {
		return stackOf(64, true).food(TerravibeFoodComponents.THISTLE_LEAVES);
	}

	public static FabricItemSettings thistle_stamens() {
		return stackOf(64, true);
	}

	public static FabricItemSettings thistle_stamens_and_salt() {
		return stackOf(64, true);
	}

	public static FabricItemSettings tomato() {
		return stackOf(64, true).food(TerravibeFoodComponents.TOMATO);
	}

	public static FabricItemSettings tomato_sauce_bottle() {
		return stackOf(16, true).recipeRemainder(Items.GLASS_BOTTLE);
	}

	public static FabricItemSettings tomato_seeds() {
		return stackOf(64, true);
	}

	public static FabricItemSettings tray() {
		return stackOf(64, true);
	}

	public static FabricItemSettings tun() {
		return stackOf(64, true);
	}

	public static FabricItemSettings warped_cork() {
		return stackOf(64, true);
	}

	public static FabricItemSettings warped_cork_plug() {
		return stackOf(64, true);
	}

	/**
	 * Generates basic item settings, specifying the stack count, and if the item is visible in the creative tab.
	 */
	private static FabricItemSettings stackOf(int count, boolean visible) {
		FabricItemSettings settings = new FabricItemSettings().maxCount(count);
		return visible ? settings.group(TerravibeItemGroups.TERRAVIBE) : settings;
	}
}
