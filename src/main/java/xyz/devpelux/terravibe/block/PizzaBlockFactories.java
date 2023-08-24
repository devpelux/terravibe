package xyz.devpelux.terravibe.block;

import net.minecraft.block.AbstractBlock;
import xyz.devpelux.terravibe.item.TerravibeItems;

/**
 * Factories of pizza blocks.
 */
public class PizzaBlockFactories {
	/**
	 * Creates a new block of pizza four cheese.
	 */
	public static PizzaBlock createPizzaFourCheese(AbstractBlock.Settings settings) {
		return new PizzaBlock(settings, () -> TerravibeItems.PIZZA_SLICE_FOUR_CHEESE);
	}

	/**
	 * Creates a new block of pizza margherita.
	 */
	public static PizzaBlock createPizzaMargherita(AbstractBlock.Settings settings) {
		return new PizzaBlock(settings, () -> TerravibeItems.PIZZA_SLICE_MARGHERITA);
	}
}
