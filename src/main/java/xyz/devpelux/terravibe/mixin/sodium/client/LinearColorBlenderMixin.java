package xyz.devpelux.terravibe.mixin.sodium.client;

import me.jellysquid.mods.sodium.client.model.quad.ModelQuadView;
import me.jellysquid.mods.sodium.client.model.quad.blender.ColorSampler;
import me.jellysquid.mods.sodium.client.model.quad.blender.LinearColorBlender;
import me.jellysquid.mods.sodium.client.util.color.ColorARGB;
import net.fabricmc.fabric.impl.tag.convention.TagRegistration;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin to {@link LinearColorBlender} of sodium mod by JellySquid.<p>
 * <a href="https://github.com/CaffeineMC/sodium-fabric">Sodium</a>
 */
@Mixin(LinearColorBlender.class)
public abstract class LinearColorBlenderMixin {
	/**
	 * Tag for non blendable blocks.
	 */
	@Unique
	private static final TagKey<Block> NON_BLENDABLE_BLOCKS = TagRegistration.BLOCK_TAG_REGISTRATION.registerCommon("non_blendable");

	/**
	 * Tag for non blendable fluids.
	 */
	@Unique
	private static final TagKey<Fluid> NON_BLENDABLE_FLUIDS = TagRegistration.FLUID_TAG_REGISTRATION.registerCommon("non_blendable");

	/**
	 * Retrieves the color value for the current block.
	 */
	@Shadow
	protected abstract <T> int getBlockColor(BlockRenderView world, T state, ColorSampler<T> sampler, int x, int y, int z, int colorIdx);

	/**
	 * Retrieves the color value for the current block.<p>
	 * This mixin avoids the color blending for certain blocks.
	 */
	@Inject(method = "getVertexColor", at = @At("HEAD"), cancellable = true)
	private <T> void injectGetVertexColor(BlockRenderView world, BlockPos origin, ModelQuadView quad, ColorSampler<T> sampler,
	                                      T state, int vertexIdx, CallbackInfoReturnable<Integer> cir) {
		if (isNonBlendable(state)) {
			//If the block or fluid is set to disable the color blending, it gets the color from the actual position.
			int color = getBlockColor(world, state, sampler, origin.getX(), origin.getY(), origin.getZ(), quad.getColorIndex());
			cir.setReturnValue(ColorARGB.toABGR(color));
		}
	}

	/**
	 * Checks if a block or fluid is non-blendable.
	 */
	@Unique
	private static <T> boolean isNonBlendable(T state) {
		if (state instanceof BlockState blockState) {
			return blockState.isIn(NON_BLENDABLE_BLOCKS);
		} else if (state instanceof FluidState fluidState) {
			return fluidState.isIn(NON_BLENDABLE_FLUIDS);
		}
		return false;
	}
}
