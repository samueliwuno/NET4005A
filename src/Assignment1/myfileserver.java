package Assignment1;
import java.io.*;
import java.net.*;


public class myfileserver {
	public static final int serverPort = 6800;
	private static final String defaultFolder = System.getProperty("user.dir") + File.separator + "sfiles";
	private static String folder = defaultFolder;
	private String fileName;
	private byte[] b;
	ServerSocket server;
	Socket client;
	
	

	private String getfolder() {return folder;}
	
	public void acceptconnection()  {
		try {
			ServerSocket server = new ServerSocket(serverPort);
			Socket clientx = server.accept();
			this.client = clientx;
			System.out.println("connection accepted, extracting filename");
			getfileName();
			System.out.println("filname extracted, checking if file exists");
			writetoclient();
		} catch (IOException e) {
			System.out.println("Error Accepting client connection");
			e.printStackTrace();
		}
		
	}
	

	private void getfileName() {		
		try {
			InputStreamReader isr = new InputStreamReader(client.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			String cfn = br.readLine();
			System.out.println(cfn);
			if (cfn != null)
				fileName = cfn;
			System.out.println("filename is "+fileName);
		} catch (IOException e) {
			System.out.println("cannot extract fileName");
			e.printStackTrace();
		}
		
		
	}
	public void writetoclient() {
		
		
		try {
			
			
			String filePath = getfolder()+File.separator+fileName;
			File file = null;
			file = new File(filePath);
			
			if(file.exists()) {
			byte[] b = new byte[(int) file.length()];
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(b,0,b.length);
			OutputStream os = client.getOutputStream();
			System.out.println("sending "+fileName+"("+b.length+" bytes)");
			os.write(b,0,b.length);
			os.flush();
			fis.close();
			} 
			
			
		} catch (FileNotFoundException e) {
			System.out.println("File "+fileName+" does not exist on the server");
			e.printStackTrace();
		} catch (SocketException e) {
			System.out.println("Error Creating Socket");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Critical Error");
			e.printStackTrace();
		} 
		
		
		
		
	}
	
	public static void main(String[] args) {
		myfileserver s = new myfileserver();
		System.out.println("File Server is Operational. waiting for connection");
		s.acceptconnection();
		
	}
}