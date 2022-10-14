package xyz.devpelux.terravibe.advancement;

import net.minecraft.advancement.criterion.Criteria;

/**
 * List of all the advancements.
 */
public final class TerravibeAdvancements {
	/**
	 * "Side effects from food" advancement criterion.
	 */
	public static final SideEffectFromFoodCriterion SIDE_EFFECT_FROM_FOOD;

	private TerravibeAdvancements() {
	}

	/**
	 * Loads all the advancements.
	 */
	public static void load() {
	}

	static {
		SIDE_EFFECT_FROM_FOOD = Criteria.register(new SideEffectFromFoodCriterion());
	}
}
