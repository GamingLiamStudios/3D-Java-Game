package com.GLS.LWJGL2G.renderEngine;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import com.GLS.LWJGL2G.entities.Entity;
import com.GLS.LWJGL2G.models.RawModel;
import com.GLS.LWJGL2G.models.TexturedModel;
import com.GLS.LWJGL2G.shaders.StaticShader;
import com.GLS.LWJGL2G.textures.ModelTexture;
import com.GLS.LWJGL2G.toolbox.Maths;

public class Renderer {
	
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	
	private Matrix4f projectionMatrix;
	private StaticShader shader;
	
	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(1, 0, 0, 1);
	}
	
	public Renderer(StaticShader shader) {
		this.shader = shader;
		createProjectionMatrix();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	public void render(Entity entity) {
		TexturedModel texturedModel = entity.getModel();
		RawModel model = texturedModel.getRawModel();
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRx(), entity.getRy(), entity.getRz(), entity.getS());
		shader.loadTransformationMatrix(transformationMatrix);
		ModelTexture texture = texturedModel.getTexture();
		shader.loadShineValues(texture.getShineDamper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID());
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private void createProjectionMatrix() {
		float aR = (float) Display.getWidth()/(float) Display.getHeight();
		float ys = (float) ((1f/Math.tan(Math.toRadians(FOV/2f)))*aR);
		float xs = ys/aR;
		float fl = FAR_PLANE-NEAR_PLANE;
		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = xs;
		projectionMatrix.m11 = ys;
		projectionMatrix.m22 = -((FAR_PLANE+NEAR_PLANE)/fl);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2*NEAR_PLANE*FAR_PLANE)/fl);
		projectionMatrix.m33 = 0;
	}

}
