package beans;

import java.sql.Timestamp;

public class ConnectDevice {
	private String nomdevice;
	private Timestamp date ;
	private String ipaddress;
	private int port;
	public String getNomdevice() {
		return nomdevice;
	}
	public void setNomdevice(String nomdevice) {
		this.nomdevice = nomdevice;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
}
