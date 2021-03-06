package com.GLS.LWJGL2G.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private Vector3f position = new Vector3f(0,10,0);
	private float pitch, yaw, roll;
	private final float speed = 0.05f;
	
	public void move() {
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) position.z-=speed;
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) position.z+=speed;
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) position.x-=speed;
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) position.x+=speed;
		if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) position.y-=speed;
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) position.y+=speed;
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) yaw-=.5f;
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) yaw+=.5f;
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) pitch-=.5f;
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) pitch+=.5f;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	

}
