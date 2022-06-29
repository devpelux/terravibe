package xyz.devpelux.terravibe.core;

import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

/** Contains some utilities. */
public class Util {
    private Util() {}

    /** This logger is used to write text to the console and the log file. */
    public static final Logger LOGGER = LoggerFactory.getLogger(ModInfo.MOD_ID);

    /** Combines the specified voxel shapes. */
    public static @NotNull VoxelShape combineVoxelShapes(VoxelShape... shapes) {
        return Stream.of(shapes).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).orElse(VoxelShapes.empty());
    }
}
