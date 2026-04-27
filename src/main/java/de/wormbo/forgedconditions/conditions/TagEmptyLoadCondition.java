package de.wormbo.forgedconditions.conditions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * Codec for Neoforge's "Tag Empty" data load condition, which succeeds if the specified tag does not exist or
 * doesn't contain any entries.
 *
 * @param tag The item tag to check.
 */
public record TagEmptyLoadCondition(TagKey<Item> tag) implements LoadCondition {
	public static final MapCodec<TagEmptyLoadCondition> CODEC = RecordCodecBuilder.mapCodec(builder -> builder
			.group(ResourceLocation.CODEC
					.xmap(loc -> TagKey.create(Registries.ITEM, loc), TagKey::location)
					.fieldOf("tag").forGetter(TagEmptyLoadCondition::tag))
			.apply(builder, TagEmptyLoadCondition::new));

	@Override
	public MapCodec<? extends LoadCondition> codec() {
		return CODEC;
	}
}
