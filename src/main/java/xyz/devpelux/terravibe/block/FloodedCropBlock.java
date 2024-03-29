package xyz.devpelux.terravibe.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import xyz.devpelux.terravibe.tags.TerravibeBlockTags;

/**
 * Crop that can be planted partially underwater.
 */
public abstract class FloodedCropBlock extends CropBlock {
	/**
	 * Initializes a new instance.
	 */
	public FloodedCropBlock(Settings settings) {
		super(settings);
	}

	/**
	 * Gets the time to grow.
	 */
	public abstract int getGrowingTime();

	/**
	 * Gets the required light to grow.
	 */
	public abstract int getMinLightToGrow();

	/**
	 * Gets a value indicating if the crop can be planted on top of the specified block.
	 */
	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return floor.isIn(TerravibeBlockTags.FLOODED_FARMLAND) && floor.getFluidState().isIn(FluidTags.WATER);
	}

	/**
	 * Executed every tick.
	 * Handles the natural growing.
	 */
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!isMature(state) && random.nextInt(getGrowingTime()) == 0
				&& world.getBaseLightLevel(pos, 0) >= getMinLightToGrow()) {
			//Increases the age by 1.
			world.setBlockState(pos, withAge(getAge(state) + 1), 2);
		}
	}
}
