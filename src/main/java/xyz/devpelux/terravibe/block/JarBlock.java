package xyz.devpelux.terravibe.block;

import it.unimi.dsi.fastutil.Pair;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.blockentity.ContainerBlockEntity;
import xyz.devpelux.terravibe.core.Terravibe;
import xyz.devpelux.terravibe.item.ColoredItem;
import xyz.devpelux.terravibe.item.TerravibeItems;
import xyz.devpelux.terravibe.tags.TerravibeItemTags;

import java.util.HashMap;
import java.util.Map;

/**
 * Container for things.
 */
public class JarBlock extends ContainerBlock implements BlockColorProvider {
	/**
	 * Settings of the block.
	 */
	public static final Settings SETTINGS;

	/**
	 * Level of the content when full.
	 */
	public static final int MAX_LEVEL = 3;

	/**
	 * Level of the content.
	 */
	public static final IntProperty LEVEL;

	/**
	 * Specifies if the jar is closed.
	 */
	public static final BooleanProperty CLOSED;

	/**
	 * Identifier of the block.
	 */
	private static final Identifier ID;

	/**
	 * Outline shape of the block.
	 */
	private static final VoxelShape OUTLINE_SHAPE;

	/**
	 * Default content color.
	 */
	private static final int DEFAULT_CONTENT_COLOR = 0x241a09;

	/**
	 * Default plug color.
	 */
	private static final int DEFAULT_PLUG_COLOR = 0xb8945f;

	/**
	 * List of all the color providers to color the content.
	 */
	private static final Map<String, ContainerBlockEntity.ContainerBlockColorProvider> COLOR_PROVIDERS = new HashMap<>();

	/**
	 * List of all the possible behaviors of the container.
	 */
	private static final Map<Pair<String, Item>, ContainerBehavior> BEHAVIORS = new HashMap<>();

	/**
	 * Initializes a new {@link JarBlock}.
	 */
	public JarBlock(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState().with(CLOSED, false));
	}

	/**
	 * Initializes a new {@link JarBlock} with default settings.
	 */
	public static JarBlock of() {
		return new JarBlock(SETTINGS);
	}

	/**
	 * Registers a behavior for the content and input specified.
	 */
	public static void registerBehavior(String content, Item used, ContainerBehavior interaction) {
		BEHAVIORS.putIfAbsent(Pair.of(content, used), interaction);
	}

	/**
	 * Registers a color provider for the content specified.
	 */
	public static void registerColorProvider(String content, ContainerBlockEntity.ContainerBlockColorProvider colorProvider) {
		COLOR_PROVIDERS.putIfAbsent(content, colorProvider);
	}

	/**
	 * Gets the plug.
	 */
	@Nullable
	public static Item getPlug(ContainerBlockEntity container) {
		NbtCompound value = container.getValue("Plug");
		String plug = value.getString("Id");

		//If the plug exists then returns the item, else returns null.
		if (!plug.equals("")) {
			Item item = Registry.ITEM.get(new Identifier(plug));
			if (item != Items.AIR) {
				return item;
			}
		}

		return null;
	}

	/**
	 * Sets the plug. (null to remove)
	 */
	public static void setPlug(ContainerBlockEntity container, @Nullable Item plug) {
		if (plug != null) {
			//Sets the plug.
			NbtCompound value = new NbtCompound();
			value.putString("Id", Registry.ITEM.getId(plug).toString());
			container.setValue("Plug", value);
		} else {
			//Removes the plug.
			container.setValue("Plug", null);
		}
	}

	/**
	 * Registers the properties of the block.
	 */
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(CLOSED);
	}

	/**
	 * Gets the default state adjusted from nbt container data.
	 */
	@Override
	public BlockState withContainerData(NbtCompound nbt) {
		boolean closed = nbt.getBoolean("Closed");
		return super.withContainerData(nbt).with(CLOSED, closed);
	}

	/**
	 * Gets the level property.
	 */
	@Override
	public IntProperty getLevelProperty() {
		return LEVEL;
	}

	/**
	 * Gets the max level.
	 */
	@Override
	public int getMaxLevel() {
		return MAX_LEVEL;
	}

	/**
	 * Gets the item of the filled closed jar.
	 */
	public ItemConvertible getClosedJarFilledItem() {
		return TerravibeItems.CLOSED_JAR_FILLED;
	}

	/**
	 * Gets the identifier of the block.
	 */
	@Override
	protected Identifier getId() {
		return ID;
	}

	/**
	 * Gets a value indicating if the specified block state is a closed jar.
	 */
	protected boolean isClosed(BlockState state) {
		return state.get(CLOSED);
	}

	/**
	 * Gets the behavior for the content and input specified.
	 */
	@Nullable
	@Override
	protected ContainerBehavior getBehavior(String content, Item input) {
		return BEHAVIORS.get(Pair.of(content, input));
	}

	/**
	 * Gets the color provider for the content specified.
	 */
	@Nullable
	protected ContainerBlockEntity.ContainerBlockColorProvider getColorProvider(String content) {
		return COLOR_PROVIDERS.get(content);
	}

	/**
	 * Save extra container data to nbt.
	 */
	@Override
	protected void saveToNbt(BlockState state, BlockView world, BlockPos pos, NbtCompound nbt) {
		super.saveToNbt(state, world, pos, nbt);
		nbt.putBoolean("Closed", isClosed(state));
	}

	/**
	 * Executed when the block entity is used.
	 * Inserts or extracts the content, closes or opens the jar.
	 */
	@Override
	public ActionResult onUseBlockEntity(BlockState state, World world, BlockPos pos, PlayerEntity player, ContainerBlockEntity container) {
		ItemStack inHand = player.getStackInHand(Hand.MAIN_HAND);
		if (isClosed(state)) {
			if (inHand.isEmpty()) {
				if (!world.isClient()) {
					//Opens the jar.
					world.setBlockState(pos, state.with(CLOSED, false));
					Item plug = getPlug(container);
					setPlug(container, null);

					//Drops the button (only if the player is not in creative).
					if (!player.getAbilities().creativeMode && plug != null) {
						player.getInventory().offerOrDrop(new ItemStack(plug));
					}
				}

				//Client: SUCCESS / Server: CONSUME
				return ActionResult.success(world.isClient());
			}

			return ActionResult.PASS;
		} else {
			if (inHand.isIn(TerravibeItemTags.JAR_PLUGS)) {
				if (!world.isClient()) {
					//Closes the jar.
					world.setBlockState(pos, state.with(CLOSED, true));
					setPlug(container, inHand.getItem());

					//Consumes the hand stack (only if the player is not in creative).
					if (!player.getAbilities().creativeMode) {
						inHand.decrement(1);
					}
				}

				return ActionResult.success(world.isClient());
			}

			return super.onUseBlockEntity(state, world, pos, player, container);
		}
	}

	/**
	 * Executed at the block breaking.
	 * Drops a jar with the content if it is closed.
	 */
	@Override
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		super.onBreak(world, pos, state, player);
		if (!isClosed(state)) {
			onBreakOpened(world, pos, state, player);
		} else {
			//Drops a jar with the content data in server world, if the player is not in creative mode.
			if (!world.isClient() && !player.getAbilities().creativeMode) {
				NbtCompound nbt = getContainerData(state, world, pos);
				//Check if the content data is present, just in case.
				if (nbt.contains("ContentData", NbtElement.COMPOUND_TYPE)) {
					ItemConvertible jar = getLevel(state) > 0 ? getClosedJarFilledItem() : TerravibeItems.CLOSED_JAR_EMPTY;
					ItemStack stack = new ItemStack(jar, 1);
					stack.setNbt(nbt);
					dropStack(world, pos, stack);
				}
			}
		}
	}

	/**
	 * Executed at the block breaking when opened.
	 */
	public void onBreakOpened(World world, BlockPos pos, BlockState state, PlayerEntity player) {
	}

	/**
	 * Gets the outline shape of the block.
	 */
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return OUTLINE_SHAPE;
	}

	/**
	 * Gets the colors of the block.
	 */
	@Override
	public int getColor(BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos, int tintIndex) {
		if (tintIndex == 1) {
			//Gets the container.
			ContainerBlockEntity container = getContainerEntity(world, pos);
			if (container != null) {
				//Gets the color provider for the container content.
				ContainerBlockEntity.ContainerBlockColorProvider colorProvider = getColorProvider(getContent(container));

				if (colorProvider != null) {
					//Gets the color.
					return colorProvider.getColor(container, state, world, pos, tintIndex);
				}
			}

			return DEFAULT_CONTENT_COLOR;
		} else if (tintIndex == 2) {
			//Gets the plug.
			ContainerBlockEntity container = getContainerEntity(world, pos);
			Item plug = container != null ? getPlug(container) : null;

			//Gets the plug color.
			return plug instanceof ColoredItem coloredPlug ? coloredPlug.getColor(null, 1) : DEFAULT_PLUG_COLOR;
		}

		return -1;
	}

	static {
		SETTINGS = FabricBlockSettings.copyOf(Blocks.FLOWER_POT);
		LEVEL = IntProperty.of("level", 0, MAX_LEVEL);
		CLOSED = BooleanProperty.of("closed");
		ID = Terravibe.identified("jar");
		OUTLINE_SHAPE = Block.createCuboidShape(5, 0, 5, 11, 9, 11);
	}
}
