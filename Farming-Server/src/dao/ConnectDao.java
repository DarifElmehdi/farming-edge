package dao;

import java.util.List;

import beans.Connect;

public interface ConnectDao {
	
	public void addConnect(Connect c);
	public void deleteConnect(int id);
	public Connect getConnect(int id);
	public List<Connect> getConnect();
	public int getCountConnect();
}
