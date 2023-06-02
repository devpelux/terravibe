package xyz.devpelux.terravibe.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.item.Items;
import xyz.devpelux.terravibe.core.CorkStrippableBlockRegistry;

import static xyz.devpelux.terravibe.item.TerravibeItems.*;

public class TerravibeItemConfigs {
	private TerravibeItemConfigs() { }

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
		CompostingChanceRegistry.INSTANCE.add(GILLYWEED_SEEDS_ANCIENT, 0.3f);
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
		CompostingChanceRegistry.INSTANCE.add(NIGHTSHADE_FERN_SEEDS_ANCIENT, 0.3f);
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
		CompostingChanceRegistry.INSTANCE.add(TOMATO, 0.65f);
		CompostingChanceRegistry.INSTANCE.add(TOMATO_SEEDS, 0.3f);
		CompostingChanceRegistry.INSTANCE.add(WARPED_CORK, 0.2f);
	}

	public static void registerCorkStrippables() {
		CorkStrippableBlockRegistry.register(Blocks.CRIMSON_STEM, CRIMSON_CORK, 0.1f);
		CorkStrippableBlockRegistry.register(Blocks.CRIMSON_HYPHAE, CRIMSON_CORK, 0.1f);
		CorkStrippableBlockRegistry.register(Blocks.DARK_OAK_LOG, DARK_OAK_CORK, 0.1f);
		CorkStrippableBlockRegistry.register(Blocks.DARK_OAK_WOOD, DARK_OAK_CORK, 0.1f);
		CorkStrippableBlockRegistry.register(Blocks.OAK_LOG, OAK_CORK, 0.2f);
		CorkStrippableBlockRegistry.register(Blocks.OAK_WOOD, OAK_CORK, 0.2f);
		CorkStrippableBlockRegistry.register(Blocks.WARPED_STEM, WARPED_CORK, 0.2f);
		CorkStrippableBlockRegistry.register(Blocks.WARPED_HYPHAE, WARPED_CORK, 0.2f);
	}

	@Environment(EnvType.CLIENT)
	public static void registerColorProviders() {
		ColorProviderRegistry.ITEM.register((ItemColorProvider) CRIMSON_CORK_PLUG, CRIMSON_CORK_PLUG);
		ColorProviderRegistry.ITEM.register((ItemColorProvider) DARK_OAK_CORK_PLUG, DARK_OAK_CORK_PLUG);
		ColorProviderRegistry.ITEM.register((ItemColorProvider) GILLYWEED_SEEDS_ANCIENT, GILLYWEED_SEEDS_ANCIENT);
		ColorProviderRegistry.ITEM.register((ItemColorProvider) NIGHTSHADE_FERN_SEEDS_ANCIENT, NIGHTSHADE_FERN_SEEDS_ANCIENT);
		ColorProviderRegistry.ITEM.register((ItemColorProvider) OAK_CORK_PLUG, OAK_CORK_PLUG);
		ColorProviderRegistry.ITEM.register((ItemColorProvider) TOMATO_SAUCE_BOTTLE, TOMATO_SAUCE_BOTTLE);
		ColorProviderRegistry.ITEM.register((ItemColorProvider) WARPED_CORK_PLUG, WARPED_CORK_PLUG);
		ColorProviderRegistry.ITEM.register((ItemColorProvider) CLOSED_JAR_EMPTY, CLOSED_JAR_EMPTY);
		ColorProviderRegistry.ITEM.register((ItemColorProvider) CLOSED_JAR_FILLED, CLOSED_JAR_FILLED);
	}
}
