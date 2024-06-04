# Database 생성
create database DB2024Team10;
use DB2024Team10;

# Database user 생성 + 권한 부여 --> root 계정
create user 'DB2024Team10'@localhost identified by 'DB2024Team10';
grant all privileges on DB2024Team10.* to 'DB2024Team10'@localhost:3306;

# 관리자 테이블 생성
CREATE TABLE DB2024_Administrator( 
    id BIGINT NOT NULL, # 관리자 id
    password VARCHAR(20) NOT NULL, # 관리자 password
    contact VARCHAR(20) NOT NULL, # 관리자 연락처
    PRIMARY KEY(id) # 기본키: 관리자 id
);

# 사용자 테이블 생성
CREATE TABLE DB2024_User(
    id BIGINT NOT NULL, # 회원 ID(학번)
    admin_id BIGINT NOT NULL, # 회원을 관리하는 관리자 id
    password VARCHAR(20) NOT NULL, # 회원 비밀번호
    canReserve TINYINT(1) NOT NULL DEFAULT 1, # 예약 권한(예약 가능한 사용자/예약 불가한 사용자)
    email VARCHAR(50) NOT NULL, # 이메일 주소
    name VARCHAR(20) NOT NULL, # 이름
    major VARCHAR(20) NOT NULL, # 학과
    phone_num VARCHAR(13), # 전화번호
    PRIMARY KEY(id), # 기본키: 사용자 id
	FOREIGN KEY (admin_id) REFERENCES db2024_Administrator(id) ON DELETE NO ACTION # 외래키: 관리자 table의 관리자 id
);

# 강의실 테이블 생성
CREATE TABLE DB2024_Classroom(
    room_id BIGINT NOT NULL, # 강의실 id
    building VARCHAR(255) NOT NULL, # 건물 이름
    room_num VARCHAR(255) NOT NULL, # 호실
    capacity INT(3) NOT NULL, # 수용 인원
    plug_count INT(3) NOT NULL, # 콘센트 개수
    hasMic BOOLEAN NOT NULL, # 마이크 여부
    hasProjector BOOLEAN NOT NULL, # 빔 프로젝터 여부
    PRIMARY KEY(room_id) # 기본키: 강의실 id
    );

# 강의정보 테이블 생성
CREATE TABLE DB2024_Lecture( 
    course_no INT(10) NOT NULL, # 학수번호
    class_no INT(2) NOT NULL, # 분반
    prof VARCHAR(20) NOT NULL, # 교수 이름
    quota INT(3) NOT NULL, # 수강 인원
    day1_of_week INT(1) NOT NULL, # 첫 번째 강의 요일(월~일 : 1~7)
    period1 INTEGER NOT NULL, # 첫 번째 강의 교시(1~10)
    day2_of_week INT(1),# 두 번째 강의 요일(월~일 : 1~7)
    period2 INTEGER, # 두 번째 강의 교시(1~10) (1학점, 2학점 수업의 경우 null)
    room_id BIGINT NOT NULL, # 강의실 id (1학점, 2학점 수업의 경우 null)
    PRIMARY KEY(course_no, class_no), # 기본키: (학수번호, 분반)
    FOREIGN KEY (room_id) REFERENCES db2024_Classroom(room_id) ON DELETE NO ACTION # 외래키: 강의실 table의 강의 id
);

# 예약 테이블 생성
CREATE TABLE DB2024_Reservation(
	reserved_id BIGINT NOT NULL AUTO_INCREMENT, # 예약 id(자동으로 하나씩 증가)
	room_id BIGINT NOT NULL, # 강의실 id
	admin_id BIGINT NOT NULL, # 관리자 id
	user_id BIGINT NOT NULL, # 이용자 id
	user_name VARCHAR(20) NOT NULL, # 이용자 이름
	reason VARCHAR(255) NOT NULL, # 예약 목적
	people_num INTEGER NOT NULL, # 예약 인원 
    reserved_date DATE NOT NULL, # 예약 날짜
	reserved_period INTEGER NOT NULL, # 예약 교시
	created_date DATE NOT NULL, # 예약 요청 시간
	PRIMARY KEY (reserved_id), # 기본키: 예약 id
	FOREIGN KEY (room_id) REFERENCES db2024_Classroom(room_id) ON DELETE CASCADE, # 외래키: 강의실 table의 강의 id
	FOREIGN KEY (admin_id) REFERENCES db2024_Administrator(id) ON DELETE NO ACTION, # 외래키: 관리자 table의 관리자 id
	FOREIGN KEY (user_id) REFERENCES db2024_User(id) ON DELETE CASCADE # 외래키: 사용자 table의 사용자 id
);

# 예약 검색 뷰 생성
CREATE VIEW DB2024_ReservationView AS
SELECT reserved_id, room_id, reserved_date, reserved_period
FROM db2024_Reservation;