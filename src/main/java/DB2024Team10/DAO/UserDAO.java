package main.java.DB2024Team10.DAO;

import java.util.List;

import main.java.DB2024Team10.DTO.UserDTO;

public interface UserDAO {
	
    String signup(UserDTO newUser); // 회원가입
	
    UserDTO login(long id, String password); // 로그인
    
    int resetPassword(long id, String originPassword, String newPassword); // 비밀번호 변경
    // 성공 시 1, 실패시 0 반환
    
    void logout();
    
    List<UserDTO> getUserListByAdminId(long adminId);

}