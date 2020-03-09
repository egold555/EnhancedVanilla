package org.golde.enhancedvanilla.blocks.decor;

import java.util.List;

import org.golde.enhancedvanilla.blocks._core._BBlock;
import org.golde.enhancedvanilla.init._core.shared.EnumCreativeTab;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEnhancedObsidian extends _BBlock {

	public BlockEnhancedObsidian() {
		super("enhanced_obsidian", EnumCreativeTab.FINISHED);
		this.setHardness(70.0F);
		this.setResistance(6000000.0F);
	}
	
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return MapColor.OBSIDIAN;
	}
	
	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
		return !(entity instanceof EntityWither) && !(entity instanceof EntityWitherSkull) && !(entity instanceof EntityDragon) && !(entity instanceof EntityFireball) && !(entity instanceof EntityTNTPrimed) && !(entity instanceof EntityEnderCrystal);
	}
	
	@Override
	public void onBlockExploded(World world, BlockPos pos, Explosion explosion) {
		
	}
	
	//Piston moving
	@Override
	public EnumPushReaction getMobilityFlag(IBlockState state) {
		return EnumPushReaction.BLOCK;
	}
	
	@Override
	public boolean canDropFromExplosion(Explosion explosionIn) {
		return false;
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		super.addInformation(stack, player, tooltip, advanced);
		tooltip.add("Somehow only players can break this block.");
		tooltip.add("Not sure how this works, but science.");
	}

}
