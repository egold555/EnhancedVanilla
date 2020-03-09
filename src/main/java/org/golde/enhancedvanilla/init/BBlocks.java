package org.golde.enhancedvanilla.init;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.golde.enhancedvanilla.blocks._core._BBlock;
import org.golde.enhancedvanilla.blocks._core._BBlockWithTE;
import org.golde.enhancedvanilla.blocks._core._IBObjectBlock;
import org.golde.enhancedvanilla.blocks.blockbreaker.BlockBlockBreaker;
import org.golde.enhancedvanilla.blocks.blockplacer.BlockBlockPlacer;
import org.golde.enhancedvanilla.blocks.decor.BlockBarbedWire;
import org.golde.enhancedvanilla.blocks.decor.BlockCharredBrick;
import org.golde.enhancedvanilla.blocks.decor.BlockEnhancedObsidian;
import org.golde.enhancedvanilla.blocks.decor.BlockStonePath;
import org.golde.enhancedvanilla.blocks.decor.futuremc.BlockStrippedLog;
import org.golde.enhancedvanilla.init._core.shared.EnumCreativeTab;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class BBlocks {

	public static _BBlock exampleBlock;
	
	public static BlockEnhancedObsidian enhancedObsidian;
	public static BlockBlockBreaker blockBreaker;
	public static BlockBlockPlacer blockPlacer;
	public static BlockBarbedWire barbedWire;
	public static BlockCharredBrick charredBrick;
	public static BlockStonePath stonePath;
	
	public static BlockStrippedLog STRIPPED_ACACIA_LOG;
	public static BlockStrippedLog STRIPPED_JUNGLE_LOG;
	public static BlockStrippedLog STRIPPED_BIRCH_LOG;
	public static BlockStrippedLog STRIPPED_OAK_LOG;
	public static BlockStrippedLog STRIPPED_SPRUCE_LOG;
	public static BlockStrippedLog STRIPPED_DARK_OAK_LOG;
	
	private static List<Block> ALL_BLOCKS = new ArrayList<Block>();
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().register(exampleBlock = new _BBlock("example_block", EnumCreativeTab.UNFINISHED));
		event.getRegistry().register(enhancedObsidian = new BlockEnhancedObsidian());
		event.getRegistry().register(blockBreaker = new BlockBlockBreaker());
		event.getRegistry().register(blockPlacer = new BlockBlockPlacer());
		event.getRegistry().register(barbedWire = new BlockBarbedWire());
		event.getRegistry().register(charredBrick = new BlockCharredBrick());
		event.getRegistry().register(stonePath = new BlockStonePath());
		
		//"acacia", "jungle", "birch", "oak", "spruce", "dark_oak"
		event.getRegistry().register(STRIPPED_ACACIA_LOG = new BlockStrippedLog("acacia"));
		event.getRegistry().register(STRIPPED_JUNGLE_LOG = new BlockStrippedLog("jungle"));
		event.getRegistry().register(STRIPPED_BIRCH_LOG = new BlockStrippedLog("birch"));
		event.getRegistry().register(STRIPPED_OAK_LOG = new BlockStrippedLog("oak"));
		event.getRegistry().register(STRIPPED_SPRUCE_LOG = new BlockStrippedLog("spruce"));
		event.getRegistry().register(STRIPPED_DARK_OAK_LOG = new BlockStrippedLog("dark_oak"));
		
		populateAllBlocksArray();
		
		registerTileEntities();

	}
	
	private static void registerTileEntities() {
		for(Block block : ALL_BLOCKS) {
			
			if(block == null) {
				System.err.println("Null block detected. Check assignmentss");
				continue;
			}
			
			if(block instanceof _BBlockWithTE) {
				_BBlockWithTE<?> blockTE = (_BBlockWithTE<?>)block;
				GameRegistry.registerTileEntity(blockTE.getTileEntityClass(), blockTE.getRegistryName());
			}
		}
	}

	private static void registerTE(_BBlockWithTE<?> block) {
		GameRegistry.registerTileEntity(block.getTileEntityClass(), block.getRegistryName());
	}
	
	@SideOnly(Side.CLIENT)
    public static void initModels() {
		
		for(Block block : ALL_BLOCKS) {
			if(block instanceof _IBObjectBlock) {
				_IBObjectBlock _block = (_IBObjectBlock)block;
				if(_block.shouldRegisterItem()) {
					_block.initModel();
				}
			}
			
		}
		
//		exampleBlock.initModel();
//		tickChanger.initModel();
//		tickChanger2.initModel();
//		tickChanger3.initModel();
//		enhancedObsidian.initModel();
//		weatherDetector.initModel();
	}
	
	@SideOnly(Side.CLIENT)
	public static void bindTESR() {
		
	}
	
	private static void populateAllBlocksArray(){
		
		for (Field field : BBlocks.class.getDeclaredFields()) {
		    if (Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers())) {
		        
		    	try {
		    		ALL_BLOCKS.add((Block) field.get(null));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
		    	
		    }
		}
		
	}
	
	public static List<Block> getAllBlocksReflection() {
		return ALL_BLOCKS;
	}
	
}
