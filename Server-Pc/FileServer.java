import java.io.*;
import java.net.*;

public class FileServer {

  public final static int SOCKET_PORT = 13267;  // you may change this

  public final static String
       FILE_TO_RECEIVED = "test.png";  // you may change this, I give a
                                                            // different name because i don't want to
                                                            // overwrite the one used by server...

  public final static int FILE_SIZE = 6022386; // file size temporary hard coded
                                               // should bigger than the file to be downloaded
  public static void main (String [] args ) throws IOException {
    int bytesRead;
    int current = 0;
    DataInputStream dis = null ; 
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    ServerSocket servsock = null;
    Socket sock = null;
    try {
      servsock = new ServerSocket(SOCKET_PORT);
      while (true) {
        System.out.println("Waiting...");
        try {
          sock = servsock.accept();
          System.out.println("Accepted connection : " + sock);
          dis = new DataInputStream(sock.getInputStream());
          String imgname = dis.readUTF();
          String result = dis.readUTF();
          String time = dis.readUTF();
          // receive file
          byte [] mybytearray  = new byte [FILE_SIZE];
          InputStream is = sock.getInputStream();
          fos = new FileOutputStream(imgname+".jpg");
          bos = new BufferedOutputStream(fos);
          bytesRead = is.read(mybytearray,0,mybytearray.length);
          current = bytesRead;

          do {
            bytesRead =
                is.read(mybytearray, current, (mybytearray.length-current));
            if(bytesRead >= 0) current += bytesRead;
          } while(bytesRead > -1);

          bos.write(mybytearray, 0 , current);
          bos.flush();
          System.out.println("File " + imgname + " downloaded (" + current + " bytes read)");
          System.out.println("Test result :"+result+"\n"+"Found in :"+time);
        }
        finally {
        if (fos != null) fos.close();
        if (bos != null) bos.close();
        if (sock != null) sock.close();
        }
      }
    }
    finally {
      if (servsock != null) servsock.close();
    }
  }
}