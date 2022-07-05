package xyz.devpelux.terravibe.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.tags.TerravibeBlockTags;

/** Crop that can be planted partially underwater. */
public abstract class FloodedCropBlock extends CropBlock implements Fertilizable {
    /** Growing time. */
    public static final int GROWING_TIME = 6;

    /** Minimum light required for growing. */
    public static final int MIN_LIGHT_TO_GROW = 9;

    /** Initializes a new {@link FloodedCropBlock}. */
    public FloodedCropBlock(Settings settings) {
        super(settings);
    }

    /** Gets a value indicating if the crop can be planted on top of the specified block. */
    @Override
    protected boolean canPlantOnTop(@NotNull BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(TerravibeBlockTags.FLOODED_FARMLAND);
    }

    /**
     * Executed every tick.
     * Handles the natural growing.
     */
    @Override
    public void randomTick(BlockState state, @NotNull ServerWorld world, BlockPos pos, Random random) {
        if (!isMature(state) && random.nextInt(GROWING_TIME) == 0
                && world.getBaseLightLevel(pos.up(), 0) >= MIN_LIGHT_TO_GROW) {
            //Increases the age by 1.
            world.setBlockState(pos, withAge(getAge(state) + 1), 2);
        }
    }
}
