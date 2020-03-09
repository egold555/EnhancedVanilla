package org.golde.enhancedvanilla.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTUtils {

	public static void clearStorage(ItemStack stack, String... keys) {
        if (stack.hasTagCompound()) {
            NBTTagCompound compound = stack.getTagCompound();
            for (String key : keys) {
                compound.removeTag(key);
            }
        }
    }
	
	public static NBTTagCompound getTagCompound(ItemStack stack) {
        NBTTagCompound tagCompound = stack.getTagCompound();

        if (tagCompound == null) {
            tagCompound = new NBTTagCompound();
            stack.setTagCompound(tagCompound);
        }

        return tagCompound;
    }
	
}
