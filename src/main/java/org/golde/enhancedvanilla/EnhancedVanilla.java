package org.golde.enhancedvanilla;

import java.util.Random;
import java.util.Timer;

import org.apache.logging.log4j.Logger;
import org.golde.enhancedvanilla.proxy.CommonProxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = EnhancedVanilla.MODID, name = EnhancedVanilla.MODNAME, version = EnhancedVanilla.VERSION, dependencies = "", useMetadata = true)
public class EnhancedVanilla {

	public static final String MODID = "ev";
    public static final String MODNAME = "EnhancedVanilla";
    public static final String VERSION = "1.0.0";
    
    @SidedProxy(modId = EnhancedVanilla.MODID, clientSide = "org.golde.enhancedvanilla.proxy.ClientProxy", serverSide = "org.golde.enhancedvanilla.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance(EnhancedVanilla.MODID)
    public static EnhancedVanilla instance;
    
    public static Logger logger;
    public static Random RANDOM;
	
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	RANDOM = new Random();
        logger = event.getModLog();
        proxy.preInit(event);
        proxy.registerRenders();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
    
    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent e) {
    	proxy.serverStarting(e);
    }
    
}
