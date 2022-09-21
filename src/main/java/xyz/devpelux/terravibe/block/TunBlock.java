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
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.block.container.ContainerBlock;
import xyz.devpelux.terravibe.blockentity.ContainerBlockEntity;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.core.Util;

import java.util.HashMap;

/** Container for "non-lava" fluids. */
public class TunBlock extends ContainerBlock implements BlockColorProvider {
    /** Identifier of the block. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "tun");

    /** Settings of the block. */
    public static final Settings SETTINGS = FabricBlockSettings.copyOf(Blocks.BARREL);

    /** Level of the content when full. */
    public static final int MAX_LEVEL = 27;

    /** Level of the content. */
    public static final IntProperty LEVEL;

    /** Voxel shape of the block. */
    private static final VoxelShape VOXEL_SHAPE;

    /** Default content color. */
    private static final int DEFAULT_CONTENT_COLOR = 0x241a09;

    /** List of all the color providers to color the content. */
    private static final HashMap<String, ContainerBlockEntity.ContainerBlockColorProvider> COLOR_PROVIDERS = new HashMap<>();

    /** List of all the possible behaviors of the container. */
    private static final HashMap<Pair<String, Item>, ContainerBehavior> BEHAVIORS = new HashMap<>();

    /** Initializes a new {@link TunBlock}. */
    public TunBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(LEVEL, 0));
    }

    /** Registers a color provider for the content specified. */
    public static void registerColorProvider(@NotNull String content, @NotNull ContainerBlockEntity.ContainerBlockColorProvider colorProvider) {
        COLOR_PROVIDERS.putIfAbsent(content, colorProvider);
    }

    /** Gets the color provider for the content specified. */
    public static @Nullable ContainerBlockEntity.ContainerBlockColorProvider getColorProvider(@NotNull String content) {
        return COLOR_PROVIDERS.get(content);
    }

    /** Registers a behavior for the content and input specified. */
    public static void registerBehavior(@NotNull String content, @NotNull Item used, @NotNull ContainerBehavior interaction) {
        BEHAVIORS.putIfAbsent(Pair.of(content, used), interaction);
    }

    /** Gets the identifier of the block. */
    @Override
    public Identifier getId() {
        return ID;
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

    /** Gets the behavior for the content and input specified. */
    @Override
    public @Nullable ContainerBehavior getBehavior(@NotNull String content, @NotNull Item input) {
        return BEHAVIORS.get(Pair.of(content, input));
    }

    /** Gets the outline shape of the block. */
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VOXEL_SHAPE;
    }

    /** Gets the colors of the block. */
    @Override
    public int getColor(BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos, int tintIndex) {
        if (tintIndex != 1) return -1;

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
