package main.java.com.example.team10.GUI.Admin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import main.java.com.example.team10.DAO.AdministratorDAO;
import main.java.com.example.team10.DAO.AdministratorDAOImpl;
import main.java.com.example.team10.DAO.UserDAO;
import main.java.com.example.team10.DAO.UserDAOImpl;
import main.java.com.example.team10.DTO.AdministratorDTO;
import main.java.com.example.team10.DTO.UserDTO;
import main.java.com.example.team10.GUI.Home;
import main.java.com.example.team10.GUI.Admin.TableModel.UserTableModel;
import main.java.com.example.team10.util.SessionManager;

public class UserManagement extends JFrame {
    private AdministratorDTO admin;
    private UserDAO userDAO;
    private UserDTO user;
	private AdministratorDAO adminDAO;
	
	private JTable userTable;
    private UserTableModel userTableModel;
	private JScrollPane scrollPane;
    private JLabel lblWelcome;

    private JButton btnLogout;
    private JButton btnBack;
    private JButton btnMyHome;
    
    public UserManagement() {
        adminDAO = new AdministratorDAOImpl();
        userDAO = new UserDAOImpl();
        admin = SessionManager.getCurrentAdmin();
        if (admin == null) {
            JOptionPane.showMessageDialog((Component) null,
                    "로그인 정보가 없습니다. 다시 로그인 해주세요.",
                    "오류",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0); // 종료
        }
        init();
        setDisplay();
        addListeners();
        showFrame();
    }
    
    public void init() {
        String welcomeText = "<html>관리자 <span style='color:blue;'>" + admin.getId() + "</span>님, 안녕하세요.</html>";
        lblWelcome = new JLabel(welcomeText);

        List<UserDTO> users = userDAO.getUserListByAdminId(admin.getId());

        userTableModel = new UserTableModel(users, adminDAO);
        userTable = new JTable(userTableModel); // userTable 초기화
        userTable.setFillsViewportHeight(true);
        userTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JCheckBox())); // userTable 초기화 후 컬럼 설정

        scrollPane = new JScrollPane(userTable); // JScrollPane 초기화
        
        btnLogout = new JButton("로그아웃");
        btnBack = new JButton("이전");
        btnMyHome = new JButton("My홈");
    }
    
    public void setDisplay() {
    	setLayout(new BorderLayout());
    	add(new JScrollPane(userTable), BorderLayout.CENTER);
    	
        // 상단 패널
        JPanel panelNorth = new JPanel(new BorderLayout());

        // 좌측 웰컴 메시지
        JPanel panelLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelLeft.add(lblWelcome);

        // 우측 버튼들
        JPanel panelRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelRight.add(btnLogout);
        panelRight.add(btnBack);
        panelRight.add(btnMyHome);

        panelNorth.add(panelLeft, BorderLayout.WEST);
        panelNorth.add(panelRight, BorderLayout.EAST);

        add(panelNorth, BorderLayout.PAGE_START);
    }
    public void addListeners() {
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 로그아웃 처리
                adminDAO.logout();
                dispose(); // 현재 창 닫기
                new Home(); // 홈 화면 열기
            }
        });
        
        btnBack.addActionListener(e -> {
            dispose();
            new AdminMyHome();
        });
        
        btnMyHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdminMyHome();
            }
        });
    }
    public void showFrame() {
        setTitle("관리자 모드:사용자 관리");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
