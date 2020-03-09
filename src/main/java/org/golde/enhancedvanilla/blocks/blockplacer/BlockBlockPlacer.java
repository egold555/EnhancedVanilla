package org.golde.enhancedvanilla.blocks.blockplacer;

import org.golde.enhancedvanilla.blocks._core._BBlockFacingWithTE;
import org.golde.enhancedvanilla.gui.ForgeGuiHandler;
import org.golde.enhancedvanilla.init._core.shared.EnumCreativeTab;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

public class BlockBlockPlacer extends _BBlockFacingWithTE<TileEntityBlockPlacer>{

	public BlockBlockPlacer() {
		super("block_placer", Material.IRON, EnumCreativeTab.UNFINISHED);
		setGuiId(ForgeGuiHandler.GUI_INDEX_BLOCK_PLACER);
	}

	@Override
	public Class<TileEntityBlockPlacer> getTileEntityClass() {
		return TileEntityBlockPlacer.class;
	}

	@Override
	public TileEntityBlockPlacer createTileEntity(World world, IBlockState state) {
		return new TileEntityBlockPlacer();
	}

}
