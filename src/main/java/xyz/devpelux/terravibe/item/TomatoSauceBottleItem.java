package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;

/** Bottle that contains tomato sauce. */
public class TomatoSauceBottleItem extends Item {
    /** Identifier of the item. */
    public static final Identifier ID = new Identifier(ModInfo.MOD_ID, "tomato_sauce_bottle");

    /** Initializes a new {@link TomatoSauceBottleItem}. */
    public TomatoSauceBottleItem(Settings settings) {
        super(settings);
    }

    /** Gets the item settings. */
    public static @NotNull FabricItemSettings getSettings() {
        return new FabricItemSettings()
                .maxCount(16)
                .group(TerravibeItemGroups.TERRAVIBE);
    }

    /** Gets the fluid color for the tun. */
    public static int getFluidColorForTun(BlockState state, BlockRenderView world, BlockPos blockPos, int i) {
        return 0xf61815;
    }

    /** Gets the overlay color of the item. */
    public static int getOverlayColor(ItemStack itemStack, int i) {
        return i == 1 ? 0xf61815 : -1;
    }
}
