package xyz.devpelux.terravibe.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.devpelux.terravibe.core.compatibility.sodium.IgnoreColorBlendingRegistry;

/** List of all the blocks. */
public class TerravibeBlocks {
    /** Mortar block: Crushes an item to obtain another item. */
    public static final MortarBlock MORTAR;

    /** Shredder block: Shreds a bunch of items to obtain something. */
    public static final ShredderBlock SHREDDER;

    /** Tun block: Container for "non-lava" fluids. */
    public static final TunBlock TUN;

    /** Tray block: Tray used to make salt. */
    public static final TrayBlock TRAY;

    /** Onion crop block: Crop of the onion. */
    public static final OnionCropBlock ONION_CROP;

    /** Sweet potato crop block: Crop of the sweet potato. */
    public static final SweetPotatoCropBlock SWEET_POTATO_CROP;

    /** Tomato crop block: Crop of the tomato. */
    public static final TomatoCropBlock TOMATO_CROP;

    /** Loads all the blocks. */
    public static void load() {
        IgnoreColorBlendingRegistry.register(TUN);
    }

    /** Loads all the color providers for the items. */
    @Environment(EnvType.CLIENT)
    public static void loadColorProviders() {
        ColorProviderRegistry.BLOCK.register(TunBlock::getContainedFluidColor, TUN);
        ColorProviderRegistry.BLOCK.register(TrayBlock::getWaterColor, TRAY);
    }

    /** Loads all the render layer maps for the blocks. */
    @Environment(EnvType.CLIENT)
    public static void loadRenderLayerMaps() {
        BlockRenderLayerMap.INSTANCE.putBlock(ONION_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(SWEET_POTATO_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TOMATO_CROP, RenderLayer.getCutout());
    }

    /** Registers the specified block with the specified id. */
    private static <T extends Block> T register(Identifier id, T block) {
        return Registry.register(Registry.BLOCK, id, block);
    }

    static {
        MORTAR = register(MortarBlock.ID, new MortarBlock(MortarBlock.getSettings()));
        SHREDDER = register(ShredderBlock.ID, new ShredderBlock(ShredderBlock.getSettings()));
        TUN = register(TunBlock.ID, new TunBlock(TunBlock.getSettings()));
        TRAY = register(TrayBlock.ID, new TrayBlock(TrayBlock.getSettings()));
        ONION_CROP = register(OnionCropBlock.ID, new OnionCropBlock(OnionCropBlock.getSettings()));
        SWEET_POTATO_CROP = register(SweetPotatoCropBlock.ID, new SweetPotatoCropBlock(SweetPotatoCropBlock.getSettings()));
        TOMATO_CROP = register(TomatoCropBlock.ID, new TomatoCropBlock(TomatoCropBlock.getSettings()));
    }
}
