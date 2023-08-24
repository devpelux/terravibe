package xyz.devpelux.terravibe.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.render.RenderLayer;

/**
 * Block configurations.
 */
public class TerravibeBlockConfigsClient {
	/**
	 * Registers all the block color providers.
	 */
	@Environment(EnvType.CLIENT)
	public static void registerColorProviders() {
		ColorProviderRegistry.BLOCK.register(TerravibeBlockColorProviders::getMilkCauldronColor, TerravibeBlocks.MILK_CAULDRON);
		ColorProviderRegistry.BLOCK.register(TerravibeBlockColorProviders::getNightshadeFernColor, TerravibeBlocks.NIGHTSHADE_FERN);
		ColorProviderRegistry.BLOCK.register(TerravibeBlockColorProviders::getTrayColor, TerravibeBlocks.TRAY);
	}

	/**
	 * Registers all the block render layer maps.
	 */
	@Environment(EnvType.CLIENT)
	public static void registerRenderLayerMaps() {
		BlockRenderLayerMap.INSTANCE.putBlock(TerravibeBlocks.BASIL_HERB, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(TerravibeBlocks.BEANS_CROP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(TerravibeBlocks.BIRCH_MOLD, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(TerravibeBlocks.CORN_CROP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(TerravibeBlocks.DARK_SWEET_BERRY_BUSH, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(TerravibeBlocks.DARK_MOLD, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(TerravibeBlocks.EGGPLANT_CROP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(TerravibeBlocks.FLOWERING_OPUNTIA, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(TerravibeBlocks.GILLYWEED_ALGA, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(TerravibeBlocks.GLOWING_DARK_MOLD, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(TerravibeBlocks.KALE_CROP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(TerravibeBlocks.LETTUCE_CROP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(TerravibeBlocks.NIGHTLOCK_BERRY_BUSH, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(TerravibeBlocks.NIGHTSHADE_FERN, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(TerravibeBlocks.ONION_CROP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(TerravibeBlocks.OPUNTIA, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(TerravibeBlocks.RICE_CROP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(TerravibeBlocks.SWEET_POTATO_CROP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(TerravibeBlocks.TOMATO_CROP, RenderLayer.getCutout());
	}
}
