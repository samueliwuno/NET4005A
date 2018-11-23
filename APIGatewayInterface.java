package assignment3;

import java.net.MalformedURLException;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface APIGatewayInterface {
	@WebMethod public String chooseServer(String srv) throws MalformedURLException;
	@WebMethod public void showLog();

}
