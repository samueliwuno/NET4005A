package assignment3;

import javax.xml.ws.Endpoint;

public class Publisher {
	
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:9000/MyWS/APIGateway", new APIGateway());
		Endpoint.publish("http://localhost:9001/MyWS/ServiceA", new MyServices());
		Endpoint.publish("http://localhost:9002/MyWS/ServiceA", new MyServices());
		Endpoint.publish("http://localhost:9003/MyWS/ServiceB", new MyServices());
		Endpoint.publish("http://localhost:9004/MyWS/ServiceB", new MyServices());
		
		
	}


}
