package main.java.com.example.team10.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

public class Search extends JFrame {

	private JTextField capacityTextField;
	private JTable table;
	//private JCalendar calendar;
	private JDateChooser dateChooser;
	private DefaultTableModel tableModel;

	/**
	 * Launch the application.
	 */
//	 public static void main(String[] args) { // 이 화면만 실행해볼 시 주석 해제
//	 	EventQueue.invokeLater(new Runnable() {
//	 		public void run() {
//	 			try {
//	 				Search window = new Search();
//	 				window.setVisible(true);
//	 			} catch (Exception e) {
//	 				e.printStackTrace();
//	 			}
//	 		}
//	 	});
//	 }

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
			period_rdbtn[i-1].setText(i + "교시");//rdbtn[0]=1교시
			period_panel.add(period_rdbtn[i-1]);
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
		JCheckBox buildingCheckBox = new JCheckBox("건물");
		building_panel.add(buildingCheckBox);

		// 건물 라디오 버튼
		String[] buildings = {"아산공학관", "신공학관", "연구협력관", "포스코관"};
		JRadioButton[] building_rdbtn=new JRadioButton[4];//배열로 수정
		for (int i=0;i<4;i++) {
			building_rdbtn[i]=new JRadioButton(buildings[i]);
			building_panel.add(building_rdbtn[i]);
		}

		// 달력 선택 패널
		JPanel calendar_panel = new JPanel();
		condition_panel1.add(calendar_panel);
		calendar_panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 15));
		
		JLabel dateLabel = new JLabel("\uC608\uC57D \uB0A0\uC9DC");
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
		JCheckBox capacityCheckBox = new JCheckBox("최소 수용 인원");
		capacity_panel.add(capacityCheckBox);

		// 수용인원 입력받는 텍스트 필드
		capacityTextField = new JTextField();
		capacity_panel.add(capacityTextField);
		capacityTextField.setColumns(5);

		// 콘센트 개수 패널
		JPanel plug_panel = new JPanel();
		other_conditions_panel.add(plug_panel);

		// 콘센트 개수 체크박스
		JCheckBox plugCheckBox = new JCheckBox("최소 콘센트 개수");
		plug_panel.add(plugCheckBox);

		// 콘센트 개수 콤보박스
		JComboBox<String> plugComboBox = new JComboBox<>();
		plugComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"1", "2", "3"}));
		plug_panel.add(plugComboBox);

		// 마이크 패널
		JPanel mic_panel = new JPanel();
		other_conditions_panel.add(mic_panel);

		// 마이크 여부 체크박스
		JCheckBox micCheckBox = new JCheckBox("마이크 여부");
		mic_panel.add(micCheckBox);

		// 빔 프로젝터 패널
		JPanel projector_panel = new JPanel();
		other_conditions_panel.add(projector_panel);

		// 빔 프로젝터 여부 체크박스
		JCheckBox projectorCheckBox = new JCheckBox("빔프로젝터 여부");
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
		table.getColumnModel().getColumn(7).setPreferredWidth(300); // 교시 열 너비 조정

		// 검색 버튼에 액션 리스너 추가
		searchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 검색 조건을 기반으로 데이터를 가져오는 로직을 추가
				// 현재는 더미 데이터를 사용하여 예시를 보여줌

				// 더미 데이터 설정
				Object[][] data = {
						{"아산공학관", "101호", 30, 1, "예", "아니오", "2023-05-27", "1:o, 2:o, 3:x, 4:o, 5:o, 6:x, 7:x, 8:o, 9:o, 10:o"},
						{"신공학관", "202호", 25, 2, "아니오", "예", "2023-05-28", "1:o, 2:o, 3:o, 4:x, 5:o, 6:o, 7:x, 8:o, 9:o, 10:o"},
						{"연구협력관", "303호", 35, 1, "예", "아니오", "2023-05-29", "1:o, 2:o, 3:o, 4:o, 5:x, 6:o, 7:o, 8:o, 9:o, 10:o"},
						{"포스코관", "404호", 28, 2, "아니오", "예", "2023-05-30", "1:o, 2:o, 3:o, 4:o, 5:o, 6:o, 7:o, 8:o, 9:x, 10:o"}
				};

				// 테이블 모델 업데이트
				tableModel.setDataVector(data, columnNames);
			}
		});

		//스크롤바 추가
		Scrollbar scrollbar = new Scrollbar();
		table_panel.add(scrollbar, BorderLayout.EAST);
	}
}
