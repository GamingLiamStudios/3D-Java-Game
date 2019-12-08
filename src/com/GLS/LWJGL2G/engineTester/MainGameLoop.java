package com.GLS.LWJGL2G.engineTester;

import org.lwjgl.opengl.Display;

import com.GLS.LWJGL2G.renderEngine.*;
import com.GLS.LWJGL2G.shaders.StaticShader;

public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager display = new DisplayManager(1280,720,"Java Game");
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		StaticShader shader = new StaticShader();
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
			shader.start();
			renderer.render(model);
			shader.stop();
			display.updateDisplay();
		}
		shader.cleanUp();
		loader.cleanUp();
		display.removeDisplay();
	}

}
