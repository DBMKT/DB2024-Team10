package main.java.DB2024Team10.GUI.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import main.java.DB2024Team10.DAO.ReservationDAOImpl;
import main.java.DB2024Team10.DTO.ReservationDTO;
import main.java.DB2024Team10.util.JdbcUtil;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

public class Search extends JFrame {

	private JTextField capacityTextField;
	private JTable table;
	private JDateChooser dateChooser;
	private DefaultTableModel tableModel;
    private JComboBox<String> plugComboBox;
    private JRadioButton[] building_rdbtn;
    private JCheckBox buildingCheckBox, capacityCheckBox, plugCheckBox, micCheckBox, projectorCheckBox;
    

	public Search() {
		initialize();
	}

	private void initialize() {
		setTitle("예약 검색");
		setSize(1000, 500); // 창 크기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 튜플 선택 후 교시 선택하는 패널(화면 하단)
		JPanel reserve_panel = new JPanel();
		reserve_panel.setBorder(new TitledBorder(null, "예약하기", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
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
		
		 //교시 선택 라디오 버튼
		JRadioButton[] period_rdbtn=new JRadioButton[10];//배열로 수정
		for (int i = 1; i <= 10; i++) {
			period_rdbtn[i-1] = new JRadioButton(); //초기화
			period_rdbtn[i-1].setText(i + "교시"); //rdbtn[0]=1교시
			period_panel.add(period_rdbtn[i-1]);

			// 각 교시 라디오 버튼에 액션 리스너 추가
			period_rdbtn[i - 1].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JRadioButton selectedButton = (JRadioButton) e.getSource();
					if (selectedButton.isSelected()) {
						for (int j = 0; j < period_rdbtn.length; j++) {
							if (period_rdbtn[j] != selectedButton) {
								period_rdbtn[j].setSelected(false);
							}
						}
					}
				}

		});
	}
		
		// 예약 버튼 올라가는 패널
		JPanel btnPanel = new JPanel();
		reserve_panel.add(btnPanel);
		btnPanel.setLayout(new BorderLayout(0, 0));

		// 예약 버튼
		JButton reserveBtn = new JButton("예약");
		reserveBtn.setFont(new Font("굴림", Font.PLAIN, 18));
		reserveBtn.setPreferredSize(new Dimension(100,20));
		btnPanel.add(reserveBtn, BorderLayout.EAST);
		
		// 검색 조건 패널(화면 상단)
		JPanel condition_panel = new JPanel();
		condition_panel.setForeground(new Color(0, 0, 0));
		getContentPane().add(condition_panel, BorderLayout.NORTH);
		condition_panel.setLayout(new GridLayout(0, 1, 20, 0));
		
		// 건물, 날짜 패널
		JPanel condition_panel1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) condition_panel1.getLayout();
		flowLayout_1.setVgap(0);
		condition_panel.add(condition_panel1);
		
		// 기타 검색 조건 및 검색 버튼 패널
		JPanel condition_panel2 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) condition_panel2.getLayout();
		flowLayout_2.setVgap(0);
		condition_panel.add(condition_panel2);

		// 건물 선택 패널
		JPanel building_panel = new JPanel();
		condition_panel1.add(building_panel);
		building_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 15));
		
		// 건물 선택 체크박스
		buildingCheckBox = new JCheckBox("건물");
		building_panel.add(buildingCheckBox);

		// 건물 라디오 버튼
		String[] buildings = {"아산공학관", "신공학관", "연구협력관", "포스코관"};
		building_rdbtn=new JRadioButton[4];//배열로 수정
		for (int i=0;i<4;i++) {
			building_rdbtn[i]=new JRadioButton(buildings[i]);
			building_panel.add(building_rdbtn[i]);
		}

		// 달력 선택 패널
		JPanel calendar_panel = new JPanel();
		condition_panel1.add(calendar_panel);
		calendar_panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 15));
		
		JLabel dateLabel = new JLabel("(필수) 예약 날짜");
		calendar_panel.add(dateLabel);

		// JdateChooser 추가(예약 날짜 선택용): dateCheckBox 선택 시 활성화되도록 해야함
		dateChooser = new JDateChooser();
		dateChooser.setPreferredSize(new Dimension(100, 20));
		calendar_panel.add(dateChooser);

		// 검색 조건들 패널
		JPanel other_conditions_panel = new JPanel();
		condition_panel2.add(other_conditions_panel);
		other_conditions_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 40, 0));

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
		plugComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"1", "2", "3", "4", "5", "6", "7", "8"}));
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
		condition_panel2.add(search_button_panel);
		search_button_panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 40, 10));

		// 검색 버튼
		JButton searchBtn = new JButton("검색");
		searchBtn.setFont(new Font("굴림", Font.PLAIN, 18));
		searchBtn.setPreferredSize(new Dimension(100,30));
		search_button_panel.add(searchBtn);

		// 검색 결과가 표 형태로 올라가는 패널(화면 중간)
		JPanel table_panel = new JPanel();
		getContentPane().add(table_panel, BorderLayout.CENTER);
		table_panel.setLayout(new BorderLayout(0, 0));

		// 표 모델 설정
		String[] columnNames = {"건물명", "강의실", "수용 인원", "콘센트 개수", "마이크 여부", "빔프로젝터 여부", "날짜", "교시"};
		tableModel = new DefaultTableModel(null, columnNames);

		table = new JTable(tableModel);
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
		table.getColumnModel().getColumn(7).setPreferredWidth(300); // 교시 열 너비 조정

		// 표에서 항목 선택시
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) {
						// 선택된 행에서 건물명과 강의실 정보 가져오기
						String buildingName = (String) table.getValueAt(selectedRow, 0);
						String roomNum = (String) table.getValueAt(selectedRow, 1);

						// 선택 정보 라벨에 건물명과 강의실 정보 설정하기
						selectedLabel.setText(buildingName + " - " + roomNum);

						String reservedPeriod = (String) table.getValueAt(selectedRow, 7);

						// 선택된 행의 교시 값에 따라 교시 라디오 버튼 설정하기
						for (int i = 0; i < period_rdbtn.length; i++) {
							String period = (i + 1) + ":o";
							String periodText = (i + 1) + "교시";
							if (reservedPeriod.contains(period)) {
								period_rdbtn[i].setEnabled(true);
							} else {
								period_rdbtn[i].setEnabled(false);
								period_rdbtn[i].setSelected(false);
							}
						}
					}
				}
			}
		});


		// 검색 버튼에 액션 리스너 추가
		searchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (dateChooser.getDate() == null) {
					JOptionPane.showMessageDialog(Search.this, "예약 날짜를 선택해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
					return; // 날짜를 선택하지 않은 경우 메소드 종료
				}
				// 검색 조건을 기반으로 데이터를 가져오는 로직
				fetchSearchResults();
			}
		});

		// 예약 버튼 액션 리스너에서 선택된 정보 가져오기
		reserveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 선택된 행 인덱스 가져오기
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(Search.this, "강의실을 선택하세요.", "오류", JOptionPane.ERROR_MESSAGE);
					return;
				}

				// 선택된 행에서 건물명과 강의실 정보 가져오기
				String buildingName = (String) table.getValueAt(selectedRow, 0);
				String roomNum = (String) table.getValueAt(selectedRow, 1);

				// 선택된 행에서 다른 정보들 가져오기
				int capacity = (int) table.getValueAt(selectedRow, 2);
				int plugCount = (int) table.getValueAt(selectedRow, 3);
				String hasMic = (String) table.getValueAt(selectedRow, 4);
				String hasProjector = (String) table.getValueAt(selectedRow, 5);
				String reservedDate = (String) table.getValueAt(selectedRow, 6);
				String reservedPeriod = (String) table.getValueAt(selectedRow, 7);

				// 선택된 교시 정보 가져오기
				String selectedPeriod = null;
				for (int i = 0; i < period_rdbtn.length; i++) {
					if (period_rdbtn[i].isSelected()) {
						selectedPeriod = period_rdbtn[i].getText();
						break;
					}
				}

				if (selectedPeriod == null) {
					JOptionPane.showMessageDialog(Search.this, "교시를 선택하세요.", "오류", JOptionPane.ERROR_MESSAGE);
					return;
				}

				// 여기서 선택된 정보와 선택된 교시를 사용하여 예약 로직을 처리할 수 있습니다.
				// 여기에 필요한 예약 처리 코드를 추가하세요.

				// 예약할 정보와 선택된 교시를 출력하여 확인합니다.
				System.out.println("예약 정보:");
				System.out.println("건물명: " + buildingName);
				System.out.println("강의실: " + roomNum);
				System.out.println("수용 인원: " + capacity);
				System.out.println("콘센트 개수: " + plugCount);
				System.out.println("마이크 여부: " + hasMic);
				System.out.println("빔프로젝터 여부: " + hasProjector);
				System.out.println("날짜: " + reservedDate);
				System.out.println("교시: " + reservedPeriod);
				System.out.println("선택한 교시: " + selectedPeriod);
				
				sendSearchInfo(buildingName,roomNum,reservedDate, selectedPeriod);//정보 보내기
			}
		});

		//스크롤바 추가
		Scrollbar scrollbar = new Scrollbar();
		table_panel.add(scrollbar, BorderLayout.EAST);
	}

	// 검색 조건을 기반으로 데이터를 가져오는 함수
	private void fetchSearchResults() {
		List<String> selectedBuildings = new ArrayList<>(); // 선택된 강의 건물 목록

		if (buildingCheckBox.isSelected()) {
			boolean flag = false;
			// 선택된 건물들을 리스트에 추가
			for (JRadioButton radioButton : building_rdbtn) {
				if (radioButton.isSelected()) {
					flag = true;
					selectedBuildings.add(radioButton.getText());
				}
			}
			if(!flag) {
				System.out.println("디버깅");
				selectedBuildings.clear();
				for(JRadioButton radioButton : building_rdbtn) {
					selectedBuildings.add(radioButton.getText());
				}
			}
		} else {
			// 건물 선택 체크박스가 선택되지 않은 경우에는 모든 건물을 선택한 것으로 처리
			selectedBuildings.clear(); // 선택된 건물 리스트를 비움
			for (JRadioButton radioButton : building_rdbtn) {
				selectedBuildings.add(radioButton.getText());
			}
		}
		System.out.println(selectedBuildings+ " ");
				 
		// 예외 처리
		if(selectedBuildings.isEmpty()) {
			JOptionPane.showMessageDialog(this, "건물을 선택해주세요", "오류", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// 선택한 날짜 (필수)
		java.util.Date selectedDate = dateChooser.getDate();
		java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());

		// 수용인원
		   int capacity = 0;
		   // 수용인원을 조건으로 선택한 경우
		   if (capacityCheckBox.isSelected()) {
		        String capacityText = capacityTextField.getText().trim();
		        if (capacityText.isEmpty()) {
		            JOptionPane.showMessageDialog(this, "수용 인원을 입력해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		        try {
		            capacity = Integer.parseInt(capacityText);
		        } catch (NumberFormatException e) {
		            JOptionPane.showMessageDialog(this, "수용 인원은 숫자로 입력해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		    }
		// 콘센트 개수
		    int plugCount = 0;
		   // 콘센트 개수를 조건으로 선택한 경우
		    if (plugCheckBox.isSelected()) {
		        String plugText = (String) plugComboBox.getSelectedItem();
		        try {
		            plugCount = Integer.parseInt(plugText);
		        } catch (NumberFormatException e) {
		            JOptionPane.showMessageDialog(this, "콘센트 개수는 숫자로 입력해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		    }
		// 마이크 존재 여부
		    boolean hasMic = micCheckBox.isSelected();
		// 빔 프로젝터 존재 여부
			boolean hasProjector = projectorCheckBox.isSelected();

			// 예약, 강의 테이블에 해당 강의실이 존재하는 경우
			// 해당 조건을 만족하는 강의실의 정보를 불러오기 위한 쿼리
			// c.building, c.room_num, c.capacity, c.plug_count, c.hasMic, c.hasProjector: 조건을 만족하는 강의실 정보
			// r.reserved_date, r.reserved_period: 해당 강의실의 예약 정보
			// l.day1_of_week, l.period1, l.day2_of_week, l.period2: 해당 강의실의 강의 정보
			// 예약 가능한 강의실을 조회하는 쿼리문에 Reservation 테이블에서
			// 예약자의 학번, 이름 등의 개인정보와 조회에 불필요한 속성을 제외한 뷰 ‘DB2024_ReservationView’를 이용
			// 해당 강의실의 강의 내역을 빠르게 반환하기 위해 idx_lecture를 사용
			// 선택한 건물명, 수용 인원에 맞는 예약 내역을 빠르게 반환하기 위해 idx_classroom을 사용
		    String query = "SELECT c.building, c.room_num, c.capacity, c.plug_count, c.hasMic, c.hasProjector, " +
	                   "r.reserved_date, r.reserved_period, l.day1_of_week, l.period1, l.day2_of_week, l.period2 " +
	                   "FROM db2024_Classroom c USE INDEX(idx_classroom) " +
	                   "LEFT JOIN db2024_ReservationView r ON c.room_id = r.room_id " +
	                   "AND (r.reserved_date IS NULL OR r.reserved_date = ?) " +
	                   "LEFT JOIN db2024_Lecture l USE INDEX(idx_lecture) ON c.room_id = l.room_id " +
	                   "WHERE c.building IN (" + String.join(",", Collections.nCopies(selectedBuildings.size(), "?")) + ") " +
	                   "AND c.capacity >= ? AND c.plug_count >= ? " +
	                   (hasMic ? "AND c.hasMic = 1 " : "") +
	                   (hasProjector ? "AND c.hasProjector = 1 " : "");

	    try (Connection conn = JdbcUtil.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) {

	        int paramIndex = 1;
	        pstmt.setDate(paramIndex++, sqlDate); // 선택한 날짜
	        for (String building : selectedBuildings) {
	            pstmt.setString(paramIndex++, building); // 선택한 건물
	        }
	        pstmt.setInt(paramIndex++, capacity); // 선택한 수용 인원
	        pstmt.setInt(paramIndex++, plugCount); // 선택한 콘센트 수

	        ResultSet rs = pstmt.executeQuery();
			tableModel.setRowCount(0); // 테이블 선택 리셋

			// 강의실 정보와 교시 정보를 매핑
			Map<String, String> roomPeriodMap = new HashMap<>();

			while (rs.next()) {
				// select 결과 받아오기
				String buildingName = rs.getString("building");
				String roomNum = rs.getString("room_num");
				int roomCapacity = rs.getInt("capacity");
				int roomPlugCount = rs.getInt("plug_count");
				boolean roomHasMic = rs.getBoolean("hasMic");
				boolean roomHasProjector = rs.getBoolean("hasProjector");
				int period = rs.getInt("reserved_period");
				int day1OfWeek = rs.getInt("day1_of_week");
				int period1 = rs.getInt("period1");
				int day2OfWeek = rs.getInt("day2_of_week");
				int period2 = rs.getInt("period2");

				// 건물 - 호실
				String key = buildingName + "-" + roomNum;
				// 교시별 예약 정보: 기본 o로 설정
				String periodInfo = roomPeriodMap.getOrDefault(key, "1:o, 2:o, 3:o, 4:o, 5:o, 6:o, 7:o, 8:o, 9:o, 10:o");

				// 이미 예약된 교시가 있으면 x 처리 (예약 테이블값)
				if (period > 0) {
					periodInfo = periodInfo.replace(period + ":o", period + ":x");
				}

				// 현재 날짜의 요일을 계산하여 강의와 겹치는지 확인
				Calendar cal = Calendar.getInstance();
				cal.setTime(sqlDate);
				int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
				// 1: 일요일, 2: 월요일, ..., 7: 토요일 이라
				// 1: 월요일, 2: 화요일, ..., 5: 금요일로 바꿔줌

				// 강의에 사용되는 교시가 있으면 x 처리 (강의 테이블값)
				if (dayOfWeek == day1OfWeek && period1 > 0) {
					periodInfo = periodInfo.replace(period1 + ":o", period1 + ":x");
				}
				if (dayOfWeek == day2OfWeek && period2 > 0) {
					periodInfo = periodInfo.replace(period2 + ":o", period2 + ":x");
				}

				roomPeriodMap.put(key, periodInfo);

				// roomPeriodMap에 아무것도 없는 초기 상태일 때
				if (!roomPeriodMap.containsKey(key + "-info")) {
					tableModel.addRow(new Object[]{buildingName, roomNum, roomCapacity, roomPlugCount, roomHasMic ? "예" : "아니오",
							roomHasProjector ? "예" : "아니오", sqlDate.toString(), periodInfo});
					roomPeriodMap.put(key + "-info", periodInfo);  // Mark this room as added
				} else {
					// roomPeriodMap에 교시 정보가 업데이트 되었을 때
					for (int row = 0; row < tableModel.getRowCount(); row++) {
						// 검색 결과 테이블 업데이트
						if (tableModel.getValueAt(row, 0).equals(buildingName) && tableModel.getValueAt(row, 1).equals(roomNum)) {
							tableModel.setValueAt(periodInfo, row, 7);
							break;
						}
					}
				}
			}

			// 예약, 강의 테이블에 해당 강의실이 존재하지 않는 경우
			// 해당 조건을 만족하는 강의실의 정보를 불러오기 위한 쿼리
			String classroomQuery = "SELECT building, room_num, capacity, plug_count, hasMic, hasProjector " +
					"FROM db2024_Classroom WHERE building IN (" + String.join(",", Collections.nCopies(selectedBuildings.size(), "?")) + ") " +
					"AND capacity >= ? AND plug_count >= ? " +
					(hasMic ? "AND hasMic = 1 " : "") + (hasProjector ? "AND hasProjector = 1 " : "");
			try (PreparedStatement classroomStmt = conn.prepareStatement(classroomQuery)) {
				paramIndex = 1;
				for (String building : selectedBuildings) {
					classroomStmt.setString(paramIndex++, building); // 선택한 건물
				}
				classroomStmt.setInt(2, capacity); // 선택한 수용 인원
				classroomStmt.setInt(3, plugCount); // 선택한 콘센트 수
				ResultSet classroomRs = classroomStmt.executeQuery();

				while (classroomRs.next()) {
					// select 결과 받아오기
					String buildingName = classroomRs.getString("building");
					String roomNum = classroomRs.getString("room_num");
					int roomCapacity = classroomRs.getInt("capacity");
					int roomPlugCount = classroomRs.getInt("plug_count");
					boolean roomHasMic = classroomRs.getBoolean("hasMic");
					boolean roomHasProjector = classroomRs.getBoolean("hasProjector");

					// 건물 - 호실
					String key = buildingName + "-" + roomNum;
					if (!roomPeriodMap.containsKey(key)) {
						// 교시별 예약 정보: 기본 o로 설정
						String periodInfo = "1:o, 2:o, 3:o, 4:o, 5:o, 6:o, 7:o, 8:o, 9:o, 10:o";
						tableModel.addRow(new Object[]{buildingName, roomNum, roomCapacity, roomPlugCount, roomHasMic ? "예" : "아니오",
								roomHasProjector ? "예" : "아니오", sqlDate.toString(), periodInfo});
					}
				}
			}

		} catch (SQLException e) {
			//e.printStackTrace();
		}
	}

	public void sendSearchInfo(String buildingName, String roomNum,String reservedDate, String selectedPeriod) { //Reserve로 보내기   	
    	//Reserve로 넘겨줄 값들 변환
		//2: room_id
    	long room_id=findRoomId(buildingName,roomNum);
  
    	//8: reserved_date(예약 날짜)
    	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    	Date date;
		try {//String->date
			date = format.parse(reservedDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		char firstChar = selectedPeriod.charAt(0);
		int reserved_period = firstChar-'0';	
		//System.out.println("Search 수행 완료");

		dispose();
        new Reserve(room_id,date,reserved_period).setVisible(true);//Reserve 창 생성하면서 넘겨주기
	
	}
	
	// building, roomNum 알고 있음 -> classroom table에서 roomId 찾아야함
	public long findRoomId(String buildingName, String roomNum) {
	    long roomId = 0;

	    String query = "SELECT room_id FROM db2024_Classroom WHERE building=? and room_num = ?";

	    try (Connection conn = JdbcUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(query)) {

	        // building
	        pstmt.setString(1, buildingName);
	        // room_num
	        pstmt.setString(2, roomNum);

	        // 쿼리 실행
	        ResultSet rs = pstmt.executeQuery();

	        // 결과가 있을 경우 roomId 변수에 할당
	        if (rs.next()) {
	            roomId = rs.getLong("room_id");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "데이터베이스 쿼리 실행 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
	    }

	    return roomId;
	}

	
	
    }