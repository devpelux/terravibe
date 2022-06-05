package xyz.devpelux.terravibe.recipe;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

/** List of all the recipe types. */
public class RecipeTypeList {
    /** Crushing recipe: Crushes an item to obtain another item with a successful chance. */
    public static final RecipeType<CrushingRecipe> CRUSHING;

    /** Shredding recipe: Shreds a bunch of items to obtain something into a container. */
    public static final RecipeType<ShreddingRecipe> SHREDDING;

    /** Loads all the recipe types. */
    public static void load() {}

    /** Registers the specified recipe type with the specified id. */
    private static <T extends Recipe<?>> RecipeType<T> register(@NotNull Identifier id) {
        return RecipeType.register(id.toString());
    }

    static {
        CRUSHING = register(CrushingRecipe.ID);
        SHREDDING = register(ShreddingRecipe.ID);
    }
}
