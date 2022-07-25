package xyz.devpelux.terravibe.core;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

/** Color provider for a containable element in a container. */
@Environment(EnvType.CLIENT)
public interface ContainableColorProvider {
    /** Gets the color. */
    int getColor(ItemStack contained, BlockState state, @Nullable BlockRenderView view, @Nullable BlockPos pos, int i);
}
