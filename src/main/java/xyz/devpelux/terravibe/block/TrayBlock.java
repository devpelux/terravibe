package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
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
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.item.TerravibeItems;

/** Tray used to make salt. */
public class TrayBlock extends Block {
    /** Identifier of the block. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "tray");

    /** Settings of the block. */
    public static final Settings SETTINGS;

    /** Content of the tray. */
    public static final EnumProperty<Content> CONTENT;

    /** Max evaporation time. */
    public static final int MAX_EVAPORATION_TIME = 50;

    /** Min evaporation time. */
    public static final int MIN_EVAPORATION_TIME = 10;

    /** Rain filling chance. */
    public static final float RAIN_FILLING_CHANCE = 0.2F;

    /** Voxel shape of the block. */
    private static final VoxelShape VOXEL_SHAPE;

    /** Initializes a new {@link TrayBlock}. */
    public TrayBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(CONTENT, Content.Nothing));
    }

    /** Registers the properties of the block. */
    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(CONTENT);
    }

    /** Gets the content of the tray. */
    protected Content getContent(@NotNull BlockState state) {
        return state.get(CONTENT);
    }

    /** Sets the content of the tray. */
    protected void setContent(@NotNull BlockState state, @NotNull World world, BlockPos pos, Content content) {
        world.setBlockState(pos, state.with(CONTENT, content));
    }

    /** Gets the take salt sound. */
    protected SoundEvent getTakeSaltSound() {
        return SoundEvents.BLOCK_SAND_BREAK;
    }

    /** Gets the empty water sound. */
    protected SoundEvent getEmptyWaterSound() {
        return SoundEvents.ITEM_BOTTLE_EMPTY;
    }

    /** Gets the fill water sound. */
    protected SoundEvent getFillWaterSound() {
        return SoundEvents.ITEM_BOTTLE_FILL;
    }

    /**
     * Executed when the block is used.
     * Take the salt or put the water.
     */
    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, @NotNull PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack handStack = player.getStackInHand(hand);
        Content content = getContent(state);
        switch (content) {
            case Nothing -> {
                if (PotionUtil.getPotion(handStack) == Potions.WATER) {
                    if (!world.isClient()) {
                        setContent(state, world, pos, Content.Water);
                        //Takes the bottle with water and gives a glass bottle.
                        if (!player.getAbilities().creativeMode) {
                            //The inventory is changed only if the player is not in creative mode.
                            handStack.decrement(1);
                            player.getInventory().offerOrDrop(new ItemStack(Items.GLASS_BOTTLE));
                        }
                        player.playSound(getEmptyWaterSound(), SoundCategory.BLOCKS, 1f, 1f);
                    }

                    //Client: SUCCESS / Server: CONSUME
                    return ActionResult.success(world.isClient());
                }
            }
            case Water -> {
                if (handStack.isOf(Items.GLASS_BOTTLE)) {
                    if (!world.isClient()) {
                        setContent(state, world, pos, Content.Nothing);
                        //Takes the glass bottle and gives a bottle with water.
                        if (!player.getAbilities().creativeMode) {
                            //The inventory is changed only if the player is not in creative mode.
                            handStack.decrement(1);
                            player.getInventory().offerOrDrop(PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.WATER));
                        }
                        player.playSound(getFillWaterSound(), SoundCategory.BLOCKS, 1f, 1f);
                    }

                    //Client: SUCCESS / Server: CONSUME
                    return ActionResult.success(world.isClient());
                }
            }
            case Salt -> {
                if (!world.isClient()) {
                    setContent(state, world, pos, Content.Nothing);
                    //Gives salt crystals.
                    dropStack(world, pos, new ItemStack(TerravibeItems.SALT_CRYSTALS));
                    player.playSound(getTakeSaltSound(), SoundCategory.BLOCKS, 1f, 1f);
                }

                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.PASS;
    }

    /**
     * Executed when something like rain drops on the block.
     * Randomly fills the block with water.
     */
    @Override
    public void precipitationTick(BlockState state, World world, BlockPos pos, Biome.Precipitation precipitation) {
        if (precipitation == Biome.Precipitation.RAIN && world.getRandom().nextFloat() < RAIN_FILLING_CHANCE) {
            setContent(state, world, pos, Content.Water);
        }
    }

    /** Gets a property indicating if the block reacts with the ticking system. */
    @Override
    public boolean hasRandomTicks(BlockState state) {
        return getContent(state) == Content.Water;
    }

    /** Executed every tick. */
    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, @NotNull ServerWorld world, @NotNull BlockPos pos, @NotNull Random random) {
        if (!world.hasRain(pos.up())) {
            int light = world.getLightLevel(LightType.SKY, pos);
            int lightBonus = (int)((MAX_EVAPORATION_TIME - MIN_EVAPORATION_TIME) * (light / 15d));
            int evaporationTime = MAX_EVAPORATION_TIME - lightBonus;
            int attempt = random.nextInt(evaporationTime + 1);
            if (attempt == 0) setContent(state, world, pos, Content.Salt);
        }
    }

    /** Gets the outline shape of the block. */
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VOXEL_SHAPE;
    }

    /** Gets the water color. */
    @SuppressWarnings("unused")
    public static int getWaterColor(BlockState blockState, BlockRenderView blockRenderView, BlockPos blockPos, int i) {
        if (i != 1) return -1;
        return BiomeColors.getWaterColor(blockRenderView, blockPos);
    }

    static {
        SETTINGS = FabricBlockSettings.of(Material.STONE, MapColor.BLACK)
                .breakInstantly()
                .sounds(BlockSoundGroup.STONE);
        CONTENT = EnumProperty.of("content", Content.class);
        VOXEL_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 4, 16);
    }


    /** Represents the content of the {@link TrayBlock}. */
    public enum Content implements StringIdentifiable {
        /** The {@link TrayBlock} contains nothing. */
        Nothing("nothing"),

        /** The {@link TrayBlock} contains water. */
        Water("water"),

        /** The {@link TrayBlock} contains salt. */
        Salt("salt");

        /** Name representing the value. */
        private final String name;

        /** Initializes a new {@link Content} with the name specified. */
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
