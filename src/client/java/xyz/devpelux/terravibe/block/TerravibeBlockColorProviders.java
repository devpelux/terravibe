package xyz.devpelux.terravibe.block;

import net.minecraft.block.BlockState;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

/**
 * Color providers for the blocks.
 */
public class TerravibeBlockColorProviders {
	/**
	 * Gets the color of the milk cauldron block.
	 */
	public static int getMilkCauldronColor(BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos, int tintIndex) {
		//Switches the color basing on the content.
		if (tintIndex == 0) {
			return switch (state.get(MilkCauldronBlock.CONTENT)) {
				case Milk -> 0xffffff;
				case AcidMilk, AcidMilkWithSalt, AcidMilkWithSaltAndMold -> 0xfcffe7;
				default -> -1;
			};
		}
		return -1;
	}

	/**
	 * Gets the color of the nightshade fern block.
	 */
	public static int getNightshadeFernColor(BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos, int tintIndex) {
		//For layer 1 the color changes at age 7.
		return switch (tintIndex) {
			case 0 -> 0x87cb87;
			case 1 -> state.get(NightshadeFernBlock.AGE) == 7 ? 0x4d4067 : 0x569211;
			default -> -1;
		};
	}

	/**
	 * Gets the color of the tray block.
	 */
	public static int getTrayColor(BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos, int tintIndex) {
		//Sets the water color basing on the current biome.
		if (tintIndex != 1 || world == null || pos == null) return -1;
		return BiomeColors.getWaterColor(world, pos);
	}
}
