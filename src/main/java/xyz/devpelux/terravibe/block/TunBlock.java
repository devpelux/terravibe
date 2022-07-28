package xyz.devpelux.terravibe.block;

import it.unimi.dsi.fastutil.Pair;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.block.container.ContainableColorProvider;
import xyz.devpelux.terravibe.block.container.ContainerBlock;
import xyz.devpelux.terravibe.block.container.ContainerInteraction;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.core.Util;

import java.util.HashMap;
import java.util.Optional;

/** Container for "non-lava" fluids. */
public class TunBlock extends ContainerBlock {
    /** Identifier of the block. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "tun");

    /** Settings of the block. */
    public static final Settings SETTINGS = FabricBlockSettings.copyOf(Blocks.BARREL);

    /** Level of the contained when full. */
    public static final int MAX_LEVEL = 27;

    /** Level of the contained. */
    public static final IntProperty LEVEL;

    /** Voxel shape of the block. */
    private static final VoxelShape VOXEL_SHAPE;

    /** Default color. */
    private static final int DEFAULT_COLOR = 0x241a09;

    /** List of all the color providers to color the contained. */
    private static final HashMap<Item, ContainableColorProvider> COLOR_PROVIDERS = new HashMap<>();

    /** List of all the possible interactions with items of the player. */
    private static final HashMap<Pair<Item, Item>, ContainerInteraction> INTERACTIONS = new HashMap<>();

    /** Initializes a new {@link TunBlock}. */
    public TunBlock(Settings settings) {
        super(settings);
    }

    /** Registers a color provider for the item specified. */
    public static void registerColorProvider(@NotNull Item item, @NotNull ContainableColorProvider colorProvider) {
        COLOR_PROVIDERS.putIfAbsent(item, colorProvider);
    }

    /** Gets the color provider for the item specified. */
    public static Optional<ContainableColorProvider> getColorProvider(@NotNull Item item) {
        return Optional.ofNullable(COLOR_PROVIDERS.get(item));
    }

    /** Registers an interaction for the items specified. */
    public static void registerInteraction(@NotNull Item used, @NotNull Item contained, @NotNull ContainerInteraction interaction) {
        INTERACTIONS.putIfAbsent(Pair.of(used, contained), interaction);
    }

    /** Gets the level property. */
    @Override
    public IntProperty getLevelProperty() {
        return LEVEL;
    }

    /** Gets the max level. */
    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    /** Gets the interaction for the items specified. */
    @Override
    public Optional<ContainerInteraction> getInteraction(@NotNull Item used, @NotNull Item contained) {
        return Optional.ofNullable(INTERACTIONS.get(Pair.of(used, contained)));
    }

    /** Gets the outline shape of the block. */
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VOXEL_SHAPE;
    }

    /** Gets the contained color. */
    public static int getContainedColor(BlockState state, BlockRenderView view, BlockPos pos, int i) {
        if (i != 1) return -1;

        //Gets the contained stack.
        ItemStack contained = getContained(view, pos);

        //Gets the contained color provider, basing on the contained item.
        Optional<ContainableColorProvider> colorProvider = getColorProvider(contained.getItem());

        //Gets the contained color.
        return colorProvider.map(provider -> provider.getColor(contained, state, view, pos, i)).orElse(DEFAULT_COLOR);
    }

    static {
        LEVEL = IntProperty.of("level", 0, MAX_LEVEL);
        VOXEL_SHAPE = Util.combineVoxelShapes(
                Block.createCuboidShape(2, 0, 2, 14, 1, 14),
                Block.createCuboidShape(0, 0, 2, 2, 16, 16),
                Block.createCuboidShape(14, 0, 0, 16, 16, 14),
                Block.createCuboidShape(2, 0, 14, 16, 16, 16),
                Block.createCuboidShape(0, 0, 0, 14, 16, 2)
        );
    }
}
