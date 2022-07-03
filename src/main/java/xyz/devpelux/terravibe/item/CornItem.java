package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;

/** Fruit composed by little yellow seeds (or grains). */
public class CornItem extends Item {
    /** Identifier of the item. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "corn");

    /** Composting chance of the item. */
    public static final float COMPOSTING_CHANCE = 0.6f;

    /** Initializes a new {@link CornItem}. */
    public CornItem(Settings settings) {
        super(settings);
    }

    /** Gets the item settings. */
    public static @NotNull FabricItemSettings getSettings() {
        return new FabricItemSettings()
                .maxCount(64)
                .group(TerravibeItemGroups.TERRAVIBE);
    }
}
