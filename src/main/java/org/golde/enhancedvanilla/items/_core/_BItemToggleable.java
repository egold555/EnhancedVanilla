package org.golde.enhancedvanilla.items._core;

import java.util.List;

import org.golde.enhancedvanilla.init._core.shared.EnumCreativeTab;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public abstract class _BItemToggleable extends _BItem {
	
	private static final String ENABLED_KEY = "IsEnabled";
	
	public _BItemToggleable(String name, EnumCreativeTab type) {
		super(name, type);
	}

	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
        if (!worldIn.isRemote && player.isSneaking()) {
        	worldIn.playSound(null, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 10F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
            changeEnabled(player, hand);
            return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
        }
        return super.onItemRightClick(worldIn, player, hand);
    }
	
	@Override
    public boolean hasEffect(ItemStack stack) {
        return isEnabled(stack);
    }
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add("Enabled: " + isEnabled(stack));
	}

	public static boolean isEnabled(ItemStack stack) {
		return stack.hasTagCompound() && stack.getTagCompound().getBoolean(ENABLED_KEY);
	}

	public static void changeEnabled(EntityPlayer player, EnumHand hand) {
		changeEnabled(player.getHeldItem(hand));
	}

	public static void changeEnabled(ItemStack stack) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setBoolean(ENABLED_KEY, false);
		}

		boolean isEnabled = isEnabled(stack);
		stack.getTagCompound().setBoolean(ENABLED_KEY, !isEnabled);
	}
	
	
	
}
