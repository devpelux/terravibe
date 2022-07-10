package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import xyz.devpelux.terravibe.core.ModInfo;

/** Herb of the basil. */
public class BasilHerbBlock extends HerbBlock {
    /** Identifier of the block. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "basil_herb");

    /** Settings of the block. */
    public static final Settings SETTINGS;

    /** Initializes a new {@link BasilHerbBlock}. */
    public BasilHerbBlock(Settings settings) {
        super(settings);
    }

    /** Gets the time to grow. */
    @Override
    public int getGrowingTime() {
        return 20;
    }

    /** Gets the required light to grow. */
    @Override
    public int getMinLightToGrow() {
        return 9;
    }

    static {
        SETTINGS = FabricBlockSettings.copyOf(Blocks.GRASS);
    }
}
