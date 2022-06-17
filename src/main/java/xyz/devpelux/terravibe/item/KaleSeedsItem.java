package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;

/** Seeds of kale. */
public class KaleSeedsItem extends AliasedBlockItem {
    /** Identifier of the item. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "kale_seeds");

    /** Composting chance of the item. */
    public static final float COMPOSTING_CHANCE = 0.3f;

    /** Initializes a new {@link KaleSeedsItem}. */
    public KaleSeedsItem(Block block, Settings settings) {
        super(block, settings);
    }

    /** Gets the item settings. */
    public static @NotNull FabricItemSettings getSettings() {
        return new FabricItemSettings()
                .maxCount(64)
                .group(TerravibeItemGroups.TERRAVIBE);
    }
}
