package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.item.ItemConvertible;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.item.TerravibeItems;

/** Crop of the rice. */
public class RiceCropBlock extends FloodedCropBlock {
    /** Identifier of the block. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "rice_crop");

    /** Initializes a new {@link RiceCropBlock}. */
    public RiceCropBlock(Settings settings) {
        super(settings);
    }

    /** Gets the block settings. */
    public static @NotNull FabricBlockSettings getSettings() {
        return FabricBlockSettings.of(Material.PLANT)
                .nonOpaque()
                .noCollision()
                .ticksRandomly()
                .breakInstantly()
                .sounds(BlockSoundGroup.CROP);
    }

    /** Gets the seeds item of the block. */
    @Override
    public ItemConvertible getSeedsItem() {
        return TerravibeItems.GRAINS_OF_RICE;
    }
}
