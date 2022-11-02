package xyz.devpelux.terravibe.block;

import net.minecraft.block.BlockState;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.blockentity.ContainerBlockEntity;
import xyz.devpelux.terravibe.particle.TerravibeParticleTypes;
import xyz.devpelux.terravibe.tags.TerravibeBlockTags;

/**
 * A jar for mold dusts.
 */
public class MoldDustJarBlock extends DustJarBlock {
	/**
	 * Mold spreading time.
	 */
	public static final int MOLD_SPREADING_TIME = 48;

	/**
	 * Max height from the current position to spread the mold.
	 */
	public static final int MOLD_SPREADING_HEIGHT = 4;

	/**
	 * Max radius from the current position to spread the mold.
	 */
	public static final int MOLD_SPREADING_RADIUS = 8;

	/**
	 * Initializes a new {@link MoldDustJarBlock}.
	 */
	public MoldDustJarBlock(Settings settings) {
		super(settings);
	}

	/**
	 * Initializes a new {@link MoldDustJarBlock} with default settings.
	 */
	public static MoldDustJarBlock of() {
		return of(0);
	}

	/**
	 * Initializes a new {@link MoldDustJarBlock} with default settings and luminance per level.
	 */
	public static MoldDustJarBlock of(int luminancePerLevel) {
		return new MoldDustJarBlock(settings(luminancePerLevel, LEVEL));
	}

	/**
	 * Executed every tick randomly.
	 * Spreads the mold of the corresponding mold dust into the world.
	 */
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		super.randomTick(state, world, pos, random);
		//Gets the level and the block entity.
		int level = getLevel(state);
		ContainerBlockEntity container = getContainerEntity(world, pos);

		if (container != null) {
			//Mold to place.
			BlockState mold = getMold(getContent(container));

			//Tries to place the mold.
			if (mold != null) {
				int time = MOLD_SPREADING_TIME * (MAX_LEVEL - level + 1);
				if (random.nextInt(time) == 0) {
					//Gets a random position.
					int posX = pos.getX() + random.nextBetween(-MOLD_SPREADING_RADIUS, MOLD_SPREADING_RADIUS);
					int posZ = pos.getZ() + random.nextBetween(-MOLD_SPREADING_RADIUS, MOLD_SPREADING_RADIUS);
					int posYBottom = Math.max(pos.getY() - MOLD_SPREADING_HEIGHT, world.getBottomY());
					int posYTop = Math.min(pos.getY() + MOLD_SPREADING_HEIGHT, world.getTopY());
					int posY = random.nextBetween(posYBottom, posYTop);

					//2 Iterators, 1 will go up and 1 will go down.
					BlockPos.Mutable spreadPosUp = new BlockPos.Mutable(posX, posY, posZ);
					BlockPos.Mutable spreadPosDown = new BlockPos.Mutable(posX, posY, posZ);

					//Search a valid placement in a column of max 20 blocks between posYTop and posYBottom.
					while (spreadPosDown.getY() > posYBottom || spreadPosUp.getY() < posYTop) {
						//Up iterator.
						BlockState spreadPosUpState = world.getBlockState(spreadPosUp);
						if (spreadPosUpState.isAir() || spreadPosUpState.isIn(TerravibeBlockTags.MOLD_REPLACEABLE)) {
							if (mold.canPlaceAt(world, spreadPosUp)) {
								world.setBlockState(spreadPosUp, mold);
								return;
							}
						}

						//Down iterator.
						BlockState spreadPosDownState = world.getBlockState(spreadPosDown);
						if (spreadPosDownState.isAir() || spreadPosDownState.isIn(TerravibeBlockTags.MOLD_REPLACEABLE)) {
							if (mold.canPlaceAt(world, spreadPosDown)) {
								world.setBlockState(spreadPosDown, mold);
								return;
							}
						}

						//Moves the iterators up and down.
						if (spreadPosUp.getY() < posYTop) spreadPosUp.move(Direction.UP);
						if (spreadPosDown.getY() > posYBottom) spreadPosDown.move(Direction.DOWN);
					}
				}
			}
		}
	}

	/**
	 * Gets the dust particle type of the corresponding dust.
	 */
	@Override
	@Nullable
	protected DefaultParticleType getDustParticle(String dust) {
		return switch (dust) {
			case ContainerBlockData.CONTENT_BIRCH_MOLD_DUST -> TerravibeParticleTypes.BIRCH_MOLD_SPORE;
			case ContainerBlockData.CONTENT_DARK_MOLD_DUST -> TerravibeParticleTypes.DARK_MOLD_SPORE;
			case ContainerBlockData.CONTENT_GLOWING_DARK_MOLD_DUST -> TerravibeParticleTypes.GLOWING_DARK_MOLD_SPORE;
			default -> null;
		};
	}

	/**
	 * Gets the mold block state of the corresponding mold dust.
	 */
	@Nullable
	protected BlockState getMold(String dust) {
		return switch (dust) {
			case ContainerBlockData.CONTENT_BIRCH_MOLD_DUST -> TerravibeBlocks.BIRCH_MOLD.getDefaultState();
			case ContainerBlockData.CONTENT_DARK_MOLD_DUST -> TerravibeBlocks.DARK_MOLD.getDefaultState();
			case ContainerBlockData.CONTENT_GLOWING_DARK_MOLD_DUST ->
					TerravibeBlocks.GLOWING_DARK_MOLD.getDefaultState();
			default -> null;
		};
	}
}
