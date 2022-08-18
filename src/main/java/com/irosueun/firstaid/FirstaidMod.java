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
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.*;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import static net.minecraft.world.gen.feature.OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES;
import static net.minecraft.world.gen.feature.OreConfiguredFeatures.STONE_ORE_REPLACEABLES;



public class FirstaidMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("firstaid");

	public static final Block HEART_CRYSTAL_SHARD_ORE = new ExperienceDroppingBlock(QuiltBlockSettings.of(Material.STONE).strength(3.0F, 3.0F).requiresTool(), UniformIntProvider.create(10, 15));
	public static final Block DEEPSLATE_HEART_CRYSTAL_SHARD_ORE = new ExperienceDroppingBlock(QuiltBlockSettings.of(Material.STONE).strength(4.5F, 3.0F).requiresTool(), UniformIntProvider.create(10, 15));

	public static final ConfiguredFeature<?, ?> CONFIGURED_HEART_CRYSTAL_SHARD_FEATURE = new ConfiguredFeature(Feature.ORE, new OreFeatureConfig(List.of(OreFeatureConfig.createTarget(STONE_ORE_REPLACEABLES, HEART_CRYSTAL_SHARD_ORE.getDefaultState()), OreFeatureConfig.createTarget(DEEPSLATE_ORE_REPLACEABLES, DEEPSLATE_HEART_CRYSTAL_SHARD_ORE.getDefaultState())), 1));
	public static final PlacedFeature PLACED_HEART_CRYSTAL_SHARD_FEATURE = new PlacedFeature(Holder.createDirect(CONFIGURED_HEART_CRYSTAL_SHARD_FEATURE), List.of());

	public static final Item HEART_CRYSTAL_SHARD = new Item(new QuiltItemSettings().group(ItemGroup.MISC));


	@Override
	public void onInitialize(ModContainer mod) {

		Registry.register(Registry.BLOCK, new Identifier("firstaid", "heart_crystal_shard_ore"), HEART_CRYSTAL_SHARD_ORE);
		Registry.register(Registry.ITEM, new Identifier("firstaid", "heart_crystal_shard_ore"), new BlockItem(HEART_CRYSTAL_SHARD_ORE, new QuiltItemSettings().group(ItemGroup.BUILDING_BLOCKS)));

		Registry.register(Registry.BLOCK, new Identifier("firstaid", "deepslate_heart_crystal_shard_ore"), DEEPSLATE_HEART_CRYSTAL_SHARD_ORE);
		Registry.register(Registry.ITEM, new Identifier("firstaid", "deepslate_heart_crystal_shard_ore"), new BlockItem(DEEPSLATE_HEART_CRYSTAL_SHARD_ORE, new QuiltItemSettings().group(ItemGroup.BUILDING_BLOCKS)));

		Registry.register(Registry.ITEM, new Identifier("firstaid", "heart_crystal_shard"), HEART_CRYSTAL_SHARD);


	}
}
