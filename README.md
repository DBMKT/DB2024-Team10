# DB2024-Team10
[2024-1 데이터베이스 팀 프로젝트] JDBC 를 사용한 데이터베이스 응용 프로그램

## 프로젝트 구조
├─bin
│ └─main
│ ├─java
│ │ └─com
│ │ └─example
│ │ └─team10
│ │ ├─DAO
│ │ ├─DTO
│ │ │ └─enums
│ │ ├─GUI
│ │ │ ├─Admin
│ │ │ │ └─TableModel
│ │ │ └─User
│ │ └─util
│ └─resources
│ └─sql
└─src
└─main
├─java
│ └─com
│ └─example
│ └─team10
│ ├─DAO
│ ├─DTO
│ │ └─enums
│ ├─GUI
│ │ ├─Admin
│ │ │ └─TableModel
│ │ └─User
│ └─util
└─resources
└─sql

## 메서드 및 클래스 설명


## AdministratorDAO.java

### 인터페이스: `AdministratorDAO`

`AdministratorDAO` 인터페이스는 관리자 계정의 인증 및 예약 관리 기능을 제공하는 DAO입니다. 이 인터페이스는 다음과 같은 기능을 정의합니다:
- 관리자 로그인/로그아웃
- 관리자 담당 사용자의 예약 권한 조정
- 예약 정보 검색, 조회 및 삭제

#### 사용된 클래스
- `AdministratorDTO`
- `UserDTO`
- `ReservationDTO`

#### 사용된 메서드

1. `AdministratorDTO adminLogin(long id, String password)`
   - **설명**: 관리자가 로그인하는 기능을 제공합니다.
   - **매개변수**: 관리자 ID (`id`)와 비밀번호 (`password`)
   - **반환값**: 해당 관리자의 `AdministratorDTO`

2. `void logout()`
   - **설명**: 관리자가 로그아웃하는 기능을 제공합니다.
   - **매개변수**: 없음
   - **반환값**: 없음

3. `void updateUserInfo(UserDTO user)`
   - **설명**: 담당 사용자의 예약 권한을 조정하는 기능을 제공합니다.
   - **매개변수**: 수정할 사용자의 `UserDTO`
   - **반환값**: 없음

4. `void deleteReservationInfo(List<ReservationDTO> reservations)`
   - **설명**: 현재 예약 내역을 삭제하는 기능을 제공합니다.
   - **매개변수**: 삭제할 예약들의 리스트 (`List<ReservationDTO>`)
   - **반환값**: 없음

5. `List<ReservationDTO> getUpcomingReservationList()`
   - **설명**: 현재 시각 기준 가장 가까운 예약 내역들을 조회하는 기능을 제공합니다.
   - **매개변수**: 없음
   - **반환값**: 가까운 예약 내역들의 리스트 (`List<ReservationDTO>`)

6. `List<ReservationDTO> searchReservationList(String keyword, List<String> selectedFields, int period, Date selectedDate)`
   - **설명**: 예약 내역을 검색하는 기능을 제공합니다.
   - **매개변수**: 검색 키워드 (`keyword`), 선택된 필드들의 리스트 (`List<String> selectedFields`), 교시 (`int period`), 선택된 날짜 (`Date selectedDate`)
   - **반환값**: 검색된 예약 내역들의 리스트 (`List<ReservationDTO>`)

7. `void deletePastReservations()`
   - **설명**: 이미 진행된 예약 내역을 삭제하는 기능을 제공합니다.
   - **매개변수**: 없음
   - **반환값**: 없음

이러한 클래스와 메서드들을 사용하여 관리자가 시스템에서 사용자 및 예약을 관리할 수 있습니다.






## AdministratorDAOImpl.java

### 클래스: `AdministratorDAOImpl`

`AdministratorDAOImpl` 클래스는 `AdministratorDAO` 인터페이스를 구현하며, 관리자 관련 데이터베이스 작업을 수행합니다. 이 클래스는 데이터베이스와의 연결을 관리하는 역할도 합니다.

#### 생성자: `AdministratorDAOImpl()`

- **기능**: `AdministratorDAOImpl` 클래스의 생성자로, 데이터베이스 연결을 설정하고, 연결이 실패할 경우 예외를 발생시킵니다.
- **동작 방식**:
  - `this.conn = JdbcUtil.getConnection();`: `JdbcUtil` 클래스의 `getConnection` 메서드를 호출하여 데이터베이스 연결을 설정합니다. 이 연결 객체는 클래스의 인스턴스 변수 `conn`에 저장됩니다.
  - `if (this.conn == null)`: 데이터베이스 연결이 성공적으로 이루어지지 않았을 경우를 확인합니다.
  - `throw new RuntimeException("커넥션이 제대로 맺어지지 않았습니다.");`: 연결이 `null`인 경우, 런타임 예외를 발생시킵니다.
  - 이 생성자는 `AdministratorDAOImpl` 클래스가 인스턴스화될 때 올바르게 데이터베이스에 연결되도록 하며, 데이터베이스 연결이 실패하면 즉시 예외를 발생시켜 문제를 명확히 드러냅니다.

#### 클래스 설명

- `AdministratorDAO` 인터페이스를 구현한 클래스로, 관리자 관련 데이터베이스 작업을 수행합니다.
- `Connection` 객체를 사용해 데이터베이스와의 연결을 관리합니다. 데이터베이스 연결이 성공적으로 이루어지지 않았을 경우, 적절한 예외 처리를 통해 문제를 알립니다.








### 메서드: `public AdministratorDTO adminLogin(long id, String password)`

#### 메서드 설명
- **기능**: 관리자 로그인을 처리합니다. 이 메서드는 데이터베이스 연결을 통해 SQL 쿼리를 실행하고, 결과를 처리하는 과정에서 예외 처리와 자원 관리를 수행합니다. 관리자 로그인 기능을 구현하기 위해 데이터베이스 트랜잭션을 사용하고, 예외 발생 시 롤백을 수행하여 데이터 일관성을 유지합니다.
- **매개변수**: 관리자의 `id`와 `password`
- **반환 타입**: 성공적으로 로그인하면 해당 관리자 정보를 포함한 `AdministratorDTO` 객체를 반환합니다.
- **동작 방식**:
  - 입력받은 `id`와 `password`를 사용하여 데이터베이스에서 해당 정보와 일치하는 관리자를 조회합니다.
  - 조회 결과가 있다면 `AdministratorDTO` 객체를 생성하고, 세션에 로그인한 관리자 정보를 저장합니다.
  - 조회 결과가 없다면 로그인 실패 메시지를 출력합니다.

#### 사용된 주요 메서드들
- `res.next()`: `ResultSet`의 다음 행이 있는지 확인합니다. 로그인 성공 시 `true`를 반환하고, 그렇지 않으면 `false`를 반환합니다.
- 트랜잭션을 사용하여 데이터베이스 작업을 수행합니다.
  - `conn.setAutoCommit(false)`: 데이터베이스 연결의 자동 커밋 모드를 해제하여, 트랜잭션을 시작합니다.
  - `conn.commit()`: 트랜잭션을 커밋하여 모든 변경사항을 데이터베이스에 반영합니다.
  - `conn.rollback()`: 예외 발생 시 롤백을 수행합니다.
  - `conn.setAutoCommit(true)`: 마지막에 자동 커밋 모드를 원래 상태로 되돌립니다.









### 메서드: `public void updateUserInfo(UserDTO targetUser)`

#### 메서드 설명
- **기능**: 이 메서드는 `UserDTO` 객체를 매개변수로 받아, 해당 객체에 담긴 정보를 바탕으로 사용자의 정보를 업데이트하는 기능을 수행합니다.
- **조건**: 현재 로그인한 관리자가 해당 사용자의 담당 관리자가 아닐 경우, 업데이트 작업을 수행하지 않고 종료합니다.
- **동작 방식**:
  - 데이터베이스와의 상호작용 중에 트랜잭션 관리, 예외 처리, 자원 관리를 적절히 수행합니다.
  - 현재 로그인한 사용자의 권한을 확인하여 특정 작업에 대한 접근 권한을 제어하는 보안 관련 로직도 포함하고 있습니다.









### 메서드: `public void deleteReservationInfo(List<ReservationDTO> reservations)`

#### 메서드 설명
- **기능**: 트랜잭션 관리, 배치 처리, 예외 처리 등을 적절히 활용하여 데이터베이스의 예약 정보 삭제 작업을 수행합니다.
- **매개변수**: 삭제할 예약 정보 목록이 담긴 `ReservationDTO` 객체의 리스트 `List<ReservationDTO>`를 매개변수로 받습니다.
- **반환 타입**: `void` - 메서드 실행 후 반환값은 없습니다.
- **동작 방식**:
  - 입력 받은 예약 목록에 대해 데이터베이스에서 해당 예약 내역을 삭제합니다. 삭제 작업은 일괄 처리(batch)를 통해 수행되며, 모든 예약 내역이 성공적으로 삭제되었는지 여부에 따라 커밋 또는 롤백을 결정합니다.
  - 트랜잭션 관리: 이 메서드는 `conn.setAutoCommit(false);`을 호출하여 자동 커밋을 비활성화하고 트랜잭션을 시작합니다. 모든 삭제 작업이 성공하면 `conn.commit();`을 호출하여 변경사항을 확정하고, 하나라도 실패하면 `conn.rollback();`을 호출하여 모든 변경사항을 취소합니다.
  - 예외 처리: SQL 실행 중 예외가 발생하면 트랜잭션을 롤백하여 데이터베이스의 일관성을 유지합니다.
  - 자원 관리: `prepareStatement(sql);`와 같은 데이터베이스 리소스 사용 후 이를 안전하게 닫습니다.

#### 사용된 주요 메서드들
- `pStmt = conn.prepareStatement(sql);`: 주어진 SQL 문을 준비하고 `PreparedStatement` 객체를 생성합니다. 이 객체를 사용하여 SQL 문을 실행합니다.
- `pStmt.setLong(1, reservation.getReservedId());`: `PreparedStatement`의 지정된 위치에 `long` 값을 설정합니다. 이는 SQL 문의 파라미터를 동적으로 바인딩하는 데 사용됩니다.
- `pStmt.addBatch();`: 현재 `PreparedStatement` 객체에 설정된 파라미터 값을 배치에 추가합니다. 이를 통해 여러 개의 SQL 문을 한 번에 실행할 수 있습니다.
- `pStmt.executeBatch();`: 추가된 배치의 모든 SQL 문을 실행하고 각 문의 영향을 받은 행 수를 나타내는 배열을 반환합니다.








### 메서드: `public List<ReservationDTO> getUpcomingReservationList()`

#### 메서드 설명
- **기능**: 데이터베이스에서 예약 정보를 조회하여, 현재 시점을 기준으로 가까운 예약부터 순서대로 정렬된 예약 목록을 객체 리스트로 변환하여 반환합니다. 이 과정에서 데이터베이스 연결, SQL 쿼리 실행, 결과 처리, 예외 처리 및 리소스 관리를 수행해 안정적으로 데이터를 처리합니다.
- **매개변수**: 없음.
- **반환 타입**: `List<ReservationDTO>` - 조회된 예약 정보가 담긴 `ReservationDTO` 객체의 리스트를 반환합니다.
- **동작 방식**: 데이터베이스에서 `reserved_date` 필드를 기준으로 오름차순 정렬하여 예약 내역을 조회하고, 조회된 데이터를 바탕으로 `ReservationDTO` 객체 리스트를 생성하여 반환합니다.

#### 사용된 주요 메서드들
- `executeQuery()`: `PreparedStatement` 객체에 설정된 SQL 쿼리를 실행하고, 조회 결과를 담고 있는 `ResultSet` 객체를 반환합니다.
- `res.next()`: `ResultSet` 객체에서 다음 행으로 커서를 이동하며, 더 이상 행이 없으면 `false`를 반환합니다.
- `getLong("reserved_id")`, `rs.getString("reason")`, `rs.getInt("people_num")`, `rs.getDate("created_date")`: `ResultSet` 객체에서 지정된 컬럼명에 해당하는 데이터를 각각의 타입(`long`, `String`, `int`, `Date`)으로 가져옵니다.
- `close()`: `ResultSet`, `PreparedStatement`와 같은 리소스를 안전하게 닫습니다.







### 메서드: `public List<ReservationDTO> searchReservationList(String keyword, List<String> selectedFields, int period, Date selectedDate)`

#### 메서드 설명
- **기능**: 사용자가 입력한 키워드와 선택된 필드를 기준으로 예약 내역을 검색하고, 결과를 `ReservationDTO` 객체 리스트로 반환합니다. 주어진 조건에 따라 SQL 쿼리를 동적으로 생성하고, 해당 쿼리를 실행하여 검색 결과를 반환합니다.
- **매개변수**:
  - `keyword`: 검색어로 사용될 문자열.
  - `selectedFields`: 검색 조건으로 사용할 필드들의 리스트.
  - `period`: 예약된 교시를 나타내는 정수.
  - `selectedDate`: 예약된 날짜를 나타내는 `Date` 객체.
- **반환 타입**: `List<ReservationDTO>` - 검색된 예약 내역을 `ReservationDTO` 객체 리스트로 반환합니다.

#### 사용된 주요 메서드들
- `setString(int parameterIndex, String x)`, `setDate(int parameterIndex, Date x)`, `setInt(int parameterIndex, int x)`: 지정된 인덱스에 문자열, 날짜, 정수 매개변수를 설정합니다.
- `getLong(String columnLabel)`, `getString(String columnLabel)`, `getInt(String columnLabel)`, `getDate(String columnLabel)`: `ResultSet` 객체에서 지정된 열 레이블에 해당하는 데이터를 각각 `long`, `String`, `int`, `Date` 타입으로 가져옵니다.






### 메서드: `public void deletePastReservations()`

#### 메서드 설명
- **기능**: 현재 시점을 기준으로 지난 예약 내역을 자동으로 데이터베이스에서 삭제합니다.
- **매개변수**: 없음.
- **반환 타입**: `void` - 메서드 실행 후 반환값은 없습니다.
- **동작 방식**: 현재 시간을 기준으로 예약 날짜가 그 이전인 모든 예약 내역을 찾아 데이터베이스에서 삭제합니다. 삭제된 행의 수를 콘솔에 출력합니다.

#### 사용된 주요 메서드들
- `setTimestamp(int parameterIndex, Timestamp x)`: 지정된 인덱스에 타임스탬프 매개변수를 설정합니다. 이 메서드는 현재 시간을 기준으로 과거의 예약 내역을 식별하는 데 사용됩니다.
- `executeUpdate()`: `PreparedStatement` 객체에 설정된 SQL 쿼리를 실행하고 데이터베이스에 변경 사항을 반영하는 메서드로, 여기서는 삭제된 행의 수를 반환합니다.






### 메서드: `public void logout()`

#### 메서드 설명
- **기능**: 관리자를 로그아웃시킵니다.
- **매개변수**: 없음.
- **반환 타입**: `void` - 메서드 실행 후 반환값은 없습니다.
- **동작 방식**: `SessionManager` 클래스의 `adminLogout` 정적 메서드를 호출하여 관리자 사용자의 세션을 종료합니다. 이로써 관리자 사용자는 시스템에서 로그아웃되며, 관련된 세션 정보가 제거되거나 초기화됩니다.

#### 사용된 주요 메서드들
- `SessionManager.adminLogout()`: `SessionManager` 클래스에 정의된 정적 메서드로, 관리자 사용자의 세션을 종료하고 로그아웃을 처리합니다. 이 메서드를 통해 사용자 인증 상태를 관리하고, 필요한 경우 사용자의 로그인 상태를 적절히 변경합니다. 이는 보안을 유지하고, 사용자가 시스템을 안전하게 이용할 수 있도록 돕습니다.







### ReservationDAO.java

`ReservationDAO` 인터페이스는 예약 관련 데이터베이스 작업을 정의하며, 예약과 관련된 데이터를 가져오거나 생성, 삽입하는 등의 기능을 제공하는 메서드를 선언합니다.

#### 사용된 메서드

1. `getReservationsByUserId(long id)`
   - **기능**: 주어진 사용자 ID에 해당하는 모든 예약 내역을 가져옵니다.
   - **매개변수**: `long id` - 사용자 ID
   - **반환 타입**: `List<ReservationDTO>` - 사용자의 예약 내역 리스트.

2. `getClassroomByRoomId(long roomId)`
   - **기능**: 주어진 강의실 ID에 해당하는 강의실 정보를 가져옵니다.
   - **매개변수**: `long roomId` - 강의실 ID
   - **반환 타입**: `ClassroomDTO` - 강의실 정보 객체.

3. `createReservation(ReservationDTO reservation)`
   - **기능**: 새로운 예약을 생성합니다.
   - **매개변수**: `ReservationDTO reservation` - 생성할 예약 정보 객체.
   - **반환 타입**: `void` - 반환값 없음.

4. `insertReservation(ReservationDTO reservation)`
   - **기능**: 새로운 예약을 데이터베이스에 삽입합니다.
   - **매개변수**: `ReservationDTO reservation` - 삽입할 예약 정보 객체.
   - **반환 타입**: `void` - 반환값 없음.

이 인터페이스는 예약 시스템에서 예약 정보를 효율적으로 관리하고 조작할 수 있도록 하는 여러 메서드들을 제공합니다. 이를 통해 구현 클래스는 데이터베이스와 상호작용하며 예약 정보를 처리합니다.







### ReservationDAOImpl.java

#### 클래스: `ReservationDAOImpl`

`ReservationDAOImpl` 클래스는 `ReservationDAO` 인터페이스를 구현하며, 예약 관련 작업을 수행하는 클래스입니다.

#### 생성자: `ReservationDAOImpl()`

- **기능**: `ReservationDAOImpl` 클래스의 생성자로, 데이터베이스 연결을 설정하고, 연결이 실패할 경우 예외를 발생시킵니다.
- **동작 방식**:
  - `this.conn = JdbcUtil.getConnection();`: `JdbcUtil` 클래스의 `getConnection` 메서드를 호출하여 데이터베이스 연결을 설정합니다. 이 연결 객체는 클래스의 인스턴스 변수 `conn`에 저장됩니다.
  - `if (this.conn == null)`: 데이터베이스 연결이 성공적으로 이루어지지 않았을 경우를 확인합니다.
  - `throw new RuntimeException("커넥션이 제대로 맺어지지 않았습니다.");`: 연결이 `null`인 경우, 런타임 예외를 발생시킵니다.
  - 이 생성자는 `ReservationDAOImpl` 클래스가 인스턴스화될 때 올바르게 데이터베이스에 연결되도록 하며, 데이터베이스 연결이 실패하면 즉시 예외를 발생시켜 문제를 명확히 드러냅니다.

#### 클래스 설명

- `ReservationDAO` 인터페이스를 구현한 클래스로, 예약 관련 작업을 수행합니다.







### 메서드: `public ClassroomDTO getClassroomByRoomId(long roomId)`

#### 메서드 설명
- **기능**: 관리자 로그인 기능을 구현합니다. 주어진 ID와 비밀번호를 사용하여 데이터베이스에서 관리자를 찾고, 로그인 세션을 생성합니다.
- **매개변수**:
  - `long id`: 관리자 ID
  - `String password`: 관리자 비밀번호
- **반환 타입**: `AdministratorDTO` - 로그인 성공 시 로그인한 관리자의 정보를 담은 DTO 객체를 반환하고, 실패 시 `null`을 반환합니다.
- **동작 방식**:
  - 먼저, 데이터베이스 연결의 자동 커밋을 비활성화하여 트랜잭션을 시작합니다.
  - `PreparedStatement`를 사용하여 ID와 비밀번호가 일치하는 관리자를 데이터베이스에서 조회합니다.
  - 결과가 있으면(로그인 성공), `AdministratorDTO` 객체를 생성하고, 조회한 결과를 바탕으로 정보를 설정합니다. 이후, 세션 관리자를 통해 로그인한 관리자 정보를 세션에 저장합니다.
  - 결과가 없으면(로그인 실패), 적절한 메시지를 출력합니다.
  - 최종적으로, 트랜잭션을 커밋합니다.
  - `SQLException`이 발생하면, 롤백을 시도합니다.
  - 마지막으로, 사용한 `ResultSet`, `PreparedStatement`를 닫고, 데이터베이스 연결의 자동 커밋을 다시 활성화합니다.

#### 사용된 주요 메서드들
- `conn.setAutoCommit(false);`: 데이터베이스 연결의 자동 커밋을 비활성화하여 트랜잭션을 시작합니다.
- `pStmt = conn.prepareStatement(...);`: SQL 쿼리를 준비합니다. 이 쿼리는 ID와 비밀번호를 매개변수로 받아 해당하는 관리자를 조회합니다.
- `res = pStmt.executeQuery();`: 준비된 쿼리를 실행하고, 실행 결과를 `ResultSet` 객체에 저장합니다.
- `SessionManager.adminAuthenticate(loginAdmin);`: 로그인 성공 시, 세션 관리자를 통해 로그인한 관리자 정보를 세션에 저장합니다.
- `conn.commit();`: 트랜잭션을 커밋합니다. 이는 데이터베이스에 대한 모든 변경 사항을 확정짓습니다.
- `conn.rollback();`: 예외 발생 시, 트랜잭션을 롤백합니다. 이는 데이터베이스에 대한 모든 변경 사항을 취소합니다.
- `JdbcUtil.close(res);`, `JdbcUtil.close(pStmt);`: 사용한 `ResultSet`과 `PreparedStatement`를 닫습니다. 이는 자원을 해제하여 시스템 자원의 낭비를 방지합니다.
- `conn.setAutoCommit(true);`: 데이터베이스 연결의 자동 커밋을 다시 활성화합니다. 이는 트랜잭션이 종료됨을 의미합니다.






### DTO 클래스

1. **AdministratorDTO**: 관리자 관련 데이터를 전달합니다.
2. **UserDTO**: 사용자 관련 데이터를 전달합니다.
3. **ReservationDTO**: 예약 관련 데이터를 전달합니다.












