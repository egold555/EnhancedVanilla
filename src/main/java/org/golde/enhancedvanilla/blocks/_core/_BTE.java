package org.golde.enhancedvanilla.blocks._core;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class _BTE extends TileEntity {

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.readSyncNBT(nbt);
    }

    public void readSyncNBT(NBTTagCompound nbt) {}

	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        return this.writeSyncNBT(super.writeToNBT(nbt));
    }

    public NBTTagCompound writeSyncNBT(NBTTagCompound nbt) {
        return nbt;
    }

    public void syncTile(boolean rerender) {
        IBlockState state = this.world.getBlockState(this.pos);

        this.world.notifyBlockUpdate(this.pos, state, state, 2 + (rerender ? 4 : 0));
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, -999, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readSyncNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeSyncNBT(this.setupNbt());
    }

    private NBTTagCompound setupNbt() {
        NBTTagCompound nbt = super.writeToNBT(new NBTTagCompound());

        nbt.removeTag("ForgeData");
        nbt.removeTag("ForgeCaps");
        return nbt;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    public EnumFacing getFacing() {
        try {
            return EnumFacing.getFront(this.getBlockMetadata() & 7);
        } catch (Exception exception) {
            return EnumFacing.UP;
        }
    }

    public boolean gettingPower() {
        return this.world.isBlockPowered(this.getPos());
    }
    
    public static <T extends TileEntity> List<T> searchAABBForTiles(World world, AxisAlignedBB area, Class<T> tileClazz, boolean firstOnly, List<T> list) {
		int x0 = ((int) Math.floor(area.minX) >> 4);
		int x1 = ((int) Math.ceil(area.maxX) >> 4);
		int z0 = ((int) Math.floor(area.minZ) >> 4);
		int z1 = ((int) Math.ceil(area.maxZ) >> 4);

		if (list == null) list = Lists.newArrayList();

		for (int x = x0; x <= x1; x++) {
			for (int z = z0; z <= z1; z++) {
				Chunk chunk = world.getChunkFromChunkCoords(x, z);
				for (Map.Entry<BlockPos, TileEntity> entry : chunk.getTileEntityMap().entrySet()) {
					BlockPos pos = entry.getKey();
					if (tileClazz == entry.getValue().getClass() && area.contains(new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5))) {
						list.add((T) entry.getValue());
						if (firstOnly) return list;
					}
				}
			}
		}

		return list;
	}

	
}
