package net.barbieloth.ethertech.event;

import net.barbieloth.ethertech.EtherTech;
import net.barbieloth.ethertech.entity.ModEntities;
import net.barbieloth.ethertech.entity.custom.RhinoEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EtherTech.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.RHINO.get(), RhinoEntity.createAttributes().build());
        event.put(ModEntities.VITA.get(), RhinoEntity.createAttributes().build());

        }
    }



