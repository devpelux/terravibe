package xyz.devpelux.terravibe;

import net.fabricmc.api.ModInitializer;
import xyz.devpelux.terravibe.block.TerravibeBlocks;
import xyz.devpelux.terravibe.blockentity.TerravibeBlockEntityTypes;
import xyz.devpelux.terravibe.core.Util;
import xyz.devpelux.terravibe.item.TerravibeItemGroups;
import xyz.devpelux.terravibe.item.TerravibeItems;
import xyz.devpelux.terravibe.recipe.TerravibeRecipeTypes;
import xyz.devpelux.terravibe.screenhandler.TerravibeScreenHandlerTypes;

//1346728994490959230

/** Main mod initializer. */
public class Initializer implements ModInitializer {
	/** {@inheritDoc} */
	@Override
	public void onInitialize() {
		TerravibeRecipeTypes.load();

		TerravibeItemGroups.load();

		TerravibeBlocks.load();
		TerravibeBlockEntityTypes.load();
		TerravibeItems.load();

		TerravibeScreenHandlerTypes.load();

		Util.LOGGER.info("Loaded Terravibe components.");
	}
}
