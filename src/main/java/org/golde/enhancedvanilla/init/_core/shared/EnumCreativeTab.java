package org.golde.enhancedvanilla.init._core.shared;

import org.golde.enhancedvanilla.init.BBlocks;
import org.golde.enhancedvanilla.init.BItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public enum EnumCreativeTab {

	FINISHED(new CreativeTabs("ev") {

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(BBlocks.charredBrick);
		}
	}), 
	
	UNFINISHED( new CreativeTabs("evDebug") {

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(BItems.exampleItem);
		}
	});

	private final CreativeTabs tab;
	EnumCreativeTab(CreativeTabs tab){
		this.tab = tab;
	}
	
	public CreativeTabs getTab() {
		return tab;
	}

}
