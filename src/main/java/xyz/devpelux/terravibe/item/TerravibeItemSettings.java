package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import xyz.devpelux.terravibe.item.AncientSeedItem.AncientSeedItemSettings;
import xyz.devpelux.terravibe.item.ColoredItem.ColoredItemSettings;
import xyz.devpelux.terravibe.item.SideEffectFoodItem.SideEffectFoodItemSettings;

import java.util.function.Supplier;

/**
 * List of all the item settings generators.
 */
public final class TerravibeItemSettings {
	private TerravibeItemSettings() {
	}

	public static AncientSeedItemSettings ancient_nightshade_fern_seeds() {
		AncientSeedItemSettings settings = stackOf(1, false, AncientSeedItemSettings::new);
		settings.dirtyLevel(3);
		settings.colorProvider(ColoredItem.color(0, 0x814731));
		settings.recipeRemainder(TerravibeItems.NIGHTSHADE_FERN_SEEDS);
		return settings;
	}

	public static FabricItemSettings baked_sweet_potato() {
		return stackOf(64, true)
				.food(TerravibeFoodComponents.BAKED_SWEET_POTATO);
	}

	public static FabricItemSettings basil() {
		return stackOf(64, true);
	}

	public static FabricItemSettings beans() {
		return stackOf(64, true)
				.food(TerravibeFoodComponents.BEANS);
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
		return stackOf(64, true)
				.food(TerravibeFoodComponents.CHEESE);
	}

	public static FabricItemSettings cheese_flakes() {
		return stackOf(64, true)
				.food(TerravibeFoodComponents.CHEESE_FLAKES);
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

	public static ColoredItemSettings crimson_cork_plug() {
		return stackOf(64, true, ColoredItemSettings::new)
				.colorProvider(ColoredItem.color(1, 0x7e3a56));
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

	public static ColoredItemSettings dark_oak_cork_plug() {
		return stackOf(64, true, ColoredItemSettings::new)
				.colorProvider(ColoredItem.color(1, 0x4f3218));
	}

	public static FabricItemSettings dark_sweet_berries() {
		return stackOf(64, true)
				.food(TerravibeFoodComponents.DARK_SWEET_BERRIES);
	}

	public static FabricItemSettings eggplant() {
		return stackOf(64, true)
				.food(TerravibeFoodComponents.EGGPLANT);
	}

	public static FabricItemSettings eggplant_seeds() {
		return stackOf(64, true);
	}

	public static FabricItemSettings excavated_mud() {
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
		return stackOf(64, true)
				.food(TerravibeFoodComponents.GORGONZOLA);
	}

	public static FabricItemSettings gorgonzola_flakes() {
		return stackOf(64, true)
				.food(TerravibeFoodComponents.GORGONZOLA_FLAKES);
	}

	public static FabricItemSettings gorgonzola_wheel() {
		return stackOf(16, true);
	}

	public static FabricItemSettings jar() {
		return stackOf(64, true);
	}

	public static FabricItemSettings kale() {
		return stackOf(64, true)
				.food(TerravibeFoodComponents.KALE);
	}

	public static FabricItemSettings kale_seeds() {
		return stackOf(64, true);
	}

	public static FabricItemSettings lettuce_leaves() {
		return stackOf(64, true)
				.food(TerravibeFoodComponents.LETTUCE_LEAVES);
	}

	public static FabricItemSettings lettuce_seeds() {
		return stackOf(64, true);
	}

	public static FabricItemSettings mortar() {
		return stackOf(64, true);
	}

	public static FabricItemSettings mozzarella() {
		return stackOf(64, true)
				.food(TerravibeFoodComponents.MOZZARELLA);
	}

	public static FabricItemSettings mozzarella_flakes() {
		return stackOf(64, true)
				.food(TerravibeFoodComponents.MOZZARELLA_FLAKES);
	}

	public static FabricItemSettings nightlock_berries() {
		return stackOf(64, true)
				.food(TerravibeFoodComponents.NIGHTLOCK_BERRIES);
	}

	public static SideEffectFoodItemSettings nightshade_fern_blueberries() {
		SideEffectFoodItemSettings settings = stackOf(64, true, SideEffectFoodItemSettings::new);
		settings.food(TerravibeFoodComponents.NIGHTSHADE_FERN_BLUEBERRIES);
		settings.sideEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 100, 0), 0.2f);
		settings.cooldown(600);
		return settings;
	}

	public static FabricItemSettings nightshade_fern_seeds() {
		return stackOf(64, true);
	}

	public static FabricItemSettings oak_cork() {
		return stackOf(64, true);
	}

	public static ColoredItemSettings oak_cork_plug() {
		return stackOf(64, true, ColoredItemSettings::new)
				.colorProvider(ColoredItem.color(1, 0xb8945f));
	}

	public static FabricItemSettings olives() {
		return stackOf(64, true);
	}

	public static FabricItemSettings onion() {
		return stackOf(64, true)
				.food(TerravibeFoodComponents.ONION);
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
		return stackOf(4, true)
				.food(TerravibeFoodComponents.PIZZA_SLICE_FOUR_CHEESE);
	}

	public static FabricItemSettings pizza_slice_margherita() {
		return stackOf(4, true)
				.food(TerravibeFoodComponents.PIZZA_SLICE_MARGHERITA);
	}

	public static FabricItemSettings pottage() {
		return stackOf(16, true)
				.food(TerravibeFoodComponents.POTTAGE);
	}

	public static FabricItemSettings prickly_pear() {
		return stackOf(64, true)
				.food(TerravibeFoodComponents.PRICKLY_PEAR);
	}

	public static FabricItemSettings red_sweet_potato() {
		return stackOf(64, true)
				.food(TerravibeFoodComponents.RED_SWEET_POTATO);
	}

	public static FabricItemSettings rice() {
		return stackOf(64, true);
	}

	public static FabricItemSettings rice_grains() {
		return stackOf(64, true);
	}

	public static FabricItemSettings salad() {
		return stackOf(16, true)
				.food(TerravibeFoodComponents.SALAD);
	}

	public static FabricItemSettings salad_full() {
		return stackOf(16, true)
				.food(TerravibeFoodComponents.SALAD_FULL);
	}

	public static FabricItemSettings salad_mixed() {
		return stackOf(16, true)
				.food(TerravibeFoodComponents.SALAD_MIXED);
	}

	public static FabricItemSettings salad_rich() {
		return stackOf(16, true)
				.food(TerravibeFoodComponents.SALAD_RICH);
	}

	public static FabricItemSettings salad_simple() {
		return stackOf(16, true)
				.food(TerravibeFoodComponents.SALAD_SIMPLE);
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
		return stackOf(64, true)
				.food(TerravibeFoodComponents.SWEET_POTATO);
	}

	public static FabricItemSettings sweet_potato_buds() {
		return stackOf(64, false)
				.food(TerravibeFoodComponents.SWEET_POTATO_BUDS);
	}

	public static FabricItemSettings thistle() {
		return stackOf(64, true);
	}

	public static FabricItemSettings thistle_leaves() {
		return stackOf(64, true)
				.food(TerravibeFoodComponents.THISTLE_LEAVES);
	}

	public static FabricItemSettings thistle_stamens() {
		return stackOf(64, true);
	}

	public static FabricItemSettings thistle_stamens_and_salt() {
		return stackOf(64, true);
	}

	public static FabricItemSettings tomato() {
		return stackOf(64, true)
				.food(TerravibeFoodComponents.TOMATO);
	}

	public static ColoredItemSettings tomato_sauce_bottle() {
		ColoredItemSettings settings = stackOf(16, true, ColoredItemSettings::new);
		settings.colorProvider(ColoredItem.color(1, 0xf61815));
		settings.recipeRemainder(Items.GLASS_BOTTLE);
		return settings;
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

	public static ColoredItemSettings warped_cork_plug() {
		return stackOf(64, true, ColoredItemSettings::new)
				.colorProvider(ColoredItem.color(1, 0x398382));
	}

	/**
	 * Generates basic item settings, specifying the stack count, and if the item is visible in the creative tab.
	 */
	private static FabricItemSettings stackOf(int count, boolean visible) {
		return stackOf(count, visible, FabricItemSettings::new);
	}

	/**
	 * Generates basic item settings, specifying the stack count, and if the item is visible in the creative tab.
	 * Specify also the settings type to create.
	 */
	private static <T extends FabricItemSettings> T stackOf(int count, boolean visible, Supplier<T> factory) {
		T settings = factory.get();
		settings.maxCount(count);
		settings.group(visible ? TerravibeItemGroups.TERRAVIBE : null);
		return settings;
	}
}
