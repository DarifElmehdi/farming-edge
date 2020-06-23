package beans;

import java.sql.Timestamp;

public class Connect {
	private int iddevice;
	private Timestamp date;
	private String ipadress;
	private int port;
	
	public Connect(int iddevice,Timestamp date,String ipadress,int port) {
		this.setDate(date);
		this.setIddevice(iddevice);
		this.setPort(port);
		this.setIpadress(ipadress);
	}

	public int getIddevice() {
		return iddevice;
	}

	public void setIddevice(int iddevice) {
		this.iddevice = iddevice;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getIpadress() {
		return ipadress;
	}

	public void setIpadress(String ipadress) {
		this.ipadress = ipadress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
