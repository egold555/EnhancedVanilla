package org.golde.enhancedvanilla.events;

import java.lang.reflect.Field;

import org.golde.enhancedvanilla.EnhancedVanilla;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = EnhancedVanilla.MODID)
public class IHateTheCombatUpdate {

	private static Field targetField;

	@SubscribeEvent
	public static void handleLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
		try {
			if (event.getEntity() instanceof EntityPlayerMP) {
				final EntityPlayerMP player = (EntityPlayerMP)event.getEntity();
				if (targetField.getInt(player) < 9999) {
					targetField.set(player, 1000);
				}
			}
		}
		catch (Exception ex) {}
	}

	static {
		targetField = EntityLivingBase.class.getDeclaredFields()[24];
		targetField.setAccessible(true);
	}

}
