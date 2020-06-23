package beans;

public class User {
	private int iduser;
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private String phonenumber;
	private String image;
	private String address;
	
	public User(String firstName,String lastName,String email,String password,String phoneNumber,String image,String address) {
		this.firstname=firstName;
		this.lastname = lastName;
		this.address = address;
		this.email=email;
		this.image=image;
		this.password=password;
		this.phonenumber=phoneNumber;
	}
		
	public int getIdUser() {
		return iduser;
	}
	public String getAddress() {
		return address;
	}
	public String getEmail() {
		return email;
	}
	public String getFirstName() {
		return firstname;
	}
	public String getImage() {
		return image;
	}
	public String getLastName() {
		return lastname;
	}
	public String getPassword() {
		return password;
	}
	public String getPhoneNumber() {
		return phonenumber;
	}
	public void setIdUser(int idUser) {
		this.iduser = idUser;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setFirstName(String firstName) {
		this.firstname = firstName;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phonenumber = phoneNumber;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public void setLastName(String lastName) {
		this.lastname = lastName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
