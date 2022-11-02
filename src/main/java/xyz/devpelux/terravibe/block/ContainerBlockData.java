package xyz.devpelux.terravibe.block;

import net.minecraft.block.BlockState;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import xyz.devpelux.terravibe.block.ContainerBlock.ContainerBehavior;
import xyz.devpelux.terravibe.block.ContainerBlock.ContentTexture;
import xyz.devpelux.terravibe.blockentity.ContainerBlockEntity;
import xyz.devpelux.terravibe.blockentity.ContainerBlockEntity.ContainerBlockColorProvider;
import xyz.devpelux.terravibe.item.TerravibeItems;

/**
 * List of all the behaviors, color providers, content names, and other things to make containers to work.
 */
public final class ContainerBlockData {
	//Content types

	public static final String CONTENT_EMPTY = "minecraft:empty";
	public static final String CONTENT_WATER = "minecraft:water";
	public static final String CONTENT_HONEY = "minecraft:honey";
	public static final String CONTENT_MILK = "minecraft:milk";
	public static final String CONTENT_TOMATO_SAUCE = "terravibe:tomato_sauce";
	public static final String CONTENT_BIRCH_MOLD_DUST = "terravibe:birch_mold_dust";
	public static final String CONTENT_DARK_MOLD_DUST = "terravibe:dark_mold_dust";
	public static final String CONTENT_GLOWING_DARK_MOLD_DUST = "terravibe:glowing_dark_mold_dust";
	public static final String CONTENT_BURNED_BIRCH_MOLD_DUST = "terravibe:burned_birch_mold_dust";
	public static final String CONTENT_BURNED_DARK_MOLD_DUST = "terravibe:burned_dark_mold_dust";
	public static final String CONTENT_BURNED_GLOWING_DARK_MOLD_DUST = "terravibe:burned_glowing_dark_mold_dust";

	//Behaviors

	public static final ContainerBehavior GET_WATER_OR_POTION;
	public static final ContainerBehavior PUT_WATER_OR_POTION;
	public static final ContainerBehavior GET_HONEY;
	public static final ContainerBehavior PUT_HONEY;
	public static final ContainerBehavior GET_MILK;
	public static final ContainerBehavior PUT_MILK;
	public static final ContainerBehavior GET_TOMATO_SAUCE;
	public static final ContainerBehavior PUT_TOMATO_SAUCE;
	public static final ContainerBehavior GET_BIRCH_MOLD_DUST_JAR;
	public static final ContainerBehavior PUT_BIRCH_MOLD_DUST_JAR;
	public static final ContainerBehavior GET_DARK_MOLD_DUST_JAR;
	public static final ContainerBehavior PUT_DARK_MOLD_DUST_JAR;
	public static final ContainerBehavior GET_GLOWING_DARK_MOLD_DUST_JAR;
	public static final ContainerBehavior PUT_GLOWING_DARK_MOLD_DUST_JAR;
	public static final ContainerBehavior GET_BURNED_BIRCH_MOLD_DUST_JAR;
	public static final ContainerBehavior PUT_BURNED_BIRCH_MOLD_DUST_JAR;
	public static final ContainerBehavior GET_BURNED_DARK_MOLD_DUST_JAR;
	public static final ContainerBehavior PUT_BURNED_DARK_MOLD_DUST_JAR;
	public static final ContainerBehavior GET_BURNED_GLOWING_DARK_MOLD_DUST_JAR;
	public static final ContainerBehavior PUT_BURNED_GLOWING_DARK_MOLD_DUST_JAR;

	//Color providers

	public static final ContainerBlockColorProvider COLORIZE_WATER_OR_POTION;
	public static final ContainerBlockColorProvider COLORIZE_HONEY;
	public static final ContainerBlockColorProvider COLORIZE_MILK;
	public static final ContainerBlockColorProvider COLORIZE_TOMATO_SAUCE;
	public static final ContainerBlockColorProvider COLORIZE_BIRCH_MOLD_DUST;
	public static final ContainerBlockColorProvider COLORIZE_DARK_MOLD_DUST;
	public static final ContainerBlockColorProvider COLORIZE_GLOWING_DARK_MOLD_DUST;
	public static final ContainerBlockColorProvider COLORIZE_BURNED_BIRCH_MOLD_DUST;
	public static final ContainerBlockColorProvider COLORIZE_BURNED_DARK_MOLD_DUST;
	public static final ContainerBlockColorProvider COLORIZE_BURNED_GLOWING_DARK_MOLD_DUST;

	private ContainerBlockData() {
	}

	/**
	 * Loads all the data.
	 */
	public static void load() {
		loadJarBehaviors();
		loadTunBehaviors();
		loadJarColorProviders();
		loadTunColorProviders();
	}

	/**
	 * Loads the {@link JarBlock} behaviors.
	 */
	private static void loadJarBehaviors() {
		JarBlock.registerBehavior(CONTENT_EMPTY, Items.POTION, PUT_WATER_OR_POTION);
		JarBlock.registerBehavior(CONTENT_EMPTY, Items.WATER_BUCKET, PUT_WATER_OR_POTION);
		JarBlock.registerBehavior(CONTENT_EMPTY, Items.MILK_BUCKET, PUT_MILK);
		JarBlock.registerBehavior(CONTENT_EMPTY, Items.HONEY_BOTTLE, PUT_HONEY);
		JarBlock.registerBehavior(CONTENT_EMPTY, TerravibeItems.TOMATO_SAUCE_BOTTLE, PUT_TOMATO_SAUCE);
		JarBlock.registerBehavior(CONTENT_EMPTY, TerravibeItems.BIRCH_MOLD_DUST, PUT_BIRCH_MOLD_DUST_JAR);
		JarBlock.registerBehavior(CONTENT_EMPTY, TerravibeItems.DARK_MOLD_DUST, PUT_DARK_MOLD_DUST_JAR);
		JarBlock.registerBehavior(CONTENT_EMPTY, TerravibeItems.GLOWING_DARK_MOLD_DUST, PUT_GLOWING_DARK_MOLD_DUST_JAR);
		JarBlock.registerBehavior(CONTENT_EMPTY, TerravibeItems.BURNED_BIRCH_MOLD_DUST, PUT_BURNED_BIRCH_MOLD_DUST_JAR);
		JarBlock.registerBehavior(CONTENT_EMPTY, TerravibeItems.BURNED_DARK_MOLD_DUST, PUT_BURNED_DARK_MOLD_DUST_JAR);
		JarBlock.registerBehavior(CONTENT_EMPTY, TerravibeItems.BURNED_GLOWING_DARK_MOLD_DUST, PUT_BURNED_GLOWING_DARK_MOLD_DUST_JAR);
		JarBlock.registerBehavior(CONTENT_WATER, Items.POTION, PUT_WATER_OR_POTION);
		JarBlock.registerBehavior(CONTENT_WATER, Items.WATER_BUCKET, PUT_WATER_OR_POTION);
		JarBlock.registerBehavior(CONTENT_WATER, Items.GLASS_BOTTLE, GET_WATER_OR_POTION);
		JarBlock.registerBehavior(CONTENT_WATER, Items.BUCKET, GET_WATER_OR_POTION);
		JarBlock.registerBehavior(CONTENT_MILK, Items.MILK_BUCKET, PUT_MILK);
		JarBlock.registerBehavior(CONTENT_MILK, Items.BUCKET, GET_MILK);
		JarBlock.registerBehavior(CONTENT_HONEY, Items.HONEY_BOTTLE, PUT_HONEY);
		JarBlock.registerBehavior(CONTENT_HONEY, Items.GLASS_BOTTLE, GET_HONEY);
		JarBlock.registerBehavior(CONTENT_TOMATO_SAUCE, TerravibeItems.TOMATO_SAUCE_BOTTLE, PUT_TOMATO_SAUCE);
		JarBlock.registerBehavior(CONTENT_TOMATO_SAUCE, Items.GLASS_BOTTLE, GET_TOMATO_SAUCE);
		JarBlock.registerBehavior(CONTENT_BIRCH_MOLD_DUST, TerravibeItems.BIRCH_MOLD_DUST, PUT_BIRCH_MOLD_DUST_JAR);
		JarBlock.registerBehavior(CONTENT_BIRCH_MOLD_DUST, Items.AIR, GET_BIRCH_MOLD_DUST_JAR);
		JarBlock.registerBehavior(CONTENT_DARK_MOLD_DUST, TerravibeItems.DARK_MOLD_DUST, PUT_DARK_MOLD_DUST_JAR);
		JarBlock.registerBehavior(CONTENT_DARK_MOLD_DUST, Items.AIR, GET_DARK_MOLD_DUST_JAR);
		JarBlock.registerBehavior(CONTENT_GLOWING_DARK_MOLD_DUST, TerravibeItems.GLOWING_DARK_MOLD_DUST, PUT_GLOWING_DARK_MOLD_DUST_JAR);
		JarBlock.registerBehavior(CONTENT_GLOWING_DARK_MOLD_DUST, Items.AIR, GET_GLOWING_DARK_MOLD_DUST_JAR);
		JarBlock.registerBehavior(CONTENT_BURNED_BIRCH_MOLD_DUST, TerravibeItems.BURNED_BIRCH_MOLD_DUST, PUT_BURNED_BIRCH_MOLD_DUST_JAR);
		JarBlock.registerBehavior(CONTENT_BURNED_BIRCH_MOLD_DUST, Items.AIR, GET_BURNED_BIRCH_MOLD_DUST_JAR);
		JarBlock.registerBehavior(CONTENT_BURNED_DARK_MOLD_DUST, TerravibeItems.BURNED_DARK_MOLD_DUST, PUT_BURNED_DARK_MOLD_DUST_JAR);
		JarBlock.registerBehavior(CONTENT_BURNED_DARK_MOLD_DUST, Items.AIR, GET_BURNED_DARK_MOLD_DUST_JAR);
		JarBlock.registerBehavior(CONTENT_BURNED_GLOWING_DARK_MOLD_DUST, TerravibeItems.BURNED_GLOWING_DARK_MOLD_DUST, PUT_BURNED_GLOWING_DARK_MOLD_DUST_JAR);
		JarBlock.registerBehavior(CONTENT_BURNED_GLOWING_DARK_MOLD_DUST, Items.AIR, GET_BURNED_GLOWING_DARK_MOLD_DUST_JAR);
	}

	/**
	 * Loads the {@link TunBlock} behaviors.
	 */
	private static void loadTunBehaviors() {
		TunBlock.registerBehavior(CONTENT_EMPTY, Items.POTION, PUT_WATER_OR_POTION);
		TunBlock.registerBehavior(CONTENT_EMPTY, Items.WATER_BUCKET, PUT_WATER_OR_POTION);
		TunBlock.registerBehavior(CONTENT_EMPTY, Items.MILK_BUCKET, PUT_MILK);
		TunBlock.registerBehavior(CONTENT_EMPTY, Items.HONEY_BOTTLE, PUT_HONEY);
		TunBlock.registerBehavior(CONTENT_EMPTY, TerravibeItems.TOMATO_SAUCE_BOTTLE, PUT_TOMATO_SAUCE);
		TunBlock.registerBehavior(CONTENT_WATER, Items.POTION, PUT_WATER_OR_POTION);
		TunBlock.registerBehavior(CONTENT_WATER, Items.WATER_BUCKET, PUT_WATER_OR_POTION);
		TunBlock.registerBehavior(CONTENT_WATER, Items.GLASS_BOTTLE, GET_WATER_OR_POTION);
		TunBlock.registerBehavior(CONTENT_WATER, Items.BUCKET, GET_WATER_OR_POTION);
		TunBlock.registerBehavior(CONTENT_MILK, Items.MILK_BUCKET, PUT_MILK);
		TunBlock.registerBehavior(CONTENT_MILK, Items.BUCKET, GET_MILK);
		TunBlock.registerBehavior(CONTENT_HONEY, Items.HONEY_BOTTLE, PUT_HONEY);
		TunBlock.registerBehavior(CONTENT_HONEY, Items.GLASS_BOTTLE, GET_HONEY);
		TunBlock.registerBehavior(CONTENT_TOMATO_SAUCE, TerravibeItems.TOMATO_SAUCE_BOTTLE, PUT_TOMATO_SAUCE);
		TunBlock.registerBehavior(CONTENT_TOMATO_SAUCE, Items.GLASS_BOTTLE, GET_TOMATO_SAUCE);
	}

	/**
	 * Loads the {@link JarBlock} color providers.
	 */
	private static void loadJarColorProviders() {
		JarBlock.registerColorProvider(CONTENT_WATER, COLORIZE_WATER_OR_POTION);
		JarBlock.registerColorProvider(CONTENT_HONEY, COLORIZE_HONEY);
		JarBlock.registerColorProvider(CONTENT_MILK, COLORIZE_MILK);
		JarBlock.registerColorProvider(CONTENT_TOMATO_SAUCE, COLORIZE_TOMATO_SAUCE);
		JarBlock.registerColorProvider(CONTENT_BIRCH_MOLD_DUST, COLORIZE_BIRCH_MOLD_DUST);
		JarBlock.registerColorProvider(CONTENT_DARK_MOLD_DUST, COLORIZE_DARK_MOLD_DUST);
		JarBlock.registerColorProvider(CONTENT_GLOWING_DARK_MOLD_DUST, COLORIZE_GLOWING_DARK_MOLD_DUST);
		JarBlock.registerColorProvider(CONTENT_BURNED_BIRCH_MOLD_DUST, COLORIZE_BURNED_BIRCH_MOLD_DUST);
		JarBlock.registerColorProvider(CONTENT_BURNED_DARK_MOLD_DUST, COLORIZE_BURNED_DARK_MOLD_DUST);
		JarBlock.registerColorProvider(CONTENT_BURNED_GLOWING_DARK_MOLD_DUST, COLORIZE_BURNED_GLOWING_DARK_MOLD_DUST);
	}

	/**
	 * Loads the {@link TunBlock} color providers.
	 */
	private static void loadTunColorProviders() {
		TunBlock.registerColorProvider(CONTENT_WATER, COLORIZE_WATER_OR_POTION);
		TunBlock.registerColorProvider(CONTENT_HONEY, COLORIZE_HONEY);
		TunBlock.registerColorProvider(CONTENT_MILK, COLORIZE_MILK);
		TunBlock.registerColorProvider(CONTENT_TOMATO_SAUCE, COLORIZE_TOMATO_SAUCE);
		TunBlock.registerColorProvider(CONTENT_BIRCH_MOLD_DUST, COLORIZE_BIRCH_MOLD_DUST);
		TunBlock.registerColorProvider(CONTENT_DARK_MOLD_DUST, COLORIZE_DARK_MOLD_DUST);
		TunBlock.registerColorProvider(CONTENT_GLOWING_DARK_MOLD_DUST, COLORIZE_GLOWING_DARK_MOLD_DUST);
		TunBlock.registerColorProvider(CONTENT_BURNED_BIRCH_MOLD_DUST, COLORIZE_BURNED_BIRCH_MOLD_DUST);
		TunBlock.registerColorProvider(CONTENT_BURNED_DARK_MOLD_DUST, COLORIZE_BURNED_DARK_MOLD_DUST);
		TunBlock.registerColorProvider(CONTENT_BURNED_GLOWING_DARK_MOLD_DUST, COLORIZE_BURNED_GLOWING_DARK_MOLD_DUST);
	}

	/**
	 * Gets something from a container and updates the container.
	 */
	private static ActionResult getFromContainer(BlockState state, World world, BlockPos pos, PlayerEntity player,
	                                             ItemStack stack, ContainerBlockEntity container, IntProperty level,
	                                             int currentLevel, int decrement, ItemStack drop, SoundEvent sound) {
		int newLevel = currentLevel - decrement;

		//If there is enough content to drain.
		if (newLevel >= 0) {
			if (!world.isClient()) {
				//Sets the new level.
				BlockState newState = state.with(level, newLevel);

				//If the new level is zero and is supported the content texture, resets the content texture.
				if (newLevel == 0 && ((ContainerBlock) state.getBlock()).useDefaultContentTexture()) {
					newState = newState.with(ContainerBlock.CONTENT_TEXTURE, ContentTexture.Fluid);
				}

				//Sets the new block state.
				world.setBlockState(pos, newState);

				//If the new level is zero, sets the content to empty.
				if (newLevel == 0) {
					ContainerBlock.setContent(container, CONTENT_EMPTY);
				}

				//If the player is not in creative mode, consumes the hand stack, then returns the drop.
				if (!player.getAbilities().creativeMode) {
					stack.decrement(1);
					player.getInventory().offerOrDrop(drop);
				}

				//Plays the sound.
				player.playSound(sound, SoundCategory.BLOCKS, 1f, 1f);
			}

			//Client: SUCCESS / Server: CONSUME
			return ActionResult.success(world.isClient());
		}

		return ActionResult.PASS;
	}

	/**
	 * Puts something into a container and updates the container.
	 */
	private static ActionResult putInContainer(BlockState state, World world, BlockPos pos, PlayerEntity player,
	                                           ItemStack stack, ContainerBlockEntity container, IntProperty level,
	                                           int currentLevel, int maxLevel, int increment, String content,
	                                           ContentTexture contentTexture, ItemStack drop, SoundEvent sound) {
		int newLevel = currentLevel + increment;

		//If there is enough space.
		if (newLevel <= maxLevel) {
			if (!world.isClient()) {
				//Sets the new level.
				BlockState newState = state.with(level, newLevel);

				//If is supported the content texture, sets the content texture.
				if (((ContainerBlock) state.getBlock()).useDefaultContentTexture()) {
					newState = newState.with(ContainerBlock.CONTENT_TEXTURE, contentTexture);
				}

				//Sets the new block state.
				world.setBlockState(pos, newState);

				//If the container was empty sets the content.
				if (currentLevel == 0) {
					ContainerBlock.setContent(container, content);
				}

				//If the player is not in creative mode, consumes the hand stack, then returns the drop.
				if (!player.getAbilities().creativeMode) {
					stack.decrement(1);
					player.getInventory().offerOrDrop(drop);
				}

				//Plays the sound.
				player.playSound(sound, SoundCategory.BLOCKS, 1f, 1f);
			}

			//Client: SUCCESS / Server: CONSUME
			return ActionResult.success(world.isClient());
		}

		return ActionResult.PASS;
	}

	/**
	 * Puts something into a container and updates the container.
	 */
	private static ActionResult putInContainer(BlockState state, World world, BlockPos pos, PlayerEntity player,
	                                           ItemStack stack, ContainerBlockEntity container, IntProperty level,
	                                           int currentLevel, int maxLevel, int increment, String content,
	                                           ItemStack drop, SoundEvent sound) {
		return putInContainer(state, world, pos, player, stack, container, level, currentLevel,
				maxLevel, increment, content, ContentTexture.Fluid, drop, sound);
	}

	static {
		//Behaviors
		GET_WATER_OR_POTION = (state, world, pos, player, stack, container, level, currentLevel, maxLevel) -> {
			ActionResult result = ActionResult.PASS;
			int expectedLevel = currentLevel;
			Potion potion = PotionUtil.getPotion(container.getValue("PotionData"));

			if (stack.isOf(Items.BUCKET) && potion == Potions.WATER) {
				//Get a bucket of water.
				expectedLevel -= 3;
				result = getFromContainer(state, world, pos, player, stack, container, level, currentLevel,
						3, Items.WATER_BUCKET.getDefaultStack(), SoundEvents.ITEM_BUCKET_FILL);
			} else if (stack.isOf(Items.GLASS_BOTTLE)) {
				//Get a bottle of water or potion.
				expectedLevel -= 1;
				ItemStack drop = PotionUtil.setPotion(Items.POTION.getDefaultStack(), potion);
				result = getFromContainer(state, world, pos, player, stack, container, level, currentLevel,
						1, drop, SoundEvents.ITEM_BOTTLE_FILL);
			}

			//Removes the potion data on server side if empty.
			if (expectedLevel == 0 && result == ActionResult.CONSUME) {
				container.setValue("PotionData", null);
			}

			return result;
		};

		PUT_WATER_OR_POTION = (state, world, pos, player, stack, container, level, currentLevel, maxLevel) -> {
			ActionResult result = ActionResult.PASS;
			Potion containedPotion = PotionUtil.getPotion(container.getValue("PotionData"));
			//The potion is always set, even if water, to get correctly bottles of water.
			Potion inputPotion = stack.isOf(Items.WATER_BUCKET) ? Potions.WATER : PotionUtil.getPotion(stack);

			if (currentLevel == 0 || containedPotion == inputPotion) {
				if (stack.isOf(Items.WATER_BUCKET)) {
					//Put a buket of water.
					result = putInContainer(state, world, pos, player, stack, container, level, currentLevel, maxLevel,
							3, CONTENT_WATER, ContentTexture.Fluid, Items.BUCKET.getDefaultStack(), SoundEvents.ITEM_BUCKET_EMPTY);
				} else {
					//Put a bottle of water or potion.
					result = putInContainer(state, world, pos, player, stack, container, level, currentLevel, maxLevel,
							1, CONTENT_WATER, ContentTexture.Fluid, Items.GLASS_BOTTLE.getDefaultStack(), SoundEvents.ITEM_BOTTLE_EMPTY);
				}

				//Sets the potion data on server side if empty.
				if (currentLevel == 0 && result == ActionResult.CONSUME) {
					NbtCompound nbt = new NbtCompound();
					nbt.putString("Potion", Registry.POTION.getId(inputPotion).toString());
					container.setValue("PotionData", nbt);
				}
			}

			return result;
		};

		GET_HONEY = (state, world, pos, player, stack, container, level, currentLevel, maxLevel) -> {
			return getFromContainer(state, world, pos, player, stack, container, level, currentLevel,
					1, Items.HONEY_BOTTLE.getDefaultStack(), SoundEvents.ITEM_BOTTLE_FILL);
		};

		PUT_HONEY = (state, world, pos, player, stack, container, level, currentLevel, maxLevel) -> {
			return putInContainer(state, world, pos, player, stack, container, level, currentLevel, maxLevel,
					1, CONTENT_HONEY, ContentTexture.DenseFluid, new ItemStack(stack.getItem().getRecipeRemainder()), SoundEvents.ITEM_BOTTLE_EMPTY);
		};

		GET_MILK = (state, world, pos, player, stack, container, level, currentLevel, maxLevel) -> {
			return getFromContainer(state, world, pos, player, stack, container, level, currentLevel,
					3, Items.MILK_BUCKET.getDefaultStack(), SoundEvents.ITEM_BUCKET_FILL);
		};

		PUT_MILK = (state, world, pos, player, stack, container, level, currentLevel, maxLevel) -> {
			return putInContainer(state, world, pos, player, stack, container, level, currentLevel, maxLevel,
					3, CONTENT_MILK, ContentTexture.Fluid, new ItemStack(stack.getItem().getRecipeRemainder()), SoundEvents.ITEM_BUCKET_EMPTY);
		};

		GET_TOMATO_SAUCE = (state, world, pos, player, stack, container, level, currentLevel, maxLevel) -> {
			return getFromContainer(state, world, pos, player, stack, container, level, currentLevel,
					1, TerravibeItems.TOMATO_SAUCE_BOTTLE.getDefaultStack(), SoundEvents.ITEM_BOTTLE_FILL);
		};

		PUT_TOMATO_SAUCE = (state, world, pos, player, stack, container, level, currentLevel, maxLevel) -> {
			return putInContainer(state, world, pos, player, stack, container, level, currentLevel, maxLevel,
					1, CONTENT_TOMATO_SAUCE, ContentTexture.DenseFluid, new ItemStack(stack.getItem().getRecipeRemainder()), SoundEvents.ITEM_BOTTLE_EMPTY);
		};

		GET_BIRCH_MOLD_DUST_JAR = (state, world, pos, player, stack, container, level, currentLevel, maxLevel) -> {
			if (currentLevel == 1) {
				state = ContainerBlock.setContainer(TerravibeBlocks.JAR.getDefaultState(), world, pos);
			}
			return getFromContainer(state, world, pos, player, stack, container, level, currentLevel,
					1, TerravibeItems.BIRCH_MOLD_DUST.getDefaultStack(), SoundEvents.BLOCK_MOSS_BREAK);
		};

		PUT_BIRCH_MOLD_DUST_JAR = (state, world, pos, player, stack, container, level, currentLevel, maxLevel) -> {
			if (currentLevel == 0) {
				state = ContainerBlock.setContainer(TerravibeBlocks.MOLD_DUST_JAR.getDefaultState(), world, pos);
			}
			return putInContainer(state, world, pos, player, stack, container, level, currentLevel, maxLevel,
					1, CONTENT_BIRCH_MOLD_DUST, ItemStack.EMPTY, SoundEvents.BLOCK_MOSS_PLACE);
		};

		GET_DARK_MOLD_DUST_JAR = (state, world, pos, player, stack, container, level, currentLevel, maxLevel) -> {
			if (currentLevel == 1) {
				state = ContainerBlock.setContainer(TerravibeBlocks.JAR.getDefaultState(), world, pos);
			}
			return getFromContainer(state, world, pos, player, stack, container, level, currentLevel,
					1, TerravibeItems.DARK_MOLD_DUST.getDefaultStack(), SoundEvents.BLOCK_MOSS_BREAK);
		};

		PUT_DARK_MOLD_DUST_JAR = (state, world, pos, player, stack, container, level, currentLevel, maxLevel) -> {
			if (currentLevel == 0) {
				state = ContainerBlock.setContainer(TerravibeBlocks.MOLD_DUST_JAR.getDefaultState(), world, pos);
			}
			return putInContainer(state, world, pos, player, stack, container, level, currentLevel, maxLevel,
					1, CONTENT_DARK_MOLD_DUST, ItemStack.EMPTY, SoundEvents.BLOCK_MOSS_PLACE);
		};

		GET_GLOWING_DARK_MOLD_DUST_JAR = (state, world, pos, player, stack, container, level, currentLevel, maxLevel) -> {
			if (currentLevel == 1) {
				state = ContainerBlock.setContainer(TerravibeBlocks.JAR.getDefaultState(), world, pos);
			}
			return getFromContainer(state, world, pos, player, stack, container, level, currentLevel,
					1, TerravibeItems.GLOWING_DARK_MOLD_DUST.getDefaultStack(), SoundEvents.BLOCK_MOSS_BREAK);
		};

		PUT_GLOWING_DARK_MOLD_DUST_JAR = (state, world, pos, player, stack, container, level, currentLevel, maxLevel) -> {
			if (currentLevel == 0) {
				state = ContainerBlock.setContainer(TerravibeBlocks.GLOWING_DARK_MOLD_DUST_JAR.getDefaultState(), world, pos);
			}
			return putInContainer(state, world, pos, player, stack, container, level, currentLevel, maxLevel,
					1, CONTENT_GLOWING_DARK_MOLD_DUST, ItemStack.EMPTY, SoundEvents.BLOCK_MOSS_PLACE);
		};

		GET_BURNED_BIRCH_MOLD_DUST_JAR = (state, world, pos, player, stack, container, level, currentLevel, maxLevel) -> {
			if (currentLevel == 1) {
				state = ContainerBlock.setContainer(TerravibeBlocks.JAR.getDefaultState(), world, pos);
			}
			return getFromContainer(state, world, pos, player, stack, container, level, currentLevel,
					1, TerravibeItems.BURNED_BIRCH_MOLD_DUST.getDefaultStack(), SoundEvents.BLOCK_MOSS_BREAK);
		};

		PUT_BURNED_BIRCH_MOLD_DUST_JAR = (state, world, pos, player, stack, container, level, currentLevel, maxLevel) -> {
			if (currentLevel == 0) {
				state = ContainerBlock.setContainer(TerravibeBlocks.DUST_JAR.getDefaultState(), world, pos);
			}
			return putInContainer(state, world, pos, player, stack, container, level, currentLevel, maxLevel,
					1, CONTENT_BURNED_BIRCH_MOLD_DUST, ItemStack.EMPTY, SoundEvents.BLOCK_MOSS_PLACE);
		};

		GET_BURNED_DARK_MOLD_DUST_JAR = (state, world, pos, player, stack, container, level, currentLevel, maxLevel) -> {
			if (currentLevel == 1) {
				state = ContainerBlock.setContainer(TerravibeBlocks.JAR.getDefaultState(), world, pos);
			}
			return getFromContainer(state, world, pos, player, stack, container, level, currentLevel,
					1, TerravibeItems.BURNED_DARK_MOLD_DUST.getDefaultStack(), SoundEvents.BLOCK_MOSS_BREAK);
		};

		PUT_BURNED_DARK_MOLD_DUST_JAR = (state, world, pos, player, stack, container, level, currentLevel, maxLevel) -> {
			if (currentLevel == 0) {
				state = ContainerBlock.setContainer(TerravibeBlocks.DUST_JAR.getDefaultState(), world, pos);
			}
			return putInContainer(state, world, pos, player, stack, container, level, currentLevel, maxLevel,
					1, CONTENT_BURNED_DARK_MOLD_DUST, ItemStack.EMPTY, SoundEvents.BLOCK_MOSS_PLACE);
		};

		GET_BURNED_GLOWING_DARK_MOLD_DUST_JAR = (state, world, pos, player, stack, container, level, currentLevel, maxLevel) -> {
			if (currentLevel == 1) {
				state = ContainerBlock.setContainer(TerravibeBlocks.JAR.getDefaultState(), world, pos);
			}
			return getFromContainer(state, world, pos, player, stack, container, level, currentLevel,
					1, TerravibeItems.BURNED_GLOWING_DARK_MOLD_DUST.getDefaultStack(), SoundEvents.BLOCK_MOSS_BREAK);
		};

		PUT_BURNED_GLOWING_DARK_MOLD_DUST_JAR = (state, world, pos, player, stack, container, level, currentLevel, maxLevel) -> {
			if (currentLevel == 0) {
				state = ContainerBlock.setContainer(TerravibeBlocks.BURNED_GLOWING_DARK_MOLD_DUST_JAR.getDefaultState(), world, pos);
			}
			return putInContainer(state, world, pos, player, stack, container, level, currentLevel, maxLevel,
					1, CONTENT_BURNED_GLOWING_DARK_MOLD_DUST, ItemStack.EMPTY, SoundEvents.BLOCK_MOSS_PLACE);
		};

		//Color providers
		COLORIZE_WATER_OR_POTION = (container, state, world, pos, tintIndex) -> {
			Potion potion = PotionUtil.getPotion(container.getValue("PotionData"));
			return potion == Potions.WATER && world != null ? BiomeColors.getWaterColor(world, pos) : PotionUtil.getColor(potion);
		};
		COLORIZE_HONEY = (container, state, world, pos, tintIndex) -> 0x976018;
		COLORIZE_MILK = (container, state, world, pos, tintIndex) -> 0xffffff;
		COLORIZE_TOMATO_SAUCE = (container, state, world, pos, tintIndex) -> 0xf61815;
		COLORIZE_BIRCH_MOLD_DUST = (container, state, world, pos, tintIndex) -> {
			return switch (tintIndex) {
				case 2 -> 0xb3af9c;
				case 3 -> 0xa39c82;
				case 4 -> 0x7b7665;
				default -> 0xd1d1c7;
			};
		};
		COLORIZE_DARK_MOLD_DUST = (container, state, world, pos, tintIndex) -> {
			return switch (tintIndex) {
				case 2 -> 0x273832;
				case 3 -> 0x212f29;
				case 4 -> 0x1d2a24;
				default -> 0x2c3f3a;
			};
		};
		COLORIZE_GLOWING_DARK_MOLD_DUST = (container, state, world, pos, tintIndex) -> {
			return switch (tintIndex) {
				case 2 -> 0x1f2d2c;
				case 3 -> 0x1b2827;
				case 4 -> 0x00b7bb;
				default -> 0x2a3c3d;
			};
		};
		COLORIZE_BURNED_BIRCH_MOLD_DUST = (container, state, world, pos, tintIndex) -> {
			return switch (tintIndex) {
				case 2 -> 0xbea894;
				case 3 -> 0xaf947a;
				case 4 -> 0x866c5b;
				default -> 0xdccdc2;
			};
		};
		COLORIZE_BURNED_DARK_MOLD_DUST = (container, state, world, pos, tintIndex) -> {
			return switch (tintIndex) {
				case 2 -> 0x30322c;
				case 3 -> 0x292923;
				case 4 -> 0x24241f;
				default -> 0x363934;
			};
		};
		COLORIZE_BURNED_GLOWING_DARK_MOLD_DUST = (container, state, world, pos, tintIndex) -> {
			return switch (tintIndex) {
				case 2 -> 0x282524;
				case 3 -> 0x23201f;
				case 4 -> 0x00b1b5;
				default -> 0x353334;
			};
		};
	}
}
