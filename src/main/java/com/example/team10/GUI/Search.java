package main.java.com.example.team10.GUI;

import java.awt.*;

import javax.swing.*;

public class Search extends JFrame{

	private JTextField capacityTextField;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {//화면 실행
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
		setSize(1000,500); //창 크기
		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//튜플 선택 후 교시 선택하는 패널(화면 하단)
		JPanel reserve_panel = new JPanel();
		reserve_panel.setSize(900,100);
		getContentPane().add(reserve_panel, BorderLayout.SOUTH);
		reserve_panel.setLayout(new GridLayout(3, 1, 0, 0));
		
		//선택 정보 패널
		JPanel select_panel = new JPanel();
		FlowLayout fl_select_panel = (FlowLayout) select_panel.getLayout();
		fl_select_panel.setVgap(3);
		fl_select_panel.setAlignment(FlowLayout.LEADING);
		fl_select_panel.setHgap(15);
		reserve_panel.add(select_panel);
		
		//'선택 정보' 라벨
		JLabel selectInfoLabel = new JLabel("\uC120\uD0DD \uC815\uBCF4 :");
		selectInfoLabel.setFont(new Font("굴림", Font.PLAIN, 16));
		select_panel.add(selectInfoLabel);
		
		//'강의실을 선택하세요' 라벨->튜플 선택 시 안에 메시지가 선택한 건물 강의실명으로 변경되어야함 ex) 아산공학관 101호
		JLabel selectedLabel = new JLabel("\uAC15\uC758\uC2E4\uC744 \uC120\uD0DD\uD558\uC138\uC694.");
		selectedLabel.setFont(new Font("굴림", Font.PLAIN, 16));
		select_panel.add(selectedLabel);
		
		//교시 선택 패널
		JPanel period_panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) period_panel.getLayout();
		flowLayout.setVgap(3);
		flowLayout.setHgap(15);
		flowLayout.setAlignment(FlowLayout.LEADING);
		reserve_panel.add(period_panel);
		
		//'교시 선택' 라벨
		JLabel choosePeriodLabel = new JLabel("\uAD50\uC2DC \uC120\uD0DD");
		choosePeriodLabel.setFont(new Font("굴림", Font.PLAIN, 16));
		period_panel.add(choosePeriodLabel);
		
		//교시 선택 라디오 버튼(중복 선택 가능)
		JRadioButton period_rdbtn1 = new JRadioButton("1\uAD50\uC2DC");
		period_panel.add(period_rdbtn1);
		
		JRadioButton period_rdbtn2 = new JRadioButton("2\uAD50\uC2DC");
		period_panel.add(period_rdbtn2);
		
		JRadioButton period_rdbtn3 = new JRadioButton("3\uAD50\uC2DC");
		period_panel.add(period_rdbtn3);
		
		JRadioButton period_rdbtn4 = new JRadioButton("4\uAD50\uC2DC");
		period_panel.add(period_rdbtn4);
		
		JRadioButton period_rdbtn5 = new JRadioButton("5\uAD50\uC2DC");
		period_panel.add(period_rdbtn5);
		
		JRadioButton period_rdbtn6 = new JRadioButton("6\uAD50\uC2DC");
		period_panel.add(period_rdbtn6);
		
		JRadioButton period_rdbtn7 = new JRadioButton("7\uAD50\uC2DC");
		period_panel.add(period_rdbtn7);
		
		JRadioButton period_rdbtn8 = new JRadioButton("8\uAD50\uC2DC");
		period_panel.add(period_rdbtn8);
		
		JRadioButton period_rdbtn9 = new JRadioButton("9\uAD50\uC2DC");
		period_panel.add(period_rdbtn9);
		
		JRadioButton period_rdbtn10 = new JRadioButton("10\uAD50\uC2DC");
		period_panel.add(period_rdbtn10);
		
		//예약 버튼 올라가는 패널
		JPanel btnPanel = new JPanel();
		reserve_panel.add(btnPanel);
		btnPanel.setLayout(new BorderLayout(0, 0));
		
		//예약 버튼: 튜플 선택해야 활성화(아직 구현 안 함)
		JButton reserveBtn = new JButton("\uC608\uC57D");
		reserveBtn.setFont(new Font("굴림", Font.PLAIN, 18));
		btnPanel.add(reserveBtn, BorderLayout.CENTER);
		
		
		
		//검색 조건 패널(화면 상단)
		Panel condition_panel = new Panel();
		getContentPane().add(condition_panel, BorderLayout.NORTH);
		condition_panel.setLayout(new GridLayout(2, 1, 0, 0));
		
		//건물 선택. 예약 날짜 선택 패널
		JPanel select_conditions_panel1 = new JPanel();
		condition_panel.add(select_conditions_panel1);
		select_conditions_panel1.setLayout(new GridLayout(1, 3, 0, 0));
		
		//'건물' 및 건물 라디오 버튼 올라가는 건물 패널
		JPanel building_panel = new JPanel();
		select_conditions_panel1.add(building_panel);
		
		//'건물' 라벨
		JLabel buildingLabel = new JLabel("\uAC74\uBB3C");
		building_panel.add(buildingLabel);
		
		//건물 라디오 버튼(dataset에 현재 아산, 신공, 연협, 포관만 있어서 4개만 추가한 상태)
		JRadioButton asan_rdbtn = new JRadioButton("\uC544\uC0B0\uACF5\uD559\uAD00");
		building_panel.add(asan_rdbtn);
		
		JRadioButton newEng_rdbtn = new JRadioButton("\uC2E0\uACF5\uD559\uAD00");
		building_panel.add(newEng_rdbtn);
		
		JRadioButton research_rdbtn = new JRadioButton("\uC5F0\uAD6C\uD611\uB825\uAD00");
		building_panel.add(research_rdbtn);
		
		JRadioButton posco_rdbtn = new JRadioButton("\uD3EC\uC2A4\uCF54\uAD00");
		building_panel.add(posco_rdbtn);
		
		//달력 선택 패널(아직 JCalendar 미배치한 상태)
		JPanel calendar_panel = new JPanel();
		select_conditions_panel1.add(calendar_panel);
		
		//'예약 날짜' 라벨
		JLabel dateLabel = new JLabel("\uC608\uC57D \uB0A0\uC9DC");
		calendar_panel.add(dateLabel);
		
		//캘린더 추가 시 삭제해야하는 라벨
		JLabel tmpLabel = new JLabel("JCalendar \uB123\uC5B4\uC57C\uD568(\uB123\uC740 \uD6C4 \uC774 \uB77C\uBCA8 \uC0AD\uC81C)");
		calendar_panel.add(tmpLabel);
		
		//나머지 검색 조건+검색 버튼 올라가는 조건 패널
		JPanel select_conditions_panel2 = new JPanel();
		condition_panel.add(select_conditions_panel2);
		select_conditions_panel2.setLayout(new GridLayout(0, 1, 0, 0));
		
		//검색 요소들 올라가는 패널(사실상 select_conditions_panel2랑 비슷)
		JPanel platform_panel = new JPanel();
		select_conditions_panel2.add(platform_panel);
		platform_panel.setLayout(new GridLayout(0, 5, 0, 0));
		
		//수용인원 패널
		JPanel capacity_panel = new JPanel();
		platform_panel.add(capacity_panel);
		
		//수용인원 체크박스(체크 시 sql에 해당 조건 추가)
		JCheckBox capacityCheckBox = new JCheckBox("\uCD5C\uC18C \uC218\uC6A9 \uC778\uC6D0");
		capacity_panel.add(capacityCheckBox);
		
		//수용인원 입력받는 텍스트 필드. 수용인원 체크 시 활성화(아직 구현 안 함)
		capacityTextField = new JTextField();
		capacity_panel.add(capacityTextField);
		capacityTextField.setColumns(5);//미관상 5칸 입력 가능하게 하였으나 입력 예외처리 따로 추가해야함
		
		//콘센트 개수 패널
		JPanel plug_panel = new JPanel();
		platform_panel.add(plug_panel);
		
		//콘센트 개수 체크박스(체크 시 sql에 해당 조건 추가)
		JCheckBox plugCheckBox = new JCheckBox("\uCD5C\uC18C \uCF58\uC13C\uD2B8 \uAC1C\uC218");
		plug_panel.add(plugCheckBox);
		
		//콘센트 개수 콤보박스. 현재 0-3까지 넣어놓았음.
		JComboBox plugComboBox = new JComboBox();
		plug_panel.add(plugComboBox);
		plugComboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3"}));
		
		//마이크 패널
		JPanel mic_panel = new JPanel();
		platform_panel.add(mic_panel);
		
		//마이크 여부 체크박스(체크 시 sql에 해당 조건 추가)
		JCheckBox micCheckBox = new JCheckBox("\uB9C8\uC774\uD06C \uC720\uBB34");
		mic_panel.add(micCheckBox);
		
		//빔 프로젝터 패널
		JPanel projector_panel = new JPanel();
		platform_panel.add(projector_panel);
		
		//빔 프로젝터 여부 체크박스(체크 시 sql에 해당 조건 추가)
		JCheckBox projectorCheckBox = new JCheckBox("\uBE54\uD504\uB85C\uC81D\uD130 \uC720\uBB34");
		projector_panel.add(projectorCheckBox);
		
		//검색 버튼
		JButton searchBtn = new JButton("\uAC80\uC0C9");
		platform_panel.add(searchBtn);
		
		
		
		
		//검색 결과가 표 형태로 올라가는 패널(화면 중간)
		JPanel table_panel = new JPanel();
		getContentPane().add(table_panel, BorderLayout.CENTER);
		table_panel.setLayout(new BorderLayout(0, 0));
		
		//표(수정 필요)
		table = new JTable();
		table.setCellSelectionEnabled(true);
		table_panel.add(table, BorderLayout.SOUTH);
		
		//스크롤바 추가
		Scrollbar scrollbar = new Scrollbar();
		table_panel.add(scrollbar, BorderLayout.EAST);
	}
	
}
