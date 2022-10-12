package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.AlternativeLootCondition;
import net.minecraft.loot.condition.LocationCheckLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.NumberRange.FloatRange;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import xyz.devpelux.terravibe.item.TerravibeItems;

/**
 * List of all the extra loot pools.
 */
public class TerravibeLootPools {
	private TerravibeLootPools() {
	}

	public static LootPool ancientSeedsFromDirtBlockPool() {
		LootPool.Builder pool = LootPool.builder();

		//Ancient nightshade fern seeds.
		LeafEntry.Builder<?> nightshadeFernSeeds = ItemEntry.builder(TerravibeItems.ANCIENT_NIGHTSHADE_FERN_SEEDS);
		nightshadeFernSeeds.conditionally(RandomChanceLootCondition.builder(0.003f));
		nightshadeFernSeeds.conditionally(AlternativeLootCondition.builder(
				LocationCheckLootCondition.builder(biomeFilter(BiomeKeys.OLD_GROWTH_PINE_TAIGA, FloatRange.between(55, 125))),
				LocationCheckLootCondition.builder(biomeFilter(BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA, FloatRange.between(55, 125)))
		));
		pool.with(nightshadeFernSeeds);

		return pool.build();
	}

	public static LootPool ancientSeedsFromPodzolBlockPool() {
		LootPool.Builder pool = LootPool.builder();

		//Ancient nightshade fern seeds.
		LeafEntry.Builder<?> nightshadeFernSeeds = ItemEntry.builder(TerravibeItems.ANCIENT_NIGHTSHADE_FERN_SEEDS);
		nightshadeFernSeeds.conditionally(RandomChanceLootCondition.builder(0.0003f));
		nightshadeFernSeeds.conditionally(LocationCheckLootCondition.builder(biomeFilter(BiomeKeys.OLD_GROWTH_PINE_TAIGA, FloatRange.between(55, 125))));
		pool.with(nightshadeFernSeeds);

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
		} else if (id.equals(Blocks.PODZOL.getLootTableId())) {
			tableBuilder.pool(ancientSeedsFromPodzolBlockPool());
		}
	}

	/**
	 * Generates a {@link LocationPredicate.Builder} to filter with the specified biome and y range.
	 */
	private static LocationPredicate.Builder biomeFilter(RegistryKey<Biome> biome, NumberRange.FloatRange y) {
		return new LocationPredicate.Builder().biome(biome).y(y);
	}
}
