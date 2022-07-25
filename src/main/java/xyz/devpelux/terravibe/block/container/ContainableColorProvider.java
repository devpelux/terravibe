package xyz.devpelux.terravibe.block.container;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

/** Color provider for a containable element in a container. */
@Environment(EnvType.CLIENT)
public interface ContainableColorProvider {
    /** Gets the color. */
    int getColor(ItemStack contained, BlockState state, BlockRenderView view, BlockPos pos, int i);
}
