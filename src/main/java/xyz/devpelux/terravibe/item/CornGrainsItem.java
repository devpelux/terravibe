package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;

/** Some grains of corn. */
public class CornGrainsItem extends AliasedBlockItem {
    /** Identifier of the item. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "corn_grains");

    /** Composting chance of the item. */
    public static final float COMPOSTING_CHANCE = 0.05f;

    /** Initializes a new {@link CornGrainsItem}. */
    public CornGrainsItem(Block block, Settings settings) {
        super(block, settings);
    }

    /** Gets the item settings. */
    public static @NotNull FabricItemSettings getSettings() {
        return new FabricItemSettings()
                .maxCount(64)
                .group(TerravibeItemGroups.TERRAVIBE);
    }
}
