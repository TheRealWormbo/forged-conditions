package de.wormbo.forgedconditions.mixin;

import de.wormbo.forgedconditions.conditions.LoadCondition;
import de.wormbo.forgedconditions.conditions.ModLoadedLoadCondition;
import de.wormbo.forgedconditions.conditions.OrLoadCondition;
import de.wormbo.forgedconditions.converter.ConvertibleCondition;
import net.fabricmc.fabric.impl.resource.conditions.conditions.AnyModsLoadedResourceCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

/**
 * Adds Neoforge codec conversion for the "any_mods_loaded" condition. Depending on the number of specified mod IDs,
 * the conversion either returns a single "mod_loaded" condition or an OR condition wrapping one "mod_loaded"
 * condition for each specified mod ID.
 */
@SuppressWarnings("UnstableApiUsage")
@Mixin(AnyModsLoadedResourceCondition.class)
public abstract class AnyModsLoadedResourceConditionMixin implements ConvertibleCondition {
	@Shadow
	public abstract List<String> modIds();

	@Override
	public LoadCondition forgedconditions_convert() {
		List<String> modIds = modIds();
		if (modIds.size() == 1) {
			return new ModLoadedLoadCondition(modIds.getFirst());
		}
		return new OrLoadCondition(List.copyOf(modIds.stream().map(ModLoadedLoadCondition::new).toList()));
	}
}
