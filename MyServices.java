package assignment3;

import javax.jws.WebService;
import  assignment3.ServiceInterface;

@WebService(endpointInterface = "assignment3.ServiceInterface")
public class MyServices implements ServiceInterface {
	public String getServiceB() {
		String cout = "This is service B";
		return cout;
	}

	public String getServiceA() {
		String cout = "This is Service A";
		return cout;
	}

}
