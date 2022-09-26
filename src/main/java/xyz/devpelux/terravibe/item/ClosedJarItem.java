package xyz.devpelux.terravibe.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.block.ContainerBlock;
import xyz.devpelux.terravibe.blockentity.ContainerBlockEntity;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/** Jar with plug and content. */
public class ClosedJarItem extends Item implements ItemColorProvider {
    public static final String CONTENT_WATER = "minecraft:water";
    public static final String CONTENT_HONEY = "minecraft:honey";
    public static final String CONTENT_MILK = "minecraft:milk";
    public static final String CONTENT_TOMATO_SAUCE = "terravibe:tomato_sauce";
    public static final String CONTENT_BIRCH_MOLD_DUST = "terravibe:birch_mold_dust";
    public static final String CONTENT_DARK_MOLD_DUST = "terravibe:dark_mold_dust";
    public static final String CONTENT_GLOWING_DARK_MOLD_DUST = "terravibe:glowing_dark_mold_dust";
    public static final String CONTENT_BURNED_BIRCH_MOLD_DUST = "terravibe:burned_birch_mold_dust";
    public static final String CONTENT_BURNED_DARK_MOLD_DUST = "terravibe:burned_dark_mold_dust";
    public static final String CONTENT_BURNED_GLOWING_DARK_MOLD_DUST = "terravibe:burned_glowing_dark_mold_dust";

    /** Default content color. */
    private static final int DEFAULT_CONTENT_COLOR = 0x241a09;

    /** Default plug color. */
    private static final int DEFAULT_PLUG_COLOR = 0xb8945f;

    /** Tooltip for unknown content. */
    private static final Text UNKNOWN_CONTENT = Text.translatable("item.terravibe.jar.unknown_content.tooltip");

    /** List of all the color providers to color the content. */
    private static final HashMap<String, ItemColorProvider> COLOR_PROVIDERS = new HashMap<>();

    /** List of all the tooltip providers to display the content. */
    private static final HashMap<String, Function<ItemStack, MutableText>> TOOLTIP_PROVIDERS = new HashMap<>();

    /** Initializes a new {@link ClosedJarItem}. */
    public ClosedJarItem(Settings settings) {
        super(settings);
    }

    /** Executed when the item is used on block. */
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ItemPlacementContext ctx = new ItemPlacementContext(context);

        if (ctx.canPlace()) {
            //If is possible to place a block in the current position, gets the block id from the stack.
            ItemStack stack = ctx.getStack();
            NbtCompound nbt = stack.getOrCreateNbt();
            String blockId = nbt.getString("Block");

            if (!blockId.equals("")) {
                //If the block id is valid, gets the block.
                Block block = Registry.BLOCK.get(new Identifier(blockId));

                if (block instanceof ContainerBlock container) {
                    //If the block exists and is a container, then gets the correct block state and tries to place it.
                    World world = ctx.getWorld();
                    BlockPos pos = ctx.getBlockPos();
                    BlockState state = container.getStateFromContainerData(nbt);
                    PlayerEntity player = ctx.getPlayer();
                    if (state.canPlaceAt(world, pos) && world.setBlockState(pos, state, 11)) {
                        //If the block has been placed correctly, setups final things.

                        //Restores the block entity data.
                        ContainerBlockEntity containerEntity = ContainerBlock.getContainerEntity(world, pos);
                        if (containerEntity != null) {
                            //Sets the content.
                            NbtCompound content = nbt.getCompound("ContentData").copy();
                            containerEntity.setContent(content);
                        }

                        //Calls the block placed event.
                        state.getBlock().onPlaced(world, pos, state, player, stack);

                        //Increases the blocks placed counter.
                        if (player instanceof ServerPlayerEntity serverPlayer) {
                            Criteria.PLACED_BLOCK.trigger(serverPlayer, pos, stack);
                        }

                        //Plays the placed sound.
                        BlockSoundGroup sound = state.getSoundGroup();
                        world.playSound(player, pos, sound.getPlaceSound(), SoundCategory.BLOCKS,
                                (sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F);

                        //Emits the placed game event.
                        world.emitGameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Emitter.of(player, state));

                        //If the player is not in creative mode, consumes the stack.
                        if (player == null || !player.getAbilities().creativeMode) {
                            stack.decrement(1);
                        }

                        return ActionResult.success(world.isClient());
                    }
                }
            }
        }

        return ActionResult.FAIL;
    }

    /** Gets the color of the item. */
    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        if (tintIndex == 1) {
            String content = getContent(stack);
            ItemColorProvider colorProvider = getColorProvider(content);
            return colorProvider != null ? colorProvider.getColor(stack, tintIndex) : DEFAULT_CONTENT_COLOR;
        }
        else if (tintIndex == 2) {
            //Gets the plug.
            Item plug = getPlug(stack);

            //Gets the plug color.
            return plug instanceof ColoredItem coloredPlug ? coloredPlug.getColor(null, 1) : DEFAULT_PLUG_COLOR;
        }

        return -1;
    }

    /** Adds a tooltip that shows the current content of the stack. */
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        int level = stack.getOrCreateNbt().getInt("Level");
        if (level > 0) {
            //Displays the content, with the current level, if something is contained.
            MutableText levelTooltip = Text.literal(level + " ");
            MutableText contentTooltip = getTooltip(getContent(stack), stack, UNKNOWN_CONTENT);

            tooltip.add(levelTooltip.append(contentTooltip).formatted(Formatting.GRAY));
        }
    }

    /** Gets the content from the stack. */
    public static String getContent(@NotNull ItemStack stack) {
        return stack.getOrCreateNbt().getCompound("ContentData").getCompound("Content").getString("Id");
    }

    /** Gets the plug from the stack. */
    public static @Nullable Item getPlug(@NotNull ItemStack stack) {
        String plugId = stack.getOrCreateNbt().getCompound("ContentData").getCompound("Plug").getString("Id");

        //If the plug exists then returns the item, else returns null.
        if (!plugId.equals("")) {
            Item plug = Registry.ITEM.get(new Identifier(plugId));
            if (plug != Items.AIR) {
                return plug;
            }
        }

        return null;
    }

    /** Gets the potion color from the stack. */
    public static int getPotionColor(ItemStack stack) {
        Potion potion = PotionUtil.getPotion(stack.getOrCreateNbt().getCompound("ContentData").getCompound("PotionData"));
        return PotionUtil.getColor(potion);
    }

    /** Gets the tooltip from the content of the stack. */
    public static MutableText getTooltipKeyFromContent(ItemStack stack) {
        String content = getContent(stack).replace(':', '.');
        return content.equals("") ? UNKNOWN_CONTENT.copy() : Text.translatable("item.terravibe.jar." + content + ".tooltip");
    }

    /** Gets the tooltip from the water/potion content of the stack. */
    public static MutableText getTooltipKeyFromPotion(ItemStack stack) {
        Potion potion = PotionUtil.getPotion(stack.getOrCreateNbt().getCompound("ContentData").getCompound("PotionData"));
        if (potion != Potions.WATER) {
            return Text.translatable(potion.finishTranslationKey(Items.POTION.getTranslationKey() + ".effect."));
        }
        return Text.translatable("item.terravibe.jar.minecraft.water.tooltip");
    }

    /** Registers a color provider for the content specified. */
    public static void registerColorProvider(@NotNull String content, @NotNull ItemColorProvider colorProvider) {
        COLOR_PROVIDERS.putIfAbsent(content, colorProvider);
    }

    /** Gets the color provider for the content specified. */
    public static @Nullable ItemColorProvider getColorProvider(@NotNull String content) {
        return COLOR_PROVIDERS.get(content);
    }

    /** Registers a tooltip provider for the content specified. */
    public static void registerTooltipProvider(@NotNull String content, @NotNull Function<ItemStack, MutableText> tooltipProvider) {
        TOOLTIP_PROVIDERS.putIfAbsent(content, tooltipProvider);
    }

    /** Gets the tooltip provider for the content specified. */
    public static @Nullable Function<ItemStack, MutableText> getTooltipProvider(@NotNull String content, @NotNull ItemStack stack) {
        return TOOLTIP_PROVIDERS.get(content);
    }

    /** Gets the tooltip for the content specified, specifying a default value, if the content is unknown. */
    public static MutableText getTooltip(@NotNull String content, @NotNull ItemStack stack, @NotNull Text unknown) {
        Function<ItemStack, MutableText> provider = getTooltipProvider(getContent(stack), stack);
        return provider != null ? provider.apply(stack).copy() : unknown.copy();
    }

    static {
        //Color providers.
        registerColorProvider(CONTENT_WATER, (s, i) -> getPotionColor(s));
        registerColorProvider(CONTENT_MILK, (s, i) -> 0xffffff);
        registerColorProvider(CONTENT_HONEY, (s, i) -> 0x976018);
        registerColorProvider(CONTENT_TOMATO_SAUCE, (s, i) -> 0xf61815);
        registerColorProvider(CONTENT_BIRCH_MOLD_DUST, (s, i) -> 0xb3af9c);
        registerColorProvider(CONTENT_DARK_MOLD_DUST, (s, i) -> 0x273832);
        registerColorProvider(CONTENT_GLOWING_DARK_MOLD_DUST, (s, i) -> 0x253535);
        registerColorProvider(CONTENT_BURNED_BIRCH_MOLD_DUST, (s, i) -> 0xbea894);
        registerColorProvider(CONTENT_BURNED_DARK_MOLD_DUST, (s, i) -> 0x30322c);
        registerColorProvider(CONTENT_BURNED_GLOWING_DARK_MOLD_DUST, (s, i) -> 0x2f2c2c);

        //Tooltip key providers.
        registerTooltipProvider(CONTENT_WATER, ClosedJarItem::getTooltipKeyFromPotion);
        registerTooltipProvider(CONTENT_MILK, ClosedJarItem::getTooltipKeyFromContent);
        registerTooltipProvider(CONTENT_HONEY, ClosedJarItem::getTooltipKeyFromContent);
        registerTooltipProvider(CONTENT_TOMATO_SAUCE, ClosedJarItem::getTooltipKeyFromContent);
        registerTooltipProvider(CONTENT_BIRCH_MOLD_DUST, ClosedJarItem::getTooltipKeyFromContent);
        registerTooltipProvider(CONTENT_DARK_MOLD_DUST, ClosedJarItem::getTooltipKeyFromContent);
        registerTooltipProvider(CONTENT_GLOWING_DARK_MOLD_DUST, ClosedJarItem::getTooltipKeyFromContent);
        registerTooltipProvider(CONTENT_BURNED_BIRCH_MOLD_DUST, ClosedJarItem::getTooltipKeyFromContent);
        registerTooltipProvider(CONTENT_BURNED_DARK_MOLD_DUST, ClosedJarItem::getTooltipKeyFromContent);
        registerTooltipProvider(CONTENT_BURNED_GLOWING_DARK_MOLD_DUST, ClosedJarItem::getTooltipKeyFromContent);
    }
}
