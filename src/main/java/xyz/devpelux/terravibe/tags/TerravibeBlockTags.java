package xyz.devpelux.terravibe.tags;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

/**
 * List of all the block tags.
 */
public final class TerravibeBlockTags {
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

	static {
		FLOODED_FARMLAND = TagKey.of(RegistryKeys.BLOCK, new Identifier("c", "flooded_farmland"));
		MOLD_INFESTABLE = TagKey.of(RegistryKeys.BLOCK, new Identifier("c", "mold_infestable"));
		MOLD_REPLACEABLE = TagKey.of(RegistryKeys.BLOCK, new Identifier("c", "mold_replaceable"));
	}
}
