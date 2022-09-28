package xyz.devpelux.terravibe.recipe;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import xyz.devpelux.terravibe.core.Terravibe;

/** List of all the recipe types. */
public final class TerravibeRecipeTypes {
    private TerravibeRecipeTypes() {}

    /** Crushes an item to obtain another item with a successful chance. */
    public static final RecipeType<CrushingRecipe> CRUSHING;

    /** Shreds a bunch of items to obtain something into a container. */
    public static final RecipeType<ShreddingRecipe> SHREDDING;

    /** Loads all the recipe types. */
    public static void load() {}

    /** Registers the specified recipe type with the specified id. */
    private static <T extends Recipe<?>> RecipeType<T> register(String id) {
        return RecipeType.register(new Identifier(Terravibe.ID, id).toString());
    }

    static {
        CRUSHING = register("crushing");
        SHREDDING = register("shredding");
    }
}
