package xyz.devpelux.terravibe.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import xyz.devpelux.terravibe.item.SideEffectFoodItem.SideEffects;

/**
 * List of all the food effects.
 */
public final class TerravibeFoodEffects {
	//Food components
	
	public static final FoodComponent BAKED_SWEET_POTATO;
	public static final FoodComponent BEANS;
	public static final FoodComponent CHEESE;
	public static final FoodComponent DARK_SWEET_BERRIES;
	public static final FoodComponent EGGPLANT;
	public static final FoodComponent GILLYWEED;
	public static final FoodComponent GORGONZOLA;
	public static final FoodComponent KALE;
	public static final FoodComponent LEMON;
	public static final FoodComponent LEMON_JUICE;
	public static final FoodComponent LETTUCE_LEAVES;
	public static final FoodComponent MOZZARELLA;
	public static final FoodComponent NIGHTLOCK_BERRIES;
	public static final FoodComponent NIGHTSHADE_FERN_BLUEBERRIES;
	public static final FoodComponent ONION;
	public static final FoodComponent PIZZA_SLICE_FOUR_CHEESE;
	public static final FoodComponent PIZZA_SLICE_MARGHERITA;
	public static final FoodComponent POTTAGE;
	public static final FoodComponent PRICKLY_PEAR;
	public static final FoodComponent RED_SWEET_POTATO;
	public static final FoodComponent SALAD;
	public static final FoodComponent SALAD_FULL;
	public static final FoodComponent SALAD_MIXED;
	public static final FoodComponent SALAD_RICH;
	public static final FoodComponent SALAD_SIMPLE;
	public static final FoodComponent SWEET_POTATO;
	public static final FoodComponent SWEET_POTATO_BUDS;
	public static final FoodComponent TOMATO;

	//Side effects

	public static final SideEffects GILLYWEED_SIDE_EFFECTS;
	public static final SideEffects NIGHTSHADE_FERN_BLUEBERRIES_SIDE_EFFECTS;

	static {
		BAKED_SWEET_POTATO = new FoodComponent.Builder().hunger(5).saturationModifier(0.7f).build();
		BEANS = new FoodComponent.Builder().hunger(1).saturationModifier(0.1f).build();
		CHEESE = new FoodComponent.Builder().hunger(3).saturationModifier(0.7f).build();
		DARK_SWEET_BERRIES = new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).build();
		EGGPLANT = new FoodComponent.Builder().hunger(3).saturationModifier(0.6f).build();
		GILLYWEED = new FoodComponent.Builder().hunger(1).saturationModifier(0.1f)
				.statusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 600, 0), 1f) //30s
				.alwaysEdible()
				.build();
		GORGONZOLA = new FoodComponent.Builder().hunger(4).saturationModifier(0.7f).build();
		KALE = new FoodComponent.Builder().hunger(2).saturationModifier(0.4f).build();
		LEMON = new FoodComponent.Builder().hunger(3).saturationModifier(0.4f).build();
		LEMON_JUICE = new FoodComponent.Builder().hunger(4).saturationModifier(0.6f).build();
		LETTUCE_LEAVES = new FoodComponent.Builder().hunger(2).saturationModifier(0.3f).build();
		MOZZARELLA = new FoodComponent.Builder().hunger(3).saturationModifier(0.6f).build();
		NIGHTLOCK_BERRIES = new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).build();
		NIGHTSHADE_FERN_BLUEBERRIES = new FoodComponent.Builder().hunger(1).saturationModifier(0.1f)
				.statusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 300, 0), 1f) //15s
				.alwaysEdible()
				.build();
		ONION = new FoodComponent.Builder().hunger(1).saturationModifier(0.4f).build();
		PIZZA_SLICE_FOUR_CHEESE = new FoodComponent.Builder().hunger(4).saturationModifier(0.4f).build();
		PIZZA_SLICE_MARGHERITA = new FoodComponent.Builder().hunger(2).saturationModifier(0.2f).build();
		POTTAGE = new FoodComponent.Builder().hunger(9).saturationModifier(0.9f).build();
		PRICKLY_PEAR = new FoodComponent.Builder().hunger(4).saturationModifier(0.6f).build();
		RED_SWEET_POTATO = new FoodComponent.Builder().hunger(3).saturationModifier(0.7f)
				.statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 180, 0), 1f) //9s
				.statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 140, 0), 1f) //7s
				.alwaysEdible()
				.build();
		SALAD = new FoodComponent.Builder().hunger(6).saturationModifier(0.6f).build();
		SALAD_FULL = new FoodComponent.Builder().hunger(8).saturationModifier(0.8f).build();
		SALAD_MIXED = new FoodComponent.Builder().hunger(6).saturationModifier(0.7f).build();
		SALAD_RICH = new FoodComponent.Builder().hunger(7).saturationModifier(0.7f).build();
		SALAD_SIMPLE = new FoodComponent.Builder().hunger(5).saturationModifier(0.6f).build();
		SWEET_POTATO = new FoodComponent.Builder().hunger(1).saturationModifier(0.4f).build();
		SWEET_POTATO_BUDS = new FoodComponent.Builder().hunger(1).saturationModifier(0.1f)
				.statusEffect(new StatusEffectInstance(StatusEffects.POISON, 400, 1), 1f) //20s
				.build();
		TOMATO = new FoodComponent.Builder().hunger(3).saturationModifier(0.6f).build();

		GILLYWEED_SIDE_EFFECTS = new SideEffects(1000)
				.sideEffect(new StatusEffectInstance(StatusEffects.POISON, 200, 0), 0.3f); //10s
		NIGHTSHADE_FERN_BLUEBERRIES_SIDE_EFFECTS = new SideEffects(600)
				.sideEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 100, 0), 0.2f); //5s
	}
}
