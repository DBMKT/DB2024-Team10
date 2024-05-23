package main.java.com.example.team10.DTO;

public class LectureDTO {

    private int course_no; 			// 학수번호
    private int class_no;	// 분반
    private String prof; // 교수 이름
    private int quota;		// 수강 인원
    private DayType day1_of_week;	// 강의 요일 1
    private DayType day2_of_week;		// 강의 요일 2
    private PeriodType period;      // 강의 교시 1
    private PeriodType period2;		// 강의 교시 2
    private long room_id;	// 강의실 id


    public  LectureDTO(int course_no, int class_no, String prof, int quota, DayType day1_of_week, DayType day2_of_week, PeriodType period, PeriodType period2, long room_id) {
        this.course_no = course_no;
        this.class_no = class_no;
        this.prof = prof;
        this.quota = quota;
        this.day1_of_week = day1_of_week;
        this.day2_of_week = day2_of_week;
        this.period = period;
        this.period2 = period2;
        this.room_id = room_id;
    }

    /* Lecture 의 각 필드값에 대한 getter, setter 정의 */
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
    public String getProf() {
        return prof;
    }
    public void setProf(String prof) {
        this.prof = prof;
    }
    public int getQuota() {
        return quota;
    }
    public void setQuota(int quota) {
        this.quota = quota;
    }
    public DayType getDay1OfWeek() {
        return day1_of_week;
    }
    public void setDay1OfWeek(DayType day1_of_week) {
        this.day1_of_week = day1_of_week;
    }
    public DayType getDay2OfWeek() {
        return day2_of_week;
    }
    public void setDay2OfWeek(DayType day2_of_week) {
        this.day2_of_week = day2_of_week;
    }
    public PeriodType getPeriod() {
        return period;
    }
    public void setPeriod(PeriodType period) {
        this.period = period;
    }
    public PeriodType getPeriod2() {
        return period2;
    }
    public void setPeriod2(PeriodType period2) {
        this.period2 = period2;
    }
    public long getRoomId() {
        return room_id;
    }
    public void setRoomId(long room_id) {
        this.room_id = room_id;
    }
}