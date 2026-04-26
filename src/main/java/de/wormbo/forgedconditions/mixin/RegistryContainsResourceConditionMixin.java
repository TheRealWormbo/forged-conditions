package de.wormbo.forgedconditions.mixin;

import de.wormbo.forgedconditions.ForgedConditions;
import de.wormbo.forgedconditions.conditions.AndCondition;
import de.wormbo.forgedconditions.conditions.ItemExistsCondition;
import de.wormbo.forgedconditions.conditions.LoadCondition;
import de.wormbo.forgedconditions.converter.ConvertibleCondition;
import net.fabricmc.fabric.impl.resource.conditions.conditions.RegistryContainsResourceCondition;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
@Mixin(RegistryContainsResourceCondition.class)
public abstract class RegistryContainsResourceConditionMixin implements ConvertibleCondition {
	@Shadow
	public abstract ResourceLocation registry();

	@Shadow
	public abstract List<ResourceLocation> entries();

	@SuppressWarnings("AddedMixinMembersNamePattern")
	@Override
	public LoadCondition convert() {
		if (!Registries.ITEM.location().equals(registry())) {
			ForgedConditions.LOGGER.warn(
					"Unsupported registry '{}' for registry_contains condition, only '{}' is supported",
					registry(), Registries.ITEM.location());
			return null;
		}
		List<ResourceLocation> entries = entries();
		if (entries.size() == 1) {
			return new ItemExistsCondition(entries.getFirst());
		}
		return new AndCondition(List.copyOf(entries.stream().map(ItemExistsCondition::new).toList()));
	}
}
