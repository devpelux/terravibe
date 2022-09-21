package xyz.devpelux.terravibe.block;

import it.unimi.dsi.fastutil.Pair;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.block.container.ContainerBlock;
import xyz.devpelux.terravibe.blockentity.ContainerBlockEntity;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.item.ColoredItem;
import xyz.devpelux.terravibe.item.TerravibeItems;
import xyz.devpelux.terravibe.tags.TerravibeItemTags;

import java.util.HashMap;

/** Container for things. */
public class JarBlock extends ContainerBlock implements BlockColorProvider {
    /** Identifier of the block. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "jar");

    /** Settings of the block. */
    public static final Settings SETTINGS;

    /** Level of the content when full. */
    public static final int MAX_LEVEL = 3;

    /** Level of the content. */
    public static final IntProperty LEVEL;

    /** Specifies if the jar is closed. */
    public static final BooleanProperty CLOSED;

    /** Voxel shape of the block. */
    private static final VoxelShape VOXEL_SHAPE;

    /** Default content color. */
    private static final int DEFAULT_CONTENT_COLOR = 0x241a09;

    /** Default plug color. */
    private static final int DEFAULT_PLUG_COLOR = 0xb8945f;

    /** List of all the color providers to color the content. */
    private static final HashMap<String, ContainerBlockEntity.ContainerBlockColorProvider> COLOR_PROVIDERS = new HashMap<>();

    /** List of all the possible behaviors of the container. */
    private static final HashMap<Pair<String, Item>, ContainerBehavior> BEHAVIORS = new HashMap<>();

    /** Initializes a new {@link JarBlock}. */
    public JarBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(LEVEL, 0).with(CLOSED, false));
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

    /** Registers the properties of the block. */
    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(CLOSED);
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

    /** Gets the block state from nbt container data. */
    @Override
    public BlockState getStateFromContainerData(@NotNull NbtCompound nbt) {
        boolean closed = nbt.getBoolean("Closed");
        return super.getStateFromContainerData(nbt).with(CLOSED, closed);
    }

    /** Save extra container data to nbt. */
    @Override
    protected void saveToNbt(@NotNull BlockState state, @NotNull BlockView world, @NotNull BlockPos pos, @NotNull NbtCompound nbt) {
        super.saveToNbt(state, world, pos, nbt);
        nbt.putBoolean("Closed", state.get(CLOSED));
    }

    /** Gets the item of the filled closed jar. */
    public Item getClosedJarFilled() {
        return TerravibeItems.CLOSED_JAR_FILLED;
    }

    /** Gets the plug. */
    public static @Nullable Item getPlug(@NotNull ContainerBlockEntity container) {
        NbtCompound value = container.getValue("Plug");
        String plug = value.getString("Id");

        //If the plug exists then returns the item, else returns null.
        if (!plug.equals("")) {
            Item item = Registry.ITEM.get(new Identifier(plug));
            if (item != Items.AIR) {
                return item;
            }
        }

        return null;
    }

    /** Sets the plug. (null to remove) */
    public static void setPlug(@NotNull ContainerBlockEntity container, @Nullable Item plug) {
        if (plug != null) {
            //Sets the plug.
            NbtCompound value = new NbtCompound();
            value.putString("Id", Registry.ITEM.getId(plug).toString());
            container.setValue("Plug", value);
        }
        else {
            //Removes the plug.
            container.setValue("Plug", null);
        }
    }

    /**
     * Executed when the block entity is used.
     * Inserts or extracts the content, closes or opens the jar.
     */
    @Override
    public ActionResult onUseBlockEntity(BlockState state, World world, BlockPos pos, PlayerEntity player, ContainerBlockEntity container) {
        ItemStack inHand = player.getStackInHand(Hand.MAIN_HAND);
        boolean isClosed = state.get(CLOSED);
        if (isClosed) {
            if (inHand.isEmpty()) {
                if (!world.isClient()) {
                    //Opens the jar.
                    world.setBlockState(pos, state.with(CLOSED, false));
                    Item plug = getPlug(container);
                    setPlug(container, null);

                    //Drops the button (only if the player is not in creative).
                    if (!player.getAbilities().creativeMode && plug != null) {
                        player.getInventory().offerOrDrop(new ItemStack(plug));
                    }
                }

                //Client: SUCCESS / Server: CONSUME
                return ActionResult.success(world.isClient());
            }

            return ActionResult.PASS;
        }
        else {
            if (inHand.isIn(TerravibeItemTags.JAR_PLUGS)) {
                if (!world.isClient()) {
                    //Closes the jar.
                    world.setBlockState(pos, state.with(CLOSED, true));
                    setPlug(container, inHand.getItem());

                    //Consumes the hand stack (only if the player is not in creative).
                    if (!player.getAbilities().creativeMode) {
                        inHand.decrement(1);
                    }
                }

                return ActionResult.success(world.isClient());
            }

            return super.onUseBlockEntity(state, world, pos, player, container);
        }
    }

    /**
     * Executed at the block breaking.
     * Drops a jar with the content if it is closed.
     */
    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        if (!state.get(CLOSED)) {
            onBreakOpened(world, pos, state, player);
        }
        else {
            //Drops a jar with the content data in server world, if the player is not in creative mode.
            if (!world.isClient() && !player.getAbilities().creativeMode) {
                NbtCompound nbt = getContainerData(state, world, pos);
                //Check if the content data is present, just in case.
                if (nbt.contains("ContentData", NbtElement.COMPOUND_TYPE)) {
                    Item jar = getLevel(state) > 0 ? getClosedJarFilled() : TerravibeItems.CLOSED_JAR_EMPTY;
                    ItemStack stack = new ItemStack(jar, 1);
                    stack.setNbt(nbt);
                    dropStack(world, pos, stack);
                }
            }
        }
    }

    /** Executed at the block breaking when opened. */
    public void onBreakOpened(World world, BlockPos pos, BlockState state, PlayerEntity player) { }

    /** Gets the outline shape of the block. */
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VOXEL_SHAPE;
    }

    /** Gets the colors of the block. */
    @Override
    public int getColor(BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos, int tintIndex) {
        if (tintIndex == 1) {
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
        else if (tintIndex == 2) {
            //Gets the plug.
            ContainerBlockEntity container = getContainerEntity(world, pos);
            Item plug = container != null ? getPlug(container) : null;

            //Gets the plug color.
            return plug instanceof ColoredItem coloredPlug ? coloredPlug.getColor(null, 1) : DEFAULT_PLUG_COLOR;
        }

        return -1;
    }

    static {
        SETTINGS = FabricBlockSettings.copyOf(Blocks.FLOWER_POT);
        LEVEL = IntProperty.of("level", 0, MAX_LEVEL);
        CLOSED = BooleanProperty.of("closed");
        VOXEL_SHAPE = Block.createCuboidShape(5, 0, 5, 11, 9, 11);
    }
}
