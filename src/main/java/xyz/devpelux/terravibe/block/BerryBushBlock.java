package xyz.devpelux.terravibe.block;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Optional;

/**
 * Berry bush.
 */
public abstract class BerryBushBlock extends PlantBlock implements Fertilizable {
	/**
	 * Voxel shapes of the block.
	 */
	private static final VoxelShape[] AGE_TO_SHAPE;

	/**
	 * Initializes a new {@link BerryBushBlock}.
	 */
	public BerryBushBlock(Settings settings) {
		super(settings);
		setDefaultState(getStateManager().getDefaultState().with(getAgeProperty(), 0));
	}

	/**
	 * Registers the properties of the block.
	 */
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(getAgeProperty());
	}

	/**
	 * Gets the age of the bush.
	 */
	protected int getAge(BlockState state) {
		return state.get(getAgeProperty());
	}

	/**
	 * Gets a value indicating if the bush is fully grown.
	 */
	protected boolean isFullyGrown(BlockState state) {
		return getAge(state) >= getMaxAge();
	}

	/**
	 * Gets a value indicating if the bush is mature and has fruits.
	 */
	protected boolean isMature(BlockState state) {
		return getAge(state) >= getMatureAge();
	}

	/**
	 * Gets the age property.
	 */
	public abstract IntProperty getAgeProperty();

	/**
	 * Gets the max age.
	 */
	public abstract int getMaxAge();

	/**
	 * Gets the age when the bush has fruits.
	 */
	public abstract int getMatureAge();

	/**
	 * Gets the time to grow.
	 */
	public abstract int getGrowingTime();

	/**
	 * Gets the required light to grow.
	 */
	public abstract int getMinLightToGrow();

	/**
	 * Gets the pick sound.
	 */
	public abstract Optional<SoundEvent> getPickSound();

	/**
	 * Gets the fruit item to pick from the plant.
	 */
	public abstract ItemConvertible getFruitItem();

	/**
	 * Gets the amount of the fruit item to pick from the plant.
	 */
	protected abstract int getFruitItemAmount(Random random, BlockState state);

	/**
	 * Gets the thorns damage caused by the bush. (0 = no thorns)
	 */
	protected abstract float getThornsDamage(BlockState state, World world, BlockPos pos, Entity entity, Vec3d entityMovement);

	/**
	 * Gets the movement slowing amount when inside the bush.
	 */
	protected abstract Vec3d getSlowingAmount(BlockState state, World world, BlockPos pos, Entity entity);

	/**
	 * Executed when the block is used.
	 * Drops the stack to pick and decrement the age or bonemeals the bush.
	 */
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!isFullyGrown(state) && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
			//If the plant is not fully grown, and the player uses the bone meal, does nothing.
			//So is possible to fully grow the plant with bonemeal.
			return ActionResult.PASS;
		} else if (isMature(state)) {
			//If the plant is mature to give fruits, then drops the fruits.
			int nDrops = getFruitItemAmount(world.random, state);
			dropStack(world, pos, new ItemStack(getFruitItem(), nDrops));

			//Plays the pick sound if not null.
			getPickSound().ifPresent(s -> {
				float pitch = 0.8F + world.random.nextFloat() * 0.4F;
				world.playSound(null, pos, s, SoundCategory.BLOCKS, 1.0F, pitch);
			});

			//Reset the age to the mature age minus 1, or zero if is zero.
			BlockState blockState = state.with(getAgeProperty(), Math.max(getMatureAge() - 1, 0));
			world.setBlockState(pos, blockState, 2);
			world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, blockState));

			//Client: SUCCESS / Server: CONSUME
			return ActionResult.success(world.isClient);
		} else {
			return super.onUse(state, world, pos, player, hand, hit);
		}
	}

	/**
	 * Gets a value indicating if the block reacts with the ticking system.
	 */
	@Override
	public boolean hasRandomTicks(BlockState state) {
		return !this.isFullyGrown(state);
	}

	/**
	 * Executed every tick.
	 * Handles the natural growing.
	 */
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!isFullyGrown(state) && random.nextInt(getGrowingTime()) == 0
				&& world.getBaseLightLevel(pos, 0) >= getMinLightToGrow()) {
			//Increases the age by 1.
			BlockState nextGrowState = state.with(getAgeProperty(), getAge(state) + 1);
			world.setBlockState(pos, nextGrowState, 2);
			world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(nextGrowState));
		}
	}

	/**
	 * Executed when an entity collides with the bush.
	 * Slow down the entity and applies the thorns damage.
	 */
	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		entity.slowMovement(state, getSlowingAmount(state, world, pos, entity));
		if (!world.isClient()) {
			double movementX = Math.abs(entity.getX() - entity.lastRenderX);
			double movementY = Math.abs(entity.getY() - entity.lastRenderY);
			double movementZ = Math.abs(entity.getZ() - entity.lastRenderZ);
			Vec3d movement = new Vec3d(movementX, movementY, movementZ);
			float damage = getThornsDamage(state, world, pos, entity, movement);
			if (damage > 0F) {
				entity.damage(DamageSource.SWEET_BERRY_BUSH, damage);
			}
		}
	}

	/**
	 * Gets a value indicating if the plant can be fertilized with bonemeal.
	 */
	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
		return !isFullyGrown(state);
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
		//Increases the age by 1.
		int age = Math.min(getMaxAge(), getAge(state) + 1);
		BlockState nextGrowState = state.with(getAgeProperty(), age);
		world.setBlockState(pos, nextGrowState, 2);
	}

	/**
	 * Gets the path node type.
	 */
	@Override
	public Optional<PathNodeType> getPathNodeType() {
		return Optional.of(PathNodeType.DAMAGE_OTHER);
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
				Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 8.0, 13.0),
				Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0),
				Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0),
				VoxelShapes.fullCube()
		};
	}
}
