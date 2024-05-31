package main.java.com.example.team10.DAO;

import main.java.com.example.team10.DTO.ReservationDTO;
import main.java.com.example.team10.DTO.UserDTO;
import main.java.com.example.team10.util.JdbcUtil;
import main.java.com.example.team10.util.SessionManager;

import java.sql.Connection;
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

    // 예약 생성 함수 예시
    @Override
    public void createReservation(ReservationDTO reservation) {
        // 세션에 저장된 user 정보 불러오기
        UserDTO currentUser = SessionManager.getCurrentUser();
        if (currentUser == null) {
            System.out.println("로그인된 사용자가 없습니다. 예약을 생성할 수 없습니다.");
            return;
        }



        String query = "INSERT INTO db2024_Reservation (room_id, user_id, reason, people_num, reserved_date, reserved_period, created_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setLong(1, reservation.getRoomId());
            statement.setLong(2, currentUser.getId());
            statement.setString(3, reservation.getReason());
            statement.setInt(4, reservation.getPeopleNum());
            statement.setDate(5, new java.sql.Date(reservation.getReservedDate().getTime()));
            statement.setInt(6, reservation.getReservedPeriod());
            statement.setDate(7, new java.sql.Date(reservation.getCreatedDate().getTime()));
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