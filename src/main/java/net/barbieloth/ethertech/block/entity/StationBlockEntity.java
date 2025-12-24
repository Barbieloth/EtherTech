package net.barbieloth.ethertech.block.entity;

import net.barbieloth.ethertech.item.ModItems;
import net.barbieloth.ethertech.screen.StationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StationBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(2);

    public static final  int INPUT_SLOT = 0;
    public static final  int OUTPUT_SLOT = 1;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress= 0;
    private int maxProgress = 78;

    public StationBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntities.STATION_BE.get(),p_155229_, p_155230_);
        this.data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i){
                    case 0 -> StationBlockEntity.this.progress;
                    case 1 -> StationBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int i1) {
                switch (i){
                    case 0 -> StationBlockEntity.this.progress = i1;
                    case 1 -> StationBlockEntity.this.maxProgress = i1;
                }

            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }



    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
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

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.ethertech.station");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new StationMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag p_187471_) {
        p_187471_.put("inventory", itemHandler.serializeNBT());
        p_187471_.putInt("station.progress", progress);
        super.saveAdditional(p_187471_);
    }

    @Override
    public void load(CompoundTag p_155245_) {
        super.load(p_155245_);
        itemHandler.deserializeNBT(p_155245_.getCompound("inventory"));
        progress = p_155245_.getInt("station.progress");
    }

    public void tick(Level p1532121, BlockPos p60505, BlockState pState1) {
        if(hasRecipe()){
            increaseCraftingProgress();
            setChanged(p1532121, p60505, pState1);

            if(hasProgressFinished()){
                craftItem();
                resetProgress();

            } else{
                resetProgress();
            }
        }

    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {
        ItemStack result = new ItemStack(ModItems.ETHER.get(), 1);
        this.itemHandler.extractItem(INPUT_SLOT, 1, false);
        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));

    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        boolean hasCraftingItem = this.itemHandler.getStackInSlot(OUTPUT_SLOT).getItem() == ModItems.RAW_ETHER.get();
        ItemStack result = new ItemStack(ModItems.ETHER.get());
        return hasCraftingItem && canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());

    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();

    }
}
