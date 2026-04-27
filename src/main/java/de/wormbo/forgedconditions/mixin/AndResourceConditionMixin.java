package de.wormbo.forgedconditions.mixin;

import de.wormbo.forgedconditions.ForgedConditions;
import de.wormbo.forgedconditions.conditions.AndLoadCondition;
import de.wormbo.forgedconditions.conditions.LoadCondition;
import de.wormbo.forgedconditions.converter.ConvertibleCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.impl.resource.conditions.conditions.AndResourceCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Objects;

/**
 * Adds Neoforge codec conversion for the AND condition, which has a direct equivalent in Neoforge. Any unsupported
 * conditions are dropped, but the potentially empty AND condition is returned anyway in such cases, along with a log
 * warning.
 */
@SuppressWarnings("UnstableApiUsage")
@Mixin(AndResourceCondition.class)
public abstract class AndResourceConditionMixin implements ConvertibleCondition {
	@Shadow
	public abstract List<ResourceCondition> conditions();

	@Override
	public LoadCondition forgedconditions_convert() {
		List<LoadCondition> children = conditions().stream()
				.map(ConvertibleCondition.class::cast)
				.map(ConvertibleCondition::forgedconditions_convert)
				.filter(Objects::nonNull)
				.toList();
		if (children.isEmpty()) {
			ForgedConditions.LOGGER.warn("Empty AND condition list");
		}
		return new AndLoadCondition(children);
	}
}
