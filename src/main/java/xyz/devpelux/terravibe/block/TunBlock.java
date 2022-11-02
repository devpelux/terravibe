package xyz.devpelux.terravibe.block;

import it.unimi.dsi.fastutil.Pair;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.item.Item;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.blockentity.ContainerBlockEntity;
import xyz.devpelux.terravibe.blockentity.ContainerBlockEntity.ContainerBlockColorProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Container for "non-lava" fluids.
 */
public class TunBlock extends ContainerBlock implements BlockColorProvider {
	/**
	 * Settings of the block.
	 */
	public static final Settings SETTINGS;

	/**
	 * Level of the content when full.
	 */
	public static final int MAX_LEVEL = 27;

	/**
	 * Level of the content.
	 */
	public static final IntProperty LEVEL;

	/**
	 * Outline shape of the block.
	 */
	private static final VoxelShape OUTLINE_SHAPE;

	/**
	 * Default content color.
	 */
	private static final int DEFAULT_CONTENT_COLOR = 0x241a09;

	/**
	 * List of all the color providers to color the content.
	 */
	private static final Map<String, ContainerBlockEntity.ContainerBlockColorProvider> COLOR_PROVIDERS = new HashMap<>();

	/**
	 * List of all the possible behaviors of the container.
	 */
	private static final Map<Pair<String, Item>, ContainerBehavior> BEHAVIORS = new HashMap<>();

	/**
	 * Initializes a new {@link TunBlock}.
	 */
	public TunBlock(Settings settings) {
		super(settings);
	}

	/**
	 * Initializes a new {@link TunBlock} with default settings.
	 */
	public static TunBlock of() {
		return new TunBlock(SETTINGS);
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
		ContainerBlockEntity container = getContainerEntity(world, pos);
		if (container != null) {
			//Gets the color provider for the content, then gets the color from the provider if exists.
			String content = getContent(container);
			if (content.equals(CONTENT_EMPTY)) return -1;
			ContainerBlockColorProvider colorProvider = getColorProvider(content);
			if (colorProvider != null) {
				return colorProvider.getColor(container, state, world, pos, tintIndex);
			}
		}

		return DEFAULT_CONTENT_COLOR;
	}

	static {
		SETTINGS = FabricBlockSettings.copyOf(Blocks.BARREL);
		LEVEL = IntProperty.of("level", 0, MAX_LEVEL);
		OUTLINE_SHAPE = Stream.of(
				Block.createCuboidShape(2, 0, 2, 14, 1, 14),
				Block.createCuboidShape(0, 0, 2, 2, 16, 16),
				Block.createCuboidShape(14, 0, 0, 16, 16, 14),
				Block.createCuboidShape(2, 0, 14, 16, 16, 16),
				Block.createCuboidShape(0, 0, 0, 14, 16, 2)
		).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
	}
}
