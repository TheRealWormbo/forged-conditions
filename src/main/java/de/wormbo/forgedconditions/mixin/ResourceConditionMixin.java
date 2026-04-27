package de.wormbo.forgedconditions.mixin;

import de.wormbo.forgedconditions.converter.ConvertibleCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Extends the Fabric {@link ResourceCondition} interface so all custom resource conditions can log a warning when
 * trying to convert them.
 */
@Mixin(ResourceCondition.class)
public interface ResourceConditionMixin extends ConvertibleCondition {
}
