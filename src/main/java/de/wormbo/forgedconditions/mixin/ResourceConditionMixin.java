package de.wormbo.forgedconditions.mixin;

import de.wormbo.forgedconditions.converter.ConvertibleCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ResourceCondition.class)
public interface ResourceConditionMixin extends ConvertibleCondition {
}
