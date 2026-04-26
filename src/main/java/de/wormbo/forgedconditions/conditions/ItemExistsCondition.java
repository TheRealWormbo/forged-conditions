package de.wormbo.forgedconditions.conditions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public record ItemExistsCondition(ResourceLocation item) implements LoadCondition {
	public static MapCodec<ItemExistsCondition> CODEC = RecordCodecBuilder.mapCodec(builder -> builder
			.group(ResourceLocation.CODEC.fieldOf("item").forGetter(ItemExistsCondition::item))
			.apply(builder, ItemExistsCondition::new));

	@Override
	public MapCodec<? extends LoadCondition> codec() {
		return CODEC;
	}
}
