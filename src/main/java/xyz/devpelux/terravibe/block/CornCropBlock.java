package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.item.TerravibeItems;

/** Crop of the corn. */
public class CornCropBlock extends TallCropBlock {
    /** Identifier of the block. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "corn_crop");

    /** Settings of the block. */
    public static final Settings SETTINGS;

    /** Voxel shapes of the lower block the crop. */
    private static final VoxelShape[] LOWER_AGE_TO_SHAPE;

    /** Voxel shapes of the upper block the crop. */
    private static final VoxelShape[] UPPER_AGE_TO_SHAPE;

    /** Initializes a new {@link CornCropBlock}. */
    public CornCropBlock(Settings settings) {
        super(settings);
    }

    /** Gets the seeds item of the block. */
    @Override
    public ItemConvertible getSeedsItem() {
        return TerravibeItems.CORN_GRAINS;
    }

    /** Gets a value indicating if the crop can be planted on top of the specified block. */
    @Override
    protected boolean canPlantOnTop(@NotNull BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND);
    }

    /** Gets the age when the block must have an upper and lower block. */
    @Override
    public int getAgeForUpper() {
        return 4;
    }

    /** Gets the outline shape of the lower block. */
    @Override
    public VoxelShape getLowerOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return LOWER_AGE_TO_SHAPE[getAge(state)];
    }

    /** Gets the outline shape of the upper block. */
    @Override
    public VoxelShape getUpperOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return UPPER_AGE_TO_SHAPE[getAge(state)];
    }

    static {
        SETTINGS = FabricBlockSettings.of(Material.PLANT)
                .nonOpaque()
                .noCollision()
                .ticksRandomly()
                .breakInstantly()
                .sounds(BlockSoundGroup.CROP)
                .offsetType(OffsetType.XZ);
        LOWER_AGE_TO_SHAPE = new VoxelShape[]{
                Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 6.0, 16.0),
                Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 8.0, 16.0),
                Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 10.0, 16.0),
                Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 15.0, 16.0),
                Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 15.0, 16.0),
                Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 15.0, 16.0),
                Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 15.0, 16.0),
                Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 15.0, 16.0)
        };
        UPPER_AGE_TO_SHAPE = new VoxelShape[]{
                VoxelShapes.empty(),
                VoxelShapes.empty(),
                VoxelShapes.empty(),
                VoxelShapes.empty(),
                Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 6.0, 16.0),
                Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 9.0, 16.0),
                Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 11.0, 16.0),
                Block.createCuboidShape(0.0, -1.0, 0.0, 16.0, 11.0, 16.0)
        };
    }
}
