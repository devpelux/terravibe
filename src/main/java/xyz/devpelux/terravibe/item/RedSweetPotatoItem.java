package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;

/** Rare mutation of the sweet potato. */
public class RedSweetPotatoItem extends AliasedBlockItem {
    /** Identifier of the item. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "red_sweet_potato");

    /** Composting chance of the item. */
    public static final float COMPOSTING_CHANCE = 0.65f;

    /** Initializes a new {@link RedSweetPotatoItem}. */
    public RedSweetPotatoItem(Block block, Settings settings) {
        super(block, settings);
    }

    /** Gets the item settings. */
    public static @NotNull FabricItemSettings getSettings() {
        FoodComponent foodEffects = new FoodComponent.Builder()
                .hunger(3)
                .saturationModifier(0.7f)
                .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 180, 0), 1.0F)
                .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 140, 0), 1.0F)
                .build();

        return new FabricItemSettings()
                .maxCount(64)
                .food(foodEffects);
    }
}
