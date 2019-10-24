#include <pigpio.h>
#include <stdlib.h>

#define OUTPUT_PIN_1 14
#define OUTPUT_PIN_2 15
#define OUTPUT_PIN_3 18
#define OUTPUT_PIN_4 25

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

	//initialize the first state. the state will be stored for each motor system.
	int currentState = 0;

	void updatePinsToCurrentState(){
		gpioWrite(OUTPUT_PIN_1, halfStepSequence[currentState][0]);
		gpioWrite(OUTPUT_PIN_2, halfStepSequence[currentState][1]);
		gpioWrite(OUTPUT_PIN_3, halfStepSequence[currentState][2]);
		gpioWrite(OUTPUT_PIN_4, halfStepSequence[currentState][3]);
	}

	void halfStep(){
		currentState++;
		updatePinsToCurrentState();
	}

	void driverPinsOff(){
		gpioWrite(OUTPUT_PIN_1, 0);
		gpioWrite(OUTPUT_PIN_2, 0);
		gpioWrite(OUTPUT_PIN_3, 0);
		gpioWrite(OUTPUT_PIN_4, 0);
	}

	int initMotor(){
		if(gpioInitialize() < 0){
			exit(1);
		}
		gpioSetMode(OUTPUT_PIN_1, PI_OUTPUT);
		gpioSetMode(OUTPUT_PIN_2, PI_OUTPUT);
		gpioSetMode(OUTPUT_PIN_3, PI_OUTPUT);
		gpioSetMode(OUTPUT_PIN_4, PI_OUTPUT);
		driverPinsOff();
	}
};

int main(){

	StepperMotor horiMotor;
	horiMotor.initMotor();
}
