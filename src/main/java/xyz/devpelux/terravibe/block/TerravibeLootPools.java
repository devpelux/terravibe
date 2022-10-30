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
import net.minecraft.loot.entry.AlternativeEntry;
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

import java.util.ArrayList;
import java.util.List;

/**
 * List of all the extra loot pools added to existing blocks.
 */
public class TerravibeLootPools {
	private TerravibeLootPools() {
	}

	/**
	 * Gets all the pools added to dirt block.
	 */
	public static List<LootPool> dirt() {
		List<LootPool> pools = new ArrayList<>();

		//Ancient nightshade fern seeds at height 55 <-> 125.
		LeafEntry.Builder<?> nightshadeFernSeeds = ItemEntry.builder(TerravibeItems.ANCIENT_NIGHTSHADE_FERN_SEEDS);
		nightshadeFernSeeds.conditionally(RandomChanceLootCondition.builder(0.005f));
		nightshadeFernSeeds.conditionally(AlternativeLootCondition.builder(
				LocationCheckLootCondition.builder(biomeAndY(BiomeKeys.OLD_GROWTH_PINE_TAIGA, FloatRange.between(55, 125))),
				LocationCheckLootCondition.builder(biomeAndY(BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA, FloatRange.between(55, 125)))
		));
		pools.add(LootPool.builder().with(nightshadeFernSeeds).build());

		return pools;
	}

	/**
	 * Gets all the pools added to gravel block.
	 */
	public static List<LootPool> gravel() {
		List<LootPool> pools = new ArrayList<>();

		//Ancient gillyweed seeds at height 22 <-> 54.
		LeafEntry.Builder<?> gillyweedSeedsH1 = ItemEntry.builder(TerravibeItems.ANCIENT_GILLYWEED_SEEDS);
		gillyweedSeedsH1.conditionally(RandomChanceLootCondition.builder(0.002f));
		gillyweedSeedsH1.conditionally(AlternativeLootCondition.builder(
				LocationCheckLootCondition.builder(biomeAndY(BiomeKeys.OCEAN, FloatRange.between(22, 54))),
				LocationCheckLootCondition.builder(biomeAndY(BiomeKeys.DEEP_OCEAN, FloatRange.between(22, 54))),
				LocationCheckLootCondition.builder(biomeAndY(BiomeKeys.COLD_OCEAN, FloatRange.between(22, 54))),
				LocationCheckLootCondition.builder(biomeAndY(BiomeKeys.DEEP_COLD_OCEAN, FloatRange.between(22, 54)))
		));

		//Ancient gillyweed seeds at height 24 <-> 42.
		LeafEntry.Builder<?> gillyweedSeedsH2 = ItemEntry.builder(TerravibeItems.ANCIENT_GILLYWEED_SEEDS);
		gillyweedSeedsH2.conditionally(RandomChanceLootCondition.builder(0.008f)); //Total: 0,009984
		gillyweedSeedsH2.conditionally(AlternativeLootCondition.builder(
				LocationCheckLootCondition.builder(biomeAndY(BiomeKeys.OCEAN, FloatRange.between(24, 42))),
				LocationCheckLootCondition.builder(biomeAndY(BiomeKeys.DEEP_OCEAN, FloatRange.between(24, 42))),
				LocationCheckLootCondition.builder(biomeAndY(BiomeKeys.COLD_OCEAN, FloatRange.between(24, 42))),
				LocationCheckLootCondition.builder(biomeAndY(BiomeKeys.DEEP_COLD_OCEAN, FloatRange.between(24, 42)))
		));
		pools.add(LootPool.builder().with(AlternativeEntry.builder(gillyweedSeedsH1, gillyweedSeedsH2)).build());

		return pools;
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
		if (id.equals(Blocks.DIRT.getLootTableId())) tableBuilder.pools(dirt());
		else if (id.equals(Blocks.GRAVEL.getLootTableId())) tableBuilder.pools(gravel());
	}

	/**
	 * Generates a {@link LocationPredicate.Builder} to filter with the specified biome and y range.
	 */
	private static LocationPredicate.Builder biomeAndY(RegistryKey<Biome> biome, NumberRange.FloatRange y) {
		return new LocationPredicate.Builder().biome(biome).y(y);
	}
}
