package main;
import java.io.IOException;
import java.net.*;
import java.util.*;


public class CameraLineOfSight {

	private static int[] tagLocation = new int[]{50,5,60};
	private static int[] cameraLocation = new int[]{10,20,30};
	
	private static int[] doorEdge1 = new int[]{10,20,0};
	private static int[] doorEdge2 = new int[]{15,40,0};
		
	public int[] getCameraLocation() {
		return cameraLocation;
	}
	
	public int[] getTagLocation() {
		return tagLocation;
	}
	
	public static int[] equationOfLine(int[] cameraLocation, int[] tagLocation) {
		
		//vector v = cameraLocation - tagLocation
		int[] v = new int[] {cameraLocation[0]-tagLocation[0], cameraLocation[1]-tagLocation[1], cameraLocation[2]-tagLocation[2]};
		//vector r (not required for calculation)
		int[] r = new int[] {cameraLocation[0]+v[0], cameraLocation[1]+v[1], cameraLocation[2]+v[2]};
		
		//Equation of line in 3D space = ax+by+c
		int a = v[1]*v[2];
		int b = -v[0]*v[2];
		int c = (cameraLocation[0]*v[1] - cameraLocation[1]*v[0])*v[2]+cameraLocation[2];
		
		int[] equationCoordinates = new int[] {a, b, c};
		return equationCoordinates;
		
	}
	
	public static void main(String[] args) {
		
		int[] lineofsight = equationOfLine(cameraLocation, tagLocation);
		System.out.println("z = " + lineofsight[0] + "x" + lineofsight[1]+ "y" + lineofsight[2]);
		
		int[] entranceBoundary = equationOfLine(doorEdge1, doorEdge2);
		System.out.println("z = " + entranceBoundary[0] + "x" + entranceBoundary[1]+ "y" + entranceBoundary[2]);
		
		
	}
	
	
		
}
	


