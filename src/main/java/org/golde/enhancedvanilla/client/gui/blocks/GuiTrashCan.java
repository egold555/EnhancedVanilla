package org.golde.enhancedvanilla.client.gui.blocks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.IInventory;

public class GuiTrashCan extends GuiChest {

	public GuiTrashCan(IInventory playerInv) {
		super(playerInv, new IIventoryTrashCan());
	}

}
