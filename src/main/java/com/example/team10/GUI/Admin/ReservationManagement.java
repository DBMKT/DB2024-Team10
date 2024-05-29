package main.java.com.example.team10.GUI.Admin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.table.TableColumn;

import com.toedter.calendar.JDateChooser;
import main.java.com.example.team10.DAO.*;
import main.java.com.example.team10.DTO.*;
import main.java.com.example.team10.GUI.Home;
import main.java.com.example.team10.GUI.Admin.TableModel.ReserveTableModel;
import main.java.com.example.team10.util.SessionManager;

public class ReservationManagement extends JFrame {
    private AdministratorDTO admin;
    private AdministratorDAO adminDAO;
    private JTable reserveTable;
    private ReserveTableModel reserveTableModel;
    private JScrollPane scrollPane;
    private JLabel lblWelcome;

    private JPanel panelSearch;
    private JButton btnSearch;
    private JTextField tfSearch;
    private JButton btnLogout;
    private JButton btnBack;
    private JButton btnMyHome;
    private JButton btnDelete;

    private JCheckBox chkUserName = new JCheckBox("예약자 이름");
    private JCheckBox chkReserve_date = new JCheckBox("예약 날짜");
    private JCheckBox chkPeriod = new JCheckBox("신청 교시");

    private JComboBox<Integer> cmbPeriod; // 교시 선택 콤보박스
    private JDateChooser dateChooser; // 날짜 선택기

    public ReservationManagement() {
        adminDAO = new AdministratorDAOImpl();
        admin = SessionManager.getCurrentAdmin();
        if (admin == null) {
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
        String welcomeText = "<html>관리자 <span style='color:blue;'>" + admin.getId() + "</span>님, 안녕하세요.</html>";
        lblWelcome = new JLabel(welcomeText);

        btnLogout = new JButton("로그아웃");
        btnBack = new JButton("이전");
        btnMyHome = new JButton("My홈");
        btnDelete = new JButton("선택 항목 삭제");

        // 예약 내역 테이블 초기화
        reserveTableModel = new ReserveTableModel(new ArrayList<>());
        reserveTable = new JTable(reserveTableModel);
        scrollPane = new JScrollPane(reserveTable);
    }

    public void setDisplay() {
        // 상단 패널
        JPanel panelNorth = new JPanel(new BorderLayout());

        // 좌측 웰컴 메시지
        JPanel panelLeft = new JPanel();
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
        panelLeft.add(lblWelcome, BorderLayout.NORTH);

        // 검색 필터링(체크박스) 패널
        	// 예약자 이름, 신청 교시, 예약 날짜별 체크 박스 표시 후 검색 가능
        JPanel chkPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        chkPanel.setBorder(BorderFactory.createTitledBorder("검색 조건"));
        
        // 예약자 이름 필드값
        chkPanel.add(chkUserName);
        JPanel searchUserNameInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tfSearch = new JTextField(10);
        chkPanel.add(tfSearch);
        
        // 신청 교시
        chkPanel.add(chkPeriod);
        	// 신청 교시 콤보박스
        Integer[] periods = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        cmbPeriod = new JComboBox<>(periods);
        chkPanel.add(cmbPeriod);

        chkPanel.add(chkReserve_date);
        // 예약 날짜 선택기
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        chkPanel.add(dateChooser);
        
        btnSearch = new JButton("검색");
        chkPanel.add(btnSearch);
        
        panelLeft.add(chkPanel);

        
        // 우측 버튼들
        JPanel panelRight = new JPanel();
        panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelButtons.add(btnLogout);
        panelButtons.add(btnBack);
        panelButtons.add(btnMyHome);
        
        JPanel panelDeleteButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelDeleteButton.add(btnDelete);
        
        panelRight.add(panelButtons);
        panelRight.add(panelDeleteButton);

        panelNorth.add(panelLeft, BorderLayout.WEST);
        panelNorth.add(panelRight, BorderLayout.EAST);

        add(panelNorth, BorderLayout.PAGE_START);

        // 예약 내역 테이블 추가
        add(scrollPane, BorderLayout.CENTER);

        TableColumn column = reserveTable.getColumnModel().getColumn(3);
        column.setPreferredWidth(300); // 원하는 너비로 설정
        // 예약 내역 로드
        loadAllReservations();
    }

    private void loadAllReservations() {
        List<ReservationDTO> reservations = adminDAO.getUpcomingReservationList();
        reserveTableModel.setReservations(reservations);
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

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = tfSearch.getText();
                List<String> selectedFields = new ArrayList<>();
                if (chkUserName.isSelected()) selectedFields.add("예약자 이름");
                if (chkReserve_date.isSelected()) selectedFields.add("예약 날짜");
                if (chkPeriod.isSelected()) selectedFields.add("신청 교시");

                // 신청 교시 값
                int period = (int) cmbPeriod.getSelectedItem();
                if (chkPeriod.isSelected()) {
                    selectedFields.add("신청 교시: " + period + "교시");
                }

                // 예약 날짜 값
                Date selectedDate = dateChooser.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (chkReserve_date.isSelected() && selectedDate != null) {
                    selectedFields.add("예약 날짜: " + sdf.format(selectedDate));
                }

                if (selectedFields.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "검색 조건을 선택하세요.");
                } else {
                    searchReservationList(keyword, selectedFields, period, selectedDate);
                }
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedReservations();
            }
        });
    }

    private void searchReservationList(String keyword, List<String> selectedFields, int period, Date selectedDate) {
        List<ReservationDTO> reservations = adminDAO.searchReservationList(keyword, selectedFields, period, selectedDate);
        reserveTableModel.setReservations(reservations);
    }

    private void deleteSelectedReservations() {
        List<ReservationDTO> toDelete = new ArrayList<>();
        for (int i = 0; i < reserveTableModel.getRowCount(); i++) {
            Boolean isChecked = (Boolean) reserveTableModel.getValueAt(i, 0); // 체크박스 컬럼 인덱스
            if (isChecked != null && isChecked) {
                toDelete.add(reserveTableModel.getReservationAt(i));
            }
        }

        if (!toDelete.isEmpty()) {
            adminDAO.deleteReservationInfo(toDelete);
            loadAllReservations(); // 삭제 후 테이블 갱신
        } else {
            JOptionPane.showMessageDialog(null, "삭제할 항목을 선택하세요.");
        }
    }


    public void showFrame() {
        setTitle("예약 내역 관리");
        setSize(1200, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
