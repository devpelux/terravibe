package xyz.devpelux.terravibe.block.container;

import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import xyz.devpelux.terravibe.block.TunBlock;
import xyz.devpelux.terravibe.item.TerravibeItems;

/** List of all the interactions with the container blocks. */
public class TerravibeContainerInteractions {
    private TerravibeContainerInteractions() {}

    /** Insert bottle. */
    public static final ContainerInteraction BOTTLE_INSERT;

    /** Extract bottle. */
    public static final ContainerInteraction BOTTLE_EXTRACT;

    /** Insert bucket. */
    public static final ContainerInteraction BUCKET_INSERT;

    /** Extract bucket. */
    public static final ContainerInteraction BUCKET_EXTRACT;

    /** Insert water bucket. */
    public static final ContainerInteraction WATER_BUCKET_INSERT;

    /** Extract water bucket. */
    public static final ContainerInteraction WATER_BUCKET_EXTRACT;

    /** Loads all the interactions. */
    public static void load() {
        //Color providers
        TunBlock.registerColorProvider(Items.MILK_BUCKET, (c, s, v, p, i) -> 0xffffff);
        TunBlock.registerColorProvider(Items.HONEY_BOTTLE, (c, s, v, p, i) -> 0x976018);
        TunBlock.registerColorProvider(Items.POTION, (c, s, v, p, i) ->
                PotionUtil.getPotion(c) == Potions.WATER ? BiomeColors.getWaterColor(v, p) : PotionUtil.getColor(c));
        TunBlock.registerColorProvider(TerravibeItems.OIL_BOTTLE, (c, s, v, p, i) -> 0x808000);
        TunBlock.registerColorProvider(TerravibeItems.TOMATO_SAUCE_BOTTLE, (c, s, v, p, i) -> 0xf61815);

        //Interactions
        TunBlock.registerInteraction(Items.MILK_BUCKET, Items.AIR, TerravibeContainerInteractions.BUCKET_INSERT);
        TunBlock.registerInteraction(Items.MILK_BUCKET, Items.MILK_BUCKET, TerravibeContainerInteractions.BUCKET_INSERT);
        TunBlock.registerInteraction(Items.BUCKET, Items.MILK_BUCKET, TerravibeContainerInteractions.BUCKET_EXTRACT);

        TunBlock.registerInteraction(Items.HONEY_BOTTLE, Items.AIR, TerravibeContainerInteractions.BOTTLE_INSERT);
        TunBlock.registerInteraction(Items.HONEY_BOTTLE, Items.HONEY_BOTTLE, TerravibeContainerInteractions.BOTTLE_INSERT);
        TunBlock.registerInteraction(Items.GLASS_BOTTLE, Items.HONEY_BOTTLE, TerravibeContainerInteractions.BOTTLE_EXTRACT);

        TunBlock.registerInteraction(Items.POTION, Items.AIR, TerravibeContainerInteractions.BOTTLE_INSERT);
        TunBlock.registerInteraction(Items.POTION, Items.POTION, TerravibeContainerInteractions.BOTTLE_INSERT);
        TunBlock.registerInteraction(Items.GLASS_BOTTLE, Items.POTION, TerravibeContainerInteractions.BOTTLE_EXTRACT);
        TunBlock.registerInteraction(Items.WATER_BUCKET, Items.AIR, TerravibeContainerInteractions.WATER_BUCKET_INSERT);
        TunBlock.registerInteraction(Items.WATER_BUCKET, Items.POTION, TerravibeContainerInteractions.WATER_BUCKET_INSERT);
        TunBlock.registerInteraction(Items.BUCKET, Items.POTION, TerravibeContainerInteractions.WATER_BUCKET_EXTRACT);

        TunBlock.registerInteraction(TerravibeItems.OIL_BOTTLE, Items.AIR, TerravibeContainerInteractions.BOTTLE_INSERT);
        TunBlock.registerInteraction(TerravibeItems.OIL_BOTTLE, TerravibeItems.OIL_BOTTLE, TerravibeContainerInteractions.BOTTLE_INSERT);
        TunBlock.registerInteraction(Items.GLASS_BOTTLE, TerravibeItems.OIL_BOTTLE, TerravibeContainerInteractions.BOTTLE_EXTRACT);

        TunBlock.registerInteraction(TerravibeItems.TOMATO_SAUCE_BOTTLE, Items.AIR, TerravibeContainerInteractions.BOTTLE_INSERT);
        TunBlock.registerInteraction(TerravibeItems.TOMATO_SAUCE_BOTTLE, TerravibeItems.TOMATO_SAUCE_BOTTLE, TerravibeContainerInteractions.BOTTLE_INSERT);
        TunBlock.registerInteraction(Items.GLASS_BOTTLE, TerravibeItems.TOMATO_SAUCE_BOTTLE, TerravibeContainerInteractions.BOTTLE_EXTRACT);
    }

    static {
        BOTTLE_INSERT = (state, world, pos, player, hand, contained, level) -> {
            if (contained.isEmpty() || ItemStack.canCombine(hand, contained)) {
                return ContainerInteractionResult.insert(
                        hand, //contained
                        new ItemStack(Items.GLASS_BOTTLE), //drop
                        1, //consumed
                        level + 1, //level
                        SoundEvents.ITEM_BOTTLE_EMPTY //sound
                );
            }
            else return ContainerInteractionResult.none(ActionResult.PASS);
        };
        BOTTLE_EXTRACT = (state, world, pos, player, hand, contained, level) -> {
            return ContainerInteractionResult.extract(
                    contained, //drop
                    1, //consumed
                    level - 1, //level
                    SoundEvents.ITEM_BOTTLE_FILL //sound
            );
        };
        BUCKET_INSERT = (state, world, pos, player, hand, contained, level) -> {
            if (contained.isEmpty() || ItemStack.canCombine(hand, contained)) {
                return ContainerInteractionResult.insert(
                        hand, //contained
                        new ItemStack(Items.BUCKET), //drop
                        1, //consumed
                        level + 3, //level
                        SoundEvents.ITEM_BUCKET_EMPTY //sound
                );
            }
            else return ContainerInteractionResult.none(ActionResult.PASS);
        };
        BUCKET_EXTRACT = (state, world, pos, player, hand, contained, level) -> {
            return ContainerInteractionResult.extract(
                    contained, //drop
                    1, //consumed
                    level - 3, //level
                    SoundEvents.ITEM_BUCKET_FILL //sound
            );
        };
        WATER_BUCKET_INSERT = (state, world, pos, player, hand, contained, level) -> {
            if (contained.isEmpty() || PotionUtil.getPotion(contained) == Potions.WATER) {
                return ContainerInteractionResult.insert(
                        PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.WATER), //contained
                        new ItemStack(Items.BUCKET), //drop
                        1, //consumed
                        level + 3, //level
                        SoundEvents.ITEM_BUCKET_EMPTY //sound
                );
            }
            else return ContainerInteractionResult.none(ActionResult.PASS);
        };
        WATER_BUCKET_EXTRACT = (state, world, pos, player, hand, contained, level) -> {
            if (PotionUtil.getPotion(contained) == Potions.WATER) {
                return ContainerInteractionResult.extract(
                        new ItemStack(Items.WATER_BUCKET), //drop
                        1, //consumed
                        level - 3, //level
                        SoundEvents.ITEM_BUCKET_FILL //sound
                );
            }
            else return ContainerInteractionResult.none(ActionResult.PASS);
        };
    }
}
