package org.golde.enhancedvanilla.proxy;

import org.golde.enhancedvanilla.EnhancedVanilla;
import org.golde.enhancedvanilla.gui.ForgeGuiHandler;
import org.golde.enhancedvanilla.init.BDispenserFunctions;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.oredict.OreDictionary;

@Mod.EventBusSubscriber
public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e) {
		OreDictionary.registerOre("wool", new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE));
	}
	
	public void init(FMLInitializationEvent e) {
		//idk this needs to be called server side. COnfusing cause guis on client side but its forge who knows
		NetworkRegistry.INSTANCE.registerGuiHandler(EnhancedVanilla.instance, new ForgeGuiHandler());
	}
	
	public void postInit(FMLPostInitializationEvent e) {
		BDispenserFunctions.postInit();
	}
	
	public void serverStarting(FMLServerStartingEvent e) {
		
	}
	
	public void registerRenders() {/*Client Only Method*/}
	
	public boolean isClient() {
		return false;
	}
	
}
