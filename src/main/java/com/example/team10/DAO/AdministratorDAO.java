package main.java.com.example.team10.DAO;

import java.util.Date;
import java.util.List;

import main.java.com.example.team10.DTO.AdministratorDTO;
import main.java.com.example.team10.DTO.ReservationDTO;
import main.java.com.example.team10.DTO.UserDTO;

public interface AdministratorDAO {
	AdministratorDTO adminLogin(long id, String password); // 관리자 로그인
	
	void logout(); // 관리자 로그아웃
	
	void updateUserInfo(UserDTO user); // 자신의 담당 사용자의 예약 권한 조정
	
	void deleteReservationInfo(List<ReservationDTO> reservations); // 현재 예약 내역 삭제
	
    List<ReservationDTO> getUpcomingReservationList();	// 현재 시각을 기준으로 정렬된 가장 가까운 예약 내역들 조회
    
    List<ReservationDTO> searchReservationList(String keyword, List<String> selectedFields, int period, Date selectedDate); // 예약 내역 검색 기능
    
    void deletePastReservations(); //이미 진행된 예약 내역 삭제
}
