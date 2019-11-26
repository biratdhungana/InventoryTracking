package main;
import java.io.IOException;
import java.lang.Math;
import java.net.*;
import java.util.*;


public class CameraLineOfSight {

	public static int[] tagLocation = new int[]{50,10,90};
	public static int[] cameraLocation = new int[]{30,5,20};
	
	public static int[] doorEdge1 = new int[]{10,20,0};
	public static int[] doorEdge2 = new int[]{15,40,7};

	public static double[] referenceLine = new double[]{0, 0, 0};
	
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
		
		//Angle of Elevation
		double distanceCameratoObject1 = Math.sqrt(Math.pow(cameraLocation[0]-referenceLine[0],2) + Math.pow(cameraLocation[1]-referenceLine[1],2) + Math.pow(cameraLocation[2]-referenceLine[2],2));
		double distanceCameratoObject2 = Math.sqrt(Math.pow(cameraLocation[0]-tagLocation[0],2) + Math.pow(cameraLocation[1]-tagLocation[1],2) + Math.pow(cameraLocation[2]-tagLocation[2],2));
		
		double cameraHeight = cameraLocation[2];
		double object1Height = referenceLine[2];
		double object2Height = tagLocation[2];
		
		double theta1 = Math.acos((Math.abs(cameraHeight-object1Height)) / distanceCameratoObject1);
		double theta2 = Math.acos((Math.abs(cameraHeight-object2Height)) / distanceCameratoObject2);
		
		double updownAngle = (Math.abs(theta2-theta1))*180/Math.PI;
		
		//SidewaysAngle
		double distanceBetweenObjects = Math.sqrt(Math.pow(referenceLine[0]-tagLocation[0],2) + Math.pow(referenceLine[1]-tagLocation[1],2) + Math.pow(referenceLine[2]-tagLocation[2],2));
		
		double sidewaysAngle = (Math.acos((Math.pow(distanceCameratoObject1,2) + Math.pow(distanceCameratoObject2,2) - Math.pow(distanceBetweenObjects,2))/(2*distanceCameratoObject1*distanceCameratoObject2)))*180/Math.PI;
	
		
		double[] angles = new double[] {updownAngle, sidewaysAngle};
		
		return angles;  //send these 3 angles to the camera controlling rpi to move the motors based on these angles
		
	}
	
	public static void main(String[] args) {
		
		int[] lineofsight = equationOfLine(cameraLocation, tagLocation);
		System.out.println("z = " + lineofsight[0] + "x" + lineofsight[1]+ "y" + lineofsight[2]);
		
		int[] entranceBoundary = equationOfLine(doorEdge1, doorEdge2);
		System.out.println("z = " + entranceBoundary[0] + "x" + entranceBoundary[1]+ "y" + entranceBoundary[2]);

		
		
	}
	
	
		
}
	


