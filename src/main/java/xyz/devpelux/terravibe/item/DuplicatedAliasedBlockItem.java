package xyz.devpelux.terravibe.item;

import net.minecraft.block.Block;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;

import java.util.Map;

/**
 * Object for extra items associated to the same block.
 */
public class DuplicatedAliasedBlockItem extends AliasedBlockItem {
	/**
	 * Initializes a new instance.
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
