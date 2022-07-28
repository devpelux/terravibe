package xyz.devpelux.terravibe.mixin.terravibe;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.devpelux.terravibe.core.CorkStrippableBlockRegistry;

/** Mixin to {@link AxeItem}. */
@Mixin(AxeItem.class)
public class AxeItemMixin {
    /** Chance to drop cork. */
    private static final float CORK_DROPPING_CHANCE = 0.1f;

    /** Adds a random chance to drop cork from the supported wood types. */
    @Inject(method = "useOnBlock", at = @At("HEAD"))
    private void useOnBlock(@NotNull ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        Block block = world.getBlockState(pos).getBlock();
        Item cork = CorkStrippableBlockRegistry.getCork(block);
        PlayerEntity player = context.getPlayer();

        if (player != null && cork != null && world.getRandom().nextFloat() < CORK_DROPPING_CHANCE) {
            Block.dropStack(world, pos, new ItemStack(cork));
        }
    }
}
