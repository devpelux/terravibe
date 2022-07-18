package xyz.devpelux.terravibe.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.CuttingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.core.Util;

import java.util.Objects;

/** Crushes an item to obtain other items. */
public class CrushingRecipe extends CuttingRecipe {
    /** Identifier of the recipe. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "crushing");

    /** Json serializer of the {@link CrushingRecipe}. */
    public static final RecipeSerializer<CrushingRecipe> CRUSHING_RECIPE_SERIALIZER =
            Registry.register(Registry.RECIPE_SERIALIZER, ID, new Serializer());

    protected final int minCount;
    protected final int maxCount;

    /** Initializes a new {@link CrushingRecipe}. */
    public CrushingRecipe(Identifier id, String group, Ingredient input, ItemStack output, int minCount, int maxCount) {
        super(TerravibeRecipeTypes.CRUSHING, CRUSHING_RECIPE_SERIALIZER, id, group, input, output);
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    /** Gets the input ingredient. */
    public Ingredient getInput() {
        return input;
    }

    /** Gets the min count of the result. */
    public int getMinCount() {
        return minCount;
    }

    /** Gets the max count of the result. */
    public int getMaxCount() {
        return maxCount;
    }

    /** Gets a random count from min count and max count of the result. */
    public int getRandomCount(@NotNull Random random) {
        return random.nextBetween(minCount, maxCount);
    }

    /** Checks if the specified inventory matches the ingredient list. */
    @Override
    public boolean matches(@NotNull Inventory inventory, World world) {
        return inventory.size() == 1 && this.input.test(inventory.getStack(0));
    }


    /** {@link CrushingRecipe} json serializer. */
    public static class Serializer implements RecipeSerializer<CrushingRecipe> {
        /** Initializes a new {@link Serializer}. */
        public Serializer() {}

        /** Reads the recipe from a json object. */
        @Override
        public CrushingRecipe read(Identifier id, JsonObject json) {
            //Deserializing recipe json
            CrushingRecipeFormat recipe = new Gson().fromJson(json, CrushingRecipeFormat.class);

            //Group
            String group = Objects.requireNonNullElse(recipe.group, "");

            //Input
            Ingredient input = Ingredient.fromJson(recipe.ingredient);

            //Checking if there is a result
            if (recipe.result == null) throw new JsonSyntaxException("No recipe result");

            //Result
            ItemStack output = Util.getStackFromName(recipe.result.item);

            //Result min and max count
            int maxCount = recipe.result.max_count;
            int minCount = Math.min(recipe.result.min_count, maxCount);

            return new CrushingRecipe(id, group, input, output, minCount, maxCount);
        }

        /** Reads the recipe from a web packet. */
        @Override
        public CrushingRecipe read(Identifier id, @NotNull PacketByteBuf buf) {
            String group = buf.readString();
            Ingredient input = Ingredient.fromPacket(buf);
            ItemStack output = buf.readItemStack();
            int minCount = buf.readInt();
            int maxCount = buf.readInt();
            return new CrushingRecipe(id, group, input, output, minCount, maxCount);
        }

        /** Writes the recipe into a web packet. */
        @Override
        public void write(@NotNull PacketByteBuf buf, @NotNull CrushingRecipe recipe) {
            buf.writeString(recipe.getGroup());
            recipe.getInput().write(buf);
            buf.writeItemStack(recipe.getOutput());
            buf.writeInt(recipe.getMinCount());
            buf.writeInt(recipe.getMaxCount());
        }


        /** {@link CrushingRecipe} json format. */
        private static class CrushingRecipeFormat {
            public String group;
            public JsonElement ingredient;
            public ResultFormat result;

            /** {@link CrushingRecipeFormat} result json format. */
            public static class ResultFormat {
                public String item;
                public int min_count;
                public int max_count;
            }
        }
    }
}
