package main.java.DB2024Team10.DTO;

public class AdministratorDTO {
	private long id;			// 관리자 id
	private String password;	// 관리자 password
	private String contact;		// 관리자 연락처
	
	public long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
}
