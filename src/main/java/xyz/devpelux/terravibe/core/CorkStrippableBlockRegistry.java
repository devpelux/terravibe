package xyz.devpelux.terravibe.core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.HashMap;

/** Registry for logs that will drop cork when stripped, with the type of cork dropped. */
public class CorkStrippableBlockRegistry {
    /** Singleton instance. */
    private static final CorkStrippableBlockRegistry INSTANCE = new CorkStrippableBlockRegistry();

    /** List of blocks with the type of cork dropped. */
    private final HashMap<Block, Item> CORK_STRIPPABLE = new HashMap<>();

    /** Initializes a new {@link CorkStrippableBlockRegistry}. */
    private CorkStrippableBlockRegistry() {}

    /** Registers a cork for a block. */
    public static void register(Block log, Item cork) {
        INSTANCE.CORK_STRIPPABLE.putIfAbsent(log, cork);
    }

    /** Gets the cork from the block. */
    public static Item getCork(Block block) {
        return INSTANCE.CORK_STRIPPABLE.get(block);
    }
}
