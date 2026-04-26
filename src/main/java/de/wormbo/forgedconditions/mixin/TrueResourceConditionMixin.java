package de.wormbo.forgedconditions.mixin;

import de.wormbo.forgedconditions.conditions.LoadCondition;
import de.wormbo.forgedconditions.conditions.TrueCondition;
import de.wormbo.forgedconditions.converter.ConvertibleCondition;
import net.fabricmc.fabric.impl.resource.conditions.conditions.TrueResourceCondition;
import org.spongepowered.asm.mixin.Mixin;

@SuppressWarnings("UnstableApiUsage")
@Mixin(TrueResourceCondition.class)
public abstract class TrueResourceConditionMixin implements ConvertibleCondition {
	@SuppressWarnings("AddedMixinMembersNamePattern")
	@Override
	public LoadCondition convert() {
		return TrueCondition.INSTANCE;
	}
}
