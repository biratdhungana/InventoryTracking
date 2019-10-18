package main;

import org.json.*;

public class SendToCamera {

public JSONObject params;
	
	public SendToCamera() {
		
	}

	public JSONObject send(double xAngle, double yAngle, double zAngle) throws JSONException {
		//Get the information that will go through the sender
		params = new JSONObject();
		params.put("Angle to be moved on x-axis", xAngle);
		params.put("Angle to be moved on y-axis", yAngle);
		params.put("Angle to be moved on z-axis", zAngle);
		return params;
		}
}
