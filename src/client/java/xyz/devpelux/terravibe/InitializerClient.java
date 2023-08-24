package xyz.devpelux.terravibe;

import net.fabricmc.api.ClientModInitializer;
import xyz.devpelux.terravibe.block.TerravibeBlockConfigsClient;
import xyz.devpelux.terravibe.core.Terravibe;
import xyz.devpelux.terravibe.item.TerravibeItemConfigsClient;
import xyz.devpelux.terravibe.particle.TerravibeParticleConfigsClient;
import xyz.devpelux.terravibe.screenhandler.TerravibeScreenConfigsClient;

/**
 * Client-side mod initializer.
 */
public class InitializerClient implements ClientModInitializer {
	/**
	 * Runs the mod initializer on the client environment.
	 */
	@Override
	public void onInitializeClient() {
		//Loads block configurations.
		TerravibeBlockConfigsClient.registerColorProviders();
		TerravibeBlockConfigsClient.registerRenderLayerMaps();

		//Loads item configurations.
		TerravibeItemConfigsClient.registerColorProviders();

		//Loads particle configurations.
		TerravibeParticleConfigsClient.registerParticleFactories();

		//Loads screen configurations.
		TerravibeScreenConfigsClient.registerScreens();

		Terravibe.LOGGER.info("Loaded Terravibe client components.");
	}
}
