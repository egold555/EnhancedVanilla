package org.golde.enhancedvanilla.utils;

import net.minecraft.item.ItemStack;

public class StackUtils {

    public static ItemStack grow(ItemStack s, int i) {
        s.grow(i);
        return s;
    }

    public static ItemStack shrink(ItemStack s, int i) {
        s.shrink(i);
        return s;
    }
	
}
