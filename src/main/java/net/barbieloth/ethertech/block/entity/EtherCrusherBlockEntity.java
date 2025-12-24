package net.barbieloth.ethertech.block.entity;

import net.barbieloth.ethertech.item.ModItems;
import net.barbieloth.ethertech.screen.EtherCrusherMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EtherCrusherBlockEntity extends BlockEntity implements MenuProvider {

    // Слот 0 - вхід, Слот 1 - вихід
    private final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> stack.is(ModItems.RAW_ETHER.get()); // Дозволяємо сирий ефір на вхід
                case 1 -> false; // Забороняємо класти щось у вихід вручну
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 100;

    // Файл: src/main/java/net/barbieloth/ethertech/block/entity/EtherCrusherBlockEntity.java
    public EtherCrusherBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ETHER_CRUSHER_BE.get(), pPos, pBlockState);
        // Ініціалізуємо відразу для стабільної роботи Menu та воронок
        this.lazyItemHandler = LazyOptional.of(() -> itemHandler);

        this.data = new ContainerData() {
            @Override public int get(int i) { return i == 0 ? progress : maxProgress; }
            @Override public void set(int i, int i1) { if (i == 0) progress = i1; }
            @Override public int getCount() { return 2; }
        };
    }

    private void processItem() {
        // Видаляємо 1 сирий ефір
        this.itemHandler.extractItem(0, 1, false);
        // Вставляємо 1 звичайний ефір у вихідний слот
        this.itemHandler.insertItem(1, new ItemStack(ModItems.ETHER.get(), 1), false);

        this.setChanged(); // Повідомляємо світ про зміни в інвентарі
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Ether Crusher");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new EtherCrusherMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, EtherCrusherBlockEntity pBlockEntity) {
        if (pLevel.isClientSide()) return;

        if (pBlockEntity.canProcess()) {
            pBlockEntity.progress++;
            setChanged(pLevel, pPos, pState);

            if (pBlockEntity.progress >= pBlockEntity.maxProgress) {
                pBlockEntity.processItem();
                pBlockEntity.progress = 0;
            }
        } else {
            if (pBlockEntity.progress != 0) {
                pBlockEntity.progress = 0;
                setChanged(pLevel, pPos, pState);
            }
        }
    }

    private boolean canProcess() {
        ItemStack input = itemHandler.getStackInSlot(0);
        ItemStack output = itemHandler.getStackInSlot(1);

        // Перевіряємо наявність сирого ефіру та чи є місце для результату
        return input.is(ModItems.RAW_ETHER.get()) &&
                (output.isEmpty() || (output.is(ModItems.ETHER.get()) && output.getCount() < output.getMaxStackSize()));
    }



    // ВАЖЛИВО: Без цього меню не зможе "побачити" предмети всередині
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("progress", progress);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("progress");
    }
    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        load(tag);
    }
}