# Forged Conditions

A Neoforge data load condition generator for Fabric

## Who is this for?

This mod is not meant to be used by players or included in any mod packs.

It only has one specific purpose, and that is generating Neoforge data load conditions from any specified Fabric resource conditions while running Fabric datagen.
If you don't know what that means, **you do not need/want this mod**. If your cross-platform datagen happens in Neoforge, unfortunately this mod cannot help you.

## Setup

To use the mod for datagen, add the following line to your Fabric module's Gradle dependencies:

    modRuntimeOnly "de.wormbo.forgedconditions:forged-conditions:21.1.0"

Do **not** add any dependency in your `fabric.mod.json`.

## Limitations

This mod only supports the default conditions available in the Fabric Resource Conditions API and in Neoforge. It performs a bit of structural conversion, since Neoforge's data load conditions are defined slightly differently from Fabric's implementation. Any unsupported conditions, such as the built-in `fabric:features_enabled` or any custom resource conditions will be dropped with a warning in the datagen log output.

Condition conversions:

* `fabric:and`: Translates directly to `neoforge:and`. Any unsupported child conditions are dropped, even if this means the AND list is empty.
* `fabric:any_mods_loaded`: Same as `fabric:all_mods_loaded`, except `neoforge:or` is used if not just one ID is specified.
* `fabric:all_mods_loaded`: If only one mod ID is specified, `neoforge:mod_loaded` is generated for that mod ID. Otherwise `neoforge:and` with a list of `neoforge:mod_loaded` for the listed IDs is generated.
* `fabric:feature_enabled`: Not supported.
* `fabric:not`: Translates directly to `neoforge:not`. If the wrapped condition is not supported, the NOT condition is not generated at all. If the child condition is `fabric:true`, `neoforge:false` is generated directly instead of wrapping TRUE in NOT. If a NOT somehow ends up being directly wrapped in another NOT, the two cancel out and only the no-longer-doubly-NOT-wrapped child is generated.
* `fabric:or`: Translates directly to `neoforge:or`. Any unsupported child conditions are dropped, even if this means the OR list is empty.
* `fabric:registry_contains`: Only supports registry `minecraft:item`. If a single item is specified, `neoforge:item_exists` is generated, otherwise `neoforge:and` is generated with a `neoforge:item_exists` for each item.
* `fabric:tags_populated`: Only supports registry `minecraft:item`. Neoforge's equivalent condition is inverted, so the condition is transformed to "none of these tags are empty". If a single tag is specified, `neoforge:tag_empty` is wrapped in `neoforge:not`, otherwise `neoforge:or` is generated with a `neoforge:tag_empty` for each tag and then wrapped in `neoforge:not`. (If this causes a pair of nested NOT conditions, they cancel out and only the OR or single TAG-EMPTY is generated.)
* `fabric:true`: Translates directly to `neoforge:true`. If TRUE is directly wrapped in NOT, that combination translates directly to `neoforge:false`.