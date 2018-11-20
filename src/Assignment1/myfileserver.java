package Assignment1;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author Samuel Iwuno(100960021)
 * 		   Greg Kingsbury(101004429)
 *
 */
public class myfileserver {
	
	public static void main(String[] args) throws IOException  {
		System.out.println("File Server is Operational. waiting for connection");
		final int serverPort = 6800;
		ServerSocket server = new ServerSocket(serverPort);
		
		try {
			
			ExecutorService workerthread = Executors.newFixedThreadPool(10); // This sets a maximum of 10 worker threads
			nmCounter nm = new nmCounter();
		    
			while (true) {
				Socket client = server.accept();
				workerthread.execute(new ServerConnectionThread(client, nm));
				}
			}catch (SocketException ex) {
				System.out.println("cannot establish connection to client");
				System.out.println(ex);
		}
		server.close();
		
	}
}



class ServerConnectionThread extends Thread{
	
	private static final String defaultFolder = System.getProperty("user.dir") + File.separator + "sfiles";
	private static String folder = defaultFolder;
	private String fileName;
	ServerSocket server;
	Socket client;
	nmCounter nm;
	ServerConnectionThread(Socket c, nmCounter r) {
		client = c;
		nm = r;
	}
	/**
	 * method to get server directory
	 * @return
	 */
	private String getfolder() {return folder;}
	
	/**
	 * method to call other methods so main can be without variables/objects
	 */
	public void acceptconnection()  {
		getfileName();
		System.out.println("filname extracted, checking if file exists");
		writetoclient();
		
	}
	
	
	/**
	 * method to extract the filename
	 */
	private void getfileName() {		
		try {
			InputStreamReader isr = new InputStreamReader(client.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			String cfn = br.readLine();
			if (cfn != null)
				fileName = cfn;
			System.out.println("filename is "+fileName);
		} catch (IOException e) {
			System.out.println("cannot extract fileName");
			e.printStackTrace();
		}
		
		
	}
	
	
	/**
	 * method to check if file exist in server directory, and if it does send it to client
	 */
	public void writetoclient() {
		
		InetAddress inet = client.getInetAddress();
		try {
			
			
			//DataInputStream in = new DataInputStream(client.getInputStream());
			DataOutputStream out = new DataOutputStream(client.getOutputStream());
			
			
			String filePath = getfolder()+File.separator+fileName;
			File file = null;
			file = new File(filePath);
			
			
			if(file.exists()) {
			System.out.println("file exists");
			nm.ncounter();
		    nm.mcounter();
		    out.writeUTF(Integer.toString(nm.getncount()));
		    out.writeUTF(Integer.toString(nm.getmcount()));
		    out.writeUTF(Long.toString(file.length()));
		    byte[] b = new byte[(int) file.length()];
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(b,0,b.length);
			OutputStream os = client.getOutputStream();
			System.out.println("sending "+fileName+"("+b.length+" bytes)");
			os.write(b,0,b.length);
			os.flush();
			fis.close();
			os.close();
			out.close();
			
			System.out.println("REQ " + nm.getncount() +": File " + fileName + " requested from " + inet.getHostAddress()); 
	    	System.out.println("REQ " + nm.getncount() +": successful");
	    	System.out.println("REQ " + nm.getncount() +": Total successful requests so far = " + nm.getmcount());
	    	System.out.println("REQ " + nm.getncount() +": Total requests so far = " + nm.getncount());
			System.out.println("REQ " + nm.getncount() +": File transfer complete ");
			
			
			
			
			} else {
				System.out.println(fileName+ " does not exist on the server");
				out.writeUTF("No");
		    	nm.ncounter();
			    out.writeUTF(Integer.toString(nm.getncount()));
			    out.writeUTF(Integer.toString(nm.getmcount()));
		    	System.out.println("REQ " + nm.getncount() +": File " + fileName + " requested from " + inet.getHostAddress());
		    	System.out.println("REQ " + nm.getncount() +": not successful");
		    	System.out.println("REQ " + nm.getncount() +": Total successful requests so far = " + nm.getmcount()); 
		    	System.out.println("REQ " + nm.getncount() +": Total requests so far = " + nm.getncount());
				System.out.println("REQ " + nm.getncount() +": File transfer not complete ");
				out.close();
			} //end of if/else
			
			
		} catch (FileNotFoundException e) {
			System.out.println("File "+fileName+" does not exist on the server");
			e.printStackTrace();
		} catch (SocketException e) {
			System.out.println("Error Creating Socket");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Critical Error");
			e.printStackTrace();
		} //end of try/catch
		
		
		
		
	} //end of writetoclient method
	
	
	
	public void run() {
		acceptconnection();
	}
}
	
class nmCounter
{
	int ncount = 0;
	int mcount = 0;
	
	//Constructors
	public synchronized int getncount() {
		return ncount;
	}
	public synchronized int getmcount() {
		return mcount;
	}
	//These functions increment the count variables
	public synchronized void ncounter() {
		ncount++;
	}
	public synchronized void mcounter() {
		mcount++;
	}
}

	