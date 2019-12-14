package com.GLS.LWJGL2G.renderEngine;

import java.util.ArrayList;
import java.util.HashMap;

import com.GLS.LWJGL2G.entities.Camera;
import com.GLS.LWJGL2G.entities.Entity;
import com.GLS.LWJGL2G.entities.Light;
import com.GLS.LWJGL2G.models.TexturedModel;
import com.GLS.LWJGL2G.shaders.StaticShader;

public class MasterRenderer {
	
	private StaticShader shader = new StaticShader();
	private Renderer renderer = new Renderer(shader);
	
	private HashMap<TexturedModel, ArrayList<Entity>> entities = new HashMap<>();
	
	public void render(Light light, Camera camera) {
		renderer.prepare();
		shader.start();
		shader.loadLight(light);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		entities.clear();
	}
	
	public void processEntity(Entity entity) {
		TexturedModel entityModel = entity.getModel();
		if(entities.get(entityModel)==null)entities.put(entityModel, new ArrayList<>());
		ArrayList<Entity> batch = entities.get(entityModel);
		batch.add(entity);
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}

}
