package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;

/** List of all the item groups. */
public class ItemGroupList {
    /** Generic "terravibe" item group. */
    public static final ItemGroup TERRAVIBE;

    /** Loads all the item groups. */
    public static void load() {}

    /** Registers the specified item group with the specified id. */
    @Contract("_, _ -> new")
    private static @NotNull ItemGroup register(Identifier id, ItemStack iconItem) {
        return FabricItemGroupBuilder.create(id).icon(() -> iconItem).build();
    }

    static {
        TERRAVIBE = register(new Identifier(ModInfo.MOD_ID, "terravibe"), new ItemStack(Blocks.DEEPSLATE_REDSTONE_ORE));
    }
}
