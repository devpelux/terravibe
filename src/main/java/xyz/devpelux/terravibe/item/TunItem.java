package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;

/** Container for "non-lava" fluids. */
public class TunItem extends BlockItem {
    /** Identifier of the item. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "tun");

    /** Initializes a new {@link TunItem}. */
    public TunItem(Block block, Settings settings) {
        super(block, settings);
    }

    /** Gets the item settings. */
    public static @NotNull FabricItemSettings getSettings() {
        return new FabricItemSettings()
                .maxCount(64)
                .group(ItemGroupList.TERRAVIBE);
    }
}
