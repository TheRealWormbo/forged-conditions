package de.wormbo.forgedconditions.conditions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record OrCondition(List<LoadCondition> values) implements LoadCondition {
	public static final MapCodec<OrCondition> CODEC = RecordCodecBuilder.mapCodec(builder -> builder
			.group(LIST_CODEC.fieldOf("values").forGetter(OrCondition::values))
			.apply(builder, OrCondition::new));

	@Override
	public MapCodec<? extends LoadCondition> codec() {
		return CODEC;
	}
}
