package main.java.com.example.team10.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.com.example.team10.DTO.AdministratorDTO;
import main.java.com.example.team10.DTO.UserDTO;
import main.java.com.example.team10.util.JdbcUtil;

public class AdministratorDAOImpl implements AdministratorDAO {
	private Connection conn;

	public AdministratorDAOImpl() {
		this.conn = JdbcUtil.getConnection();
		if(this.conn == null) {
			throw new RuntimeException("커넥션이 제대로 맺어지지 않았습니다.");
		}
	}
	// 관리자 로그인 메서드: null값 반환 시에는, ID/PW 잘못됨
	@Override
	public AdministratorDTO adminLogin(long id, String password){
		AdministratorDTO loginAdmin = null;
		PreparedStatement pStmt = null;
		ResultSet res = null;
		try {
			conn.setAutoCommit(false); // 트랜잭션 시작

			pStmt = conn.prepareStatement("SELECT * FROM db2024_administrator WHERE id = ? AND password = ?");
			pStmt.setLong(1, id);
			pStmt.setString(2, password);
			res = pStmt.executeQuery();
			if(res.next()) { // 로그인 성공 시
				loginAdmin = new AdministratorDTO();
                loginAdmin.setId(res.getLong("id"));
                loginAdmin.setPassword(res.getString("password"));
                loginAdmin.setContact(res.getString("contact"));
                // 세션 문제 --> 세션에 로그인한 관리자 정보 저장
			}
			conn.commit(); // 트랜잭션 커밋
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // 예외 발생 시 롤백
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            JdbcUtil.close(res);
            JdbcUtil.close(pStmt);
        }
		return loginAdmin;
	}
	
	@Override
	public void updateUserInfo(UserDTO user) {
		// 관리자 자신의 담당 사용자의 예약 권한 조정
		PreparedStatement pStmt = null;
		
		try {
			conn.setAutoCommit(false); // 트랜잭션 시작
			
			String sql = "UPDATE db2024_User SET canReserve = ? WHERE id = ? AND admin_id = ?";
			pStmt = conn.prepareStatement(sql);
			pStmt.setBoolean(1, user.isCanReserve());
			pStmt.setLong(2, user.getId());
			pStmt.setLong(3, user.getAdmin_id());
			
			int rowsAffected = pStmt.executeUpdate();
			if(rowsAffected > 0) {
				conn.commit(); // 업데이트 성공 --> 트랜잭션 커밋
				System.out.println("예약권한이 성공적으로 업데이트되었습니다.");
			}
			else {
				conn.rollback(); // 실패 시 롤백
				System.out.println("예약 권한 업데이트에 실패하였습니다.");
			}
		} catch(SQLException e) {
			if(conn != null) {
				try {
					conn.rollback();
				} catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
			e.printStackTrace();
			System.out.println("예약 권한 업데이트 중 알 수 없는 오류가 발생하였습니다.");
		} finally {
			JdbcUtil.close(pStmt);
		}
	}

}
