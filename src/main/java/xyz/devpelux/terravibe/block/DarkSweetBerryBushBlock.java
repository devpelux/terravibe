package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.item.TerravibeItems;

import java.util.Optional;

/** Bush of dark berries. */
public class DarkSweetBerryBushBlock extends BerryBushBlock {
    /** Identifier of the block. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "dark_sweet_berry_bush");

    /** Movement slowing amount. */
    public static final Vec3d MOVEMENT_SLOWING_AMOUNT = new Vec3d(0.800000011920929, 0.75, 0.800000011920929);

    /** Minimum movement to get the thorns damage. */
    public static final double MIN_MOVEMENT_FOR_THORNS_DAMAGE = 0.003000000026077032;

    /** Max age. */
    public static final int MAX_AGE = 3;

    /** Mature age. */
    public static final int MATURE_AGE = 2;

    /** Age of the bush. */
    public static final IntProperty AGE = IntProperty.of("age", 0, MAX_AGE);

    /** Voxel shapes of the block. */
    private static VoxelShape[] AGE_TO_SHAPE = null;

    /** Initializes a new {@link DarkSweetBerryBushBlock}. */
    public DarkSweetBerryBushBlock(Settings settings) {
        super(settings);
    }

    /** Gets the block settings. */
    public static @NotNull FabricBlockSettings getSettings() {
        return FabricBlockSettings.of(Material.PLANT)
                .nonOpaque()
                .noCollision()
                .ticksRandomly()
                .breakInstantly()
                .sounds(BlockSoundGroup.SWEET_BERRY_BUSH);
    }

    /** {@inheritDoc} */
    @Override
    public @NotNull IntProperty getAgeProperty() {
        return AGE;
    }

    /** {@inheritDoc} */
    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    /** {@inheritDoc} */
    @Override
    public int getMatureAge() {
        return MATURE_AGE;
    }

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    @Override
    public Vec3d getSlowingAmount(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE) {
            return MOVEMENT_SLOWING_AMOUNT;
        }
        return Vec3d.ZERO;
    }

    /** {@inheritDoc} */
    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(TerravibeItems.DARK_SWEET_BERRIES);
    }

    /** {@inheritDoc} */
    @Override
    public int getPickStackAmount(BlockView world, BlockPos pos, BlockState state) {
        int randomAmount = ((World)world).random.nextBetween(1, 2);
        int bonus = isFullyGrown(state) ? 1 : 0;

        return randomAmount + bonus;
    }

    /** {@inheritDoc} */
    @Override
    public Optional<SoundEvent> getPickSound() {
        return Optional.of(SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES);
    }

    /** {@inheritDoc} */
    @Override
    public int getMinLightToGrow() {
        return 4;
    }

    /** Gets the outline shape of the block. */
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getVoxelShape(getAge(state));
    }

    /** Gets the ray-cast shape of the block. */
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return getVoxelShape(getAge(state));
    }

    /** Gets the voxel shape of the block. */
    public static @NotNull VoxelShape getVoxelShape(int age) {
        if (AGE_TO_SHAPE == null) {
            AGE_TO_SHAPE = new VoxelShape[]{
                    Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 8.0, 13.0),
                    Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0),
                    Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0),
                    VoxelShapes.fullCube()
            };
        }
        return AGE_TO_SHAPE[age];
    }
}
