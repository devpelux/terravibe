package xyz.devpelux.terravibe.advancement;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.TickCriterion;
import xyz.devpelux.terravibe.core.Terravibe;

/**
 * List of all the advancement criteria.
 */
public final class TerravibeCriteria {
	/**
	 * "side_effects_from_food" criterion.
	 */
	public static final SideEffectsFromFoodCriterion SIDE_EFFECTS_FROM_FOOD;

	/**
	 * "make_dairy_products" criterion.
	 */
	public static final TickCriterion MAKE_DAIRY_PRODUCTS;

	/**
	 * Registers a {@link TickCriterion} with the specified id.
	 */
	private static TickCriterion registerTickCriterion(String id) {
		return Criteria.register(new TickCriterion(Terravibe.identified(id)));
	}

	static {
		MAKE_DAIRY_PRODUCTS = registerTickCriterion("make_dairy_products");
		SIDE_EFFECTS_FROM_FOOD = Criteria.register(new SideEffectsFromFoodCriterion());
	}
}
