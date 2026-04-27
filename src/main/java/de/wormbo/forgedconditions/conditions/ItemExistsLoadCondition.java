package de.wormbo.forgedconditions.conditions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

/**
 * Codec for Neoforge's "Item Exists" data load condition, which succeeds only if an item with a specific ID is
 * registered.
 *
 * @param item ID of the item to check for.
 */
public record ItemExistsLoadCondition(ResourceLocation item) implements LoadCondition {
	public static MapCodec<ItemExistsLoadCondition> CODEC = RecordCodecBuilder.mapCodec(builder -> builder
			.group(ResourceLocation.CODEC.fieldOf("item").forGetter(ItemExistsLoadCondition::item))
			.apply(builder, ItemExistsLoadCondition::new));

	@Override
	public MapCodec<? extends LoadCondition> codec() {
		return CODEC;
	}
}
