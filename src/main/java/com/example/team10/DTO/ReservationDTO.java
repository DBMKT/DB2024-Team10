package main.java.com.example.team10.DTO;

import java.util.Date;

public class ReservationDTO {

    private long reserve_id;  // 예약 id
    private long room_id;	// 강의실 id
    private long admin_id; // 관리자 id
    private long user_id;   // 이용자 id
    private int course_no; 	// 학수번호
    private int class_no;// 분반
    private Date date;	// 예약 날짜
    private PeriodType period;	// 예약 교시
    private String reason;  // 목적
    private int people_num;		// 사용 인원
    private Date created_date;  // 예약 요청 시간


    public  ReservationDTO(long reserve_id, long room_id, long admin_id, long user_id,
                           int course_no, int class_no, Date date, PeriodType period, String reason, int people_num, Date created_date) {
        this.reserve_id = reserve_id;
        this.room_id = room_id;
        this.admin_id = admin_id;
        this.user_id = user_id;
        this.course_no = course_no;
        this.class_no = class_no;
        this.date = date;
        this.period = period;
        this.reason = reason;
        this.people_num = people_num;
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
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public PeriodType getPeriod() {
        return period;
    }
    public void setPeriod(PeriodType period) {
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

enum PeriodType {
    ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN
}
enum DayType {
    MON, TUE, WED, THU, FRI, SAT, SUN;
}