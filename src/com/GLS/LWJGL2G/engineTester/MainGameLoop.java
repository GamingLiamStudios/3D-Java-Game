package com.GLS.LWJGL2G.engineTester;

import java.util.ArrayList;
import java.util.Random;

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
		Random rand = new Random();
		ArrayList<Entity> objects = new ArrayList<>();
		ArrayList<Terrain> terrains = new ArrayList<>();
		DisplayManager display = new DisplayManager(1280,720,"Java Game");
		Loader loader = new Loader();
		MasterRenderer renderer = new MasterRenderer();
		objects.add(new Entity(new TexturedModel(OBJLoader.loadObjModel("stall", loader),new ModelTexture(loader.loadTexture("dragonTexture")).setShineDamper(10).setReflectivity(1)),new Vector3f(0,0,-25f),0,0,0,1f));
		for(int i = 0; i < 100; i++) {
			objects.add(new Entity(new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),new ModelTexture(loader.loadTexture("grassTexture")).setHasTransparency(true).setUseFakeLighting(true)),new Vector3f(rand.nextFloat()*800-400,0,rand.nextFloat()*-400),0,0,0,1f));
			objects.add(new Entity(new TexturedModel(OBJLoader.loadObjModel("tree", loader),new ModelTexture(loader.loadTexture("tree"))),new Vector3f(rand.nextFloat()*800-400,0,rand.nextFloat()*-400),0,0,0,6f));
		}
		Camera camera = new Camera();
		Light light = new Light(new Vector3f(3000,2000,3000),new Vector3f(1,1,1));
		terrains.add(new Terrain(-1,0,loader,new ModelTexture(loader.loadTexture("grass"))));
        terrains.add(new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("grass"))));
        terrains.add(new Terrain(-1,-1,loader,new ModelTexture(loader.loadTexture("grass"))));
        terrains.add(new Terrain(0,-1,loader,new ModelTexture(loader.loadTexture("grass"))));
		long last = System.nanoTime();
		float delta = 0;
		int frames = 0;
		float ns = 1000000000/120f;
		int FPS = 0;
		long timer = System.currentTimeMillis();
		while(!Display.isCloseRequested()) {
			long now = System.nanoTime();
			delta+=(System.nanoTime()-last)/ns;
			last = now;
	        if(delta>=1) {
	        	delta--;
	        	objects.get(0).increaseRotation(0, 1, 0);
				camera.move();
	        }
	        for(Terrain terrain:terrains) renderer.processTerrain(terrain);
			for(Entity entity:objects) renderer.processEntity(entity);
			renderer.render(light, camera);
			display.updateDisplay();
			frames++;
			if(System.currentTimeMillis()-timer>1000) {
				timer+=1000;
				FPS = frames;
				Display.setTitle("FPS: "+FPS);
				frames = 0;
			}
		}
		renderer.cleanUp();
		loader.cleanUp();
		display.removeDisplay();
	}
	
}
