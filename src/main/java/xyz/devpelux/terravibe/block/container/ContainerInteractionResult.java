package xyz.devpelux.terravibe.block.container;

import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/** Represents a result of an interaction with a container. */
public class ContainerInteractionResult {
    private final int consumed;
    private final @NotNull ItemStack contained;
    private final @NotNull ItemStack drop;
    private final @NotNull Interaction interaction;
    private final int level;
    private final @NotNull ActionResult action;
    private final @Nullable SoundEvent sound;

    /** {@link ContainerInteractionResult} with no interaction. */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull ContainerInteractionResult none(@NotNull ActionResult action) {
        return new ContainerInteractionResult(
                Interaction.NONE, //interaction
                action, //action
                ItemStack.EMPTY, //contained
                ItemStack.EMPTY, //drop
                0, //consumed
                0, //level
                null //sound
        );
    }

    /** {@link ContainerInteractionResult} with insert interaction. */
    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    public static @NotNull ContainerInteractionResult insert(@NotNull ItemStack contained, @NotNull ItemStack drop, int consumed, int level, @Nullable SoundEvent sound) {
        return new ContainerInteractionResult(
                Interaction.INSERT, //interaction
                ActionResult.SUCCESS, //action
                contained, //contained
                drop, //drop
                consumed, //consumed
                level, //level
                sound //sound
        );
    }

    /** {@link ContainerInteractionResult} with extract interaction. */
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull ContainerInteractionResult extract(@NotNull ItemStack drop, int consumed, int level, @Nullable SoundEvent sound) {
        return new ContainerInteractionResult(
                Interaction.EXTRACT, //interaction
                ActionResult.SUCCESS, //action
                ItemStack.EMPTY, //contained
                drop, //drop
                consumed, //consumed
                level, //level
                sound //sound
        );
    }

    /**
     * Initializes a new {@link ContainerInteractionResult}.
     * @param interaction Interaction type.
     * @param action Action result of the operation.
     * @param contained Contained stack to set.
     * @param drop Stack to drop.
     * @param consumed Number of items to consume from the main hand.
     * @param level Level to set.
     * @param sound Sound to play. (null to play no sound)
     */
    private ContainerInteractionResult(@NotNull Interaction interaction, @NotNull ActionResult action,
                                       @NotNull ItemStack contained, @NotNull ItemStack drop,
                                       int consumed, int level, @Nullable SoundEvent sound) {
        this.interaction = interaction;
        this.action = action;
        this.contained = contained;
        this.drop = drop;
        this.consumed = consumed;
        this.level = level;
        this.sound = sound;
    }

    /** Gets the number of items to consume from the main hand. */
    public int getConsumed() {
        return consumed;
    }

    /** Gets the contained stack to set. */
    public @NotNull ItemStack getContained() {
        return contained;
    }

    /** Gets the stack to drop. */
    public @NotNull ItemStack getDrop() {
        return drop;
    }

    /** Gets the interaction type. */
    public @NotNull Interaction getInteraction() {
        return interaction;
    }

    /** Gets the level to set. */
    public int getLevel() {
        return level;
    }

    /** Gets the action result of the operation. */
    public @NotNull ActionResult getAction() {
        return action;
    }

    /** Gets the sound to play. */
    public @NotNull Optional<SoundEvent> getSound() {
        return Optional.ofNullable(sound);
    }


    /** Represents an interaction type. */
    public enum Interaction {
        /** Insert something. */
        INSERT,

        /** Extract something. */
        EXTRACT,

        /** Do nothing. */
        NONE
    }
}
