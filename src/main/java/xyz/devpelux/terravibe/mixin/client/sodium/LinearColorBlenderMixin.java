package xyz.devpelux.terravibe.mixin.client.sodium;

import me.jellysquid.mods.sodium.client.model.quad.ModelQuadView;
import me.jellysquid.mods.sodium.client.model.quad.blender.ColorSampler;
import me.jellysquid.mods.sodium.client.model.quad.blender.LinearColorBlender;
import me.jellysquid.mods.sodium.client.util.color.ColorARGB;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.devpelux.terravibe.core.compatibility.sodium.IgnoreColorBlendingRegistry;

/**
 * Mixin to {@link LinearColorBlender} of sodium mod by JellySquid.<p>
 * <a href="https://github.com/CaffeineMC/sodium-fabric">Sodium</a>
 */
@Mixin(LinearColorBlender.class)
public abstract class LinearColorBlenderMixin {
    /** Retrieves the color value for the current block. */
    @Shadow protected abstract <T> int getBlockColor(BlockRenderView world, T state, ColorSampler<T> sampler, int x, int y, int z, int colorIdx);

    /**
     * Retrieves the color value for the current block.<p>
     * This mixin avoids the color blending for certain blocks.
     */
    @Inject(method = "getVertexColor", at = @At("HEAD"), cancellable = true)
    private <T> void injectGetVertexColor(BlockRenderView world, BlockPos origin, ModelQuadView quad, ColorSampler<T> sampler,
                                      T state, int vertexIdx, CallbackInfoReturnable<Integer> cir) {
        if (state instanceof BlockState blockState) {
            if (IgnoreColorBlendingRegistry.isRegistered(blockState.getBlock())) {
                //If the block is registered to ignore the color blending, it gets the color from the actual position.
                int color = getBlockColor(world, state, sampler, origin.getX(), origin.getY(), origin.getZ(), quad.getColorIndex());
                cir.setReturnValue(ColorARGB.toABGR(color));
            }
        }
    }
}
