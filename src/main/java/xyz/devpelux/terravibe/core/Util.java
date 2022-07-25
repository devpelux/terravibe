package xyz.devpelux.terravibe.core;

import com.google.gson.JsonSyntaxException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.stream.Stream;

/** Contains some utilities. */
public class Util {
    private Util() {}

    /** This logger is used to write text to the console and the log file. */
    public static final Logger LOGGER = LoggerFactory.getLogger(ModInfo.MOD_ID);

    /** Combines the specified voxel shapes. */
    public static @NotNull VoxelShape combineVoxelShapes(VoxelShape... shapes) {
        return Stream.of(shapes).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).orElse(VoxelShapes.empty());
    }

    /** Gets the stack from the name. */
    public static @NotNull ItemStack getStackFromName(@Nullable String id) {
        if (id != null) {
            Optional<Item> item = Registry.ITEM.getOrEmpty(new Identifier(id));
            if (item.isPresent()) {
                return new ItemStack(item.get());
            }
        }
        throw new JsonSyntaxException("Invalid item '" + id + "'");
    }

    /** Get an item stack from a nbt. */
    public static @NotNull ItemStack getStackFromNbt(@NotNull NbtCompound nbt) {
        NbtCompound stackNbt = nbt.getCompound("Stack");
        return ItemStack.fromNbt(stackNbt);
    }

    /** Put an item stack to a nbt. */
    public static void putStackToNbt(@NotNull NbtCompound nbt, @NotNull ItemStack stack) {
        NbtCompound stackNbt = new NbtCompound();
        stack.writeNbt(stackNbt);
        nbt.put("Stack", stackNbt);
    }

    /** Copies an item stack with the specified amount. */
    public static @NotNull ItemStack copyStack(@NotNull ItemStack stack, int amount) {
        ItemStack copied = stack.copy();
        copied.setCount(amount);
        return copied;
    }
}
