package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import xyz.devpelux.terravibe.core.ModInfo;

/** List of all the item groups. */
public class ItemGroupList {
    /** Generic "terravibe" item group. */
    public static final ItemGroup TERRAVIBE = FabricItemGroupBuilder.create(new Identifier(ModInfo.MOD_ID, "terravibe"))
            .icon(() -> new ItemStack(Blocks.DEEPSLATE_REDSTONE_ORE)).build();

    /** Loads all the item groups. */
    public static void load() {}
}
