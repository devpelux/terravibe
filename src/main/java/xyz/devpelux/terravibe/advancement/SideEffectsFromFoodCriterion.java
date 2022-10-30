package xyz.devpelux.terravibe.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import xyz.devpelux.terravibe.core.Terravibe;

/**
 * Criterion triggered after getting side effects from food.
 * Checks the consumed item.
 */
public class SideEffectsFromFoodCriterion extends AbstractCriterion<SideEffectsFromFoodCriterion.Conditions> {
	private static final Identifier ID = new Identifier(Terravibe.ID, "side_effects_from_food");

	/**
	 * Loads the criterion conditions from json file.
	 */
	@Override
	protected Conditions conditionsFromJson(JsonObject obj, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
		return new Conditions(playerPredicate, ItemPredicate.fromJson(obj.get("item")));
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
	public void trigger(ServerPlayerEntity player, ItemStack stack) {
		trigger(player, conditions -> conditions.matches(stack));
	}


	/**
	 * Conditions of the criterion.
	 */
	public static class Conditions extends AbstractCriterionConditions {
		private final ItemPredicate itemPredicate;

		/**
		 * Initializes a new {@link Conditions}.
		 */
		public Conditions(EntityPredicate.Extended playerPredicate, ItemPredicate itemPredicate) {
			super(ID, playerPredicate);
			this.itemPredicate = itemPredicate;
		}

		/**
		 * Checks the conditions of the criterion.
		 */
		public boolean matches(ItemStack stack) {
			return this.itemPredicate.test(stack);
		}

		/**
		 * Converts the conditions into a json object.
		 */
		@Override
		public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
			JsonObject obj = super.toJson(predicateSerializer);
			obj.add("item", this.itemPredicate.toJson());
			return obj;
		}
	}
}
