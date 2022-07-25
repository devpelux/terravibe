package xyz.devpelux.terravibe.block;

import it.unimi.dsi.fastutil.Pair;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
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
import xyz.devpelux.terravibe.block.container.ContainerInteraction;
import xyz.devpelux.terravibe.block.container.ContainerInteractionResult;
import xyz.devpelux.terravibe.blockentity.ContainerBlockEntity;
import xyz.devpelux.terravibe.blockentity.TunBlockEntity;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.core.Util;

import java.util.HashMap;
import java.util.Optional;

/** Container for "non-lava" fluids. */
public class TunBlock extends BlockWithEntity {
    /** Identifier of the block. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "tun");

    /** Settings of the block. */
    public static final Settings SETTINGS = FabricBlockSettings.copyOf(Blocks.BARREL);

    /** Level of the fluid contained when the tun is full. */
    public static final int FULL_LEVEL = 27;

    /** Level of the fluid contained. */
    public static final IntProperty LEVEL;

    /** Voxel shape of the block. */
    private static final VoxelShape VOXEL_SHAPE;

    /** Default color. */
    private static final int DEFAULT_COLOR = 0x241a09;

    /** List of all containable items with their colors. */
    private static final HashMap<Item, ContainableColorProvider> CONTAINABLE = new HashMap<>();

    /** List of all the possible interactions with items of the player. */
    private static final HashMap<Pair<Item, Item>, ContainerInteraction> INTERACTIONS = new HashMap<>();

    /** Initializes a new {@link TunBlock}. */
    public TunBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(LEVEL, 0));
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

    /** Gets the interaction for the items specified. */
    public static Optional<ContainerInteraction> getInteraction(@NotNull Item used, @NotNull Item contained) {
        return Optional.ofNullable(INTERACTIONS.get(Pair.of(used, contained)));
    }

    /** Registers the properties of the block. */
    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(LEVEL);
    }

    /** Gets the level of the container in the specified position. */
    protected int getLevel(@NotNull World world, @NotNull BlockPos pos) {
        return world.getBlockState(pos).get(LEVEL);
    }

    /** Sets the level of the container in the specified position. */
    protected void setLevel(@NotNull World world, @NotNull BlockPos pos, int level) {
        world.setBlockState(pos, world.getBlockState(pos).with(LEVEL, level));
    }

    /** Gets the contained stack. */
    public static @NotNull ItemStack getContained(@NotNull BlockView world, @NotNull BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof ContainerBlockEntity container) {
            return container.getContained();
        }
        return ItemStack.EMPTY;
    }

    /** Sets the contained stack. */
    public static void setContained(@NotNull BlockView world, @NotNull BlockPos pos, @NotNull ItemStack stack) {
        if (world.getBlockEntity(pos) instanceof ContainerBlockEntity container) {
            container.setContained(stack);
        }
    }

    /**
     * Executed when the block is used.
     * Gets or pours the fluid.
     */
    @Override
    public ActionResult onUse(BlockState state, @NotNull World world, BlockPos pos, @NotNull PlayerEntity player, Hand hand, BlockHitResult hit) {
        //Gets the stack in the main hand and the contained items, then gets the interaction for them.
        ItemStack inHand = player.getStackInHand(Hand.MAIN_HAND);
        ItemStack contained = getContained(world, pos);
        Optional<ContainerInteraction> interaction = getInteraction(inHand.getItem(), contained.getItem());

        if (interaction.isPresent()) {
            //Gets the level.
            int level = getLevel(world, pos);

            //Gets the result of the interaction.
            ContainerInteractionResult result = interaction.get().onUse(state, world, pos, player, inHand, contained, level);

            if (result.getInteraction() == ContainerInteractionResult.Interaction.INSERT) {
                //If the tun can contain the extra fluid, insert into the tun.
                if (result.getLevel() <= FULL_LEVEL) {
                    if (!world.isClient()) {
                        //Increments the level.
                        setLevel(world, pos, result.getLevel());

                        //Sets the contained to the new contained.
                        setContained(world, pos, Util.copyStack(result.getContained(), 1));

                        //Consumes the main stack and drops the drop (only if the player is not in creative).
                        if (!player.getAbilities().creativeMode) {
                            inHand.decrement(result.getConsumed());
                            player.getInventory().offerOrDrop(result.getDrop().copy());
                        }

                        //Plays the sound.
                        result.getSound().ifPresent(sound -> player.playSound(sound, SoundCategory.BLOCKS, 1f, 1f));
                    }

                    return ActionResult.success(world.isClient());
                }
            }
            else if (result.getInteraction() == ContainerInteractionResult.Interaction.EXTRACT) {
                //If the tun contains enough fluid, extract from the tun.
                if (result.getLevel() >= 0) {
                    if (!world.isClient()) {
                        //Decrements the level.
                        setLevel(world, pos, result.getLevel());

                        if (result.getLevel() > 0) {
                            //Sets the contained to the new contained.
                            setContained(world, pos, Util.copyStack(contained, 1));
                        }
                        else {
                            //If the tun becomes empty, removes the contained.
                            setContained(world, pos, ItemStack.EMPTY);
                        }

                        //Consumes the main stack and drops the drop (only if the player is not in creative).
                        if (!player.getAbilities().creativeMode) {
                            inHand.decrement(result.getConsumed());
                            player.getInventory().offerOrDrop(result.getDrop().copy());
                        }

                        //Plays the fill sound.
                        result.getSound().ifPresent(sound -> player.playSound(sound, SoundCategory.BLOCKS, 1f, 1f));
                    }

                    return ActionResult.success(world.isClient());
                }
            }
            else {
                return result.getAction();
            }
        }

        return ActionResult.PASS;
    }

    /** Gets the render type of the block. */
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    /** Returns a value indicating if to enable color blending for this block. */
    @Override
    public boolean enableSodiumColorBlending() {
        return false;
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
        LEVEL = IntProperty.of("level", 0, FULL_LEVEL);
        VOXEL_SHAPE = Util.combineVoxelShapes(
                Block.createCuboidShape(2, 0, 2, 14, 1, 14),
                Block.createCuboidShape(0, 0, 2, 2, 16, 16),
                Block.createCuboidShape(14, 0, 0, 16, 16, 14),
                Block.createCuboidShape(2, 0, 14, 16, 16, 16),
                Block.createCuboidShape(0, 0, 0, 14, 16, 2)
        );
    }
}
