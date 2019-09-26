package main;
import java.io.IOException;
import java.net.*;
import java.util.*;


public class CameraLineOfSight {

	private int[] tagLocation = new int[]{50,5,60};
	private int[] cameraLocation = new int[]{10,20,30};
		
	public int[] getCameraLocation() {
		return cameraLocation;
	}
	
	public int[] getTagLocation() {
		return tagLocation;
	}
	
	public int[] equationOfLine() {
		
		//vector v = cameraLocation - tagLocation
		int[] v = new int[] {cameraLocation[0]-tagLocation[0], cameraLocation[1]-tagLocation[1], cameraLocation[2]-tagLocation[2]};
		//vector r (not required for calculation)
		int[] r = new int[] {cameraLocation[0]+v[0], cameraLocation[1]+v[1], cameraLocation[2]+v[2]};
		
		//Equation of line in 3D space = ax+by+c
		int a = v[1];
		int b = -v[0];
		int c = cameraLocation[0]*v[1] - cameraLocation[1]*v[0];
		
		int[] equationCoordinates = new int[] {a, b, c};
		return equationCoordinates;
		
	}
	
	
		
}
	


