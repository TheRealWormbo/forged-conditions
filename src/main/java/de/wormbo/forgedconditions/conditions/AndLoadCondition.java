package de.wormbo.forgedconditions.conditions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

/**
 * Codec for Neoforge's AND data load condition, which succeeds only if all its child conditions succeed.
 *
 * @param values The list conditions that need to succeed.
 */
public record AndLoadCondition(List<LoadCondition> values) implements LoadCondition {
	public static final MapCodec<AndLoadCondition> CODEC = RecordCodecBuilder.mapCodec(builder -> builder
			.group(LIST_CODEC.fieldOf("values").forGetter(AndLoadCondition::values))
			.apply(builder, AndLoadCondition::new));

	@Override
	public MapCodec<? extends LoadCondition> codec() {
		return CODEC;
	}
}
