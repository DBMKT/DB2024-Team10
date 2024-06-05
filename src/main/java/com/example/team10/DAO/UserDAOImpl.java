package main.java.com.example.team10.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import main.java.com.example.team10.DTO.UserDTO;
import main.java.com.example.team10.util.JdbcUtil;
import main.java.com.example.team10.util.SessionManager;

public class UserDAOImpl implements UserDAO {
	private Connection conn;
    public UserDAOImpl() {
        this.conn = JdbcUtil.getConnection();
    }
    
    private void checkAndReconnect() throws SQLException {
        if (conn == null || conn.isClosed()) {
            this.conn = JdbcUtil.getConnection();
        }
    }

	// 회원가입
    @Override
	public String signup(UserDTO newUser) {
		
		//UserDTO newUser = null;
		PreparedStatement pStmt = null;
		ResultSet res = null;

		try {
			checkAndReconnect();
			conn.setAutoCommit(false);	// 자동 커밋 비활성화
			
			// 중복 회원인지 먼저 확인해주는 작업 필요
			pStmt = conn.prepareStatement("SELECT * from db2024_user WHERE id = ?");
			pStmt.setLong(1, newUser.getId());
			res = pStmt.executeQuery();
			
			if(res.next()) {
				return "이미 가입한 사용자입니다. 다른 id를 사용해주세요.";
			}
			JdbcUtil.close(res);
			JdbcUtil.close(pStmt);
			
			
			long randomAdmin = getRandomNumberInRange(99999991L, 99999994L);

			// (id, password, admin_id, email, name, major, phone_num) 
			String sql = "INSERT INTO db2024_user(id, password, major, email, name, phone_num, canReserve, admin_id) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			pStmt = conn.prepareStatement(sql);
			pStmt.setLong(1, newUser.getId());
			pStmt.setString(2, newUser.getPassword());
			pStmt.setString(3, newUser.getMajor());
			pStmt.setString(4, newUser.getEmail());
			pStmt.setString(5, newUser.getName());
			pStmt.setString(6, newUser.getPhoneNum());
			pStmt.setBoolean(7,newUser.isCanReserve());
			pStmt.setLong(8, randomAdmin); // 사용자를 관리할 관리자 랜덤 지정
			
			int rowsAffected = pStmt.executeUpdate();
			if(rowsAffected > 0) {
				conn.commit();
				String welcomeName = newUser.getName();
				return welcomeName+"님, 회원가입에 성공하였습니다.";
			}
			else {
				conn.rollback();
				return "회원가입에 실패하였습니다.";
			}
		}
		catch(SQLIntegrityConstraintViolationException e) {
			return "이미 가입한 사용자입니다. 다른 ID를 사용해주세요.";
		}
		catch(SQLException e) {
			e.printStackTrace();
			return "회원가입 중 알 수 없는 오류가 발생하였습니다." + e.getMessage();
		}
		finally {
			JdbcUtil.close(res);
			JdbcUtil.close(pStmt);
		}
	}
	
    private long getRandomNumberInRange(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max + 1);
    }

	@Override
	// 로그인 메서드: null 값 반환 시에는 잘못된 ID/비밀번호라고 입력
	public UserDTO login(long id, String password) {
		
		UserDTO loginUser = null;
		PreparedStatement pStmt = null;
		ResultSet res = null;
		try {
			checkAndReconnect();
			conn.setAutoCommit(false); // 트랜잭션 시작
			
			pStmt = conn.prepareStatement("SELECT * FROM db2024_user USE INDEX(idx_user) WHERE id = ? AND password = ?");
			pStmt.setLong(1, id);
			pStmt.setString(2, password);
			res = pStmt.executeQuery();
			if(res.next()) { // 로그인 성공 시
				loginUser = new UserDTO();
                loginUser.setId(res.getLong("id"));
                loginUser.setPassword(res.getString("password"));
                loginUser.setEmail(res.getString("email"));
                loginUser.setName(res.getString("name"));
                loginUser.setMajor(res.getString("major"));
                loginUser.setPhoneNum(res.getString("phone_num"));
                loginUser.setCanReserve(res.getBoolean("canReserve"));
                loginUser.setAdmin_id(res.getLong("admin_id"));
                
                // 구현 필요 --> 세션에 로그인 사용자 저장 
                // 세션에 로그인 사용자 저장
                SessionManager.userAuthenticate(loginUser);
                System.out.println("로그인 성공: " + loginUser.getName());
            } else {
                System.out.println("로그인 실패: 잘못된 ID 또는 비밀번호");
            }

            conn.commit(); // 트랜잭션 커밋
		} catch(Exception e) {
			if(conn != null) {
				try {
					conn.rollback();
				} catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		} finally {
			JdbcUtil.close(res);
			JdbcUtil.close(pStmt);
			try {
				conn.setAutoCommit(true); // 트랜잭션 종료
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return loginUser;
	}
	
	@Override
	public List<UserDTO> getUserListByAdminId(long adminId){
		List<UserDTO> users = new ArrayList<>();
		String sql = "SELECT * FROM db2024_UserView WHERE admin_id=?";
		
        try (PreparedStatement pStmt = conn.prepareStatement(sql)) {
			checkAndReconnect();
            pStmt.setLong(1, adminId);
            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                UserDTO user = new UserDTO();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setMajor(rs.getString("major"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNum(rs.getString("phone_num"));
                user.setCanReserve(rs.getBoolean("canReserve"));
                user.setAdmin_id(adminId);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
	}
	
	// 사용자 비밀번호 변경
	@Override
	public int resetPassword(long id, String originPassword, String newPassword) {
		PreparedStatement pStmt = null;
		ResultSet res = null;
		String sql = "SELECT password FROM db2024_User WHERE id = ?";
		String updateSql = "UPDATE db2024_User SET password = ? WHERE id = ?";
		
	     try {
			checkAndReconnect();
	         conn.setAutoCommit(false); // 트랜잭션 시작
	            
	            pStmt = conn.prepareStatement(sql);
	            pStmt.setLong(1, id);
	            res = pStmt.executeQuery();
	            
	            if (res.next()) {
	                String currentPassword = res.getString("password");
	                
	                if (currentPassword.equals(originPassword)) {
	                    pStmt = conn.prepareStatement(updateSql);
	                    pStmt.setString(1, newPassword);
	                    pStmt.setLong(2, id);
	                    int rowsAffected = pStmt.executeUpdate();
	                    
	                    if (rowsAffected > 0) {
	                        conn.commit(); // 트랜잭션 커밋
	                        return 1; // 비밀번호 변경 성공
	                    }
	                } else {
	                    conn.rollback(); // 기존 비밀번호 불일치 시 롤백
	                    return -1; // 기존 비밀번호 불일치
	                }
	            }
	            conn.rollback(); // 결과가 없을 시 롤백
	        } catch (SQLException e) {
	            if (conn != null) {
	                try {
	                    conn.rollback(); // 예외 발생 시 롤백
	                } catch (SQLException ex) {
	                    ex.printStackTrace();
	                }
	            }
	            e.printStackTrace();
	        } finally {
	            JdbcUtil.close(res);
	            JdbcUtil.close(pStmt);
	            try {
	                conn.setAutoCommit(true); // 트랜잭션 종료
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        return 0; // 비밀번호 변경 실패
	    }
	
	// 사용자 로그아웃
	@Override
	public void logout() {
		SessionManager.userLogout();
	}
}
