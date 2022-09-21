package xyz.devpelux.terravibe.blockentity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.core.ModInfo;

import java.util.Objects;

/** Block entity for containers. */
public class ContainerBlockEntity extends BlockEntity {
    /** Identifier of the block entity. */
    public static final Identifier ID = new Identifier(ModInfo.MOD_ID, "container");

    /** Content data. */
    @NotNull
    private NbtCompound content = new NbtCompound();

    /** Initializes a new {@link ContainerBlockEntity}. */
    public ContainerBlockEntity(BlockPos pos, BlockState state) {
        super(TerravibeBlockEntityTypes.CONTAINER, pos, state);
    }

    /** Gets the specified value. */
    public NbtCompound getValue(String key) {
        return content.getCompound(key);
    }

    /**
     * Sets the specified value.
     * Pass null to remove the value.
     */
    public void setValue(String key, @Nullable NbtCompound value) {
        if (value != null) {
            content.put(key, value);
        }
        else {
            content.remove(key);
        }
        markDirty();
    }

    /** Gets the content data. */
    public @NotNull NbtCompound getContent() {
        return content;
    }

    /** Sets the content data. */
    public void setContent(@NotNull NbtCompound content) {
        this.content = Objects.requireNonNull(content);
        markDirty();
    }

    /** Reads from saved data. */
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        content = nbt.getCompound("Content");
    }

    /** Saves data. */
    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.put("Content", content);
    }

    /** Creates a packet to enable client-server synchronization. */
    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    /** Creates an initial nbt to sync saved data with the client. */
    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    /**
     * Executed when the content is saved or loaded.
     * Syncs with the client.
     */
    @Override
    public void markDirty() {
        if (world != null) {
            if (world.isClient()) {
                MinecraftClient.getInstance().worldRenderer.scheduleBlockRenders(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ());
            } else {
                ((ServerWorld) world).getChunkManager().markForUpdate(pos);
            }
            super.markDirty();
        }
    }


    /** Block color provider with an extra container block entity as parameter. */
    @Environment(EnvType.CLIENT)
    @FunctionalInterface
    public interface ContainerBlockColorProvider {
        /** Gets the color. */
        int getColor(ContainerBlockEntity container, BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos, int tintIndex);
    }
}
