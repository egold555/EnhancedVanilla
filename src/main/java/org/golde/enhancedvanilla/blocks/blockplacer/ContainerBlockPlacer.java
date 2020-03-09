package org.golde.enhancedvanilla.blocks.blockplacer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ContainerBlockPlacer extends Container {

	private final IInventory dispenserInventory;

	public ContainerBlockPlacer(IInventory playerInventory, TileEntityBlockPlacer dispenserInventoryIn)
	{
		this.dispenserInventory = dispenserInventoryIn;


		this.addSlotToContainer(new SlotOnlyBlocks(dispenserInventoryIn, 0, 62 + 1 * 18, 17 + 1 * 18));



		for (int k = 0; k < 3; ++k)
		{
			for (int i1 = 0; i1 < 9; ++i1)
			{
				this.addSlotToContainer(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
			}
		}

		for (int l = 0; l < 9; ++l)
		{
			this.addSlotToContainer(new Slot(playerInventory, l, 8 + l * 18, 142));
		}
	}

	/**
	 * Determines whether supplied player can use this container
	 */
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return this.dispenserInventory.isUsableByPlayer(playerIn);
	}

	/**
	 * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
	 * inventory and the other inventory(s).
	 */
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < 9)
			{
				if (!this.mergeItemStack(itemstack1, 9, 37, true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 0, 9, false))
			{
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount())
			{
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemstack1);
		}

		return itemstack;
	}

	public class SlotOnlyBlocks extends Slot {

		public SlotOnlyBlocks(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			if(stack == null || stack.getItem() == null) {
				return false;
			}
			return stack.getItem() instanceof ItemBlock; 
		}

	}

}
