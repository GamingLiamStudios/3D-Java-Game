package com.GLS.LWJGL2G.engineTester;

import org.lwjgl.opengl.Display;

import com.GLS.LWJGL2G.renderEngine.*;

public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager display = new DisplayManager(1280,720,"Java Game");
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		float[] vertices = {
			-0.5f, 0.5f, 0f,
			-0.5f, -0.5f, 0f,
			0.5f, -0.5f, 0f,
			
			0.5f, -0.5f, 0f,
			0.5f, 0.5f, 0f,
			-0.5f, 0.5f, 0f
		};
		RawModel model = loader.loadToVAO(vertices);
		while(!Display.isCloseRequested()) {
			renderer.prepare();
			renderer.render(model);
			display.updateDisplay();
		}
		display.removeDisplay();
	}

}
