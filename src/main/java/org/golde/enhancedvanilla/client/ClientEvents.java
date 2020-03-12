package org.golde.enhancedvanilla.client;

import org.golde.enhancedvanilla.EnhancedVanilla;
import org.golde.enhancedvanilla.client.gui.controls.GuiNewControls;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiOptions;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(modid = EnhancedVanilla.MODID, value = Side.CLIENT)
public class ClientEvents {

	@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
	public static void onGuiOpen(GuiOpenEvent e){
		if(e.getGui() instanceof GuiOptions) {
			Minecraft.getMinecraft().gameSettings.attackIndicator = 0;
		}
//		else if(e.getGui() instanceof GuiControls) {
//			System.out.println("EEE");
//			e.setGui(new GuiNewControls(new GuiOptions(null, Minecraft.getMinecraft().gameSettings), Minecraft.getMinecraft().gameSettings));
//		}
		
	}
	
}
