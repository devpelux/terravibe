package xyz.devpelux.terravibe.mixin.sodium.client;

import net.minecraft.fluid.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import xyz.devpelux.terravibe.core.compatibility.sodium.SodiumColorBlendable;

/** Injects the {@link SodiumColorBlendable} interface. */
@Mixin(Fluid.class)
public class FluidMixin implements SodiumColorBlendable {}
