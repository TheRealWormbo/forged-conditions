package de.wormbo.forgedconditions.conditions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import de.wormbo.forgedconditions.ForgedConditions;

import java.util.List;
import java.util.function.Function;

/**
 * A mirror of the Neoforge ICondition interface, except it only provides codec functionality.
 */
public interface LoadCondition {
	Codec<LoadCondition> BY_NAME_CODEC = ForgedConditions.CONDITION_SERIALIZERS.byNameCodec()
			.dispatch(LoadCondition::codec, Function.identity());
	Codec<List<LoadCondition>> LIST_CODEC = BY_NAME_CODEC.listOf();


	MapCodec<? extends LoadCondition> codec();
}
