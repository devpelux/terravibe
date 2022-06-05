package xyz.devpelux.terravibe;

import net.fabricmc.api.ModInitializer;
import xyz.devpelux.terravibe.block.BlockList;
import xyz.devpelux.terravibe.blockentity.BlockEntityList;
import xyz.devpelux.terravibe.core.Util;
import xyz.devpelux.terravibe.item.ItemGroupList;
import xyz.devpelux.terravibe.item.ItemList;
import xyz.devpelux.terravibe.recipe.RecipeTypeList;
import xyz.devpelux.terravibe.screenhandler.ScreenHandlerTypeList;

/** Main mod initializer. */
public class Initializer implements ModInitializer {
	/** {@inheritDoc} */
	@Override
	public void onInitialize() {
		RecipeTypeList.load();

		ItemGroupList.load();

		BlockList.load();
		BlockEntityList.load();
		ItemList.load();

		ScreenHandlerTypeList.load();

		Util.LOGGER.info("Loaded Terravibe components.");
	}
}
