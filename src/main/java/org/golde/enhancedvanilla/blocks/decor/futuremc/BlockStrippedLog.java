package org.golde.enhancedvanilla.blocks.decor.futuremc;

import org.golde.enhancedvanilla.EnhancedVanilla;
import org.golde.enhancedvanilla.blocks._core._IBObjectBlock;
import org.golde.enhancedvanilla.init._core.shared.EnumCreativeTab;

import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockStrippedLog extends BlockLog implements _IBObjectBlock {

	
	public BlockStrippedLog(String variant) {
		setUnlocalizedName(EnhancedVanilla.MODID + ".stripped_" + variant + "_log");
		setRegistryName("stripped_" + variant + "_log");
		if(shouldBeInCreatveTab()) {setCreativeTab(EnumCreativeTab.FINISHED.getTab());}
		this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, EnumAxis.Y));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));	
	}
	
	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return true;
	}
	
	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 5;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, LOG_AXIS);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = 0;
		EnumAxis enumfacing$axis = (EnumAxis)state.getValue(LOG_AXIS);

		if (enumfacing$axis == EnumAxis.X)
		{
			i |= 4;
		}
		else if (enumfacing$axis == EnumAxis.Z)
		{
			i |= 8;
		}

		return i;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
    {
		EnumAxis enumfacing$axis = EnumAxis.Y;
        int i = meta & 12;

        if (i == 4)
        {
            enumfacing$axis = EnumAxis.X;
        }
        else if (i == 8)
        {
            enumfacing$axis = EnumAxis.Z;
        }

        return this.getDefaultState().withProperty(LOG_AXIS, enumfacing$axis);
    }
	
}
