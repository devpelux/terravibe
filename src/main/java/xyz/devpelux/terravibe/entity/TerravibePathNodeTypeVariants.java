package xyz.devpelux.terravibe.entity;

import net.fabricmc.fabric.api.registry.LandPathNodeTypesRegistry;
import net.minecraft.entity.ai.pathing.PathNodeType;
import xyz.devpelux.terravibe.block.TerravibeBlocks;

/**
 * List of all the custom path node types.
 */
public final class TerravibePathNodeTypeVariants {
	private TerravibePathNodeTypeVariants() {
	}

	/**
	 * Loads all the custom path node types.
	 */
	public static void load() {
		LandPathNodeTypesRegistry.register(TerravibeBlocks.DARK_SWEET_BERRY_BUSH, PathNodeType.DAMAGE_OTHER, PathNodeType.DANGER_OTHER);
		LandPathNodeTypesRegistry.register(TerravibeBlocks.FLOWERING_OPUNTIA, PathNodeType.DAMAGE_CACTUS, PathNodeType.DANGER_CACTUS);
		LandPathNodeTypesRegistry.register(TerravibeBlocks.NIGHTLOCK_BERRY_BUSH, PathNodeType.DAMAGE_OTHER, PathNodeType.DANGER_OTHER);
		LandPathNodeTypesRegistry.register(TerravibeBlocks.OPUNTIA, PathNodeType.DAMAGE_CACTUS, PathNodeType.DANGER_CACTUS);
		LandPathNodeTypesRegistry.register(TerravibeBlocks.THISTLE_PLANT, PathNodeType.DAMAGE_CACTUS, PathNodeType.DANGER_CACTUS);
	}
}
