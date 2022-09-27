package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;

/** List of all the item groups. */
public final class TerravibeItemGroups {
    private TerravibeItemGroups() {}

    /** "Terravibe" item group. */
    public static final ItemGroup TERRAVIBE;

    /** Item used as icon for the "Terravibe" item group. */
    public static final Item TERRAVIBE_ICON;

    /** Loads all the item groups. */
    public static void load() {}

    /** Registers the specified item group with the specified id. */
    @Contract("_, _ -> new")
    private static @NotNull ItemGroup register(String id, ItemStack iconItem) {
        return FabricItemGroupBuilder.build(new Identifier(ModInfo.MOD_ID, id), () -> iconItem);
    }

    /** Registers the specified icon item with the specified id. */
    private static Item registerIcon(String id) {
        return Registry.register(Registry.ITEM, new Identifier(ModInfo.MOD_ID, id), new Item(new Item.Settings().maxCount(1).rarity(Rarity.EPIC)));
    }

    static {
        TERRAVIBE_ICON = registerIcon("terravibe");
        TERRAVIBE = register("terravibe", TERRAVIBE_ICON.getDefaultStack());
    }
}
