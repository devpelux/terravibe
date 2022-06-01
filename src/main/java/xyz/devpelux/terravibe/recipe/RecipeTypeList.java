package xyz.devpelux.terravibe.recipe;

import net.minecraft.recipe.RecipeType;

/** List of all the recipe types. */
public class RecipeTypeList {
    /** Converting recipe: Converts an item to another with a percentage chance. */
    public static final RecipeType<ConvertingRecipe> CONVERTING_RECIPE = RecipeType.register(ConvertingRecipe.ID.toString());

    /** Loads all the recipe types. */
    public static void load() {}
}
