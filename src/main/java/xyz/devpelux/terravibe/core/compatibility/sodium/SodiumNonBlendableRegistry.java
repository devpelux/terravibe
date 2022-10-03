package xyz.devpelux.terravibe.core.compatibility.sodium;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;

import java.util.HashSet;
import java.util.Set;

/**
 * Registry of blocks and fluids that should not be blended by sodium.
 */
@Environment(EnvType.CLIENT)
public final class SodiumNonBlendableRegistry {
	/**
	 * Singleton instance.
	 */
	private static final SodiumNonBlendableRegistry INSTANCE = new SodiumNonBlendableRegistry();

	/**
	 * List of blocks that should not be blended.
	 */
	private final Set<Block> NON_BLENDABLE_BLOCKS = new HashSet<>();

	/**
	 * List of fluids that should not be blended.
	 */
	private final Set<Fluid> NON_BLENDABLE_FLUIDS = new HashSet<>();

	@Environment(EnvType.CLIENT)
	private SodiumNonBlendableRegistry() {
	}

	/**
	 * Registers a block.
	 */
	@Environment(EnvType.CLIENT)
	public static void register(Block block) {
		INSTANCE.NON_BLENDABLE_BLOCKS.add(block);
	}

	/**
	 * Registers a fluid.
	 */
	@Environment(EnvType.CLIENT)
	@SuppressWarnings("unused")
	public static void register(Fluid fluid) {
		INSTANCE.NON_BLENDABLE_FLUIDS.add(fluid);
	}

	/**
	 * Checks if a block or fluid is registered as non-blendable.
	 */
	@Environment(EnvType.CLIENT)
	public static <T> boolean isNonBlendable(T state) {
		if (state instanceof BlockState blockState) {
			return INSTANCE.NON_BLENDABLE_BLOCKS.contains(blockState.getBlock());
		}
		else if (state instanceof FluidState fluidState) {
			return INSTANCE.NON_BLENDABLE_FLUIDS.contains(fluidState.getFluid());
		}
		return false;
	}
}
