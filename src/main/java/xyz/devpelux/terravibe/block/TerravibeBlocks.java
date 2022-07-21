package xyz.devpelux.terravibe.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.registry.FlattenableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/** List of all the blocks. */
public class TerravibeBlocks {
    private TerravibeBlocks() {}

    //Objects

    /** Crushes an item to obtain other items. */
    public static final Block MORTAR;

    /** Shreds a bunch of items to obtain something. */
    public static final Block SHREDDER;

    /** Tray used to make salt. */
    public static final Block TRAY;

    /** Container for "non-lava" fluids. */
    public static final Block TUN;

    //Plants

    /** Herb of the basil. */
    public static final Block BASIL_HERB;

    /** Crop of the beans. */
    public static final Block BEANS_CROP;

    /** Crop of the corn. */
    public static final Block CORN_CROP;

    /** Bush of dark sweet berries. */
    public static final Block DARK_SWEET_BERRY_BUSH;

    /** Crop of the eggplant. */
    public static final Block EGGPLANT_CROP;

    /** Crop of the kale. */
    public static final Block KALE_CROP;

    /** Crop of the lettuce. */
    public static final Block LETTUCE_CROP;

    /** Bush of nightlock berries, without thorns. */
    public static final Block NIGHTLOCK_BERRY_BUSH;

    /** Crop of the onion. */
    public static final Block ONION_CROP;

    /** Crop of the rice. */
    public static final Block RICE_CROP;

    /** Crop of the sweet potato. */
    public static final Block SWEET_POTATO_CROP;

    /** Crop of the tomato. */
    public static final Block TOMATO_CROP;

    //Other plants

    /** Birch mold that can always spread. */
    public static final Block BIRCH_MOLD;

    /** Dark mold that can always spread. */
    public static final Block DARK_MOLD;

    /** Glowing dark mold that can always spread. */
    public static final Block GLOWING_DARK_MOLD;

    //Tree blocks

    /** Main block of the opuntia. */
    public static final Block OPUNTIA;

    /** Flowering block of the opuntia. */
    public static final Block FLOWERING_OPUNTIA;

    //Terrain blocks

    /** A mud block excavated and flooded with water. */
    public static final Block FLOODED_MUD;

    /** Loads all the blocks. */
    public static void load() {
        FlattenableBlockRegistry.register(Blocks.MUD, FLOODED_MUD.getDefaultState());
        FlattenableBlockRegistry.register(FLOODED_MUD, Blocks.MUD.getDefaultState());
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
        BlockRenderLayerMap.INSTANCE.putBlock(BASIL_HERB, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BEANS_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BIRCH_MOLD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CORN_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DARK_SWEET_BERRY_BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DARK_MOLD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(EGGPLANT_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FLOWERING_OPUNTIA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(GLOWING_DARK_MOLD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(KALE_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(LETTUCE_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(NIGHTLOCK_BERRY_BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ONION_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(OPUNTIA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RICE_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(SWEET_POTATO_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TOMATO_CROP, RenderLayer.getCutout());
    }

    /** Registers the specified block with the specified id. */
    private static <T extends Block> T register(Identifier id, T block) {
        return Registry.register(Registry.BLOCK, id, block);
    }

    static {
        MORTAR = register(MortarBlock.ID, new MortarBlock(MortarBlock.SETTINGS));
        SHREDDER = register(ShredderBlock.ID, new ShredderBlock(ShredderBlock.SETTINGS));
        TRAY = register(TrayBlock.ID, new TrayBlock(TrayBlock.SETTINGS));
        TUN = register(TunBlock.ID, new TunBlock(TunBlock.SETTINGS));

        BASIL_HERB = register(BasilHerbBlock.ID, new BasilHerbBlock(BasilHerbBlock.SETTINGS));
        BEANS_CROP = register(BeansCropBlock.ID, new BeansCropBlock(BeansCropBlock.SETTINGS));
        CORN_CROP = register(CornCropBlock.ID, new CornCropBlock(CornCropBlock.SETTINGS));
        DARK_SWEET_BERRY_BUSH = register(DarkSweetBerryBushBlock.ID, new DarkSweetBerryBushBlock(DarkSweetBerryBushBlock.SETTINGS));
        EGGPLANT_CROP = register(EggplantCropBlock.ID, new EggplantCropBlock(EggplantCropBlock.SETTINGS));
        KALE_CROP = register(KaleCropBlock.ID, new KaleCropBlock(KaleCropBlock.SETTINGS));
        LETTUCE_CROP = register(LettuceCropBlock.ID, new LettuceCropBlock(LettuceCropBlock.SETTINGS));
        NIGHTLOCK_BERRY_BUSH = register(NightlockBerryBushBlock.ID, new NightlockBerryBushBlock(NightlockBerryBushBlock.SETTINGS));
        ONION_CROP = register(OnionCropBlock.ID, new OnionCropBlock(OnionCropBlock.SETTINGS));
        RICE_CROP = register(RiceCropBlock.ID, new RiceCropBlock(RiceCropBlock.SETTINGS));
        SWEET_POTATO_CROP = register(SweetPotatoCropBlock.ID, new SweetPotatoCropBlock(SweetPotatoCropBlock.SETTINGS));
        TOMATO_CROP = register(TomatoCropBlock.ID, new TomatoCropBlock(TomatoCropBlock.SETTINGS));

        BIRCH_MOLD = register(BirchMoldBlock.ID, new BirchMoldBlock(BirchMoldBlock.SETTINGS));
        DARK_MOLD = register(DarkMoldBlock.ID, new DarkMoldBlock(DarkMoldBlock.SETTINGS));
        GLOWING_DARK_MOLD = register(GlowingDarkMoldBlock.ID, new GlowingDarkMoldBlock(GlowingDarkMoldBlock.SETTINGS));

        OPUNTIA = register(OpuntiaBlock.ID, new OpuntiaBlock(OpuntiaBlock.SETTINGS));
        FLOWERING_OPUNTIA = register(FloweringOpuntiaBlock.ID, new FloweringOpuntiaBlock(FloweringOpuntiaBlock.SETTINGS));

        FLOODED_MUD = register(FloodedMudBlock.ID, new FloodedMudBlock(FloodedMudBlock.SETTINGS));
    }
}
