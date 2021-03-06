package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.recipe.CrushingRecipe;
import xyz.devpelux.terravibe.recipe.TerravibeRecipeTypes;

import java.util.Optional;

/** Crushes an item to obtain other items. */
public class MortarBlock extends Block {
    /** Identifier of the block. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "mortar");

    /** Settings of the block. */
    public static final Settings SETTINGS = FabricBlockSettings.copyOf(Blocks.FLOWER_POT);

    /** Voxel shape of the block. */
    private static final VoxelShape VOXEL_SHAPE;

    /** Initializes a new {@link MortarBlock}. */
    public MortarBlock(Settings settings) {
        super(settings);
    }

    /** Gets the crush sound. */
    protected SoundEvent getCrushSound() {
        return SoundEvents.BLOCK_ROOTED_DIRT_BREAK;
    }

    /**
     * Executed when the block is used.
     * Tries to crush the item in hand to obtain another item.
     */
    @Override
    public ActionResult onUse(BlockState state, @NotNull World world, BlockPos pos, @NotNull PlayerEntity player, Hand hand, BlockHitResult hit) {
        //Checks if exists a crushing recipe for the item in hand.
        Optional<CrushingRecipe> match = world.getRecipeManager()
                .getFirstMatch(TerravibeRecipeTypes.CRUSHING, new SimpleInventory(player.getStackInHand(hand)), world);

        //Gets the recipe if exists.
        if (match.isPresent()) {
            CrushingRecipe recipe = match.get();

            //This is server side.
            if (!world.isClient()) {
                //Gets the item from the player hand.
                if (!player.getAbilities().creativeMode) {
                    //The item is consumed only if the player is not in creative mode.
                    player.getStackInHand(hand).decrement(1);
                }

                //Gets a random count of the output item to return.
                int resultCount = recipe.getRandomCount(world.random);
                if (resultCount > 0) {
                    ItemStack output = recipe.getOutput().copy();
                    output.setCount(resultCount);
                    player.getInventory().offerOrDrop(output);
                }

                //Plays the crush sound.
                player.playSound(getCrushSound(), SoundCategory.BLOCKS, 1f, 1f);
            }

            //Client: SUCCESS / Server: CONSUME
            return ActionResult.success(world.isClient());
        }
        
        else return ActionResult.PASS;
    }

    /** Gets the outline shape of the block. */
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VOXEL_SHAPE;
    }

    static {
        VOXEL_SHAPE = Block.createCuboidShape(5, 0, 5, 11, 10, 11);
    }
}
