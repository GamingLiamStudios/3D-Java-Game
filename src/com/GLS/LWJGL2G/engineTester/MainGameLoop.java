package com.GLS.LWJGL2G.engineTester;

import org.lwjgl.opengl.Display;

import com.GLS.LWJGL2G.renderEngine.DisplayManager;

public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager display = new DisplayManager(1280,720,"Java Game");
		while(!Display.isCloseRequested()) {
			display.updateDisplay();
		}
		display.removeDisplay();
	}

}
