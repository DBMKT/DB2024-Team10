package main.java.com.example.team10.DTO;

import java.util.Date;
public class ReservationDTO {

    private long reserve_id;  // 예약 id
    private long room_id;	// 강의실 id
    private long admin_id; // 관리자 id
    private long user_id;   // 이용자 id
    private String user_name; // 이용자 이름
    private int course_no; 	// 학수번호
    private int class_no;// 분반
    private Date reserve_date;	// 예약 날짜
    private int period;	// 예약 교시
    private String reason;  // 목적
    private int people_num;		// 사용 인원
    private Date created_date;  // 예약 요청 시간

    public  ReservationDTO(long reserve_id, long room_id, long admin_id, long user_id,
                           int course_no, int class_no, Date reserve_date, int period, String reason, int people_num, Date created_date) {
        this.reserve_id = reserve_id;
        this.room_id = room_id;
        this.admin_id = admin_id;
        this.user_id = user_id;
        this.course_no = course_no;
        this.class_no = class_no;
        this.reserve_date = reserve_date;
        this.period = period;
        this.reason = reason;
        this.people_num = people_num;
        this.created_date = created_date;
    }

    public ReservationDTO() {
	}

	public ReservationDTO(long reserve_id, long room_id, String user_name, String reason, int people_num,
			Date reserve_date, int period, Date created_date) {
		this.reserve_id = reserve_id;
        this.room_id = room_id;
        this.user_name = user_name;
        this.reason = reason;
        this.people_num = people_num;
        this.reserve_date = reserve_date;
        this.period = period;
        this.created_date = created_date;
	}

	/* Reservation 의 각 필드값에 대한 getter, setter 정의 */
    public long getReserveId() {
        return reserve_id;
    }
    public void setReserveId(long reserve_id) {
        this.reserve_id = reserve_id;
    }
    public long getRoomId() {
        return room_id;
    }
    public void setRoomId(long room_id) {
        this.room_id = room_id;
    }
    public long getAdminId() {
        return admin_id;
    }
    public void setAdmin_id(long admin_id) {
        this.admin_id = admin_id;
    }
    public long getUserId() {
        return user_id;
    }
    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
    public int getCourseNo() {
        return course_no;
    }
    public void setCourseNo(int course_no) {
        this.course_no = course_no;
    }
    public int getClassNo() {
        return class_no;
    }
    public void setClassNo(int class_no) {
        this.class_no = class_no;
    }
    public Date getReserve_date() {
    	return reserve_date;
    }
    public void setReserve_date(Date reserve_date) {
    	this.reserve_date = reserve_date;
    }
    public int getPeriod() {
        return period;
    }
    public void setPeriod(int period) {
        this.period = period;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public int getPeopleNum() {
        return people_num;
    }
    public void setPeopleNum(int people_num) {
        this.people_num = people_num;
    }
    public Date getCreatedDate() {
        return created_date;
    }
    public void setCreatedDate(Date created_date) {
        this.created_date = created_date;
    }
}