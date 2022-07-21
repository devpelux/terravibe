package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
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
import xyz.devpelux.terravibe.blockentity.TunBlockEntity;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.core.Util;
import xyz.devpelux.terravibe.core.rendering.ContainableColorProvider;

import java.util.HashMap;

/** Container for "non-lava" fluids. */
public class TunBlock extends BlockWithEntity {
    /** Identifier of the block. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "tun");

    /** Settings of the block. */
    public static final Settings SETTINGS = FabricBlockSettings.copyOf(Blocks.BARREL);

    /** Level of the fluid contained when the tun is empty. */
    public static final int EMPTY_LEVEL = 0;

    /** Level of the fluid contained when the tun is full. */
    public static final int FULL_LEVEL = 27;

    /** Level of the fluid contained. */
    public static final IntProperty LEVEL;

    /** Voxel shape of the block. */
    private static final VoxelShape VOXEL_SHAPE;

    /** List of all containable items with their fluid colors. */
    private static final HashMap<Item, ContainableColorProvider> CONTAINABLE = new HashMap<>();

    /** Initializes a new {@link MortarBlock}. */
    public TunBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(LEVEL, EMPTY_LEVEL));
    }

    /** Registers the properties of the block. */
    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
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
    public static void registerContainable(Item item, ContainableColorProvider colorProvider) {
        CONTAINABLE.putIfAbsent(item, colorProvider);
    }

    /** Gets a value indicating if the specified item is a fluid containable in the tun. */
    public static boolean isContainable(Item item) {
        return CONTAINABLE.containsKey(item);
    }

    /** Gets the contained stack. */
    protected static @NotNull ItemStack getContained(@NotNull BlockView world, @NotNull BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof TunBlockEntity tunEntity) {
            return tunEntity.getContained();
        }
        return ItemStack.EMPTY;
    }

    /** Sets the contained stack. */
    protected static void setContained(@NotNull BlockView world, @NotNull BlockPos pos, @NotNull ItemStack stack) {
        if (world.getBlockEntity(pos) instanceof TunBlockEntity tunEntity) {
            tunEntity.setContained(stack);
        }
    }

    /**
     * Executed when the block is used.
     * Gets or pours the fluid.
     */
    @Override
    public ActionResult onUse(BlockState state, @NotNull World world, BlockPos pos, @NotNull PlayerEntity player, Hand hand, BlockHitResult hit) {
        //Gets the stack in the main hand.
        ItemStack stackInHand = player.getStackInHand(Hand.MAIN_HAND);

        //Checks if the item of the stack is containable.
        if (isContainable(stackInHand.getItem()) && !isFull(state)) {
            //Checks if the stack can be contained (the stack is the same or the tun is empty).
            if (ItemStack.canCombine(getContained(world, pos), stackInHand) || isEmpty(state)) {
                if (!world.isClient()) {
                    //Increments the level by 1.
                    setLevel(state, world, pos, getLevel(state) + 1);

                    //Gets the stack to store, then sets the count to 1.
                    ItemStack toContain = stackInHand.copy();
                    toContain.setCount(1);

                    //Sets the contained stack.
                    setContained(world, pos, toContain);

                    //Coverts an item from the stack into a glass bottle.
                    if (!player.getAbilities().creativeMode) {
                        //The inventory is changed only if the player is not in creative mode.
                        stackInHand.decrement(1);
                        player.getInventory().offerOrDrop(new ItemStack(Items.GLASS_BOTTLE));
                    }

                    //Plays the empty sound.
                    player.playSound(getEmptySound(), SoundCategory.BLOCKS, 1f, 1f);
                }

                //Client: SUCCESS / Server: CONSUME
                return ActionResult.success(world.isClient());
            }
        }
        else if(stackInHand.isOf(Items.GLASS_BOTTLE) && !isEmpty(state)) {
            //Checks if the item of the stack is a glass bottle.
            if (!world.isClient()) {
                //Decrements the level by 1.
                int level = getLevel(state);
                setLevel(state, world, pos, level - 1);

                //Gets the contained stack to drop.
                ItemStack contained = getContained(world, pos).copy();

                //If the tun becomes empty, removes the contained item.
                if (level == 1) setContained(world, pos, ItemStack.EMPTY);

                //Coverts the bottle into the original item.
                if (!player.getAbilities().creativeMode) {
                    //The inventory is changed only if the player is not in creative mode.
                    player.getStackInHand(Hand.MAIN_HAND).decrement(1);
                    player.getInventory().offerOrDrop(contained);
                }

                //Plays the fill sound.
                player.playSound(getFillSound(), SoundCategory.BLOCKS, 1f, 1f);
            }

            //Client: SUCCESS / Server: CONSUME
            return ActionResult.success(world.isClient());
        }

        return ActionResult.PASS;
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
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VOXEL_SHAPE;
    }

    /** Returns a value indicating if to enable color blending for this block. */
    @Override
    public boolean enableSodiumColorBlending() {
        return false;
    }

    /** Gets the color of the contained fluid. */
    public static int getContainedFluidColor(BlockState state, BlockRenderView view, BlockPos pos, int i) {
        if (i != 1) return -1;

        //Gets the contained stack.
        ItemStack contained = getContained(view, pos);

        //Checks if the contained stack is not empty.
        if (!contained.isEmpty()) {
            ContainableColorProvider colorProvider = CONTAINABLE.get(contained.getItem());
            if (colorProvider != null) {
                return colorProvider.getColor(contained, state, view, pos, i);
            }
        }

        return 0x241a09;
    }

    static {
        LEVEL = IntProperty.of("level", EMPTY_LEVEL, FULL_LEVEL);
        VOXEL_SHAPE = Util.combineVoxelShapes(
                Block.createCuboidShape(2, 0, 2, 14, 1, 14),
                Block.createCuboidShape(0, 0, 2, 2, 16, 16),
                Block.createCuboidShape(14, 0, 0, 16, 16, 14),
                Block.createCuboidShape(2, 0, 14, 16, 16, 16),
                Block.createCuboidShape(0, 0, 0, 14, 16, 2)
        );
    }
}
