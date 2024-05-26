# Database 생성
#create database db2024;
use db2024;

# Database user 생성 + 권한 부여
create user 'db2024'@localhost identified by 'db2024';
grant all privileges on db2024.* to 'db2024'@localhost;

# 관리자 테이블 생성
CREATE TABLE db2024_Administrator( 
    id BIGINT NOT NULL,
    password VARCHAR(20) NOT NULL,
    contact VARCHAR(20) NOT NULL,
    PRIMARY KEY(id)
);

# 사용자 테이블 생성
CREATE TABLE db2024_User(
    id BIGINT NOT NULL,
    admin_id BIGINT,
    password VARCHAR(20) NOT NULL,
    canReserve TINYINT(1) NOT NULL DEFAULT 1, 
    email VARCHAR(50) NOT NULL,
    name VARCHAR(20) NOT NULL,
    major VARCHAR(20) NOT NULL,
    phoneNum VARCHAR(13),
    PRIMARY KEY(id),
	FOREIGN KEY (admin_id) REFERENCES db2024_Administrator(id) ON DELETE SET NULL
);

# 강의실 테이블 생성
CREATE TABLE db2024_Classroom(
    room_id BIGINT NOT NULL,
    # admin_id BIGINT,(DTO에서 삭제됨) # on delete set null에 배치 되어서 not null 삭제
    building VARCHAR(255) NOT NULL,
    room_num VARCHAR(255) NOT NULL,
    capacity INT(3) NOT NULL,
    plug_count INT(3),
    hasMic BOOLEAN NOT NULL,
    hasProjector BOOLEAN NOT NULL,
    PRIMARY KEY(room_id)
    # FOREIGN KEY (admin_id) REFERENCES db2024_Administrator(id) ON DELETE SET NULL (DTO에서 삭제됨)
);

# 강의정보 테이블 생성
CREATE TABLE db2024_Lecture( #DTO에 맞게 수정
    course_no INT(10) NOT NULL,
    class_no INT(2) NOT NULL,
    # admin_id BIGINT,(DTO에서 삭제됨) # on delete set null에 배치 되어서 not null 삭제
    prof VARCHAR(20) NOT NULL,
    quota INT(3) NOT NULL,
    day1_of_week INT(1) NOT NULL, #월~일 : 1~7
    period1 INTEGER NOT NULL,
    day2_of_week INT(1),
    period2 INTEGER,
    room_id BIGINT, # on delete set null에 배치 되어서 not null 삭제
    PRIMARY KEY(course_no, class_no),
    #FOREIGN KEY (admin_id) REFERENCES db2024_Administrator(id) ON DELETE SET NULL,(DTO에서 삭제됨)
    FOREIGN KEY (room_id) REFERENCES db2024_Classroom(room_id) ON DELETE SET NULL
);

# 예약 테이블 생성
CREATE TABLE db2024_Reservation(
	reserved_id BIGINT NOT NULL,
	room_id BIGINT, # on delete set null에 배치 되어서 not null 삭제
	admin_id BIGINT, # on delete set null에 배치 되어서 not null 삭제
	user_id BIGINT, # on delete set null에 배치 되어서 not null 삭제
	# user_name VARCHAR(20) NOT NULL,(DTO에서 삭제됨)
	reason VARCHAR(255) NOT NULL,
	people_num INTEGER NOT NULL,
    date DATE NOT NULL, #예약 날짜
	period INTEGER NOT NULL, #예약 교시
	created_date DATE NOT NULL, #예약 요청 시간
	PRIMARY KEY (reserved_id),
	FOREIGN KEY (room_id) REFERENCES db2024_Classroom(room_id) ON DELETE SET NULL,
	#FOREIGN KEY (admin_id) REFERENCES db2024_Classroom(admin_id) ON DELETE SET NULL,(DTO에 맞게 삭제)
	FOREIGN KEY (user_id) REFERENCES db2024_User(id) ON DELETE SET NULL
);