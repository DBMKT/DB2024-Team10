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

import javax.swing.border.EtchedBorder;
import javax.swing.DropMode;

public class Reserve extends JFrame {

	private JFrame frame;
	private JTextField reasonTextField;
	private JTextField peopleNumTextField;
	private JLabel selectedClassroomLabel;

	/**
	 * Create the application.
	 */
	public Reserve() {
		//initialize();
	}
	
	public Reserve(long room_id, Date reserved_date, int reserved_period) {// 편리한 예약 생성을 위해 생성자 사용
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
		
		selectedClassroomLabel = new JLabel("강의실"); //선택된 강의실 데이터로 업데이트
		selectedClassroomLabel.setFont(new Font("굴림", Font.PLAIN, 16));
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
		reserveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//예약 버튼 누르면 예약 생성
				sendReserveInfo(room_id,reserved_date,reserved_period);
				System.out.println("예약 완료");
			}
		});
		btnPanel.add(reserveBtn);
	}

	public void sendReserveInfo(long room_id, Date reserved_date, int reserved_period) {//예약 버튼 누를 시 받아온 값 전달
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
		if(reason==null) {//예약 목적 미입력
			JOptionPane.showMessageDialog(this, "예약 목적을 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		//예약 인원 값 받아오기
		try {
	        people_num = Integer.parseInt(peopleNumTextField.getText());
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(this, "올바른 예약 인원을 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
		//예약 인원 음수
	    if (people_num <= 0) {
	        JOptionPane.showMessageDialog(this, "유효하지 않은 예약 인원입니다.", "오류", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    //Reserve에서 받은 값 전달하기
	    reservation.setReason(reason);
        reservation.setPeopleNum(people_num);
        reservation.setCreatedDate(new Date());//예약 요청 날짜
	    
        System.out.println(reservation.getRoomId());//search 대표(추후 삭제)
        System.out.println(reservation.getPeopleNum());//reserve 대표(추후 삭제)
        
        ReservationDAOImpl reservationDAO=new ReservationDAOImpl();//DAO 생성
        reservationDAO.createReservation(reservation);
	}
}
