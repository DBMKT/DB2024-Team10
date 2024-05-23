package main.java.com.example.team10.DAO;

import main.java.com.example.team10.DTO.AdministratorDTO;
import main.java.com.example.team10.DTO.UserDTO;

public interface AdministratorDAO {
	AdministratorDTO adminLogin(long id, String password); // 관리자 로그인
	void logout(); // 관리자 로그아웃
	void updateUserInfo(UserDTO user); // 자신의 담당 사용자의 예약 권한 조정
	// --> 예약 가능한 사용자인지 아닌지
	//String updateClassroomInfo(ClassroomDTO classroom); // 자신의 담당 강의실 정보 수정
	// String updateReservationInfo(ReservationDTO reservation); // 현재 예약 내역 수정
}
