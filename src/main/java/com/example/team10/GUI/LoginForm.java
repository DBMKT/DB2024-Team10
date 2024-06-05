package main.java.com.example.team10.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import main.java.com.example.team10.DAO.AdministratorDAO;
import main.java.com.example.team10.DAO.AdministratorDAOImpl;
import main.java.com.example.team10.DAO.UserDAO;
import main.java.com.example.team10.DAO.UserDAOImpl;
import main.java.com.example.team10.DTO.AdministratorDTO;
import main.java.com.example.team10.DTO.UserDTO;
import main.java.com.example.team10.GUI.Admin.AdminMyHome;
import main.java.com.example.team10.GUI.User.UserMyHome;

public class LoginForm extends JFrame {
	
	private UserDAOImpl userDAO;
	private AdministratorDAOImpl adminDAO;
	private boolean isAdmin;
	
	private JLabel jlabelId;
	private JLabel jlabelPassword;
	private JTextField tfId;
	private JPasswordField tfPassword;
	private JButton btnLogin;
	
	public LoginForm() {
	}
	public LoginForm(boolean isAdmin) {
		this.isAdmin = isAdmin;
		if(isAdmin) {
			adminDAO = new AdministratorDAOImpl();
		} else {
			userDAO = new UserDAOImpl();
		}		
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
	
	public void init() {
		Dimension jLabelSize = new Dimension(80, 30);
		int tfSize = 10;
		Dimension btnSize = new Dimension(100, 20);
		
		jlabelId = new JLabel("ID(학번)");
		jlabelId.setPreferredSize(jLabelSize);
		jlabelPassword = new JLabel("비밀번호");
		jlabelPassword.setPreferredSize(jLabelSize);
		
		tfId = new JTextField(tfSize);
		tfPassword = new JPasswordField(tfSize);
		btnLogin = new JButton("로그인");
		
	}
	
	public void setDisplay() {
	      JPanel panel = new JPanel(new GridBagLayout());
	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.insets = new Insets(10, 10, 10, 10);
	        gbc.fill = GridBagConstraints.HORIZONTAL;

	        // ID
	        gbc.gridx = 0;
	        gbc.gridy = 0;
	        gbc.gridwidth = 1;
	        panel.add(jlabelId, gbc);

	        gbc.gridx = 1;
	        gbc.gridy = 0;
	        gbc.gridwidth = 2;
	        panel.add(tfId, gbc);

	        // 비밀번호
	        gbc.gridx = 0;
	        gbc.gridy = 1;
	        gbc.gridwidth = 1;
	        panel.add(jlabelPassword, gbc);

	        gbc.gridx = 1;
	        gbc.gridy = 1;
	        gbc.gridwidth = 2;
	        panel.add(tfPassword, gbc);

	        // 로그인 버튼
	        gbc.gridx = 0;
	        gbc.gridy = 2;
	        gbc.gridwidth = 3;
	        gbc.anchor = GridBagConstraints.CENTER;
	        panel.add(btnLogin, gbc);

	        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

	        add(panel, BorderLayout.CENTER);
	}
	
	public void addListeners() {
		btnLogin.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// 아이디 칸이 비었을 경우
				if(tfId.getText().isEmpty()) {
					JOptionPane.showMessageDialog(LoginForm.this,
							"아이디를 입력하세요.",
							"로그인폼",
							JOptionPane.WARNING_MESSAGE);
				} else if(tfPassword.getPassword().length == 0) {
					JOptionPane.showMessageDialog(LoginForm.this,
							"비밀번호를 입력하세요.",
							"로그인폼",
							JOptionPane.WARNING_MESSAGE);
				} else {
					long id = Long.parseLong(tfId.getText());
					String password = new String(tfPassword.getPassword());
					if(isAdmin) {
						AdministratorDTO loginRequestAdmin = adminDAO.adminLogin(id, password);
						
						if(loginRequestAdmin != null) {
                            JOptionPane.showMessageDialog(LoginForm.this,
                                    "로그인 성공: " + loginRequestAdmin.getId() + "님, 안녕하세요.",
                                    "로그인폼",
                                    JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new AdminMyHome();
						}
						else {
	                        JOptionPane.showMessageDialog(LoginForm.this,
	                                "로그인 실패: 잘못된 ID 또는 비밀번호",
	                                "로그인폼",
	                                JOptionPane.ERROR_MESSAGE);
						}
					}
					else {
						UserDTO loginRequestUser = userDAO.login(id, password);
						
						if(loginRequestUser != null) {
							JOptionPane.showMessageDialog(LoginForm.this,
							"로그인 성공: "+loginRequestUser.getName()+"님, 안녕하세요.",
							"로그인폼",
							JOptionPane.INFORMATION_MESSAGE);
						dispose();
						new UserMyHome();
							// 로그인 성공 시 관리자 my home, 사용자 my home으로 이동
						} else {
							JOptionPane.showMessageDialog(LoginForm.this,
							"로그인 실패: 잘못된 ID 또는 비밀번호",
							"로그인폼",
							JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
	}
	public void showFrame() {
		setTitle("로그인");
		setSize(400, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
