package main.java.com.example.team10.GUI.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import main.java.com.example.team10.DAO.ReservationDAO;
import main.java.com.example.team10.DAO.ReservationDAOImpl;
import main.java.com.example.team10.DTO.ReservationDTO;
import main.java.com.example.team10.DTO.UserDTO;
import main.java.com.example.team10.GUI.Home;
import main.java.com.example.team10.GUI.Admin.TableModel.ReserveTableModel;
import main.java.com.example.team10.util.SessionManager;

public class MyReservations extends JFrame {
    private UserDTO user;
    private ReservationDAO reservationDAO;
    private JTable reserveTable;
    private ReserveTableModel reserveTableModel;
    private JButton btnLogout;
    private JButton btnBack;
    private JLabel lblUserName;
    private Font koreanFont;

   public MyReservations() {
        user = SessionManager.getCurrentUser();
        if (user == null) {
            JOptionPane.showMessageDialog(null, "로그인 정보가 없습니다. 다시 로그인 해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
            System.exit(0); // 종료
        }

        reservationDAO = new ReservationDAOImpl();
        init();
        setDisplay();
        addListeners();
        showFrame();
    }

    private void init() {
    	try {
            koreanFont = new Font("Malgun Gothic", Font.PLAIN, 14);
            // Ensure the font is available on the system
            if (!isFontAvailable(koreanFont)) {
                koreanFont = new Font("SansSerif", Font.PLAIN, 14); // Fallback font
            }
        } catch (Exception e) {
            koreanFont = new Font("SansSerif", Font.PLAIN, 14); // Fallback font
        }
    	
        btnLogout = new JButton("로그아웃");
        btnBack = new JButton("이전");

        lblUserName = new JLabel(user.getName()+" 학생의 예약내역");
        lblUserName.setFont(koreanFont);
        reserveTableModel = new ReserveTableModel(new ArrayList<>(), false);
        reserveTable = new JTable(reserveTableModel);

        loadUserReservations();
    }

    private void setDisplay() {
    	JPanel panelNorth = new JPanel(new BorderLayout());
        JPanel panelNorthButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelNorthButtons.add(btnLogout);
        panelNorthButtons.add(btnBack);

        panelNorth.add(lblUserName, BorderLayout.WEST);

        panelNorth.add(panelNorthButtons, BorderLayout.EAST);

        add(panelNorth, BorderLayout.NORTH);
        add(new JScrollPane(reserveTable), BorderLayout.CENTER);
    }

    private void loadUserReservations() {
        List<ReservationDTO> reservations = reservationDAO.getReservationsByUserId(user.getId());
        for (ReservationDTO reservation : reservations) {
            reservation.setUserName(user.getName());
        }  
        reserveTableModel.setReservations(reservations);
    }

    private void addListeners() {
        btnLogout.addActionListener(e -> {
            SessionManager.userLogout();
            dispose(); // 현재 창 닫기
            new Home(); // 홈 화면 열기
        });

        btnBack.addActionListener(e -> {
            dispose();
            new UserMyHome();
        });
    }

    private void showFrame() {
        setTitle("내 예약 내역");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private boolean isFontAvailable(Font font) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (Font f : ge.getAllFonts()) {
            if (f.getName().equals(font.getName())) {
                return true;
            }
        }
        return false;

    }

}
