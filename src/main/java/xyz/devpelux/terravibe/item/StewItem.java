package xyz.devpelux.terravibe.item;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

/**
 * Food contained in a bowl.
 */
public class StewItem extends Item {
	/**
	 * Initializes a new instance.
	 */
	public StewItem(Settings settings) {
		super(settings);
	}

	/**
	 * Executed when an entity finishes using the item.
	 * Eats the stew and drops the bowl.
	 */
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity entity) {
		ItemStack usedStack = super.finishUsing(stack, world, entity);
		if (entity instanceof PlayerEntity player) {
			//For the player.
			if (!player.getAbilities().creativeMode) {
				//If the player is not in creative, a bowl is returned, replacing the consumed item.
				if (usedStack.isEmpty()) {
					//If the consumed stack becomes empty, is replaced directly with the bowl.
					usedStack = new ItemStack(Items.BOWL);
				} else {
					//Else the bowl is dropped.
					player.getInventory().offerOrDrop(new ItemStack(Items.BOWL));
				}
			}
		} else {
			//For other entities.
			if (usedStack.isEmpty()) {
				//If the consumed stack becomes empty, is replaced directly with a bowl.
				usedStack = new ItemStack(Items.BOWL);
			} else {
				//Else drops the bowl.
				Block.dropStack(world, entity.getBlockPos(), new ItemStack(Items.BOWL));
			}
		}

		//Returns the consumed stack.
		return usedStack;
	}
}
