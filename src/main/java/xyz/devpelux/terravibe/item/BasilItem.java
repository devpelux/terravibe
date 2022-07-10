package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.util.Identifier;
import xyz.devpelux.terravibe.core.ModInfo;

/** Basil spice. */
public class BasilItem extends AliasedBlockItem {
    /** Identifier of the item. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "basil");

    /** Settings of the item. */
    public static final Settings SETTINGS;

    /** Composting chance of the item. */
    public static final float COMPOSTING_CHANCE = 0.3f;

    /** Initializes a new {@link BasilItem}. */
    public BasilItem(Block block, Settings settings) {
        super(block, settings);
    }

    static {
        SETTINGS = new FabricItemSettings()
                .maxCount(64)
                .group(TerravibeItemGroups.TERRAVIBE);
    }
}
