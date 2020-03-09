package org.golde.enhancedvanilla.blocks;

import javax.annotation.Nullable;

import org.golde.enhancedvanilla.EnhancedVanilla;
import org.golde.enhancedvanilla.blocks._core._BTE;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

//Mostly copied from dispenser. No clue what these methods do what so evrr
public abstract class TileEntityContainerModified extends _BTE implements IInventory {

	protected String customName;

	protected NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{
	}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		if (this.world.getTileEntity(this.pos) != this)
		{
			return false;
		}
		else
		{
			return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
		}
	}

	@Override
	public boolean hasCustomName()
	{
		return this.customName != null && !this.customName.isEmpty();
	}


	public void setCustomName(String p_190575_1_)
	{
		this.customName = p_190575_1_;
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return (ITextComponent)(this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]));
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		if(index > this.stacks.size() -1) {
			EnhancedVanilla.logger.error("**PREVENT CRASH** Tried to get item in slot " + index + " but max is " + (stacks.size() - 1));
			return ItemStack.EMPTY;
		}
		return (ItemStack)this.stacks.get(index);
	}

	/**
	 * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
	 */
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		ItemStack itemstack = ItemStackHelper.getAndSplit(this.stacks, index, count);

		if (!itemstack.isEmpty())
		{
			this.markDirty();
		}

		return itemstack;
	}

	/**
	 * Removes a stack from the given slot and returns it.
	 */
	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.stacks, index);
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int index, @Nullable ItemStack stack)
	{
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
	public void clear()
	{
		this.stacks.clear();
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack itemstack : this.stacks)
		{
			if (!itemstack.isEmpty())
			{
				return false;
			}
		}

		return true;
	}

	//Never seems to change. Minecraft legicy code needs to be cleaneed up
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public String getName()
	{
		return this.hasCustomName() ? this.customName : "bt.container." + getContainerNameForTranslation();
	}

	@Override
	public void readSyncNBT(NBTTagCompound compound)
	{
		super.readSyncNBT(compound);
		this.stacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);

		ItemStackHelper.loadAllItems(compound, stacks);

		if (compound.hasKey("CustomName", 8))
		{
			this.customName = compound.getString("CustomName");
		}
	}

	public NBTTagCompound writeSyncNBT(NBTTagCompound compound)
	{
		super.writeSyncNBT(compound);

		ItemStackHelper.saveAllItems(compound, this.stacks);

		if (this.hasCustomName())
		{
			compound.setString("CustomName", this.customName);
		}

		return compound;
	}
	
	public int addItemStack(ItemStack stack)
    {
        for (int i = 0; i < this.stacks.size(); ++i)
        {
        	
        	if(ItemStack.areItemsEqual(stacks.get(i), stack)) {
        		if(stacks.get(i).getCount() < 64) {
        			ItemStack copy = stacks.get(i).copy();
        			copy.grow(1);
        			this.setInventorySlotContents(i, copy);
        			return i;
        		}
        	}
        	else if (this.stacks.get(i).isEmpty())
            {
        		this.setInventorySlotContents(i, stack);
                return i;
            }
        }

        return -1;
    }

	public abstract String getContainerNameForTranslation();


}
