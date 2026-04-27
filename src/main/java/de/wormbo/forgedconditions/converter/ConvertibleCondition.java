package de.wormbo.forgedconditions.converter;

import de.wormbo.forgedconditions.ForgedConditions;
import de.wormbo.forgedconditions.conditions.LoadCondition;
import org.jetbrains.annotations.Nullable;

/**
 * This interface is mixed into Fabric's {@link net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition}
 * interface and is used as entry point to attempt to convert resource conditions to data load conditions.
 */
public interface ConvertibleCondition {
	/**
	 * Converts this resource condition to an equivalent codec representation matching for a Neoforge data load
	 * condition.
	 *
	 * @return A codec representation of a Neoforge condition that matches this resource condition's meaning, or
	 * {@code null} if this condition does not have a standard Neoforge equivalent.
	 */
	@Nullable
	default LoadCondition forgedconditions_convert() {
		ForgedConditions.LOGGER.warn("Condition type not supported for conversion: {}", this.getClass());
		return null;
	}
}
