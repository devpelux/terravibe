package xyz.devpelux.terravibe;

import net.fabricmc.api.ModInitializer;
import xyz.devpelux.terravibe.advancement.TerravibeCriteria;
import xyz.devpelux.terravibe.block.TerravibeBlocks;
import xyz.devpelux.terravibe.block.TerravibeLootPools;
import xyz.devpelux.terravibe.blockentity.TerravibeBlockEntityTypes;
import xyz.devpelux.terravibe.core.Terravibe;
import xyz.devpelux.terravibe.entity.TerravibePathNodeTypeVariants;
import xyz.devpelux.terravibe.item.TerravibeItemGroups;
import xyz.devpelux.terravibe.item.TerravibeItems;
import xyz.devpelux.terravibe.particle.TerravibeParticleTypes;
import xyz.devpelux.terravibe.recipe.TerravibeRecipeSerializers;
import xyz.devpelux.terravibe.recipe.TerravibeRecipeTypes;
import xyz.devpelux.terravibe.screenhandler.TerravibeScreenHandlerTypes;
import xyz.devpelux.terravibe.tags.TerravibeBlockTags;
import xyz.devpelux.terravibe.tags.TerravibeFluidTags;
import xyz.devpelux.terravibe.tags.TerravibeItemTags;

//1346728994490959230

/**
 * Main mod initializer.
 */
public class Initializer implements ModInitializer {
	/**
	 * Runs the mod initializer.
	 */
	@Override
	public void onInitialize() {
		TerravibeBlocks.load();
		TerravibeItemGroups.load();
		TerravibeItems.load();
		TerravibeBlockEntityTypes.load();
		TerravibeScreenHandlerTypes.load();
		TerravibeRecipeTypes.load();
		TerravibeRecipeSerializers.load();
		TerravibeParticleTypes.load();
		TerravibePathNodeTypeVariants.load();
		TerravibeBlockTags.load();
		TerravibeFluidTags.load();
		TerravibeItemTags.load();
		TerravibeLootPools.load();
		TerravibeCriteria.load();

		Terravibe.LOGGER.info("Loaded Terravibe components.");
	}
}
