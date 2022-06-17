package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;

/** Vegetable mostly used to make soups. */
public class KaleItem extends Item {
    /** Identifier of the item. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "kale");

    /** Composting chance of the item. */
    public static final float COMPOSTING_CHANCE = 0.65f;

    /** Initializes a new {@link KaleItem}. */
    public KaleItem(Settings settings) {
        super(settings);
    }

    /** Gets the item settings. */
    public static @NotNull FabricItemSettings getSettings() {
        FoodComponent foodEffects = new FoodComponent.Builder()
                .hunger(2)
                .saturationModifier(0.4f)
                .build();

        return new FabricItemSettings()
                .maxCount(64)
                .food(foodEffects)
                .group(TerravibeItemGroups.TERRAVIBE);
    }
}
