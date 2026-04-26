package de.wormbo.forgedconditions.mixin;

import de.wormbo.forgedconditions.conditions.AndCondition;
import de.wormbo.forgedconditions.conditions.LoadCondition;
import de.wormbo.forgedconditions.conditions.ModLoadedCondition;
import de.wormbo.forgedconditions.converter.ConvertibleCondition;
import net.fabricmc.fabric.impl.resource.conditions.conditions.AllModsLoadedResourceCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
@Mixin(AllModsLoadedResourceCondition.class)
public abstract class AllModsLoadedResourceConditionMixin implements ConvertibleCondition {
	@Shadow
	public abstract List<String> modIds();

	@SuppressWarnings("AddedMixinMembersNamePattern")
	@Override
	public LoadCondition convert() {
		List<String> modIds = modIds();
		if (modIds.size() == 1) {
			return new ModLoadedCondition(modIds.getFirst());
		}
		return new AndCondition(List.copyOf(modIds.stream().map(ModLoadedCondition::new).toList()));
	}
}
