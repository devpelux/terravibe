package xyz.devpelux.terravibe.item;

import net.minecraft.block.Block;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;

import java.util.Map;

/**
 * {@link AliasedBlockItem} for extra items associated with the same block.
 */
public class DuplicatedAliasedBlockItem extends AliasedBlockItem {
	/**
	 * Initializes a new {@link DuplicatedAliasedBlockItem}.
	 */
	public DuplicatedAliasedBlockItem(Block block, Settings settings) {
		super(block, settings);
	}

	/**
	 * Associates the item with the corresponding block.
	 * By default, avoids the association because this is a duplicated item.
	 */
	@Override
	public void appendBlocks(Map<Block, Item> map, Item item) {
	}
}
