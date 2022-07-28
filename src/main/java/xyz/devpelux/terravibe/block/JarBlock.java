package xyz.devpelux.terravibe.block;

import it.unimi.dsi.fastutil.Pair;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.block.container.ContainableColorProvider;
import xyz.devpelux.terravibe.block.container.ContainerBlock;
import xyz.devpelux.terravibe.block.container.ContainerInteraction;
import xyz.devpelux.terravibe.blockentity.TunBlockEntity;
import xyz.devpelux.terravibe.core.ModInfo;

import java.util.HashMap;
import java.util.Optional;

/** Container for things. */
public class JarBlock extends ContainerBlock {
    /** Identifier of the block. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "jar");

    /** Settings of the block. */
    public static final Settings SETTINGS;

    /** Level of the contained when full. */
    public static final int MAX_LEVEL = 3;

    /** Level of the contained. */
    public static final IntProperty LEVEL;

    /** Specifies if the jar is closed. */
    public static final BooleanProperty CLOSED;

    /** Voxel shape of the block. */
    private static final VoxelShape VOXEL_SHAPE;

    /** Default color. */
    private static final int DEFAULT_COLOR = 0x241a09;

    /** List of all containable items with their colors. */
    private static final HashMap<Item, ContainableColorProvider> CONTAINABLE = new HashMap<>();

    /** List of all the possible interactions with items of the player. */
    private static final HashMap<Pair<Item, Item>, ContainerInteraction> INTERACTIONS = new HashMap<>();

    /** Initializes a new {@link JarBlock}. */
    public JarBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(LEVEL, 0).with(CLOSED, false));
    }

    /** Registers a color provider for the item specified. */
    public static void registerColorProvider(@NotNull Item item, @NotNull ContainableColorProvider colorProvider) {
        CONTAINABLE.putIfAbsent(item, colorProvider);
    }

    /** Gets the color provider for the item specified. */
    public static Optional<ContainableColorProvider> getColorProvider(@NotNull Item item) {
        return Optional.ofNullable(CONTAINABLE.get(item));
    }

    /** Registers an interaction for the items specified. */
    public static void registerInteraction(@NotNull Item used, @NotNull Item contained, @NotNull ContainerInteraction interaction) {
        INTERACTIONS.putIfAbsent(Pair.of(used, contained), interaction);
    }

    /** Registers the properties of the block. */
    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(CLOSED);
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

    /**
     * Executed when the block is used.
     * Closes or opens the jar.
     */
    @Override
    public ActionResult onUse(BlockState state, @NotNull World world, BlockPos pos, @NotNull PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack inHand = player.getStackInHand(Hand.MAIN_HAND);
        boolean isClosed = world.getBlockState(pos).get(CLOSED);
        if (isClosed) {
            if (inHand.isEmpty()) {
                if (!world.isClient()) {
                    //Opens the jar.
                    world.setBlockState(pos, state.with(CLOSED, false));

                    //Drops the button (only if the player is not in creative).
                    if (!player.getAbilities().creativeMode) {
                        player.getInventory().offerOrDrop(new ItemStack(Blocks.OAK_BUTTON));
                    }
                }

                //Client: SUCCESS / Server: CONSUME
                return ActionResult.success(world.isClient());
            }

            return ActionResult.PASS;
        }
        else {
            if (inHand.isOf(Items.OAK_BUTTON)) {
                if (!world.isClient()) {
                    //Closes the jar.
                    world.setBlockState(pos, state.with(CLOSED, true));

                    //Consumes the hand stack (only if the player is not in creative).
                    if (!player.getAbilities().creativeMode) {
                        inHand.decrement(1);
                    }
                }

                return ActionResult.success(world.isClient());
            }

            return super.onUse(state, world, pos, player, hand, hit);
        }
    }

    /** Creates the block entity for the block. */
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TunBlockEntity(pos, state);
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

        //Gets the container color provider, basing on the contained item.
        Optional<ContainableColorProvider> colorProvider = getColorProvider(contained.getItem());

        //Gets the container color.
        return colorProvider.map(provider -> provider.getColor(contained, state, view, pos, i)).orElse(DEFAULT_COLOR);
    }

    static {
        SETTINGS = FabricBlockSettings.copyOf(Blocks.GLASS).breakInstantly();
        LEVEL = IntProperty.of("level", 0, MAX_LEVEL);
        CLOSED = BooleanProperty.of("closed");
        VOXEL_SHAPE = Block.createCuboidShape(5, 0, 5, 11, 9, 11);
    }
}
