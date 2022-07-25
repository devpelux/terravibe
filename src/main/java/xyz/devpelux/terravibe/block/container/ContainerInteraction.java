package xyz.devpelux.terravibe.block.container;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Defines an interaction of the player with a container. */
public interface ContainerInteraction {
    /** Executed when the player interacts with the container with an item. */
    ContainerInteractionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack hand, ItemStack contained, int level);
}
