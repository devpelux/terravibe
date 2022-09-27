package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.item.TerravibeItems;

/** Crop of the kale. */
public class KaleCropBlock extends CropBlock {
    /** Settings of the block. */
    public static final Settings SETTINGS;

    /** Voxel shapes of the block. */
    private static final VoxelShape[] AGE_TO_SHAPE;

    /** Initializes a new {@link KaleCropBlock} with default settings. */
    public static KaleCropBlock of() {
        return new KaleCropBlock(SETTINGS);
    }

    /** Initializes a new {@link KaleCropBlock}. */
    public KaleCropBlock(Settings settings) {
        super(settings);
    }

    /** Gets the seeds item of the block. */
    @Override
    public ItemConvertible getSeedsItem() {
        return TerravibeItems.KALE_SEEDS;
    }

    /** Gets the outline shape of the block. */
    @Override
    public VoxelShape getOutlineShape(@NotNull BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[getAge(state)];
    }

    static {
        SETTINGS = FabricBlockSettings.of(Material.PLANT)
                .nonOpaque()
                .noCollision()
                .ticksRandomly()
                .breakInstantly()
                .sounds(BlockSoundGroup.CROP);
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
}
