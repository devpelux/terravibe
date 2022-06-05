package xyz.devpelux.terravibe.recipe;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

/** List of all the recipe types. */
public class RecipeTypeList {
    /** Crushing recipe: Crushes an item to obtain another item with a successful chance. */
    public static final RecipeType<CrushingRecipe> CRUSHING;

    /** Loads all the recipe types. */
    public static void load() {}

    /** Registers the specified recipe type with the specified id. */
    private static <T extends Recipe<?>> RecipeType<T> register(@NotNull Identifier id) {
        return RecipeType.register(id.toString());
    }

    static {
        CRUSHING = register(CrushingRecipe.ID);
    }
}
