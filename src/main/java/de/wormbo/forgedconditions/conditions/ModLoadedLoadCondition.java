package de.wormbo.forgedconditions.conditions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/**
 * Codec for Neoforge's "Mod Loaded" data load condition, which only succeeds if a mod with a specified ID is loaded.
 *
 * @param modid The mod ID to check for.
 */
public record ModLoadedLoadCondition(String modid) implements LoadCondition {
	public static final MapCodec<ModLoadedLoadCondition> CODEC = RecordCodecBuilder.mapCodec(builder -> builder
			.group(Codec.STRING.fieldOf("modid").forGetter(ModLoadedLoadCondition::modid))
			.apply(builder, ModLoadedLoadCondition::new));

	@Override
	public MapCodec<? extends LoadCondition> codec() {
		return CODEC;
	}
}
