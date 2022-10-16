package xyz.devpelux.terravibe.recipe;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import xyz.devpelux.terravibe.core.Terravibe;

/**
 * List of all the recipe types.
 */
public final class TerravibeRecipeTypes {
	/**
	 * Crushes an item to obtain another item with a successful chance.
	 */
	public static final RecipeType<CrushingRecipe> CRUSHING;

	/**
	 * Shreds a bunch of items to obtain something into a container.
	 */
	public static final RecipeType<ShreddingRecipe> SHREDDING;

	private TerravibeRecipeTypes() {
	}

	/**
	 * Loads all the recipe types.
	 */
	public static void load() {
	}

	/**
	 * Registers a new recipe type with the specified id.
	 */
	private static <T extends Recipe<?>> RecipeType<T> register(String id) {
		return RecipeType.register(Terravibe.identified(id).toString());
	}

	static {
		CRUSHING = register("crushing");
		SHREDDING = register("shredding");
	}
}
