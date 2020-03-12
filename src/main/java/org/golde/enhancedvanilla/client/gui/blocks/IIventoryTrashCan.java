package org.golde.enhancedvanilla.client.gui.blocks;

import org.golde.enhancedvanilla.EnhancedVanilla;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class IIventoryTrashCan implements IInventory {

	protected NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);

	@Override
	public String getName() {
		return "Trash Can";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public int getSizeInventory() {
		return 32;
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.stacks)
		{
			if (!itemstack.isEmpty())
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if(index > this.stacks.size() -1) {
			EnhancedVanilla.logger.error("**PREVENT CRASH** Tried to get item in slot " + index + " but max is " + (stacks.size() - 1));
			return ItemStack.EMPTY;
		}
		return (ItemStack)this.stacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack itemstack = ItemStackHelper.getAndSplit(this.stacks, index, count);

		if (!itemstack.isEmpty())
		{
			this.markDirty();
		}

		return itemstack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.stacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if(stack != null) {

			if(index > this.stacks.size() - 1) {
				EnhancedVanilla.logger.error("**PREVENT STACKTRACE** Tried to put item in slot " + index + " but max is " + (stacks.size() - 1));
				return;
			}
			this.stacks.set(index, stack);


			if (stack.getCount() > this.getInventoryStackLimit())
			{
				stack.setCount(this.getInventoryStackLimit());
			}
		}
		this.markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		this.stacks.clear();
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString(getName());
	}

	@Override
	public void markDirty() {

	}

}
