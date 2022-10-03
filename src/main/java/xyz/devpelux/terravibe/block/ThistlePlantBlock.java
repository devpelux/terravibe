package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import xyz.devpelux.terravibe.particle.TerravibeParticleTypes;

import java.util.Optional;

/**
 * Plant of the thistle.
 */
public class ThistlePlantBlock extends PlantBlock implements Fertilizable {
	/**
	 * Settings of the block.
	 */
	public static final Settings SETTINGS;

	/**
	 * Spreading radius in blocks from the current position.
	 */
	public static final int SPREADING_RADIUS = 2;

	/**
	 * Minimum plants to spread.
	 */
	public static final int MIN_PLANTS_TO_SPREAD = 0;

	/**
	 * Maximum plants to spread.
	 */
	public static final int MAX_PLANTS_TO_SPREAD = 2;

	/**
	 * Minimum movement to get the thorns damage.
	 */
	public static final double MIN_MOVEMENT_FOR_THORNS_DAMAGE = 0.003;

	/**
	 * Chance to spread a pollen particle.
	 */
	public static final float POLLEN_SPREAD_CHANCE = 0.5F;

	/**
	 * Voxel shape of the block.
	 */
	private static final VoxelShape VOXEL_SHAPE;

	/**
	 * Initializes a new {@link ThistlePlantBlock}.
	 */
	public ThistlePlantBlock(Settings settings) {
		super(settings);
	}

	/**
	 * Initializes a new {@link ThistlePlantBlock} with default settings.
	 */
	public static ThistlePlantBlock of() {
		return new ThistlePlantBlock(SETTINGS);
	}

	/**
	 * Gets the thorns damage caused by the plant. (0 = no thorns)
	 */
	protected float getThornsDamage(BlockState state, World world, BlockPos pos, Entity entity, Vec3d entityMovement) {
		if (entity instanceof LivingEntity) {
			if (entityMovement.getX() >= MIN_MOVEMENT_FOR_THORNS_DAMAGE || entityMovement.getZ() >= MIN_MOVEMENT_FOR_THORNS_DAMAGE) {
				return 0.7F;
			}
		}
		return 0F;
	}

	/**
	 * Gets a value indicating if the block can be replaced by another block.
	 */
	@Override
	public boolean canReplace(BlockState state, ItemPlacementContext context) {
		return false;
	}

	/**
	 * Gets a value indicating if the plant can be fertilized with bonemeal.
	 */
	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	/**
	 * Gets a value indicating if the plant can grow if bonemealed.
	 */
	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return true;
	}

	/**
	 * Executed when the plant is bonemealed.
	 */
	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		spread(world, pos, random);
	}

	/**
	 * Spreads the plant in the world.
	 */
	public void spread(ServerWorld world, BlockPos pos, Random random) {
		//Number of new plants to spread.
		int spreadCount = random.nextBetween(MIN_PLANTS_TO_SPREAD, MAX_PLANTS_TO_SPREAD);

		for (int i = 0; i < spreadCount; i++) {
			//Select a random position in the spreading radius.
			int x = pos.getX() + random.nextBetween(-SPREADING_RADIUS, SPREADING_RADIUS);
			int z = pos.getZ() + random.nextBetween(-SPREADING_RADIUS, SPREADING_RADIUS);
			BlockPos.Mutable spreadPos = new BlockPos.Mutable(x, pos.getY(), z).move(Direction.UP);

			BlockState plant = getDefaultState();

			//Tries to place the new plant in a block in a column of 3.
			for (int j = 0; j < 3; j++) {
				//Can replace only air blocks.
				if (world.getBlockState(spreadPos).isAir() && plant.canPlaceAt(world, spreadPos)) {
					world.setBlockState(spreadPos, plant);
					break;
				}

				spreadPos.move(Direction.DOWN);
			}
		}
	}

	/**
	 * Executed when an entity collides with the plant.
	 * Applies the thorns damage.
	 */
	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (!world.isClient()) {
			double movementX = Math.abs(entity.getX() - entity.lastRenderX);
			double movementY = Math.abs(entity.getY() - entity.lastRenderY);
			double movementZ = Math.abs(entity.getZ() - entity.lastRenderZ);
			Vec3d movement = new Vec3d(movementX, movementY, movementZ);
			float damage = getThornsDamage(state, world, pos, entity, movement);
			if (damage > 0F) {
				entity.damage(DamageSource.CACTUS, damage);
			}
		}
	}

	/**
	 * Executed every tick randomly to handle effects.
	 */
	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (random.nextFloat() < POLLEN_SPREAD_CHANCE) {
			//Spawns a pollen particle in a random position inside the flower part of the plant.
			double x = pos.getX() + 0.35d + (random.nextDouble() * 0.3d);
			double y = pos.getY() + 0.8d + (random.nextDouble() * 0.4d);
			double z = pos.getZ() + 0.35d + (random.nextDouble() * 0.3d);
			world.addParticle(TerravibeParticleTypes.THISTLE_POLLEN, x, y, z, 0d, 0.4d, 0d);
		}
	}

	/**
	 * Gets the path node type.
	 */
	@Override
	public Optional<PathNodeType> getPathNodeType() {
		return Optional.of(PathNodeType.DAMAGE_CACTUS);
	}

	/**
	 * Gets the outline shape of the block.
	 */
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		Vec3d vec3d = state.getModelOffset(world, pos);
		return VOXEL_SHAPE.offset(vec3d.x, vec3d.y, vec3d.z);
	}

	static {
		SETTINGS = FabricBlockSettings.of(Material.PLANT)
				.noCollision()
				.breakInstantly()
				.sounds(BlockSoundGroup.GRASS)
				.offsetType(OffsetType.XYZ);
		VOXEL_SHAPE = Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
	}
}
