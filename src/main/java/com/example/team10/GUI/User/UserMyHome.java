package main.java.com.example.team10.GUI.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class UserMyHome extends JFrame{
	
	private JButton btnAdminLogin;
	private JButton btnUserLogin;
	private JButton btnUserSignup;
	
	public UserMyHome() {
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
	
	public void init() {
		btnAdminLogin = new JButton("비밀번호 변경");
		btnUserLogin = new JButton("강의실 예약");
		btnUserSignup = new JButton("내 예약내역 조회");
		
		Dimension buttonSize = new Dimension(250,70);
        btnAdminLogin.setPreferredSize(buttonSize);
        btnUserLogin.setPreferredSize(buttonSize);
        btnUserSignup.setPreferredSize(buttonSize);
        
        Font font = new Font("굴림", Font.PLAIN, 18);
        btnAdminLogin.setFont(font);
        btnUserLogin.setFont(font);
        btnUserSignup.setFont(font);
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
        panel.add(btnAdminLogin, gbc);

        // 강의실 예약 버튼
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(btnUserLogin, gbc);

        // 나의 예약내역 조회 버튼
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(btnUserSignup, gbc);
        
		panel.setBorder(BorderFactory.createEmptyBorder(35, 35, 35, 35));
		
		add(panel, BorderLayout.CENTER);
	}
    public void addListeners() {
        btnAdminLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 
            }
        });
        
        btnUserLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 
            }
        });
        
        btnUserSignup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 
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

