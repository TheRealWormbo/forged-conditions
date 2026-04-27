package de.wormbo.forgedconditions.conditions;

import com.mojang.serialization.MapCodec;

/**
 * Codec for Neoforge's TRUE data load condition, i.e. a condition that always succeeds. This is a singleton instance.
 */
public final class TrueLoadCondition implements LoadCondition {
	public static final TrueLoadCondition INSTANCE = new TrueLoadCondition();
	public static final MapCodec<TrueLoadCondition> CODEC = MapCodec.unit(INSTANCE).stable();

	private TrueLoadCondition() {
	}

	@Override
	public MapCodec<? extends LoadCondition> codec() {
		return CODEC;
	}
}
