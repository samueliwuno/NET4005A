package assignment2;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.ArrayList;


//Implements the methods declared in the interface that will be invoked by the remote client.

/**
 * 
 * @author Samuel Iwuno(100960021)
 *@author Greg Kingsbury(101004429)
 */
public class Server extends RemoteObject implements rsvinterface
{
	
 
 public static ArrayList<Integer> seats = new ArrayList<>();
 public ArrayList<plist> passengerlist = new ArrayList<>(); 
 int lowprice = 0;
 int midprice=0;
 int highprice=0;
 private static String serverName = "server";
 
 
 



	
	/**
	 * this methods shows the available seats on the Server
	 * @param commands
	 */
	public String list() throws RemoteException {
		 int Bclass = 0;
		 int Eclass = 0;
		 String bseats = "";
		 String eseats = "";
		
		 String b="";
		 String e = "";
		 
		// for loop to determine business/economy seats
		for (int i=0;i<seats.size();i++) {
 			if (seats.get(i) < 5) {
 				Bclass++;
 				bseats += seats.get(i)+", ";
 				
 			} else if (seats.get(i) == 5) {
 				Bclass++;
 				bseats +=seats.get(i);
 				
 			} else if (seats.get(i) > 5 && seats.get(i) <30) {
 				Eclass++;
 				eseats +=seats.get(i)+", ";
 				
 			} else if (seats.get(i) == 30) {
 				Eclass++;
 				eseats +=seats.get(i);
 				
 			} //end of if/else if loop
 		} //end of for loop to populate eseats and bseats
		
		// if statements to set prices for business seats 
		if (Bclass >0) {
			b = " Business Class:\n";
			
			if (Bclass > 3) {
				lowprice =Bclass -2;
				highprice = 2;
				b += lowprice+ " seats at $500 each \n" + highprice +" seats at $800 each \n";
				b += "seat numbers: " + bseats;
			} else {
				highprice = Bclass;
				b += highprice+" seats at $800 each \n";
				b += "seat numbers: " + bseats;
			}
		} else { b = "No business Tickets available.";}
		
		// if statements to set prices for economy tickets
		if (Eclass > 0) {
			e = "\n Economy Class:\n";
			if (Eclass >15) {
				lowprice = Eclass - 15;
				midprice = 10;
				highprice = 5;
				e += lowprice + " seats at $200 each \n"+midprice+" seats at $300 each \n"+highprice+" seats at $450 each \n";
				e += "seat numbers: "+eseats;
			} else if (Eclass >5) {
				midprice = Eclass - 5;
				highprice = 5;
				e += midprice+" seats at $300 each \n"+highprice+" seats at $450 each \n";
				e += "seat numbers: "+eseats;
			} else {
				highprice = Eclass;
				e+= highprice+"seats at $450 each \n";
				e+= "seat numbers: " + eseats;
				
			}
		} else { e= "No economy Seats available.\n";} // end of if else statements to populate list with accurate prices
	String cOutput = b+e;
	return cOutput;	
	}
	
	@Override
	/**
	 * this method is used to reserve the airplane seats
	 * @param commands2 holds flight class
	 * @param commands3 holds passenger name
	 * @param commands4 holds seat number
	 */

	public String reserve(String commands2, String commands3, String commands4) throws RemoteException {
		String Pnames = commands3;
		String Pclass = commands2;
		int seatNumber = Integer.parseInt(commands4);
		String cOutput = "";
		boolean available = false;
		for (int i=0;i<seats.size();i++) 
		{
			
				if (seatNumber==seats.get(i)) {
					available = true;
					passengerlist.add(new plist(Pnames,Pclass,seatNumber));
					seats.remove(i);
					cOutput = "Successfully reserved seat #"+seatNumber+" for "+Pnames;
					break;
				}
				if (available == false) {
					cOutput = "failed to reserve: seat not available";
				}
	
	}
		return cOutput;
	}
	
	
	/**
	 * this method prints out all booked reservations
	 * @param commands holds server_name
	 * 
	 */
	public String passengerlist() throws RemoteException {
		
		String cOutput= "\n Reservations booked \n";
		for (plist list : passengerlist) {
			cOutput +=list+"\n";
		}
		cOutput +="\n end of list \n"; 
		return cOutput;
	
	}
	
	
	
	
	
	public static void main(String[] args)
	{
		try
		{
			rsvinterface obj = new Server();
			Registry registry = LocateRegistry.createRegistry(1098);
			rsvinterface stub = (rsvinterface) UnicastRemoteObject.exportObject(obj, 0);
			registry.bind(serverName, stub);
			for (int i=1;i<=30;i++) {
				seats.add(i);
			}
			
		} catch (RemoteException ex)
		{
			System.err.println(ex.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());;
		}
		System.out.println("Server is ready");
	}

	/**
	 * this class is used to take all user info into an arraylist and correctly display them as required
	 * @author samuel
	 *
	 */
class plist
{
	String Pnames;
	String Pclass;
	int seatNumber;
	
	public plist(String Pnames, String Pclass, int seatNumber) {
		this.Pnames = Pnames;
		this.Pclass = Pclass;
		this.seatNumber = seatNumber;
	}
	
	public String toString() {
		return  Pnames+", "+Pclass+", "+seatNumber;
	}
}
	

}