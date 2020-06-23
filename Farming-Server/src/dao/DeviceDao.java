package dao;

import java.util.List;

import beans.ConnectDevice;
import beans.Device;

public interface DeviceDao {
	public void addDevice(Device d);
	public void deleteDevice(int id);
	public Device getDevice(int id);
	public List<Device> getDevice();
	public int getDeviceId(String name);
	public int getCountDevice();
	public List<ConnectDevice> getConnectDevice();
}
