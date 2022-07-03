package xyz.devpelux.terravibe.block;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
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
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/** Generic berry bush. */
public abstract class BerryBushBlock extends PlantBlock implements Fertilizable {
    /** Growing time. */
    public static final int GROWING_TIME = 5;

    /** Initializes a new {@link BerryBushBlock}. */
    public BerryBushBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(getAgeProperty(), 0));
    }

    /** Registers the properties of the block. */
    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(getAgeProperty());
    }

    /** Gets the age of the bush. */
    protected int getAge(@NotNull BlockState state) {
        return state.get(getAgeProperty());
    }

    /** Gets a value indicating if the bush is fully grown. */
    protected boolean isFullyGrown(@NotNull BlockState state) {
        return getAge(state) >= getMaxAge();
    }

    /** Gets a value indicating if the bush is mature and has fruits. */
    protected boolean isMature(@NotNull BlockState state) {
        return getAge(state) >= getMatureAge();
    }

    /** Gets the age property. */
    public abstract @NotNull IntProperty getAgeProperty();

    /** Gets the max age. */
    public abstract int getMaxAge();

    /** Gets the age when the bush has fruits. */
    public abstract int getMatureAge();

    /** Gets the thorns damage caused by the bush. (0 = no thorns) */
    public abstract float getThornsDamage(BlockState state, World world, BlockPos pos, Entity entity, @NotNull Vec3d entityMovement);

    /** Gets the movement slowing amount when inside the bush. */
    public abstract Vec3d getSlowingAmount(BlockState state, World world, BlockPos pos, Entity entity);

    /** Gets the stack to pick from the bush. */
    @Override
    public abstract ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state);

    /** Gets the amount of the stack to pick from the bush. */
    public abstract int getPickStackAmount(BlockView world, BlockPos pos, BlockState state);

    /** Gets the pick sound. */
    public abstract Optional<SoundEvent> getPickSound();

    /** Gets the required light to grow. */
    public abstract int getMinLightToGrow();

    /**
     * Executed when the block is used.<br>
     * Drops the stack to pick and decrement the age or bonemeals the bush.
     */
    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!isFullyGrown(state) && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
            //If the plant is not fully grown, and the player uses the bone meal, does nothing.
            //So is possible to fully grow the plant with bonemeal.
            return ActionResult.PASS;
        }
        else if (isMature(state)) {
            //If the plant is mature to give fruits, then drops the fruits.
            int fruitAmount = getPickStackAmount(world, pos, state);
            dropStack(world, pos, new ItemStack(getPickStack(world, pos, state).getItem(), fruitAmount));

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
        }
        else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }

    /** Gets a property indicating if the block reacts with the ticking system. */
    @Override
    public boolean hasRandomTicks(BlockState state) {
        return !this.isFullyGrown(state);
    }

    /**
     * Executed every tick.
     * Handles the natural growing.
     */
    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!isFullyGrown(state) && random.nextInt(GROWING_TIME) == 0
                && world.getBaseLightLevel(pos.up(), 0) >= getMinLightToGrow()) {
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
    @SuppressWarnings("deprecation")
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

    /** Gets a value indicating if the plant can be fertilized with bonemeal. */
    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return !isFullyGrown(state);
    }

    /** Gets a value indicating if the plant can grow if bonemealed. */
    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    /** Executed when the plant is bonemealed. */
    @Override
    public void grow(@NotNull ServerWorld world, Random random, BlockPos pos, BlockState state) {
        //Increases the age by 1.
        int age = Math.min(getMaxAge(), getAge(state) + 1);
        BlockState nextGrowState = state.with(getAgeProperty(), age);
        world.setBlockState(pos, nextGrowState, 2);
    }
}
