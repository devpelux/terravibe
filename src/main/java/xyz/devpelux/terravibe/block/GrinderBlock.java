package xyz.devpelux.terravibe.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.recipe.ConvertingRecipe;
import xyz.devpelux.terravibe.recipe.RecipeTypeList;

import java.util.Optional;

/** Grinds an item to obtain another item. */
public class GrinderBlock extends Block {
    /** Identifier of the block. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "grinder");

    /** Voxel shape of the block. */
    private static final VoxelShape VOXEL_SHAPE = Block.createCuboidShape(5, 0, 5, 11, 10, 11);

    /** Spawn position of the fail particles. */
    private static final Vec3f FAIL_PARTICLES_SPAWN_POINT = new Vec3f(0.5f, 0.1875f, 0.5f);

    /** Initializes a new {@link GrinderBlock}. */
    public GrinderBlock(Settings settings) {
        super(settings);
    }

    /**
     * Executed when the block is used.<br>
     * Tries to grind the item in hand to obtain another item.
     */
    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        //Checks if exists a grinding recipe for the item in hand.
        Optional<ConvertingRecipe> match = world.getRecipeManager()
                .getFirstMatch(RecipeTypeList.CONVERTING_RECIPE, new SimpleInventory(player.getStackInHand(hand)), world);

        //Gets the recipe if exists.
        if (match.isPresent()) {
            ConvertingRecipe recipe = match.get();

            //This is server side.
            if (!world.isClient()) {

                //If the recipe exists, gets the item from the hand and tries to "grind" (convert) it.
                //If the conversion is successful, then adds or drops the resulting item.
                //Else, spawns fail particles to say that the item was consumed but nothing is returned.
                player.getStackInHand(hand).decrement(1);
                if (recipe.isSuccessful()) {
                    player.getInventory().offerOrDrop(recipe.getOutput().copy());

                    //If is successful, drops experience.
                    ExperienceOrbEntity.spawn((ServerWorld)world, player.getPos(), 2);
                }
                else failEffects(world, pos);
            }

            //Client: SUCCESS / Server: CONSUME
            return ActionResult.success(world.isClient());
        }
        else return ActionResult.PASS;
    }

    /** Spawn the fail effects. */
    private static void failEffects(@NotNull World world, @NotNull BlockPos pos) {
        Random random = world.getRandom();
        double speed = random.nextGaussian() * 0.01d + 0.02d; // [0.02, 0.03]
        double deltaX = random.nextGaussian() * 0.02d; // [0, 0.02]
        double deltaY = random.nextGaussian() * 0.02d; // [0, 0.02]
        double deltaZ = random.nextGaussian() * 0.02d; // [0, 0.02]

        //Spawns random particles.
        ((ServerWorld)world).spawnParticles(ParticleTypes.SMOKE,
                pos.getX() + FAIL_PARTICLES_SPAWN_POINT.getX(),
                pos.getY() + FAIL_PARTICLES_SPAWN_POINT.getY(),
                pos.getZ() + FAIL_PARTICLES_SPAWN_POINT.getZ(),
                10,
                deltaX,
                deltaY,
                deltaZ,
                speed);
    }

    /** Gets the outline shape of the block. */
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VOXEL_SHAPE;
    }

    /** Gets the ray-cast shape of the block. */
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return VOXEL_SHAPE;
    }
}
