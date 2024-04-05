


# **HBM Nuclear Tech Mod - 1.12.2 Extended Edition**

# CraftTweaker Integration
Removing of oreDict recipes is currently not supported.


## Anvil

> **tier** must be > 0

mods.ntm.Anvil.addRecipe(IItemStack[] outputs, IIngredient[] inputs, int tier);<br>
``mods.ntm.Anvil.addRecipe(<minecraft:beef>, [<minecraft:cooked_beef>*4, <minecraft:egg>*6], 3);``

mods.ntm.Anvil.addRecipe(IItemStack[] outputs, IIngredient input, int tier);<br>
``mods.ntm.Anvil.addRecipe([<minecraft:beef>, <minecraft:cooked_beef>*4], <minecraft:egg>*6, 4);``

mods.ntm.Anvil.addRecipe(IItemStack output, IIngredient[] inputs, int tier);<br>
``mods.ntm.Anvil.addRecipe(<minecraft:beef>, [<minecraft:cooked_beef>*4, <minecraft:egg>*6], 6);``

mods.ntm.Anvil.addRecipe(IItemStack output, IIngredient input, int tier);<br>
``mods.ntm.Anvil.addRecipe(<minecraft:beef>, <minecraft:cooked_beef>, 5);``


mods.ntm.Anvil.replaceRecipe(IItemStack output, IIngredient[] inputs, int tier);<br>
``mods.ntm.Anvil.replaceRecipe(<hbm:machine_silex>, [<minecraft:glass>, <minecraft:cobblestone>], 4);``

mods.ntm.Anvil.removeRecipe(IItemStack[] outputs);<br>
``mods.ntm.Anvil.removeRecipe([<hbm:machine_silex>]);``

mods.ntm.Anvil.removeRecipeByInput(IIngredient[] inputs);<br>
``mods.ntm.Anvil.removeRecipeByInput([<minecraft:cooked_beef>]);``


## Assembler

> **duration** must be > 0

mods.ntm.Assembler.addRecipe(IItemStack output, IItemStack[] inputs, int duration);<br>
``mods.ntm.Assembler.addRecipe(<minecraft:beef>, [<minecraft:cooked_beef>*4, <minecraft:egg>*6], 30);``

mods.ntm.Assembler.replaceRecipe(IItemStack output, IItemStack[] inputs, int duration);<br>
``mods.ntm.Assembler.replaceRecipe(<hbm:machine_silex>, [<minecraft:glass>, <minecraft:cobblestone>], 50);``

mods.ntm.Assembler.removeRecipe(IItemStack output);<br>
``mods.ntm.Assembler.removeRecipe(<hbm:machine_silex>);``


## Chemplant

> **duration** must be > 0

mods.ntm.Chemplant.addChemplantRecipe(int index, String name, IItemStack[] inputItems, String[] inputFluids, int[] inputFluidAmounts, IItemStack[] outputItems, String[] outputFluids, int[] outputFluidAmounts, int duration);<br>
``mods.ntm.Chemplant.addChemplantRecipe(371, "NTM_BEEF", null, ["coolant"], [500], [<minecraft:snowball>], ["water", "petroleum"], [1000, 2000], 50);``


> **index** must be > 0 and is the item meta value

mods.ntm.Chemplant.removeChemplantRecipe(int index);<br>
``mods.ntm.Chemplant.removeChemplantRecipe(370);``


## Blast Furnace

mods.ntm.BlastFurnace.addRecipe(IItemStack input1, IItemStack input2, IItemStack output);<br>
``mods.ntm.BlastFurnace.addRecipe(<minecraft:beef>, <minecraft:stick>, <minecraft:cooked_beef>);``

mods.ntm.BlastFurnace.removeRecipe(IItemStack input1, IItemStack input2);<br>
``mods.ntm.BlastFurnace.removeRecipe(<minecraft:beef>, <minecraft:stick>);``

> **fuelAmount** must be > 0 and < 12800

mods.ntm.BlastFurnace.addFuel(IItemStack fuel, int fuelAmount);<br>
``mods.ntm.BlastFurnace.addFuel(<minecraft:stick>, 4000);``

mods.ntm.BlastFurnace.removeFuel(IItemStack fuel);<br>
``mods.ntm.BlastFurnace.removeFuel(<hbm:solid_fuel>);``


## Breeding Reactor

> **heat** must be > 0 and <= 4

mods.ntm.BreedingReactor.addRecipe(IItemStack input, IItemStack output, int heat);<br>
``mods.ntm.BreedingReactor.addRecipe(<minecraft:beef>, <minecraft:cooked_beef>, 3);``

mods.ntm.BreedingReactor.removeRecipe(IItemStack input);<br>
``mods.ntm.BreedingReactor.removeRecipe(<hbm:rod_pu239>);``


> **heat** must be > 0 and <= 4<br>
>  **usesInNuclearFurnace** must be > 0

mods.ntm.BreedingReactor.addRecipe(IItemStack fuel, int heat, int usesInNuclearFurnace);<br>
``mods.ntm.BreedingReactor.addFuel(<minecraft:dirt>, 4, 100);``

mods.ntm.BreedingReactor.removeRecipe(IItemStack fuel);<br>
``mods.ntm.BreedingReactor.removeFuel(<hbm:rod_polonium>);``


## Centrifuge

> The length of the **outputs** array must be > 0 and <= 4

mods.ntm.Centrifuge.addRecipe(IItemStack input, IItemStack[] outputs);<br>
``mods.ntm.Centrifuge.addRecipe(<minecraft:cooked_beef>, [<hbm:billet_nuclear_waste>, <minecraft:beef>, <minecraft:beef>]);``

mods.ntm.Centrifuge.removeRecipe(IItemStack input);<br>
``mods.ntm.Centrifuge.removeRecipe(<hbm:crystal_phosphorus>);``


## DFC

> **requiredSpark** must be > 0

mods.ntm.DFC.addRecipe(IItemStack input, IItemStack output, long requiredSpark);<br>
``mods.ntm.DFC.addRecipe(<minecraft:beef>, <minecraft:cooked_beef>, 420000000);``

mods.ntm.DFC.removeRecipe(IItemStack input);<br>
``mods.ntm.DFC.removeRecipe(<minecraft:stick>);``


## RBMK Irradiation Channel

> **requiredFlux** must be > 0

mods.ntm.IrradiationChannel.addRecipe(IItemStack input, IItemStack output, int requiredFlux);<br>
``mods.ntm.IrradiationChannel.addRecipe(<minecraft:beef>, <minecraft:egg>, 30000);``

mods.ntm.IrradiationChannel.removeRecipe(IItemStack input);<br>
``mods.ntm.IrradiationChannel.removeRecipe(<hbm:ingot_strontium>);``

## Press

> **type** must be 1-8
 - 1: Flat,  
 - 2: Plate,  
 - 3: Wire,  
 - 4: Circuit,  
 - 5: .357mm,  
 - 6: .44mm,    
 - 7: 9mm,  
 - 8: .50mm

mods.ntm.Press.addRecipe(IItemStack input, IIngredient output, int type);<br>
``mods.ntm.Press.addRecipe(<minecraft:cooked_beef>, <minecraft:beef>);``


## SILEX

> **wavelengthNr** can be 1-8:
> - 1 : Radio
> - 2 : Micro
> - 3 : IR
> - 4 : Visible
> - 5 : UV
> - 6 : X-Ray
> - 7 : Gamma
> - 8 : Digamma
> 
> **fluidAmount** is the amount of fluid in mb that gets created when the
> input item gets disolved. 
> **fluidConsumption** is the amount of fluid in
> mb that gets used per operation. 
> - So with a fluidAmount of 900mb and a fluidConsumption of 100mb you get 9 operations
> 
> The **ouputItems** and **outputWeights** need to have the same length. The
> outputWeights must be > 0 In the example there is a 70% chance for
> nuclear waste billets for every operation.

mods.ntm.SILEX.addRecipe(int wavelengthNr, int fluidAmount, int fluidConsumption, IItemStack input, IItemStack[] outputItems, int[] outputWeights);<br>
``mods.ntm.SILEX.addRecipe(2, 900, 100, <minecraft:cooked_beef>, [<hbm:billet_nuclear_waste>, <minecraft:beef>, <minecraft:beef>], [70,20,10]);``

mods.ntm.SILEX.removeRecipe(IItemStack input);<br>
``mods.ntm.SILEX.removeRecipe(<hbm:waste_dirt>);``


## Shredder
mods.ntm.Shredder.addRecipe(IItemStack input, IItemStack output);<br>
``mods.ntm.Shredder.addRecipe(<minecraft:beef>, <minecraft:cooked_beef>);``

mods.ntm.Shredder.removeRecipe(IItemStack input);<br>
``mods.ntm.Shredder.removeRecipe(<minecraft:tnt>);``


## Waste Drum
mods.ntm.WasteDrum.addRecipe(IItemStack input, IItemStack output);<br>
``mods.ntm.WasteDrum.addRecipe(<minecraft:cooked_beef>, <minecraft:beef>);``


## Fluid Heat Recipes

> **inputFluid** is the input fluid name
> **inputAmount** amount of mB used - must be > 0
> **outputFluid** is the output fluid name
> **outputAmount** amount of mB used - must be > 0
> **heatCapacity** TU used to convert this recipe - must be > 0

1mb of IC2 coolant and 450TU are used to produce 1mb of hot IC2 Coolant<br> mods.ntm.FluidHeating.addBoilRecipe(String inputFluid, int inputAmount, String outputFluid, int outputAmount, int heatCapacity);<br> ``mods.ntm.FluidHeating.addBoilRecipe("ic2coolant", 1, "ic2hot_coolant", 1, 450);``

1mb of hot IC2 Coolant if cooled releases 450TU and produces 1mb of IC2 Coolant<br>mods.ntm.FluidHeating.addCoolRecipe(String inputFluid, int inputAmount, String outputFluid, int outputAmount, int heatCapacity);<br>
``mods.ntm.FluidHeating.addCoolRecipe("ic2hot_coolant", 1, "ic2coolant", 1, 450);``

The top 2 recipes combined into one method<br>mods.ntm.FluidHeating.addBoilAndCoolRecipe(String inputFluid, int inputAmount, String outputFluid, int outputAmount, int heatCapacity);<br>
``mods.ntm.FluidHeating.addBoilAndCoolRecipe("ic2coolant", 1, "ic2hot_coolant", 1, 450);``

mods.ntm.FluidHeating.removeBoilRecipe(String inputFluid);<br>
``mods.ntm.FluidHeating.removeBoilRecipe("ic2coolant");``

mods.ntm.FluidHeating.removeCoolRecipe(String inputFluid);<br>
``mods.ntm.FluidHeating.removeCoolRecipe("ic2hot_coolant");``


## Fluid Combustion

> **inputFluid** is the input fluid name
> **heatPerMiliBucket** TU per 1mB of fluid - must be > 0 and < 100,000

Burn 1mB of experience fluid and get 5 TU<br>mods.ntm.FluidCombustion.addBurnableFluid(String inputFluid, int heatPerMiliBucket);<br>
``mods.ntm.FluidCombustion.addBurnableFluid("experience", 5);``

mods.ntm.FluidCombustion.removeBurnableFluid(String inputFluid);<br>
``mods.ntm.FluidCombustion.removeBurnableFluid("experience");``

