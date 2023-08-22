package xyz.devpelux.terravibe.item;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import xyz.devpelux.terravibe.advancement.TerravibeCriteria;

import java.util.*;

/**
 * Food with side effects that are applied if the item is reused
 * before a specified cooldown ends.
 */
public class SideEffectFoodItem extends Item {
	/**
	 * List of player ids that have a cooldown for this item.
	 */
	private final Map<Integer, Integer> COOLDOWNS = new HashMap<>();
	/**
	 * List of side effects of the item.
	 */
	private final SideEffects sideEffects;
	/**
	 * Stores the current time.
	 */
	private int time = 0;

	/**
	 * Initializes a new instance.
	 */
	public SideEffectFoodItem(Settings settings, SideEffects sideEffects) {
		super(settings);
		this.sideEffects = Objects.requireNonNullElse(sideEffects, new SideEffects(0));
		ServerTickEvents.END_SERVER_TICK.register((s) -> tick(s.getTicks()));
	}

	/**
	 * Executed at every tick of the server on server side.
	 */
	private void tick(int serverTime) {
		//Stores the current server time.
		this.time = serverTime;
		//Every 7 minutes, cleanup the cooldown list.
		if (time % 8400L == 0L) COOLDOWNS.entrySet().removeIf(element -> element.getValue() <= time);
	}

	/**
	 * Sets the cooldown of the item for the specified player.
	 */
	public final void setCooldown(PlayerEntity player, int cooldownTime) {
		COOLDOWNS.put(player.getId(), time + cooldownTime);
	}

	/**
	 * Checks if the item has a cooldown for the specified player.
	 */
	public final boolean isCoolingDown(PlayerEntity player) {
		int cooldownTime = Math.max(COOLDOWNS.getOrDefault(player.getId(), 0) - time, 0);
		return cooldownTime > 0;
	}

	/**
	 * Executed when an entity finishes using the item.
	 * Applies the side effects if the cooldown is not respected and sets a new cooldown.
	 */
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity entity) {
		if (isFood() && entity instanceof ServerPlayerEntity player) {
			if (isCoolingDown(player)) {
				//Applies the side effects with their chance if the item is in cooldown for the player.
				List<Pair<StatusEffectInstance, Float>> sideEffectList = sideEffects.getSideEffects();
				boolean sideEffectsApplied = false;
				for (Pair<StatusEffectInstance, Float> sideEffect : sideEffectList) {
					if (sideEffect.getFirst() != null && world.getRandom().nextFloat() < sideEffect.getSecond()) {
						player.addStatusEffect(new StatusEffectInstance(sideEffect.getFirst()));
						sideEffectsApplied = true;
					}
				}

				//Triggers "Side effects from food" advancement criterion.
				if (sideEffectsApplied) TerravibeCriteria.SIDE_EFFECTS_FROM_FOOD.trigger(player, stack);
			}

			//Sets a cooldown for the player that used the item.
			setCooldown(player, sideEffects.getCooldownTime());
		}
		return super.finishUsing(stack, world, entity);
	}


	/**
	 * Represents a list of side effects.
	 */
	public static class SideEffects {
		/**
		 * List of side effects.
		 */
		private final List<Pair<StatusEffectInstance, Float>> sideEffectList = new ArrayList<>();

		/**
		 * Cooldown to get the side effects.
		 */
		private final int cooldownTime;

		/**
		 * Initializes a new instance.
		 */
		public SideEffects(int cooldownTime) {
			this.cooldownTime = Math.max(cooldownTime, 0);
		}

		/**
		 * Gets the list of side effects.
		 */
		public List<Pair<StatusEffectInstance, Float>> getSideEffects() {
			return sideEffectList;
		}

		/**
		 * Gets the cooldown time.
		 */
		public int getCooldownTime() {
			return cooldownTime;
		}

		/**
		 * Adds a side effect with the specified chance.
		 */
		public SideEffects sideEffect(StatusEffectInstance effect, float chance) {
			sideEffectList.add(Pair.of(effect, chance));
			return this;
		}
	}
}
