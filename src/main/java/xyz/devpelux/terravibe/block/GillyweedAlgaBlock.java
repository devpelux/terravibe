package xyz.devpelux.terravibe.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import xyz.devpelux.terravibe.item.TerravibeItems;
import xyz.devpelux.terravibe.tags.TerravibeBiomeTags;

/**
 * An ancient extinct alga.
 */
public class GillyweedAlgaBlock extends AlgaCropBlock {
	/**
	 * Voxel shapes of the block.
	 */
	private static final VoxelShape[] AGE_TO_SHAPE;

	/**
	 * Initializes a new instance.
	 */
	public GillyweedAlgaBlock(Settings settings) {
		super(settings);
	}

	/**
	 * Gets the age property.
	 */
	@Override
	public IntProperty getAgeProperty() {
		return Properties.AGE_4;
	}

	/**
	 * Gets the max age.
	 */
	@Override
	public int getMaxAge() {
		return Properties.AGE_4_MAX;
	}

	/**
	 * Gets the seeds item of the block.
	 */
	@Override
	protected ItemConvertible getSeedsItem() {
		return TerravibeItems.GILLYWEED_SEEDS;
	}

	/**
	 * Gets the time to grow.
	 */
	@Override
	public int getGrowingTime() {
		return 40;
	}

	/**
	 * Gets the required light to grow.
	 */
	@Override
	public int getMinLightToGrow() {
		return 0;
	}

	/**
	 * Executed every tick.
	 * Handles the natural growing.
	 */
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if ((getAge(state) < 3) || world.getBiome(pos).isIn(TerravibeBiomeTags.WARM_OCEAN)) {
			super.randomTick(state, world, pos, random);
		}
	}

	/**
	 * Gets the growth amount to apply when the plant is bonemealed.
	 */
	@Override
	protected int getGrowthAmount(World world) {
		return MathHelper.nextInt(world.getRandom(), 1, 2);
	}

	/**
	 * Gets the outline shape of the block.
	 */
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return AGE_TO_SHAPE[getAge(state)];
	}

	static {
		AGE_TO_SHAPE = new VoxelShape[]{
				Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
				Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D),
				Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D),
				Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D),
				Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
		};
	}
}
