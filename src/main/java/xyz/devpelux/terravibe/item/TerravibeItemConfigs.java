package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.item.Items;

import static xyz.devpelux.terravibe.item.TerravibeItems.*;

/**
 * Item configurations.
 */
public class TerravibeItemConfigs {
	/**
	 * Registers all the item composting chances.
	 */
	public static void registerCompostingChances() {
		CompostingChanceRegistry.INSTANCE.add(BAKED_SWEET_POTATO, 0.85f);
		CompostingChanceRegistry.INSTANCE.add(BASIL, 0.3f);
		CompostingChanceRegistry.INSTANCE.add(BEANS, 0.7f);
		CompostingChanceRegistry.INSTANCE.add(BIRCH_MOLD, 0.2f);
		CompostingChanceRegistry.INSTANCE.add(BIRCH_MOLD_DUST, 0.04f);
		CompostingChanceRegistry.INSTANCE.add(BURNED_BIRCH_MOLD_DUST, 0.01f);
		CompostingChanceRegistry.INSTANCE.add(BURNED_DARK_MOLD_DUST, 0.01f);
		CompostingChanceRegistry.INSTANCE.add(BURNED_GLOWING_DARK_MOLD_DUST, 0.01f);
		CompostingChanceRegistry.INSTANCE.add(CHEESE, 0.65f);
		CompostingChanceRegistry.INSTANCE.add(CHEESE_WHEEL, 1f);
		CompostingChanceRegistry.INSTANCE.add(CORN, 0.6f);
		CompostingChanceRegistry.INSTANCE.add(CORN_GRAINS, 0.1f);
		CompostingChanceRegistry.INSTANCE.add(CORK, 0.2f);
		CompostingChanceRegistry.INSTANCE.add(DARK_CORK, 0.2f);
		CompostingChanceRegistry.INSTANCE.add(DARK_MOLD, 0.2f);
		CompostingChanceRegistry.INSTANCE.add(DARK_MOLD_DUST, 0.04f);
		CompostingChanceRegistry.INSTANCE.add(DARK_SWEET_BERRIES, 0.3f);
		CompostingChanceRegistry.INSTANCE.add(EGGPLANT, 0.65f);
		CompostingChanceRegistry.INSTANCE.add(EGGPLANT_SEEDS, 0.3f);
		CompostingChanceRegistry.INSTANCE.add(GILLYWEED, 0.5f);
		CompostingChanceRegistry.INSTANCE.add(GILLYWEED_SEEDS, 0.3f);
		CompostingChanceRegistry.INSTANCE.add(GILLYWEED_SEEDS_ANCIENT, 0.3f);
		CompostingChanceRegistry.INSTANCE.add(GLOWING_DARK_MOLD, 0.2f);
		CompostingChanceRegistry.INSTANCE.add(GLOWING_DARK_MOLD_DUST, 0.04f);
		CompostingChanceRegistry.INSTANCE.add(GORGONZOLA, 0.65f);
		CompostingChanceRegistry.INSTANCE.add(GORGONZOLA_WHEEL, 1f);
		CompostingChanceRegistry.INSTANCE.add(KALE, 0.65f);
		CompostingChanceRegistry.INSTANCE.add(KALE_SEEDS, 0.3f);
		CompostingChanceRegistry.INSTANCE.add(LEMON, 0.65f);
		CompostingChanceRegistry.INSTANCE.add(LETTUCE_LEAVES, 0.5f);
		CompostingChanceRegistry.INSTANCE.add(LETTUCE_SEEDS, 0.3f);
		CompostingChanceRegistry.INSTANCE.add(MOZZARELLA, 0.5f);
		CompostingChanceRegistry.INSTANCE.add(NIGHTLOCK_BERRIES, 0.3f);
		CompostingChanceRegistry.INSTANCE.add(NIGHTSHADE_FERN_BLUEBERRIES, 0.3f);
		CompostingChanceRegistry.INSTANCE.add(NIGHTSHADE_FERN_SEEDS, 0.3f);
		CompostingChanceRegistry.INSTANCE.add(NIGHTSHADE_FERN_SEEDS_ANCIENT, 0.3f);
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
		CompostingChanceRegistry.INSTANCE.add(TOMATO, 0.65f);
		CompostingChanceRegistry.INSTANCE.add(TOMATO_SEEDS, 0.3f);
	}
}
