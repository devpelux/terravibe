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
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.item.TerravibeItems;

import java.util.Optional;

/** Bush of dark berries. */
public class DarkSweetBerryBushBlock extends BerryBushBlock {
    /** Identifier of the block. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "dark_sweet_berry_bush");

    /** Settings of the block. */
    public static final Settings SETTINGS;

    /** Movement slowing amount. */
    public static final Vec3d MOVEMENT_SLOWING_AMOUNT = new Vec3d(0.800000011920929, 0.75, 0.800000011920929);

    /** Minimum movement to get the thorns damage. */
    public static final double MIN_MOVEMENT_FOR_THORNS_DAMAGE = 0.003000000026077032;

    /** Initializes a new {@link DarkSweetBerryBushBlock}. */
    public DarkSweetBerryBushBlock(Settings settings) {
        super(settings);
    }

    /** Gets the age property. */
    @Override
    public @NotNull IntProperty getAgeProperty() {
        return Properties.AGE_3;
    }

    /** Gets the max age. */
    @Override
    public int getMaxAge() {
        return Properties.AGE_3_MAX;
    }

    /** Gets the age when the bush has fruits. */
    @Override
    public int getMatureAge() {
        return 2;
    }

    /** Gets the fruit item to pick from the plant. */
    @Override
    public ItemConvertible getFruitItem() {
        return TerravibeItems.DARK_SWEET_BERRIES;
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
        return 5;
    }

    /** Gets the required light to grow. */
    @Override
    public int getMinLightToGrow() {
        return 4;
    }

    /** Gets the thorns damage caused by the bush. (0 = no thorns) */
    @Override
    public float getThornsDamage(BlockState state, World world, BlockPos pos, Entity entity, @NotNull Vec3d entityMovement) {
        if (getAge(state) > 0) {
            if (entity instanceof LivingEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE) {
                if (entityMovement.getX() >= MIN_MOVEMENT_FOR_THORNS_DAMAGE || entityMovement.getZ() >= MIN_MOVEMENT_FOR_THORNS_DAMAGE)
                {
                    return 1F;
                }
            }
        }
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
    }
}
