package main.java.com.example.team10.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.border.TitledBorder;

import main.java.com.example.team10.DAO.ReservationDAOImpl;
import main.java.com.example.team10.DTO.ReservationDTO;
import main.java.com.example.team10.DTO.UserDTO;
import main.java.com.example.team10.GUI.User.UserMyHome;
import main.java.com.example.team10.util.JdbcUtil;
import main.java.com.example.team10.util.SessionManager;

import javax.swing.border.EtchedBorder;
import javax.swing.DropMode;

public class Reserve extends JFrame {

	private JFrame frame;
	private JTextField reasonTextField;
	private JTextField peopleNumTextField;
	private JLabel selectedClassroomLabel;

	private Connection conn;
	
	/**
	 * Create the application.
	 */
	public Reserve() {
		//initialize();
	}
	
	public Reserve(long room_id, Date reserved_date, int reserved_period) {// 편리한 예약 생성을 위해 생성자 사용
		this.conn=JdbcUtil.getConnection();
			
		initialize(room_id, reserved_date, reserved_period);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(long room_id, Date reserved_date, int reserved_period) {
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(5, 1, 0, 0));
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(15);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		getContentPane().add(panel);
		
		JLabel titleLabel = new JLabel("예약하기");
		panel.add(titleLabel);
		titleLabel.setFont(new Font("굴림", Font.PLAIN, 18));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel classroomPanel = new JPanel();
		FlowLayout fl_classroomPanel = (FlowLayout) classroomPanel.getLayout();
		fl_classroomPanel.setVgap(20);
		getContentPane().add(classroomPanel);
		
		JLabel classroomLabel = new JLabel("선택한 강의실:");
		classroomLabel.setFont(new Font("굴림", Font.PLAIN, 16));
		classroomPanel.add(classroomLabel);
		
		//room_id로 building name이랑 room number 찾기
		String buildingName="";
		String roomNum="";
		
		String query = "SELECT building, room_num FROM DB2024_Classroom WHERE room_id=?";
	    try {
	        PreparedStatement pStmt = conn.prepareStatement(query);
	        pStmt.setLong(1, room_id);
	        ResultSet rs = pStmt.executeQuery();
	        while (rs.next()) {
	            buildingName = rs.getString(1);
	            roomNum = rs.getString(2);
	        }
	        rs.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		
		selectedClassroomLabel = new JLabel("강의실"); //선택된 강의실 데이터로 업데이트
		selectedClassroomLabel.setFont(new Font("굴림", Font.PLAIN, 16));
		selectedClassroomLabel.setText(buildingName+" "+roomNum);
		classroomPanel.add(selectedClassroomLabel);
		
		JPanel reasonPanel = new JPanel();
		FlowLayout fl_reasonPanel = (FlowLayout) reasonPanel.getLayout();
		fl_reasonPanel.setVgap(15);
		getContentPane().add(reasonPanel);
		
		JLabel reasonLabel = new JLabel("예약 목적:");
		reasonLabel.setFont(new Font("굴림", Font.PLAIN, 14));
		reasonPanel.add(reasonLabel);
		
		reasonTextField = new JTextField();
		reasonPanel.add(reasonTextField);
		reasonTextField.setColumns(30);
		
		JPanel peopleNumPanel = new JPanel();
		FlowLayout fl_peopleNumPanel = (FlowLayout) peopleNumPanel.getLayout();
		fl_peopleNumPanel.setVgap(15);
		getContentPane().add(peopleNumPanel);
		
		JLabel peopleNumLabel = new JLabel("예약 인원:");
		peopleNumLabel.setFont(new Font("굴림", Font.PLAIN, 14));
		peopleNumPanel.add(peopleNumLabel);
		
		peopleNumTextField = new JTextField();
		peopleNumPanel.add(peopleNumTextField);
		peopleNumTextField.setColumns(30);
		
		JPanel btnPanel = new JPanel();
		FlowLayout fl_btnPanel = (FlowLayout) btnPanel.getLayout();
		fl_btnPanel.setVgap(15);
		getContentPane().add(btnPanel);
		
		JButton reserveBtn = new JButton("예약");
		// 세션에 저장된 user 정보 불러오기
	    UserDTO currentUser = SessionManager.getCurrentUser();
	  //예약 가능한 사용자인지 확인
		int available=checkUser(currentUser);
		
		reserveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//예약 버튼 누르면 예약 생성
				if(available==0) {//예약 불가 사용자
					JOptionPane.showMessageDialog(null,"현재 예약하실 수 없습니다. 관리자에게 문의하세요.", "예약 불가 사용자", JOptionPane.ERROR_MESSAGE);
					dispose();
					new UserMyHome().setVisible(true);
					return;
				}
				int end=sendReserveInfo(room_id,reserved_date,reserved_period);
				if(end==1) {
					dispose();
					System.out.println("예약이 성공적으로 완료되었습니다.");
					// 알림 창 표시
					JOptionPane.showMessageDialog(null, "예약이 성공적으로 완료되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
					new UserMyHome().setVisible(true);
				}
			}
		});
		btnPanel.add(reserveBtn);
	}
	
	public int sendReserveInfo(long room_id, Date reserved_date, int reserved_period) {//예약 버튼 누를 시 받아온 값 전달
		ReservationDTO reservation = new ReservationDTO(); //DTO 생성
		//Search.java에서 받아온 값 집어넣기
		reservation.setRoomId(room_id);
		reservation.setReservedDate(reserved_date);
		reservation.setReservedPeriod(reserved_period);
		
		String reason;
		int people_num;
		
		//txt필드에서 값 받아오기
		reason=reasonTextField.getText();
		//예약 목적 예외 처리
		if(reason == null || reason.equals("")) {//예약 목적 미입력
			JOptionPane.showMessageDialog(this, "예약 목적을 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
			return 0;
		}
		
		//예약 인원 값 받아오기
		try {
	        people_num = Integer.parseInt(peopleNumTextField.getText());
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(this, "올바른 예약 인원을 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
	        return 0;
	    }
		//예약 인원 음수
	    if (people_num <= 0) {
	        JOptionPane.showMessageDialog(this, "유효하지 않은 예약 인원입니다.", "오류", JOptionPane.ERROR_MESSAGE);
	        return 0;
	    }
	    
	    int capacity=0;
		
		String query = "SELECT capacity FROM DB2024_Classroom WHERE room_id=?";
	    try {
	        PreparedStatement pStmt = conn.prepareStatement(query);
	        pStmt.setLong(1, room_id);
	        ResultSet rs = pStmt.executeQuery();
	        while (rs.next()) {
	            capacity= rs.getInt(1);
	        }
	        rs.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    if(people_num>capacity) {//신청 인원이 강의실 수용인원 초과
	    	JOptionPane.showMessageDialog(this, "예약 인원이 해당 강의실의 수용 인원보다 많습니다.", "오류", JOptionPane.ERROR_MESSAGE);
	        return 0;
	    }

	    //Reserve에서 받은 값 전달하기
	    reservation.setReason(reason);
        reservation.setPeopleNum(people_num);
        reservation.setCreatedDate(new Date());//예약 요청 날짜

        System.out.println(reservation.getRoomId());//search 대표(추후 삭제)
        System.out.println(reservation.getPeopleNum());//reserve 대표(추후 삭제)
        
        ReservationDAOImpl reservationDAO=new ReservationDAOImpl();//DAO 생성
        reservationDAO.createReservation(reservation);
        
        return 1;
	}
	
	public int checkUser(UserDTO user) {//user table에서 예약 가능 여부 확인하기
		String query="select canReserve from DB2024_User where id=?";
		int canReserve=0;
		PreparedStatement pStmt;
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setLong(1, user.getId());
			ResultSet rs=pStmt.executeQuery();
			if (rs.next()) {
				canReserve = rs.getInt(1);
	        }
			rs.close();
			return canReserve;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
}
