package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import xyz.devpelux.terravibe.core.ModInfo;

/** Dark mold. */
public class DarkMoldItem extends Item {
    /** Identifier of the item. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "dark_mold");

    /** Settings of the item. */
    public static final Settings SETTINGS;

    /** Composting chance of the item. */
    public static final float COMPOSTING_CHANCE = 0.2f;

    /** Initializes a new {@link DarkMoldItem}. */
    public DarkMoldItem(Settings settings) {
        super(settings);
    }

    static {
        SETTINGS = new FabricItemSettings()
                .maxCount(64)
                .group(TerravibeItemGroups.TERRAVIBE);
    }
}
