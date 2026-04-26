package de.wormbo.forgedconditions.mixin;

import de.wormbo.forgedconditions.ForgedConditions;
import de.wormbo.forgedconditions.conditions.LoadCondition;
import de.wormbo.forgedconditions.conditions.NotCondition;
import de.wormbo.forgedconditions.conditions.OrCondition;
import de.wormbo.forgedconditions.conditions.TagEmptyCondition;
import de.wormbo.forgedconditions.converter.ConvertibleCondition;
import net.fabricmc.fabric.impl.resource.conditions.conditions.TagsPopulatedResourceCondition;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
@Mixin(TagsPopulatedResourceCondition.class)
public abstract class TagsPopulatedResourceConditionMixin implements ConvertibleCondition {
	@Shadow
	public abstract ResourceLocation registry();

	@Shadow
	public abstract List<ResourceLocation> tags();

	@SuppressWarnings("AddedMixinMembersNamePattern")
	@Override
	public LoadCondition convert() {
		if (!Registries.ITEM.location().equals(registry())) {
			ForgedConditions.LOGGER.warn(
					"Unsupported registry '{}' for tags_populated condition, only '{}' is supported",
					registry(), Registries.ITEM.location());
			return null;
		}
		List<TagKey<Item>> tags = tags().stream().map(loc -> TagKey.create(Registries.ITEM, loc)).toList();
		return new NotCondition(tags.size() == 1
				? new TagEmptyCondition(tags.getFirst())
				: new OrCondition(List.copyOf(tags.stream().map(TagEmptyCondition::new).toList())));
	}
}
