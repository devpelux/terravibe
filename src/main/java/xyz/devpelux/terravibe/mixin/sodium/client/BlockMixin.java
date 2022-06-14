package xyz.devpelux.terravibe.mixin.sodium.client;

import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import xyz.devpelux.terravibe.core.compatibility.sodium.SodiumColorBlendable;

/** Injects the {@link SodiumColorBlendable} interface. */
@Mixin(Block.class)
public class BlockMixin implements SodiumColorBlendable {}
