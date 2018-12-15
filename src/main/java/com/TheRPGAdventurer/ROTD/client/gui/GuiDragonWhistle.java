package com.TheRPGAdventurer.ROTD.client.gui;

import org.lwjgl.input.Keyboard;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.network.MessageDragonWhistle;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiDragonWhistle extends GuiScreen {
	
	EntityTameableDragon dragonToControl;
	private final MessageDragonWhistle dcw = new MessageDragonWhistle();
	
	GuiButton nothing;
	GuiButton circle;
	GuiButton followFlying;
	GuiButton goToPlayer;
	GuiButton sit;

	public GuiDragonWhistle() {
		super();
		
	}
	
	@Override
	public void initGui() {
		buttonList.clear();
		Keyboard.enableRepeatEvents(true);
		
		nothing = new GuiButton(0, width / 2, 70, 
	              98, 20, I18n.format("gui.nothing", new Object[0]));
		circle = new GuiButton(0, width / 2 + 100, 44, 
	              98, 20, I18n.format("gui.circle", new Object[0]));
		followFlying = new GuiButton(0, width / 2 - 100, 44, 
	              98, 20, I18n.format("gui.followFlying", new Object[0]));
		goToPlayer = new GuiButton(0, width / 2, 44, 
	              98, 20, I18n.format("gui.goToPlayer", new Object[0]));
		buttonList.add(nothing);
		buttonList.add(circle);
		buttonList.add(followFlying);
		buttonList.add(goToPlayer);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		if(button == circle) { 
			
		}
		

	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

}