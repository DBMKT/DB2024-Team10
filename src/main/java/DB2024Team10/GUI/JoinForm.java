package main.java.DB2024Team10.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.java.DB2024Team10.DAO.AdministratorDAOImpl;
import main.java.DB2024Team10.DAO.UserDAOImpl;
import main.java.DB2024Team10.DTO.UserDTO;

public class JoinForm extends JFrame {

	private UserDAOImpl userDAO;
	
	private JLabel jlabelTitle;
	private JLabel jlabelId;
	private JLabel jlabelPassword;
	private JLabel jlabelName;
	private JLabel jlabelMajor;
	private JLabel jlabelEmail;
	private JLabel jlabelPhoneNum;
	
	private JTextField tfId;
	private JPasswordField tfPassword;
	private JTextField tfName;
	private JTextField tfMajor;
	private JTextField tfEmail;
	private JTextField tfPhoneNum;
	
	private JButton btnJoin;
	private JButton btnCancel;
	
	public JoinForm() {
		userDAO = new UserDAOImpl();
		init();
		setDisplay();
		addListeners();
	}
	
	public void init() {
		Dimension jLabelSize = new Dimension(80,30);
		int tfSize = 15;
		
		jlabelId = new JLabel("ID(학번)");
        jlabelId.setPreferredSize(jLabelSize);
        jlabelPassword = new JLabel("비밀번호");
        jlabelPassword.setPreferredSize(jLabelSize);
        jlabelEmail = new JLabel("이메일");
        jlabelEmail.setPreferredSize(jLabelSize);
        jlabelName = new JLabel("이름");
        jlabelName.setPreferredSize(jLabelSize);
        jlabelMajor = new JLabel("학과");
        jlabelMajor.setPreferredSize(jLabelSize);
        jlabelPhoneNum = new JLabel("전화번호");
        jlabelPhoneNum.setPreferredSize(jLabelSize);

        tfId = new JTextField(tfSize);
        tfPassword = new JPasswordField(tfSize);
        tfEmail = new JTextField(tfSize);
        tfName = new JTextField(tfSize);
        tfMajor = new JTextField(tfSize);
        tfPhoneNum = new JTextField(tfSize);
        btnJoin = new JButton("회원가입");
	}
    public void setDisplay() {
        FlowLayout flowLeft = new FlowLayout(FlowLayout.LEFT);

        JPanel panel = new JPanel(new GridLayout(0, 1));

        JPanel panelId = new JPanel(flowLeft);
        panelId.add(jlabelId);
        panelId.add(tfId);

        JPanel panelPassword = new JPanel(flowLeft);
        panelPassword.add(jlabelPassword);
        panelPassword.add(tfPassword);

        JPanel panelEmail = new JPanel(flowLeft);
        panelEmail.add(jlabelEmail);
        panelEmail.add(tfEmail);

        JPanel panelName = new JPanel(flowLeft);
        panelName.add(jlabelName);
        panelName.add(tfName);

        JPanel panelMajor = new JPanel(flowLeft);
        panelMajor.add(jlabelMajor);
        panelMajor.add(tfMajor);

        JPanel panelPhoneNum = new JPanel(flowLeft);
        panelPhoneNum.add(jlabelPhoneNum);
        panelPhoneNum.add(tfPhoneNum);

        panel.add(panelId);
        panel.add(panelPassword);
        panel.add(panelEmail);
        panel.add(panelName);
        panel.add(panelMajor);
        panel.add(panelPhoneNum);

        JPanel panelSouth = new JPanel();
        panelSouth.add(btnJoin);

        panel.setBorder(new EmptyBorder(10, 20, 10, 20));

        add(panel, BorderLayout.CENTER);
        add(panelSouth, BorderLayout.SOUTH);
    }

    public void addListeners() {
        btnJoin.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // 회원가입 로직
                try {
                    long id = Long.parseLong(tfId.getText());
                    String password = new String(tfPassword.getPassword());
                    String email = tfEmail.getText();
                    String name = tfName.getText();
                    String major = tfMajor.getText();
                    String phoneNum = tfPhoneNum.getText();

                    UserDTO newUser = new UserDTO(id, password, major, email, name, phoneNum);
                    String result = userDAO.signup(newUser);
                    JOptionPane.showMessageDialog(JoinForm.this, result, "회원가입 결과", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(JoinForm.this, "Invalid ID format", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
	
}
