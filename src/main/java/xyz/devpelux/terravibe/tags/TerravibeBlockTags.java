package xyz.devpelux.terravibe.tags;

import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

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

    /** Registers a new tag with the specified identifier. */
    private static TagKey<Block> of(Identifier id) {
        return TagKey.of(Registry.BLOCK_KEY, id);
    }

    static {
        FLOODED_FARMLAND = of(new Identifier("c", "flooded_farmland"));
        MOLD_INFESTABLE = of(new Identifier("c", "mold_infestable"));
        MOLD_REPLACEABLE = of(new Identifier("c", "mold_replaceable"));
    }
}
