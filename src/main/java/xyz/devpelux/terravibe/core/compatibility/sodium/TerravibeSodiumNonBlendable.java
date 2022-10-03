package xyz.devpelux.terravibe.core.compatibility.sodium;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import xyz.devpelux.terravibe.block.TerravibeBlocks;

/**
 * List of all the blocks and fluids that should not be blended by sodium.
 */
@Environment(EnvType.CLIENT)
public class TerravibeSodiumNonBlendable {
	@Environment(EnvType.CLIENT)
	private TerravibeSodiumNonBlendable() {
	}

	/**
	 * Loads all the non blendable blocks and fluids.
	 */
	@Environment(EnvType.CLIENT)
	public static void load() {
		SodiumNonBlendableRegistry.register(TerravibeBlocks.JAR);
		SodiumNonBlendableRegistry.register(TerravibeBlocks.TUN);
	}
}
