package xyz.devpelux.terravibe.core;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

import static net.minecraft.block.ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE;
import static net.minecraft.item.ShovelItem.PATH_STATES;

/** Contains some utilities. */
public class Util {
    private Util() {}

    /** This logger is used to write text to the console and the log file. */
    public static final Logger LOGGER = LoggerFactory.getLogger(ModInfo.MOD_ID);

    /** Registers a compostable item. */
    public static void registerCompostable(float levelIncreaseChance, @NotNull ItemConvertible item) {
        ITEM_TO_LEVEL_INCREASE_CHANCE.put(item.asItem(), levelIncreaseChance);
    }

    /** Registers an excavable block. */
    public static void registerExcavable(Block block, BlockState blockState) {
        PATH_STATES.put(block, blockState);
    }

    /** Combines the specified voxel shapes. */
    public static @NotNull VoxelShape combineVoxelShapes(VoxelShape... shapes) {
        return Stream.of(shapes).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).orElse(VoxelShapes.empty());
    }
}
