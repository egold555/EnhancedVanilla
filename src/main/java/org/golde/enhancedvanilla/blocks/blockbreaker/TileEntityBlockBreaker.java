package org.golde.enhancedvanilla.blocks.blockbreaker;

import org.golde.enhancedvanilla.blocks.TileEntityContainerModified;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;

public class TileEntityBlockBreaker extends TileEntityContainerModified implements ITickable {

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory() {
		return 9;
	}

	@Override
	public String getContainerNameForTranslation() {
		return "breaker";
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public void update() {

		if(!world.isRemote) {

			//USE Container.mergeItemStack
			EnumFacing enumfacing = this.world.getBlockState(this.pos).getValue(BlockBlockBreaker.FACING);

			BlockPos front = new BlockPos(pos.getX() + enumfacing.getFrontOffsetX(), enumfacing.getFrontOffsetY() + pos.getY(), pos.getZ() + enumfacing.getFrontOffsetZ());

			if(world.getBlockState(front) == null || world.isAirBlock(front)) {
				return;
			}

			IBlockState state = world.getBlockState(front);

			NonNullList<ItemStack> drops = NonNullList.create();

			if(world.getTileEntity(front) != null) {

				TileEntity te = world.getTileEntity(front);

				if(te instanceof IInventory) {
					IInventory inv = (IInventory) te;

					for(int i = 0; i < inv.getSizeInventory(); i++) {
						drops.add(inv.getStackInSlot(i));
						System.out.println("added: " + i + " - " + inv.getStackInSlot(i).getDisplayName());
					}
				}
				else {
					world.getBlockState(front).getBlock().getDrops(drops, world, front, state, 0);
				}
			}
			else {
				System.out.println("bs get drops");
				world.getBlockState(front).getBlock().getDrops(drops, world, front, state, 0);
			}

			boolean didSuccessfullyBreakBlock = true;

			for(ItemStack theDrops : drops) {
				System.out.println("dropppp00: " + theDrops.getDisplayName());
				if(addItemStack(theDrops) == -1) {
					didSuccessfullyBreakBlock = false;
				}
			}

			if(didSuccessfullyBreakBlock) {
				world.playEvent(2001, front, Block.getStateId(state)); //block break particles
				world.setBlockToAir(front);
			}
		}

	}



}
