 package main;
import java.io.IOException;
import java.lang.Math;
import java.net.*;
import java.util.*;


public class CameraLineOfSight {

	public static int[] tagLocation = new int[]{50,5,60};
	public static int[] cameraLocation = new int[]{10,20,30};
	
	public static int[] doorEdge1 = new int[]{10,20,0};
	public static int[] doorEdge2 = new int[]{15,40,0};
	
	public CameraLineOfSight() {

	}
		
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
		
		//Information to be sent to Arduino: Angle in comparison to a reference point/line
		//Based on this angle, the motors will move the camera to this angle from the reference point/line
	}
	
	public static double[] angles() {
		
		double[] referenceLine = new double[] {0, 0, 0};
		
		//Calculate x-y plane angle: y/x
		double x = equationOfLine(cameraLocation, tagLocation)[0] - referenceLine[0];
		double y = equationOfLine(cameraLocation, tagLocation)[1] - referenceLine[1];
		double z = equationOfLine(cameraLocation, tagLocation)[2] - referenceLine[2];
		double xyAngle = Math.tan(y / x);
		
		//Calculate x-z plane angle: z/x
		double yzAngle = Math.tan(z / x);
		
		//Send these two angles to the Arduino
		double[] angles = new double[] {xyAngle, yzAngle};
		
		return angles;
	}
	
	public static void main(String[] args) {
		
		int[] lineofsight = equationOfLine(cameraLocation, tagLocation);
		System.out.println("z = " + lineofsight[0] + "x" + lineofsight[1]+ "y" + lineofsight[2]);
		
		int[] entranceBoundary = equationOfLine(doorEdge1, doorEdge2);
		System.out.println("z = " + entranceBoundary[0] + "x" + entranceBoundary[1]+ "y" + entranceBoundary[2]);

		
		
	}
	
	
		
}
	


