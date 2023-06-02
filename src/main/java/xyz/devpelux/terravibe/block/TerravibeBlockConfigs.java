package xyz.devpelux.terravibe.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.registry.FlattenableBlockRegistry;
import net.fabricmc.fabric.api.registry.LandPathNodeTypesRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.ai.pathing.PathNodeType;

import static xyz.devpelux.terravibe.block.TerravibeBlocks.*;

public class TerravibeBlockConfigs {
	private TerravibeBlockConfigs() { }

	public static void registerBehaviors() {
		MilkCauldronBlock.registerBehaviors();
		ContainerBlockData.registerBehaviors();
	}

	public static void registerFlattenables() {
		FlattenableBlockRegistry.register(Blocks.MUD, EXCAVATED_MUD.getDefaultState());
		FlattenableBlockRegistry.register(EXCAVATED_MUD, Blocks.MUD.getDefaultState());
	}

	public static void registerLandPathNodeTypes() {
		LandPathNodeTypesRegistry.register(TerravibeBlocks.DARK_SWEET_BERRY_BUSH, PathNodeType.DAMAGE_OTHER, PathNodeType.DANGER_OTHER);
		LandPathNodeTypesRegistry.register(TerravibeBlocks.FLOWERING_OPUNTIA, PathNodeType.DAMAGE_OTHER, PathNodeType.DANGER_OTHER);
		LandPathNodeTypesRegistry.register(TerravibeBlocks.NIGHTLOCK_BERRY_BUSH, PathNodeType.DAMAGE_OTHER, PathNodeType.DANGER_OTHER);
		LandPathNodeTypesRegistry.register(TerravibeBlocks.OPUNTIA, PathNodeType.DAMAGE_OTHER, PathNodeType.DANGER_OTHER);
		LandPathNodeTypesRegistry.register(TerravibeBlocks.THISTLE_PLANT, PathNodeType.DAMAGE_OTHER, PathNodeType.DANGER_OTHER);
	}

	@Environment(EnvType.CLIENT)
	public static void registerColorProviders() {
		ColorProviderRegistry.BLOCK.register((BlockColorProvider) BURNED_GLOWING_DARK_MOLD_DUST_JAR, BURNED_GLOWING_DARK_MOLD_DUST_JAR);
		ColorProviderRegistry.BLOCK.register((BlockColorProvider) DUST_JAR, DUST_JAR);
		ColorProviderRegistry.BLOCK.register((BlockColorProvider) GLOWING_DARK_MOLD_DUST_JAR, GLOWING_DARK_MOLD_DUST_JAR);
		ColorProviderRegistry.BLOCK.register((BlockColorProvider) MILK_CAULDRON, MILK_CAULDRON);
		ColorProviderRegistry.BLOCK.register((BlockColorProvider) MOLD_DUST_JAR, MOLD_DUST_JAR);
		ColorProviderRegistry.BLOCK.register((BlockColorProvider) NIGHTSHADE_FERN, NIGHTSHADE_FERN);
		ColorProviderRegistry.BLOCK.register((BlockColorProvider) JAR, JAR);
		ColorProviderRegistry.BLOCK.register((BlockColorProvider) TRAY, TRAY);
		ColorProviderRegistry.BLOCK.register((BlockColorProvider) TUN, TUN);
		ContainerBlockData.registerColorProviders();
	}

	@Environment(EnvType.CLIENT)
	public static void registerRenderLayerMaps() {
		BlockRenderLayerMap.INSTANCE.putBlock(BASIL_HERB, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(BEANS_CROP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(BIRCH_MOLD, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(BURNED_GLOWING_DARK_MOLD_DUST_JAR, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(CORN_CROP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(DARK_SWEET_BERRY_BUSH, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(DARK_MOLD, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(DUST_JAR, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(MOLD_DUST_JAR, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(EGGPLANT_CROP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(FLOWERING_OPUNTIA, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(GILLYWEED_ALGA, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(GLOWING_DARK_MOLD, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(GLOWING_DARK_MOLD_DUST_JAR, RenderLayer.getCutout());
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
}
