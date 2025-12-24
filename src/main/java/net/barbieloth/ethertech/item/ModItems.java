package net.barbieloth.ethertech.item;

import net.barbieloth.ethertech.EtherTech;
import net.barbieloth.ethertech.entity.ModEntities;
import net.barbieloth.ethertech.item.custom.MetalDetectorItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, EtherTech.MOD_ID);

    public static void register(IEventBus eventBus) {
        ModItems.ITEMS.register(eventBus);
    }
    public static final RegistryObject<Item> ORANGE = ITEMS.register("orange",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ETHER = ITEMS.register("ether",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_ETHER = ITEMS.register("raw_ether",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ETHER_WAND = ITEMS.register("ether_wand",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> METAL_DETECTOR = ITEMS.register("metal_detector",
            () -> new MetalDetectorItem(new Item.Properties().durability(100)));

    public static final RegistryObject<Item> RHINO_EGG = ITEMS.register("rhino_egg",
            () -> new ForgeSpawnEggItem(ModEntities.RHINO, 0x7e9680,0xc5d1c5,
                    new Item.Properties()));

    public static final RegistryObject<Item> VITA_EGG = ITEMS.register("vita_egg",
            () -> new ForgeSpawnEggItem(ModEntities.VITA, 0x7e9680,0xc5d1c5,
                    new Item.Properties()));

    public static final RegistryObject<Item> JAGER = ITEMS.register("jager",
            () -> new Item(new Item.Properties().stacksTo(1)));



}
