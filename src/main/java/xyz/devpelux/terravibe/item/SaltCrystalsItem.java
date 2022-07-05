package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import xyz.devpelux.terravibe.core.ModInfo;

/** Salt crystals. */
public class SaltCrystalsItem extends Item {
    /** Identifier of the item. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "salt_crystals");

    /** Settings of the item. */
    public static final Settings SETTINGS;

    /** Initializes a new {@link SaltCrystalsItem}. */
    public SaltCrystalsItem(Settings settings) {
        super(settings);
    }

    static {
        SETTINGS = new FabricItemSettings()
                .maxCount(64)
                .group(TerravibeItemGroups.TERRAVIBE);
    }
}
