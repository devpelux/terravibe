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

/** Crushes an item to obtain another item with a successful chance. */
public class CrushingRecipe extends CuttingRecipe {
    /** Identifier of the recipe. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "crushing");

    /** Json serializer of the {@link CrushingRecipe}. */
    public static final RecipeSerializer<CrushingRecipe> CRUSHING_RECIPE_SERIALIZER =
            Registry.register(Registry.RECIPE_SERIALIZER, ID, new Serializer());

    protected final Random random = Random.create();

    protected final float chance;
    protected final int experience;

    /** Initializes a new {@link CrushingRecipe}. */
    public CrushingRecipe(Identifier id, String group, Ingredient input, ItemStack output, float chance, int experience) {
        super(RecipeTypeList.CRUSHING, CRUSHING_RECIPE_SERIALIZER, id, group, input, output);
        this.chance = chance;
        this.experience = experience;
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
        return random.nextFloat() <= getChance();
    }

    /** Gets the experience amount value of the recipe. */
    public int getExperience() {
        return experience;
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

            //Experience
            int experience = JsonHelper.hasElement(json, "experience") ? JsonHelper.getInt(json, "experience") : 0;

            return new CrushingRecipe(id, group, input, output, chance, experience);
        }

        /** Reads the recipe from a web packet. */
        @Override
        public CrushingRecipe read(Identifier id, @NotNull PacketByteBuf buf) {
            String group = buf.readString();
            Ingredient input = Ingredient.fromPacket(buf);
            ItemStack output = buf.readItemStack();
            float chance = buf.readFloat();
            int experience = buf.readInt();
            return new CrushingRecipe(id, group, input, output, chance, experience);
        }

        /** Writes the recipe into a web packet. */
        @Override
        public void write(@NotNull PacketByteBuf buf, @NotNull CrushingRecipe recipe) {
            buf.writeString(recipe.getGroup());
            recipe.getInput().write(buf);
            buf.writeItemStack(recipe.getOutput());
            buf.writeFloat(recipe.getChance());
            buf.writeInt(recipe.getExperience());
        }
    }
}
