package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;

/** Salt. */
public class SaltItem extends Item {
    /** Identifier of the item. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "salt");

    /** Initializes a new {@link SaltItem}. */
    public SaltItem(Settings settings) {
        super(settings);
    }

    /** Gets the item settings. */
    public static @NotNull FabricItemSettings getSettings() {
        return new FabricItemSettings()
                .maxCount(64)
                .group(ItemGroupList.TERRAVIBE);
    }
}
