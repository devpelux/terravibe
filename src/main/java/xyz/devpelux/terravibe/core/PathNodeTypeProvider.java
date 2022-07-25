package xyz.devpelux.terravibe.core;

import net.minecraft.entity.ai.pathing.PathNodeType;

import java.util.Optional;

/** Defines a {@link PathNodeType} provider. */
public interface PathNodeTypeProvider {
    /** Gets the path node type. */
    default Optional<PathNodeType> getPathNodeType() {
        return Optional.empty();
    }
}
