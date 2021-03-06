package com.creativemd.littletiles.common.items;

import java.util.List;

import com.creativemd.creativecore.common.packet.PacketHandler;
import com.creativemd.littletiles.LittleTiles;
import com.creativemd.littletiles.common.packet.LittleBlockPacket;
import com.creativemd.littletiles.common.tileentity.TileEntityLittleTiles;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLittleSaw extends Item{
	
	public ItemLittleSaw(){
		setCreativeTab(CreativeTabs.TOOLS);
		setMaxStackSize(1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced)
	{
		list.add("rightclick to increase and");
		list.add("shift+rightclick to decrease");
		list.add("the size of a placed tile");
	}
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		
		TileEntity tileEntity = world.getTileEntity(pos);
		if(tileEntity instanceof TileEntityLittleTiles)
		{
			if(world.isRemote)
			{
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("side", facing.getIndex());
				PacketHandler.sendPacketToServer(new LittleBlockPacket(pos, player, 2, nbt));
			}
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
    }
	
}
