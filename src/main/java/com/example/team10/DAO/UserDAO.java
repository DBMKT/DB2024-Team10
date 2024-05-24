package main.java.com.example.team10.DAO;

import java.util.List;

import main.java.com.example.team10.DTO.UserDTO;

public interface UserDAO {
	
    String signup(UserDTO newUser); // 회원가입
	
    UserDTO login(long id, String password); // 로그인
    
    void logout();
    
	List<UserDTO> getUserListByAdminId(long adminId);

}