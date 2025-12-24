package net.barbieloth.ethertech.datagen;

import net.barbieloth.ethertech.EtherTech;
import net.barbieloth.ethertech.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, EtherTech.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.ETHER);
        simpleItem(ModItems.RAW_ETHER);
        simpleItem(ModItems.ETHER_WAND);
        simpleItem(ModItems.ORANGE);
        simpleItem(ModItems.JAGER);

    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",new ResourceLocation(EtherTech.MOD_ID,"item/" + item.getId().getPath()));
    }

}
