package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.util.Rarity;
import xyz.devpelux.terravibe.item.AncientSeedItem.AncientSeedItemSettings;
import xyz.devpelux.terravibe.item.ColoredItem.ColoredItemSettings;
import xyz.devpelux.terravibe.item.SideEffectFoodItem.SideEffectFoodItemSettings;

import java.util.function.Supplier;

/**
 * List of all the item settings generators.
 */
public final class TerravibeItemSettings {
	private TerravibeItemSettings() { }

	public static FabricItemSettings terravibe() {
		return stackOf(1).rarity(Rarity.EPIC);
	}

	public static AncientSeedItemSettings ancient_gillyweed_seeds() {
		AncientSeedItemSettings settings = stackOf(1, AncientSeedItemSettings::new);
		settings.dirtyLevel(5);
		settings.colorProvider(AncientSeedItem.dirtyColor((d, i) -> {
			return switch (i) {
				case 0 -> 0x619976;
				case 1 -> d < 50 ? 0x619976 : 0xffc71c;
				default -> -1;
			};
		}));
		settings.recipeRemainder(TerravibeItems.GILLYWEED_SEEDS);
		return settings;
	}

	public static AncientSeedItemSettings ancient_nightshade_fern_seeds() {
		AncientSeedItemSettings settings = stackOf(1, AncientSeedItemSettings::new);
		settings.dirtyLevel(3);
		settings.colorProvider(ColoredItem.color(0, 0x814731));
		settings.recipeRemainder(TerravibeItems.NIGHTSHADE_FERN_SEEDS);
		return settings;
	}

	public static FabricItemSettings baked_sweet_potato() {
		return stackOf(64).food(TerravibeFoodComponents.BAKED_SWEET_POTATO);
	}

	public static FabricItemSettings basil() {
		return stackOf(64);
	}

	public static FabricItemSettings beans() {
		return stackOf(64).food(TerravibeFoodComponents.BEANS);
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
		return stackOf(64).food(TerravibeFoodComponents.CHEESE);
	}

	public static FabricItemSettings cheese_flakes() {
		return stackOf(64).food(TerravibeFoodComponents.CHEESE_FLAKES);
	}

	public static FabricItemSettings cheese_wheel() {
		return stackOf(16);
	}

	public static FabricItemSettings closed_jar_empty() {
		return stackOf(16);
	}

	public static FabricItemSettings closed_jar_filled() {
		return stackOf(16);
	}

	public static FabricItemSettings corn() {
		return stackOf(64);
	}

	public static FabricItemSettings corn_grains() {
		return stackOf(64);
	}

	public static FabricItemSettings crimson_cork() {
		return stackOf(64);
	}

	public static ColoredItemSettings crimson_cork_plug() {
		return stackOf(64, ColoredItemSettings::new).colorProvider(ColoredItem.color(1, 0x7e3a56));
	}

	public static FabricItemSettings dark_mold() {
		return stackOf(64);
	}

	public static FabricItemSettings dark_mold_dust() {
		return stackOf(64);
	}

	public static FabricItemSettings dark_oak_cork() {
		return stackOf(64);
	}

	public static ColoredItemSettings dark_oak_cork_plug() {
		return stackOf(64, ColoredItemSettings::new).colorProvider(ColoredItem.color(1, 0x4f3218));
	}

	public static FabricItemSettings dark_sweet_berries() {
		return stackOf(64).food(TerravibeFoodComponents.DARK_SWEET_BERRIES);
	}

	public static FabricItemSettings eggplant() {
		return stackOf(64).food(TerravibeFoodComponents.EGGPLANT);
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

	public static SideEffectFoodItemSettings gillyweed() {
		SideEffectFoodItemSettings settings = stackOf(64, SideEffectFoodItemSettings::new);
		settings.food(TerravibeFoodComponents.GILLYWEED);
		settings.sideEffect(new StatusEffectInstance(StatusEffects.POISON, 200, 0), 0.3f); //10s
		settings.cooldown(1000); //50s
		return settings;
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
		return stackOf(64).food(TerravibeFoodComponents.GORGONZOLA);
	}

	public static FabricItemSettings gorgonzola_flakes() {
		return stackOf(64).food(TerravibeFoodComponents.GORGONZOLA_FLAKES);
	}

	public static FabricItemSettings gorgonzola_wheel() {
		return stackOf(16);
	}

	public static FabricItemSettings jar() {
		return stackOf(64);
	}

	public static FabricItemSettings kale() {
		return stackOf(64).food(TerravibeFoodComponents.KALE);
	}

	public static FabricItemSettings kale_seeds() {
		return stackOf(64);
	}

	public static FabricItemSettings lettuce_leaves() {
		return stackOf(64).food(TerravibeFoodComponents.LETTUCE_LEAVES);
	}

	public static FabricItemSettings lettuce_seeds() {
		return stackOf(64);
	}

	public static FabricItemSettings mozzarella() {
		return stackOf(64).food(TerravibeFoodComponents.MOZZARELLA);
	}

	public static FabricItemSettings mozzarella_flakes() {
		return stackOf(64).food(TerravibeFoodComponents.MOZZARELLA_FLAKES);
	}

	public static FabricItemSettings nightlock_berries() {
		return stackOf(64).food(TerravibeFoodComponents.NIGHTLOCK_BERRIES);
	}

	public static SideEffectFoodItemSettings nightshade_fern_blueberries() {
		SideEffectFoodItemSettings settings = stackOf(64, SideEffectFoodItemSettings::new);
		settings.food(TerravibeFoodComponents.NIGHTSHADE_FERN_BLUEBERRIES);
		settings.sideEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 100, 0), 0.2f); //5s
		settings.cooldown(600); //30s
		return settings;
	}

	public static FabricItemSettings nightshade_fern_seeds() {
		return stackOf(64);
	}

	public static FabricItemSettings oak_cork() {
		return stackOf(64);
	}

	public static ColoredItemSettings oak_cork_plug() {
		return stackOf(64, ColoredItemSettings::new).colorProvider(ColoredItem.color(1, 0xb8945f));
	}

	public static FabricItemSettings olives() {
		return stackOf(64);
	}

	public static FabricItemSettings onion() {
		return stackOf(64).food(TerravibeFoodComponents.ONION);
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
		return stackOf(4).food(TerravibeFoodComponents.PIZZA_SLICE_FOUR_CHEESE);
	}

	public static FabricItemSettings pizza_slice_margherita() {
		return stackOf(4).food(TerravibeFoodComponents.PIZZA_SLICE_MARGHERITA);
	}

	public static FabricItemSettings pottage() {
		return stackOf(16).food(TerravibeFoodComponents.POTTAGE);
	}

	public static FabricItemSettings prickly_pear() {
		return stackOf(64).food(TerravibeFoodComponents.PRICKLY_PEAR);
	}

	public static FabricItemSettings red_sweet_potato() {
		return stackOf(64).food(TerravibeFoodComponents.RED_SWEET_POTATO);
	}

	public static FabricItemSettings rice() {
		return stackOf(64);
	}

	public static FabricItemSettings rice_grains() {
		return stackOf(64);
	}

	public static FabricItemSettings salad() {
		return stackOf(16).food(TerravibeFoodComponents.SALAD);
	}

	public static FabricItemSettings salad_full() {
		return stackOf(16).food(TerravibeFoodComponents.SALAD_FULL);
	}

	public static FabricItemSettings salad_mixed() {
		return stackOf(16).food(TerravibeFoodComponents.SALAD_MIXED);
	}

	public static FabricItemSettings salad_rich() {
		return stackOf(16).food(TerravibeFoodComponents.SALAD_RICH);
	}

	public static FabricItemSettings salad_simple() {
		return stackOf(16).food(TerravibeFoodComponents.SALAD_SIMPLE);
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
		return stackOf(64).food(TerravibeFoodComponents.SWEET_POTATO);
	}

	public static FabricItemSettings sweet_potato_buds() {
		return stackOf(64).food(TerravibeFoodComponents.SWEET_POTATO_BUDS);
	}

	public static FabricItemSettings thistle() {
		return stackOf(64);
	}

	public static FabricItemSettings thistle_leaves() {
		return stackOf(64).food(TerravibeFoodComponents.THISTLE_LEAVES);
	}

	public static FabricItemSettings thistle_stamens() {
		return stackOf(64);
	}

	public static FabricItemSettings tomato() {
		return stackOf(64).food(TerravibeFoodComponents.TOMATO);
	}

	public static ColoredItemSettings tomato_sauce_bottle() {
		ColoredItemSettings settings = stackOf(16, ColoredItemSettings::new);
		settings.colorProvider(ColoredItem.color(1, 0xf61815));
		settings.recipeRemainder(Items.GLASS_BOTTLE);
		return settings;
	}

	public static FabricItemSettings tomato_seeds() {
		return stackOf(64);
	}

	public static FabricItemSettings tray() {
		return stackOf(64);
	}

	public static FabricItemSettings tun() {
		return stackOf(64);
	}

	public static FabricItemSettings warped_cork() {
		return stackOf(64);
	}

	public static ColoredItemSettings warped_cork_plug() {
		return stackOf(64, ColoredItemSettings::new).colorProvider(ColoredItem.color(1, 0x398382));
	}

	/**
	 * Generates basic item settings, specifying the stack count.
	 */
	private static FabricItemSettings stackOf(int count) {
		return stackOf(count, FabricItemSettings::new);
	}

	/**
	 * Generates basic item settings, specifying the stack count, with a settings supplier.
	 */
	private static <T extends FabricItemSettings> T stackOf(int count, Supplier<T> settingsFactory) {
		T settings = settingsFactory.get();
		settings.maxCount(count);
		return settings;
	}
}
