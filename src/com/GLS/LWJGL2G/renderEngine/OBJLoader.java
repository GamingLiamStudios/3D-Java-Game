package com.GLS.LWJGL2G.renderEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.GLS.LWJGL2G.models.RawModel;

public class OBJLoader {
	
	public static RawModel loadObjModel(String fileName, Loader loader) {
		FileReader fr = null;
		try {
			fr = new FileReader(new File("res/"+fileName+".obj"));
		} catch (Exception e) {
			System.err.println("Could not read file!");
			e.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(fr);
		String line;
		ArrayList<Vector3f> vertices = new ArrayList<>();
		ArrayList<Vector2f> textures = new ArrayList<>();
		ArrayList<Vector3f> normals = new ArrayList<>();
		ArrayList<Integer> indices = new ArrayList<>();
		float[] verticesArray = null;
		float[] normalsArray = null;
		float[] textureArray = null;
		int[] indicesArray = null;
		try {
			while(true) {
				line = reader.readLine();
				String[] lines = line.split(" ");
				if(line.startsWith("v ")) vertices.add(new Vector3f(Float.parseFloat(lines[1]),Float.parseFloat(lines[2]),Float.parseFloat(lines[3])));
				else if(line.startsWith("vt ")) textures.add(new Vector2f(Float.parseFloat(lines[1]),Float.parseFloat(lines[2])));
				else if(line.startsWith("vn ")) normals.add(new Vector3f(Float.parseFloat(lines[1]),Float.parseFloat(lines[2]),Float.parseFloat(lines[3])));
				else if(line.startsWith("f ")) {
					textureArray = new float[vertices.size()*2];
					normalsArray = new float[vertices.size()*3];
					break;
				}
			}
			while(line!=null) {
				if(!line.startsWith("f ")) {
					line = reader.readLine();
					continue;
				}
				String[] lines = line.split(" ");
				String[] v1 = lines[1].split("/");
				String[] v2 = lines[2].split("/");
				String[] v3 = lines[3].split("/");
				processVertex(v1,indices,textures,normals,textureArray,normalsArray);
				processVertex(v2,indices,textures,normals,textureArray,normalsArray);
				processVertex(v3,indices,textures,normals,textureArray,normalsArray);
				line = reader.readLine();
			}
			reader.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		verticesArray = new float[vertices.size()*3];
		indicesArray = new int[indices.size()];
		int pointer = 0;
		for(Vector3f vertex:vertices) {
			verticesArray[pointer++] = vertex.x;
			verticesArray[pointer++] = vertex.y;
			verticesArray[pointer++] = vertex.z;
		}
		for(int i = 0; i < indices.size(); i++) indicesArray[i] = indices.get(i);
		return loader.loadToVAO(verticesArray, textureArray, normalsArray, indicesArray);
	}
	
	private static void processVertex(String[] vertexData, ArrayList<Integer> indices, ArrayList<Vector2f> textures, ArrayList<Vector3f> normals, float[] textureArray, float[] normalsArray) {
		int vP = Integer.parseInt(vertexData[0])-1;
		indices.add(vP);
		Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1])-1);
		textureArray[vP*2] = currentTex.x;
		textureArray[vP*2+1] = 1-currentTex.y;
		Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2])-1);
		normalsArray[vP*3] = currentNorm.x;
		normalsArray[vP*3+1] = currentNorm.y;
		normalsArray[vP*3+2] = currentNorm.z;
	}

}
