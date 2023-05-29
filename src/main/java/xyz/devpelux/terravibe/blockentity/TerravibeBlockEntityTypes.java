package xyz.devpelux.terravibe.blockentity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import xyz.devpelux.terravibe.block.TerravibeBlocks;
import xyz.devpelux.terravibe.core.Terravibe;

/**
 * List of all the block entity types.
 */
public final class TerravibeBlockEntityTypes {
	/**
	 * Block entity type for containers.
	 */
	public static final BlockEntityType<ContainerBlockEntity> CONTAINER;

	/**
	 * Block entity type for the shredder block.
	 */
	public static final BlockEntityType<ShredderBlockEntity> SHREDDER;

	private TerravibeBlockEntityTypes() {
	}

	/**
	 * Loads all the block entity types.
	 */
	public static void load() {
	}

	/**
	 * Registers the specified block entity type with the specified id.
	 */
	private static <B extends BlockEntity, T extends BlockEntityType<B>> T register(String id, T blockEntity) {
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, Terravibe.identified(id), blockEntity);
	}

	static {
		CONTAINER = register("container", FabricBlockEntityTypeBuilder.create(ContainerBlockEntity::new,
				TerravibeBlocks.TUN, TerravibeBlocks.JAR).build());
		SHREDDER = register("shredder", FabricBlockEntityTypeBuilder.create(ShredderBlockEntity::new,
				TerravibeBlocks.SHREDDER).build());
	}
}
