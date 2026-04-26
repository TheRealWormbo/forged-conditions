package de.wormbo.forgedconditions;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.impl.resource.conditions.conditions.AndResourceCondition;
import net.fabricmc.fabric.impl.resource.conditions.conditions.AnyModsLoadedResourceCondition;
import net.fabricmc.fabric.impl.resource.conditions.conditions.NotResourceCondition;
import net.fabricmc.fabric.impl.resource.conditions.conditions.RegistryContainsResourceCondition;
import net.fabricmc.fabric.impl.resource.conditions.conditions.TagsPopulatedResourceCondition;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static de.wormbo.forgedconditions.ForgedConditions.fcId;

public class ForgedConditionsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(RecipeProvider::new);
	}

	@SuppressWarnings("UnstableApiUsage")
	public static class RecipeProvider extends FabricRecipeProvider {

		public RecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
			super(output, registriesFuture);
		}

		@Override
		public void buildRecipes(RecipeOutput exporter) {
			ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Blocks.END_PORTAL_FRAME)
					.define('E', Blocks.END_STONE)
					.define('S', Items.NETHER_STAR)
					.pattern(" E ")
					.pattern("ESE")
					.pattern(" E ")
					.unlockedBy("has_Star", has(Items.NETHER_STAR))
					.save(withConditions(exporter,
									new AndResourceCondition(List.of(
											new AnyModsLoadedResourceCondition(
													List.of("craftable_endportal_frames", "better-end-access")),
											new RegistryContainsResourceCondition(Registries.ITEM.location(),
													BuiltInRegistries.ITEM.getKey(Items.NETHER_STAR),
													BuiltInRegistries.ITEM.getKey(Items.END_STONE)),
											new RegistryContainsResourceCondition(Registries.BLOCK.location(),
													BuiltInRegistries.BLOCK.getKey(Blocks.END_PORTAL_FRAME)),
											new NotResourceCondition(
													new TagsPopulatedResourceCondition(ItemTags.PICKAXES,
															ItemTags.SHOVELS))
									))),
							fcId("example-recipe"));
		}
	}
}
