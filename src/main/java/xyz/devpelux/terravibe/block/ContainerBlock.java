package xyz.devpelux.terravibe.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.blockentity.ContainerBlockEntity;

import java.util.Objects;

/**
 * Container.
 */
public abstract class ContainerBlock extends BlockWithEntity {
	/**
	 * Empty content.
	 */
	public static final String CONTENT_EMPTY = "minecraft:empty";

	/**
	 * Initializes a new {@link ContainerBlock}.
	 */
	public ContainerBlock(Settings settings) {
		super(settings);
	}

	/**
	 * Gets the content.
	 */
	public static String getContent(ContainerBlockEntity container) {
		NbtCompound value = container.getValue("Content");
		return value.getString("Id");
	}

	/**
	 * Sets the content.
	 */
	public static void setContent(ContainerBlockEntity container, String content) {
		NbtCompound value = new NbtCompound();
		value.putString("Id", Objects.requireNonNullElse(content, CONTENT_EMPTY));
		container.setValue("Content", value);
	}

	/**
	 * Gets the container block entity.
	 */
	@Nullable
	public static ContainerBlockEntity getContainerEntity(BlockView world, BlockPos pos) {
		if (world == null || pos == null) return null;
		return world.getBlockEntity(pos) instanceof ContainerBlockEntity container ? container : null;
	}

	/**
	 * Registers the properties of the block.
	 */
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(getLevelProperty());
	}

	/**
	 * Gets the default state adjusted from nbt container data.
	 */
	public BlockState withContainerData(NbtCompound nbt) {
		int level = nbt.getInt("Level");
		return getDefaultState().with(getLevelProperty(), level);
	}

	/**
	 * Gets the level property.
	 */
	public abstract IntProperty getLevelProperty();

	/**
	 * Gets the max level.
	 */
	public abstract int getMaxLevel();

	/**
	 * Gets the identifier of the block.
	 */
	protected abstract Identifier getId();

	/**
	 * Gets the level of the container from its block state.
	 */
	protected int getLevel(BlockState state) {
		return state.get(getLevelProperty());
	}

	/**
	 * Gets the behavior for the content and input specified.
	 */
	@Nullable
	protected abstract ContainerBehavior getBehavior(String content, Item input);

	/**
	 * Gets the container data as nbt.
	 */
	protected NbtCompound getContainerData(BlockState state, BlockView world, BlockPos pos) {
		NbtCompound nbt = new NbtCompound();
		ContainerBlockEntity container = getContainerEntity(world, pos);
		if (container != null) {
			//Adds a copy of the content.
			NbtCompound content = container.getContent().copy();
			nbt.put("ContentData", content);
		}
		//Adds other container data.
		saveToNbt(state, world, pos, nbt);
		return nbt;
	}

	/**
	 * Save container data to nbt.
	 */
	protected void saveToNbt(BlockState state, BlockView world, BlockPos pos, NbtCompound nbt) {
		nbt.putString("Block", getId().toString());
		nbt.putInt("Level", getLevel(state));
	}

	/**
	 * Executed when the block is used.
	 * Interacts with the block entity.
	 */
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ContainerBlockEntity container = getContainerEntity(world, pos);
		if (container != null) {
			return onUseBlockEntity(state, world, pos, player, container);
		}
		return ActionResult.PASS;
	}

	/**
	 * Executed when the block entity is used.
	 * Inserts or extracts the content.
	 */
	public ActionResult onUseBlockEntity(BlockState state, World world, BlockPos pos, PlayerEntity player, ContainerBlockEntity container) {
		//Gets the stack in the main hand and the content nbt.
		ItemStack inHand = player.getStackInHand(Hand.MAIN_HAND);

		//Gets the interaction for the current content, and the current item in hand.
		ContainerBehavior behavior = getBehavior(getContent(container), inHand.getItem());

		if (behavior != null) {
			//Gets the level property, the current level, and the max level.
			IntProperty level = getLevelProperty();
			int currentLevel = getLevel(state);
			int maxLevel = getMaxLevel();

			//Gets the result of the interaction.
			return behavior.interact(state, world, pos, player, inHand, container, level, currentLevel, maxLevel);
		}

		return ActionResult.PASS;
	}

	/**
	 * Creates the block entity for the block.
	 */
	@Override
	public final BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		ContainerBlockEntity container = new ContainerBlockEntity(pos, state);
		setContent(container, CONTENT_EMPTY);
		return container;
	}

	/**
	 * Gets the render type of the block.
	 */
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	/**
	 * Gets a value indicating if the block gives an output to comparators.
	 */
	@Override
	public boolean hasComparatorOutput(BlockState state) {
		return true;
	}

	/**
	 * Gets the comparator output value.
	 */
	@Override
	public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return (int) ((float) getLevel(state) / (float) getMaxLevel() * 15f);
	}


	/**
	 * Defines an interaction of the player with a container.
	 */
	@FunctionalInterface
	public interface ContainerBehavior {
		/**
		 * Executed when the player interacts with the container with an item.
		 */
		ActionResult interact(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack,
		                      ContainerBlockEntity container, IntProperty level, int currentLevel, int maxLevel);
	}
}