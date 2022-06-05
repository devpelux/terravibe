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

/** Bud of sweet potato. */
public class SweetPotatoBudItem extends AliasedBlockItem {
    /** Identifier of the item. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "sweet_potato_bud");

    /** Composting chance of the item. */
    public static final float COMPOSTING_CHANCE = 0.3f;

    /** Initializes a new {@link SweetPotatoBudItem}. */
    public SweetPotatoBudItem(Block block, Settings settings) {
        super(block, settings);
    }

    /** Gets the item settings. */
    public static @NotNull FabricItemSettings getSettings() {
        FoodComponent foodEffects = new FoodComponent.Builder()
                .hunger(1)
                .saturationModifier(0.2f)
                .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 80, 0), 0.1f)
                .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 240, 2), 1f)
                .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 240, 0), 1f)
                .statusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 240, 0), 1f)
                .statusEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 1, 0), 0.4f)
                .build();

        return new FabricItemSettings()
                .maxCount(64)
                .food(foodEffects);
    }
}
