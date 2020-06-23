package beans;

import java.sql.Timestamp;

public class AnalyseDevice {
	private int id;
	private String nomdevice;
	private Timestamp dateanalyse;
	private String resultat;
	private String image;
	private String plant;
	private int temps;
	private Double latitude;
	private Double longitude;
	private Double confidence;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNomdevice() {
		return nomdevice;
	}
	public void setNomdevice(String nomdevice) {
		this.nomdevice = nomdevice;
	}
	public Timestamp getDateanalyse() {
		return dateanalyse;
	}
	public void setDateanalyse(Timestamp dateanalyse) {
		this.dateanalyse = dateanalyse;
	}
	public String getResultat() {
		return resultat;
	}
	public void setResultat(String resultat) {
		this.resultat = resultat;
	}
	public String getImage() {
		return "C:\\Users\\Darif\\eclipse-workspace\\Farming-Server\\img\\"+image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getPlant() {
		return plant;
	}
	public void setPlant(String plant) {
		this.plant = plant;
	}
	public int getTemps() {
		return temps;
	}
	public void setTemps(int temps) {
		this.temps = temps;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getConfidence() {
		return confidence;
	}
	public void setConfidence(Double confidence) {
		this.confidence = confidence;
	}
}
