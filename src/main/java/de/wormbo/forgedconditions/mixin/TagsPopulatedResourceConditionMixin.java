package de.wormbo.forgedconditions.mixin;

import de.wormbo.forgedconditions.ForgedConditions;
import de.wormbo.forgedconditions.conditions.LoadCondition;
import de.wormbo.forgedconditions.conditions.NotLoadCondition;
import de.wormbo.forgedconditions.conditions.OrLoadCondition;
import de.wormbo.forgedconditions.conditions.TagEmptyLoadCondition;
import de.wormbo.forgedconditions.converter.ConvertibleCondition;
import net.fabricmc.fabric.impl.resource.conditions.conditions.TagsPopulatedResourceCondition;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

/**
 * Adds Neoforge codec conversion for the "tags_populated" resource condition. Conversion is only supported if the
 * resource condition tests against the items registry. Depending on the number of tag keys to test, the conversion
 * result
 * includes either a single "tag_empty" condition, or an OR condition wrapping one "tag_empty" condition for each
 * specified tag key. In either case the result is wrapped in a NOT condition, since the Neoforge condition's
 * definition is inverted from the Fabric's.
 */
@SuppressWarnings("UnstableApiUsage")
@Mixin(TagsPopulatedResourceCondition.class)
public abstract class TagsPopulatedResourceConditionMixin implements ConvertibleCondition {
	@Shadow
	public abstract ResourceLocation registry();

	@Shadow
	public abstract List<ResourceLocation> tags();

	@Override
	public LoadCondition forgedconditions_convert() {
		if (!Registries.ITEM.location().equals(registry())) {
			ForgedConditions.LOGGER.warn(
					"Unsupported registry '{}' for tags_populated condition, only '{}' is supported",
					registry(), Registries.ITEM.location());
			return null;
		}
		List<TagKey<Item>> tags = tags().stream().map(loc -> TagKey.create(Registries.ITEM, loc)).toList();
		return new NotLoadCondition(tags.size() == 1
				? new TagEmptyLoadCondition(tags.getFirst())
				: new OrLoadCondition(List.copyOf(tags.stream().map(TagEmptyLoadCondition::new).toList())));
	}
}
