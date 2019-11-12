#include <pigpio.h>
#include <stdlib.h>
#include <unistd.h>
#include <iostream>
#include <cmath>

using namespace std;

#define OUTPUT_PIN_1 14
#define OUTPUT_PIN_2 15
#define OUTPUT_PIN_3 18
#define OUTPUT_PIN_4 23

#define OUTPUT_PIN_5 4
#define OUTPUT_PIN_6 17
#define OUTPUT_PIN_7 27
#define OUTPUT_PIN_8 22

class StepperMotor
{
	public:
	int halfStepSequence[8][4] = {
		{1,0,0,0},
		{1,1,0,0},
		{0,1,0,0},
		{0,1,1,0},
		{0,0,1,0},
    		{0,0,1,1},
		{0,0,0,1},
		{1,0,0,1}
	};
	int pin1;
	int pin2;
	int pin3;
	int pin4;

	double currentAngle=0;

	//initialize the first state. the state will be stored for each motor system.
	int currentState = 0;

	double getAngle(){
		return currentAngle;
	}

	void setAngle(double angle){
		currentAngle = angle;
	}


	void updatePinsToCurrentState(){
		gpioWrite(this->pin1,halfStepSequence[currentState][0]);
		gpioWrite(this->pin2, halfStepSequence[currentState][1]);
		gpioWrite(this->pin3, halfStepSequence[currentState][2]);
		gpioWrite(this->pin4, halfStepSequence[currentState][3]);	
	}

	void halfStepCW(){
		if(currentState == 8){
			currentState = 0;
		}

		updatePinsToCurrentState();
		currentState++;
	}
	void halfStepCCW(){
		if(currentState == -1){
			currentState = 7;
		}
	
		updatePinsToCurrentState();	
		currentState--;
	}

	void driverPinsOff(){
		gpioWrite(this->pin1, 0);
		gpioWrite(this->pin2, 0);
		gpioWrite(this->pin3, 0);
		gpioWrite(this->pin4, 0);
	}

	int initMotor(int pin1, int pin2, int pin3, int pin4){
		
		if(gpioInitialise() < 0){
			exit(1);
		}

		this->pin1 = pin1;
		this->pin2 = pin2;
		this->pin3 = pin3;
		this->pin4 = pin4;
	 
		gpioSetMode(pin1, PI_OUTPUT);
		gpioSetMode(pin2, PI_OUTPUT);
		gpioSetMode(pin3, PI_OUTPUT);
		gpioSetMode(pin4, PI_OUTPUT);
		
		driverPinsOff();
	}
};

void turnMotorsToAngles(StepperMotor horiMotor, StepperMotor vertiMotor, double horiAngle, double vertiAngle){

	while(fabs(horiMotor.getAngle()) <= fabs(horiAngle) && fabs(vertiMotor.getAngle()) <= fabs(vertiAngle)){
		if(horiMotor.getAngle() < horiAngle){
			horiMotor.halfStepCW();
			horiMotor.setAngle(horiMotor.getAngle()+0.088);//motor turns 0.088 degrees per step
			usleep(500);
		}
		else if(horiMotor.getAngle() > horiAngle){
			horiMotor.halfStepCCW();
			horiMotor.setAngle(horiMotor.getAngle()-0.088);//motor turns 0.088 degrees per step
			usleep(500);
		}

		if(vertiMotor.getAngle() < vertiAngle){
			vertiMotor.halfStepCW();
			vertiMotor.setAngle(vertiMotor.getAngle()+0.088);//motor turns 0.088 degrees per step
			usleep(500);
		}
		else if(vertiMotor.getAngle() > vertiAngle){
			vertiMotor.halfStepCCW();
			vertiMotor.setAngle(vertiMotor.getAngle()-0.088);//motor turns 0.088 degrees per step 	
		}

	}
}

int main(){
	
	StepperMotor horiMotor;
	StepperMotor vertiMotor;
	horiMotor.initMotor(OUTPUT_PIN_1, OUTPUT_PIN_2, OUTPUT_PIN_3, OUTPUT_PIN_4);
	vertiMotor.initMotor(OUTPUT_PIN_5, OUTPUT_PIN_6, OUTPUT_PIN_7, OUTPUT_PIN_8);
	
	//			hori, verti
	double inputAngles[2] = {45, 180};
	turnMotorsToAngles(horiMotor, vertiMotor, inputAngles[0], inputAngles[1]);	

}
