import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

  // 사용자로부터 예약 조건을 선택받는 메소드
  public static List<Integer> getReservationConditions(Scanner scanner) {
    System.out.println("*****검색 조건*****");
    System.out.println("1. 건물");
    System.out.println("2. 수용인원");
    System.out.println("3. 콘센트 개수");
    System.out.println("4. 마이크 유무");
    System.out.println("5. 빔프로젝터 유무");
    System.out.println("6. 날짜");
    System.out.println("7. 교시");
    System.out.println("검색 조건을 선택하세요(7개 조건 중 0개 이상의 조건을 골라 숫자를 공백으로 구분하여 입력) : ");
    scanner.nextLine(); // 엔터키 입력 소모
    String[] conditionInputs = scanner.nextLine().split(" ");
    List<Integer> selectedConditions = new ArrayList<>();
    for (String input : conditionInputs) {
      selectedConditions.add(Integer.parseInt(input));
    }
    return selectedConditions;
  }

  // 선택된 조건에 맞는 옵션을 데이터베이스에서 가져와 사용자에게 선택받는 메소드
  public static Map<Integer, List<String>> getConditionOptions(Connection connection, Scanner scanner,
      List<Integer> selectedConditions) throws SQLException {
    Map<Integer, List<String>> selectedOptions = new HashMap<>();

    for (int condition : selectedConditions) {
      List<String> options = new ArrayList<>();
      switch (condition) {
        case 1:
          System.out.println("*****건물*****");
          String buildingQuery = "SELECT DISTINCT building FROM db2024_Classroom";
          try (Statement statement = connection.createStatement();
              ResultSet resultSet = statement.executeQuery(buildingQuery)) {
            int count = 1;
            while (resultSet.next()) {
              String building = resultSet.getString("building");
              System.out.println(count + ". " + building);
              options.add(building);
              count++;
            }
          }
          System.out.println("건물을 선택하세요(여러 건물 선택 시 번호를 공백으로 구분하여 입력) : ");
          scanner.nextLine(); // 엔터키 입력 소모
          String[] buildingSelections = scanner.nextLine().split(" ");
          List<String> selectedBuildings = new ArrayList<>();
          for (String selection : buildingSelections) {
            int index = Integer.parseInt(selection) - 1;
            selectedBuildings.add(options.get(index));
          }
          options = selectedBuildings;
          break;
        case 2:
          System.out.println("*****수용인원*****");
          String capacityQuery = "SELECT DISTINCT capacity FROM db2024_Classroom ORDER BY capacity";
          try (Statement statement = connection.createStatement();
              ResultSet resultSet = statement.executeQuery(capacityQuery)) {
            int count = 1;
            while (resultSet.next()) {
              String capacity = resultSet.getString("capacity");
              System.out.println(count + ". " + capacity);
              options.add(capacity);
              count++;
            }
          }
          System.out.println("최소 수용인원을 선택하세요(번호를 입력) : ");
          scanner.nextLine(); // 엔터키 입력 소모
          int capacitySelection = scanner.nextInt();
          options = Collections.singletonList(options.get(capacitySelection - 1));
          break;
        case 3:
          System.out.println("*****콘센트 개수*****");
          String outletsQuery = "SELECT DISTINCT outlets FROM db2024_Classroom ORDER BY outlets";
          try (Statement statement = connection.createStatement();
              ResultSet resultSet = statement.executeQuery(outletsQuery)) {
            int count = 1;
            while (resultSet.next()) {
              String outlets = resultSet.getString("outlets");
              System.out.println(count + ". " + outlets);
              options.add(outlets);
              count++;
            }
          }
          System.out.println("최소 콘센트 개수를 선택하세요(번호를 입력) : ");
          scanner.nextLine(); // 엔터키 입력 소모
          int outletsSelection = scanner.nextInt();
          options = Collections.singletonList(options.get(outletsSelection - 1));
          break;
        case 4:
          System.out.println("마이크 유무를 입력하세요 (true/false) : ");
          options.add(scanner.next());
          break;
        case 5:
          System.out.println("빔프로젝터 유무를 입력하세요 (true/false) : ");
          options.add(scanner.next());
          break;
        case 6:
          LocalDate currentDate = LocalDate.now();
          LocalDate maxDate = currentDate.plusDays(29);
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
          System.out.println("조회 시작 날짜를 입력하세요(YYYY-MM-DD형식으로 입력 ex. " + currentDate.format(formatter) + ", "
              + maxDate.format(formatter) + "까지 입력 가능) : ");
          options.add(scanner.next());
          System.out.println("조회 끝 날짜를 입력하세요(YYYY-MM-DD형식으로 입력 ex. " + currentDate.format(formatter) + ", "
              + maxDate.format(formatter) + "까지 입력 가능) : ");
          options.add(scanner.next());
          break;
        case 7:
          System.out.println("*****교시*****");
          System.out.println("1. 08:00-09:30");
          System.out.println("2. 09:30-11:00");
          System.out.println("3. 11:00-12:30");
          System.out.println("4. 12:30-14:00");
          System.out.println("5. 14:00-15:30");
          System.out.println("6. 15:30-17:00");
          System.out.println("7. 17:00-18:30");
          System.out.println("8. 18:30-20:00");
          System.out.println("9. 20:00-21:30");
          System.out.println("10. 21:30-23:00");
          System.out.println("교시를 선택하세요(여러 교시 선택 시 번호를 공백으로 구분하여 입력) : ");
          options.add(scanner.next());
          break;
      }
      selectedOptions.put(condition, options);
    }
    return selectedOptions;
  }

  // 선택된 조건에 맞는 예약 가능한 강의실을 검색하고 출력하는 메소드
  public static void searchAndDisplayAvailableRooms(Connection connection, List<Integer> selectedConditions,
      Map<Integer, List<String>> selectedOptions, Scanner scanner) throws SQLException {
    String query = "SELECT * FROM db2024_Classroom WHERE 1=1";
    String startDate = "";
    String endDate = "";
    List<Integer> timeslots = new ArrayList<>();

    // 선택된 조건에 따라 쿼리 구성
    for (Map.Entry<Integer, List<String>> entry : selectedOptions.entrySet()) {
      int condition = entry.getKey();
      List<String> options = entry.getValue();
      switch (condition) {
        case 1:
          query += " AND building IN (";
          for (String option : options) {
            query += "'" + option + "',";
          }
          query = query.substring(0, query.length() - 1) + ")";
          break;
        case 2:
          query += " AND capacity >= " + options.get(0);
          break;
        case 3:
          query += " AND outlets >= " + options.get(0);
          break;
        case 4:
          query += " AND mics = " + options.get(0);
          break;
        case 5:
          query += " AND projectors = " + options.get(0);
          break;
        case 6:
          startDate = options.get(0);
          endDate = options.get(1);
          break;
        case 7:
          for (String timeslot : options.get(0).split(" ")) {
            timeslots.add(Integer.parseInt(timeslot));
          }
          break;
      }
    }

    // 예약 가능한 강의실 목록을 가져옴
    List<String[]> availableRooms = new ArrayList<>();
    try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query)) {
      while (resultSet.next()) {
        String[] roomDetails = new String[7];
        roomDetails[0] = resultSet.getString("id"); // classroom_id
        roomDetails[1] = resultSet.getString("building");
        roomDetails[2] = resultSet.getString("room_num");
        roomDetails[3] = Integer.toString(resultSet.getInt("capacity"));
        roomDetails[4] = Integer.toString(resultSet.getInt("outlets"));
        roomDetails[5] = resultSet.getBoolean("mics") ? "O" : "X";
        roomDetails[6] = resultSet.getBoolean("projectors") ? "O" : "X";
        availableRooms.add(roomDetails);
      }
    }

    Map<Integer, String[]> roomSelectionMap = new HashMap<>();
    System.out.println("*****예약 가능 장소-시간*****");
    int count = 1;
    for (String[] room : availableRooms) {
      for (String date = startDate; !date.equals(getNextDate(endDate)); date = getNextDate(date)) {
        System.out.printf("%d. %s/%s/%s/%s/%s/%s/%s/", count, room[1], room[2], room[3], room[4], room[5], room[6],
            date);
        StringBuilder availability = new StringBuilder();
        for (int i = 1; i <= 10; i++) {
          // 사용자가 선택한 교시의 예약 가능 여부를 확인
          if (timeslots.contains(i)) {
            String availabilityQuery = "SELECT * FROM db2024_Reservation WHERE classroom_id = ? AND reserve_date = ? AND reserve_time = ?";
            try (PreparedStatement stmt = connection.prepareStatement(availabilityQuery)) {
              stmt.setString(1, room[0]); // classroom_id 사용
              stmt.setString(2, date);
              stmt.setInt(3, i);
              ResultSet rs = stmt.executeQuery();
              if (rs.next()) {
                availability.append(String.format("%d:X ", i));
              } else {
                availability.append(String.format("%d:O ", i));
              }
            }
          }
        }
        System.out.println(availability);
        roomSelectionMap.put(count, new String[] { room[0], date });
        count++;
      }
    }

    System.out.println("예약할 강의실 번호와 교시 번호를 선택하세요(예: 1 3) : ");
    int selection = scanner.nextInt();
    int timeslotSelection = scanner.nextInt();

    String[] selectedRoomDate = roomSelectionMap.get(selection);
    int classroomId = Integer.parseInt(selectedRoomDate[0]);
    String reserveDate = selectedRoomDate[1];

    // 예약 진행을 위한 추가 정보 입력
    System.out.println("사용자 ID를 입력하세요:");
    int userId = scanner.nextInt();
    scanner.nextLine(); // 엔터키 입력 소모
    System.out.println("예약 목적을 입력하세요:");
    String purpose = scanner.nextLine();

    int personNum;
    while (true) {
      System.out.println("예약할 인원 수를 입력하세요:");
      personNum = scanner.nextInt();
      if (personNum <= Integer.parseInt(selectedRoomDate[3])) {
        break;
      } else {
        System.out.println("인원수가 강의실 정원보다 작거나 같아야 합니다. 다시 입력하세요.");
      }
    }

    createReservation(connection, userId, classroomId, purpose, reserveDate, timeslotSelection, personNum);
  }

  // 예약을 생성하는 메소드
  public static void createReservation(Connection connection, int userId, int roomId, String purpose,
      String reserveDate, int timeslotId, int personNum) throws SQLException {
    String query = "INSERT INTO db2024_Reservation (classroom_id, user_id, user_name, purpose, person_num, reserve_date, reserve_time, create_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    String userName = getUserName(connection, userId);

    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setInt(1, roomId);
      statement.setInt(2, userId);
      statement.setString(3, userName);
      statement.setString(4, purpose);
      statement.setInt(5, personNum);
      statement.setDate(6, java.sql.Date.valueOf(reserveDate)); // java.sql.Date 사용
      statement.setInt(7, timeslotId);
      statement.setDate(8, new java.sql.Date(System.currentTimeMillis())); // java.sql.Date 사용
      statement.executeUpdate();
      System.out.println("예약이 성공적으로 완료되었습니다.");
    }
  }

  // 주어진 날짜의 다음 날을 반환하는 메소드
  private static String getNextDate(String date) {
    LocalDate localDate = LocalDate.parse(date);
    return localDate.plusDays(1).toString();
  }

  // 사용자 이름을 가져오는 메소드 (실제 구현 필요)
  private static String getUserName(Connection connection, int userId) throws SQLException {
    // 실제 구현이 필요합니다. 여기서는 임시로 사용자 이름을 반환합니다.
    return "사용자 이름";
  }
}
