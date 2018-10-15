package Assignment1;
import java.io.*;
import java.net.*;
import java.util.*;

public class myfileclient {
	
	public static final int serverPort = 6800;
	private static final String defaultFolder = System.getProperty("user.dir") + File.separator + "cfiles";
	private String folder = defaultFolder;
	public static String host = "localhost";
	
	
	/**
	 * 
	 * @return
	 */
	private String getfolder() {return folder;}
	/**
	 * 
	 */
	private static void menu() {
		System.out.println("Welcome "+host+ " to the FileServer. Please select the operation you wish to perform");
		System.out.println("menu - show main menu");
		System.out.println("read <filename> - read file from server (i.e read rolly.txt)");
		System.out.println("exit - close client connection");
	}
	/**
	 * 
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
		case "menu":
			menu();
			continue;
		case "read":
			if (commands.length !=2) {
				System.out.println("<fileName> not found on server. Please enter FQN of the file i.e read rolly.txt");
			}
			readfromserver(commands[1]);
			System.out.println("File Found, Downloading....");
			break;
		case "exit":
			System.out.println("Terminating Client...");
			sc.close();
			return;
		default:
			System.out.println("Incompatible command, Please enter command from list");
			}
		}
	}
	/**
	 * 
	 * @param fileName
	 * @throws FileNotFoundException
	 */
	public void readfromserver(String fileName) throws FileNotFoundException {
		int current = 0;
		int bytesRead;
		try {
			Socket socket = new Socket(host,serverPort);
			if (socket.isConnected()) {
			System.out.println("connection to server established");
			PrintStream ps = new PrintStream(socket.getOutputStream());
			ps.println(fileName);
			ps.flush();
			
			//
			
			String filePath = getfolder()+File.separator+fileName;
			File file = null;
			file = new File(filePath);
			InputStream is = socket.getInputStream();
			FileOutputStream fos  = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			byte[] b = new byte[999999999];
			bytesRead = is.read(b,0,b.length);
			 current = bytesRead;
			 do {
					System.out.println("Downloading file "+ fileName);
					bytesRead = is.read(b, current,
							(b.length - current));
					if (bytesRead >= 0)
						current += bytesRead;
				}

				while (bytesRead > -1);
				{
					bos.write(b, 0, current);
					bos.flush();
					System.out.println("Download Complete");
				} 
			
			}
		//	socket.close();
			
		} catch (SocketException e) {
			System.out.println("cannot create socket");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Critical Error");
		}
		
		
			
			
			
			
			
	}
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
