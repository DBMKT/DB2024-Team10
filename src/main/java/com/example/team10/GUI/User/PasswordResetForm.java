package main.java.com.example.team10.GUI.User;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import main.java.com.example.team10.DAO.UserDAOImpl;
import main.java.com.example.team10.DTO.UserDTO;
import main.java.com.example.team10.GUI.Home;
import main.java.com.example.team10.GUI.LoginForm;
import main.java.com.example.team10.GUI.Admin.AdminMyHome;
import main.java.com.example.team10.util.SessionManager;

public class PasswordResetForm extends JFrame {
    private UserDTO user;
	private UserDAOImpl userDAO;

	private JLabel jLabelOriginPassword;
	private JLabel jLabelNewPassword;
	private JPasswordField tfOriginPassword;
	private JPasswordField tfNewPassword;
	private JButton btnReset;
	
	public PasswordResetForm() {
		userDAO = new UserDAOImpl();        
        user = SessionManager.getCurrentUser(); // 현재 로그인한 사용자 정보 끌어오기 
        if (user == null) {
            JOptionPane.showMessageDialog(null, "로그인 정보가 없습니다. 다시 로그인 해주세요.", "오류",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0); // 종료
        }
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
	
	public void init() {
        Dimension jLabelSize = new Dimension(120, 10); // 레이블 크기
        Dimension tfSize = new Dimension(200, 10); // 텍스트 필드 크기
        Dimension btnSize = new Dimension(100, 10); // 버튼 크기
        
        jLabelOriginPassword = new JLabel("기존 비밀번호");
        jLabelOriginPassword.setPreferredSize(jLabelSize);
        jLabelNewPassword = new JLabel("새로운 비밀번호");
        jLabelNewPassword.setPreferredSize(jLabelSize);
        
        tfOriginPassword = new JPasswordField();
        tfOriginPassword.setPreferredSize(tfSize);
        tfNewPassword = new JPasswordField();
        tfNewPassword.setPreferredSize(tfSize);
        btnReset = new JButton("변경");
        btnReset.setPreferredSize(btnSize);
	}
	
	public void setDisplay() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.8;
        gbc.weighty = 0.8;
        
	        // 기존 비번
	        gbc.gridx = 0;
	        gbc.gridy = 0;
	        gbc.gridwidth = 1;
	        panel.add(jLabelOriginPassword, gbc);

	        gbc.gridx = 1;
	        gbc.gridy = 0;
	        gbc.gridwidth = 2;
	        panel.add(tfOriginPassword, gbc);

	        // 비밀번호
	        gbc.gridx = 0;
	        gbc.gridy = 1;
	        gbc.gridwidth = 1;
	        panel.add(jLabelNewPassword, gbc);

	        gbc.gridx = 1;
	        gbc.gridy = 1;
	        gbc.gridwidth = 2;
	        panel.add(tfNewPassword, gbc);

	        // 변경 버튼
	        gbc.gridx = 0;
	        gbc.gridy = 2;
	        gbc.gridwidth = 3;
	        gbc.anchor = GridBagConstraints.CENTER;
	        panel.add(btnReset, gbc);

	        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

	        add(panel, BorderLayout.CENTER);
	}
	
	public void addListeners() {
		btnReset.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// 아이디 칸이 비었을 경우
				if(tfOriginPassword.getPassword().length == 0) {
					JOptionPane.showMessageDialog(PasswordResetForm.this,
							"기존 비밀번호를 입력하세요.",
							"비밀번호 변경 폼",
							JOptionPane.WARNING_MESSAGE);
				} else if(tfNewPassword.getPassword().length == 0) {
					JOptionPane.showMessageDialog(PasswordResetForm.this,
							"새로운 비밀번호를 입력하세요.",
							"비밀번호 변경 폼",
							JOptionPane.WARNING_MESSAGE);
				} else {
					String originPassword = new String(tfOriginPassword.getPassword());
					String newPassword = new String(tfNewPassword.getPassword());
					
					long currentUserId = SessionManager.getCurrentUser().getId();
					int res = userDAO.resetPassword(currentUserId, originPassword, newPassword);
					
						if(res == 1) {
							JOptionPane.showMessageDialog(PasswordResetForm.this,
									"비밀번호 변경이 성공적으로 되었습니다. 다시 로그인해주세요.",
									"비밀번호 변경 폼",
									JOptionPane.INFORMATION_MESSAGE);
							userDAO.logout();
							dispose();
						} else if(res == -1){
							JOptionPane.showMessageDialog(PasswordResetForm.this,
									"비밀번호 변경 실패: 기존 비밀번호가 일치하지 않습니다.",
									"비밀번호 변경 폼",
									JOptionPane.ERROR_MESSAGE);
						} else {
	                        JOptionPane.showMessageDialog(PasswordResetForm.this,
	                                "비밀번호 변경에 실패했습니다.",
	                                "비밀번호 변경",
	                                JOptionPane.ERROR_MESSAGE);
	                    }
					}
				}
			});
	}
	public void showFrame() {
		setTitle("비밀번호 변경");
		setSize(400, 250);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
