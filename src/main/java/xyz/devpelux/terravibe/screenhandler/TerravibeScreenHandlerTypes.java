package xyz.devpelux.terravibe.screenhandler;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import xyz.devpelux.terravibe.core.Terravibe;

/**
 * List of all the screen handler types.
 */
public final class TerravibeScreenHandlerTypes {
	/**
	 * Screen handler type for the shredder block entity UI.
	 */
	public static final ScreenHandlerType<ShredderScreenHandler> SHREDDER;

	/**
	 * Registers the specified screen handler type with the specified id.
	 */
	private static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ScreenHandlerType.Factory<T> factory) {
		return Registry.register(Registries.SCREEN_HANDLER, Terravibe.identified(id), new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES));
	}

	static {
		SHREDDER = register("shredder", ShredderScreenHandler::create);
	}
}
