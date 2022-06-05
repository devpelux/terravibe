package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;

/** Bottle that contains oil. */
public class OilBottleItem extends Item {
    /** Identifier of the item. */
    public static final Identifier ID = new Identifier(ModInfo.MOD_ID, "oil_bottle");

    /** Initializes a new {@link OilBottleItem}. */
    public OilBottleItem(Settings settings) {
        super(settings);
    }

    /** Gets the item settings. */
    public static @NotNull FabricItemSettings getSettings() {
        return new FabricItemSettings().maxCount(16).group(ItemGroupList.TERRAVIBE);
    }
}
