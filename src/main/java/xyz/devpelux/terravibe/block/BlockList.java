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
public class BlockList {
    /** Mortar block: Crushes an item to obtain another item. */
    public static final MortarBlock MORTAR;

    /** Shredder block: Shreds a bunch of items to obtain something. */
    public static final ShredderBlock SHREDDER;

    /** Sweet potato crop block: Crop of the sweet potato. */
    public static final SweetPotatoCropBlock SWEET_POTATO_CROP;

    /** Tun block: Container for "non-lava" fluids. */
    public static final TunBlock TUN;

    /** Loads all the blocks. */
    public static void load() {
        IgnoreColorBlendingRegistry.register(TUN);
    }

    /** Loads all the color providers for the items. */
    @Environment(EnvType.CLIENT)
    public static void loadColorProviders() {
        ColorProviderRegistry.BLOCK.register(TunBlock::getContainedFluidColor, TUN);
    }

    /** Loads all the render layer maps for the blocks. */
    @Environment(EnvType.CLIENT)
    public static void loadRenderLayerMaps() {
        BlockRenderLayerMap.INSTANCE.putBlock(SWEET_POTATO_CROP, RenderLayer.getCutout());
    }

    /** Registers the specified block with the specified id. */
    private static <T extends Block> T register(Identifier id, T block) {
        return Registry.register(Registry.BLOCK, id, block);
    }

    static {
        MORTAR = register(MortarBlock.ID, new MortarBlock(MortarBlock.getSettings()));
        SHREDDER = register(ShredderBlock.ID, new ShredderBlock(ShredderBlock.getSettings()));
        SWEET_POTATO_CROP = register(SweetPotatoCropBlock.ID, new SweetPotatoCropBlock(SweetPotatoCropBlock.getSettings()));
        TUN = register(TunBlock.ID, new TunBlock(TunBlock.getSettings()));
    }
}
