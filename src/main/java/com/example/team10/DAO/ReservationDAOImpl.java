package main.java.com.example.team10.DAO;

import main.java.com.example.team10.DTO.ReservationDTO;
import main.java.com.example.team10.DTO.UserDTO;
import main.java.com.example.team10.util.JdbcUtil;
import main.java.com.example.team10.util.SessionManager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ReservationDAOImpl implements ReservationDAO {

    private Connection conn;

    public ReservationDAOImpl() {
        this.conn = JdbcUtil.getConnection();
        if (this.conn == null) {
            throw new RuntimeException("커넥션이 제대로 맺어지지 않았습니다.");
        }
    }

//    #1 예약 페이지에서 정보를 선택하면->set함수
//    다음 예약 확정페이지에서 정보를 #2get함수로 불러오면 되는건가요?!?
//    		 ReservationDAO에서욥??
//    		보통 #1 => 부분을 ReservationDTO의 생성자부분(받는 파라미터) 로 만들어서 인스턴스 생성해주고, 그 다음에 set을 이용해서 정의를 해주고 => 
//    		만들어진 인스턴스에서 #2처리하면 될걸요??

    // 예약하기 함수
    @Override
    public ReservationDTO createReservation() {
    	//예약 인스턴스 생성
    	ReservationDTO reservation=null;
    	
        // 세션에 저장된 user 정보 불러오기
        UserDTO currentUser = SessionManager.getCurrentUser();
        if (currentUser == null) {
            System.out.println("로그인된 사용자가 없습니다. 예약을 생성할 수 없습니다.");
            return reservation;
        }
        
        
        
    }
    
    public void insertReservation(ReservationDTO reservation) {//sql에 예약 내역 insert하는 함수(Reserve.java 화면에서 예약 버튼을 누를 시 실행)
    	String query = "INSERT INTO db2024_Reservation (reserved_id, room_id, admin_id, user_id, user_name, reason, people_num, reserved_date, reserved_period, created_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
        	//1: reserved_id
            statement.setLong(1, reservation.getReservedId()); 
            //2: room_id
            statement.setLong(2, reservation.getRoomId()); //Reserve.java에서 가져옴
            //3: admin_id
            statement.setLong(3, reservation.getAdminId()); //Reserve.java에서 가져옴
            //4: user_id
            statement.setInt(4, reservation.getPeopleNum()); 
            //5: user_name
            statement.setString(5, reservation.getUserName());
            //6: reason
            statement.setString(6, reservation.getReason());
            //7: people_num
            statement.setInt(7, reservation.getPeopleNum());            
            //8: reserved_date
            statement.setDate(8,(Date) reservation.getReservedDate());
            //9: reserved_period
            statement.setInt(9,reservation.getReservedPeriod());
            //10: created_date
            statement.setDate(10,(Date)reservation.getCreatedDate());

            statement.executeUpdate();
            System.out.println("예약이 성공적으로 완료되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
