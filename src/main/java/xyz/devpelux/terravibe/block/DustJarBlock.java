package xyz.devpelux.terravibe.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.blockentity.ContainerBlockEntity;
import xyz.devpelux.terravibe.particle.TerravibeParticleTypes;

/**
 * A jar for dusts.
 */
public class DustJarBlock extends JarBlock {
	/**
	 * Dust consuming time.
	 */
	public static final int DUST_CONSUMING_TIME = 144;

	/**
	 * Max height from the current position to spread the wandering dust.
	 */
	public static final int DUST_SPREADING_HEIGHT = 4;

	/**
	 * Max radius from the current position to spread the wandering dust.
	 */
	public static final int DUST_SPREADING_RADIUS = 8;

	/**
	 * Number of dust particles to spread from the block every tick.
	 */
	public static final int DUST_TO_SPREAD_PER_TICK = 1;

	/**
	 * Initializes a new {@link DustJarBlock}.
	 */
	public DustJarBlock(Settings settings) {
		super(settings);
	}

	/**
	 * Gets a value indicating if to use the default content textures.
	 */
	@Override
	public boolean useDefaultContentTexture() {
		return false;
	}

	/**
	 * Executed at the block breaking when opened.
	 * Drops the contained.
	 */
	@Override
	public void onBreakOpened(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		super.onBreakOpened(world, pos, state, player);
		//Drops the content in server world, if the player is not in creative mode.
		if (!world.isClient() && !player.getAbilities().creativeMode) {
			ContainerBlockEntity container = getContainerEntity(world, pos);
			if (container == null) return;
			ItemStack contained = Registries.ITEM.get(new Identifier(getContent(container))).getDefaultStack();
			if (!contained.isEmpty()) {
				contained.setCount(getLevel(state));
				dropStack(world, pos, contained);
			}
		}
	}

	/**
	 * Gets a value indicating if the block reacts with the ticking system.
	 */
	@Override
	public boolean hasRandomTicks(BlockState state) {
		return !state.get(CLOSED);
	}

	/**
	 * Executed every tick randomly.
	 * Consumes the dust.
	 */
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		//Consumes the dust.
		int level = getLevel(state);
		//More dust is faster to consume.
		int consumingTime = DUST_CONSUMING_TIME * (MAX_LEVEL - level + 1);
		if (random.nextInt(consumingTime) == 0) {
			int newLevel = Math.max(level - 1, 0);
			if (newLevel > 0) {
				world.setBlockState(pos, state.with(getLevelProperty(), newLevel));
			} else {
				world.setBlockState(pos, TerravibeBlocks.JAR.getDefaultState());
			}
		}
	}

	/**
	 * Executed every tick randomly to handle effects.
	 */
	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (!state.get(CLOSED)) {
			//Gets the block entity.
			ContainerBlockEntity container = getContainerEntity(world, pos);
			if (container == null) return;

			//Particle to spawn.
			DefaultParticleType particle = getDustParticle(getContent(container));

			if (particle != null) {
				BlockPos.Mutable randomPos = new BlockPos.Mutable();
				int level = getLevel(state);

				//Generates the dust from the block.
				for (int i = 0; i < DUST_TO_SPREAD_PER_TICK; ++i) {
					int time = MAX_LEVEL - level;
					if (time == 0 || random.nextInt(time) == 0) {
						//Spawns a particle in a random position in the block.
						double x = pos.getX() + (0.4d + (random.nextDouble() * 0.4d));
						double y = pos.getY() + (0.4d + (random.nextDouble() * 0.4d));
						double z = pos.getZ() + (0.4d + (random.nextDouble() * 0.4d));
						world.addParticle(particle, x, y, z, 0d, 0.3d, 0d);
					}
				}

				//Generates the wandering air dust.
				for (int i = 0; i < level; ++i) {
					//Select a random spawn position between a radius of 10.
					int posX = pos.getX() + random.nextBetween(-DUST_SPREADING_RADIUS, DUST_SPREADING_RADIUS);
					int posY = pos.getY() + random.nextInt(DUST_SPREADING_HEIGHT);
					int posZ = pos.getZ() + random.nextBetween(-DUST_SPREADING_RADIUS, DUST_SPREADING_RADIUS);
					randomPos.set(posX, posY, posZ);

					//If the position has air, then spawns a particle in a random position in the block.
					if (!world.getBlockState(randomPos).isFullCube(world, randomPos)) {
						double x = randomPos.getX() + random.nextDouble();
						double y = randomPos.getY() + random.nextDouble();
						double z = randomPos.getZ() + random.nextDouble();
						world.addParticle(particle, x, y, z, 0d, 0d, 0d);
					}
				}
			}
		}
	}

	/**
	 * Gets the dust particle type of the corresponding dust.
	 */
	@Nullable
	protected DefaultParticleType getDustParticle(String dust) {
		return switch (dust) {
			case ContainerBlockData.CONTENT_BURNED_BIRCH_MOLD_DUST -> TerravibeParticleTypes.BIRCH_MOLD_SPORE;
			case ContainerBlockData.CONTENT_BURNED_DARK_MOLD_DUST -> TerravibeParticleTypes.DARK_MOLD_SPORE;
			case ContainerBlockData.CONTENT_BURNED_GLOWING_DARK_MOLD_DUST ->
					TerravibeParticleTypes.GLOWING_DARK_MOLD_SPORE;
			default -> null;
		};
	}
}
