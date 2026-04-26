package de.wormbo.forgedconditions.mixin;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import de.wormbo.forgedconditions.ForgedConditions;
import de.wormbo.forgedconditions.conditions.LoadCondition;
import de.wormbo.forgedconditions.converter.ConvertibleCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.impl.datagen.FabricDataGenHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@SuppressWarnings("UnstableApiUsage")
@Mixin(FabricDataGenHelper.class)
public abstract class FabricDataGenHelperMixin {
	@Inject(method = "addConditions(Lcom/google/gson/JsonObject;" +
			"[Lnet/fabricmc/fabric/api/resource/conditions/v1/ResourceCondition;)V",
			at = @At(value = "INVOKE", target = "Lcom/google/gson/JsonObject;add(Ljava/lang/String;" +
					"Lcom/google/gson/JsonElement;)V"))
	private static void addNeoforgeConditions(JsonObject baseObject, ResourceCondition[] conditions, CallbackInfo ci) {
		baseObject.add(ForgedConditions.CONDITIONS_KEY, LoadCondition.LIST_CODEC.encodeStart(JsonOps.INSTANCE,
				Arrays.stream(conditions)
						.map(ConvertibleCondition.class::cast)
						.map(ConvertibleCondition::convert)
						.toList()
		).getOrThrow());
	}
}
