package xyz.devpelux.terravibe.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.devpelux.terravibe.core.Util;

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

    /** Flooded mud block: A mud block excavated and flooded with water. */
    public static final FloodedMudBlock FLOODED_MUD;

    /** Bean crop block: Crop of the beans. */
    public static final BeansCropBlock BEANS_CROP;

    /** Dark sweet berry bush block: Bush of dark sweet berries. */
    public static final DarkSweetBerryBushBlock DARK_SWEET_BERRY_BUSH;

    /** Eggplant crop block: Crop of the eggplant. */
    public static final EggplantCropBlock EGGPLANT_CROP;

    /** Kale crop block: Crop of the kale. */
    public static final KaleCropBlock KALE_CROP;

    /** Lettuce crop block: Crop of the lettuce. */
    public static final LettuceCropBlock LETTUCE_CROP;

    /** Nightlock berry bush block: Bush of nightlock berries, without thorns. */
    public static final NightlockBerryBushBlock NIGHTLOCK_BERRY_BUSH;

    /** Onion crop block: Crop of the onion. */
    public static final OnionCropBlock ONION_CROP;

    /** Rice crop block: Crop of the rice. */
    public static final RiceCropBlock RICE_CROP;

    /** Sweet potato crop block: Crop of the sweet potato. */
    public static final SweetPotatoCropBlock SWEET_POTATO_CROP;

    /** Tomato crop block: Crop of the tomato. */
    public static final TomatoCropBlock TOMATO_CROP;

    /** Loads all the blocks. */
    public static void load() {
        Util.registerExcavable(Blocks.MUD, FLOODED_MUD.getDefaultState());
        FloodedCropBlock.registerFloodedFertileBlock(FLOODED_MUD);
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
        BlockRenderLayerMap.INSTANCE.putBlock(BEANS_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DARK_SWEET_BERRY_BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(EGGPLANT_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(KALE_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(LETTUCE_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(NIGHTLOCK_BERRY_BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ONION_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RICE_CROP, RenderLayer.getCutout());
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

        FLOODED_MUD = register(FloodedMudBlock.ID, new FloodedMudBlock(FloodedMudBlock.getSettings()));

        BEANS_CROP = register(BeansCropBlock.ID, new BeansCropBlock(BeansCropBlock.getSettings()));
        DARK_SWEET_BERRY_BUSH = register(DarkSweetBerryBushBlock.ID, new DarkSweetBerryBushBlock(DarkSweetBerryBushBlock.getSettings()));
        EGGPLANT_CROP = register(EggplantCropBlock.ID, new EggplantCropBlock(EggplantCropBlock.getSettings()));
        KALE_CROP = register(KaleCropBlock.ID, new KaleCropBlock(KaleCropBlock.getSettings()));
        LETTUCE_CROP = register(LettuceCropBlock.ID, new LettuceCropBlock(LettuceCropBlock.getSettings()));
        NIGHTLOCK_BERRY_BUSH = register(NightlockBerryBushBlock.ID, new NightlockBerryBushBlock(NightlockBerryBushBlock.getSettings()));
        ONION_CROP = register(OnionCropBlock.ID, new OnionCropBlock(OnionCropBlock.getSettings()));
        RICE_CROP = register(RiceCropBlock.ID, new RiceCropBlock(RiceCropBlock.getSettings()));
        SWEET_POTATO_CROP = register(SweetPotatoCropBlock.ID, new SweetPotatoCropBlock(SweetPotatoCropBlock.getSettings()));
        TOMATO_CROP = register(TomatoCropBlock.ID, new TomatoCropBlock(TomatoCropBlock.getSettings()));
    }
}
