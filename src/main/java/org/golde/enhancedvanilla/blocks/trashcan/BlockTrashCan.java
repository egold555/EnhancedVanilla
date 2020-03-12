package org.golde.enhancedvanilla.blocks.trashcan;

import org.golde.enhancedvanilla.blocks._core._BBlock;
import org.golde.enhancedvanilla.blocks._core._BBlockWithTE;
import org.golde.enhancedvanilla.gui.ForgeGuiHandler;
import org.golde.enhancedvanilla.init._core.shared.EnumCreativeTab;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTrashCan extends _BBlockWithTE<TileEntityTrashCan> {

	public BlockTrashCan() {
		super("trash_can", Material.IRON, EnumCreativeTab.FINISHED);
		setGuiId(ForgeGuiHandler.GUI_INDEX_TRASH_CAN);
	}

	@Override
	public Class<TileEntityTrashCan> getTileEntityClass() {
		return TileEntityTrashCan.class;
	}

	@Override
	public TileEntityTrashCan createTileEntity(World world, IBlockState state) {
		return new TileEntityTrashCan();
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
    {
        return false;
    }
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

}
