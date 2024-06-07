package main.java.DB2024Team10.GUI.Admin;
import main.java.DB2024Team10.util.SessionManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import main.java.DB2024Team10.DAO.AdministratorDAO;
import main.java.DB2024Team10.DAO.AdministratorDAOImpl;
import main.java.DB2024Team10.DTO.AdministratorDTO;
import main.java.DB2024Team10.GUI.Home;

public class AdminMyHome extends JFrame {

    private AdministratorDTO admin;
    private AdministratorDAO adminDAO;
    private JLabel lblWelcome;
    private JButton btnUserManagement;
    private JButton btnReservationManagement;
    private JButton btnLogout;
    private JButton btnBack;
    private JButton btnMyHome;

    public AdminMyHome() {
        admin = SessionManager.getCurrentAdmin();
        if (admin == null) {
            JOptionPane.showMessageDialog((Component) null,
                    "로그인 정보가 없습니다. 다시 로그인 해주세요.",
                    "오류",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0); // 종료
        }
        adminDAO = new AdministratorDAOImpl();
        init();
        setDisplay();
        addListeners();
        showFrame();
    }

    public void init() {
        String welcomeText = "<html>관리자 <span style='color:blue;'>" + admin.getId() + "</span>님, 안녕하세요.</html>";
        lblWelcome = new JLabel(welcomeText);
        btnUserManagement = new JButton("사용자 관리");
        btnReservationManagement = new JButton("예약 내역 관리");
        
        btnLogout = new JButton("로그아웃");
        btnBack = new JButton("이전");
        btnMyHome = new JButton("My홈");
        
        // 버튼 크기 설정
        Dimension buttonSize = new Dimension(200, 60);
        btnUserManagement.setPreferredSize(buttonSize);
        btnReservationManagement.setPreferredSize(buttonSize);
        
        Font buttonFont = new Font("굴림", Font.BOLD, 20);
        btnUserManagement.setFont(buttonFont);
        btnReservationManagement.setFont(buttonFont);
    }

    public void setDisplay() {
        setLayout(new BorderLayout());

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

        // 중앙 패널
        JPanel panelCenter = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panelCenter.add(btnUserManagement, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panelCenter.add(btnReservationManagement, gbc);

        panelCenter.setBorder(new EmptyBorder(20, 20, 20, 20));

        add(panelCenter, BorderLayout.CENTER);
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

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 이전 화면으로 돌아가기
            	adminDAO.logout();
                dispose(); // 현재 창 닫기
            }
        });

        btnMyHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // My홈 화면으로 돌아가기 (여기서는 현재 화면이므로 별도 작업 불필요)
            }
        });
        btnUserManagement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 사용자 관리 화면으로 전환 (예시로 간단한 메시지 박스를 띄움)
                		new UserManagement(); // 홈 화면 열기
        	}
    	});
       

        btnReservationManagement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 예약 관리 화면으로 전환 (예시로 간단한 메시지 박스를 띄움)
                new ReservationManagement();
            }
        });
    }
    public void showFrame() {
        setTitle("관리자 모드: my 홈");
        setSize(500, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
