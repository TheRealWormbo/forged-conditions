package de.wormbo.forgedconditions.conditions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

/**
 * Codec for Neoforge's OR data load condition, which succeeds if at least one of its child conditions succeeds.
 *
 * @param values The list of conditions to check.
 */
public record OrLoadCondition(List<LoadCondition> values) implements LoadCondition {
	public static final MapCodec<OrLoadCondition> CODEC = RecordCodecBuilder.mapCodec(builder -> builder
			.group(LIST_CODEC.fieldOf("values").forGetter(OrLoadCondition::values))
			.apply(builder, OrLoadCondition::new));

	@Override
	public MapCodec<? extends LoadCondition> codec() {
		return CODEC;
	}
}
