package server;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import beans.Device;

import java.io.IOException;
import java.sql.Connection;


import dao.DaoFactory;
import dao.DeviceDao;
class Main
{
public static void main(String args[]) throws IOException
{
	Server server = new Server();
	//server.RunServer();
	DaoFactory daoFactory = DaoFactory.getInstance();
	DeviceDao deviceDao;
try
{
Connection connection = null;
connection = daoFactory.getConnection();
deviceDao= daoFactory.getDeviceDao();

List<Device> d = deviceDao.getDevice();

for(int i=0;i<d.size();i++) {
	System.out.println(d.get(i).getIddevice() + d.get(i).getNomdevice());
	System.out.println("Count devices :"+ deviceDao.getCountDevice());
	}
System.out.print("Database is connected !");
connection.close();
}
catch(Exception e)
{
System.out.print("Do not connect to DB - Error:"+e);
}
}
}