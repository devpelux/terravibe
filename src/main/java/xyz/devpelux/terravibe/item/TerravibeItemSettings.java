package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Items;
import net.minecraft.util.Rarity;

/**
 * List of all the item settings generators.
 */
public final class TerravibeItemSettings {
	public static FabricItemSettings terravibe() {
		return stackOf(1).rarity(Rarity.EPIC);
	}

	public static FabricItemSettings ancient_gillyweed_seeds() {
		return stackOf(1).recipeRemainder(TerravibeItems.GILLYWEED_SEEDS);
	}

	public static FabricItemSettings ancient_nightshade_fern_seeds() {
		return stackOf(1).recipeRemainder(TerravibeItems.NIGHTSHADE_FERN_SEEDS);
	}

	public static FabricItemSettings baked_sweet_potato() {
		return stackOf(64).food(TerravibeFoodEffects.BAKED_SWEET_POTATO);
	}

	public static FabricItemSettings basil() {
		return stackOf(64);
	}

	public static FabricItemSettings beans() {
		return stackOf(64).food(TerravibeFoodEffects.BEANS);
	}

	public static FabricItemSettings birch_mold() {
		return stackOf(64);
	}

	public static FabricItemSettings birch_mold_dust() {
		return stackOf(64);
	}

	public static FabricItemSettings burned_birch_mold_dust() {
		return stackOf(64);
	}

	public static FabricItemSettings burned_dark_mold_dust() {
		return stackOf(64);
	}

	public static FabricItemSettings burned_glowing_dark_mold_dust() {
		return stackOf(64);
	}

	public static FabricItemSettings cheese() {
		return stackOf(64).food(TerravibeFoodEffects.CHEESE);
	}

	public static FabricItemSettings cheese_wheel() {
		return stackOf(16);
	}

	public static FabricItemSettings corn() {
		return stackOf(64);
	}

	public static FabricItemSettings corn_grains() {
		return stackOf(64);
	}

	public static FabricItemSettings cork() {
		return stackOf(64);
	}

	public static FabricItemSettings cork_plug() {
		return stackOf(64);
	}

	public static FabricItemSettings dark_cork() {
		return stackOf(64);
	}

	public static FabricItemSettings dark_cork_plug() {
		return stackOf(64);
	}

	public static FabricItemSettings dark_mold() {
		return stackOf(64);
	}

	public static FabricItemSettings dark_mold_dust() {
		return stackOf(64);
	}

	public static FabricItemSettings dark_sweet_berries() {
		return stackOf(64).food(TerravibeFoodEffects.DARK_SWEET_BERRIES);
	}

	public static FabricItemSettings eggplant() {
		return stackOf(64).food(TerravibeFoodEffects.EGGPLANT);
	}

	public static FabricItemSettings eggplant_seeds() {
		return stackOf(64);
	}

	public static FabricItemSettings excavated_mud() {
		return stackOf(64);
	}

	public static FabricItemSettings flowering_opuntia() {
		return stackOf(64);
	}

	public static FabricItemSettings gillyweed() {
		return stackOf(64).food(TerravibeFoodEffects.GILLYWEED);
	}

	public static FabricItemSettings gillyweed_seeds() {
		return stackOf(64);
	}

	public static FabricItemSettings glowing_dark_mold() {
		return stackOf(64);
	}

	public static FabricItemSettings glowing_dark_mold_dust() {
		return stackOf(64);
	}

	public static FabricItemSettings gorgonzola() {
		return stackOf(64).food(TerravibeFoodEffects.GORGONZOLA);
	}

	public static FabricItemSettings gorgonzola_wheel() {
		return stackOf(16);
	}

	public static FabricItemSettings kale() {
		return stackOf(64).food(TerravibeFoodEffects.KALE);
	}

	public static FabricItemSettings kale_seeds() {
		return stackOf(64);
	}

	public static FabricItemSettings lemon() {
		return stackOf(64).food(TerravibeFoodEffects.LEMON);
	}

	public static FabricItemSettings lemon_juice() {
		return stackOf(16).recipeRemainder(Items.GLASS_BOTTLE).food(TerravibeFoodEffects.LEMON_JUICE);
	}

	public static FabricItemSettings lettuce_leaves() {
		return stackOf(64).food(TerravibeFoodEffects.LETTUCE_LEAVES);
	}

	public static FabricItemSettings lettuce_seeds() {
		return stackOf(64);
	}

	public static FabricItemSettings mozzarella() {
		return stackOf(64).food(TerravibeFoodEffects.MOZZARELLA);
	}

	public static FabricItemSettings nightlock_berries() {
		return stackOf(64).food(TerravibeFoodEffects.NIGHTLOCK_BERRIES);
	}

	public static FabricItemSettings nightshade_fern_blueberries() {
		return stackOf(64).food(TerravibeFoodEffects.NIGHTSHADE_FERN_BLUEBERRIES);
	}

	public static FabricItemSettings nightshade_fern_seeds() {
		return stackOf(64);
	}

	public static FabricItemSettings oil() {
		return stackOf(16).recipeRemainder(Items.GLASS_BOTTLE);
	}

	public static FabricItemSettings olives() {
		return stackOf(64);
	}

	public static FabricItemSettings onion() {
		return stackOf(64).food(TerravibeFoodEffects.ONION);
	}

	public static FabricItemSettings onion_seeds() {
		return stackOf(64);
	}

	public static FabricItemSettings opuntia() {
		return stackOf(64);
	}

	public static FabricItemSettings pizza_four_cheese() {
		return stackOf(1);
	}

	public static FabricItemSettings pizza_margherita() {
		return stackOf(1);
	}

	public static FabricItemSettings pizza_slice_four_cheese() {
		return stackOf(4).food(TerravibeFoodEffects.PIZZA_SLICE_FOUR_CHEESE);
	}

	public static FabricItemSettings pizza_slice_margherita() {
		return stackOf(4).food(TerravibeFoodEffects.PIZZA_SLICE_MARGHERITA);
	}

	public static FabricItemSettings pottage() {
		return stackOf(16).food(TerravibeFoodEffects.POTTAGE);
	}

	public static FabricItemSettings prickly_pear() {
		return stackOf(64).food(TerravibeFoodEffects.PRICKLY_PEAR);
	}

	public static FabricItemSettings red_sweet_potato() {
		return stackOf(64).food(TerravibeFoodEffects.RED_SWEET_POTATO);
	}

	public static FabricItemSettings rice() {
		return stackOf(64);
	}

	public static FabricItemSettings rice_grains() {
		return stackOf(64);
	}

	public static FabricItemSettings salad() {
		return stackOf(16).food(TerravibeFoodEffects.SALAD);
	}

	public static FabricItemSettings salad_full() {
		return stackOf(16).food(TerravibeFoodEffects.SALAD_FULL);
	}

	public static FabricItemSettings salad_mixed() {
		return stackOf(16).food(TerravibeFoodEffects.SALAD_MIXED);
	}

	public static FabricItemSettings salad_rich() {
		return stackOf(16).food(TerravibeFoodEffects.SALAD_RICH);
	}

	public static FabricItemSettings salad_simple() {
		return stackOf(16).food(TerravibeFoodEffects.SALAD_SIMPLE);
	}

	public static FabricItemSettings salt() {
		return stackOf(64);
	}

	public static FabricItemSettings salt_crystals() {
		return stackOf(64);
	}

	public static FabricItemSettings shredder() {
		return stackOf(64);
	}

	public static FabricItemSettings sweet_potato() {
		return stackOf(64).food(TerravibeFoodEffects.SWEET_POTATO);
	}

	public static FabricItemSettings sweet_potato_buds() {
		return stackOf(64).food(TerravibeFoodEffects.SWEET_POTATO_BUDS);
	}

	public static FabricItemSettings tomato() {
		return stackOf(64).food(TerravibeFoodEffects.TOMATO);
	}

	public static FabricItemSettings tomato_sauce() {
		return stackOf(16).recipeRemainder(Items.GLASS_BOTTLE);
	}

	public static FabricItemSettings tomato_seeds() {
		return stackOf(64);
	}

	public static FabricItemSettings tray() {
		return stackOf(64);
	}

	/**
	 * Generates basic item settings, specifying the stack count.
	 */
	private static FabricItemSettings stackOf(int count) {
		FabricItemSettings settings = new FabricItemSettings();
		settings.maxCount(count);
		return settings;
	}
}
