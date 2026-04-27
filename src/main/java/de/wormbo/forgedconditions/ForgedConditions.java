package de.wormbo.forgedconditions;

import com.mojang.serialization.MapCodec;
import de.wormbo.forgedconditions.conditions.AndLoadCondition;
import de.wormbo.forgedconditions.conditions.FalseLoadCondition;
import de.wormbo.forgedconditions.conditions.ItemExistsLoadCondition;
import de.wormbo.forgedconditions.conditions.LoadCondition;
import de.wormbo.forgedconditions.conditions.ModLoadedLoadCondition;
import de.wormbo.forgedconditions.conditions.NotLoadCondition;
import de.wormbo.forgedconditions.conditions.OrLoadCondition;
import de.wormbo.forgedconditions.conditions.TagEmptyLoadCondition;
import de.wormbo.forgedconditions.conditions.TrueLoadCondition;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Initializer for the Forged Conditions datagen mod. Since this mod is only supposed to be used in a datagen
 * environment, it will refuse to register anything when run outside a development environment.
 */
public class ForgedConditions implements ModInitializer {
	public static final String MOD_ID = "forged-conditions";
	public static final String CONDITIONS_KEY = "neoforge:conditions";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final ResourceKey<Registry<MapCodec<? extends LoadCondition>>> CONDITION_SERIALIZERS_KEY =
			ResourceKey.createRegistryKey(fcId("condition_serializers"));
	public static final Registry<MapCodec<? extends LoadCondition>> CONDITION_SERIALIZERS =
			FabricRegistryBuilder.createSimple(CONDITION_SERIALIZERS_KEY).buildAndRegister();

	public static ResourceLocation fcId(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

	public static ResourceLocation neoforgeId(String path) {
		return ResourceLocation.fromNamespaceAndPath("neoforge", path);
	}

	@Override
	public void onInitialize() {
		if (!FabricLoader.getInstance().isDevelopmentEnvironment()) {
			LOGGER.error("Forged Conditions should not be used outside of a development environment!");
			return;
		}
		LOGGER.info("Forged Conditions loaded");
		registerSerializer(neoforgeId("and"), AndLoadCondition.CODEC);
		registerSerializer(neoforgeId("false"), FalseLoadCondition.CODEC);
		registerSerializer(neoforgeId("item_exists"), ItemExistsLoadCondition.CODEC);
		registerSerializer(neoforgeId("mod_loaded"), ModLoadedLoadCondition.CODEC);
		registerSerializer(neoforgeId("not"), NotLoadCondition.CODEC);
		registerSerializer(neoforgeId("or"), OrLoadCondition.CODEC);
		registerSerializer(neoforgeId("tag_empty"), TagEmptyLoadCondition.CODEC);
		registerSerializer(neoforgeId("true"), TrueLoadCondition.CODEC);
	}

	private void registerSerializer(ResourceLocation id, MapCodec<? extends LoadCondition> codec) {
		Registry.register(CONDITION_SERIALIZERS, id, codec);
	}
}