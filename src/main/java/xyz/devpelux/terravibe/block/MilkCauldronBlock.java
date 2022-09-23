package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.core.Util;
import xyz.devpelux.terravibe.item.TerravibeItems;
import xyz.devpelux.terravibe.tags.TerravibeItemTags;

import java.util.Map;

/** A cauldron that contains milk. */
public class MilkCauldronBlock extends AbstractCauldronBlock implements BlockColorProvider {
    /** Identifier of the block. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "milk_cauldron");

    /** Settings of the block. */
    public static final Settings SETTINGS = FabricBlockSettings.copyOf(Blocks.CAULDRON);

    /** Fermenting time to the next stage. */
    public static final int FERMENTING_TIME = 60;

    /** Content of the cauldron. */
    public static final EnumProperty<Content> CONTENT;

    /** Fill the cauldron with milk. */
    public static final CauldronBehavior FILL_WITH_MILK;

    /** Voxel shape when the cauldron is filled with solid or partially solid content. */
    private static final VoxelShape SOLID_FILLED_CAULDRON_VOXEL_SHAPE;

    /** Behavior map for milk cauldron. */
    private static final Map<Item, CauldronBehavior> MILK_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();

    /** Initializes a new {@link MilkCauldronBlock}. */
    public MilkCauldronBlock(Settings settings) {
        super(settings, MILK_CAULDRON_BEHAVIOR);
        setDefaultState(getStateManager().getDefaultState().with(CONTENT, Content.Milk));
    }

    /** Registers the properties of the block. */
    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(CONTENT);
    }

    /**
     * Executed when the block is used.
     * Insert ingredients, or interacts with the cauldron.
     */
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        //Gets the stack and the content.
        ItemStack stack = player.getStackInHand(hand);
        Content content = state.get(CONTENT);

        //Interacts with the content.
        if (stack.isEmpty()) {
            //If the content is mozzarella or a type of cheese, gets the content.
            if (content == Content.Mozzarella) {
                ItemStack drop = new ItemStack(TerravibeItems.MOZZARELLA, 6);
                return CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, drop, s -> true, SoundEvents.BLOCK_HONEY_BLOCK_BREAK);
            }
            if (content == Content.Cheese) {
                ItemStack drop = new ItemStack(TerravibeItems.CHEESE, 4);
                return CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, drop, s -> true, SoundEvents.BLOCK_HONEY_BLOCK_BREAK);
            }
            if (content == Content.Gorgonzola) {
                ItemStack drop = new ItemStack(TerravibeItems.GORGONZOLA, 4);
                return CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, drop, s -> true, SoundEvents.BLOCK_HONEY_BLOCK_BREAK);
            }
        }
        else if (stack.isIn(TerravibeItemTags.MILK_COAGULANTS) && content == Content.Milk) {
            //If the stack is a milk coagulant, and the content is milk, converts the content to acid milk.
            putIngredient(state, world, pos, player, stack, Content.AcidMilk, SoundEvents.ITEM_BOTTLE_EMPTY);

            //Client: SUCCESS / Server: CONSUME
            return ActionResult.success(world.isClient());
        }
        else if (stack.isIn(TerravibeItemTags.EDIBLE_MOLDS) && content == Content.AcidMilk) {
            //If the stack is an edible mold, and the content is acid milk, converts the content to acid moldy milk.
            putIngredient(state, world, pos, player, stack, Content.AcidMoldyMilk, SoundEvents.BLOCK_MOSS_PLACE);

            //Client: SUCCESS / Server: CONSUME
            return ActionResult.success(world.isClient());
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    /** Puts an ingredient into the cauldron, and converts the content into a result content. */
    private void putIngredient(BlockState state, World world, BlockPos pos, PlayerEntity player,
                              ItemStack ingredient, Content result, SoundEvent sound) {
        if (!world.isClient()) {
            //Converts the content to the result
            world.setBlockState(pos, state.with(CONTENT, result));

            //Updates the player statistics.
            player.incrementStat(Stats.USE_CAULDRON);
            player.incrementStat(Stats.USED.getOrCreateStat(ingredient.getItem()));

            //Consumes the item used, if the player is not in creative mode.
            if (!player.getAbilities().creativeMode) {
                ingredient.decrement(1);
            }

            //Plays the mold placing sound.
            world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    /** Gets a value indicating if the block reacts with the ticking system. */
    @Override
    public boolean hasRandomTicks(@NotNull BlockState state) {
        Content content = state.get(CONTENT);
        return content == Content.AcidMilk || content == Content.AcidMoldyMilk || content == Content.Mozzarella;
    }

    /**
     * Executed every tick randomly.
     * Handles the milk fermentation.
     */
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(FERMENTING_TIME) == 0) {
            //Pass to the next stage basing on the current stage.
            switch (state.get(CONTENT)) {
                case AcidMilk -> world.setBlockState(pos, state.with(CONTENT, Content.Mozzarella));
                case AcidMoldyMilk -> {
                    if (random.nextBoolean()) world.setBlockState(pos, state.with(CONTENT, Content.Gorgonzola));
                }
                case Mozzarella -> world.setBlockState(pos, state.with(CONTENT, Content.Cheese));
            }
        }
    }

    /**
     * Executed when an entity enter the cauldron.
     * Extinguishes fire.
     */
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient() && entity.isOnFire() && this.isEntityTouchingFluid(state, pos, entity)) {
            entity.extinguish();
        }
    }

    /** Gets a value indicating if the cauldron is full. */
    @Override
    public boolean isFull(BlockState state) {
        return true;
    }

    /** Gets the fluid height. */
    @Override
    protected double getFluidHeight(BlockState state) {
        return switch (state.get(CONTENT)) {
            case Milk, AcidMilk, AcidMoldyMilk -> 0.9375;
            case Mozzarella -> 0.75;
            case Cheese, Gorgonzola -> 0.5625;
        };
    }

    /** Gets the comparator output basing on the block state. */
    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return switch (state.get(CONTENT)) {
            case Milk, AcidMilk, AcidMoldyMilk -> 3;
            case Mozzarella -> 2;
            case Cheese, Gorgonzola -> 1;
        };
    }

    /** Gets the outline shape of the block. */
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Content content = state.get(CONTENT);
        if (content == Content.Cheese || content == Content.Gorgonzola || content == Content.Mozzarella) {
            return SOLID_FILLED_CAULDRON_VOXEL_SHAPE;
        }
        return super.getOutlineShape(state, world, pos, context);
    }

    /** Gets the colors of the block. */
    @Override
    public int getColor(BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos, int tintIndex) {
        if (tintIndex == 0) {
            return switch (state.get(CONTENT)) {
                case Milk -> 0xffffff;
                case AcidMilk-> 0xf0f0d0;
                case AcidMoldyMilk -> 0xd8ebd8;
                default -> -1;
            };
        }
        return -1;
    }

    /** Gets a value indicating if the cauldron can be emptied. */
    private static boolean canEmpty(BlockState state) {
        return state.get(CONTENT) == Content.Milk;
    }

    /** Refills the cauldron with another fluid only if it can be emptied. */
    private static ActionResult refillConditionally(BlockState state, World world, BlockPos pos, PlayerEntity player,
                                                    Hand hand, ItemStack stack, CauldronBehavior behavior) {
        return canEmpty(state) ? behavior.interact(state, world, pos, player, hand, stack) : ActionResult.PASS;
    }

    /** Loads all the cauldron behaviors. */
    public static void loadBehaviors() {
        //Fill with milk.
        CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(Items.MILK_BUCKET, FILL_WITH_MILK);
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.MILK_BUCKET, FILL_WITH_MILK);
        CauldronBehavior.LAVA_CAULDRON_BEHAVIOR.put(Items.MILK_BUCKET, FILL_WITH_MILK);
        CauldronBehavior.POWDER_SNOW_CAULDRON_BEHAVIOR.put(Items.MILK_BUCKET, FILL_WITH_MILK);

        //Milk cauldron refill.
        MILK_CAULDRON_BEHAVIOR.put(Items.WATER_BUCKET, (state, world, pos, player, hand, stack) -> {
            return refillConditionally(state, world, pos, player, hand, stack, CauldronBehavior.FILL_WITH_WATER);
        });
        MILK_CAULDRON_BEHAVIOR.put(Items.LAVA_BUCKET, (state, world, pos, player, hand, stack) -> {
            return refillConditionally(state, world, pos, player, hand, stack, CauldronBehavior.FILL_WITH_LAVA);
        });
        MILK_CAULDRON_BEHAVIOR.put(Items.POWDER_SNOW_BUCKET, (state, world, pos, player, hand, stack) -> {
            return refillConditionally(state, world, pos, player, hand, stack, CauldronBehavior.FILL_WITH_POWDER_SNOW);
        });

        //Milk cauldron emptying.
        MILK_CAULDRON_BEHAVIOR.put(Items.BUCKET, (state, world, pos, player, hand, stack) -> {
            return CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack,
                    new ItemStack(Items.MILK_BUCKET), MilkCauldronBlock::canEmpty, SoundEvents.ITEM_BUCKET_FILL);
        });
    }

    static {
        CONTENT = EnumProperty.of("content", Content.class);
        SOLID_FILLED_CAULDRON_VOXEL_SHAPE = Util.combineVoxelShapes(OUTLINE_SHAPE,
                Block.createCuboidShape(2, 4, 2, 14, 9, 14));
        FILL_WITH_MILK = (state, world, pos, player, hand, stack) -> {
            return CauldronBehavior.fillCauldron(world, pos, player, hand, stack,
                    TerravibeBlocks.MILK_CAULDRON.getDefaultState(), SoundEvents.ITEM_BUCKET_EMPTY);
        };
    }


    /**
     * Represents the milk type contained in a {@link MilkCauldronBlock}.
     */
    public enum Content implements StringIdentifiable {
        /** The cauldron contains milk. */
        Milk("milk"),

        /** The cauldron contains acid milk. */
        AcidMilk("acid_milk"),

        /** The cauldron contains acid moldy milk. */
        AcidMoldyMilk("acid_moldy_milk"),

        /** The cauldron contains mozzarella. */
        Mozzarella("mozzarella"),

        /** The cauldron contains cheese. */
        Cheese("cheese"),

        /** The cauldron contains gorgonzola. */
        Gorgonzola("gorgonzola");

        /** Name representing the value. */
        private final String name;

        /** Initializes a new value with the name specified. */
        Content(String name) {
            this.name = name;
        }

        /** Returns the string representation of this instance. */
        @Override
        public @NotNull String asString() {
            return this.name;
        }

        /** Returns the string representation of this instance. */
        @Override
        public @NotNull String toString() {
            return asString();
        }
    }
}
