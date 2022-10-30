package xyz.devpelux.terravibe.core;

import it.unimi.dsi.fastutil.Pair;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Registry for logs that will drop cork when stripped, with the type of cork dropped.
 */
public final class CorkStrippableBlockRegistry {
	private static boolean enabled = false;

	/**
	 * Singleton instance.
	 */
	private static final CorkStrippableBlockRegistry INSTANCE = new CorkStrippableBlockRegistry();

	/**
	 * List of blocks with the type of cork dropped.
	 */
	private final Map<Block, Pair<Item, Float>> CORK_STRIPPABLE = new IdentityHashMap<>();

	private CorkStrippableBlockRegistry() {
	}

	/**
	 * Registers a cork for a block with a drop chance.
	 * Is supported only one drop type per block.
	 * Duplicated registrations will override the previous ones.
	 */
	public static void register(Block log, Item cork, float chance) {
		INSTANCE.CORK_STRIPPABLE.putIfAbsent(log, Pair.of(cork, chance));

		//Enables the use-block event handler.
		if (!enabled) {
			UseBlockCallback.EVENT.register(CorkStrippableBlockRegistry::corkStrippingHandler);
			enabled = true;
		}
	}

	/**
	 * Gets the droppable cork from the block with his drop chance.
	 * Returns null if the block does not drop any cork.
	 */
	@Nullable
	public static Pair<Item, Float> getCorkDrop(Block block) {
		return INSTANCE.CORK_STRIPPABLE.get(block);
	}

	/**
	 * Handles the drop mechanic from the registered blocks.
	 */
	private static ActionResult corkStrippingHandler(PlayerEntity player, World world, Hand hand, BlockHitResult hit) {
		//Adds a random chance to drop cork from the supported wood types.
		//Only for axes.
		if (!player.isSpectator() && player.getStackInHand(hand).isIn(ConventionalItemTags.AXES)) {
			BlockPos pos = hit.getBlockPos();
			Block block = world.getBlockState(pos).getBlock();
			Pair<Item, Float> corkDrop = getCorkDrop(block);

			if (corkDrop != null) {
				//Gets the cork drop params.
				Item cork = corkDrop.first();
				float dropChance = corkDrop.second();

				//If success, drops the cork.
				if (world.getRandom().nextFloat() < dropChance) {
					Block.dropStack(world, pos, new ItemStack(cork));
				}
			}
		}

		return ActionResult.PASS;
	}
}
