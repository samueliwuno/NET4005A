package assignment3;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import com.sun.net.httpserver.HttpExchange;
import assignment3.Ports;

@WebService(endpointInterface = "assignment3.APIGatewayInterface")

public class APIGateway implements APIGatewayInterface{
	private Random random;
	public String Logs;
	public int requestsa=0;
	public int requestsb=0;
	public APIGateway() {
		this.random = new Random();
		this.Logs ="";
	}
	
	@Resource WebServiceContext wsContext;
	public String chooseServer(String srv) throws MalformedURLException {
		String cout= "";

		if (srv.equals("a"))
		{		
			
			int serverPort = Ports.aPorts[random.nextInt(2)];
			
			requestsa++;
			MessageContext context = wsContext.getMessageContext();
			HttpExchange exchange = (HttpExchange)context.get("com.sun.xml.internal.ws.http.exchange");
			InetSocketAddress remoteAddress = exchange.getRemoteAddress();
			String remoteHost = remoteAddress.getHostName();
			InetAddress remoteAddr = remoteAddress.getAddress();
			int clientPort = remoteAddress.getPort();
			Logs +="========================================\n";
			Logs += "Service A requested from Client "+remoteHost+" at: \n IP: "+remoteAddr+" and Port: "+clientPort+"\nSending request to server at Port: "+serverPort+"\n";
			Logs +="========================================\n";
			
			URL url = new URL("http://localhost:"+serverPort+"/MyWS/ServiceA?wsdl");
			QName name = new QName("http://assignment3/", "MyServicesService");
			Service service = Service.create(url, name);
			ServiceInterface serv = service.getPort(ServiceInterface.class);
			cout = serv.getServiceA();
			
		}	else if (srv.equals("b"))
		{
			int serverPort = Ports.bPorts[random.nextInt(2)];
			
			requestsb++;
			MessageContext context = wsContext.getMessageContext();
			HttpExchange exchange = (HttpExchange)context.get("com.sun.xml.internal.ws.http.exchange");
			InetSocketAddress remoteAddress = exchange.getRemoteAddress();
			String remoteHost = remoteAddress.getHostName();
			InetAddress remoteAddr = remoteAddress.getAddress();
			int clientPort = remoteAddress.getPort();
			Logs +="========================================\n";
			Logs+= "Service B requested from Client "+remoteHost+" at: \n IP: "+remoteAddr+" and Port: "+clientPort+"\nSending request to server at Port: "+serverPort+"\n";
			Logs +="========================================\n";
			URL url = new URL("http://localhost:"+serverPort+"/MyWS/ServiceB?wsdl");
			QName name = new QName("http://assignment3/", "MyServicesService");
			Service service = Service.create(url, name);
			ServiceInterface serv = service.getPort(ServiceInterface.class);
			cout = serv.getServiceB();
		} else {
			cout = "no valid services were requested, try again";
		}
		
		
		return cout;
	}
	public void showLog() {
		if (Logs.equals("")) {
			System.out.println("No Requests have been logged");
		} else {
			System.out.println(Logs);
		}
		System.out.println("the APIGateway received "+requestsa+" requests for serviceA and "+requestsb+" requests for serviceB");
		System.out.println("the APIGateway has handled "+(requestsa+requestsb)+" requests in total");
		
	}
	
	

}
