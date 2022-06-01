package xyz.devpelux.terravibe.recipe;

import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.CuttingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;

/** Recipe to convert an item to another with a successful value. */
public class ConvertingRecipe extends CuttingRecipe {
    /** Identifier of the recipe. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "converting");

    /** Json serializer of the {@link ConvertingRecipe}. */
    public static final RecipeSerializer<ConvertingRecipe> CONVERTING_RECIPE_SERIALIZER =
            Registry.register(Registry.RECIPE_SERIALIZER, ID, new Serializer());

    protected final float chance;
    protected final Random random = Random.create();

    /** Initializes a new {@link ConvertingRecipe}. */
    public ConvertingRecipe(Identifier id, String group, Ingredient input, ItemStack output, float chance) {
        super(RecipeTypeList.CONVERTING_RECIPE, CONVERTING_RECIPE_SERIALIZER, id, group, input, output);
        this.chance = chance;
    }

    /** Gets the input ingredient. */
    public Ingredient getInput() {
        return input;
    }

    /** Gets the chance of success. */
    public float getChance() {
        return chance;
    }

    /** Executes an attempt and returns if it was successful. */
    public boolean isSuccessful() {
        return random.nextFloat() > getChance();
    }

    /** Checks if the specified inventory matches the ingredient list. */
    @Override
    public boolean matches(@NotNull Inventory inventory, World world) {
        return inventory.size() == 1 && this.input.test(inventory.getStack(0));
    }


    /** {@link ConvertingRecipe} json serializer. */
    public static class Serializer implements RecipeSerializer<ConvertingRecipe> {

        /** Initializes a new {@link Serializer}. */
        public Serializer() {}

        /** Reads the recipe from a json object. */
        @Override
        public ConvertingRecipe read(Identifier id, JsonObject json) {
            //Group
            String group = JsonHelper.getString(json, "group", "");

            //Input ingredient
            Ingredient input;
            if (JsonHelper.hasArray(json, "ingredient")) {
                input = Ingredient.fromJson(JsonHelper.getArray(json, "ingredient"));
            } else {
                input = Ingredient.fromJson(JsonHelper.getObject(json, "ingredient"));
            }

            //Output ingredient
            String outputItem = JsonHelper.getString(json, "result");
            int itemCount = JsonHelper.getInt(json, "count");
            ItemStack output = new ItemStack(Registry.ITEM.get(new Identifier(outputItem)), itemCount);

            //Chance
            float chance = JsonHelper.getFloat(json, "chance");

            return new ConvertingRecipe(id, group, input, output, chance);
        }

        /** Reads the recipe from a web packet. */
        @Override
        public ConvertingRecipe read(Identifier id, @NotNull PacketByteBuf buf) {
            String group = buf.readString();
            Ingredient input = Ingredient.fromPacket(buf);
            ItemStack output = buf.readItemStack();
            float chance = buf.readFloat();
            return new ConvertingRecipe(id, group, input, output, chance);
        }

        /** Writes the recipe into a web packet. */
        @Override
        public void write(@NotNull PacketByteBuf buf, @NotNull ConvertingRecipe recipe) {
            buf.writeString(recipe.getGroup());
            recipe.getInput().write(buf);
            buf.writeItemStack(recipe.getOutput());
            buf.writeFloat(recipe.getChance());
        }
    }
}
