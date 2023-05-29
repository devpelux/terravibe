package xyz.devpelux.terravibe.recipe;

import com.google.gson.*;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Shreds a bunch of items to obtain something into a container.
 */
public class ShreddingRecipe extends InventoryRecipe {
	protected final Ingredient container;
	protected final ItemStack output;

	/**
	 * Initializes a new {@link ShreddingRecipe}.
	 */
	public ShreddingRecipe(Identifier id, DefaultedList<Ingredient> ingredients, Ingredient container, ItemStack output) {
		super(TerravibeRecipeTypes.SHREDDING, TerravibeRecipeSerializers.SHREDDING, id, "", ingredients);
		this.container = container;
		this.output = output;
	}

	/**
	 * Gets the container.
	 */
	public Ingredient getContainer() {
		return container;
	}

	/**
	 * Gets the output.
	 */
	@Override
	public ItemStack getOutput(DynamicRegistryManager registryManager) {
		return output;
	}

	/**
	 * Checks if the specified inventory matches the ingredient list.
	 */
	@Override
	public boolean matches(Inventory inventory, World world) {
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


	/**
	 * {@link ShreddingRecipe} json serializer.
	 */
	public static class Serializer implements RecipeSerializer<ShreddingRecipe> {
		/**
		 * Initializes a new {@link CrushingRecipe.Serializer}.
		 */
		public Serializer() {
		}

		/**
		 * Reads the recipe from a json object.
		 */
		@Override
		public ShreddingRecipe read(Identifier id, JsonObject json) {
			//Checking if there is an array of ingredients
			if (!JsonHelper.hasArray(json, "ingredients"))
				throw new JsonSyntaxException("Required an array of ingredients");

			//Deserializing recipe json
			ShreddingRecipeFormat recipe = new Gson().fromJson(json, ShreddingRecipeFormat.class);

			//Ingredients
			DefaultedList<Ingredient> ingredients = DefaultedList.of();
			for (JsonElement je : recipe.ingredients) {
				ingredients.add(Ingredient.fromJson(je));
			}

			//Checking if there is at least one ingredient
			if (ingredients.size() == 0) throw new JsonSyntaxException("Required at least one ingredient");

			//Container
			Ingredient container = Ingredient.fromJson(recipe.container);

			//Output stack
			Optional<Item> outputItem = Registries.ITEM.getOrEmpty(new Identifier(recipe.result));
			ItemStack outputStack = new ItemStack(outputItem.orElseThrow(() -> new JsonSyntaxException("Invalid item '" + recipe.result + "'")));

			return new ShreddingRecipe(id, ingredients, container, outputStack);
		}

		/**
		 * Reads the recipe from a web packet.
		 */
		@Override
		public ShreddingRecipe read(Identifier id, PacketByteBuf buf) {
			int ingCount = buf.readInt();
			DefaultedList<Ingredient> ingredients = DefaultedList.of();
			for (int i = 0; i < ingCount; i++) {
				ingredients.add(Ingredient.fromPacket(buf));
			}
			Ingredient container = Ingredient.fromPacket(buf);
			ItemStack output = buf.readItemStack();
			return new ShreddingRecipe(id, ingredients, container, output);
		}

		/**
		 * Writes the recipe into a web packet.
		 */
		@Override
		public void write(PacketByteBuf buf, ShreddingRecipe recipe) {
			buf.writeInt(recipe.ingredients.size());
			for (Ingredient ing : recipe.ingredients) {
				ing.write(buf);
			}
			recipe.container.write(buf);
			buf.writeItemStack(recipe.output);
		}


		/**
		 * {@link ShreddingRecipe} json format.
		 */
		@SuppressWarnings("unused")
		private static class ShreddingRecipeFormat {
			public JsonArray ingredients;
			public JsonElement container;
			public String result;
		}
	}
}
