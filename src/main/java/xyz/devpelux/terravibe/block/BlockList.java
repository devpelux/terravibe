package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;

/** List of all the blocks. */
public class BlockList {
    /** Grinder block: Grinds an item to obtain another item. */
    public static final GrinderBlock GRINDER =
            Registry.register(Registry.BLOCK, GrinderBlock.ID, new GrinderBlock(FabricBlockSettings.copyOf(Blocks.FLOWER_POT)));

    /** Loads all the blocks. */
    public static void load() {}
}
