package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;

/** Edible purple berry, spongy, absorbent, typically used as a vegetable in cooking. */
public class EggplantItem extends Item {
    /** Identifier of the item. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "eggplant");

    /** Composting chance of the item. */
    public static final float COMPOSTING_CHANCE = 0.65f;

    /** Initializes a new {@link EggplantItem}. */
    public EggplantItem(Settings settings) {
        super(settings);
    }

    /** Gets the item settings. */
    public static @NotNull FabricItemSettings getSettings() {
        FoodComponent foodEffects = new FoodComponent.Builder()
                .hunger(3)
                .saturationModifier(0.7F)
                .build();

        return new FabricItemSettings()
                .maxCount(64)
                .food(foodEffects)
                .group(TerravibeItemGroups.TERRAVIBE);
    }
}
