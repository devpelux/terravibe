package xyz.devpelux.terravibe;

import net.fabricmc.api.ClientModInitializer;
import xyz.devpelux.terravibe.block.TerravibeBlockConfigs;
import xyz.devpelux.terravibe.core.Terravibe;
import xyz.devpelux.terravibe.item.TerravibeItemConfigs;
import xyz.devpelux.terravibe.particle.TerravibeParticleTypes;
import xyz.devpelux.terravibe.screenhandler.TerravibeScreenHandlerTypes;

/**
 * Client-side mod initializer.
 */
public class InitializerClient implements ClientModInitializer {
	/**
	 * Runs the mod initializer on the client environment.
	 */
	@Override
	public void onInitializeClient() {
		//Loads block configurations
		TerravibeBlockConfigs.registerColorProviders();
		TerravibeBlockConfigs.registerRenderLayerMaps();

		//Loads item configurations
		TerravibeItemConfigs.registerColorProviders();

		//Loads particles
		TerravibeParticleTypes.registerParticleFactories();

		//Loads screens
		TerravibeScreenHandlerTypes.registerScreens();

		Terravibe.LOGGER.info("Loaded Terravibe client components.");
	}
}
