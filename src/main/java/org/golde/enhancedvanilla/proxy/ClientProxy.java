package org.golde.enhancedvanilla.proxy;

import org.golde.enhancedvanilla.init.BBlocks;
import org.golde.enhancedvanilla.init.BItems;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
	}
	
	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
	}
	
	@Override
	public void registerRenders() {
		BBlocks.bindTESR();
	}
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		BItems.initModels();
		BBlocks.initModels();
	}
	
	@Override
	public boolean isClient() {
		return true;
	}
	
}
