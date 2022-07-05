package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.util.Identifier;
import xyz.devpelux.terravibe.core.ModInfo;

/** Little berry with a purplish color, mutation of the sweet berries. */
public class DarkSweetBerriesItem extends AliasedBlockItem {
    /** Identifier of the item. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "dark_sweet_berries");

    /** Settings of the item. */
    public static final Settings SETTINGS;

    /** Food settings of the item. */
    public static final FoodComponent FOOD_SETTINGS;

    /** Composting chance of the item. */
    public static final float COMPOSTING_CHANCE = 0.3F;

    /** Initializes a new {@link DarkSweetBerriesItem}. */
    public DarkSweetBerriesItem(Block block, Settings settings) {
        super(block, settings);
    }

    static {
        FOOD_SETTINGS = new FoodComponent.Builder()
                .hunger(2)
                .saturationModifier(0.1F)
                .build();
        SETTINGS = new FabricItemSettings()
                .maxCount(64)
                .food(FOOD_SETTINGS)
                .group(TerravibeItemGroups.TERRAVIBE);
    }
}
