package org.golde.enhancedvanilla.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class PlayerUtils {

	public static void error(EntityPlayer player, String msg) {
        player.sendStatusMessage(new TextComponentString(TextFormatting.RED + msg), false);
    }
	
	public static void notify(EntityPlayer player, String msg) {
        player.sendStatusMessage(new TextComponentString(TextFormatting.GREEN + msg), false);
    }
	
}
