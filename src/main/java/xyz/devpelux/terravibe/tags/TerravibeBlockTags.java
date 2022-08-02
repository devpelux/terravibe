package xyz.devpelux.terravibe.tags;

import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;

import net.fabricmc.fabric.impl.tag.convention.TagRegistration;

/** List of all the block tags. */
public class TerravibeBlockTags {
    private TerravibeBlockTags() {}

    /**
     * {@code c:flooded_farmland}<p/>
     * Identifies a block in which can be planted flooded crops.
     */
    public static final TagKey<Block> FLOODED_FARMLAND;

    /**
     * {@code c:mold_infestable}<p/>
     * Identifies a block that can be infested by molds.
     */
    public static final TagKey<Block> MOLD_INFESTABLE;

    /**
     * {@code c:mold_replaceable}<p/>
     * Identifies a block that can be replaced by molds.
     */
    public static final TagKey<Block> MOLD_REPLACEABLE;

    /** Loads all the tags. */
    public static void load() {}

    static {
        FLOODED_FARMLAND = TagRegistration.BLOCK_TAG_REGISTRATION.registerCommon("flooded_farmland");
        MOLD_INFESTABLE = TagRegistration.BLOCK_TAG_REGISTRATION.registerCommon("mold_infestable");
        MOLD_REPLACEABLE = TagRegistration.BLOCK_TAG_REGISTRATION.registerCommon("mold_replaceable");
    }
}
