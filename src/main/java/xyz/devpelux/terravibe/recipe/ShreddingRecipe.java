package xyz.devpelux.terravibe.recipe;

import com.google.gson.*;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;

import java.util.ArrayList;

/** Shreds a bunch of items to obtain something into a container. */
public class ShreddingRecipe implements Recipe<Inventory> {
    /** Identifier of the recipe. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "shredding");

    /** Json serializer of the {@link ShreddingRecipe}. */
    public static final RecipeSerializer<ShreddingRecipe> SHREDDING_RECIPE_SERIALIZER =
            Registry.register(Registry.RECIPE_SERIALIZER, ID, new ShreddingRecipe.Serializer());

    protected final Identifier id;
    private final RecipeType<?> type;
    private final RecipeSerializer<?> serializer;

    protected final DefaultedList<Ingredient> ingredients;
    protected final Ingredient container;
    protected final ItemStack output;

    /** Initializes a new {@link ShreddingRecipe}. */
    public ShreddingRecipe(Identifier id, DefaultedList<Ingredient> ingredients, Ingredient container, ItemStack output) {
        this(TerravibeRecipeTypes.SHREDDING, SHREDDING_RECIPE_SERIALIZER, id, ingredients, container, output);
    }

    /** Initializes a new {@link ShreddingRecipe}. */
    public ShreddingRecipe(RecipeType<?> type, RecipeSerializer<?> serializer, Identifier id,
                           DefaultedList<Ingredient> ingredients, Ingredient container,  ItemStack output) {
        this.type = type;
        this.serializer = serializer;
        this.id = id;
        this.ingredients = ingredients;
        this.container = container;
        this.output = output;
    }

    /** Gets the ingredients. */
    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return ingredients;
    }

    /** Gets the container. */
    public Ingredient getContainer() {
        return container;
    }

    /** Gets the output. */
    @Override
    public ItemStack getOutput() {
        return output;
    }

    /** Checks if the specified inventory matches the ingredient list. */
    @Override
    public boolean matches(@NotNull Inventory inventory, World world) {
        //Copies the ingredient list into a list for convenience (and avoid modifying the inventory).
        ArrayList<ItemStack> itemStacks = new ArrayList<>();
        for (int i = 0; i < inventory.size() - 1; i++) {
            //Ignores the empty elements (they are useless because this is a shapeless recipe).
            if (!inventory.getStack(i).isEmpty()) itemStacks.add(inventory.getStack(i));
        }

        //Checks if all the ingredients are in the inventory.
        for (Ingredient ingredient : ingredients) {
            boolean found = false;

            //Checks if the ingredient is in the list.
            //If the list is empty this loop is not executed and found remains false (ingredient not found).
            for (int i = 0; i < itemStacks.size() && !found; i++) {
                if (ingredient.test(itemStacks.get(i))) {
                    //If the ingredient is found in the list, removes it, then checks for the next ingredient.
                    itemStacks.remove(i);
                    found = true;
                }
            }

            //If the ingredient is not found in the list, the inventory does not match the recipe, so returns false.
            if (!found) return false;
        }

        //Checks if all the items in the inventory are valid ingredient (no item left in the list).
        //Then checks if the container is valid (the container must be the last item stack of the inventory).
        return itemStacks.size() == 0 && container.test(inventory.getStack(inventory.size() - 1));
    }

    /** Crafts the result. */
    @Override
    public ItemStack craft(Inventory inventory) {
        return getOutput().copy();
    }

    /**
     * Gets a value indicating if the shape of the recipe fits the recipe.
     * This is a shapeless recipe, so it will return always true.
     */
    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    /** Gets the recipe id. */
    @Override
    public Identifier getId() {
        return id;
    }

    /** Gets the recipe serializer. */
    @Override
    public RecipeSerializer<?> getSerializer() {
        return serializer;
    }

    /** Gets the recipe type. */
    @Override
    public RecipeType<?> getType() {
        return type;
    }


    /** {@link ShreddingRecipe} json serializer. */
    public static class Serializer implements RecipeSerializer<ShreddingRecipe> {
        /** Initializes a new {@link CrushingRecipe.Serializer}. */
        public Serializer() {}

        /** Reads the recipe from a json object. */
        @Override
        public ShreddingRecipe read(Identifier id, JsonObject json) {
            ShreddingRecipeFormat recipeJsonFormat = new Gson().fromJson(json, ShreddingRecipeFormat.class);

            if (recipeJsonFormat.ingredients != null && recipeJsonFormat.container != null && recipeJsonFormat.output != null) {
                if (recipeJsonFormat.ingredients.size() > 0) {
                    //Ingredients
                    DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(recipeJsonFormat.ingredients.size());
                    for (JsonElement je : recipeJsonFormat.ingredients) {
                        if (je.isJsonObject()) ingredients.add(Ingredient.fromJson(je.getAsJsonObject()));
                        else throw new JsonSyntaxException("ShreddingRecipe: All ingredients must be JsonObjects");
                    }

                    //Container
                    Ingredient container = Ingredient.fromJson(recipeJsonFormat.container);

                    //Output
                    Item outputItem = Registry.ITEM.getOrEmpty(new Identifier(recipeJsonFormat.output))
                            .orElseThrow(() -> new JsonSyntaxException("ShreddingRecipe: No such output item: " + recipeJsonFormat.output));
                    ItemStack output = new ItemStack(outputItem, 1);

                    return new ShreddingRecipe(id, ingredients, container, output);
                }
                else {
                    throw new JsonSyntaxException("ShreddingRecipe: Required at least 1 ingredient");
                }
            }
            else {
                throw new JsonSyntaxException("ShreddingRecipe: Missing json attributes");
            }
        }

        /** Reads the recipe from a web packet. */
        @Override
        public ShreddingRecipe read(Identifier id, PacketByteBuf buf) {
            int ingCount = buf.readInt();
            DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(ingCount);
            for (int i = 0; i < ingCount; i++) {
                ingredients.add(Ingredient.fromPacket(buf));
            }
            Ingredient container = Ingredient.fromPacket(buf);
            ItemStack output = buf.readItemStack();
            return new ShreddingRecipe(id, ingredients, container, output);
        }

        /** Writes the recipe into a web packet. */
        @Override
        public void write(PacketByteBuf buf, ShreddingRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.write(buf);
            }
            recipe.getContainer().write(buf);
            buf.writeItemStack(recipe.getOutput());
        }
    }


    /** {@link ShreddingRecipe} json format. */
    public static class ShreddingRecipeFormat {
        public JsonArray ingredients;
        public JsonObject container;
        public String output;
    }
}
