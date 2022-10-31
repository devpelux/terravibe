package xyz.devpelux.terravibe.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.registry.FlattenableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.registry.Registry;
import xyz.devpelux.terravibe.core.Terravibe;

/**
 * List of all the blocks.
 */
public final class TerravibeBlocks {
	//Objects

	/**
	 * Container for things.
	 */
	public static final Block JAR;

	/**
	 * A jar for dusts.
	 */
	public static final Block DUST_JAR;

	/**
	 * A jar for mold dusts.
	 */
	public static final Block MOLD_DUST_JAR;

	/**
	 * A cauldron that contains milk.
	 */
	public static final Block MILK_CAULDRON;

	/**
	 * Crushes an item to obtain other items.
	 */
	public static final Block MORTAR;

	/**
	 * Shreds a bunch of items to obtain something.
	 */
	public static final Block SHREDDER;

	/**
	 * Tray used to make salt.
	 */
	public static final Block TRAY;

	/**
	 * Container for "non-lava" fluids.
	 */
	public static final Block TUN;

	//Plants

	/**
	 * Herb of the basil.
	 */
	public static final Block BASIL_HERB;

	/**
	 * Crop of the beans.
	 */
	public static final Block BEANS_CROP;

	/**
	 * Birch mold that can always spread.
	 */
	public static final Block BIRCH_MOLD;

	/**
	 * Dark mold that can always spread.
	 */
	public static final Block DARK_MOLD;

	/**
	 * Glowing dark mold that can always spread.
	 */
	public static final Block GLOWING_DARK_MOLD;

	/**
	 * Crop of the corn.
	 */
	public static final Block CORN_CROP;

	/**
	 * Bush of dark sweet berries.
	 */
	public static final Block DARK_SWEET_BERRY_BUSH;

	/**
	 * Crop of the eggplant.
	 */
	public static final Block EGGPLANT_CROP;

	/**
	 * Alga Gillyweed.
	 */
	public static final Block GILLYWEED_ALGA;

	/**
	 * Crop of the kale.
	 */
	public static final Block KALE_CROP;

	/**
	 * Crop of the lettuce.
	 */
	public static final Block LETTUCE_CROP;

	/**
	 * Bush of nightlock berries, without thorns.
	 */
	public static final Block NIGHTLOCK_BERRY_BUSH;

	/**
	 * An extinct fern that produces nightshade fern blueberries.
	 */
	public static final Block NIGHTSHADE_FERN;

	/**
	 * Crop of the onion.
	 */
	public static final Block ONION_CROP;

	/**
	 * Crop of the rice.
	 */
	public static final Block RICE_CROP;

	/**
	 * Crop of the sweet potato.
	 */
	public static final Block SWEET_POTATO_CROP;

	/**
	 * Plant of the thistle.
	 */
	public static final Block THISTLE_PLANT;

	/**
	 * Crop of the tomato.
	 */
	public static final Block TOMATO_CROP;

	//Tree blocks

	/**
	 * Main block of the opuntia.
	 */
	public static final Block OPUNTIA;

	/**
	 * Flowering block of the opuntia.
	 */
	public static final Block FLOWERING_OPUNTIA;

	//Terrain blocks

	/**
	 * A mud block partially excavated.
	 */
	public static final Block EXCAVATED_MUD;

	//Foods

	/**
	 * Food made from milk coagulation.
	 */
	public static final Block CHEESE_WHEEL;

	/**
	 * Variant of cheese with mold.
	 */
	public static final Block GORGONZOLA_WHEEL;

	/**
	 * Basic pizza plus all the cheese variants.
	 */
	public static final Block PIZZA_FOUR_CHEESE;

	/**
	 * Basic pizza with tomato sauce, mozzarella, and basil.
	 */
	public static final Block PIZZA_MARGHERITA;

	private TerravibeBlocks() {
	}

	/**
	 * Loads all the blocks.
	 */
	public static void load() {
		FlattenableBlockRegistry.register(Blocks.MUD, EXCAVATED_MUD.getDefaultState());
		FlattenableBlockRegistry.register(EXCAVATED_MUD, Blocks.MUD.getDefaultState());
	}

	/**
	 * Loads all the containers behaviors.
	 */
	public static void loadContainerBehaviors() {
		TerravibeContainerBehaviors.loadBehaviors();
		MilkCauldronBlock.loadBehaviors();
	}

	/**
	 * Loads all the color providers for the items.
	 */
	@Environment(EnvType.CLIENT)
	public static void loadColorProviders() {
		ColorProviderRegistry.BLOCK.register((BlockColorProvider) DUST_JAR, DUST_JAR);
		ColorProviderRegistry.BLOCK.register((BlockColorProvider) MILK_CAULDRON, MILK_CAULDRON);
		ColorProviderRegistry.BLOCK.register((BlockColorProvider) MOLD_DUST_JAR, MOLD_DUST_JAR);
		ColorProviderRegistry.BLOCK.register((BlockColorProvider) NIGHTSHADE_FERN, NIGHTSHADE_FERN);
		ColorProviderRegistry.BLOCK.register((BlockColorProvider) JAR, JAR);
		ColorProviderRegistry.BLOCK.register((BlockColorProvider) TRAY, TRAY);
		ColorProviderRegistry.BLOCK.register((BlockColorProvider) TUN, TUN);
	}

	/**
	 * Loads all the render layer maps for the blocks.
	 */
	@Environment(EnvType.CLIENT)
	public static void loadRenderLayerMaps() {
		BlockRenderLayerMap.INSTANCE.putBlock(BASIL_HERB, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(BEANS_CROP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(BIRCH_MOLD, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(CORN_CROP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(DARK_SWEET_BERRY_BUSH, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(DARK_MOLD, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(DUST_JAR, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(MOLD_DUST_JAR, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(EGGPLANT_CROP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(FLOWERING_OPUNTIA, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(GILLYWEED_ALGA, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(GLOWING_DARK_MOLD, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(JAR, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(KALE_CROP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(LETTUCE_CROP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(NIGHTLOCK_BERRY_BUSH, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(NIGHTSHADE_FERN, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ONION_CROP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(OPUNTIA, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RICE_CROP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(SWEET_POTATO_CROP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(THISTLE_PLANT, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(TOMATO_CROP, RenderLayer.getCutout());
	}

	/**
	 * Registers the specified block with the specified id.
	 */
	private static <T extends Block> T register(String id, T block) {
		return Registry.register(Registry.BLOCK, Terravibe.identified(id), block);
	}

	static {
		BASIL_HERB = register("basil_herb", BasilHerbBlock.of());
		BEANS_CROP = register("beans_crop", BeansCropBlock.of());
		BIRCH_MOLD = register("birch_mold", BirchMoldBlock.of());
		CHEESE_WHEEL = register("cheese_wheel", CheeseWheelBlock.of());
		CORN_CROP = register("corn_crop", CornCropBlock.of());
		DARK_MOLD = register("dark_mold", DarkMoldBlock.of());
		DUST_JAR = register("dust_jar", DustJarBlock.of());
		EXCAVATED_MUD = register("excavated_mud", ExcavatedMudBlock.of());
		FLOWERING_OPUNTIA = register("flowering_opuntia", FloweringOpuntiaBlock.of());
		GILLYWEED_ALGA = register("gillyweed_alga", GillyweedAlgaBlock.of());
		GLOWING_DARK_MOLD = register("glowing_dark_mold", GlowingDarkMoldBlock.of());
		GORGONZOLA_WHEEL = register("gorgonzola_wheel", CheeseWheelBlock.of());
		DARK_SWEET_BERRY_BUSH = register("dark_sweet_berry_bush", DarkSweetBerryBushBlock.of());
		EGGPLANT_CROP = register("eggplant_crop", EggplantCropBlock.of());
		JAR = register("jar", JarBlock.of());
		KALE_CROP = register("kale_crop", KaleCropBlock.of());
		LETTUCE_CROP = register("lettuce_crop", LettuceCropBlock.of());
		MILK_CAULDRON = register("milk_cauldron", MilkCauldronBlock.of());
		MOLD_DUST_JAR = register("mold_dust_jar", MoldDustJarBlock.of());
		MORTAR = register("mortar", MortarBlock.of());
		NIGHTLOCK_BERRY_BUSH = register("nightlock_berry_bush", NightlockBerryBushBlock.of());
		NIGHTSHADE_FERN = register("nightshade_fern", NightshadeFernBlock.of());
		ONION_CROP = register("onion_crop", OnionCropBlock.of());
		OPUNTIA = register("opuntia", OpuntiaBlock.of());
		PIZZA_FOUR_CHEESE = register("pizza_four_cheese", PizzaBlock.fourCheese());
		PIZZA_MARGHERITA = register("pizza_margherita", PizzaBlock.margherita());
		RICE_CROP = register("rice_crop", RiceCropBlock.of());
		SHREDDER = register("shredder", ShredderBlock.of());
		SWEET_POTATO_CROP = register("sweet_potato_crop", SweetPotatoCropBlock.of());
		THISTLE_PLANT = register("thistle_plant", ThistlePlantBlock.of());
		TOMATO_CROP = register("tomato_crop", TomatoCropBlock.of());
		TRAY = register("tray", TrayBlock.of());
		TUN = register("tun", TunBlock.of());
	}
}
