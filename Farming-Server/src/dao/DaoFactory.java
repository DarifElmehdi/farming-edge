package dao;

import java.sql.*;

public class DaoFactory {
	private String url;
    private String username;
    private String password;

    DaoFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DaoFactory getInstance() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {

        }
        DaoFactory instance = new DaoFactory("jdbc:mysql://localhost/farming","darif","darif");
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
    
    public UserDao getUserDao() {
    	return new UserDaoImplementation(this);
    }
    
	public AnalyseDao getAnalyseDao() {
		return new AnalyseDaoImplementation(this);
	}
	
	public DeviceDao getDeviceDao() {
		return new DeviceDaoImplementation(this);
	}
	
	public ConnectDao getConnectDao() {
		return new ConnectDaoImplementation(this);
	}

}
