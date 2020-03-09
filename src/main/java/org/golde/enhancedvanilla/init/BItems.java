package org.golde.enhancedvanilla.init;

import org.golde.enhancedvanilla.blocks._core._BBlock;
import org.golde.enhancedvanilla.blocks._core._IBObjectBlock;
import org.golde.enhancedvanilla.init._core.shared.EnumCreativeTab;
import org.golde.enhancedvanilla.items.ItemMagnet;
import org.golde.enhancedvanilla.items.ItemWrench;
import org.golde.enhancedvanilla.items._core._BItem;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class BItems {

	public static _BItem exampleItem;
	public static ItemMagnet itemMagnet;
	public static ItemWrench wrench;
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		
		for(Block block : BBlocks.getAllBlocksReflection()) {
			if(block != null && block instanceof _IBObjectBlock) {
				_IBObjectBlock _block = (_IBObjectBlock)block;
				if(_block.shouldRegisterItem()) {
					event.getRegistry().register(getItem(block));
				}
			}
			
		}
		
		
		//Items
		event.getRegistry().register(exampleItem = new _BItem("example_item", EnumCreativeTab.UNFINISHED));
		event.getRegistry().register(itemMagnet = new ItemMagnet());
		event.getRegistry().register(wrench = new ItemWrench());
	}

	private static ItemBlock getItem(Block block) {
		ItemBlock ib = new ItemBlock(block);
		ib.setRegistryName(block.getRegistryName());
		return ib;
	}

	@SideOnly(Side.CLIENT)
	public static void initModels() {
		exampleItem.initModel();
		itemMagnet.initModel();
		wrench.initModel();
	}

}
