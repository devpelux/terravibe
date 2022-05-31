package xyz.devpelux.terravibe;

import net.fabricmc.api.ModInitializer;
import xyz.devpelux.terravibe.core.Util;

/**
 * Main mod initializer.
 */
public class Initializer implements ModInitializer {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onInitialize() {
		Util.LOGGER.info("Loaded Terravibe components.");
	}
}
