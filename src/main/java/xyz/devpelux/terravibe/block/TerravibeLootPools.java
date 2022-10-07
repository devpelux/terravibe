package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.SetNbtLootFunction;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtInt;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import xyz.devpelux.terravibe.item.TerravibeItems;

/**
 * List of all the extra loot pools.
 */
public class TerravibeLootPools {
	private TerravibeLootPools() {
	}

	public static LootPool ancientSeedsFromDirtBlockPool() {
		LootPool.Builder pool = LootPool.builder();

		//Ancient nightshade fern seed with dirty value of 1.
		LeafEntry.Builder<?> ancientNightshadeFernSeedsD1 = ItemEntry.builder(TerravibeItems.ANCIENT_NIGHTSHADE_FERN_SEEDS);
		ancientNightshadeFernSeedsD1.apply(SetNbtLootFunction.builder(singleValueNbt("Dirty", NbtInt.of(1))));
		ancientNightshadeFernSeedsD1.conditionally(RandomChanceLootCondition.builder(0.0003f));
		pool.with(ancientNightshadeFernSeedsD1);

		//Ancient nightshade fern seed with dirty value of 2.
		LeafEntry.Builder<?> ancientNightshadeFernSeedsD2 = ItemEntry.builder(TerravibeItems.ANCIENT_NIGHTSHADE_FERN_SEEDS);
		ancientNightshadeFernSeedsD2.apply(SetNbtLootFunction.builder(singleValueNbt("Dirty", NbtInt.of(2))));
		ancientNightshadeFernSeedsD2.conditionally(RandomChanceLootCondition.builder(0.003f));
		pool.with(ancientNightshadeFernSeedsD2);

		return pool.build();
	}

	public static LootPool ancientSeedsFromGrassBlockPool() {
		LootPool.Builder pool = LootPool.builder();

		//Ancient nightshade fern seed with dirty value of 1.
		LeafEntry.Builder<?> ancientNightshadeFernSeedsD1 = ItemEntry.builder(TerravibeItems.ANCIENT_NIGHTSHADE_FERN_SEEDS);
		ancientNightshadeFernSeedsD1.apply(SetNbtLootFunction.builder(singleValueNbt("Dirty", NbtInt.of(1))));
		ancientNightshadeFernSeedsD1.conditionally(RandomChanceLootCondition.builder(0.0003f));
		pool.with(ancientNightshadeFernSeedsD1);

		return pool.build();
	}

	/**
	 * Loads the loot table event listeners that will add the pools.
	 */
	public static void load() {
		LootTableEvents.MODIFY.register(TerravibeLootPools::lootTableModifier);
	}

	/**
	 * Executed when the loot tables are ready to be modified.
	 */
	private static void lootTableModifier(ResourceManager resourceManager, LootManager lootManager, Identifier id,
	                                      LootTable.Builder tableBuilder, LootTableSource source) {
		if (id.equals(Blocks.DIRT.getLootTableId())) {
			tableBuilder.pool(ancientSeedsFromDirtBlockPool());
		} else if (id.equals(Blocks.GRASS_BLOCK.getLootTableId())) {
			tableBuilder.pool(ancientSeedsFromGrassBlockPool());
		}
	}

	/**
	 * Generates a nbt with a single element.
	 */
	private static NbtCompound singleValueNbt(String key, NbtElement value) {
		NbtCompound nbt = new NbtCompound();
		nbt.put(key, value);
		return nbt;
	}
}
