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
import javax.swing.border.EtchedBorder;
import javax.swing.DropMode;

public class Reserve extends JFrame {

	private JFrame frame;
	private JTextField reasonTextField;
	private JTextField peopleNumTextField;
	private JLabel selectedClassroomLabel;
	
	// Database connection parameters
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/db2024?serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "root";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Reserve window = new Reserve();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Reserve() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
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
		btnPanel.add(reserveBtn);
	}

	
	
	private void insertReserveInfo() {//예약 버튼 누를 시
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
	    
	    
	    //INSERT INTO db2024_Reservation(reserved_id, room_id, admin_id, user_id,
        //user_name, reason, people_num, reserved_date, reserved_period, created_date)
//VALUES
	   
	    //insert 쿼리 작성중
	    
	}
}
