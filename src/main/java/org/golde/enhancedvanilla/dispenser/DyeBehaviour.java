package org.golde.enhancedvanilla.dispenser;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class DyeBehaviour extends BehaviorDefaultDispenseItem  {

	private static final DyeBehaviour INSTANCE = new DyeBehaviour();

	public static DyeBehaviour getInstance() {
		return INSTANCE;
	}

	@Override
	public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
		TileEntityDispenser te = source.getBlockTileEntity();
		IBlockState s = te.getWorld().getBlockState(source.getBlockPos());
		EnumFacing f = (EnumFacing) s.getProperties().get(BlockDirectional.FACING);

		BlockPos destination = source.getBlockPos().add(extend(f.getDirectionVec(), 1));
		List<EntitySheep> sheeps = te.getWorld().getEntitiesWithinAABB(EntitySheep.class, new AxisAlignedBB(destination.add(extend(f.getDirectionVec(), 0))));
		EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(stack.getMetadata());

		if (sheeps != null && sheeps.size() > 0) {
			for (EntitySheep sheep : sheeps) {
				if (sheep.getFleeceColor() != enumdyecolor) {
					sheep.setFleeceColor(enumdyecolor);
					stack.grow(-1);
					break;
				}
			}
		} 
		else if (enumdyecolor == EnumDyeColor.WHITE) {
			Block destBlock = source.getWorld().getBlockState(destination).getBlock();
			if (destBlock instanceof IGrowable) {
				ItemDye.applyBonemeal(stack, te.getWorld(), destination); // Apply bonemeal to seeds/saplings as usual, if there's any
			} 
			else {
				ItemDye.applyBonemeal(stack, te.getWorld(), destination.down()); // otherwise apply it to grass below
			}
			if (te.getWorld().isRemote) {
				ItemDye.spawnBonemealParticles(te.getWorld(), destination, 5);
			}
		}

		return stack;
	}

	private Vec3i extend(Vec3i v, int i) {
		return new Vec3i(v.getX() * i, v.getY() * i, v.getZ() * i);
	}
}
