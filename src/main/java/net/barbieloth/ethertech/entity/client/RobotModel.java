package net.barbieloth.ethertech.entity.client;

import net.barbieloth.ethertech.EtherTech;
import net.barbieloth.ethertech.entity.custom.RhinoEntity;
import net.barbieloth.ethertech.entity.custom.RobotEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class RobotModel extends GeoModel<RobotEntity> {
    @Override
    public ResourceLocation getModelResource(RobotEntity animatable) {
        return new ResourceLocation(EtherTech.MOD_ID, "geo/vita.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RobotEntity animatable) {
        return new ResourceLocation(EtherTech.MOD_ID, "textures/entity/vita.png");
    }

    @Override
    public ResourceLocation getAnimationResource(RobotEntity animatable) {
        return new ResourceLocation(EtherTech.MOD_ID, "animations/vita.animation.json");
    }
}