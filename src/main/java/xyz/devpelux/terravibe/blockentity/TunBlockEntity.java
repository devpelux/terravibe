package xyz.devpelux.terravibe.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import xyz.devpelux.terravibe.block.TunBlock;

/** Block entity for the {@link TunBlock}. */
public class TunBlockEntity extends ContainerBlockEntity {
    /** Initializes a new {@link TunBlockEntity}. */
    public TunBlockEntity(BlockPos pos, BlockState state) {
        super(TerravibeBlockEntityTypes.TUN, pos, state);
    }
}
