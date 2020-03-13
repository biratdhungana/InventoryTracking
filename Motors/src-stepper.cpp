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
#define PORT 6000

using namespace std;

#define OUTPUT_PIN_1 2
#define OUTPUT_PIN_2 3
#define OUTPUT_PIN_3 4
#define OUTPUT_PIN_4 17

#define OUTPUT_PIN_5 14
#define OUTPUT_PIN_6 15
#define OUTPUT_PIN_7 18
#define OUTPUT_PIN_8 23

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

		return true;
	}
};

void turnMotorsToAngles(StepperMotor horiMotor, StepperMotor vertiMotor, double horiAngle, double vertiAngle){

	horiMotor.setAngle(0);
	vertiMotor.setAngle(0);

	while(fabs(horiMotor.getAngle()) <= fabs(horiAngle) or fabs(vertiMotor.getAngle()) <= fabs(vertiAngle)){
	
		if((horiMotor.getAngle()) < (horiAngle)){
			horiMotor.halfStepCW();
			horiMotor.setAngle(horiMotor.getAngle()+0.088);//motor turns 0.088 degrees per step
			cout << "turning CW hori " << horiMotor.getAngle() << endl;
			usleep(500);
		}
		else if((horiMotor.getAngle()) > (horiAngle)){
			horiMotor.halfStepCCW();
			horiMotor.setAngle(horiMotor.getAngle()-0.088);//motor turns 0.088 degrees per step
			cout << "turning CCW hori " << horiMotor.getAngle() <<  endl;
			usleep(500);
		}

		if((vertiMotor.getAngle()) < (vertiAngle)){
			vertiMotor.halfStepCW();
			vertiMotor.setAngle(vertiMotor.getAngle()+0.088);//motor turns 0.088 degrees per step
			cout << "turning CW verti " << vertiMotor.getAngle() << endl;
			usleep(500);
		}
		else if((vertiMotor.getAngle()) > (vertiAngle)){
			vertiMotor.halfStepCCW();
			vertiMotor.setAngle(vertiMotor.getAngle()-0.088);//motor turns 0.088 degrees per step 
			cout << "turning CCW verti " << vertiMotor.getAngle() << endl;
			usleep(500);	
		}
		
		if((abs(horiMotor.getAngle() - (horiAngle))< 0.1) && (abs(vertiMotor.getAngle() - (vertiAngle))< 0.1)){

			horiMotor.getAngle();
			cout << "byebye!" << endl;
			break;

		}
	
	}
}

void receiveAndTurn(StepperMotor horiMotor, StepperMotor vertiMotor){
	int server_fd, new_socket, valread;
	struct sockaddr_in address;
	int opt =1;
	int addrlen = sizeof(address);
	char buffer[1024] = {0};
	char *ack = "a\n";
	//			hori, verti
	double inputAngles[2] = {0, 0};
	cout << "entered communications" << endl;

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
	//address.sin_addr.s_addr = inet_addr("192.168.1.101");
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

	valread = read(new_socket, buffer, 1024);
	cout << buffer << endl;
	string dataOut = buffer;

	//At this point, buffer contains data
	//time to parse data (two space delimited angles) and turn motors
	std::string delimeter = " ";
	size_t pos = dataOut.find(delimeter);

	inputAngles[0] = stod(dataOut.substr(0, pos));
	dataOut.erase(0, pos+delimeter.length());
	inputAngles[1] = stod(dataOut.substr(0, pos));	
		
	cout << "the angles are " << inputAngles[0] << " and " << inputAngles[1] << endl;
	turnMotorsToAngles(horiMotor, vertiMotor, inputAngles[0], inputAngles[1]);	
	usleep(10000);	
	cout << "motors have turned, sending acknowledgement to server..." << endl;
	int test = send(new_socket, ack, strlen(ack), 0);
	cout << "data sent " << test << endl;
	shutdown(server_fd, 2);
}
/*
void commsSendAck(){
	int server_fd, new_socket, valread;
	struct sockaddr_in address;
	int opt =1;
	int addrlen = sizeof(address);
	char *ack = "ready for data";
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

	cout << "about to sleep" << endl;
	usleep(50000000);
	cout << "done sleeping" << endl;
	send(new_socket, ack, strlen(ack), 0);
	cout << "sent" <<endl;
	usleep(50000000);
	shutdown(server_fd, 2);
}
*/


int main(){

	//Future implementation: server can shut down the system by sending a terminate signal?
	bool terminate = false;
	
	//initialize GPIO pins and set an initial state
	StepperMotor horiMotor;
	StepperMotor vertiMotor;
	horiMotor.initMotor(OUTPUT_PIN_1, OUTPUT_PIN_2, OUTPUT_PIN_3, OUTPUT_PIN_4);
	vertiMotor.initMotor(OUTPUT_PIN_5, OUTPUT_PIN_6, OUTPUT_PIN_7, OUTPUT_PIN_8);

	while(terminate ==false){
		receiveAndTurn(horiMotor, vertiMotor);
	}
	
	//turnMotorsToAngles(horiMotor, vertiMotor, 300, 300);
	
}


