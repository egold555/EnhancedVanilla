package org.golde.enhancedvanilla.proxy;

import org.golde.enhancedvanilla.EnhancedVanilla;
import org.golde.enhancedvanilla.gui.ForgeGuiHandler;
import org.golde.enhancedvanilla.init.BDispenserFunctions;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod.EventBusSubscriber
public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e) {
		
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
