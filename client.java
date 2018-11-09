package assignment2;



import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

//This is a user interface which captures user data calls the remote 
//method that checks for validation.

public class client
{
	String serverName;
	rsvinterface obj;
	
	
	public void acceptconnection() {
		System.out.println("attempting to establish connection to RMIserver...");
		Registry registry;
	 
		try {
			registry = LocateRegistry.getRegistry("localhost", 1098);
			obj = (rsvinterface) registry.lookup(serverName);
			System.out.println("RMI connection established");
			
			
		} catch (RemoteException e) {
			System.out.println("cannot find RMI Server");
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.out.println("Cannot Bind to RMI Server");
			e.printStackTrace();
		}
		
		
		
	}
	
	public void printmenu()
	{
		
		System.out.println("list <server_name> - shows all available seats and price for business economy class");
		System.out.println("reserve <server_name> <class> <passenger_name> <seat_number> - reserves flight seat");
		System.out.println("passengerlist <server_name> - show all booked passengers");
		System.out.println("exit - close client connection");
	}
	
	public void clientinput() throws RemoteException
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
		
		case "list":
			if (commands.length !=2) {
				System.out.println("incomplete command. enter details in the following format 'list <server_name>'");
			}
			serverName = commands[1];
			acceptconnection();
			System.out.println(obj.list());
			break;
		case "reserve":
			if (commands.length !=5) {
				System.out.println("incomplete command, fill in all the required info as shown");
			}
			
			if (Integer.parseInt(commands[4]) <= 5 && commands[2].equals("economy")) 
			{System.out.println("Invalid Seat and Class type, Try again");}
			else if (Integer.parseInt(commands[4]) > 5 && commands[2].equals("business"))
			{System.out.println("Invalid Seat and Class type, Try again");}
			else {
				serverName = commands[1];
				acceptconnection();
				System.out.println(obj.reserve(commands[2], commands[3], commands[4]));
			}
			
			break;
		case "passengerlist":
			if (commands.length != 2) {
				System.out.println("incomplete command, enter details in the following format 'passengerlist <server_name>' ");
			}
			serverName = commands[1];
			
			acceptconnection();
			System.out.println(obj.passengerlist());
			break;
		case "exit":
			System.out.println("closing client");
			sc.close();
			return;
		default:
				System.out.println("choose an option from the list");
			
		}
		}
	}
	
	

	
	

	public static void main(String[] args)
	{
		 client c = new client();
		 try {
		 System.out.println("Welcome to the RMI reservation system. Please select the operation you wish to perform");
			c.clientinput();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


