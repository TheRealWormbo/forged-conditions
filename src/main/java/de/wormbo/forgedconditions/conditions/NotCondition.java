package de.wormbo.forgedconditions.conditions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record NotCondition(LoadCondition value) implements LoadCondition {
	public static final MapCodec<NotCondition> CODEC = RecordCodecBuilder.mapCodec(builder -> builder
			.group(BY_NAME_CODEC.fieldOf("value").forGetter(NotCondition::value))
			.apply(builder, NotCondition::new));

	@Override
	public MapCodec<? extends LoadCondition> codec() {
		return CODEC;
	}
}
