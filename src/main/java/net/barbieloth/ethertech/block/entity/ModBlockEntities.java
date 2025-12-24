package net.barbieloth.ethertech.block.entity;

import net.barbieloth.ethertech.EtherTech;
import net.barbieloth.ethertech.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, EtherTech.MOD_ID);

    public static final RegistryObject<BlockEntityType<StationBlockEntity>> STATION_BE =
            BLOCK_ENTITIES.register("station_be", () ->
                    BlockEntityType.Builder.of(StationBlockEntity::new,
                            ModBlocks.STATION.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);

    }
}
