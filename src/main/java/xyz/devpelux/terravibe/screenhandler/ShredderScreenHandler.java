package xyz.devpelux.terravibe.screenhandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.blockentity.ShredderBlockEntity;
import xyz.devpelux.terravibe.core.ResultSlot;
import xyz.devpelux.terravibe.recipe.ShreddingRecipe;
import xyz.devpelux.terravibe.recipe.TerravibeRecipeTypes;

import java.util.Optional;

/** Screen handler for the {@link ShredderBlockEntity} UI: handles the interactions with the UI. */
public class ShredderScreenHandler extends ScreenHandler {
    /** Current world. */
    private final World world;

    /** Inventory for the ingredients. */
    private final SimpleInventory ingredients = new SimpleInventory(4);

    /** Inventory for the container. */
    private final SimpleInventory container = new SimpleInventory(1);

    /** Inventory for the output. */
    private final SimpleInventory output = new SimpleInventory(1);

    /** Initializes a new {@link ShredderScreenHandler}. */
    public ShredderScreenHandler(int syncId, @NotNull PlayerInventory playerInventory) {
        super(TerravibeScreenHandlerTypes.SHREDDER, syncId);
        this.world = playerInventory.player.world;
        ingredients.addListener(this::onUpdatedInputs);
        container.addListener(this::onUpdatedInputs);
        loadSlots(playerInventory);
    }

    /** Loads the slots of the UI. */
    private void loadSlots(@NotNull PlayerInventory playerInventory) {
        //Ingredients: 0 ... 3
        for (int j = 0; j < 2; ++j) {
            for (int k = 0; k < 2; ++k) {
                this.addSlot(new Slot(ingredients, k + j * 2, 16 + k * 18, 26 + j * 18));
            }
        }

        //Container: 4
        this.addSlot(new Slot(container, 0, 83, 34));

        //Output: 5
        this.addSlot(new ResultSlot(playerInventory.player, output, 0, 141, 35).setOnItemTakenListener(this::onResultTaken));

        //Player inventory and hot bar: 6 ... n
        for (int j = 0; j < 3; ++j) {
            for (int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
            }
        }
        for (int j = 0; j < 9; ++j) {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 142));
        }
    }

    /** Executed when the screen handler is closed. */
    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        //Drops all the ingredients and the container.
        dropInventory(player, ingredients);
        dropInventory(player, container);
    }

    /** Returns a value indicating if the player can use the UI. */
    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    /** Gets the take sound. */
    protected SoundEvent getTakeSound() {
        return SoundEvents.UI_STONECUTTER_TAKE_RESULT;
    }

    /** Executed when the result item stack from the result slot is taken. */
    public void onResultTaken(PlayerEntity player, ItemStack stack) {
        if (!world.isClient()) {
            //Consumes all the ingredients and the container.
            getSlot(0).getStack().decrement(1);
            getSlot(1).getStack().decrement(1);
            getSlot(2).getStack().decrement(1);
            getSlot(3).getStack().decrement(1);
            getSlot(4).getStack().decrement(1);

            //Plays the take sound.
            player.playSound(getTakeSound(), SoundCategory.BLOCKS, 1f, 1f);

            onUpdatedInputs(null);
        }
    }

    /** Executed when the input items (ingredients and container) are updated. */
    public void onUpdatedInputs(Inventory inventory) {
        if (!world.isClient()) {
            SimpleInventory inputs = new SimpleInventory(5);
            inputs.setStack(0, getSlot(0).getStack());
            inputs.setStack(1, getSlot(1).getStack());
            inputs.setStack(2, getSlot(2).getStack());
            inputs.setStack(3, getSlot(3).getStack());
            inputs.setStack(4, getSlot(4).getStack());

            //Checks if there is a valid recipe to craft something.
            Optional<ShreddingRecipe> match = world.getRecipeManager().getFirstMatch(TerravibeRecipeTypes.SHREDDING, inputs, world);

            if (match.isPresent()) {
                //If the recipe exists then set the output item stack to the crafting result.
                if (!getSlot(5).hasStack()) getSlot(5).setStack(match.get().getOutput().copy());
            }
            else {
                //If the recipe does not exist then clean the output item stack.
                if (getSlot(5).hasStack()) getSlot(5).setStack(ItemStack.EMPTY);
            }
        }
    }

    /**
     * Transfer a slot away from the specified slot when clicking the slot with the combination for auto transfer.
     * (Eg. CTRL + CLICK)
     */
    @Override
    public ItemStack transferSlot(PlayerEntity player, int originIndex) {
        //Gets the slot to transfer.
        Slot originSlot = getSlot(originIndex);

        if (originSlot.hasStack()) {
            ItemStack originStack = originSlot.getStack();

            if (originIndex <= 5) {
                if (!insertItem(originStack.copy(), 6, slots.size(), false)) return ItemStack.EMPTY;
            } else {
                if (!insertItem(originStack.copy(), 0, 3, false)) return ItemStack.EMPTY;
            }

            originSlot.setStack(ItemStack.EMPTY);
            originSlot.onTakeItem(player, originStack.copy());

            return originStack;
        }

        return ItemStack.EMPTY;
    }
}
