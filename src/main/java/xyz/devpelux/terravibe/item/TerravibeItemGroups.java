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
public class TerravibeItemGroups {
    /** Terravibe icon item: Item used as icon for the terravibe tab. */
    public static final Item TERRAVIBE_ICON;

    /** Generic "terravibe" item group. */
    public static final ItemGroup TERRAVIBE;

    /** Loads all the item groups. */
    public static void load() {}

    /** Registers the specified item group with the specified id. */
    @Contract("_, _ -> new")
    private static @NotNull ItemGroup register(Identifier id, ItemStack iconItem) {
        return FabricItemGroupBuilder.build(id, () -> iconItem);
    }

    /** Registers the specified icon item with the specified id. */
    private static Item registerIcon(Identifier id) {
        return Registry.register(Registry.ITEM, id, new Item(new Item.Settings().maxCount(1).rarity(Rarity.EPIC)));
    }

    static {
        TERRAVIBE_ICON = registerIcon(new Identifier(ModInfo.MOD_ID, "terravibe"));
        TERRAVIBE = register(new Identifier(ModInfo.MOD_ID, "terravibe"), new ItemStack(TERRAVIBE_ICON));
    }
}
