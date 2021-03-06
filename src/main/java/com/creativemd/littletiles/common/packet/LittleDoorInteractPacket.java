package com.creativemd.littletiles.common.packet;

import com.creativemd.creativecore.common.packet.CreativeCorePacket;
import com.creativemd.creativecore.common.utils.Rotation;
import com.creativemd.creativecore.common.utils.TickUtils;
import com.creativemd.littletiles.common.structure.LittleDoor;
import com.creativemd.littletiles.common.tileentity.TileEntityLittleTiles;
import com.creativemd.littletiles.common.utils.LittleTile;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LittleDoorInteractPacket extends CreativeCorePacket {
	
	
	public BlockPos blockPos;
	public Rotation direction;
	public boolean inverse;
	public Vec3d pos;
	public Vec3d look;
	
	public LittleDoorInteractPacket() {
		
	}
	
	public LittleDoorInteractPacket(BlockPos blockPos, EntityPlayer player, Rotation rotation, boolean inverse)
	{
		this.blockPos = blockPos;
		this.pos = player.getPositionEyes(TickUtils.getPartialTickTime());
		double d0 = player.capabilities.isCreativeMode ? 5.0F : 4.5F;
		Vec3d look = player.getLook(TickUtils.getPartialTickTime());
		this.look = pos.addVector(look.xCoord * d0, look.yCoord * d0, look.zCoord * d0);
		this.direction = rotation;
		this.inverse = inverse;
	}

	@Override
	public void writeBytes(ByteBuf buf) {
		writePos(buf, blockPos);
		writeVec3(pos, buf);
		writeVec3(look, buf);
		buf.writeInt(direction.ordinal());
		buf.writeBoolean(inverse);
	}

	@Override
	public void readBytes(ByteBuf buf) {
		blockPos = readPos(buf);
		pos = readVec3(buf);
		look = readVec3(buf);
		direction = Rotation.values()[buf.readInt()];
		inverse = buf.readBoolean();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void executeClient(EntityPlayer player) {
		
	}

	@Override
	public void executeServer(EntityPlayer player) {
		TileEntity tileEntity = player.worldObj.getTileEntity(blockPos);
		World world = player.worldObj;
		if(tileEntity instanceof TileEntityLittleTiles)
		{
			TileEntityLittleTiles te = (TileEntityLittleTiles) tileEntity;
			LittleTile tile = te.getFocusedTile(pos, look);
			if(tile != null && tile.isStructureBlock && tile.structure instanceof LittleDoor)
			{
				((LittleDoor) tile.structure).interactWithDoor(world, player, direction, inverse);
				//System.out.println("Open Door");
			}//else
				//System.out.println("No door found!");
		}
	}
	
	
	
}
