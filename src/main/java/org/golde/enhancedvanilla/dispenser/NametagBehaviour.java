package org.golde.enhancedvanilla.dispenser;

import java.util.List;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NametagBehaviour extends BehaviorDefaultDispenseItem {

	private static final NametagBehaviour INSTANCE = new NametagBehaviour();

	public static NametagBehaviour getInstance() {
		return INSTANCE;
	}

	@Override
	public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
		
		if (stack.hasDisplayName()) {
            World w = source.getWorld();
            IPosition pos = BlockDispenser.getDispensePosition(source);
            EnumFacing f = source.getBlockState().getValue(BlockDirectional.FACING);
            BlockPos dest = source.getBlockPos().add(f.getDirectionVec());

            List<EntityLiving> entityLivingList = w.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(dest));

            if (entityLivingList.size() > 0) {
                if (entityLivingList.get(0).hasCustomName() && entityLivingList.get(0).getCustomNameTag().equals(stack.getDisplayName()))
                    return stack;
                entityLivingList.get(0).setCustomNameTag(stack.getDisplayName());
                stack.grow(-1);
            }
        }
		
		return stack;
	}
	
}
