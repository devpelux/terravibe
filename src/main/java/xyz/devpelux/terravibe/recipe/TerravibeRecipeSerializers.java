package xyz.devpelux.terravibe.recipe;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.devpelux.terravibe.core.ModInfo;

/** List of all the recipe serializers. */
public final class TerravibeRecipeSerializers {
    private TerravibeRecipeSerializers() {}

    /** Json serializer of the {@link CrushingRecipe}. */
    public static final RecipeSerializer<CrushingRecipe> CRUSHING;

    /** Json serializer of the {@link ShreddingRecipe}. */
    public static final RecipeSerializer<ShreddingRecipe> SHREDDING;

    /** Loads all the recipe serializers. */
    public static void load() {}

    /** Registers the specified recipe serializer with the specified id. */
    private static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        return Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(ModInfo.MOD_ID, id), serializer);
    }

    static {
        CRUSHING = register("crushing", new CrushingRecipe.Serializer());
        SHREDDING = register("shredding", new ShreddingRecipe.Serializer());
    }
}
