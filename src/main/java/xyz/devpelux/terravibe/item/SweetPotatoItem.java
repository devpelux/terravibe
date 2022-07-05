package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.util.Identifier;
import xyz.devpelux.terravibe.core.ModInfo;

/** Mutation of the potato. */
public class SweetPotatoItem extends AliasedBlockItem {
    /** Identifier of the item. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "sweet_potato");

    /** Settings of the item. */
    public static final Settings SETTINGS;

    /** Food settings of the item. */
    public static final FoodComponent FOOD_SETTINGS;

    /** Composting chance of the item. */
    public static final float COMPOSTING_CHANCE = 0.65f;

    /** Initializes a new {@link SweetPotatoItem}. */
    public SweetPotatoItem(Block block, Settings settings) {
        super(block, settings);
    }

    static {
        FOOD_SETTINGS = new FoodComponent.Builder()
                .hunger(1)
                .saturationModifier(0.4f)
                .build();
        SETTINGS = new FabricItemSettings()
                .maxCount(64)
                .food(FOOD_SETTINGS)
                .group(TerravibeItemGroups.TERRAVIBE);
    }
}
