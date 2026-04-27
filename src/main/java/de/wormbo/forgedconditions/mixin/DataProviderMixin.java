package de.wormbo.forgedconditions.mixin;

import de.wormbo.forgedconditions.ForgedConditions;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.data.DataProvider;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Registers the Neoforge data load condition JSON key such that it is emitted right below the Fabric resource
 * condition key in the JSON output.
 */
@Mixin(DataProvider.class)
public interface DataProviderMixin {
	@Dynamic("lambda method passed to Util.make for FIXED_ORDER_FIELDS")
	@Inject(method = "method_43808", at = @At("HEAD"))
	private static void forgedconditions_injectNeoforgeConditionsSortOrder(Object2IntOpenHashMap<String> map,
			CallbackInfo ci) {
		map.put(ForgedConditions.CONDITIONS_KEY, -99);
	}
}
