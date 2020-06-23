package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import beans.ConnectDevice;
import beans.Device;

public class DeviceDaoImplementation implements DeviceDao{
	private DaoFactory daoFactory;
	
	public DeviceDaoImplementation(DaoFactory daoFactory) {
		this.setDaoFactory(daoFactory);
	}
		
	@Override
	public void addDevice(Device d) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement("INSERT INTO devices (iddevice, nomdevice) VALUES (NULL,?)");
			preparedStatement.setString(1,d.getNomdevice());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Override
	public void deleteDevice(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement("DELETE FROM devices WHERE iddevice = ?");
			preparedStatement.setInt(1,id);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Override
	public Device getDevice(int id) throws ParseException{
		Device d = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement("SELECT iddevice, nomdevice FROM devices WHERE iddevice= ?");
			preparedStatement.setInt(1,id);
			ResultSet result=preparedStatement.executeQuery();
            if(result.next()) {
            	d=new Device(result.getInt("iddevice"),result.getString("nomdevice"));	
            }
		} catch (Exception e) {
			// TODO: handle exception
		}
		return d;
	}

	@Override
	public List<Device> getDevice() throws ParseException{
		List<Device> ds = new  LinkedList<Device>();
		Connection connection = null;
        PreparedStatement preparedStatement = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement("SELECT iddevice, nomdevice FROM devices");
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) {
				Device d =new Device(result.getInt("iddevice"),result.getString("nomdevice"));
				ds.add(d);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ds;
	}
	public DaoFactory getDaoFactory() {
		return daoFactory;
	}
	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public int getDeviceId(String name) {
		int id = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement("SELECT iddevice FROM devices WHERE nomdevice= ?");
			preparedStatement.setString(1,name);
			ResultSet result=preparedStatement.executeQuery();
            if(result.next()) {
            	id=result.getInt("iddevice");	
            }
		} catch (Exception e) {
			// TODO: handle exception
		}
		return id;
	}

	@Override
	public int getCountDevice() {
		int c = 0;
		Connection connection = null;
        PreparedStatement preparedStatement = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement("select COUNT(iddevice) from devices");
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) {
				c=result.getInt("count(iddevice)");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return c;
	}

	@Override
	public List<ConnectDevice> getConnectDevice() {
		List<ConnectDevice> cd = new  LinkedList<ConnectDevice>();
		Connection connection = null;
        PreparedStatement preparedStatement = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM connection C ,devices D WHERE c.iddevice = d.iddevice ");
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) {
				ConnectDevice d = new ConnectDevice();
				d.setDate(result.getTimestamp("date"));
				d.setIpaddress(result.getString("ipadress"));
				d.setPort(result.getInt("port"));
				d.setNomdevice(result.getString("nomdevice"));
				cd.add(d);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return cd;
	}
	
}
