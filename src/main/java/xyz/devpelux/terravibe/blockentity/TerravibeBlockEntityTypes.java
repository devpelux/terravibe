package xyz.devpelux.terravibe.blockentity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.devpelux.terravibe.block.TerravibeBlocks;

/** List of all the block entity types. */
public class TerravibeBlockEntityTypes {
    private TerravibeBlockEntityTypes() {}

    /** Block entity type for the shredder block. */
    public static final BlockEntityType<ShredderBlockEntity> SHREDDER;

    /** Block entity type for containers. */
    public static final BlockEntityType<ContainerBlockEntity> CONTAINER;

    /** Loads all the block entity types. */
    public static void load() {}

    /** Registers the specified block entity type with the specified id. */
    private static <B extends BlockEntity, T extends BlockEntityType<B>> T register(Identifier id, T blockEntity) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, id, blockEntity);
    }

    static {
        SHREDDER = register(ShredderBlockEntity.ID, FabricBlockEntityTypeBuilder.create(ShredderBlockEntity::new,
                TerravibeBlocks.SHREDDER).build());
        CONTAINER = register(ContainerBlockEntity.ID, FabricBlockEntityTypeBuilder.create(ContainerBlockEntity::new,
                TerravibeBlocks.TUN, TerravibeBlocks.JAR).build());
    }
}
