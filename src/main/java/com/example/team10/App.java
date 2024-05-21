package main.java.com.example.team10;
import main.java.com.example.team10.DAO.*;
import main.java.com.example.team10.DTO.*;
import main.java.com.example.team10.util.SessionManager;

public class App {

	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		UserDAO userDAO = new UserDAOImpl();
//		AdministratorDAO adminDAO = new AdministratorDAOImpl();
//		
//		// 사용자 회원가입 test 코드
//		//     public UserDTO(long id, String password, String major, String email, String name, String phoneNum) {
//		UserDTO newUser = new UserDTO(2171111, "password123", "컴퓨터공학", "test@ewhain.net", "테스트0522", "010-0000-0000");
//        System.out.println(userDAO.signup(newUser));
//        
//        // 사용자 로그인 test 코드
//        UserDTO loginUser = userDAO.login(2171111, "password123");
//        if(loginUser == null) {
//        	System.out.println("로그인 실패: 잘못된 ID 혹은 비밀번호입니다.\n");
//        }
//        else {
//        	System.out.println("로그인 성공: "+loginUser.getId());
//        }
//        // Check if user is logged in
//        if (SessionManager.isUserLoggedIn()) {
//            System.out.println("세션 유지 중: " + SessionManager.getCurrentUser().getName());
//        } else {
//            System.out.println("세션 유지 실패");
//        }
//        // 관리자 로그인 test 코드
//        AdministratorDTO admin = adminDAO.adminLogin(99999991, "rhksflwk1");
//        if (admin != null) {
//            System.out.println("관리자 로그인 성공: " + admin.getContact());
//        } else {
//            System.out.println("관리자 로그인 실패: 잘못된 ID 또는 비밀번호");
//        }
//        
//        // Check if admin is logged in
//        if (SessionManager.isAdminLoggedIn()) {
//            System.out.println("관리자 세션 유지 중: " + ((AdministratorDTO) SessionManager.getCurrentAdmin()).getContact());
//        } else {
//            System.out.println("관리자 세션 유지 실패");
//        }
//
//        // 관리자 --> 사용자 권한 조정 
//        if (admin != null) {
//            loginUser.setCanReserve(false); // Update reservation permission
//            adminDAO.updateUserInfo(loginUser);
//        }
//        
//        // Test user logout
//        userDAO.logout();
//        if (!SessionManager.isUserLoggedIn()) {
//            System.out.println("사용자 로그아웃 성공");
//        } else {
//            System.out.println("사용자 로그아웃 실패");
//        }
//        
//        adminDAO.logout();
//        if (!SessionManager.isAdminLoggedIn()) {
//            System.out.println("관리자 로그아웃 성공");
//        } else {
//            System.out.println("관리자 로그아웃 실패");
//        }
    
	}
	
	

}
