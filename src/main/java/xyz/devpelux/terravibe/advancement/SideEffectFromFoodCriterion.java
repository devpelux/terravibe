package xyz.devpelux.terravibe.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import xyz.devpelux.terravibe.core.Terravibe;

/**
 * "Side effects from food" advancement criterion.
 */
public class SideEffectFromFoodCriterion extends AbstractCriterion<SideEffectFromFoodCriterion.Conditions> {
	private static final Identifier ID = new Identifier(Terravibe.ID, "side_effect_from_food");

	/**
	 * Loads the criterion conditions from json file.
	 */
	@Override
	protected Conditions conditionsFromJson(JsonObject obj, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
		return new Conditions(playerPredicate);
	}

	/**
	 * Gets the identifier of the criterion.
	 */
	@Override
	public Identifier getId() {
		return ID;
	}

	/**
	 * Triggers the criterion by checking the conditions.
	 */
	public void trigger(ServerPlayerEntity player) {
		trigger(player, Conditions::matches);
	}


	/**
	 * Conditions of the criterion.
	 * This criterion has no conditions.
	 */
	public static class Conditions extends AbstractCriterionConditions {
		/**
		 * Initializes a new {@link Conditions}.
		 */
		public Conditions(EntityPredicate.Extended playerPredicate) {
			super(ID, playerPredicate);
		}

		/**
		 * Checks the conditions of the criterion.
		 */
		public boolean matches() {
			return true;
		}
	}
}
