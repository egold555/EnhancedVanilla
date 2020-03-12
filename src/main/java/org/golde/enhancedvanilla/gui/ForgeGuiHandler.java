package org.golde.enhancedvanilla.gui;

import org.golde.enhancedvanilla.blocks.blockbreaker.TileEntityBlockBreaker;
import org.golde.enhancedvanilla.blocks.blockplacer.TileEntityBlockPlacer;
import org.golde.enhancedvanilla.blocks.trashcan.TileEntityTrashCan;
import org.golde.enhancedvanilla.client.gui.blocks.ContainerTrashCan;
import org.golde.enhancedvanilla.client.gui.blocks.GuiTrashCan;
import org.golde.enhancedvanilla.client.gui.blocks.blockbreaker.ContainerBlockBreaker;
import org.golde.enhancedvanilla.client.gui.blocks.blockbreaker.GuiBlockBreaker;
import org.golde.enhancedvanilla.client.gui.blocks.blockplacer.ContainerBlockPlacer;
import org.golde.enhancedvanilla.client.gui.blocks.blockplacer.GuiBlockPlacer;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ForgeGuiHandler implements IGuiHandler {

	public static final int GUI_INDEX_BLOCK_PLACER = 0;
	public static final int GUI_INDEX_BLOCK_BREAKER = 1;
	public static final int GUI_INDEX_TRASH_CAN = 2;

	//Returns Containers
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		BlockPos p = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(p);

		if(te == null) {
			return null;
		}

		if (te instanceof TileEntityBlockBreaker) {
			return new ContainerBlockBreaker(player.inventory, (TileEntityBlockBreaker) te);
		}
		
		if (te instanceof TileEntityBlockPlacer) {
			return new ContainerBlockPlacer(player.inventory, (TileEntityBlockPlacer) te);
		}
		
		if (te instanceof TileEntityTrashCan) {
			return new ContainerTrashCan(player);
		}


		return null;
	}

	//return new Gui
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		BlockPos p = new BlockPos(x, y, z);
		if (world instanceof WorldClient) {
			TileEntity te = world.getTileEntity(p);

			if(te == null) {
				return null;
			}

			if (te instanceof TileEntityBlockBreaker) {
				return new GuiBlockBreaker(player.inventory, (TileEntityBlockBreaker) te);
			}
			
			else if (te instanceof TileEntityBlockPlacer) {
				return new GuiBlockPlacer(player.inventory, (TileEntityBlockPlacer) te);
			}
			
			else if (te instanceof TileEntityTrashCan) {
				return new GuiTrashCan(player.inventory);
			}

		}

		return null;
	}

}
