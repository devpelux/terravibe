package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.item.TerravibeItems;

/** Crop of the kale. */
public class KaleCropBlock extends CropBlock {
    /** Identifier of the block. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "kale_crop");

    /** Voxel shapes of the block. */
    private static VoxelShape[] AGE_TO_SHAPE = null;

    /** Initializes a new {@link KaleCropBlock}. */
    public KaleCropBlock(Settings settings) {
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
        return TerravibeItems.KALE_SEEDS;
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
                    Block.createCuboidShape(0.0D, -1.0D, 0.0D, 16.0D, 0.0D, 16.0D),
                    Block.createCuboidShape(0.0D, -1.0D, 0.0D, 16.0D, 0.0D, 16.0D),
                    Block.createCuboidShape(0.0D, -1.0D, 0.0D, 16.0D, 4.0D, 16.0D),
                    Block.createCuboidShape(0.0D, -1.0D, 0.0D, 16.0D, 4.0D, 16.0D),
                    Block.createCuboidShape(0.0D, -1.0D, 0.0D, 16.0D, 11.0D, 16.0D),
                    Block.createCuboidShape(0.0D, -1.0D, 0.0D, 16.0D, 11.0D, 16.0D),
                    Block.createCuboidShape(0.0D, -1.0D, 0.0D, 16.0D, 11.0D, 16.0D),
                    Block.createCuboidShape(0.0D, -1.0D, 0.0D, 16.0D, 15.0D, 16.0D)
            };
        }
        return AGE_TO_SHAPE[age];
    }
}
