package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.blockentity.TunBlockEntity;
import xyz.devpelux.terravibe.core.ModInfo;

import java.util.HashMap;
import java.util.stream.Stream;

/** Container for "non-lava" fluids. */
public class TunBlock extends BlockWithEntity {
    /** Identifier of the block. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "tun");

    /** Level of the fluid contained when the tun is empty. */
    public static final int EMPTY_LEVEL = 0;

    /** Level of the fluid contained when the tun is full. */
    public static final int FULL_LEVEL = 16;

    /** Level of the fluid contained. */
    public static final IntProperty LEVEL = IntProperty.of("level", EMPTY_LEVEL, FULL_LEVEL);

    /** Voxel shape of the block. */
    private static VoxelShape VOXEL_SHAPE = null;

    /** List of all containable items with their fluid colors. */
    private static final HashMap<Item, BlockColorProvider> containable = new HashMap<>();

    /** Initializes a new {@link MortarBlock}. */
    public TunBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(LEVEL, 0));
    }

    /** Gets the block settings. */
    public static @NotNull FabricBlockSettings getSettings() {
        return FabricBlockSettings.copyOf(Blocks.BARREL);
    }

    /** Registers the properties of the block. */
    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    /** Gets the level of the fluid contained. */
    protected int getLevel(@NotNull BlockState state) {
        return state.get(LEVEL);
    }

    /** Sets the level of the fluid contained. */
    protected void setLevel(@NotNull BlockState state, @NotNull World world, BlockPos pos, int level) {
        world.setBlockState(pos, state.with(LEVEL, level));
    }

    /** Gets a value indicating if the tun is empty. */
    protected boolean isEmpty(@NotNull BlockState state) {
        return getLevel(state) == EMPTY_LEVEL;
    }

    /** Gets a value indicating if the tun is full. */
    protected boolean isFull(@NotNull BlockState state) {
        return getLevel(state) == FULL_LEVEL;
    }

    /** Gets the empty sound. */
    protected SoundEvent getEmptySound() {
        return SoundEvents.ITEM_BOTTLE_EMPTY;
    }

    /** Gets the fill sound. */
    protected SoundEvent getFillSound() {
        return SoundEvents.ITEM_BOTTLE_FILL;
    }

    /** Registers a containable item in the tun with its fluid color. */
    public static void registerContainable(Item item, BlockColorProvider colorProvider) {
        containable.putIfAbsent(item, colorProvider);
    }

    /** Gets a value indicating if the specified item has a fluid containable in the tun. */
    public static boolean isItemContainable(Item item) {
        return containable.containsKey(item);
    }

    /** Gets the contained item. */
    protected static @Nullable Item getContainedItem(@NotNull World world, BlockPos pos) {
        TunBlockEntity tunEntity = (TunBlockEntity) world.getBlockEntity(pos);
        return tunEntity != null ? tunEntity.getContained() : null;
    }

    /** Sets the contained item. */
    protected static void setContainedItem(@NotNull World world, BlockPos pos, Item item) {
        TunBlockEntity tunEntity = (TunBlockEntity) world.getBlockEntity(pos);
        if (tunEntity != null) {
            tunEntity.setContained(item);
        }
    }

    /**
     * Executed when the block is used.<br>
     * Gets or pours the fluid.
     */
    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, @NotNull World world, BlockPos pos, @NotNull PlayerEntity player, Hand hand, BlockHitResult hit) {
        //Gets the item in the main hand.
        Item itemInHand = player.getStackInHand(Hand.MAIN_HAND).getItem();

        if (!world.isClient()) {
            if (isItemContainable(itemInHand) && !isFull(state)) {
                //If the item is containable.
                //Checks if the item can be contained (the tun contains nothing or the item is the same).
                if (getContainedItem(world, pos) == itemInHand || getContainedItem(world, pos) == null) {
                    //Increments the level by 1.
                    int level = getLevel(state);
                    setLevel(state, world, pos, level + 1);
                    //Coverts the item into a glass bottle.
                    player.getStackInHand(Hand.MAIN_HAND).decrement(1);
                    player.getInventory().offerOrDrop(new ItemStack(Items.GLASS_BOTTLE, 1));
                    //Plays the empty sound.
                    player.playSound(getEmptySound(), 1f, 1f);

                    //Sets the contained item.
                    setContainedItem(world, pos, itemInHand);

                    return ActionResult.SUCCESS;
                }
            }
            else if(player.getStackInHand(Hand.MAIN_HAND).isOf(Items.GLASS_BOTTLE) && !isEmpty(state)) {
                //If the item is a glass bottle.
                //Decrements the level by 1.
                int level = getLevel(state);
                setLevel(state, world, pos, level - 1);
                //Coverts the bottle into the original item.
                player.getStackInHand(Hand.MAIN_HAND).decrement(1);
                player.getInventory().offerOrDrop(new ItemStack(getContainedItem(world, pos), 1));
                //Plays the fill sound.
                player.playSound(getFillSound(), 1f, 1f);

                //If is empty, removes the contained item.
                if (level == 1) setContainedItem(world, pos, null);

                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        }

        return ActionResult.SUCCESS;
    }

    /** Creates the block entity for the block. */
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TunBlockEntity(pos, state);
    }

    /** Gets the render type of the block. */
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    /** Gets the outline shape of the block. */
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getVoxelShape();
    }

    /** Gets the ray-cast shape of the block. */
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return getVoxelShape();
    }

    /** Gets the voxel shape of the block. */
    public static @NotNull VoxelShape getVoxelShape() {
        if (VOXEL_SHAPE == null) {
            VOXEL_SHAPE = Stream.of(
                            Block.createCuboidShape(2, 0, 2, 14, 2, 14),
                            Block.createCuboidShape(0, 0, 2, 2, 16, 16),
                            Block.createCuboidShape(14, 0, 0, 16, 16, 14),
                            Block.createCuboidShape(2, 0, 14, 16, 16, 16),
                            Block.createCuboidShape(0, 0, 0, 14, 16, 2))
                    .reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
        }
        return VOXEL_SHAPE;
    }

    /** Gets the color of the contained fluid. */
    public static int getContainedFluidColor(BlockState blockState, BlockRenderView blockRenderView, BlockPos blockPos, int i) {
        if (i != 1) return -1;

        if (blockRenderView != null) {
            if (blockRenderView.getBlockEntity(blockPos) instanceof TunBlockEntity tunEntity) {
                if (tunEntity.hasContained()) {
                    return containable.get(tunEntity.getContained()).getColor(blockState, blockRenderView, blockPos, i);
                }
            }
        }

        return 0x241a09;
    }
}