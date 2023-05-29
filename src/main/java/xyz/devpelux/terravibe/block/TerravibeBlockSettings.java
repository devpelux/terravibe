package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.sound.BlockSoundGroup;

/**
 * List of all the block settings generators.
 */
public final class TerravibeBlockSettings {
    private TerravibeBlockSettings() {
    }

    public static FabricBlockSettings basil_herb() {
        return FabricBlockSettings.copyOf(Blocks.GRASS);
    }

    public static FabricBlockSettings beans_crop() {
        return crop();
    }

    public static FabricBlockSettings birch_mold() {
        return mold();
    }

    public static FabricBlockSettings burned_glowing_dark_mold_dust_jar() {
        return jar().luminance(s -> 4 * s.get(JarBlock.LEVEL));
    }

    public static FabricBlockSettings cheese_wheel() {
        return FabricBlockSettings.copyOf(Blocks.CAKE);
    }

    public static FabricBlockSettings corn_crop() {
        return crop().offset(AbstractBlock.OffsetType.XZ);
    }

    public static FabricBlockSettings dark_mold() {
        return mold();
    }

    public static FabricBlockSettings dark_sweet_berry_bush() {
        return crop().sounds(BlockSoundGroup.SWEET_BERRY_BUSH);
    }

    public static FabricBlockSettings dust_jar() {
        return jar();
    }

    public static FabricBlockSettings eggplant_crop() {
        return crop();
    }

    public static FabricBlockSettings excavated_mud() {
        return FabricBlockSettings.copyOf(Blocks.DIRT)
                .mapColor(MapColor.TERRACOTTA_CYAN)
                .sounds(BlockSoundGroup.MUD);
    }

    public static FabricBlockSettings flowering_opuntia() {
        return FabricBlockSettings.copyOf(Blocks.CACTUS);
    }

    public static FabricBlockSettings gillyweed_alga() {
        return FabricBlockSettings.copyOf(Blocks.SEAGRASS);
    }

    public static FabricBlockSettings glowing_dark_mold() {
        return mold().luminance(s -> s.get(GlowingDarkMoldBlock.AGE) == 0 ? 1 : 3);
    }

    public static FabricBlockSettings glowing_dark_mold_dust_jar() {
        return jar().luminance(s -> 2 * s.get(JarBlock.LEVEL));
    }

    public static FabricBlockSettings gorgonzola_wheel() {
        return cheese_wheel();
    }

    public static FabricBlockSettings jar() {
        return FabricBlockSettings.copyOf(Blocks.FLOWER_POT);
    }

    public static FabricBlockSettings mold_dust_jar() {
        return jar();
    }

    public static FabricBlockSettings kale() {
        return crop();
    }

    public static FabricBlockSettings lettuce() {
        return crop();
    }

    public static FabricBlockSettings milk_cauldron() {
        return FabricBlockSettings.copyOf(Blocks.CAULDRON);
    }

    public static FabricBlockSettings mortar() {
        return FabricBlockSettings.copyOf(Blocks.FLOWER_POT).mapColor(MapColor.OAK_TAN);
    }

    public static FabricBlockSettings nightlock_berry_bush() {
        return crop().mapColor(MapColor.PURPLE).sounds(BlockSoundGroup.SWEET_BERRY_BUSH);
    }

    public static FabricBlockSettings nightshade_fern() {
        return crop().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ);
    }

    public static FabricBlockSettings onion_crop() {
        return crop();
    }

    public static FabricBlockSettings opuntia() {
        return FabricBlockSettings.copyOf(Blocks.CACTUS);
    }

    public static FabricBlockSettings pizza_four_cheese() {
        return pizza_margherita();
    }

    public static FabricBlockSettings pizza_margherita() {
        return FabricBlockSettings.copyOf(Blocks.CAKE);
    }

    public static FabricBlockSettings rice_crop() {
        return crop();
    }

    public static FabricBlockSettings shredder() {
        return FabricBlockSettings.create()
                .mapColor(MapColor.ORANGE)
                .requiresTool()
                .strength(3.0F, 6.0F)
                .sounds(BlockSoundGroup.COPPER);
    }

    public static FabricBlockSettings sweet_potato_crop() {
        return crop();
    }

    public static FabricBlockSettings thistle_plant() {
        return FabricBlockSettings.create()
                .mapColor(MapColor.DARK_GREEN)
                .noCollision()
                .breakInstantly()
                .sounds(BlockSoundGroup.GRASS)
                .offset(AbstractBlock.OffsetType.XYZ)
                .pistonBehavior(PistonBehavior.DESTROY);
    }

    public static FabricBlockSettings tomato_crop() {
        return crop();
    }

    public static FabricBlockSettings tray() {
        return FabricBlockSettings.create()
                .mapColor(MapColor.BLACK)
                .breakInstantly()
                .sounds(BlockSoundGroup.STONE);
    }

    public static FabricBlockSettings tun() {
        return FabricBlockSettings.copyOf(Blocks.BARREL);
    }


    /**
     * Generates block settings for a generic crop.
     */
    private static FabricBlockSettings crop() {
        return FabricBlockSettings.create()
                .mapColor(MapColor.DARK_GREEN)
                .noCollision()
                .ticksRandomly()
                .breakInstantly()
                .sounds(BlockSoundGroup.CROP)
                .pistonBehavior(PistonBehavior.DESTROY);
    }

    /**
     * Generates block settings for a generic mold.
     */
    private static FabricBlockSettings mold() {
        return FabricBlockSettings.create()
                .mapColor(MapColor.BLACK)
                .noCollision()
                .breakInstantly()
                .sounds(BlockSoundGroup.MOSS_BLOCK)
                .offset(AbstractBlock.OffsetType.XYZ)
                .pistonBehavior(PistonBehavior.DESTROY);
    }
}
