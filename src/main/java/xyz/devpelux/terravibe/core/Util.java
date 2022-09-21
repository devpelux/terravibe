package xyz.devpelux.terravibe.core;

import com.google.gson.JsonSyntaxException;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.Identifier;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Contract;
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

    /** Converts a value from a range [0 ... maxValue] to a range [0 ... newMaxValue]. */
    public static float lerpFromMaxToMax(float value, float maxValue, float newMaxValue) {
        return value / maxValue * newMaxValue;
    }

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

    /** Gets the default stack from the recipe remainder of the specified item. */
    @Contract("_ -> new")
    public static @NotNull ItemStack getRemainder(@NotNull ItemStack stack) {
        return new ItemStack(stack.getItem().getRecipeRemainder());
    }

    /** Generates an {@link NbtCompound} representing the specified potion. */
    public static @NotNull NbtCompound potionToNbt(Potion potion) {
        Identifier identifier = Registry.POTION.getId(potion);
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Potion", identifier.toString());
        return nbt;
    }

    /** Gets the water color basing on a biomes-based color and the potion contained. */
    public static int getWaterColor(BlockRenderView view, BlockPos pos, Potion potion) {
        if (potion == Potions.WATER) {
            return BiomeColors.getWaterColor(view, pos);
        }
        else {
            return PotionUtil.getColor(potion);
        }
    }
}
