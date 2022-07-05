package xyz.devpelux.terravibe.mixin.terravibe;

import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Mixin to {@link LandPathNodeMaker}. */
@Mixin(LandPathNodeMaker.class)
public class LandPathNodeMakerMixin {
    /** Gets the path node type from the block. */
    @Inject(method = "getCommonNodeType", at = @At("HEAD"), cancellable = true)
    private static void getCommonNodeType(@NotNull BlockView world, BlockPos pos, @NotNull CallbackInfoReturnable<PathNodeType> cir) {
        world.getBlockState(pos).getBlock().getPathNodeType().ifPresent(cir::setReturnValue);
    }
}
