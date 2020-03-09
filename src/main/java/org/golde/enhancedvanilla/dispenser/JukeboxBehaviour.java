package org.golde.enhancedvanilla.dispenser;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class JukeboxBehaviour extends BehaviorDefaultDispenseItem {

	private static final JukeboxBehaviour INSTANCE = new JukeboxBehaviour();

	public static JukeboxBehaviour getInstance() {
		return INSTANCE;
	}

	@Override
	public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {

		World w = source.getBlockTileEntity().getWorld();
		EnumFacing f = (EnumFacing) w.getBlockState(source.getBlockPos()).getProperties().get(BlockDispenser.FACING);

		BlockPos dest = source.getBlockPos().add(extend(f.getDirectionVec(), 1));

		TileEntity te = w.getTileEntity(dest);
		IBlockState st = w.getBlockState(dest);
		Block b = st.getBlock();

		if (te != null && te instanceof BlockJukebox.TileEntityJukebox) {
			BlockJukebox.TileEntityJukebox tJb = (BlockJukebox.TileEntityJukebox) te;

			if (tJb.getRecord().isEmpty()) {
				((BlockJukebox) b).insertRecord(w, dest, st, stack);
				w.playEvent(null, 1010, dest, Item.getIdFromItem(stack.getItem()));
				stack.grow(-1);
			} else {
				int slot = getFreeSlot((TileEntityDispenser) source.getBlockTileEntity());

				if (slot == -1)
					ejectRecord(w, tJb);
				else
					((TileEntityDispenser) source.getBlockTileEntity()).setInventorySlotContents(slot, tJb.getRecord());

				((BlockJukebox) b).insertRecord(w, dest, st, stack);
				w.playEvent(null, 1010, dest, Item.getIdFromItem(stack.getItem()));
				stack.grow(-1);
			}
		}

		return stack;
	}

	private void ejectRecord(World worldIn, BlockJukebox.TileEntityJukebox te) {
		if (te.getRecord().isEmpty())
			return;

		worldIn.playEvent(1010, te.getPos(), 0);
		worldIn.playRecord(te.getPos(), null);
		te.setRecord(ItemStack.EMPTY);
		float f = 0.7F;
		double d0 = (double)(worldIn.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
		double d1 = (double)(worldIn.rand.nextFloat() * f) + (double)(1.0F - f) * 0.2D + 0.6D;
		double d2 = (double)(worldIn.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
		ItemStack itemstack1 = te.getRecord().copy();
		EntityItem entityitem = new EntityItem(worldIn, (double)te.getPos().getX() + d0, (double)te.getPos().getY() + d1 + 1, (double)te.getPos().getZ() + d2, itemstack1);
		entityitem.setDefaultPickupDelay();
		worldIn.spawnEntity(entityitem);
	}

	private int getFreeSlot(TileEntityDispenser te) {
		for (int i = 0; i < te.getSizeInventory(); i++) {
			if (te.getStackInSlot(i) .isEmpty())
				return i;
		}
		return -1;
	}

	private Vec3i extend(Vec3i v, int i) {
		return new Vec3i(v.getX() * i, v.getY() * i, v.getZ() * i);
	}

}
