package xyz.devpelux.terravibe.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/** List of all the blocks. */
public class BlockList {
    /** Mortar block: Crushes an item to obtain another item. */
    public static final MortarBlock MORTAR;

    /** Sweet potato crop block: Crop of the sweet potato. */
    public static final SweetPotatoCropBlock SWEET_POTATO_CROP;

    /** Loads all the blocks. */
    public static void load() {}

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
        SWEET_POTATO_CROP = register(SweetPotatoCropBlock.ID, new SweetPotatoCropBlock(SweetPotatoCropBlock.getSettings()));
    }
}
