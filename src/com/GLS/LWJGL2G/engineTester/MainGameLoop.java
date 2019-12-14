package com.GLS.LWJGL2G.engineTester;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import com.GLS.LWJGL2G.entities.Camera;
import com.GLS.LWJGL2G.entities.Entity;
import com.GLS.LWJGL2G.entities.Light;
import com.GLS.LWJGL2G.models.TexturedModel;
import com.GLS.LWJGL2G.renderEngine.DisplayManager;
import com.GLS.LWJGL2G.renderEngine.Loader;
import com.GLS.LWJGL2G.renderEngine.MasterRenderer;
import com.GLS.LWJGL2G.renderEngine.OBJLoader;
import com.GLS.LWJGL2G.terrains.Terrain;
import com.GLS.LWJGL2G.textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {
		ArrayList<Entity> objects = new ArrayList<>();
		ArrayList<Terrain> terrains = new ArrayList<>();
		DisplayManager display = new DisplayManager(1280,720,"Java Game");
		Loader loader = new Loader();
		MasterRenderer renderer = new MasterRenderer();
		objects.add(new Entity(new TexturedModel(OBJLoader.loadObjModel("stall", loader),new ModelTexture(loader.loadTexture("dragonTexture")).setShineDamper(10).setReflectivity(1)),new Vector3f(0,0,-25f),0,0,0,1f));
		Camera camera = new Camera();
		Light light = new Light(new Vector3f(3000,2000,3000),new Vector3f(1,1,1));
		terrains.add(new Terrain(-1,0,loader,new ModelTexture(loader.loadTexture("grass"))));
        terrains.add(new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("grass"))));
        terrains.add(new Terrain(-1,-1,loader,new ModelTexture(loader.loadTexture("grass"))));
        terrains.add(new Terrain(0,-1,loader,new ModelTexture(loader.loadTexture("grass"))));
		long pastTime = System.nanoTime();
		long delta = 1;
		long delta2 = 1;
		int frames = 0;
		long ns = 1000000000;
		int fps = 1;
		long cap = ns/120;
		while(!Display.isCloseRequested()) {
	        if(cap/delta2>=1) {
	        	delta2-=cap;
	        	objects.get(0).increaseRotation(0, 1, 0);
				camera.move();
	        }
	        for(Terrain terrain:terrains) renderer.processTerrain(terrain);
			for(Entity entity:objects) renderer.processEntity(entity);
			renderer.render(light, camera);
			display.updateDisplay();
			long currentTime = System.nanoTime();
			delta+=currentTime-pastTime;
			delta2+=currentTime-pastTime;
			pastTime = currentTime;
			frames++;
			if(ns/delta>=1) {
				delta-=ns;
				fps = frames;
				System.out.println(fps);
				frames = 0;
			}
		}
		renderer.cleanUp();
		loader.cleanUp();
		display.removeDisplay();
	}
	
}
