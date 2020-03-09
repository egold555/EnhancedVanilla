package org.golde.enhancedvanilla.blocks.blockplacer;

import javax.annotation.Nullable;

import org.golde.enhancedvanilla.blocks.TileEntityContainerModified;
import org.golde.enhancedvanilla.blocks._core._BTE;
import org.golde.enhancedvanilla.blocks.blockbreaker.BlockBlockBreaker;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.audio.SoundList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityBlockPlacer extends TileEntityContainerModified implements ITickable {

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public String getContainerNameForTranslation() {
		return "placer";
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return stack.getItem() instanceof ItemBlock;
	}

	@Override
	public void update() {

		if(!world.isRemote) {

			EnumFacing enumfacing = this.world.getBlockState(this.pos).getValue(BlockBlockPlacer.FACING);

			BlockPos front = new BlockPos(pos.getX() + enumfacing.getFrontOffsetX(), enumfacing.getFrontOffsetY() + pos.getY(), pos.getZ() + enumfacing.getFrontOffsetZ());

			if(world.getBlockState(front) == null || world.isAirBlock(front)) {

				if(this.getStackInSlot(0) != null && this.getStackInSlot(0).getItem() != null) {

					Block block = Block.getBlockFromItem(this.getStackInSlot(0).getItem());

					if(block == Blocks.AIR) {
						return;
					}

					world.setBlockState(front, block.getDefaultState());
					setTileEntityNBT(front, this.getStackInSlot(0));

					this.getStackInSlot(0).shrink(1);

					SoundType soundtype = block.getSoundType(block.getDefaultState(), world, front, null);
					world.playSound(null, front, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
				}

			}


		}

	}

	private void setTileEntityNBT(BlockPos posIn, ItemStack stackIn)
    {
        MinecraftServer minecraftserver = world.getMinecraftServer();

        if (minecraftserver == null)
        {
            return;
        }
        else
        {
            NBTTagCompound nbttagcompound = stackIn.getSubCompound("BlockEntityTag");

            if (nbttagcompound != null)
            {
                TileEntity tileentity = world.getTileEntity(posIn);

                if (tileentity != null)
                {
                    if (!world.isRemote && tileentity.onlyOpsCanSetNbt())
                    {
                        return;
                    }

                    NBTTagCompound nbttagcompound1 = tileentity.writeToNBT(new NBTTagCompound());
                    NBTTagCompound nbttagcompound2 = nbttagcompound1.copy();
                    nbttagcompound1.merge(nbttagcompound);
                    nbttagcompound1.setInteger("x", posIn.getX());
                    nbttagcompound1.setInteger("y", posIn.getY());
                    nbttagcompound1.setInteger("z", posIn.getZ());

                    if (!nbttagcompound1.equals(nbttagcompound2))
                    {
                        tileentity.readFromNBT(nbttagcompound1);
                        tileentity.markDirty();
                        return;
                    }
                }
            }

            return;
        }
    }

}
