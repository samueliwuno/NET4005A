package assignment3;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import assignment3.APIGatewayInterface;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class SoapClient {
	

	
	
	
	public void printmenu() {
		System.out.println("service < a or b > <ip address> - Specify your IP address, then select Service ");
		System.out.println("print  - Shows the requests that have been handled by the APIGateway ");
		System.out.println("exit - close client connection");
	}
	
	public void clientinput()
	{
		Scanner sc = new Scanner(System.in);
		while(true) {
		printmenu();
		System.out.print("command:");
		String cl = sc.nextLine().toLowerCase();
		String[] commands = cl.split("\\s+");
		if (commands.length == 0)
			continue;	
		switch (commands[0]) {
		
		case "service":
			if (commands.length !=2) {
				System.out.println("incomplete command. enter details in the following format 'service a'");
			}
			
			try {
				URL url = new URL("http://localhost:9000/MyWS/APIGateway?wsdl");
				QName name = new QName("http://assignment3/", "APIGatewayService");
				Service service = Service.create(url, name);
				APIGatewayInterface ag = service.getPort(APIGatewayInterface.class);
				System.out.println(ag.chooseServer(commands[1]));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case "print":
		try {
			URL url = new URL("http://localhost:9000/MyWS/APIGateway?wsdl");
			QName name = new QName("http://assignment3/", "APIGatewayService");
			Service service = Service.create(url, name);
			APIGatewayInterface ag = service.getPort(APIGatewayInterface.class);
			ag.showLog();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			break;
		case "exit":
			System.out.println("closing client");
			sc.close();
			return;
		default:
				System.out.println("invalid command, choose an option from the list");
			
		}
		}
	}
	
	
	
	public static void main(String[] args) throws MalformedURLException {
		SoapClient sc = new SoapClient();
		sc.clientinput();
		
		
	}

}
