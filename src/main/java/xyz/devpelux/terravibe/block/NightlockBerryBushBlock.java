package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.item.TerravibeItems;

import java.util.Optional;

/** Bush of nightlock berries, without thorns. */
public class NightlockBerryBushBlock extends BerryBushBlock {
    /** Identifier of the block. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "nightlock_berry_bush");

    /** Settings of the block. */
    public static final Settings SETTINGS;

    /** Movement slow amount. */
    public static final Vec3d MOVEMENT_SLOWING_AMOUNT = new Vec3d(0.800000011920929, 0.75, 0.800000011920929);

    /** Max age. */
    public static final int MAX_AGE = 3;

    /** Mature age. */
    public static final int MATURE_AGE = 2;

    /** Age of the bush. */
    public static final IntProperty AGE;

    /** Initializes a new {@link NightlockBerryBushBlock}. */
    public NightlockBerryBushBlock(Settings settings) {
        super(settings);
    }

    /** Gets the age property. */
    @Override
    public @NotNull IntProperty getAgeProperty() {
        return AGE;
    }

    /** Gets the max age. */
    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    /** Gets the age when the bush has fruits. */
    @Override
    public int getMatureAge() {
        return MATURE_AGE;
    }

    /** Gets the fruit item to pick from the plant. */
    @Override
    public ItemConvertible getFruitItem() {
        return TerravibeItems.NIGHTLOCK_BERRIES;
    }

    /** Gets the amount of the fruit item to pick from the plant. */
    @Override
    public int getFruitItemAmount(@NotNull Random random, @NotNull BlockState state) {
        int randomAmount = random.nextBetween(1, 2);
        int bonus = isFullyGrown(state) ? 1 : 0;

        return randomAmount + bonus;
    }

    /** Gets the pick sound. */
    @Override
    public Optional<SoundEvent> getPickSound() {
        return Optional.of(SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES);
    }

    /** Gets the time to grow. */
    @Override
    public int getGrowingTime() {
        return 10;
    }

    /** Gets the required light to grow. */
    @Override
    public int getMinLightToGrow() {
        return 1;
    }

    /** Gets the thorns damage caused by the bush. (0 = no thorns) */
    @Override
    public float getThornsDamage(BlockState state, World world, BlockPos pos, Entity entity, @NotNull Vec3d entityMovement) {
        return 0F;
    }

    /** Gets the movement slowing amount when inside the bush. */
    @Override
    public Vec3d getSlowingAmount(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE) {
            return MOVEMENT_SLOWING_AMOUNT;
        }
        return Vec3d.ZERO;
    }

    static {
        SETTINGS = FabricBlockSettings.of(Material.PLANT)
                .nonOpaque()
                .noCollision()
                .ticksRandomly()
                .breakInstantly()
                .sounds(BlockSoundGroup.SWEET_BERRY_BUSH);
        AGE = IntProperty.of("age", 0, MAX_AGE);
    }
}
