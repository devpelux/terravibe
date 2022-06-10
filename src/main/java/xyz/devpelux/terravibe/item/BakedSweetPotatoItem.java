package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;

/** Sweet potato, but baked. */
public class BakedSweetPotatoItem extends Item {
    /** Identifier of the item. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "baked_sweet_potato");

    /** Composting chance of the item. */
    public static final float COMPOSTING_CHANCE = 0.85f;

    /** Initializes a new {@link BakedSweetPotatoItem}. */
    public BakedSweetPotatoItem(Settings settings) {
        super(settings);
    }

    /** Gets the item settings. */
    public static @NotNull FabricItemSettings getSettings() {
        FoodComponent foodEffects = new FoodComponent.Builder()
                .hunger(5)
                .saturationModifier(0.7f)
                .build();

        return new FabricItemSettings()
                .maxCount(64)
                .food(foodEffects)
                .group(ItemGroupList.TERRAVIBE);
    }
}
