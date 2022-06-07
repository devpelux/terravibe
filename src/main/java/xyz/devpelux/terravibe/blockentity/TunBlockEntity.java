package xyz.devpelux.terravibe.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.block.TunBlock;

/** Block entity for the {@link TunBlock}. */
public class TunBlockEntity extends BlockEntity {
    private Item contained = null;

    /** Initializes a new {@link TunBlockEntity}. */
    public TunBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityList.TUN, pos, state);
    }

    /** Gets a value indicating if the contained item is not null. */
    public boolean hasContained() {
        return contained != null;
    }

    /** Gets the contained item. */
    public @Nullable Item getContained() {
        return contained;
    }

    /** Sets the contained item. */
    public void setContained(@Nullable Item item) {
        contained = item;
        markDirty();
    }

    /** Reads from a web packet. */
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);
        Inventories.readNbt(nbt, items);
        ItemStack stack = items.get(0);
        setContained(stack.isEmpty() ? null : stack.getItem());
    }

    /** Writes into a web packet. */
    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, DefaultedList.copyOf(ItemStack.EMPTY, new ItemStack(getContained())));
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
     * Executed when the contained item is changed.
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
