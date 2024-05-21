package main.java.com.example.team10.DAO;

import main.java.com.example.team10.DTO.UserDTO;

public interface AdministratorDAO {
	String updateUserInfo(UserDTO user); // 자신의 담당 사용자의 예약 권한 조정
	// --> 예약 가능한 사용자인지 아닌지
	//String updateClassroomInfo(ClassroomDTO classroom); // 자신의 담당 강의실 정보 수정
}
