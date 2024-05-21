package main.java.com.example.team10.DTO;

public class UserDTO {
	private long id; 			// 회원 ID(학번)
	private String password;	// 회원 비밀번호
	private boolean canReserve; // 예약 권한(예약 가능한 사용자/예약 불가한 사용자)
	private String email;		// 이메일 주소
	private String phoneNum;	// 전화번호
	private String major;		// 학과
	private String name;		// 이름
	private long admin_id;		// 회원을 관리하는 관리자 id. 
								// 회원:관리자=1:N 관계
	
	/* User의 각 필드값에 대한 getter, setter 정의 */
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isCanReserve() {
		return canReserve;
	}
	public void setCanReserve(boolean canReserve) {
		this.canReserve = canReserve;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	public long getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(long admin_id) {
		this.admin_id = admin_id;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
