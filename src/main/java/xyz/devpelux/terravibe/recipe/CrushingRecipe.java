package xyz.devpelux.terravibe.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Crushes an item to obtain other items.
 */
public class CrushingRecipe extends InventoryRecipe {
	protected List<Triple<ItemStack, Integer, Integer>> outputs;

	/**
	 * Initializes a new {@link CrushingRecipe}.
	 */
	public CrushingRecipe(Identifier id, String group, Ingredient input, List<Triple<ItemStack, Integer, Integer>> outputs) {
		super(TerravibeRecipeTypes.CRUSHING, TerravibeRecipeSerializers.CRUSHING, id, group, DefaultedList.copyOf(null, input));
		this.outputs = outputs;
	}

	/**
	 * Gets the input ingredient.
	 */
	public Ingredient getInput() {
		return getIngredients().get(0);
	}

	/**
	 * Gets the output.
	 * Returns the first output element. (Main output)
	 */
	@Override
	public ItemStack getOutput() {
		//This is useful for search purposes and other things that requires a single main output.
		return getOutput(0);
	}

	/**
	 * Gets the output at the index specified.
	 */
	public ItemStack getOutput(int index) {
		return outputs.get(index).getLeft();
	}

	/**
	 * Gets the min count of the output at the index specified.
	 */
	public int getMinCount(int index) {
		return outputs.get(index).getMiddle();
	}

	/**
	 * Gets the max count of the output at the index specified.
	 */
	public int getMaxCount(int index) {
		return outputs.get(index).getRight();
	}

	/**
	 * Gets the number of output elements.
	 */
	public int getOutputCount() {
		return outputs.size();
	}

	/**
	 * Crafts the results.
	 * Generates a list of output stacks with a random item count between the min and max count specified.
	 * If the randomly selected count is zero, the stack is not included in the list.
	 */
	public List<ItemStack> multiCraft(Inventory inventory, Random random) {
		List<ItemStack> results = new ArrayList<>();
		int outputCount = getOutputCount();
		for (int i = 0; i < outputCount; i++) {
			int count = random.nextBetween(getMinCount(i), getMaxCount(i));
			if (count > 0) {
				ItemStack output = getOutput(i).copy();
				output.setCount(count);
				results.add(output);
			}
		}
		return results;
	}

	/**
	 * Checks if the specified inventory matches the ingredient list.
	 */
	@Override
	public boolean matches(Inventory inventory, World world) {
		return inventory.size() == 1 && getInput().test(inventory.getStack(0));
	}


	/**
	 * {@link CrushingRecipe} json serializer.
	 */
	public static class Serializer implements RecipeSerializer<CrushingRecipe> {
		/**
		 * Initializes a new {@link Serializer}.
		 */
		public Serializer() {
		}

		/**
		 * Reads the recipe from a json object.
		 */
		@Override
		public CrushingRecipe read(Identifier id, JsonObject json) {
			//Deserializing recipe json
			CrushingRecipeFormat recipe = new Gson().fromJson(json, CrushingRecipeFormat.class);

			//Group
			String group = Objects.requireNonNullElse(recipe.group, "");

			//Input
			Ingredient input = Ingredient.fromJson(recipe.ingredient);

			//Checking if there is a result
			if (recipe.results == null || recipe.results.length == 0) throw new JsonSyntaxException("No recipe result");

			//Output list.
			List<Triple<ItemStack, Integer, Integer>> outputs = new ArrayList<>();
			for (CrushingRecipeFormat.ResultFormat result : recipe.results) {
				//Output stack with min and max count.
				Optional<Item> outputItem = Registry.ITEM.getOrEmpty(new Identifier(result.item));
				ItemStack outputStack = new ItemStack(outputItem.orElseThrow(() -> new JsonSyntaxException("Invalid item '" + result.item + "'")));
				int maxCount = result.max_count;
				int minCount = Math.min(result.min_count, maxCount);
				outputs.add(Triple.of(outputStack, minCount, maxCount));
			}

			return new CrushingRecipe(id, group, input, outputs);
		}

		/**
		 * Reads the recipe from a web packet.
		 */
		@Override
		public CrushingRecipe read(Identifier id, PacketByteBuf buf) {
			String group = buf.readString();
			Ingredient input = Ingredient.fromPacket(buf);

			//Read the output list.
			List<Triple<ItemStack, Integer, Integer>> outputs = new ArrayList<>();
			int outputCount = buf.readInt();
			for (int i = 0; i < outputCount; i++) {
				ItemStack output = buf.readItemStack();
				int minCount = buf.readInt();
				int maxCount = buf.readInt();
				outputs.add(Triple.of(output, minCount, maxCount));
			}

			return new CrushingRecipe(id, group, input, outputs);
		}

		/**
		 * Writes the recipe into a web packet.
		 */
		@Override
		public void write(PacketByteBuf buf, CrushingRecipe recipe) {
			buf.writeString(recipe.getGroup());
			recipe.getInput().write(buf);

			//Saves the output list.
			int outputCount = recipe.getOutputCount();
			buf.writeInt(outputCount);
			for (int i = 0; i < outputCount; i++) {
				buf.writeItemStack(recipe.getOutput(i));
				buf.writeInt(recipe.getMinCount(i));
				buf.writeInt(recipe.getMaxCount(i));
			}
		}


		/**
		 * {@link CrushingRecipe} json format.
		 */
		private static class CrushingRecipeFormat {
			public String group;
			public JsonElement ingredient;
			public ResultFormat[] results;

			/**
			 * {@link CrushingRecipeFormat} result json format.
			 */
			public static class ResultFormat {
				public String item;
				public int min_count;
				public int max_count;
			}
		}
	}
}
