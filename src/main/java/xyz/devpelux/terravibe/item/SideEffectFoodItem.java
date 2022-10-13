package xyz.devpelux.terravibe.item;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private final List<Pair<StatusEffectInstance, Float>> sideEffects;

	/**
	 * Cooldown to get the side effects.
	 */
	private final int cooldown;

	private int time = 0;

	/**
	 * Initializes a new {@link SideEffectFoodItem}.
	 */
	public SideEffectFoodItem(SideEffectFoodItemSettings settings) {
		super(settings);
		ServerTickEvents.END_SERVER_TICK.register((s) -> tick(s.getTicks()));
		sideEffects = settings.sideEffects;
		cooldown = settings.cooldown;
	}

	/**
	 * Executed at every tick of the server on server side.
	 */
	private void tick(int serverTime) {
		this.time = serverTime;
		if (time % 8400L == 0L) {
			//Every 7 minutes, cleanup the cooldown list.
			COOLDOWNS.entrySet().removeIf(element -> element.getValue() <= time);
		}
	}

	/**
	 * Sets the cooldown of the item for the specified player.
	 */
	public final void setCooldown(PlayerEntity player, int cooldown) {
		COOLDOWNS.put(player.getId(), time + cooldown);
	}

	/**
	 * Gets the cooldown left of the item for the specified player.
	 */
	public final int getCooldownLeft(PlayerEntity player) {
		return Math.max(COOLDOWNS.getOrDefault(player.getId(), 0) - time, 0);
	}

	/**
	 * Checks if the item has a cooldown for the specified player.
	 */
	public final boolean isCoolingDown(PlayerEntity player) {
		return getCooldownLeft(player) > 0;
	}

	/**
	 * Executed when an entity finishes using the item.
	 * Applies the side effects if the cooldown is not respected and sets a new cooldown.
	 */
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity entity) {
		if (isFood() && entity instanceof PlayerEntity player && !world.isClient()) {
			if (isCoolingDown(player)) {
				//Applies the side effects with their chance if the item is in cooldown for the player.
				for (Pair<StatusEffectInstance, Float> sideEffect : sideEffects) {
					if (world.getRandom().nextFloat() <= sideEffect.getSecond()) {
						player.addStatusEffect(sideEffect.getFirst());
					}
				}
			}

			//Sets a cooldown for the player that used the item.
			setCooldown(player, cooldown);
		}
		return super.finishUsing(stack, world, entity);
	}


	/**
	 * Settings for items of type {@link SideEffectFoodItem}.
	 */
	public static class SideEffectFoodItemSettings extends FabricItemSettings {
		protected final List<Pair<StatusEffectInstance, Float>> sideEffects = new ArrayList<>();
		protected int cooldown = 0;

		/**
		 * Adds a side effect.
		 * After adding the side effects, must be specified a cooldown.
		 * If the item is used before the cooldown ends, the effects are applied.
		 */
		public SideEffectFoodItemSettings sideEffect(StatusEffectInstance effect, float chance) {
			this.sideEffects.add(Pair.of(effect, chance));
			return this;
		}

		/**
		 * Sets the cooldown for the side effects.
		 * Must be a positive value.
		 */
		public SideEffectFoodItemSettings cooldown(int cooldown) {
			this.cooldown = Math.max(cooldown, 0);
			return this;
		}
	}
}
