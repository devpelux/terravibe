package xyz.devpelux.terravibe.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

import static xyz.devpelux.terravibe.item.TerravibeItems.*;

public class TerravibeItemConfigsClient {
	@Environment(EnvType.CLIENT)
	public static void registerColorProviders() {
		ColorProviderRegistry.ITEM.register(TerravibeItemColorProviders::getGillyweedSeedsAncientColor, GILLYWEED_SEEDS_ANCIENT);
		ColorProviderRegistry.ITEM.register(TerravibeItemColorProviders::getLemonJuiceBottleColor, LEMON_JUICE);
		ColorProviderRegistry.ITEM.register(TerravibeItemColorProviders::getNightshadeFernSeedsAncientColor, NIGHTSHADE_FERN_SEEDS_ANCIENT);
		ColorProviderRegistry.ITEM.register(TerravibeItemColorProviders::getOilBottleColor, OIL);
		ColorProviderRegistry.ITEM.register(TerravibeItemColorProviders::getTomatoSauceBottleColor, TOMATO_SAUCE);
	}
}
