package xyz.devpelux.terravibe.core.compatibility.sodium;

import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.devpelux.terravibe.core.Util;

import java.util.HashSet;
import java.util.Set;

/**
 * All the registered blocks in this registry
 * will ignore sodium color blending optimizations.<br>
 * This is a singleton.
 */
public class IgnoreColorBlendingRegistry {
    /** Gets the instance. */
    private static final IgnoreColorBlendingRegistry INSTANCE = new IgnoreColorBlendingRegistry();

    private final Set<Identifier> blockIds = new HashSet<>();

    /** Initializes a new {@link IgnoreColorBlendingRegistry}. */
    private IgnoreColorBlendingRegistry() {}

    /** Registers a block into the registry. */
    public static void register(Block block) {
        Identifier id = Registry.BLOCK.getId(block);
        INSTANCE.blockIds.add(id);
        Util.LOGGER.info(id + " will ignore Sodium color blending optimizations.");
    }

    /** Checks if a block is into the registry. */
    public static boolean isRegistered(Block block) {
        return INSTANCE.blockIds.contains(Registry.BLOCK.getId(block));
    }
}
