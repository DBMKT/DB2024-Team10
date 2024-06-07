package main.java.com.example.team10.GUI.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.com.example.team10.GUI.Admin.ReservationManagement;

public class UserMyHome extends JFrame{
	
	private JButton btnPasswordReset;
	private JButton btnAddReservation;
	private JButton btnMyReservationList;
	
	public UserMyHome() {
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
	
	public void init() {
		btnPasswordReset = new JButton("비밀번호 변경");
		btnAddReservation = new JButton("강의실 예약하기");
		btnMyReservationList = new JButton("내 예약내역 조회");
		
		Dimension buttonSize = new Dimension(250,70);
		btnPasswordReset.setPreferredSize(buttonSize);
		btnAddReservation.setPreferredSize(buttonSize);
		btnMyReservationList.setPreferredSize(buttonSize);
        
        Font font = new Font("굴림", Font.PLAIN, 18);
        btnPasswordReset.setFont(font);
        btnAddReservation.setFont(font);
        btnMyReservationList.setFont(font);
	}
	
	public void setDisplay() {
		JPanel panel = new JPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 비밀번호 변경 버튼
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // span across two columns
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnPasswordReset, gbc);

        // 강의실 예약 버튼
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(btnAddReservation, gbc);

        // 나의 예약내역 조회 버튼
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(btnMyReservationList, gbc);
        
		panel.setBorder(BorderFactory.createEmptyBorder(35, 35, 35, 35));
		
		add(panel, BorderLayout.CENTER);
	}
    public void addListeners() {
    	btnPasswordReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PasswordResetForm(); 
            }
        });
        
    	btnAddReservation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Search().setVisible(true);
            }
        });
        
    	btnMyReservationList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MyReservations();
            }
        });
    }
    
    public void showFrame() {
        setTitle("사용자 my 홈 화면");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}

