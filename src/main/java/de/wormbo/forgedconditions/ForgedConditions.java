package de.wormbo.forgedconditions;

import com.mojang.serialization.MapCodec;
import de.wormbo.forgedconditions.conditions.AndCondition;
import de.wormbo.forgedconditions.conditions.FalseCondition;
import de.wormbo.forgedconditions.conditions.ItemExistsCondition;
import de.wormbo.forgedconditions.conditions.LoadCondition;
import de.wormbo.forgedconditions.conditions.ModLoadedCondition;
import de.wormbo.forgedconditions.conditions.NotCondition;
import de.wormbo.forgedconditions.conditions.OrCondition;
import de.wormbo.forgedconditions.conditions.TagEmptyCondition;
import de.wormbo.forgedconditions.conditions.TrueCondition;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.impl.datagen.FabricDataGenHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForgedConditions implements ModInitializer {
	public static final String MOD_ID = "forged-conditions";
	public static final String CONDITIONS_KEY = "neoforge:conditions";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final ResourceKey<Registry<MapCodec<? extends LoadCondition>>> CONDITION_SERIALIZERS_KEY = ResourceKey.createRegistryKey(fcId("condition_serializers"));
	public static final Registry<MapCodec<? extends LoadCondition>> CONDITION_SERIALIZERS = FabricRegistryBuilder.createSimple(CONDITION_SERIALIZERS_KEY).buildAndRegister();

	@Override
	public void onInitialize() {
		if (!FabricLoader.getInstance().isDevelopmentEnvironment()) {
			LOGGER.error("Forged Conditions should not be used outside of a development environment!");
			return;
		}
		LOGGER.info("Forged Conditions loaded");
		registerSerializer(neoforgeId("and"), AndCondition.CODEC);
		registerSerializer(neoforgeId("false"), FalseCondition.CODEC);
		registerSerializer(neoforgeId("item_exists"), ItemExistsCondition.CODEC);
		registerSerializer(neoforgeId("mod_loaded"), ModLoadedCondition.CODEC);
		registerSerializer(neoforgeId("not"), NotCondition.CODEC);
		registerSerializer(neoforgeId("or"), OrCondition.CODEC);
		registerSerializer(neoforgeId("tag_empty"), TagEmptyCondition.CODEC);
		registerSerializer(neoforgeId("true"), TrueCondition.CODEC);
	}

	private void registerSerializer(ResourceLocation id, MapCodec<? extends LoadCondition> codec) {
		Registry.register(CONDITION_SERIALIZERS, id, codec);
	}

	public static ResourceLocation fcId(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

	public static ResourceLocation neoforgeId(String path) {
		return ResourceLocation.fromNamespaceAndPath("neoforge", path);
	}
}