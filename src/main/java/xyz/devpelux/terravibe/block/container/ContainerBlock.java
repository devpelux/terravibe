package xyz.devpelux.terravibe.block.container;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
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
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.blockentity.ContainerBlockEntity;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.core.Util;

import java.util.Optional;

/** Container. */
public abstract class ContainerBlock extends BlockWithEntity {
    /** Identifier of the block. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "jar");

    /** Initializes a new {@link ContainerBlock}. */
    public ContainerBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(getLevelProperty(), 0));
    }

    /** Registers the properties of the block. */
    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(getLevelProperty());
    }

    /** Gets the level property. */
    public abstract IntProperty getLevelProperty();

    /** Gets the max level. */
    public abstract int getMaxLevel();

    /** Gets the interaction for the items specified. */
    public abstract Optional<ContainerInteraction> getInteraction(@NotNull Item used, @NotNull Item contained);

    /** Gets the level of the container in the specified position. */
    protected int getLevel(@NotNull World world, @NotNull BlockPos pos) {
        return world.getBlockState(pos).get(getLevelProperty());
    }

    /** Sets the level of the container in the specified position. */
    protected void setLevel(@NotNull World world, @NotNull BlockPos pos, int level) {
        world.setBlockState(pos, world.getBlockState(pos).with(getLevelProperty(), level));
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
     * Inserts or extracts the fluid.
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
                if (result.getLevel() <= getMaxLevel()) {
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

                    //Client: SUCCESS / Server: CONSUME
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

                    //Client: SUCCESS / Server: CONSUME
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
}
