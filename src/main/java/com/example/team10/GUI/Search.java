package main.java.com.example.team10.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JCalendar;

public class Search extends JFrame {

    private JTextField capacityTextField;
    private JTable table;
    private JCalendar calendar;
    private DefaultTableModel tableModel;
    private JComboBox<String> plugComboBox;
    private JRadioButton[] buildingRadioButtons;
    private JCheckBox capacityCheckBox, plugCheckBox, micCheckBox, projectorCheckBox;

    // Database connection parameters
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/db2024?serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "root";

    /**
     * Launch the application.
     */
    public static void main(String[] args) { // 화면 실행
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Search window = new Search();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Search() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        setTitle("예약 검색");
        setSize(1000, 500); // 창 크기
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 튜플 선택 후 교시 선택하는 패널(화면 하단)
        JPanel reserve_panel = new JPanel();
        reserve_panel.setSize(900, 100);
        getContentPane().add(reserve_panel, BorderLayout.SOUTH);
        reserve_panel.setLayout(new GridLayout(3, 1, 0, 0));

        // 선택 정보 패널
        JPanel select_panel = new JPanel();
        FlowLayout fl_select_panel = (FlowLayout) select_panel.getLayout();
        fl_select_panel.setVgap(3);
        fl_select_panel.setAlignment(FlowLayout.LEADING);
        fl_select_panel.setHgap(15);
        reserve_panel.add(select_panel);

        // '선택 정보' 라벨
        JLabel selectInfoLabel = new JLabel("선택 정보:");
        selectInfoLabel.setFont(new Font("굴림", Font.PLAIN, 16));
        select_panel.add(selectInfoLabel);

        // '강의실을 선택하세요' 라벨
        JLabel selectedLabel = new JLabel("강의실을 선택하세요.");
        selectedLabel.setFont(new Font("굴림", Font.PLAIN, 16));
        select_panel.add(selectedLabel);

        // 교시 선택 패널
        JPanel period_panel = new JPanel();
        FlowLayout flowLayout = (FlowLayout) period_panel.getLayout();
        flowLayout.setVgap(3);
        flowLayout.setHgap(15);
        flowLayout.setAlignment(FlowLayout.LEADING);
        reserve_panel.add(period_panel);

        // '교시 선택' 라벨
        JLabel choosePeriodLabel = new JLabel("교시 선택");
        choosePeriodLabel.setFont(new Font("굴림", Font.PLAIN, 16));
        period_panel.add(choosePeriodLabel);

        // 교시 선택 라디오 버튼
        for (int i = 1; i <= 10; i++) {
            JRadioButton period_rdbtn = new JRadioButton(i + "교시");
            period_panel.add(period_rdbtn);
        }

        // 예약 버튼 올라가는 패널
        JPanel btnPanel = new JPanel();
        reserve_panel.add(btnPanel);
        btnPanel.setLayout(new BorderLayout(0, 0));

        // 예약 버튼
        JButton reserveBtn = new JButton("예약");
        reserveBtn.setFont(new Font("굴림", Font.PLAIN, 18));
        btnPanel.add(reserveBtn, BorderLayout.CENTER);

        // 검색 조건 패널(화면 상단)
        JPanel condition_panel = new JPanel();
        getContentPane().add(condition_panel, BorderLayout.NORTH);
        condition_panel.setLayout(new GridLayout(2, 2, 40, 10));

        // 건물 선택 패널
        JPanel building_panel = new JPanel();
        condition_panel.add(building_panel);
        building_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 40, 10));

        // '건물' 라벨
        JLabel buildingLabel = new JLabel("건물");
        building_panel.add(buildingLabel);

        // 건물 라디오 버튼
        String[] buildings = {"아산공학관", "신공학관", "연구협력관", "포스코관"};
        ButtonGroup buildingGroup = new ButtonGroup();
        buildingRadioButtons = new JRadioButton[buildings.length];
        for (int i = 0; i < buildings.length; i++) {
            buildingRadioButtons[i] = new JRadioButton(buildings[i]);
            buildingGroup.add(buildingRadioButtons[i]);
            building_panel.add(buildingRadioButtons[i]);
        }

        // 달력 선택 패널
        JPanel calendar_panel = new JPanel();
        condition_panel.add(calendar_panel);
        calendar_panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 40, 10));

        // '예약 날짜' 라벨
        JLabel dateLabel = new JLabel("예약 날짜");
        calendar_panel.add(dateLabel);

        // JCalendar 추가
        calendar = new JCalendar();
        calendar.setPreferredSize(new Dimension(200, 200));
        calendar_panel.add(calendar);

        // 검색 조건들 패널
        JPanel other_conditions_panel = new JPanel();
        condition_panel.add(other_conditions_panel);
        other_conditions_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 40, 10));

        // 수용인원 패널
        JPanel capacity_panel = new JPanel();
        other_conditions_panel.add(capacity_panel);

        // 수용인원 체크박스
        capacityCheckBox = new JCheckBox("최소 수용 인원");
        capacity_panel.add(capacityCheckBox);

        // 수용인원 입력받는 텍스트 필드
        capacityTextField = new JTextField();
        capacity_panel.add(capacityTextField);
        capacityTextField.setColumns(5);

        // 콘센트 개수 패널
        JPanel plug_panel = new JPanel();
        other_conditions_panel.add(plug_panel);

        // 콘센트 개수 체크박스
        plugCheckBox = new JCheckBox("최소 콘센트 개수");
        plug_panel.add(plugCheckBox);

        // 콘센트 개수 콤보박스
        plugComboBox = new JComboBox<>();
        plugComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"1", "2", "3"}));
        plug_panel.add(plugComboBox);

        // 마이크 패널
        JPanel mic_panel = new JPanel();
        other_conditions_panel.add(mic_panel);

        // 마이크 여부 체크박스
        micCheckBox = new JCheckBox("마이크 여부");
        mic_panel.add(micCheckBox);

        // 빔 프로젝터 패널
        JPanel projector_panel = new JPanel();
        other_conditions_panel.add(projector_panel);

        // 빔 프로젝터 여부 체크박스
        projectorCheckBox = new JCheckBox("빔프로젝터 여부");
        projector_panel.add(projectorCheckBox);

        // 검색 버튼 패널
        JPanel search_button_panel = new JPanel();
        condition_panel.add(search_button_panel);
        search_button_panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 40, 10));

        // 검색 버튼
        JButton searchBtn = new JButton("검색");
        search_button_panel.add(searchBtn);

        // 검색 결과가 표 형태로 올라가는 패널(화면 중간)
        JPanel table_panel = new JPanel();
        getContentPane().add(table_panel, BorderLayout.CENTER);
        table_panel.setLayout(new BorderLayout(0, 0));

        // 표 모델 설정
        String[] columnNames = {"건물명", "강의실", "수용 인원", "콘센트 개수", "마이크 여부", "빔프로젝터 여부", "날짜", "교시별 예약 가능 여부"};
        tableModel = new DefaultTableModel(null, columnNames);

        table = new JTable(tableModel);
        table.setCellSelectionEnabled(false); // 셀 단위 선택 비활성화
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 단일 행 선택 모드로 설정
        table_panel.add(new JScrollPane(table), BorderLayout.CENTER);

        // 테이블의 너비를 조정
        table.getColumnModel().getColumn(0).setPreferredWidth(100); // 건물명 열 너비 조정
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // 강의실 열 너비 조정
        table.getColumnModel().getColumn(2).setPreferredWidth(100); // 수용 인원 열 너비 조정
        table.getColumnModel().getColumn(3).setPreferredWidth(100); // 콘센트 개수 열 너비 조정
        table.getColumnModel().getColumn(4).setPreferredWidth(100); // 마이크 여부 열 너비 조정
        table.getColumnModel().getColumn(5).setPreferredWidth(100); // 빔프로젝터 여부 열 너비 조정
        table.getColumnModel().getColumn(6).setPreferredWidth(100); // 날짜 열 너비 조정
        table.getColumnModel().getColumn(7).setPreferredWidth(300); // 교시별 예약 가능 여부 열 너비 조정

        // 검색 버튼에 액션 리스너 추가
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 검색 조건을 기반으로 데이터를 가져오는 로직
                fetchSearchResults();
            }
        });

        // 스크롤바 추가
        Scrollbar scrollbar = new Scrollbar();
        table_panel.add(scrollbar, BorderLayout.EAST);
    }

    private void fetchSearchResults() {
        String building = null;
        for (JRadioButton btn : buildingRadioButtons) {
            if (btn.isSelected()) {
                building = btn.getText();
                break;
            }
        }
        if (building == null) {
            JOptionPane.showMessageDialog(this, "건물을 선택하세요.", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        java.util.Date selectedDate = calendar.getDate();
        java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());

        int capacity = capacityCheckBox.isSelected() ? Integer.parseInt(capacityTextField.getText()) : 0;
        int plugCount = plugCheckBox.isSelected() ? Integer.parseInt((String) plugComboBox.getSelectedItem()) : 0;
        boolean hasMic = micCheckBox.isSelected();
        boolean hasProjector = projectorCheckBox.isSelected();

        String query = "SELECT c.building, c.room_num, c.capacity, c.plug_count, c.hasMic, c.hasProjector, r.date, r.period " +
                       "FROM db2024_Classroom c " +
                       "LEFT JOIN db2024_Reservation r ON c.room_id = r.room_id " +
                       "AND (r.date IS NULL OR r.date = ?) " +
                       "WHERE c.building = ? AND c.capacity >= ? AND c.plug_count >= ? " +
                       (hasMic ? "AND c.hasMic = 1 " : "") +
                       (hasProjector ? "AND c.hasProjector = 1 " : "");

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDate(1, sqlDate);
            pstmt.setString(2, building);
            pstmt.setInt(3, capacity);
            pstmt.setInt(4, plugCount);

            ResultSet rs = pstmt.executeQuery();
            tableModel.setRowCount(0); // Clear existing results

            // Map to hold room info and periods
            Map<String, String> roomPeriodMap = new HashMap<>();

            while (rs.next()) {
                String buildingName = rs.getString("building");
                String roomNum = rs.getString("room_num");
                int roomCapacity = rs.getInt("capacity");
                int roomPlugCount = rs.getInt("plug_count");
                boolean roomHasMic = rs.getBoolean("hasMic");
                boolean roomHasProjector = rs.getBoolean("hasProjector");
                int period = rs.getInt("period");

                String key = buildingName + "-" + roomNum;
                String periodInfo = roomPeriodMap.getOrDefault(key, "1:o, 2:o, 3:o, 4:o, 5:o, 6:o, 7:o, 8:o, 9:o, 10:o");

                if (period > 0) {
                    periodInfo = periodInfo.replace(period + ":o", period + ":x");
                }
                roomPeriodMap.put(key, periodInfo);

                // If this is the first time we encounter this room, add the static info
                if (!roomPeriodMap.containsKey(key + "-info")) {
                    tableModel.addRow(new Object[]{buildingName, roomNum, roomCapacity, roomPlugCount, roomHasMic ? "예" : "아니오",
                                                   roomHasProjector ? "예" : "아니오", sqlDate.toString(), periodInfo});
                    roomPeriodMap.put(key + "-info", periodInfo);  // Mark this room as added
                } else {
                    // Update the period info for this room
                    for (int row = 0; row < tableModel.getRowCount(); row++) {
                        if (tableModel.getValueAt(row, 0).equals(buildingName) && tableModel.getValueAt(row, 1).equals(roomNum)) {
                            tableModel.setValueAt(periodInfo, row, 7);  // Update the period info
                            break;
                        }
                    }
                }
            }

            // Ensure all classrooms are included even if they have no reservations
            String classroomQuery = "SELECT building, room_num, capacity, plug_count, hasMic, hasProjector " +
                                    "FROM db2024_Classroom WHERE building = ? AND capacity >= ? AND plug_count >= ? " +
                                    (hasMic ? "AND hasMic = 1 " : "") + (hasProjector ? "AND hasProjector = 1 " : "");
            try (PreparedStatement classroomStmt = conn.prepareStatement(classroomQuery)) {
                classroomStmt.setString(1, building);
                classroomStmt.setInt(2, capacity);
                classroomStmt.setInt(3, plugCount);
                ResultSet classroomRs = classroomStmt.executeQuery();

                while (classroomRs.next()) {
                    String buildingName = classroomRs.getString("building");
                    String roomNum = classroomRs.getString("room_num");
                    int roomCapacity = classroomRs.getInt("capacity");
                    int roomPlugCount = classroomRs.getInt("plug_count");
                    boolean roomHasMic = classroomRs.getBoolean("hasMic");
                    boolean roomHasProjector = classroomRs.getBoolean("hasProjector");

                    String key = buildingName + "-" + roomNum;
                    if (!roomPeriodMap.containsKey(key)) {
                        String periodInfo = "1:o, 2:o, 3:o, 4:o, 5:o, 6:o, 7:o, 8:o, 9:o, 10:o";
                        tableModel.addRow(new Object[]{buildingName, roomNum, roomCapacity, roomPlugCount, roomHasMic ? "예" : "아니오",
                                                       roomHasProjector ? "예" : "아니오", sqlDate.toString(), periodInfo});
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
