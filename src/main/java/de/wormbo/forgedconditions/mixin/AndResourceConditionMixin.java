package de.wormbo.forgedconditions.mixin;

import de.wormbo.forgedconditions.ForgedConditions;
import de.wormbo.forgedconditions.conditions.AndCondition;
import de.wormbo.forgedconditions.conditions.LoadCondition;
import de.wormbo.forgedconditions.converter.ConvertibleCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.impl.resource.conditions.conditions.AndResourceCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
@Mixin(AndResourceCondition.class)
public abstract class AndResourceConditionMixin implements ConvertibleCondition {
	@Shadow
	public abstract List<ResourceCondition> conditions();

	@SuppressWarnings("AddedMixinMembersNamePattern")
	@Override
	public LoadCondition convert() {
		List<LoadCondition> children = conditions().stream()
				.map(ConvertibleCondition.class::cast)
				.map(ConvertibleCondition::convert)
				.filter(Objects::nonNull)
				.toList();
		if (children.isEmpty()) {
			ForgedConditions.LOGGER.warn("Empty AND condition list");
		}
		return new AndCondition(children);
	}
}
