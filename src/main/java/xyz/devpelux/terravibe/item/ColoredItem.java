package xyz.devpelux.terravibe.item;

import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Item with a color provider.
 */
public class ColoredItem extends Item implements ItemColorProvider {
	/**
	 * Color provider.
	 */
	private final ItemColorProvider provider;

	/**
	 * Initializes a new {@link ColoredItem}.
	 */
	public ColoredItem(Settings settings, ItemColorProvider provider) {
		super(settings);
		this.provider = provider;
	}

	/**
	 * Generates a color provider to color the tint index specified with the color specified.
	 */
	public static ItemColorProvider color(int tintIndex, int color) {
		return (s, i) -> i == tintIndex ? color : -1;
	}

	/**
	 * Gets the colors of the item.
	 */
	@Override
	public int getColor(ItemStack stack, int tintIndex) {
		return provider.getColor(stack, tintIndex);
	}
}
