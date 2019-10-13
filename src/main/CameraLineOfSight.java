package main;
import java.io.IOException;
import java.lang.Math;
import java.net.*;
import java.util.*;


public class CameraLineOfSight {

	public static int[] tagLocation = new int[]{50,5,60};
	public static int[] cameraLocation = new int[]{10,20,30};
	
	public static int[] doorEdge1 = new int[]{10,20,0};
	public static int[] doorEdge2 = new int[]{15,40,7};
	
	public CameraLineOfSight() {

	}
		
	public int[] getCameraLocation() {
		return cameraLocation;
	}
	
	public int[] getTagLocation() {
		return tagLocation;
	}
	
	public static int[] equationOfLine(int[] cameraLocation, int[] tagLocation) {
		
		//THIS METHOD MIGHT NOT BE NEEDED ANYMORE	
		//No point in calculating equation of line if we can calculate the angles to move camera directly in angles method below
		//This method might still be useful for calculation of walls of room and doorway
		
		//vector v = cameraLocation - tagLocation
		int[] v = new int[] {tagLocation[0]-cameraLocation[0], tagLocation[1]-cameraLocation[1], tagLocation[2]-cameraLocation[2]};
		//vector r (not required for calculation)
		int[] r = new int[] {cameraLocation[0]+v[0], cameraLocation[1]+v[1], cameraLocation[2]+v[2]};
		
		//Equation of line in 3D space = ax+by+c
		int a = v[1]*v[2];
		int b = -v[0]*v[2];
		int c = (cameraLocation[0]*v[1] - cameraLocation[1]*v[0])*v[2]+cameraLocation[2];
		
		int[] equationCoordinates = new int[] {a, b, c};
		return equationCoordinates;
		
		//Information to be sent to Arduino: Angle in comparison to a reference point/line
		//Based on this angle, the motors will move the camera to this angle from the reference point/line
	}
	
	public static double[] angles() {
		
		//Calculated the angles for the movement of the camera based on the following link:
		//http://www.nabla.hr/PC-LinePlaneIn3DSp2.htm
		
		double[] referenceLine = new double[] {0, 0, 0};
		
		double[] s = new double[] {tagLocation[0]-cameraLocation[0], tagLocation[1]-cameraLocation[1], tagLocation[2]-cameraLocation[2]};
		
		double magnitude = Math.sqrt(Math.pow(s[0],2) + Math.pow(s[1],2) + Math.pow(s[2],2));
		
		double mag_i = (s[0] / magnitude) * (Math.PI/180); //converted from degrees to radians
		double mag_j = (s[1] / magnitude) * (Math.PI/180);
		double mag_k = (s[2] / magnitude) * (Math.PI/180);
		
		double a = Math.acos(mag_i);
		double b = Math.acos(mag_j);
		double c = Math.acos(mag_k);
		
		double[] angles = new double[] {a, b, c};
		
		return angles;  //send these 3 angles to the camera controlling rpi to move the motors based on these angles
		
	}
	
	public static void main(String[] args) {
		
		int[] lineofsight = equationOfLine(cameraLocation, tagLocation);
		System.out.println("z = " + lineofsight[0] + "x" + lineofsight[1]+ "y" + lineofsight[2]);
		
		int[] entranceBoundary = equationOfLine(doorEdge1, doorEdge2);
		System.out.println("z = " + entranceBoundary[0] + "x" + entranceBoundary[1]+ "y" + entranceBoundary[2]);

		
		
	}
	
	
		
}
	


