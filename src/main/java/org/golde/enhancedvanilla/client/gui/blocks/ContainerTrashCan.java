package org.golde.enhancedvanilla.client.gui.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class ContainerTrashCan extends ContainerChest {

	public ContainerTrashCan(EntityPlayer player) {
		super(player.inventory, new IIventoryTrashCan(), player);
	}

}