package xyz.devpelux.terravibe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.entity.damage.TerravibeDamageSources;

/** Little poisonous berry with a black color. */
public class NightlockBerriesItem extends AliasedBlockItem {
    /** Identifier of the item. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "nightlock_berries");

    /** Composting chance of the item. */
    public static final float COMPOSTING_CHANCE = 0.3F;

    /** Initializes a new {@link NightlockBerriesItem}. */
    public NightlockBerriesItem(Block block, Settings settings) {
        super(block, settings);
    }

    /** Gets the item settings. */
    public static @NotNull FabricItemSettings getSettings() {
        FoodComponent foodEffects = new FoodComponent.Builder()
                .hunger(2)
                .saturationModifier(0.1F)
                .build();

        return new FabricItemSettings()
                .maxCount(64)
                .food(foodEffects)
                .group(TerravibeItemGroups.TERRAVIBE);
    }

    /**
     * Executed when an entity finishes using the item.
     * Eats the berry.
     */
    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity entity) {
        if (this.isFood()) {
            ItemStack eatenStack = entity.eatFood(world, stack);
            if (entity.getType() != EntityType.FOX) {
                //The nightlock kills the entity after being eaten.
                if (entity instanceof PlayerEntity player && player.getAbilities().creativeMode) {
                    //Even in creative mode!
                    entity.damage(TerravibeDamageSources.NIGHTLOCK_CREATIVE, Float.MAX_VALUE);
                }
                else entity.damage(TerravibeDamageSources.NIGHTLOCK, Float.MAX_VALUE);
            }
            else {
                //Foxes are only poisoned, not killed.
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 80, 0));
            }
            return eatenStack;
        }
        return stack;
    }
}
