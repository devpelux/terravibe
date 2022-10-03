package xyz.devpelux.terravibe.core;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import java.util.function.BiConsumer;

/**
 * Represents a result slot for a screen handler.
 */
public class ResultSlot extends Slot {
	/**
	 * Player entity handling the slot.
	 */
	private final PlayerEntity player;

	/**
	 * Amount of items into the slot.
	 */
	private int amount;

	/**
	 * Listener called when an item is taken.
	 */
	private BiConsumer<PlayerEntity, ItemStack> onItemTakenListener;

	/**
	 * Initializes a new {@link ResultSlot}
	 */
	public ResultSlot(PlayerEntity player, Inventory inventory, int index, int x, int y) {
		super(inventory, index, x, y);
		this.player = player;
	}

	/**
	 * Sets the item taken listener for the slot.
	 */
	public ResultSlot setOnItemTakenListener(BiConsumer<PlayerEntity, ItemStack> onItemTakenListener) {
		this.onItemTakenListener = onItemTakenListener;
		return this;
	}

	/**
	 * Gets a value indicating if an {@link ItemStack} can be inserted into the slot. (Always false)
	 */
	@Override
	public boolean canInsert(ItemStack stack) {
		return false;
	}

	/**
	 * Takes a specified amount of items and insert them into the output.
	 */
	@Override
	public ItemStack takeStack(int amount) {
		if (hasStack()) this.amount += Math.min(amount, getStack().getCount());
		return super.takeStack(amount);
	}

	/**
	 * Executed when a specified amount of items is taken.
	 */
	@Override
	protected void onTake(int amount) {
		this.amount += amount;
	}

	/**
	 * Executed when an item is taken.
	 */
	@Override
	public void onTakeItem(PlayerEntity player, ItemStack stack) {
		super.onTakeItem(player, stack);
		this.onCrafted(stack);
		onItemTakenListener.accept(player, stack);
	}

	/**
	 * Executed when a crafting event is executed.
	 */
	@Override
	protected void onCrafted(ItemStack stack, int amount) {
		this.amount += amount;
		onCrafted(stack);
	}

	/**
	 * Executed when a crafting event is executed.
	 */
	@Override
	protected void onCrafted(ItemStack stack) {
		if (this.amount > 0) stack.onCraft(this.player.world, this.player, this.amount);
		this.amount = 0;
	}
}
