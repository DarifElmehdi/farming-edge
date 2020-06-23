package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import beans.Connect;

public class ConnectDaoImplementation implements ConnectDao{
	private DaoFactory daoFactory;
	
	public ConnectDaoImplementation(DaoFactory daoFactory) {
		this.setDaoFactory(daoFactory);
	}

	@Override
	public void addConnect(Connect c) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement("INSERT INTO connection(iddevice,idconnection, date, ipadress, port) VALUES (?,NULL,current_timestamp(),?,?)");
			preparedStatement.setInt(1,c.getIddevice());
			preparedStatement.setString(2,c.getIpadress());
			preparedStatement.setInt(3,c.getPort());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void deleteConnect(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement("DELETE FROM connection WHERE iddevice = ?");
			preparedStatement.setInt(1,id);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}

	@Override
	public Connect getConnect(int id) throws ParseException{
		Connect c = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement("SELECT iddevice, date, ipadress, port FROM connection WHERE 1 iddevice= ?");
			preparedStatement.setInt(1,id);
			ResultSet result=preparedStatement.executeQuery();
            if(result.next()) {
            	c=new Connect(result.getInt("iddevice"),result.getTimestamp("date"),result.getString("ipadress"),result.getInt("port"));	
            }
		} catch (Exception e) {
			// TODO: handle exception
		}
		return c;
	}

	@Override
	public List<Connect> getConnect() {
		List<Connect> cs = new  LinkedList<Connect>();
		Connection connection = null;
        PreparedStatement preparedStatement = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement("SELECT iddevice, nomdevice FROM devices");
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) {
				Connect c = new Connect(result.getInt("iddevice"),result.getTimestamp("date"),result.getString("ipadress"),result.getInt("port"));
				cs.add(c);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return cs;
	}
	
	@Override
	public int getCountConnect() {
		int c = 0;
		Connection connection = null;
        PreparedStatement preparedStatement = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement("select COUNT(idconnection) from connection");
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) {
				c=result.getInt("count(idconnection)");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return c;
	}
	
	public DaoFactory getDaoFactory() {
		return daoFactory;
	}

	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	

}
