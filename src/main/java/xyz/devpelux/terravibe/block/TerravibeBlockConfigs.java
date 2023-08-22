package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.registry.FlattenableBlockRegistry;
import net.fabricmc.fabric.api.registry.LandPathNodeTypesRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.pathing.PathNodeType;

public class TerravibeBlockConfigs {
	public static void registerBehaviors() {
		MilkCauldronBlock.registerBehaviors();
	}

	public static void registerFlattenables() {
		FlattenableBlockRegistry.register(Blocks.MUD, TerravibeBlocks.EXCAVATED_MUD.getDefaultState());
		FlattenableBlockRegistry.register(TerravibeBlocks.EXCAVATED_MUD, Blocks.MUD.getDefaultState());
	}

	public static void registerLandPathNodeTypes() {
		LandPathNodeTypesRegistry.register(TerravibeBlocks.DARK_SWEET_BERRY_BUSH, PathNodeType.DAMAGE_OTHER, PathNodeType.DANGER_OTHER);
		LandPathNodeTypesRegistry.register(TerravibeBlocks.FLOWERING_OPUNTIA, PathNodeType.DAMAGE_OTHER, PathNodeType.DANGER_OTHER);
		LandPathNodeTypesRegistry.register(TerravibeBlocks.NIGHTLOCK_BERRY_BUSH, PathNodeType.DAMAGE_OTHER, PathNodeType.DANGER_OTHER);
		LandPathNodeTypesRegistry.register(TerravibeBlocks.OPUNTIA, PathNodeType.DAMAGE_OTHER, PathNodeType.DANGER_OTHER);
	}
}
