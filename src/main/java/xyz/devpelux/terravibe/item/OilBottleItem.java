package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import xyz.devpelux.terravibe.core.ModInfo;

/** Bottle that contains oil. */
public class OilBottleItem extends Item {
    /** Identifier of the item. */
    public static final Identifier ID = new Identifier(ModInfo.MOD_ID, "oil_bottle");

    /** Settings of the item. */
    public static final Settings SETTINGS;

    /** Initializes a new {@link OilBottleItem}. */
    public OilBottleItem(Settings settings) {
        super(settings);
    }

    /** Gets the fluid color for the tun. */
    public static int getFluidColorForTun(BlockState state, BlockRenderView world, BlockPos blockPos, int i) {
        return 0x808000;
    }

    /** Gets the overlay color of the item. */
    public static int getOverlayColor(ItemStack itemStack, int i) {
        return i == 1 ? 0x808000 : -1;
    }

    static {
        SETTINGS = new FabricItemSettings()
                .maxCount(16)
                .group(TerravibeItemGroups.TERRAVIBE);
    }
}
