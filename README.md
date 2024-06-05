# DB2024-Team10
[2024-1 데이터베이스 팀 프로젝트] JDBC 를 사용한 데이터베이스 응용 프로그램

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

