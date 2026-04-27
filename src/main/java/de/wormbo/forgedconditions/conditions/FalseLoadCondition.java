package de.wormbo.forgedconditions.conditions;

import com.mojang.serialization.MapCodec;

/**
 * Codec for Neoforge's FALSE data load condition, i.e. a condition that always fails. This is a singleton instance.
 */
public final class FalseLoadCondition implements LoadCondition {
	public static final FalseLoadCondition INSTANCE = new FalseLoadCondition();
	public static final MapCodec<FalseLoadCondition> CODEC = MapCodec.unit(INSTANCE).stable();

	private FalseLoadCondition() {
	}

	@Override
	public MapCodec<? extends LoadCondition> codec() {
		return CODEC;
	}
}
