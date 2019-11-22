#include <pigpio.h>
#include <stdlib.h>
#include <unistd.h>
#include <iostream>
#include <cmath>

//Socket Stuff
#include <stdio.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <string.h>
#define PORT 8008

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
		currentState++;
		if((currentState) == 8){
			currentState = 0;
		}

		updatePinsToCurrentState();
	}

	void halfStepCCW(){
		currentState--;
		if(currentState == -1){
			currentState = 7;
		}
	
		updatePinsToCurrentState();	
		
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
	
	while(fabs(horiMotor.getAngle()) <= fabs(horiAngle) or fabs(vertiMotor.getAngle()) <= fabs(vertiAngle)){
	
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

void CommsRecieve(char *dataOut){
	int server_fd, new_socket, valread;
	struct sockaddr_in address;
	int opt =1;
	int addrlen = sizeof(address);
	char buffer[1024] = {0};
	char *hello = "Hello form server";

	cout << "entered comms" << endl;

	if((server_fd = socket(AF_INET, SOCK_STREAM, 0)) == 0){
		perror("socket failed");
		exit(EXIT_FAILURE);
	}	

	if(setsockopt(server_fd, SOL_SOCKET, SO_REUSEADDR | SO_REUSEPORT, &opt, sizeof(opt)))
	{
		perror("setsockopt");
		exit(EXIT_FAILURE);
	}	

	address.sin_family = AF_INET;
	address.sin_addr.s_addr = INADDR_ANY;
	address.sin_port = htons(PORT);
	
	if(bind(server_fd, (struct sockaddr *)&address, sizeof(address))<0){
		perror("bind failed");
		exit(EXIT_FAILURE);
	}

	if(listen(server_fd, 3) <0){
		perror("listen");
		exit(EXIT_FAILURE);
	}

	if((new_socket = accept(server_fd, (struct sockaddr *)&address, (socklen_t*)&addrlen))<0){
		perror("accept");
		exit(EXIT_FAILURE);
	}
	cout << "comms connected" << endl;

	valread = read(new_socket, buffer, 1024);
	cout << buffer << endl;
	send(new_socket, hello, strlen(hello), 0);
	cout << "sent the hello" << endl;
	dataOut = buffer;
}

int main(){
	
	char dataOut[1024] = "uninitilized";

	CommsRecieve(dataOut);

	cout << "bp1" << endl;

	StepperMotor horiMotor;
	StepperMotor vertiMotor;
	horiMotor.initMotor(OUTPUT_PIN_1, OUTPUT_PIN_2, OUTPUT_PIN_3, OUTPUT_PIN_4);
	vertiMotor.initMotor(OUTPUT_PIN_5, OUTPUT_PIN_6, OUTPUT_PIN_7, OUTPUT_PIN_8);
	cout << "bp2" << endl;	
	//			hori, verti
	double inputAngles[2] = {180, -360};
	cout << dataOut << endl;

	int dataSize = sizeof(dataOut)/sizeof(char);
	string dataString = dataOut;

	cout << dataString << endl;

	cout << "the angles are" << dataString << endl;

	turnMotorsToAngles(horiMotor, vertiMotor, inputAngles[0], inputAngles[1]);	


}


