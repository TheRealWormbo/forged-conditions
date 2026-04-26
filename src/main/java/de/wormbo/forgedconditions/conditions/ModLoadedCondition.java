package de.wormbo.forgedconditions.conditions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ModLoadedCondition(String modid) implements LoadCondition {
	public static final MapCodec<ModLoadedCondition> CODEC = RecordCodecBuilder.mapCodec(builder -> builder
			.group(Codec.STRING.fieldOf("modid").forGetter(ModLoadedCondition::modid))
			.apply(builder, ModLoadedCondition::new));

	@Override
	public MapCodec<? extends LoadCondition> codec() {
		return CODEC;
	}
}
