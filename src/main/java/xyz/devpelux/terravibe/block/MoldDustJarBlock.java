package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.core.Util;
import xyz.devpelux.terravibe.item.TerravibeItems;

/** A jar for mold dusts. */
public class MoldDustJarBlock extends JarBlock {
    /** Identifier of the block. */
    public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "mold_dust_jar");

    /** Settings of the block. */
    public static final Settings SETTINGS;

    /** Dust contained in the block. */
    public static final EnumProperty<Dust> DUST;

    /** Initializes a new {@link MoldDustJarBlock}. */
    public MoldDustJarBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(LEVEL, 0).with(CLOSED, false).with(DUST, Dust.Unspecified));
    }

    /** Registers the properties of the block. */
    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(DUST);
    }

    /** Gets the default state with the specified dust. */
    public static BlockState withDust(Dust dust) {
        return TerravibeBlocks.MOLD_DUST_JAR.getDefaultState().with(DUST, dust);
    }

    /**
     * Executed at the block breaking.
     * Drops the contained.
     */
    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        //Drops the contained in server world, if the player is not in creative mode.
        if (!world.isClient() && !player.getAbilities().creativeMode) {
            ItemStack contained = getContained(world, pos);
            if (!contained.isEmpty()) {
                dropStack(world, pos, Util.copyStack(contained, getLevel(world, pos)));
            }
        }
    }

    static {
        SETTINGS = FabricBlockSettings.copyOf(TerravibeBlocks.JAR);
        DUST = EnumProperty.of("dust", Dust.class);
    }


    /** Represents the dust type contained in a {@link MoldDustJarBlock}. */
    public enum Dust implements StringIdentifiable {
        /** The block contains unspecified dust. */
        Unspecified("unspecified"),

        /** The block contains birch mold dust. */
        BirchMoldDust("birch_mold_dust"),

        /** The block contains dark mold dust. */
        DarkMoldDust("dark_mold_dust"),

        /** The block contains glowing dark mold dust. */
        GlowingDarkMoldDust("glowing_dark_mold_dust");

        /** Name representing the value. */
        private final String name;

        /** Initializes a new value with the name specified. */
        Dust(String name) {
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

        /** Gets the dust type of the corresponding item. */
        public static Dust fromItem(Item item) {
            if (item == TerravibeItems.BIRCH_MOLD_DUST) return BirchMoldDust;
            else if (item == TerravibeItems.DARK_MOLD_DUST) return DarkMoldDust;
            else if (item == TerravibeItems.GLOWING_DARK_MOLD_DUST) return GlowingDarkMoldDust;
            else return Unspecified;
        }
    }
}
