package org.golde.enhancedvanilla.blocks._core;

import org.golde.enhancedvanilla.EnhancedVanilla;
import org.golde.enhancedvanilla.init._core.shared.EnumCreativeTab;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class _BBlock extends Block implements _IBObjectBlock {

	public _BBlock(String name, EnumCreativeTab type) {
		this(name, Material.ROCK, type);
	}

	public _BBlock(String name, Material mat, EnumCreativeTab type) {
		super(mat);
		setUnlocalizedName(EnhancedVanilla.MODID + "." + name);
		setRegistryName(name);
		if(shouldBeInCreatveTab()) {setCreativeTab(type.getTab());}
		setHardness(1.5F);
		setResistance(10.0F);
		setSoundType(SoundType.STONE);
	}

	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return MapColor.STONE;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));	
	}

	protected final void makeLikeBedrock() {
		setBlockUnbreakable();
		setResistance(6000000.0F);
		disableStats();
	}

}
