package server;

import java.io.*;
import java.net.*;

public class Server {
  public final static int SOCKET_PORT = 2023;
  
  public void RunServer() throws IOException {
	  ServerSocket ss = new ServerSocket(SOCKET_PORT);
	  System.out.println("Server Started waiting for Connection");
	  while (true) 
	  {
			Socket s = null; 
			try
			{	
				s = ss.accept(); 
				System.out.println("A new client is connected : " + s);
				
				System.out.println("Assigning new thread for this client"); 
				
				// create a new thread object 
				Thread t = new ClientHandler(s); 
				t.start(); 
				
			} 
			catch (Exception e){
				System.out.println(e);
				s.close();
				ss.close();
				e.printStackTrace(); 
			} 
		} 
  }
}