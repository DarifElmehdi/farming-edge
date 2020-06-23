package server;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.List;

import beans.Analyse;
import beans.AnalyseDevice;
import beans.Connect;
import beans.ConnectDevice;
import beans.Device;
import dao.AnalyseDao;
import dao.ConnectDao;
import dao.DaoFactory;
import dao.DeviceDao;

public class ClientHandler extends Thread {
	int bytesRead;
    int current = 0;
    public final static int FILE_SIZE = 6022386;
    DataInputStream dis = null ; 
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    ServerSocket servsock = null;
    Socket s = null;

    DaoFactory daoFactory = DaoFactory.getInstance();
	DeviceDao deviceDao=daoFactory.getDeviceDao();
	ConnectDao connectDao=daoFactory.getConnectDao();
	AnalyseDao analyseDao=daoFactory.getAnalyseDao();
	
    public ClientHandler(Socket s) {
		this.s=s;
	}
    @Override
    public void run() {
            try {
              dis = new DataInputStream(s.getInputStream());
              String imgname = dis.readUTF();
              String result = dis.readUTF();
              String time = dis.readUTF();
              String lat = dis.readUTF();
              String lng =  dis.readUTF();
              String label = dis.readUTF();
              String device = dis.readUTF();
              String confidence = dis.readUTF();
              String imagepath = imgname+".jpg";
              Device d = new Device(0,device);
              System.out.println("here;");
              deviceDao.addDevice(d);
              int dId = deviceDao.getDeviceId(device);
              Timestamp currenttime = new Timestamp(System.currentTimeMillis());
              Connect c = new Connect(dId,currenttime,s.getInetAddress().toString().replace("/",""),s.getPort());
              connectDao.addConnect(c);
              Analyse a = new Analyse(0,dId,currenttime,result,imagepath,result.substring(0,result.indexOf(' ')),Integer.parseInt(time.replace("ms","")),Double.parseDouble(lat),Double.parseDouble(lng),Double.parseDouble(confidence));
              analyseDao.addAnalyse(a);
              

              
              // receive file
              byte [] mybytearray  = new byte [FILE_SIZE];
              InputStream is = s.getInputStream(); 
              File root = new File("img");
              root.mkdir(); //this makes sure the folder exists
              File file = new File(root,imgname+".jpg");
              fos = new FileOutputStream(file, false);
              
              
                        
              //fos = new FileOutputStream(imagepath);
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
              System.out.println("Test result :"+result+"\n"+"Found in :"+time+"\n"+"confidence  :"+confidence);
              System.out.println("Device Location:Latitude "+lat+" & Longitude :"+lng);
              System.out.println("Adresse name :"+label);
              System.out.println("Device name :"+ device);
              
              List<AnalyseDevice> ad = analyseDao.getAnalyses();
              
              
              System.out.println(""+ad.get(0).getPlant() );
              System.out.println("Devices :"+ deviceDao.getCountDevice());
              System.out.println("Analyse :"+ analyseDao.getCountAnalyse());
              System.out.println("Connection:"+ connectDao.getCountConnect());
            }catch (Exception e) {
				// TODO: handle exception
			}
            
            try {
            	if (fos != null) fos.close();
                if (bos != null) bos.close();
                if (s != null) s.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
    }
}
