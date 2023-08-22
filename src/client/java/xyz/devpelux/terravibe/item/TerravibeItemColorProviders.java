package xyz.devpelux.terravibe.item;

import net.minecraft.item.ItemStack;

public class TerravibeItemColorProviders {
	/**
	 * Gets the color of the ancient gillyweed seeds item.
	 */
	public static int getGillyweedSeedsAncientColor(ItemStack stack, int tintIndex) {
		int dirtyValue = AncientSeedItem.getDirtyValue(stack);
		return switch (tintIndex) {
			case 0 -> 0x619976;
			case 1 -> dirtyValue < 50 ? 0x619976 : 0xffc71c;
			default -> -1;
		};
	}

	/**
	 * Gets the color of the ancient gillyweed seeds item.
	 */
	public static int getNightshadeFernSeedsAncientColor(ItemStack stack, int tintIndex) {
		return tintIndex == 0 ? 0x814731 : -1;
	}

	/**
	 * Gets the color of the oak cork plug item.
	 */
	public static int getOakCorkPlugColor(ItemStack stack, int tintIndex) {
		return tintIndex == 1 ? 0xb8945f : -1;
	}

	/**
	 * Gets the color of the dark oak cork plug item.
	 */
	public static int getDarkOakCorkPlugColor(ItemStack stack, int tintIndex) {
		return tintIndex == 1 ? 0x4f3218 : -1;
	}

	/**
	 * Gets the color of the crimson cork plug item.
	 */
	public static int getCrimsonCorkPlugColor(ItemStack stack, int tintIndex) {
		return tintIndex == 1 ? 0x7e3a56 : -1;
	}

	/**
	 * Gets the color of the warped cork plug item.
	 */
	public static int getWarpedCorkPlugColor(ItemStack stack, int tintIndex) {
		return tintIndex == 1 ? 0x398382 : -1;
	}

	/**
	 * Gets the color of the warped cork plug item.
	 */
	public static int getTomatoSauceBottleColor(ItemStack stack, int tintIndex) {
		return tintIndex == 1 ? 0xf61815 : -1;
	}

	/**
	 * Gets the color of the warped cork plug item.
	 */
	public static int getOilBottleColor(ItemStack stack, int tintIndex) {
		return tintIndex == 1 ? 0x808000 : -1;
	}

	/**
	 * Gets the color of the warped cork plug item.
	 */
	public static int getLemonJuiceBottleColor(ItemStack stack, int tintIndex) {
		return tintIndex == 1 ? 0xf3ff93 : -1;
	}
}