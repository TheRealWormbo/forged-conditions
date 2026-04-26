package de.wormbo.forgedconditions.conditions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public record TagEmptyCondition(TagKey<Item> tag) implements LoadCondition {
	public static final MapCodec<TagEmptyCondition> CODEC = RecordCodecBuilder.mapCodec(builder -> builder
			.group(ResourceLocation.CODEC
					.xmap(loc -> TagKey.create(Registries.ITEM, loc), TagKey::location)
					.fieldOf("tag").forGetter(TagEmptyCondition::tag))
			.apply(builder, TagEmptyCondition::new));

	@Override
	public MapCodec<? extends LoadCondition> codec() {
		return CODEC;
	}
}
