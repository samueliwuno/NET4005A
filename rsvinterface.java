package assignment2;
import java.rmi.*;

public interface rsvinterface extends java.rmi.Remote {
	
	String list() throws RemoteException;
	String reserve(String commands2, String commands3, String commands4) throws RemoteException;
	String passengerlist()throws RemoteException;
	
	

}
