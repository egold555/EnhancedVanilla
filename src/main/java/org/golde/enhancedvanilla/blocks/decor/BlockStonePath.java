package org.golde.enhancedvanilla.blocks.decor;

import org.golde.enhancedvanilla.blocks._core._BBlock;
import org.golde.enhancedvanilla.init._core.shared.EnumCreativeTab;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStonePath extends _BBlock {

	private static final AxisAlignedBB NOTHING = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

	private static final AxisAlignedBB BOUNDING = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.0625, 1.0);

	public BlockStonePath() {
		super("stone_path", EnumCreativeTab.FINISHED);
		this.setHardness(0.75F);
		this.setSoundType(SoundType.STONE);
	}



	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)  {
		if (!this.canBlockStay(worldIn, pos)) {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
	}

	private boolean canBlockStay(World world, BlockPos pos) {
		return world.getBlockState(pos.down()).isSideSolid(world, pos, EnumFacing.UP);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BOUNDING;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)  {
		return NOTHING;
	}

}
