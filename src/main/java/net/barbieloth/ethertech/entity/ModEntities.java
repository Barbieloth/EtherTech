package net.barbieloth.ethertech.entity;

import net.barbieloth.ethertech.EtherTech;
import net.barbieloth.ethertech.entity.custom.RhinoEntity;
import net.barbieloth.ethertech.entity.custom.RobotEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, EtherTech.MOD_ID);

    public static final RegistryObject<EntityType<RhinoEntity>> RHINO =
            ENTITY_TYPES.register("rhino",() -> EntityType.Builder.of(RhinoEntity::new, MobCategory.CREATURE)
                    .sized(2.5f,2.5f).build("rhino"));

    public static final RegistryObject<EntityType<RobotEntity>> VITA =
            ENTITY_TYPES.register("vita",
                    () -> EntityType.Builder.of(RobotEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.8f) // Ширина и Высота хитбокса
                            .build("vita"));



    public static void register(IEventBus bus) {
        ENTITY_TYPES.register(bus);
    }
}
