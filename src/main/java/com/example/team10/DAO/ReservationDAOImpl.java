package main.java.com.example.team10.DAO;

import main.java.com.example.team10.DTO.ReservationDTO;
import main.java.com.example.team10.DTO.UserDTO;
import main.java.com.example.team10.util.JdbcUtil;
import main.java.com.example.team10.util.SessionManager;

import java.sql.Connection;
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

    // 세션에 저장된 user 정보 불러오기
    UserDTO currentUser = SessionManager.getCurrentUser();

    // 예약 생성 함수 예시
    @Override
    public void createReservation(ReservationDTO reservation) {
        if (currentUser == null) {
            System.out.println("로그인된 사용자가 없습니다. 예약을 생성할 수 없습니다.");
            return;
        }

        String query = "INSERT INTO db2024_Reservation (room_id, user_id, reason, people_num, date, period, create_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setLong(1, reservation.getRoomId());
            statement.setLong(2, currentUser.getId());
            statement.setString(3, reservation.getReason());
            statement.setInt(4, reservation.getPeopleNum());
            statement.setDate(5, new java.sql.Date(reservation.getReserve_date().getTime()));
            statement.setInt(6, reservation.getPeriod());
            statement.setDate(7, new java.sql.Date(reservation.getCreatedDate().getTime()));
            statement.executeUpdate();
            System.out.println("예약이 성공적으로 완료되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}