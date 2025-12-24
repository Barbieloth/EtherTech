package net.barbieloth.ethertech.util;

import net.barbieloth.ethertech.EtherTech;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {


        public static final TagKey<Block> METAL_DETECTOR_VALUABLES = tag("metal_detector_valuables");





        private static TagKey<Block> tag(String name){
            return BlockTags.create(new ResourceLocation(EtherTech.MOD_ID, name));
        }
    }

    public static class Items {

    }
}
