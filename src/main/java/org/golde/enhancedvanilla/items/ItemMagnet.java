package org.golde.enhancedvanilla.items;

import java.util.List;

import org.golde.enhancedvanilla.init._core.shared.EnumCreativeTab;
import org.golde.enhancedvanilla.items._core._BItemToggleable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class ItemMagnet extends _BItemToggleable {

	private int range = 20;

	public ItemMagnet() {
		super("item_magnet", EnumCreativeTab.FINISHED);
	}



	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
		if (entity instanceof EntityPlayer && !world.isRemote && isEnabled(stack)) {
			EntityPlayer player = (EntityPlayer) entity;
			if (player.isCreative() || player.isSpectator()) return;
			if (!entity.isSneaking()) {
				//Get all the Items in the area

				List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(entity.posX - range, entity.posY - range, entity.posZ - range, entity.posX + range, entity.posY + range, entity.posZ + range));
				if (!items.isEmpty()) {
					for (EntityItem item : items) {
						if (item.getEntityData().getBoolean("PreventRemoteMovement")) continue;
						if (!item.isDead && !item.cannotPickup()) {
							int energyForItem = 50 * item.getItem().getCount();

							if (true/*this.getEnergyStored(stack) >= energyForItem*/) {
								ItemStack oldItem = item.getItem().copy();

								item.onCollideWithPlayer(player);

								if (!player.capabilities.isCreativeMode) {
									if (item.isDead || !ItemStack.areItemStacksEqual(item.getItem(), oldItem)) {
										//this.extractEnergyInternal(stack, energyForItem, false);
									}
								}
							}
						}
					}
				}
			}
		}
	}

}
