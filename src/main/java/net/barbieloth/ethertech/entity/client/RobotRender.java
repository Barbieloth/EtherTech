package net.barbieloth.ethertech.entity.client;

import net.barbieloth.ethertech.EtherTech;
import net.barbieloth.ethertech.entity.custom.RhinoEntity;
import net.barbieloth.ethertech.entity.custom.RobotEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RobotRender extends GeoEntityRenderer<RobotEntity> {
    public RobotRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new RobotModel());
    }

    @Override
    public ResourceLocation getTextureLocation(RobotEntity animatable) {
        return new ResourceLocation(EtherTech.MOD_ID, "textures/entity/vita.png");
    }
}