package org.golde.enhancedvanilla.blocks.blockbreaker;

import org.golde.enhancedvanilla.blocks._core._BBlockFacingWithTE;
import org.golde.enhancedvanilla.gui.ForgeGuiHandler;
import org.golde.enhancedvanilla.init._core.shared.EnumCreativeTab;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

public class BlockBlockBreaker extends _BBlockFacingWithTE<TileEntityBlockBreaker>{

	public BlockBlockBreaker() {
		super("block_breaker", Material.IRON, EnumCreativeTab.UNFINISHED);
		setGuiId(ForgeGuiHandler.GUI_INDEX_BLOCK_BREAKER);
	}

	@Override
	public Class<TileEntityBlockBreaker> getTileEntityClass() {
		return TileEntityBlockBreaker.class;
	}

	@Override
	public TileEntityBlockBreaker createTileEntity(World world, IBlockState state) {
		return new TileEntityBlockBreaker();
	}

}
