package net.barbieloth.ethertech.datagen;

import net.barbieloth.ethertech.EtherTech;
import net.barbieloth.ethertech.block.ModBlocks;
import net.barbieloth.ethertech.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, EtherTech.MOD_ID, existingFileHelper);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ModTags.Blocks.METAL_DETECTOR_VALUABLES)
                .add(ModBlocks.ETHER_ORE.get()).addTags(Tags.Blocks.ORES);


        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.ETHER_ORE.get());

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.ETHER_ORE.get());


    }
}
