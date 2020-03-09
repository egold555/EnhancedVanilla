package org.golde.enhancedvanilla.blocks.decor.futuremc;

import org.golde.enhancedvanilla.EnhancedVanilla;
import org.golde.enhancedvanilla.blocks._core._IBObjectBlock;
import org.golde.enhancedvanilla.init._core.shared.EnumCreativeTab;

import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockNewTrapdoor extends BlockTrapDoor implements _IBObjectBlock {

	public BlockNewTrapdoor(String name) {
		super(Material.WOOD);
		setUnlocalizedName(EnhancedVanilla.MODID + "." + name);
		setRegistryName(name);
		if(shouldBeInCreatveTab()) {setCreativeTab(EnumCreativeTab.FINISHED.getTab());}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));	
	}

}
