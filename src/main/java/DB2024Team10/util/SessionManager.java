package main.java.DB2024Team10.util;

import main.java.DB2024Team10.DTO.*;

// 세션 관리 --> Authenticate(로그인된 상태)
public class SessionManager {
	private static AdministratorDTO currentAdmin;
	private static UserDTO currentUser;
	
	public static void adminAuthenticate(AdministratorDTO admin) {
		setCurrentAdmin(admin);
	}
	
	public static void adminLogout() {
		setCurrentAdmin(null);
	}
	
	public static AdministratorDTO getCurrentAdmin() {
		return currentAdmin;
	}

	public static void setCurrentAdmin(AdministratorDTO currentAdmin) {
		SessionManager.currentAdmin = currentAdmin;
	}
	
	public static boolean isAdminLoggedIn() {
		return currentAdmin != null;
	}
	
	public static void userAuthenticate(UserDTO user) {
		setCurrentUser(user); // 세션 권한 O
	}
	
	public static void userLogout() {
		setCurrentUser(null);
	}

	public static UserDTO getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(UserDTO currentUser) {
		SessionManager.currentUser = currentUser;
	}
	
	public static boolean isUserLoggedIn() {
		return currentUser != null;
	}
}
