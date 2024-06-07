# Database 생성
create database DB2024Team10;
use DB2024Team10;

# Database user 생성 + 권한 부여 --> root 계정
create user 'DB2024Team10'@localhost identified by 'DB2024Team10';
grant all privileges on DB2024Team10.* to 'DB2024Team10'@localhost;

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
    FOREIGN KEY (room_id) REFERENCES db2024_Classroom(room_id) ON DELETE NO ACTION # 외래키: 강의실 table의 강의실 id
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
	FOREIGN KEY (room_id) REFERENCES db2024_Classroom(room_id) ON DELETE CASCADE, # 외래키: 강의실 table의 강의실 id
	FOREIGN KEY (admin_id) REFERENCES db2024_Administrator(id) ON DELETE NO ACTION, # 외래키: 관리자 table의 관리자 id
	FOREIGN KEY (user_id) REFERENCES db2024_User(id) ON DELETE CASCADE # 외래키: 사용자 table의 사용자 id
);

# 예약 검색 뷰 생성
CREATE VIEW DB2024_ReservationView AS
SELECT reserved_id, room_id, reserved_date, reserved_period
FROM db2024_Reservation;

# 담당 사용자 조회 뷰 생성
CREATE VIEW DB2024_UserView AS
SELECT id, admin_id, name, major, email, phone_num, canReserve
FROM db2024_User;

# 로그인할 때, 해당 id, password를 가진 유저의 정보를 빠르게 반환하기 위한 인덱스
CREATE INDEX idx_user ON DB2024Team10.DB2024_User(id, password);

# 사용자가 자신의 예약 내역을 조회할 때, 예약 테이블에서 자신의 id와 user_id가 같은 예약 내역을 빠르게 반환하기 위해 이용하는 인덱스
CREATE INDEX idx_check_reservation ON DB2024Team10.DB2024_Reservation(user_id);

# 사용자가 예약 가능 강의실을 검색할 때, 해당 강의실의 강의 내역을 빠르게 반환하기 위해 이용하는 인덱스
CREATE INDEX idx_lecture ON DB2024Team10.DB2024_Lecture(room_id);

# 사용자가 예약 가능 강의실을 검색할 때, 선택한 건물명, 수용 인원에 맞는 예약 내역을 빠르게 반환하기 위해 이용하는 인덱스
CREATE INDEX idx_classroom ON DB2024Team10.DB2024_Classroom(building, capacity);

# 관리자 테이블 데이터 입력
INSERT INTO db2024_Administrator(id, password, contact)
VALUES
    (99999991, 'rhksflwk1', '02-3277-2114'), # contact 정보 -> 문자열로 수정
    (99999992, 'rhksflwk2', '02-3277-2030'),
    (99999993, 'rhksflwk3', '02-3277-3537'),
    (99999994, 'rhksflwk4', '02-3277-6644');

# 사용자 테이블 데이터 입력
INSERT INTO db2024_User(id, password, admin_id, email, name, major, phone_num)
VALUES
    (2171024, 'tlstnwjd', 99999991, 'christer10@ewhain.net', '신수정', '컴퓨터공학', '010-5729-7754'),
    (0505072, 'rlaalsdyd', 99999991, 'cselove123@ewha.ac.kr', '김민용', '컴퓨터공학', '010-2394-4242'),
    (2112349, 'dldbwjd', 99999991, 'business12@ewhain.net', '이유정', '경영학부', '010-5543-5323'),
    (2141013, 'gofltmxkdlftm', 99999991, 'engewha33@ewha.ac.kr', 'Harry Styles', '영어영문학과', '010-2341-8765'),
    (2340517, 'rlaalswl', 99999991,'cyber234@ewha.ac.kr', '김민지', '사이버보안학과', '010-5604-6668'),
    (2405156, 'rkdgofls', 99999992, 'econ012lov@ewha.ac.kr', '강해린', '경제학과', '010-2006-0515'),
    (2404119, 'ahwlgp', 99999992, 'dancute2332@ewha.ac.kr', '모지혜', '영어교육학과', '010-0411-1921'),
    (2340607, 'vkagksl', 99999992, 'pham0406@ewha.ac.kr', '팜하니', '무용과', '010-0234-6742'),
    (2340809, 'dlgpdls', 99999992,'hye08@ewha.ac.kr', '이혜인', '생명과학과', '010-0421-0808'),
    (2131021, 'rlawldnjs', 99999992, 'jiwon2@ewha.ac.kr', '김지원', '국어국문학과', '010-1019-6492'),
    (2120605, 'dbgowjd', 99999993, 'prof32@ewha.ac.kr', '유혜정', '철학과', '010-3921-3092'),
    (0190720, 'dlrkdwns', 99999993, 'oi1waka@ewha.ac.kr', '이강준', '경영학부', '010-0109-9596'),
    (0191222, 'rladudthf', 99999993,'tobe1222@ewha.ac.kr', '김영솔', '컴퓨터공학', '010-3234-0109'),
    (2340314, 'wkddnjsdud', 99999993, '32dso@ewhain.net', '장원영', '휴먼기계바이오공학부', '010-2004-1562'),
    (2239115, 'gksgodud', 99999993, '23f53@ewha.ac.kr', '한해영', '스크랜튼학부', '010-0325-4124'),
    (2192706, 'rladkgus', 99999994, 'ch235@ewhain.net', '김아현', '인공지능학부', '010-8532-9122'),
    (2318719, 'rlarbfl', 99999994, '233mkut@ewhain.net', '김규리', '서양화전공', '010-2384-7432'), # 국제학부 삭제
    (2207513, 'wlalswjd', 99999994, 'd245sdfk@ewha.ac.kr', '지민정', '물리학과', '010-2567-1032'),
    (0709013, 'rladudtj', 99999994,'r2lovephys@ewha.ac.kr', '김영서', '의예과', '010-2349-6421'),
    (2181079, 'dbduswl', 99999994,'3wdd@ewhain.net', '유연지', '간호학전공', '010-8763-8932') # 간호대학 삭제
;

# 강의실 테이블 데이터 입력
# admin_id 삭제
# hasProjector: 임의로 tf 배정해서 넣었습니다
INSERT INTO db2024_Classroom(room_id, building, room_num, capacity, plug_count, hasMic, hasProjector)
VALUES
    (1, '포스코관', '151', 70, 5, TRUE, TRUE),
    (2, '포스코관', '152', 70, 5, TRUE, TRUE),
    (3, '포스코관', '153', 70, 5, FALSE, TRUE),
    (4, '포스코관', '154', 32, 5, TRUE, TRUE),
    (5, '신공학관', '155', 32, 5, TRUE, TRUE),
    (6, '신공학관', '156', 32, 5, TRUE, TRUE),
    (7, '신공학관', '158', 48, 5, TRUE, TRUE),
    (8, '신공학관', '159', 108, 5, TRUE, TRUE),
    (9, '신공학관', '161', 108, 5, TRUE, TRUE),
    (10, '아산공학관', '101', 60, 4, TRUE, TRUE),
    (11, '아산공학관', '102', 60, 4, FALSE, FALSE),
    (12, '아산공학관', '103', 50, 4, TRUE, TRUE),
    (13, '아산공학관', '107', 70, 5, TRUE, TRUE),
    (14, '아산공학관', '108', 15, 5, FALSE, TRUE),
    (15, '아산공학관', '122', 35, 3, FALSE, TRUE),
    (16, '연구협력관', '124', 20, 2, FALSE, TRUE),
    (17, '연구협력관', '125', 40, 3, FALSE, FALSE),
    (18, '연구협력관', 'B101', 217, 8, TRUE, TRUE),
    (19, '연구협력관', '206', 12, 5, FALSE, TRUE),
    (20, '연구협력관', '223', 35, 5, FALSE, TRUE);


# 강의정보 테이블 데이터 입력
INSERT INTO db2024_Lecture (course_no, class_no, prof, quota, day1_of_week, period1, day2_of_week, period2, room_id)
VALUES
    (20480, 1, '김연정', 80, 1, 3, 3, 2, 1),
    (20480, 2, '심재형', 80, 1, 5, 3, 4, 3),
    (20480, 3, '김동성', 80, 2, 6, 4, 4, 20),
    (20480, 4, '최병주', 80, 3, 6, 5, 4, 13),
    (34811, 1, '박지현', 40, 2, 5, 4, 6, 17),
    (34811, 2, '김규호', 40, 1, 6, 3, 5, 3),
    (34811, 3, '오세은', 40, 1, 3, 3, 2, 9),
    (36507, 1, '황주선', 100, 1, 5, 3, 4, 17),
    (36507, 2, '민동보', 100, 2, 6, 4, 4, 15),
    (36507, 3, '윤명국', 100, 3, 6, 5, 4, 12),
    (36510, 1, '심재형', 70, 2, 5, 4, 6, 5),
    (36510, 2, '오세은', 70, 1, 6, 3, 5, 2),
    (38082, 1, '민동보', 50, 3, 6, 5, 4, 17),
    (38083, 1, '윤명국', 50, 2, 5, 4, 6, 6),
    (20471, 1, '김연정', 60, 1, 6, 3, 5, 4);

# 예약 테이블 데이터 입력
INSERT INTO db2024_Reservation(reserved_id, room_id, admin_id, user_id,
                               user_name, reason, people_num, reserved_date, reserved_period, created_date)
VALUES
    (1, 14, 99999992, 2171024, '신수정', '데이터베이스 팀 프로젝트 회의 진행', 5, '2024-06-02', 6, '2024-05-21'),
    (2, 14, 99999992, 2171024, '신수정', '데이터베이스 팀 프로젝트 회의 진행', 5, '2024-06-08', 6, '2024-05-21'),
    (3, 19, 99999994, 2405156, '강해린', '인공지능 팀 프로젝트 회의 진행', 4, '2024-06-03', 4, '2024-05-22'),
    (4, 3, 99999992, 2340314, '장원영', '동아리 회의', 40, '2024-06-02', 8, '2024-05-22'),
    (5, 10, 99999993, 2120605, '유혜정', '선배들과 함께 하는 취업 진로 특강', 50, '2024-06-09', 8, '2024-05-22'),
    (6, 14, 99999992, 2340517, '김민지', '데이터베이스 팀 프로젝트 회의 진행', 5, '2024-06-10', 6, '2024-05-23'),
    (7, 1, 99999991, 2207513, '지민정', '기계전자공학 TA 시간', 50, '2024-06-10', 7, '2024-05-23'),
    (8, 19, 99999994, 2120605, '유혜정', '공모전 준비', 8, '2024-06-24', 3, '2024-05-24'),
    (9, 6, 99999994, 2318719, '김규리', '동아리 임원진 회의', 25, '2024-06-16', 8, '2024-05-24'),
    (10, 11, 99999992, 0709013, '김영서', '선배들과 함께하는 취업 진로 특강', 50, '2024-06-20', 9, '2024-05-25'),
    (11, 8, 99999992, 2192706, '김아현', '데이터베이스 팀 프로젝트 회의 진행', 5, '2024-06-07', 9, '2024-05-27'),
    (12, 18, 99999993, 0505072, '김민용', '2차 중간고사', 200, '2024-06-20', 6, '2024-05-28');
