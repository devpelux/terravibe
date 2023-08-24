package xyz.devpelux.terravibe.recipe;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

/**
 * Basic shapeless recipe based to an inventory.
 */
public abstract class InventoryRecipe implements Recipe<Inventory> {
	protected final Identifier id;
	protected final String group;
	protected final DefaultedList<Ingredient> ingredients;
	private final RecipeType<?> type;
	private final RecipeSerializer<?> serializer;

	/**
	 * Initializes a new instance.
	 */
	public InventoryRecipe(RecipeType<?> type, RecipeSerializer<?> serializer, Identifier id, String group, DefaultedList<Ingredient> ingredients) {
		this.id = id;
		this.type = type;
		this.serializer = serializer;
		this.group = group;
		this.ingredients = ingredients;
	}

	/**
	 * Crafts the result.
	 */
	@Override
	public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
		return getOutput(registryManager).copy();
	}

	/**
	 * Gets a value indicating if the shape of the recipe fits the recipe.
	 * This is a shapeless recipe, so it will return always true.
	 */
	@Override
	public boolean fits(int width, int height) {
		return true;
	}

	/**
	 * Gets the id.
	 */
	@Override
	public Identifier getId() {
		return id;
	}

	/**
	 * Gets the ingredients.
	 */
	@Override
	public DefaultedList<Ingredient> getIngredients() {
		return ingredients;
	}

	/**
	 * Gets the group.
	 */
	@Override
	public String getGroup() {
		return this.group;
	}

	/**
	 * Gets the recipe serializer.
	 */
	@Override
	public RecipeSerializer<?> getSerializer() {
		return serializer;
	}

	/**
	 * Gets the recipe type.
	 */
	@Override
	public RecipeType<?> getType() {
		return type;
	}
}
