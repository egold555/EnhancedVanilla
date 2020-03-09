package org.golde.enhancedvanilla.blocks._core;

import javax.annotation.Nullable;

import org.golde.enhancedvanilla.EnhancedVanilla;
import org.golde.enhancedvanilla.init._core.shared.EnumCreativeTab;
import org.golde.enhancedvanilla.utils.InventoryUtils;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public abstract class _BBlockWithTE<TE extends TileEntity> extends _BBlock {

	private boolean keepInventory = false;
	private int guiID = -1;

	public _BBlockWithTE(String name) {
		this(name, EnumCreativeTab.FINISHED);
	}
	
	public _BBlockWithTE(String name, EnumCreativeTab type) {
		super(name, type);
		this.hasTileEntity = true;
	}

	public _BBlockWithTE(String name, Material material) {
		this(name, material, EnumCreativeTab.FINISHED);
	}
	
	public _BBlockWithTE(String name, Material material, EnumCreativeTab type) {
		super(name, material, type);
		this.hasTileEntity = true;
	}

	public abstract Class<TE> getTileEntityClass();

	public TE getTileEntity(IBlockAccess world, BlockPos pos) {
		return (TE)world.getTileEntity(pos);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public boolean hasTileEntity() {
		return true;
	}

	@Nullable
	@Override
	public abstract TE createTileEntity(World world, IBlockState state);

	@Nullable
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return createTileEntity(worldIn, getStateFromMeta(meta));
	}


	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity != null && tileentity instanceof IInventory && !keepInventory) {
			InventoryUtils.dropItems(worldIn, pos);
		}

		super.breakBlock(worldIn, pos, state);
		worldIn.removeTileEntity(pos);
	}

	//see for more info https://www.reddit.com/r/feedthebeast/comments/5mxwq9/psa_mod_devs_do_you_call_worldgettileentity_from/
	public TileEntity getTileEntitySafely(IBlockAccess blockAccess, BlockPos pos) {
		if (blockAccess instanceof ChunkCache) {
			return ((ChunkCache) blockAccess).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK);
		} else {
			return blockAccess.getTileEntity(pos);
		}
	}

	public void setGuiId(int id) {
		this.guiID = id;
	}

	public void setKeepInventoryOnBreak(boolean keepInventory) {
		this.keepInventory = keepInventory;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (player.isSneaking()) {
			return super.onBlockActivated(world, pos, state, player, hand, side, hitX, hitY, hitZ);
		}
		
		if (this.guiID > -1) {
			if (world.isRemote == false) { //If we dont block clientside only requests when opening guis, it doesnt work with FTB Utilities permissions. WHo knows idk I am confused
				player.openGui(EnhancedVanilla.instance, this.guiID, world, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		}
		
		return super.onBlockActivated(world, pos, state, player, hand, side, hitX, hitY, hitZ);
	}

}
