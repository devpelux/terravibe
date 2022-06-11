package xyz.devpelux.terravibe.core;

import net.minecraft.item.ItemConvertible;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.block.ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE;

/** Contains some utilities. */
public class Util {
    private Util() {}

    /** This logger is used to write text to the console and the log file. */
    public static final Logger LOGGER = LoggerFactory.getLogger(ModInfo.MOD_ID);

    /** Registers a compostable item */
    public static void registerCompostableItem(float levelIncreaseChance, @NotNull ItemConvertible item) {
        ITEM_TO_LEVEL_INCREASE_CHANCE.put(item.asItem(), levelIncreaseChance);
    }
}
