package xyz.devpelux.terravibe.item;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xyz.devpelux.terravibe.entity.damage.TerravibeDamageSources;

/** Little poisonous berry with a black color. */
public class NightlockBerriesItem extends AliasedBlockItem {
    /** Initializes a new {@link NightlockBerriesItem}. */
    public NightlockBerriesItem(Block block, Settings settings) {
        super(block, settings);
    }

    /**
     * Executed when an entity finishes using the item.
     * Eats the berry.
     */
    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity entity) {
        ItemStack usedStack = super.finishUsing(stack, world, entity);
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
        return usedStack;
    }
}
