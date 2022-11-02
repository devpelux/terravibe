package xyz.devpelux.terravibe;

import net.fabricmc.api.ClientModInitializer;
import xyz.devpelux.terravibe.block.TerravibeBlocks;
import xyz.devpelux.terravibe.core.Terravibe;
import xyz.devpelux.terravibe.item.TerravibeItems;
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
		TerravibeBlocks.loadClient();
		TerravibeItems.loadClient();
		TerravibeScreenHandlerTypes.loadClient();
		TerravibeParticleTypes.loadClient();

		Terravibe.LOGGER.info("Loaded Terravibe client components.");
	}
}
