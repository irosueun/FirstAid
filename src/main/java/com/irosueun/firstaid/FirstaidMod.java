package com.irosueun.firstaid;

import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.BiomePlacementModifier;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier;
import net.minecraft.world.gen.decorator.InSquarePlacementModifier;
import net.minecraft.world.gen.feature.*;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors;
import org.quiltmc.qsl.worldgen.biome.api.ModificationPhase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import static net.minecraft.world.gen.feature.OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES;
import static net.minecraft.world.gen.feature.OreConfiguredFeatures.STONE_ORE_REPLACEABLES;

public class FirstaidMod implements ModInitializer {
	public static final String MODID = "firstaid";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static final Block HEART_CRYSTAL_SHARD_ORE = new ExperienceDroppingBlock(QuiltBlockSettings.of(Material.STONE).strength(3.0F, 3.0F).requiresTool(), UniformIntProvider.create(10, 15));
	public static final Block DEEPSLATE_HEART_CRYSTAL_SHARD_ORE = new ExperienceDroppingBlock(QuiltBlockSettings.of(Material.STONE).strength(4.5F, 3.0F).requiresTool(), UniformIntProvider.create(10, 15));

	public static final ConfiguredFeature<?, ?> CONFIGURED_HEART_CRYSTAL_SHARD_FEATURE = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(MODID, "heart_crystal_shard_configured"), new ConfiguredFeature(Feature.ORE, new OreFeatureConfig(List.of(OreFeatureConfig.createTarget(DEEPSLATE_ORE_REPLACEABLES, DEEPSLATE_HEART_CRYSTAL_SHARD_ORE.getDefaultState()), OreFeatureConfig.createTarget(STONE_ORE_REPLACEABLES, HEART_CRYSTAL_SHARD_ORE.getDefaultState())), 1)));
	public static final PlacedFeature PLACED_HEART_CRYSTAL_SHARD_FEATURE = new PlacedFeature(Holder.createDirect(CONFIGURED_HEART_CRYSTAL_SHARD_FEATURE), List.of(CountPlacementModifier.create(20), InSquarePlacementModifier.getInstance(), HeightRangePlacementModifier.createUniform(YOffset.getBottom(), YOffset.fixed(64)), BiomePlacementModifier.getInstance()));

	public static final Item HEART_CRYSTAL_SHARD = new Item(new QuiltItemSettings().group(ItemGroup.MISC));

	public static final Item HEART_CRYSTAL = new Item(new QuiltItemSettings().group(ItemGroup.MISC));

	@Override
	public void onInitialize(ModContainer mod) {

		Registry.register(Registry.BLOCK, new Identifier(MODID, "heart_crystal_shard_ore"), HEART_CRYSTAL_SHARD_ORE);
		Registry.register(Registry.ITEM, new Identifier(MODID, "heart_crystal_shard_ore"), new BlockItem(HEART_CRYSTAL_SHARD_ORE, new QuiltItemSettings().group(ItemGroup.BUILDING_BLOCKS)));

		Registry.register(Registry.BLOCK, new Identifier(MODID, "deepslate_heart_crystal_shard_ore"), DEEPSLATE_HEART_CRYSTAL_SHARD_ORE);
		Registry.register(Registry.ITEM, new Identifier(MODID, "deepslate_heart_crystal_shard_ore"), new BlockItem(DEEPSLATE_HEART_CRYSTAL_SHARD_ORE, new QuiltItemSettings().group(ItemGroup.BUILDING_BLOCKS)));

		Registry.register(Registry.ITEM, new Identifier(MODID, "heart_crystal_shard"), HEART_CRYSTAL_SHARD);

		Registry.register(Registry.ITEM, new Identifier(MODID, "heart_crystal"), HEART_CRYSTAL);

		Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(MODID, "heart_crystal_shard_placed"), PLACED_HEART_CRYSTAL_SHARD_FEATURE);
		BiomeModifications.create(new Identifier(MODID, "firstaid_deepslate_ore")).add(
				ModificationPhase.ADDITIONS,
				BiomeSelectors.foundInOverworld(),
				(biomeModificationContext -> biomeModificationContext.getGenerationSettings().addFeature(GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(MODID, "heart_crystal_shard_placed"))))
		);


	}
}
