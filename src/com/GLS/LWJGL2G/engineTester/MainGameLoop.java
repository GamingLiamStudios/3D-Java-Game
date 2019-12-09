package com.GLS.LWJGL2G.engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import com.GLS.LWJGL2G.entities.*;
import com.GLS.LWJGL2G.models.*;
import com.GLS.LWJGL2G.renderEngine.*;
import com.GLS.LWJGL2G.shaders.StaticShader;
import com.GLS.LWJGL2G.textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager display = new DisplayManager(1280,720,"Java Game");
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
		RawModel model = OBJLoader.loadObjModel("stall", loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture("stallTexture"));
		TexturedModel texturedModel = new TexturedModel(model,texture);
		Camera camera = new Camera();
		Entity entity = new Entity(texturedModel,new Vector3f(0,0,-50f),0,0,0,1f);
		while(!Display.isCloseRequested()) {
			entity.increaseRotation(0, 1, 0);
			camera.move();
			renderer.prepare();
			shader.start();
			shader.loadViewMatrix(camera);
			renderer.render(entity);
			shader.stop();
			display.updateDisplay();
		}
		shader.cleanUp();
		loader.cleanUp();
		display.removeDisplay();
	}

}
