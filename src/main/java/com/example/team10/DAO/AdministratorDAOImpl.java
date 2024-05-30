package main.java.com.example.team10.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.java.com.example.team10.DTO.AdministratorDTO;
import main.java.com.example.team10.DTO.ReservationDTO;
import main.java.com.example.team10.DTO.UserDTO;
import main.java.com.example.team10.util.JdbcUtil;
import main.java.com.example.team10.util.SessionManager;


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
                SessionManager.adminAuthenticate(loginAdmin);
                System.out.println("로그인 성공: " + loginAdmin.getContact());
            } else {
                System.out.println("로그인 실패: 잘못된 ID 또는 비밀번호");
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
            try {
            	conn.setAutoCommit(true); // 트랜잭션 종료
            } catch(SQLException e) {
            	e.printStackTrace();
            }
        }
		return loginAdmin;
	}
	
	@Override
	public void updateUserInfo(UserDTO targetUser) {
		// 관리자 자신의 담당 사용자의 예약 권한 조정
		PreparedStatement pStmt = null;
		
		try {
			conn.setAutoCommit(false); // 트랜잭션 시작
			
			if(SessionManager.getCurrentAdmin().getId() != targetUser.getAdmin_id()) {
				// 예외처리 먼저 --> 현재 로그인한 관리자의 담당 유저가 아닌 유저에 대해 접근할 경우
				conn.rollback();
				System.out.println("해당 사용자에 대한 접근 권한이 없습니다.");
				return;
			}
			else {
				String sql = "UPDATE db2024_User SET canReserve = ? WHERE id = ? AND admin_id = ?";
				pStmt = conn.prepareStatement(sql);
				pStmt.setBoolean(1, targetUser.isCanReserve());
				pStmt.setLong(2, targetUser.getId());
				pStmt.setLong(3, targetUser.getAdmin_id());

				int rowsAffected = pStmt.executeUpdate();
				if(rowsAffected > 0) {
					conn.commit(); // 업데이트 성공 --> 트랜잭션 커밋
					System.out.println("예약권한이 성공적으로 업데이트되었습니다.");
				}
				else {
					conn.rollback(); // 실패 시 롤백
					System.out.println("예약 권한 업데이트에 실패하였습니다.");
				}
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
			if(pStmt != null) {
				JdbcUtil.close(pStmt);
			}
			try {
				conn.setAutoCommit(true); // 트랜잭션 종료
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 관리자 기능: 예약 내역 삭제
	@Override
	public void deleteReservationInfo(List<ReservationDTO> reservations) {
	    PreparedStatement pStmt = null;

	    try {
	        conn.setAutoCommit(false); // 트랜잭션 시작

	        String sql = "DELETE FROM db2024_Reservation WHERE reserved_id = ?";
	        pStmt = conn.prepareStatement(sql);

	        for (ReservationDTO reservation : reservations) {
	            pStmt.setLong(1, reservation.getReservedId());
	            pStmt.addBatch();
	        }

	        int[] rowsAffected = pStmt.executeBatch();
	        boolean allDeleted = true;
	        for (int r : rowsAffected) {
	            if (r == 0) {
	                allDeleted = false;
	                break;
	            }
	        }

	        if (allDeleted) {
	            conn.commit();
	            System.out.println("선택된 예약 내역을 삭제했습니다.");
	        } else {
	            conn.rollback(); // 실패 시 롤백
	            System.out.println("일부 예약 내역 삭제에 실패했습니다.");
	        }
	    } catch (SQLException e) {
	        if (conn != null) {
	            try {
	                conn.rollback(); // 예외 발생 시 롤백
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	        e.printStackTrace();
	        System.out.println("예약 내역 삭제 중 오류가 발생했습니다.");
	    } finally {
	        if (pStmt != null) {
	            JdbcUtil.close(pStmt);
	        }
	        try {
	            conn.setAutoCommit(true); // 트랜잭션 종료
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

	
	// 현재 예약 내역 불러오기 --> 현재 시점을 기준으로 가까운 예약 내역부터 정렬하여 조회
    @Override
    public List<ReservationDTO> getUpcomingReservationList() {
        List<ReservationDTO> reservations = new ArrayList<>();
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT reserved_id, room_id, user_name, reason, people_num, reserved_date, reserved_period, created_date FROM db2024_Reservation ORDER BY reserved_date ASC;";
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();

            while (rs.next()) {
                long reserved_id = rs.getLong("reserved_id");
                long room_id = rs.getLong("room_id");
                String user_name = rs.getString("user_name");
                String reason = rs.getString("reason");
                int people_num = rs.getInt("people_num");
                Date reserved_date = rs.getDate("reserved_date");
                int reserved_period = rs.getInt("reserved_period");
                Date created_date = rs.getDate("created_date");

                ReservationDTO reservation = new ReservationDTO(reserved_id, room_id, user_name, 
                		reason, people_num, reserved_date, reserved_period, created_date);
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pStmt != null) {
                try {
                    pStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return reservations;
    }
    
    // 예약 내역 검색 메서드
    @Override
    public List<ReservationDTO> searchReservationList(String keyword, List<String> selectedFields, int period, Date selectedDate) {
        List<ReservationDTO> reservations = new ArrayList<>();
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {
            // 동적으로 SQL 쿼리 생성
            StringBuilder sql = new StringBuilder("SELECT * FROM db2024_Reservation WHERE ");
            List<String> conditions = new ArrayList<>();
            
            if (selectedFields.contains("예약자 이름")) {
                conditions.add("user_name LIKE ?");
            }
            if (selectedFields.contains("예약 날짜")) {
                conditions.add("reserved_date = ?");
            }
            if (selectedFields.contains("신청 교시")) {
                conditions.add("reserved_period = ?");
            }

            if (conditions.isEmpty()) {
                sql.append("1 = 1"); // 조건 미적용 시 모든 결과값 반환하기
            } else {
                sql.append(String.join(" AND ", conditions));
            }
            sql.append(" ORDER BY reserved_date ASC");

            pStmt = conn.prepareStatement(sql.toString());

            int paramIndex = 1;
            if (selectedFields.contains("예약자 이름")) {
                pStmt.setString(paramIndex++, "%" + keyword + "%");
            }
            if (selectedFields.contains("예약 날짜") && selectedDate != null) {
                pStmt.setDate(paramIndex++, new java.sql.Date(selectedDate.getTime()));
            }
            if (selectedFields.contains("신청 교시")) {
                pStmt.setInt(paramIndex++, period);
            }

            rs = pStmt.executeQuery();
            while (rs.next()) {
                long reserved_id = rs.getLong("reserved_id");
                long room_id = rs.getLong("room_id");
                String user_name = rs.getString("user_name");
                String reason = rs.getString("reason");
                int people_num = rs.getInt("people_num");
                Date reserved_date = rs.getDate("reserved_date");
                int reserved_period = rs.getInt("reserved_period");
                Date created_date = rs.getDate("created_date");

                ReservationDTO reservation = new ReservationDTO(reserved_id, room_id, user_name, reason, 
                		people_num, reserved_date, reserved_period, created_date);
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pStmt != null) {
                try {
                    pStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return reservations;
    }


    // 현재 시점을 기준으로 지난 예약 내역은 자동적으로 삭제
    @Override
    public void deletePastReservations() {
        PreparedStatement pStmt = null;

        try {
            String sql = "DELETE FROM db2024_Reservation WHERE reserved_date < ?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setTimestamp(1, new Timestamp(new Date().getTime()));

            int rowsAffected = pStmt.executeUpdate();
            System.out.println("삭제된 지난 예약 내역 수: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("지난 예약 내역 삭제 중 오류가 발생했습니다.");
        } finally {
            if (pStmt != null) {
                try {
                    pStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
	@Override // 관리자 로그아웃
	public void logout() {
		SessionManager.adminLogout();
	}

}
