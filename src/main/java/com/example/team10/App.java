package main.java.com.example.team10;
import main.java.com.example.team10.DAO.*;
import main.java.com.example.team10.DTO.*;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * UserDAO userDAO = new UserDAOImpl(); AdministratorDAO adminDAO = new
		 * AdministratorDAOImpl();
		 */
		
		// 사용자 회원가입 test 코드
		/*
		 * UserDTO newUser = new UserDTO(2171111, "password123", "컴퓨터공학",
		 * "test@ewhain.net", "테스트0522", "010-0000-0000");
		 * System.out.println(userDAO.signup(newUser));
		 * 
		 * // 사용자 로그인 test 코드 UserDTO loginUser = userDAO.login(2171111, "password123");
		 * if(loginUser == null) { System.out.println("로그인 실패: 잘못된 ID 혹은 비밀번호입니다.\n"); }
		 * else { System.out.println("로그인 성공: "+loginUser.getId()); }
		 * 
		 * // 관리자 로그인 test 코드 AdministratorDTO admin = adminDAO.adminLogin(99999991,
		 * "rhksflwk1"); if (admin != null) { System.out.println("관리자 로그인 성공: " +
		 * admin.getContact()); } else {
		 * System.out.println("관리자 로그인 실패: 잘못된 ID 또는 비밀번호"); }
		 * 
		 * // 관리자 --> 사용자 권한 조정 if (admin != null) { loginUser.setCanReserve(false); //
		 * Update reservation permission adminDAO.updateUserInfo(loginUser); }
		 */
	}
	
	

}
