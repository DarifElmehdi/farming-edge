package dao;

import java.util.LinkedList;
import java.util.List;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import java.sql.*;

import beans.Analyse;
import beans.AnalyseDevice;

public class AnalyseDaoImplementation implements AnalyseDao{
	
	private DaoFactory daoFactory;
	
	public AnalyseDaoImplementation(DaoFactory daoFactory) {
		this.setDaoFactory(daoFactory);
	}
	
	@Override
	public void addAnalyse(Analyse a) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement("INSERT INTO analyse (idanalyse, iddevice, dateanalyse, resultat, image, latitude, longitude, confidence, temps, plant)"
					+ " VALUES (NULL,?,current_timestamp(),?,?,?,?,?,?,?)");
			preparedStatement.setInt(1,a.getIddevice());
			preparedStatement.setString(2,a.getResultat());
			preparedStatement.setString(3, a.getImage());
			preparedStatement.setDouble(4,a.getLatitude());
			preparedStatement.setDouble(5, a.getLongitude());
			preparedStatement.setDouble(6,a.getConfidence());
			preparedStatement.setInt(7,a.getTemps());
			preparedStatement.setString(8, a.getPlant());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void deleteAnalyse(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement("DELETE FROM analyse WHERE idanalyse = ?");
			preparedStatement.setInt(1,id);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public Analyse getAnalyse(int id) throws ParseException{
		Analyse a = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement("SELECT idanalyse, iddevice, dateanalyse, resultat, image, latitude, longitude, confidence, temps, plant "
					+ "FROM analyse WHERE idanalyse =?");
			preparedStatement.setInt(1,id);
			ResultSet result=preparedStatement.executeQuery();
            if(result.next()) {
            	a=new Analyse(result.getInt("idanalyse"),result.getInt("iddevice"),result.getTimestamp("dateanalyse"), result.getString("resultat"),
            		result.getString("image"), result.getString("plant"), result.getInt("temps"), result.getDouble("latitude"),result.getDouble("longitude"),result.getDouble("confidence"));
            }
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return a;
	}

	@Override
	public List<Analyse> getAnalyse() throws ParseException{
		List<Analyse> as = new  LinkedList<Analyse>();
		Connection connection = null;
        PreparedStatement preparedStatement = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement("SELECT idanalyse, iddevice, dateanalyse, resultat, image, latitude, longitude, confidence, temps, plant FROM analyse");
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) {
				Analyse a=new Analyse(result.getInt("idanalyse"),result.getInt("iddevice"),result.getTimestamp("dateanalyse"), result.getString("resultat"),
	            		result.getString("image"), result.getString("plant"), result.getInt("temps"), result.getDouble("latitude"),result.getDouble("longitude"),result.getDouble("confidence"));
	            as.add(a);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return as;
	}
	public DaoFactory getDaoFactory() {
		return daoFactory;
	}
	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public int getCountAnalyse() {
		int c = 0;
		Connection connection = null;
        PreparedStatement preparedStatement = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement("select COUNT(idanalyse) from analyse");
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) {
				c=result.getInt("count(idanalyse)");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return c;
	}

	@Override
	public List<AnalyseDevice> getAnalyses() {
		List<AnalyseDevice> ad = new  LinkedList<AnalyseDevice>();
		Connection connection = null;
        PreparedStatement preparedStatement = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM analyse A , devices D where D.iddevice = A.iddevice");
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) {
				AnalyseDevice d = new AnalyseDevice();
				d.setResultat(result.getString("resultat"));
				d.setId(result.getInt("idanalyse"));
				d.setDateanalyse(result.getTimestamp("dateanalyse"));
				d.setImage(result.getString("image"));
				d.setLatitude(result.getDouble("latitude"));
				d.setLongitude(result.getDouble("longitude"));
				d.setConfidence(result.getDouble("confidence"));
				d.setPlant(result.getString("plant"));
				d.setNomdevice(result.getString("nomdevice"));
				d.setId(result.getInt("idanalyse"));
				d.setTemps(result.getInt("temps"));
				ad.add(d);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ad;		
	}

}
