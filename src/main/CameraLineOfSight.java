package main;
import java.io.IOException;
import java.lang.Math;
import java.net.*;
import java.util.*;


public class CameraLineOfSight {

	//public static double[] tagLocation = new double[]{50,10,90};
	public static double[] cameraLocation;
	public static double[] updatedReference;
	
	public static double[] doorEdge1 = new double[]{10,20,0};
	public static double[] doorEdge2 = new double[]{15,40,7};
	
	public static int counter = 0;

	//public static double[] referenceLine = new double[]{0, 0, 0};
	
	public CameraLineOfSight() {

	}
		
	public double[] getCameraLocation() {
		return cameraLocation;
	}
	
	
	public static double[] angles(double[] tagLocation, double[] lineofsight, double[] camera) {
		
		//Calculated the angles for the movement of the camera based on the following link:
		//http://www.nabla.hr/PC-LinePlaneIn3DSp2.htm
		

        ReceiverApp rApp = new ReceiverApp();
        double[] referenceLine;

        if(counter == 0) {
        	referenceLine = lineofsight;
        }
        else {
        	referenceLine = updatedReference;
        }
        
        cameraLocation = camera;
		 
		//double[] referenceLine = new double[] {40, 50, 60};
		System.out.println("Initial Camera Line of Sight = " + referenceLine[0] + "," + referenceLine[1] + "," + referenceLine[2]);
		
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
	
		if(tagLocation[2] < referenceLine[2]) {    //if direction is down, send negative vertical angle
			updownAngle = -updownAngle;
		}
		
		if(isLeft(cameraLocation, referenceLine, tagLocation) == true) {   //if direction is left, send negative horizontal angle
			sidewaysAngle = -sidewaysAngle;
		}
		double[] angles = new double[] {updownAngle, sidewaysAngle};
		
		updatedReference = tagLocation;
		counter++;
		
		return angles;  //send these 3 angles to the camera controlling rpi to move the motors based on these angles
		
	}
	
	public static double areaTriangle(double[] a, double[] b, double[] c) {
		
		double area = Math.abs((a[0] * (b[1] - c[1]) +  b[0] * (c[1] - a[1]) + c[0] * (a[1] - b[1])) / 2.0);
		
		return area;
	}
	
	public static double areaRectangle(double a[], double b[], double c[], double d[]) {
		
		double area = areaTriangle(a, b, d) + areaTriangle(b, c, d);
		
		return area;
	}
	
	//https://stackoverflow.com/questions/1560492/how-to-tell-whether-a-point-is-to-the-right-or-left-side-of-a-line (second answer)
	public static boolean isLeft(double[] camera, double[] referenceLocation, double[] tagLocation){
	     return ((referenceLocation[0] - camera[0])*(tagLocation[1] - camera[1]) - (referenceLocation[1] - camera[1])*(tagLocation[0] - camera[0])) > 0;
	}
	
	public static boolean activateCamera1(double[] tagLocation, double[] corner1, double[] corner2, double[] corner3, double[] corner4) {
		
		double areaT1 = areaTriangle(corner1, tagLocation, corner4);
		double areaT2 = areaTriangle(corner1, tagLocation, corner2);
		double areaT3 = areaTriangle(corner2, tagLocation, corner3);
		double areaT4 = areaTriangle(corner3, tagLocation, corner4);
		
		double sumTriangles = areaT1 + areaT2 + areaT3 + areaT4;
		
		double areaRoom = areaRectangle(corner1, corner2, corner3, corner4);
		
		if(sumTriangles > areaRoom) {
			return false;
		}
		else {
			return true;
		}
	}
	
	
		
}
	


