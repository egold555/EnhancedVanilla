package org.golde.enhancedvanilla.events;

import org.golde.enhancedvanilla.EnhancedVanilla;
import org.golde.enhancedvanilla.blocks.decor.futuremc.BlockStrippedLog;
import org.golde.enhancedvanilla.init.BBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = EnhancedVanilla.MODID)
public class StrippedLogHandler {

	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock e) {
	
		
		
		if(e.getWorld().isRemote) {
			return;
		}
		
		//we clicked on a block
		if(e.getFace() != null) {
			
			BlockPos pos = e.getPos();
			World world = e.getWorld();
			IBlockState state = e.getWorld().getBlockState(e.getPos());
			Block block = state.getBlock();
			
			EntityPlayer player = e.getEntityPlayer();
			ItemStack mainHand = player.getHeldItemMainhand();
			if(mainHand == null || mainHand.isEmpty() || mainHand.getItem() == null) {
				return;
			}
			
			if(!(mainHand.getItem() instanceof ItemAxe)) {
				return;
			}
			
			
			if(block instanceof BlockOldLog) {
				setBlock(world, pos, state, state.getValue(BlockOldLog.VARIANT));
				mainHand.damageItem(1, player);
			}
			else if(block instanceof BlockNewLog) {
				setBlock(world, pos, state, state.getValue(BlockNewLog.VARIANT));
				mainHand.damageItem(1, player);
			}
			
			
		}
		
	}
	
	private static void setBlock(World world, BlockPos pos, IBlockState state, EnumType type) {
		BlockStrippedLog log;
		
		switch(type) {
			case ACACIA:
				log = BBlocks.STRIPPED_ACACIA_LOG;
				break;
			case BIRCH:
				log = BBlocks.STRIPPED_BIRCH_LOG;
				break;
			case DARK_OAK:
				log = BBlocks.STRIPPED_DARK_OAK_LOG;
				break;
			case JUNGLE:
				log = BBlocks.STRIPPED_JUNGLE_LOG;
				break;
			case OAK:
				log = BBlocks.STRIPPED_OAK_LOG;
				break;
			case SPRUCE:
				log = BBlocks.STRIPPED_SPRUCE_LOG;
				break;
			default:
				log = BBlocks.STRIPPED_OAK_LOG;
				break;
		
		}
		world.setBlockState(pos, log.getDefaultState().withProperty(BlockLog.LOG_AXIS, state.getValue(BlockLog.LOG_AXIS)));
	}
	
}
