package xyz.devpelux.terravibe;

import net.fabricmc.api.ModInitializer;
import xyz.devpelux.terravibe.block.TerravibeBlocks;
import xyz.devpelux.terravibe.block.container.TerravibeContainerBehaviors;
import xyz.devpelux.terravibe.blockentity.TerravibeBlockEntityTypes;
import xyz.devpelux.terravibe.core.Util;
import xyz.devpelux.terravibe.item.TerravibeItemGroups;
import xyz.devpelux.terravibe.item.TerravibeItems;
import xyz.devpelux.terravibe.particle.TerravibeParticleTypes;
import xyz.devpelux.terravibe.recipe.TerravibeRecipeTypes;
import xyz.devpelux.terravibe.screenhandler.TerravibeScreenHandlerTypes;
import xyz.devpelux.terravibe.tags.TerravibeBlockTags;
import xyz.devpelux.terravibe.tags.TerravibeItemTags;

//1346728994490959230

/** Main mod initializer. */
public class Initializer implements ModInitializer {
	/** Runs the mod initializer. */
	@Override
	public void onInitialize() {
		TerravibeBlocks.load();
		TerravibeItemGroups.load();
		TerravibeItems.load();
		TerravibeBlockEntityTypes.load();
		TerravibeBlockTags.load();
		TerravibeItemTags.load();
		TerravibeParticleTypes.load();
		TerravibeRecipeTypes.load();
		TerravibeScreenHandlerTypes.load();
		TerravibeContainerBehaviors.load();

		Util.LOGGER.info("Loaded Terravibe components.");
	}
}
