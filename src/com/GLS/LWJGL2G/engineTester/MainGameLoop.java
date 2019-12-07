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
			0.5f, 0.5f, 0f
		};
		int[] indices = {
			0,1,3,
			3,1,2
		};
		RawModel model = loader.loadToVAO(vertices, indices);
		while(!Display.isCloseRequested()) {
			renderer.prepare();
			renderer.render(model);
			display.updateDisplay();
		}
		display.removeDisplay();
	}

}
