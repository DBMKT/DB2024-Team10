package main.java.DB2024Team10.GUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Home extends JFrame{
	
	private JButton btnAdminLogin;
	private JButton btnUserLogin;
	private JButton btnUserSignup;
	
	public Home() {
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
	
	public void init() {
		btnAdminLogin = new JButton("관리자 모드로 로그인");
		btnUserLogin = new JButton("사용자 로그인");
		btnUserSignup = new JButton("사용자 회원가입");
		
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

        // 관리자 모드로 로그인 버튼
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // span across two columns
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnAdminLogin, gbc);

        // 사용자 로그인 버튼
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(btnUserLogin, gbc);

        // 사용자 회원가입 버튼
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
                // 관리자 로그인 폼으로 전환
                LoginForm loginForm = new LoginForm(true);
                loginForm.setTitle("관리자 모드로 로그인");
                loginForm.setSize(390, 260);
                loginForm.setLocationRelativeTo(null);
                loginForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                loginForm.setVisible(true);
            }
        });
        
        btnUserLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 사용자 로그인 폼으로 전환
                LoginForm loginForm = new LoginForm(false);
                loginForm.setTitle("사용자 로그인");
                loginForm.setSize(390, 260);
                loginForm.setLocationRelativeTo(null);
                loginForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                loginForm.setVisible(true);
            }
        });
        
        btnUserSignup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 사용자 회원가입 폼으로 전환
                JoinForm joinForm = new JoinForm();
                joinForm.setTitle("사용자 회원가입");
                joinForm.setSize(390,260);
                joinForm.setLocationRelativeTo(null);
                joinForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                joinForm.setVisible(true);
            }
        });
    }
    
    public void showFrame() {
        setTitle("홈 화면");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
