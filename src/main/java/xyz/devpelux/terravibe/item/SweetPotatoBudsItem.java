package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.util.Identifier;
import xyz.devpelux.terravibe.core.ModInfo;

/** Buds of sweet potato. */
public class SweetPotatoBudsItem extends AliasedBlockItem {
    /** Identifier of the item. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "sweet_potato_buds");

    /** Settings of the item. */
    public static final Settings SETTINGS;

    /** Food settings of the item. */
    public static final FoodComponent FOOD_SETTINGS;

    /** Composting chance of the item. */
    public static final float COMPOSTING_CHANCE = 0.4f;

    /** Initializes a new {@link SweetPotatoBudsItem}. */
    public SweetPotatoBudsItem(Block block, Settings settings) {
        super(block, settings);
    }

    static {
        FOOD_SETTINGS = new FoodComponent.Builder()
                .hunger(1)
                .saturationModifier(0.1f)
                .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 400, 1), 1f)
                .build();
        SETTINGS = new FabricItemSettings()
                .maxCount(64)
                .food(FOOD_SETTINGS);
    }
}
