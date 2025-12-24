package net.barbieloth.ethertech.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.barbieloth.ethertech.EtherTech;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class EtherCrusherScreen extends AbstractContainerScreen<EtherCrusherMenu> {
    // Шлях до текстури інтерфейсу.
    // Файл має бути тут: src/main/resources/assets/ethertech/textures/gui/ether_crusher.png
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(EtherTech.MOD_ID, "textures/gui/ether_crusher.png");

    public EtherCrusherScreen(EtherCrusherMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        // Приховуємо стандартні написи заголовків, якщо вони перекривають дизайн
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        // Малюємо основний фон GUI
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        // Малюємо стрілку прогресу
        renderProgressArrow(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrushing()) {
            // Параметри blit: (Текстура, X на екрані, Y на екрані, X на текстурі, Y на текстурі, Ширина, Висота)
            // 176 - зазвичай початок додаткових елементів на текстурі печі (стрілки)
            // Ми малюємо стрілку зверху вниз залежно від прогресу
            guiGraphics.blit(TEXTURE, x + 85, y + 30, 176, 0, 8, menu.getScaledProgress());
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}