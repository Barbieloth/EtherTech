package net.barbieloth.ethertech;

import com.mojang.logging.LogUtils;
import net.barbieloth.ethertech.block.ModBlocks;
import net.barbieloth.ethertech.block.entity.ModBlockEntities;
import net.barbieloth.ethertech.entity.ModEntities;
import net.barbieloth.ethertech.entity.client.RhinoRender;
import net.barbieloth.ethertech.entity.client.RobotRender;
import net.barbieloth.ethertech.entity.custom.RhinoEntity;
import net.barbieloth.ethertech.item.ModItems;
import net.barbieloth.ethertech.screen.EtherCrusherScreen;
import net.barbieloth.ethertech.screen.ModMenuTypes;
import net.barbieloth.ethertech.screen.StationMenuScreend;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

@Mod(EtherTech.MOD_ID)
public class EtherTech
{
    public static final String MOD_ID = "ethertech";
    private static final Logger LOGGER = LogUtils.getLogger();

    public EtherTech()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);
        REGISTRAR.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModBlockEntities.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        GeckoLib.initialize();
    }

    // Вкладка в креативе
    public static final DeferredRegister<CreativeModeTab> REGISTRAR =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final RegistryObject<CreativeModeTab> ETHERTECH_TAB = REGISTRAR.register("ethertech_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("creativetab." + MOD_ID + ".ethertech_tab"))
            .icon(() -> new ItemStack(ModItems.ORANGE.get()))
            .displayItems((params, output) -> {
                output.accept(ModItems.ORANGE.get());
                output.accept(ModItems.ETHER.get());
                output.accept(ModItems.RAW_ETHER.get());
                output.accept(ModItems.ETHER_WAND.get());
                output.accept(ModBlocks.ETHER_ORE.get());
                output.accept(ModItems.METAL_DETECTOR.get());
                output.accept(ModItems.RHINO_EGG.get());
                output.accept(ModItems.VITA_EGG.get());
                output.accept((ModItems.JAGER.get()));
                output.accept(ModBlocks.STATION.get());
            })
            .build()
    );

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Общий код инициализации
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Регистрируем рендер для Носорога
            EntityRenderers.register(ModEntities.RHINO.get(), RhinoRender::new);
            EntityRenderers.register(ModEntities.VITA.get(), RobotRender::new);
            MenuScreens.register(ModMenuTypes.ETHER_CRUSHER_MENU.get(), EtherCrusherScreen::new);
            MenuScreens.register(ModMenuTypes.STATION_MENU.get(), StationMenuScreend::new);
        }
    }
}