package xyz.devpelux.terravibe.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/** Crop that can drop fruits without being broken. */
public abstract class FruitCropBlock extends CropBlock {
    /** Initializes a new {@link FruitCropBlock}. */
    public FruitCropBlock(Settings settings) {
        super(settings);
    }

    /** Gets the age when the plant is fully grown, and is ready to make fruits. */
    public abstract int getFloweringAge();

    /** Gets the fruit item to pick from the plant. */
    public abstract ItemConvertible getFruitItem();

    /** Gets the amount of the fruit item to pick from the plant. */
    public abstract int getFruitItemAmount(@NotNull Random random, @NotNull BlockState state);

    /** Gets the pick sound. */
    public abstract Optional<SoundEvent> getPickSound();

    /**
     * Executed when the plant is used.
     * Gets the drops, then reset the plant age to the flowering age.
     */
    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(@NotNull BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.get(AGE) == getMaxAge()) {
            //Drops the specified fruit amount if the age is the max age.
            int nDrops = getFruitItemAmount(world.random, state);
            dropStack(world, pos, new ItemStack(getFruitItem(), nDrops));

            //Plays the pick sound.
            getPickSound().ifPresent(sound -> world.playSound(null, pos, sound, SoundCategory.BLOCKS,
                    1.0F, 0.8F + world.random.nextFloat() * 0.4F));

            //Reset the plant age to the flowering age.
            BlockState blockState = state.with(AGE, getFloweringAge());
            world.setBlockState(pos, blockState, 2);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, blockState));
            return ActionResult.success(world.isClient);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
}
