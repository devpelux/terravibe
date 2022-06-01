package xyz.devpelux.terravibe.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.block.GrinderBlock;

/** Item for {@link GrinderBlock} */
public class GrinderItem extends BlockItem {
    /** Identifier of the item. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "grinder");

    /** Initializes a new {@link GrinderItem}. */
    public GrinderItem(Block block, Settings settings) {
        super(block, settings);
    }
}
