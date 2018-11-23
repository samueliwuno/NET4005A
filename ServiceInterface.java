package assignment3;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface ServiceInterface {
	@WebMethod public String getServiceA();
	@WebMethod public String getServiceB();
	

}
