package org.golde.enhancedvanilla.blocks.soundmuffler;

import java.util.List;

import org.golde.enhancedvanilla.blocks._core._BBlock;
import org.golde.enhancedvanilla.blocks._core._BBlockWithTE;
import org.golde.enhancedvanilla.blocks._core._BTE;
import org.golde.enhancedvanilla.init._core.shared.EnumCreativeTab;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSoundMuffler extends _BBlockWithTE<TileEntitySoundMuffler> {

	public BlockSoundMuffler() {
		super("sound_muffler", Material.CLOTH, EnumCreativeTab.FINISHED);
		MinecraftForge.EVENT_BUS.register(this);
		setSoundType(SoundType.CLOTH);
	}
	
	@Override
	public Class<TileEntitySoundMuffler> getTileEntityClass() {
		return TileEntitySoundMuffler.class;
	}

	@Override
	public TileEntitySoundMuffler createTileEntity(World world, IBlockState state) {
		return new TileEntitySoundMuffler();
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void supressSound(PlaySoundEvent event) {
		WorldClient theWorld = Minecraft.getMinecraft().world;
		if (theWorld == null) return;
		ISound sound = event.getSound();
		if (sound instanceof ITickableSound) return;

		AxisAlignedBB expand = new AxisAlignedBB(sound.getXPosF(), sound.getYPosF(), sound.getZPosF(), sound.getXPosF(), sound.getYPosF(), sound.getZPosF()).grow(8, 8, 8);
		
		List<TileEntitySoundMuffler> tileSoundMufflers = _BTE.searchAABBForTiles(theWorld, expand, TileEntitySoundMuffler.class, true, null);
		if (tileSoundMufflers.isEmpty()) return;

		float volume = 0.05F;

		event.setResultSound(new SoundMuffler(sound, volume));
	}

}
