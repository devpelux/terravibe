package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.util.Identifier;
import xyz.devpelux.terravibe.core.ModInfo;

/** Item of the flowering block of the opuntia. */
public class FloweringOpuntiaItem extends AliasedBlockItem {
    /** Identifier of the item. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "flowering_opuntia");

    /** Settings of the item. */
    public static final Settings SETTINGS;

    /** Composting chance of the item. */
    public static final float COMPOSTING_CHANCE = 0.6f;

    /** Initializes a new {@link FloweringOpuntiaItem}. */
    public FloweringOpuntiaItem(Block block, Settings settings) {
        super(block, settings);
    }

    static {
        SETTINGS = new FabricItemSettings()
                .maxCount(64)
                .group(TerravibeItemGroups.TERRAVIBE);
    }
}
