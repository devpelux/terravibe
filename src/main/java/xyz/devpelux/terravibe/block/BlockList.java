package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.registry.Registry;

/** List of all the blocks. */
public class BlockList {
    /** Grinder block: Grinds an item to obtain another item. */
    public static final GrinderBlock GRINDER =
            Registry.register(Registry.BLOCK, GrinderBlock.ID, new GrinderBlock(GrinderBlock.getSettings()));

    /** Sweet potato crop block: Crop of the sweet potato. */
    public static final SweetPotatoCropBlock SWEET_POTATO_CROP =
            Registry.register(Registry.BLOCK, SweetPotatoCropBlock.ID, new SweetPotatoCropBlock(SweetPotatoCropBlock.getSettings()));

    /** Loads all the blocks. */
    public static void load() {}

    /** Loads all the render layer maps for the blocks. */
    public static void loadRenderLayerMaps() {
        BlockRenderLayerMap.INSTANCE.putBlock(SWEET_POTATO_CROP, RenderLayer.getCutout());
    }
}
