package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import xyz.devpelux.terravibe.core.ModInfo;

/** Edible purple berry, spongy, absorbent, typically used as a vegetable in cooking. */
public class EggplantItem extends Item {
    /** Identifier of the item. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "eggplant");

    /** Settings of the item. */
    public static final Settings SETTINGS;

    /** Food settings of the item. */
    public static final FoodComponent FOOD_SETTINGS;

    /** Composting chance of the item. */
    public static final float COMPOSTING_CHANCE = 0.65f;

    /** Initializes a new {@link EggplantItem}. */
    public EggplantItem(Settings settings) {
        super(settings);
    }

    static {
        FOOD_SETTINGS = new FoodComponent.Builder()
                .hunger(3)
                .saturationModifier(0.7F)
                .build();
        SETTINGS = new FabricItemSettings()
                .maxCount(64)
                .food(FOOD_SETTINGS)
                .group(TerravibeItemGroups.TERRAVIBE);
    }
}
