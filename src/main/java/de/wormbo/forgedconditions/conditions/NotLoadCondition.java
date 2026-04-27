package de.wormbo.forgedconditions.conditions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/**
 * Codec for Neoforge's NOT data load condition, which inverts the success value of the nested condition.
 *
 * @param value The condition to invert.
 */
public record NotLoadCondition(LoadCondition value) implements LoadCondition {
	public static final MapCodec<NotLoadCondition> CODEC = RecordCodecBuilder.mapCodec(builder -> builder
			.group(BY_NAME_CODEC.fieldOf("value").forGetter(NotLoadCondition::value))
			.apply(builder, NotLoadCondition::new));

	@Override
	public MapCodec<? extends LoadCondition> codec() {
		return CODEC;
	}
}
