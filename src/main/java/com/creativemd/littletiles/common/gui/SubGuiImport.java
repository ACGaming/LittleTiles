package com.creativemd.littletiles.common.gui;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import com.creativemd.creativecore.gui.container.SubGui;
import com.creativemd.creativecore.gui.controls.gui.GuiButton;
import com.creativemd.creativecore.gui.controls.gui.GuiTextfield;
import com.creativemd.creativecore.gui.event.container.SlotChangeEvent;
import com.creativemd.littletiles.common.items.ItemRecipe;
import com.creativemd.littletiles.common.utils.converting.StructureStringUtils;
import com.n247s.api.eventapi.eventsystem.CustomEventSubscribe;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SubGuiImport extends SubGui {
	
	public GuiTextfield textfield;
	
	@Override
	public void createControls() {
		textfield = new GuiTextfield("import", "", 10, 30, 150, 14);
		controls.add(textfield);
		controls.add(new GuiButton("Paste", 10, 52) {
			
			@Override
			public void onClicked(int x, int y, int button) {
				StringSelection stringSelection = new StringSelection(textfield.text);
				Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
				Transferable t = clpbrd.getContents(this);
				if (t == null)
			        return ;
			    try {
			    	textfield.text = (String) t.getTransferData(DataFlavor.stringFlavor);
			    } catch (Exception e){
			    	
			    }
			}
		});
		
		controls.add(new GuiButton("Import", 100, 52) {
			
			@Override
			public void onClicked(int x, int y, int button) {
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setString("text", textfield.text);
				sendPacketToServer(nbt);
			}
		});
	}
	
	@CustomEventSubscribe
	public void onSlotChange(SlotChangeEvent event)
	{
		
	}

}
