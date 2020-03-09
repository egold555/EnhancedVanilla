package org.golde.enhancedvanilla.items._core;

import org.golde.enhancedvanilla.EnhancedVanilla;
import org.golde.enhancedvanilla.init._core.shared.EnumCreativeTab;
import org.golde.enhancedvanilla.init._core.shared._IBObject;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class _BItem extends Item implements _IBObject {

	public _BItem(String name, EnumCreativeTab type) {
		setRegistryName(name);
        setUnlocalizedName(EnhancedVanilla.MODID + "." + name);
        if(shouldBeInCreatveTab()) {setCreativeTab(type.getTab());}
	}

	@SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

}
