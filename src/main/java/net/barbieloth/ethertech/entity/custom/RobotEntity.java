package net.barbieloth.ethertech.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class RobotEntity extends PathfinderMob implements GeoEntity {
    // Кэш для анимаций GeckoLib
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public RobotEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    // Настройка атрибутов (Здоровье, Скорость, Урон)
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    // ИИ (Что моб делает)
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    // --- GECKOLIB ЛОГИКА ---

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // Регистрируем контроллер анимации
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<RobotEntity> event) {
        // Если моб двигается -> играем анимацию ходьбы
        if (event.isMoving()) {
            return event.setAndContinue(RawAnimation.begin().thenLoop("animation.vita.walk"));
        }
        // Иначе -> играем анимацию покоя (idle)
        return event.setAndContinue(RawAnimation.begin().thenLoop("animation.vita.idle"));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }
}
