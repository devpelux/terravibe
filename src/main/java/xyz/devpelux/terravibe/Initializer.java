package xyz.devpelux.terravibe;

import net.fabricmc.api.ModInitializer;
import xyz.devpelux.terravibe.advancement.TerravibeCriteria;
import xyz.devpelux.terravibe.block.TerravibeBlockConfigs;
import xyz.devpelux.terravibe.block.TerravibeBlockLootPools;
import xyz.devpelux.terravibe.block.TerravibeBlocks;
import xyz.devpelux.terravibe.blockentity.TerravibeBlockEntityTypes;
import xyz.devpelux.terravibe.core.Terravibe;
import xyz.devpelux.terravibe.item.TerravibeItemConfigs;
import xyz.devpelux.terravibe.item.TerravibeItemInventory;
import xyz.devpelux.terravibe.item.TerravibeItems;
import xyz.devpelux.terravibe.particle.TerravibeParticleTypes;
import xyz.devpelux.terravibe.recipe.TerravibeRecipeSerializers;
import xyz.devpelux.terravibe.recipe.TerravibeRecipeTypes;
import xyz.devpelux.terravibe.screenhandler.TerravibeScreenHandlerTypes;
import xyz.devpelux.terravibe.tags.TerravibeBiomeTags;
import xyz.devpelux.terravibe.tags.TerravibeBlockTags;
import xyz.devpelux.terravibe.tags.TerravibeFluidTags;
import xyz.devpelux.terravibe.tags.TerravibeItemTags;

/**
 * Main mod initializer.
 */
public class Initializer implements ModInitializer {
	/**
	 * Runs the mod initializer.
	 */
	@SuppressWarnings("InstantiationOfUtilityClass")
	@Override
	public void onInitialize() {
		//Loads blocks and items
		new TerravibeBlocks();
		new TerravibeItems();

		//Loads block configurations
		TerravibeBlockConfigs.registerBehaviors();
		TerravibeBlockConfigs.registerCorkStrippables();
		TerravibeBlockConfigs.registerFlattenables();
		TerravibeBlockConfigs.registerLandPathNodeTypes();
		TerravibeBlockLootPools.registerLootTableModifiers();

		//Loads item configurations
		TerravibeItemConfigs.registerCompostingChances();
		TerravibeItemInventory.registerItemGroupModifiers();

		//Loads block entities
		new TerravibeBlockEntityTypes();

		//Loads advancements
		new TerravibeCriteria();

		//Loads particles
		new TerravibeParticleTypes();

		//Loads recipes
		new TerravibeRecipeSerializers();
		new TerravibeRecipeTypes();

		//Loads screens
		new TerravibeScreenHandlerTypes();

		//Loads tags
		new TerravibeBiomeTags();
		new TerravibeBlockTags();
		new TerravibeFluidTags();
		new TerravibeItemTags();

		Terravibe.LOGGER.info("Loaded Terravibe components.");
	}
}
