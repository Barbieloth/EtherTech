package net.barbieloth.ethertech.block.custom;

import net.barbieloth.ethertech.block.entity.EtherCrusherBlockEntity;
import net.barbieloth.ethertech.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class EtherCrusherBlock extends BaseEntityBlock {
    public EtherCrusherBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            if (isStructureValid(pLevel, pPos)) {
                BlockEntity entity = pLevel.getBlockEntity(pPos);
                if (entity instanceof EtherCrusherBlockEntity) {
                    NetworkHooks.openScreen(((ServerPlayer) pPlayer), (EtherCrusherBlockEntity) entity, pPos);
                }
            } else {
                pPlayer.sendSystemMessage(Component.literal("Потрібна структура 3x3x3 із залізних блоків (ви в центрі основи)!"));
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    private boolean isStructureValid(Level level, BlockPos pos) {
        // Перевірка куба 3x3x3. Контролер — центр нижнього шару.
        for (int x = -1; x <= 1; x++) {
            for (int y = 0; y <= 2; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (x == 0 && y == 0 && z == 0) continue;
                    if (!level.getBlockState(pos.offset(x, y, z)).is(Blocks.IRON_BLOCK)) return false;
                }
            }
        }
        return true;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new EtherCrusherBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        // Реєструємо тікер тільки для серверної сторони
        if (pLevel.isClientSide()) return null;

        return createTickerHelper(pBlockEntityType, ModBlockEntities.ETHER_CRUSHER_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> EtherCrusherBlockEntity.tick(pLevel1, pPos, pState1, pBlockEntity));
    }
}