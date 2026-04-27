package de.wormbo.forgedconditions.mixin;

import de.wormbo.forgedconditions.ForgedConditions;
import de.wormbo.forgedconditions.conditions.FalseLoadCondition;
import de.wormbo.forgedconditions.conditions.LoadCondition;
import de.wormbo.forgedconditions.conditions.NotLoadCondition;
import de.wormbo.forgedconditions.conditions.TrueLoadCondition;
import de.wormbo.forgedconditions.converter.ConvertibleCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.impl.resource.conditions.conditions.NotResourceCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Adds Neoforge codec conversion for the NOT condition, which has a direct equivalent in Neoforge. If the inner
 * condition is unsupported, the NOT condition is not returned. If the inner condition is a TRUE condition, a FALSE
 * condition is returned instead of NOT(TRUE). If the inner condition is a NOT condition as well (e.g. because a
 * "tags_populated" condition was converted), that inner condition's inner condition is returned directly instead of
 * wrapping it into a NOT condition twice.
 */
@SuppressWarnings("UnstableApiUsage")
@Mixin(NotResourceCondition.class)
public abstract class NotResourceConditionMixin implements ConvertibleCondition {
	@Shadow
	public abstract ResourceCondition condition();

	@Override
	public LoadCondition forgedconditions_convert() {
		LoadCondition condition = ((ConvertibleCondition) condition()).forgedconditions_convert();
		if (condition == null) {
			ForgedConditions.LOGGER.warn("Empty NOT condition");
			return null;
		}
		if (condition == TrueLoadCondition.INSTANCE) {
			return FalseLoadCondition.INSTANCE;
		}
		if (condition instanceof NotLoadCondition(LoadCondition value)) {
			return value;
		}
		return new NotLoadCondition(condition);
	}
}
