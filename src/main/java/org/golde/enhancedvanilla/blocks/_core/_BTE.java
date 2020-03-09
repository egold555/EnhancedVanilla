package org.golde.enhancedvanilla.blocks._core;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
	
}
