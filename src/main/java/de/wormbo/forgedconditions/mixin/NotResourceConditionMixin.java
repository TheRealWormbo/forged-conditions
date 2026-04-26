package de.wormbo.forgedconditions.mixin;

import de.wormbo.forgedconditions.ForgedConditions;
import de.wormbo.forgedconditions.conditions.FalseCondition;
import de.wormbo.forgedconditions.conditions.LoadCondition;
import de.wormbo.forgedconditions.conditions.NotCondition;
import de.wormbo.forgedconditions.conditions.TrueCondition;
import de.wormbo.forgedconditions.converter.ConvertibleCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.impl.resource.conditions.conditions.NotResourceCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("UnstableApiUsage")
@Mixin(NotResourceCondition.class)
public abstract class NotResourceConditionMixin implements ConvertibleCondition {
	@Shadow
	public abstract ResourceCondition condition();

	@SuppressWarnings("AddedMixinMembersNamePattern")
	@Override
	public LoadCondition convert() {
		LoadCondition condition = ((ConvertibleCondition) condition()).convert();
		if (condition == null) {
			ForgedConditions.LOGGER.warn("Empty NOT condition");
			return null;
		}
		if (condition == TrueCondition.INSTANCE) {
			return FalseCondition.INSTANCE;
		}
		if (condition instanceof NotCondition(LoadCondition value)) {
			return value;
		}
		return new NotCondition(condition);
	}
}
