package xyz.devpelux.terravibe.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.core.Util;

/** Block entity that contains a single stack. */
public class ContainerBlockEntity extends BlockEntity {
    /** Identifier of the block entity. */
    public static Identifier ID = new Identifier(ModInfo.MOD_ID, "container");

    private ItemStack contained = ItemStack.EMPTY;
    private NbtCompound nbt = new NbtCompound();

    /** Initializes a new {@link ContainerBlockEntity}. */
    public ContainerBlockEntity(BlockPos pos, BlockState state) {
        super(TerravibeBlockEntityTypes.CONTAINER, pos, state);
    }

    /** Gets the contained stack. */
    public @NotNull ItemStack getContained() {
        return contained;
    }

    /** Sets the contained stack. */
    public void setContained(@NotNull ItemStack stack) {
        contained = stack;
        markDirty();
    }

    /** Gets a custom nbt data. */
    @Nullable
    public NbtElement getNbt(String key) {
        return nbt.get(key);
    }

    /** Puts a custom nbt data. */
    public void putNbt(String key, NbtElement value) {
        nbt.put(key, value);
        markDirty();
    }

    /** Removes a custom nbt data. */
    public void removeNbt(String key) {
        nbt.remove(key);
        markDirty();
    }

    /** Reads from a web packet. */
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        setContained(Util.getStackFromNbt(nbt));
        this.nbt = nbt.getCompound("ContainerData");
    }

    /** Writes into a web packet. */
    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Util.putStackToNbt(nbt, getContained());
        nbt.put("ContainerData", this.nbt);
    }

    /** Creates a web packet to sync with the client. */
    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    /** Creates the initial web packet to sync with the client. */
    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    /**
     * Executed when the contained stack is changed.
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
}
