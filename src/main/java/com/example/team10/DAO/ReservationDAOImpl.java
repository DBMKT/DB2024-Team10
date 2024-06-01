package main.java.com.example.team10.DAO;

import main.java.com.example.team10.DTO.AdministratorDTO;
import main.java.com.example.team10.DTO.ReservationDTO;
import main.java.com.example.team10.DTO.UserDTO;
import main.java.com.example.team10.util.JdbcUtil;
import main.java.com.example.team10.util.SessionManager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ReservationDAOImpl implements ReservationDAO {

    private Connection conn;

    public ReservationDAOImpl() {
        this.conn = JdbcUtil.getConnection();
        if (this.conn == null) {
            throw new RuntimeException("커넥션이 제대로 맺어지지 않았습니다.");
        }
    }


    // 예약 생성 함수
    @Override
    public void createReservation(ReservationDTO reserve) { 
    	//Search에서 정보 넘김-> Reserve에서 수합->createReservation으로 예약 생성->insertReservation에서 sql 처리
    	//예약 인스턴스 생성
    	ReservationDTO reservation=null;
    	
    	//1: reserved_id
    	reservation.setReservedId();//어디서?
    	
    	//2: room_id
    	reservation.setRoomId(reserve.getRoomId());//search
    	
    	//3: admin_id(어떻게 설정할 것인가?)
    	int admin_id=99999991+(int)reservation.getReservedId()%4;
    	reservation.setAdmin_id(admin_id);
    	
    	// 세션에 저장된 user 정보 불러오기
        UserDTO currentUser = SessionManager.getCurrentUser();
        if (currentUser == null) {
            System.out.println("로그인된 사용자가 없습니다. 예약을 생성할 수 없습니다.");
            return;
        }
    	//4: user_id
        reservation.setUser_id(currentUser.getId());
    	//5: user_name
        reservation.setUserName(currentUser.getName());
    	//6: reason
        reservation.setReason(reserve.getReason());//reserve
    	//7: people_num
        reservation.setPeopleNum(reserve.getPeopleNum());//reserve
    	//8: reserved_date(예약 날짜)
        reservation.setReservedDate(reserve.getReservedDate());//search
    	//9: reserved_period(예약 교시)
        reservation.setReservedPeriod(reserve.getReservedPeriod());//search
    	//10: created_date(예약 요청 시간)
        reservation.setCreatedDate(reserve.getCreatedDate());//reserve
        
        insertReservation(reservation);
        
        return;
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


    public List<ReservationDTO> getReservationsByUserId(long userId) {

        List<ReservationDTO> reservations = new ArrayList<>();

        String query = "SELECT * FROM db2024_Reservation WHERE user_id = ?";



        try (PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setLong(1, userId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                ReservationDTO reservation = new ReservationDTO();

                reservation.setRoomId(rs.getLong("room_id"));

                reservation.setUser_id(rs.getLong("user_id"));

                reservation.setReason(rs.getString("reason"));

                reservation.setPeopleNum(rs.getInt("people_num"));

                reservation.setReservedDate(rs.getDate("reserved_date"));

                reservation.setReservedPeriod(rs.getInt("reserved_period"));

                reservation.setCreatedDate(rs.getDate("created_date"));

                reservations.add(reservation);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }



        return reservations;
    }
}