package main.java.com.example.team10.GUI.User;

import javax.swing.*;
import javax.swing.table.TableColumn;
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
        btnLogout = new JButton("로그아웃");
        btnBack = new JButton("이전");

        reserveTableModel = new ReserveTableModel(new ArrayList<>(), false);
        reserveTable = new JTable(reserveTableModel);

        loadUserReservations();
    }

    private void setDisplay() {
        JPanel panelNorth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelNorth.add(btnLogout);
        panelNorth.add(btnBack);

        add(panelNorth, BorderLayout.NORTH);
        add(new JScrollPane(reserveTable), BorderLayout.CENTER);
    }

    private void loadUserReservations() {
        List<ReservationDTO> reservations = reservationDAO.getReservationsByUserId(user.getId());
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

}




 