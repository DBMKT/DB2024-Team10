package main.java.DB2024Team10.DTO;

import java.util.Date;
public class ReservationDTO {

    private long reserved_id;  // 예약 id
    private long room_id;	// 강의실 id
    private long admin_id; // 관리자 id
    private long user_id;   // 이용자 id
    private String user_name; // 이용자 이름
    private String reason;  // 목적
    private int people_num;		// 사용 인원
    private Date reserved_date;	// 예약 날짜
    private int reserved_period;	// 예약 교시
    private Date created_date;  // 예약 요청 시간
    private ClassroomDTO classroom; // 강의실

    public ReservationDTO(){} //기본 생성자

    public ReservationDTO(long room_id, long admin_id, long user_id, String user_name,
            Date reserved_date, int reserved_period, String reason, int people_num, Date created_date) {
    	this.room_id = room_id;
		this.admin_id = admin_id;
		this.user_id = user_id;
		this.user_name = user_name;
		this.reason = reason;
		this.people_num = people_num;
		this.reserved_date = reserved_date;
		this.reserved_period = reserved_period;
		this.created_date = created_date;
    }                      

	public ReservationDTO(long reserved_id, long room_id, String user_name, String reason, 
			int people_num, Date reserved_date, int reserved_period, 
			Date created_date) {
		this.reserved_id = reserved_id;
        this.room_id = room_id;
        this.user_name = user_name;
        this.reason = reason;
        this.people_num = people_num;
        this.reserved_date = reserved_date;
        this.reserved_period = reserved_period;
        this.created_date = created_date;
	}
	/* Reservation 의 각 필드값에 대한 getter, setter 정의 */
    public long getReservedId() {
        return reserved_id;
    }
    public void setReservedId(long reserved_id) {
        this.reserved_id = reserved_id;
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
    public String getUserName() {
    	return user_name;
    }
    public void setUserName(String user_name) {
    	this.user_name = user_name;
    }
    public Date getReservedDate() {
        return reserved_date;
    }
    public void setReservedDate(Date reserved_date) {
        this.reserved_date = reserved_date;
    }
    public int getReservedPeriod() {
        return reserved_period;
    }
    public void setReservedPeriod(int reserved_period) {
        this.reserved_period = reserved_period;
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
    
    public ClassroomDTO getClassroom() {
        return classroom;
    }
    
    public void setClassroom(ClassroomDTO classroom) {
        this.classroom = classroom;
    }
}
