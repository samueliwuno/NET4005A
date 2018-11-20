package Assignment1;
import java.io.*;
import java.net.*;
import java.util.*;



/**
 * 
 * @author Samuel Iwuno(100960021)
 * 		   Greg Kingsbury(101004429)
 *
 */

public class myfileclient {
	
	public static final int serverPort = 6800;
	private static final String defaultFolder = System.getProperty("user.dir") + File.separator + "cfiles";
	private String folder = defaultFolder;
	public static String host = "localhost";
	
	
	
	/**
	 * returns folder 
	 * @return
	 */
	private String getfolder() {return folder;}
	
	
	
	
	/**
	 * prints menu as a guideline for the client
	 */
	private static void menu() {
		System.out.println("Welcome "+host+ " to the FileServer. Please select the operation you wish to perform");
		System.out.println("read <filename> - read file from server (i.e read rolly.txt)");
		System.out.println("exit - close client connection");
	}
	
	
	
	
	/**
	 * this method handles the user input
	 * @throws IOException
	 */
	public void menuaction() throws IOException {
		Scanner sc = new Scanner(System.in);
		while(true) {
		menu();
		System.out.print("command:");
		String cl = sc.nextLine().toLowerCase();
		String[] commands = cl.split("\\s+");
		if (commands.length == 0)
			continue;
		
		switch (commands[0]) {
		case "read":
			if (commands.length !=2) {
				System.out.println("<fileName> not found on server. Please enter FQN of the file i.e read rolly.txt");
			}
			readfromserver(commands[1]);
			break;
		case "exit":
			System.out.println("Terminating Client...");
			sc.close();
			return;
		default:
			System.out.println("Incompatible command, Please enter command from list");
			}//end of switch
		}//end of while loop
	}// end of menuaction()
	
	
	
	/**
	 * method to send filename to server, and if server has that file, to get it and outputs the content into a file of same name in client
	 * @param fileName
	 * @throws FileNotFoundException
	 */
	public void readfromserver(String fileName) throws FileNotFoundException {
		int current;
		long bytesRead =0;
		
		try {
			Socket socket = new Socket(host,serverPort);
			if (socket.isConnected()) {
				
			/**
			 *  sending filename to server
			 */
			System.out.println("connection to server established");
			PrintStream ps = new PrintStream(socket.getOutputStream());
			ps.println(fileName);
			ps.flush();
			
		
			
			/**
			 * reading N(no of requests) and M(successes) statistics and size of file from server
			 */
			DataInputStream dis = new DataInputStream(socket.getInputStream());	
			String Ncount = dis.readUTF();
			String Mcount = dis.readUTF();
			long fileSize=Long.parseLong(dis.readUTF());
			
			/**
			 * creates file in client directory with contents it received from the server
			 */
			if (fileSize !=0)
			{
			
				String filePath = getfolder()+File.separator+fileName;
				File file = null;
				file = new File(filePath);
				FileOutputStream fos  = new FileOutputStream(file);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				byte[] b = new byte[1024];
				
			while (bytesRead < fileSize)
			{
				System.out.println("Downloading file "+ fileName);
				current = dis.read(b,0,b.length);
				bytesRead += current;
				bos.write(b,0,b.length);
				bos.flush();
				
			}
			System.out.println("Download Complete");
			bos.close();
			System.out.println("Server has handled "+Ncount+" requests, "+Mcount+" were successful");
			
			
			
			} else {
				System.out.println(fileName+"does not exist on the server");
				System.out.println("Server has handled "+Ncount+" requests, "+Mcount+" were successful");
				menuaction();
			}//end of if statement to download files in parts
			socket.close();
			
		}//end of if statement for socket connection
		} catch (SocketException e) {
			System.out.println("cannot create socket");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Critical Error");
		} //end of try/catch
		
		
			
			
			
			
			
	} //end of readfromserver
	/**
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		myfileclient c = new myfileclient();
		System.out.println("Client is Operational");
		c.menuaction();
	}
	
	

}
