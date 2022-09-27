package xyz.devpelux.terravibe.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.block.ShredderBlock;
import xyz.devpelux.terravibe.screenhandler.ShredderScreenHandler;

/** Block entity for the {@link ShredderBlock}. */
public class ShredderBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {
    /** Initializes a new {@link ShredderBlockEntity}. */
    public ShredderBlockEntity(BlockPos pos, BlockState state) {
        super(TerravibeBlockEntityTypes.SHREDDER, pos, state);
    }

    /** Gets the name to display in the UI of this block entity. */
    @Override
    public Text getDisplayName() {
        return Text.translatable("block.terravibe.shredder.title");
    }

    /** Gets the {@link ScreenHandler} for handling the UI of this block entity. */
    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new ShredderScreenHandler(syncId, inv);
    }
}
