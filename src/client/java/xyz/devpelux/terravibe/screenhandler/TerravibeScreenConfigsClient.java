package xyz.devpelux.terravibe.screenhandler;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

/**
 * Screen configurations.
 */
public final class TerravibeScreenConfigsClient {
	/**
	 * Register all the screens to the screen handler types.
	 */
	@Environment(EnvType.CLIENT)
	public static void registerScreens() {
		HandledScreens.register(TerravibeScreenHandlerTypes.SHREDDER, ShredderScreen::new);
	}
}
