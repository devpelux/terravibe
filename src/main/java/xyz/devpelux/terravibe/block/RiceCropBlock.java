package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemConvertible;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.item.TerravibeItems;

/** Crop of the rice. */
public class RiceCropBlock extends FloodedCropBlock {
    /** Identifier of the block. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "rice_crop");

    /** Voxel shapes of the crop. */
    private static final VoxelShape[] AGE_TO_SHAPE;

    /** Initializes a new {@link RiceCropBlock}. */
    public RiceCropBlock(Settings settings) {
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
    @Override
    public ItemConvertible getSeedsItem() {
        return TerravibeItems.RICE_GRAINS;
    }

    /** Gets the outline shape of the block. */
    @Override
    public VoxelShape getOutlineShape(@NotNull BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[getAge(state)];
    }

    static {
        AGE_TO_SHAPE = new VoxelShape[]{
                Block.createCuboidShape(0.0, -5.0, 0.0, 16.0, -3.0, 16.0),
                Block.createCuboidShape(0.0, -5.0, 0.0, 16.0, 0.0, 16.0),
                Block.createCuboidShape(0.0, -5.0, 0.0, 16.0, 2.0, 16.0),
                Block.createCuboidShape(0.0, -5.0, 0.0, 16.0, 5.0, 16.0),
                Block.createCuboidShape(0.0, -5.0, 0.0, 16.0, 9.0, 16.0),
                Block.createCuboidShape(0.0, -5.0, 0.0, 16.0, 14.0, 16.0),
                Block.createCuboidShape(0.0, -5.0, 0.0, 16.0, 15.0, 16.0),
                Block.createCuboidShape(0.0, -5.0, 0.0, 16.0, 16.0, 16.0)
        };
    }
}
