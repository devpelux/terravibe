package xyz.devpelux.terravibe.recipe;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import xyz.devpelux.terravibe.core.Terravibe;

/**
 * List of all the recipe serializers.
 */
public final class TerravibeRecipeSerializers {
	/**
	 * Json serializer of the {@link CrushingRecipe}.
	 */
	public static final RecipeSerializer<CrushingRecipe> CRUSHING;

	/**
	 * Json serializer of the {@link ShreddingRecipe}.
	 */
	public static final RecipeSerializer<ShreddingRecipe> SHREDDING;

	/**
	 * Registers the specified recipe serializer with the specified id.
	 */
	private static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
		return Registry.register(Registries.RECIPE_SERIALIZER, Terravibe.identified(id), serializer);
	}

	static {
		CRUSHING = register("crushing", new CrushingRecipe.Serializer());
		SHREDDING = register("shredding", new ShreddingRecipe.Serializer());
	}
}
