package de.wormbo.forgedconditions.conditions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record AndCondition(List<LoadCondition> values) implements LoadCondition {
	public static final MapCodec<AndCondition> CODEC = RecordCodecBuilder.mapCodec(builder -> builder
			.group(LIST_CODEC.fieldOf("values").forGetter(AndCondition::values))
			.apply(builder, AndCondition::new));

	@Override
	public MapCodec<? extends LoadCondition> codec() {
		return CODEC;
	}
}
