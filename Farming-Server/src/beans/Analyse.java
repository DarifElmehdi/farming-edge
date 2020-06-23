package beans;

import java.sql.Timestamp;

public class Analyse {
	
	private int idanalyse;
	private int iddevice;
	private Timestamp dateanalyse;
	private String resultat;
	private String image;
	private String plant;
	private int temps;
	private Double latitude;
	private Double longitude;
	private Double confidence;
	
	public Analyse(int idanalyse,int iddevice,Timestamp dateanalyse,String resultat,String image,
			String plant,int temps,Double latitude,Double longitude,Double confidence) {
		this.confidence=confidence;
		this.dateanalyse=dateanalyse;
		this.idanalyse=idanalyse;
		this.iddevice=iddevice;
		this.image=image;
		this.latitude=latitude;
		this.longitude=longitude;
		this.resultat=resultat;
		this.temps=temps;
		this.plant=plant;
	}
	
	public int getIdanalyse() {
		return idanalyse;
	}
	public void setIdanalyse(int idanalyse) {
		this.idanalyse = idanalyse;
	}
	public int getIddevice() {
		return iddevice;
	}
	public void setIddevice(int iddevice) {
		this.iddevice = iddevice;
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
		return image;
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
