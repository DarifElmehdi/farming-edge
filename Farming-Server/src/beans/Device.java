package beans;

public class Device {
	private int iddevice;
	private String nomdevice;
	
	public Device(int iddevice,String nomdevice) {
		this.setIddevice(iddevice);
		this.setNomdevice(nomdevice);
	}

	public int getIddevice() {
		return iddevice;
	}

	public void setIddevice(int iddevice) {
		this.iddevice = iddevice;
	}

	public String getNomdevice() {
		return nomdevice;
	}

	public void setNomdevice(String nomdevice) {
		this.nomdevice = nomdevice;
	}
}
