package org.golde.enhancedvanilla.dispenser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockStem;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ShearsBehaviour extends BehaviorDefaultDispenseItem {
	
	private static final ShearsBehaviour INSTANCE = new ShearsBehaviour();

	public static ShearsBehaviour getInstance() {
		return INSTANCE;
	}

	private ShearsBehaviour() {}

	private final BehaviorDefaultDispenseItem dispenseBehavior = new BehaviorDefaultDispenseItem();

	@Override
	@Nonnull
	public ItemStack dispenseStack(@Nonnull IBlockSource source, @Nonnull ItemStack stack) {
		
		TileEntityDispenser te = source.getBlockTileEntity();
        IBlockState s = te.getWorld().getBlockState(source.getBlockPos());
        EnumFacing face = source.getBlockState().getValue(BlockDispenser.FACING);

        BlockPos destination = source.getBlockPos().add(extend(face.getDirectionVec(), 1));
        List<EntitySheep> sheeps = te.getWorld().getEntitiesWithinAABB(EntitySheep.class, new AxisAlignedBB(destination));
        List<EntityMooshroom> moos = te.getWorld().getEntitiesWithinAABB(EntityMooshroom.class, new AxisAlignedBB(destination));
        List<IShearable> shearables = new ArrayList<IShearable>(sheeps);
        shearables.addAll(moos);
        
        if(shearables.size() > 0) {
        	return harvestAnimals(shearables, te, source, stack, face);
        }
        else {
        	return harvestCrop(source, stack, 2);
        }
		
		
	}

	private ItemStack harvestAnimals(List<IShearable> shearables, TileEntity te, IBlockSource source, ItemStack stack, EnumFacing face) {
		
            for (IShearable shearable : shearables) {
                if (shearable.isShearable(stack, te.getWorld(), source.getBlockPos())) {
                    List<ItemStack> drops = shearable.onSheared(stack, te.getWorld(), source.getBlockPos(),
                            EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack));

                    handelItems(drops, source.getWorld(), source.getBlockPos(), face);

                    stack.damageItem(1, (EntityLivingBase) shearable);
                }
            }
        

        return stack;
	}

	private boolean isShears(ItemStack stack) {
		return stack.getItem() instanceof ItemShears;
	}

	@Nonnull
	private ItemStack harvestCrop(@Nonnull IBlockSource source, @Nonnull ItemStack stack, int r) {
		World world = source.getWorld();
		EnumFacing face = source.getBlockState().getValue(BlockDispenser.FACING);
		BlockPos pos = source.getBlockPos();
		int range = r;
		int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);

		if (range > 0) {
			boolean success = false;
			

			BlockPos pos2 = pos.offset(face, range + 1);
			for (int x = pos2.getX() - range; x <= pos2.getX() + range; x++) {
				for (int z = pos2.getZ() - range; z <= pos2.getZ() + range; z++) {
					BlockPos p1 = new BlockPos(x, pos2.getY() + 1, z);
					BlockPos p2 = new BlockPos(x, pos2.getY(), z);
					IBlockState s1 = world.getBlockState(p1);
					IBlockState s2 = world.getBlockState(p2);

					List<ItemStack> list = new ArrayList<ItemStack>();

					if (s1.getBlock() instanceof BlockCrops) {
						BlockCrops crop = (BlockCrops) s1.getBlock();
						if (crop.isMaxAge(s1)) {
							NonNullList<ItemStack> l1 = NonNullList.create();
							crop.getDrops(l1, world, p1, s1, fortune);

							if (!l1.isEmpty()) {
								list.addAll(l1);
								world.setBlockState(p1, crop.getDefaultState());
							}
						}
					} else if (s1.getBlock() instanceof BlockCactus || s1.getBlock() instanceof BlockReed
							|| s1.getMaterial() == Material.GOURD) {
						list.add(new ItemStack(s1.getBlock().getItemDropped(s1, world.rand, fortune), 1, 0));
						world.setBlockToAir(p1);
					} else if (!(s1.getBlock() instanceof BlockStem) && s1.getBlock() instanceof IGrowable) {
						IGrowable crop = (IGrowable) s1.getBlock();
						if (!crop.canGrow(world, p1, s1, false)) {
							NonNullList<ItemStack> l1 = NonNullList.create();
							s1.getBlock().getDrops(l1, world, p1, s1, fortune);

							if (!l1.isEmpty()) {
								list.addAll(l1);
								world.setBlockState(p1, s1.getBlock().getDefaultState());
							}
						}
					}

					if (s2.getBlock() instanceof BlockCrops) {
						BlockCrops crop = (BlockCrops) s2.getBlock();
						if (crop.isMaxAge(s2)) {
							NonNullList<ItemStack> l1 = NonNullList.create();
							crop.getDrops(l1, world, p2, s2, fortune);

							if (!l1.isEmpty()) {
								list.addAll(l1);
								world.setBlockState(p2, crop.getDefaultState());
							}
						}
					} else if (s2.getBlock() instanceof BlockCactus || s2.getBlock() instanceof BlockReed
							|| s2.getMaterial() == Material.GOURD) {
						list.add(new ItemStack(s2.getBlock().getItemDropped(s2, world.rand, fortune), 1, 0));
						world.setBlockToAir(p2);
					} else if (!(s2.getBlock() instanceof BlockStem) && s2.getBlock() instanceof IGrowable) {
						IGrowable crop = (IGrowable) s2.getBlock();
						if (!crop.canGrow(world, p2, s2, false)) {
							NonNullList<ItemStack> l1 = NonNullList.create();
							s2.getBlock().getDrops(l1, world, p2, s2, fortune);

							if (!l1.isEmpty()) {
								list.addAll(l1);
								world.setBlockState(p2, s2.getBlock().getDefaultState());
							}
						}
					}

					success = handelItems(list, world, pos, face);

				}
			}

			if (success) {
				if (stack.attemptDamageItem(1, world.rand, null)) {
					stack.setCount(0);
				}
			}
		}

		return stack;

	}
	
	private static boolean handelItems(List<ItemStack> list, World world, BlockPos pos, EnumFacing face) {
		
		IItemHandler inv = null;
		for (EnumFacing f : EnumFacing.VALUES) {
			if (world.getTileEntity(pos.offset(f)) != null
					&& !(world.getTileEntity(pos.offset(f)) instanceof TileEntityDispenser)
					&& world.getTileEntity(pos.offset(f))
					.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite())) {
				inv = world.getTileEntity(pos.offset(f))
						.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite());
				break;
			}
		}
		
		boolean success = false;
		if (!list.isEmpty()) {
			success = true;
			for (int i = 0; i < list.size(); i++) {
				ItemStack c1 = list.get(i);
				ItemStack ret = ItemStack.EMPTY;
				if (inv != null) {
					int slot = 0;
					while (slot < inv.getSlots()) {
						ret = inv.insertItem(slot, c1, false);
						if (ret.isEmpty()) {
							break;
						} else {
							slot++;
						}
					}
				} else {
					ret = c1;
				}
				if (!ret.isEmpty()) {
					EntityItem drop = new EntityItem(world, pos.offset(face).getX() + 0.5D,
							pos.offset(face).getY() + 0.5D, pos.offset(face).getZ() + 0.5D, ret);
					drop.motionX = 0.0D;
					drop.motionY = 0.0D;
					drop.motionZ = 0.0D;
					world.spawnEntity(drop);
				}
			}
		}
		return success;
	}
	
	private Vec3i extend(Vec3i v, int i) {
		return new Vec3i(v.getX() * i, v.getY() * i, v.getZ() * i);
	}
}