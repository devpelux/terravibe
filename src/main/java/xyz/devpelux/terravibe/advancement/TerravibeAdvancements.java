package xyz.devpelux.terravibe.advancement;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.TickCriterion;
import xyz.devpelux.terravibe.core.Terravibe;

/**
 * List of all the advancements.
 */
public final class TerravibeAdvancements {
	/**
	 * "Side effect from food" advancement criterion.
	 */
	public static final TickCriterion SIDE_EFFECT_FROM_FOOD;

	/**
	 * "Make dairy products" advancement criterion.
	 */
	public static final TickCriterion MAKE_DAIRY_PRODUCTS;

	private TerravibeAdvancements() {
	}

	/**
	 * Loads all the advancements.
	 */
	public static void load() {
	}

	/**
	 * Registers a {@link TickCriterion} with the specified id.
	 */
	private static TickCriterion registerTickCriterion(String id) {
		return Criteria.register(new TickCriterion(Terravibe.identified(id)));
	}

	static {
		MAKE_DAIRY_PRODUCTS = registerTickCriterion("make_dairy_products");
		SIDE_EFFECT_FROM_FOOD = registerTickCriterion("side_effect_from_food");
	}
}
