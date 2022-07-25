package xyz.devpelux.terravibe.mixin.terravibe;

import net.minecraft.block.AbstractBlock;
import org.spongepowered.asm.mixin.Mixin;
import xyz.devpelux.terravibe.core.PathNodeTypeProvider;

/** Injects the {@link PathNodeTypeProvider} interface. */
@Mixin(AbstractBlock.class)
public class AbstractBlockMixin implements PathNodeTypeProvider {}
