package xyz.devpelux.terravibe.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

/**
 * Drinkable juice.
 */
public class JuiceItem extends Item {
	/**
	 * Initializes a new instance.
	 */
	public JuiceItem(Settings settings) {
		super(settings);
	}

	/**
	 * Executed when an entity finishes the juice.
	 * Drinks the juice and drops the bottle.
	 */
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity entity) {
		ItemStack usedStack = super.finishUsing(stack, world, entity);
		if (entity instanceof PlayerEntity player && !player.getAbilities().creativeMode) {
			//If the player is not in creative, a bottle is returned, replacing the consumed item.
			if (usedStack.isEmpty()) {
				//If the consumed stack becomes empty, is replaced directly with the bottle.
				usedStack = new ItemStack(Items.GLASS_BOTTLE);
			} else {
				//Else the bottle is dropped.
				player.getInventory().offerOrDrop(new ItemStack(Items.GLASS_BOTTLE));
			}
		}

		//Returns the consumed stack.
		return usedStack;
	}

	/**
	 * Gets the drinking time.
	 */
	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 30;
	}

	/**
	 * Gets the use action.
	 */
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.DRINK;
	}

	/**
	 * Gets the eat sound.
	 */
	@Override
	public SoundEvent getEatSound() {
		return SoundEvents.ENTITY_GENERIC_DRINK;
	}
}
