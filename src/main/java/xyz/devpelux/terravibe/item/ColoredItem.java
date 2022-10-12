package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
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
	public ColoredItem(ColoredItemSettings settings) {
		super(settings);
		this.provider = settings.colorProvider;
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


	/**
	 * Settings for items of type {@link ColoredItem}.
	 */
	public static class ColoredItemSettings extends FabricItemSettings {
		protected ItemColorProvider colorProvider;

		/**
		 * Sets the color provider.
		 */
		public ColoredItemSettings colorProvider(ItemColorProvider colorProvider) {
			this.colorProvider = colorProvider;
			return this;
		}
	}
}
