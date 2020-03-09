package org.golde.enhancedvanilla.blocks._core;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public abstract class _BTESimpleInventory extends _BTE {

	protected SimpleItemStackHandler itemHandler = createItemHandler();

	@Override
	public void readSyncNBT(NBTTagCompound par1NBTTagCompound) {
		itemHandler = createItemHandler();
		itemHandler.deserializeNBT(par1NBTTagCompound);
	}

	@Override
	public NBTTagCompound writeSyncNBT(NBTTagCompound par1NBTTagCompound) {
		par1NBTTagCompound.merge(itemHandler.serializeNBT());
		return par1NBTTagCompound;
	}

	public abstract int getSizeInventory();

	protected SimpleItemStackHandler createItemHandler() {
		return new SimpleItemStackHandler(this, true);
	}

	public IItemHandlerModifiable getItemHandler() {
		return itemHandler;
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> cap, EnumFacing side) {
		return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(cap, side);
	}

	@Override
	public <T> T getCapability(@Nonnull Capability<T> cap, EnumFacing side) {
		if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemHandler);
		return super.getCapability(cap, side);
	}

	/* Extension of ItemStackHandler that uses our own slot array, allows for control of writing,
	   allows control over stack limits, and allows for itemstack-slot validation */
	protected static class SimpleItemStackHandler extends ItemStackHandler {

		private final boolean allowWrite;
		private final _BTESimpleInventory tile;

		public SimpleItemStackHandler(_BTESimpleInventory inv, boolean allowWrite) {
			super(inv.getSizeInventory());
			this.allowWrite = allowWrite;
			tile = inv;
		}

		@Nonnull
		@Override
		public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
			if(allowWrite) {
				return super.insertItem(slot, stack, simulate);
			} else return stack;
		}

		@Nonnull
		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			if(allowWrite) {
				return super.extractItem(slot, amount, simulate);
			} else return ItemStack.EMPTY;
		}

		@Override
		public void onContentsChanged(int slot) {
			tile.markDirty();
		}
	}
	
}
