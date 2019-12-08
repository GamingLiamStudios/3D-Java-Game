package com.GLS.LWJGL2G.shaders;

public class StaticShader extends ShaderProgram {
	
	private static final String VERTEX_FILE = "src/com/GLS/LWJGL2G/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/com/GLS/LWJGL2G/shaders/fragmentShader.txt";

	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}
