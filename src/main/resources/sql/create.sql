# Database 생성
create database DB2024Team10;
use DB2024Team10;

# Database user 생성 + 권한 부여
create user 'DB2024Team10'@localhost identified by 'DB2024Team10';
grant all privileges on DB2024Team10.* to 'DB2024Team10'@localhost;

# 관리자 테이블 생성
CREATE TABLE DB2024_Administrator( 
    id BIGINT NOT NULL,
    password VARCHAR(20) NOT NULL,
    contact VARCHAR(20) NOT NULL,
    PRIMARY KEY(id)
);

# 사용자 테이블 생성
CREATE TABLE DB2024_User(
    id BIGINT NOT NULL,
    admin_id BIGINT,
    password VARCHAR(20) NOT NULL,
    canReserve TINYINT(1) NOT NULL DEFAULT 1, 
    email VARCHAR(50) NOT NULL,
    name VARCHAR(20) NOT NULL,
    major VARCHAR(20) NOT NULL,
    phone_num VARCHAR(13),
    PRIMARY KEY(id),
	FOREIGN KEY (admin_id) REFERENCES db2024_Administrator(id) ON DELETE SET NULL
);

# 강의실 테이블 생성
CREATE TABLE DB2024_Classroom(
    room_id BIGINT NOT NULL,
    building VARCHAR(255) NOT NULL,
    room_num VARCHAR(255) NOT NULL,
    capacity INT(3) NOT NULL,
    plug_count INT(3),
    hasMic BOOLEAN NOT NULL,
    hasProjector BOOLEAN NOT NULL,
    PRIMARY KEY(room_id)
    );

# 강의정보 테이블 생성
CREATE TABLE DB2024_Lecture( #DTO에 맞게 수정
    course_no INT(10) NOT NULL,
    class_no INT(2) NOT NULL,
    prof VARCHAR(20) NOT NULL,
    quota INT(3) NOT NULL,
    day1_of_week INT(1) NOT NULL, #월~일 : 1~7
    period1 INTEGER NOT NULL,
    day2_of_week INT(1),
    period2 INTEGER,
    room_id BIGINT, # on delete set null에 배치 되어서 not null 삭제
    PRIMARY KEY(course_no, class_no),
    FOREIGN KEY (room_id) REFERENCES db2024_Classroom(room_id) ON DELETE SET NULL
);

# 예약 테이블 생성
CREATE TABLE DB2024_Reservation(
	reserved_id BIGINT NOT NULL AUTO_INCREMENT,
	room_id BIGINT NOT NULL,
	admin_id BIGINT NOT NULL,
	user_id BIGINT NOT NULL,
	user_name VARCHAR(20) NOT NULL,
	reason VARCHAR(255) NOT NULL,
	people_num INTEGER NOT NULL,
    reserved_date DATE NOT NULL, #예약 날짜
	reserved_period INTEGER NOT NULL, #예약 교시
	created_date DATE NOT NULL, #예약 요청 시간
	PRIMARY KEY (reserved_id),
	FOREIGN KEY (room_id) REFERENCES db2024_Classroom(room_id) ON DELETE CASCADE,
	FOREIGN KEY (admin_id) REFERENCES db2024_Administrator(id) ON DELETE NO ACTION,
	FOREIGN KEY (user_id) REFERENCES db2024_User(id) ON DELETE CASCADE
);