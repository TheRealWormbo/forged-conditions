package de.wormbo.forgedconditions.converter;

import de.wormbo.forgedconditions.ForgedConditions;
import de.wormbo.forgedconditions.conditions.LoadCondition;
import org.jetbrains.annotations.Nullable;

public interface ConvertibleCondition {
	@Nullable
	default LoadCondition convert() {
		ForgedConditions.LOGGER.warn("Condition type not supported for conversion: {}", this.getClass());
		return null;
	}
}
