package de.wormbo.forgedconditions.conditions;

import com.mojang.serialization.MapCodec;

public final class FalseCondition implements LoadCondition {
	public static final FalseCondition INSTANCE = new FalseCondition();
	public static final MapCodec<FalseCondition> CODEC = MapCodec.unit(INSTANCE).stable();

	private FalseCondition() {
	}

	@Override
	public MapCodec<? extends LoadCondition> codec() {
		return CODEC;
	}
}
