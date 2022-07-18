package xyz.devpelux.terravibe.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

/** List of all the food effects. */
public class TerravibeFoodComponents {
    private TerravibeFoodComponents() {}

    public static final FoodComponent BAKED_SWEET_POTATO;
    public static final FoodComponent BEANS;
    public static final FoodComponent DARK_SWEET_BERRIES;
    public static final FoodComponent EGGPLANT;
    public static final FoodComponent KALE;
    public static final FoodComponent LETTUCE_LEAVES;
    public static final FoodComponent NIGHTLOCK_BERRIES;
    public static final FoodComponent ONION;
    public static final FoodComponent PRICKLY_PEAR;
    public static final FoodComponent RED_SWEET_POTATO;
    public static final FoodComponent SWEET_POTATO_BUDS;
    public static final FoodComponent SWEET_POTATO;
    public static final FoodComponent TOMATO;

    static {
        BAKED_SWEET_POTATO = new FoodComponent.Builder().hunger(5).saturationModifier(0.7f).build();
        BEANS = new FoodComponent.Builder().hunger(1).saturationModifier(0.4f).build();
        DARK_SWEET_BERRIES = new FoodComponent.Builder().hunger(2).saturationModifier(0.1F).build();
        EGGPLANT = new FoodComponent.Builder().hunger(3).saturationModifier(0.7F).build();
        KALE = new FoodComponent.Builder().hunger(2).saturationModifier(0.4f).build();
        LETTUCE_LEAVES = new FoodComponent.Builder().hunger(1).saturationModifier(0.3F).build();
        NIGHTLOCK_BERRIES = new FoodComponent.Builder().hunger(2).saturationModifier(0.1F).build();
        ONION = new FoodComponent.Builder().hunger(1).saturationModifier(0.4F).build();
        PRICKLY_PEAR = new FoodComponent.Builder().hunger(3).saturationModifier(0.7F).build();
        RED_SWEET_POTATO = new FoodComponent.Builder().hunger(3).saturationModifier(0.7f)
                .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 180, 0), 1.0F)
                .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 140, 0), 1.0F)
                .build();
        SWEET_POTATO_BUDS = new FoodComponent.Builder().hunger(1).saturationModifier(0.1f)
                .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 400, 1), 1f)
                .build();
        SWEET_POTATO = new FoodComponent.Builder().hunger(1).saturationModifier(0.4f).build();
        TOMATO = new FoodComponent.Builder().hunger(3).saturationModifier(0.7F).build();
    }
}
