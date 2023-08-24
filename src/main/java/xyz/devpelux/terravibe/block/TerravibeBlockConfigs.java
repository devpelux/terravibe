package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.registry.FlattenableBlockRegistry;
import net.fabricmc.fabric.api.registry.LandPathNodeTypesRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.pathing.PathNodeType;
import xyz.devpelux.terravibe.core.CorkStrippableBlockRegistry;
import xyz.devpelux.terravibe.item.TerravibeItems;

/**
 * Block configurations.
 */
public class TerravibeBlockConfigs {
	/**
	 * Registers all the block behaviors.
	 */
	public static void registerBehaviors() {
		MilkCauldronBlock.registerBehaviors();
	}

	/**
	 * Registers all the blocks that can drop cork.
	 */
	public static void registerCorkStrippables() {
		CorkStrippableBlockRegistry.register(Blocks.OAK_LOG, TerravibeItems.CORK, 0.2f);
		CorkStrippableBlockRegistry.register(Blocks.OAK_WOOD, TerravibeItems.CORK, 0.2f);
		CorkStrippableBlockRegistry.register(Blocks.DARK_OAK_LOG, TerravibeItems.DARK_CORK, 0.1f);
		CorkStrippableBlockRegistry.register(Blocks.DARK_OAK_WOOD, TerravibeItems.DARK_CORK, 0.1f);
	}

	/**
	 * Registers all the block that can be flattened with shovel.
	 */
	public static void registerFlattenables() {
		FlattenableBlockRegistry.register(Blocks.MUD, TerravibeBlocks.EXCAVATED_MUD.getDefaultState());
		FlattenableBlockRegistry.register(TerravibeBlocks.EXCAVATED_MUD, Blocks.MUD.getDefaultState());
	}

	/**
	 * Registers all the land path node types.
	 */
	public static void registerLandPathNodeTypes() {
		LandPathNodeTypesRegistry.register(TerravibeBlocks.DARK_SWEET_BERRY_BUSH, PathNodeType.DAMAGE_OTHER, PathNodeType.DANGER_OTHER);
		LandPathNodeTypesRegistry.register(TerravibeBlocks.FLOWERING_OPUNTIA, PathNodeType.DAMAGE_OTHER, PathNodeType.DANGER_OTHER);
		LandPathNodeTypesRegistry.register(TerravibeBlocks.NIGHTLOCK_BERRY_BUSH, PathNodeType.DAMAGE_OTHER, PathNodeType.DANGER_OTHER);
		LandPathNodeTypesRegistry.register(TerravibeBlocks.OPUNTIA, PathNodeType.DAMAGE_OTHER, PathNodeType.DANGER_OTHER);
	}
}
