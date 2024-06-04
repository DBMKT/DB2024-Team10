package main.java.com.example.team10;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import main.java.com.example.team10.DAO.AdministratorDAOImpl;
import main.java.com.example.team10.DTO.AdministratorDTO;
import main.java.com.example.team10.DTO.UserDTO;
import main.java.com.example.team10.GUI.Home;
import main.java.com.example.team10.util.SessionManager;

public class App {

	public static void main(String[] args) {
//		// TODO Auto-generated method stub
		
		  //UserDAO userDAO = new UserDAOImpl(); 
		  AdministratorDAOImpl adminDAO = new AdministratorDAOImpl();
//		
//		// 사용자 회원가입 test 코드
//		//     public UserDTO(long id, String password, String major, String email, String name, String phoneNum) {
//		UserDTO newUser = new UserDTO(2171111, "password123", "컴퓨터공학", "test@ewhain.net", "테스트0522", "010-0000-0000");
//        System.out.println(userDAO.signup(newUser));
//        
//        // 사용자 로그인 test 코드
		/*
		 * UserDTO loginUser = userDAO.login(2171024 , "tlstnwjd"); if(loginUser == null)
		 * { System.out.println("로그인 실패: 잘못된 ID 혹은 비밀번호입니다.\n"); } else {
		 * System.out.println("로그인 성공: "+loginUser.getId()); } // Check if user is
		 * logged in if (SessionManager.isUserLoggedIn()) {
		 * System.out.println("세션 유지 중: " + SessionManager.getCurrentUser().getName());
		 * } else { System.out.println("세션 유지 실패"); }
		 */

//      // Test user logout
//      userDAO.logout();
//      if (!SessionManager.isUserLoggedIn()) {
//          System.out.println("사용자 로그아웃 성공");
//      } else {
//          System.out.println("사용자 로그아웃 실패");
//      }
		
        // 관리자 로그인 test 코드
		
		/*
		 * AdministratorDTO admin = adminDAO.adminLogin(99999992, "rhksflwk2"); if
		 * (admin != null) { System.out.println("관리자 로그인 성공: " + admin.getContact()); }
		 * else { System.out.println("관리자 로그인 실패: 잘못된 ID 또는 비밀번호"); }
		 * 
		 * 
		 * // Check if admin is logged in
		 * 
		 * if (SessionManager.isAdminLoggedIn()) { System.out.println("관리자 세션 유지 중: " +
		 * ((AdministratorDTO) SessionManager.getCurrentAdmin()).getContact()); } else {
		 * System.out.println("관리자 세션 유지 실패"); }
		 * 
		 * // 사용자 체크 if (SessionManager.isUserLoggedIn()){
		 * System.out.println("이용자 세션 유지 중: " + ((UserDTO)
		 * SessionManager.getCurrentUser()).getPhoneNum());} else {
		 * System.out.println("이용자 세션 유지 실패"); }
		 */

       // 관리자 --> 사용자 권한 조정 (블락)
		/*
		 * if (admin != null) { // Update reservation permission
		 * if(loginUser.isCanReserve()) { loginUser.setCanReserve(false); } else {
		 * loginUser.setCanReserve(true); } adminDAO.updateUserInfo(loginUser); }
		 */

		  // 관리자 --> 예약 내역 조정
			/*
			 * if(admin != null) { admin.deleteReservationInfo(); }
			 */
		/*
		 * if (SessionManager.isAdminLoggedIn()) { System.out.println("관리자 세션 유지 중: " +
		 * ((AdministratorDTO) SessionManager.getCurrentAdmin()).getContact()); } else {
		 * System.out.println("관리자 세션 유지 실패"); }
		 */
//        
//        
//        adminDAO.logout();
//        if (!SessionManager.isAdminLoggedIn()) {
//            System.out.println("관리자 로그아웃 성공");
//        } else {
//            System.out.println("관리자 로그아웃 실패");
//        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	new Home();
            }
        });
    }
}

