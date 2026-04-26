package de.wormbo.forgedconditions.conditions;

import com.mojang.serialization.MapCodec;

public final class TrueCondition implements LoadCondition {
	public static final TrueCondition INSTANCE = new TrueCondition();
	public static final MapCodec<TrueCondition> CODEC = MapCodec.unit(INSTANCE).stable();

	private TrueCondition() {
	}

	@Override
	public MapCodec<? extends LoadCondition> codec() {
		return CODEC;
	}
}
