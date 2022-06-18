package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.item.TerravibeItems;

/** Crop of the eggplant. */
public class EggplantCropBlock extends CropBlock {
    /** Identifier of the block. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "eggplant_crop");

    /** Age when the plant is fully grown, and is ready to make fruits. */
    public static final int FULLY_GROWN_AGE = 5;

    /** Voxel shapes of the block. */
    private static VoxelShape[] AGE_TO_SHAPE = null;

    /** Initializes a new {@link EggplantCropBlock}. */
    public EggplantCropBlock(Settings settings) {
        super(settings);
    }

    /** Gets the block settings. */
    public static @NotNull FabricBlockSettings getSettings() {
        return FabricBlockSettings.of(Material.PLANT)
                .nonOpaque()
                .noCollision()
                .ticksRandomly()
                .breakInstantly()
                .sounds(BlockSoundGroup.CROP);
    }

    /** Gets the seeds item of the block. */
    public ItemConvertible getSeedsItem() {
        return TerravibeItems.EGGPLANT_SEEDS;
    }

    /**
     * Executed when the plant is used.<br>
     * Gets the drops, then reset the plant age to the fully grown age.
     */
    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.get(AGE) == 7) {
            //Will drops from 1 to 2 items if the age is 7.
            int nDrops = world.random.nextBetween(1, 2);
            dropStack(world, pos, new ItemStack(TerravibeItems.TOMATO, nDrops));

            //Plays the taken sound.
            world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS,
                    1.0F, 0.8F + world.random.nextFloat() * 0.4F);

            //Reset the plant age to the fully grown age.
            BlockState blockState = state.with(AGE, FULLY_GROWN_AGE);
            world.setBlockState(pos, blockState, 2);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, blockState));
            return ActionResult.success(world.isClient);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    /** Gets the outline shape of the block. */
    public VoxelShape getOutlineShape(@NotNull BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getVoxelShape(state.get(this.getAgeProperty()));
    }

    /** Gets the ray-cast shape of the block. */
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getRaycastShape(@NotNull BlockState state, BlockView world, BlockPos pos) {
        return getVoxelShape(state.get(this.getAgeProperty()));
    }

    /** Gets the voxel shape of the block. */
    public static @NotNull VoxelShape getVoxelShape(int age) {
        if (AGE_TO_SHAPE == null) {
            AGE_TO_SHAPE = new VoxelShape[]{
                    Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
                    Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D),
                    Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
                    Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D),
                    Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
                    Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
                    Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
                    Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D)
            };
        }
        return AGE_TO_SHAPE[age];
    }
}
