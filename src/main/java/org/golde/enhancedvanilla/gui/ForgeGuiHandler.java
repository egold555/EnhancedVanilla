package org.golde.enhancedvanilla.gui;

import org.golde.enhancedvanilla.blocks.blockbreaker.ContainerBlockBreaker;
import org.golde.enhancedvanilla.blocks.blockbreaker.GuiBlockBreaker;
import org.golde.enhancedvanilla.blocks.blockbreaker.TileEntityBlockBreaker;
import org.golde.enhancedvanilla.blocks.blockplacer.ContainerBlockPlacer;
import org.golde.enhancedvanilla.blocks.blockplacer.GuiBlockPlacer;
import org.golde.enhancedvanilla.blocks.blockplacer.TileEntityBlockPlacer;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ForgeGuiHandler implements IGuiHandler {

	public static final int GUI_INDEX_BLOCK_PLACER = 0;
	public static final int GUI_INDEX_BLOCK_BREAKER = 1;

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

		}

		return null;
	}

}
