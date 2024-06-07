package main.java.DB2024Team10.DTO;
public class ClassroomDTO {

    private long room_id; 	// 강의실 id
    private String building;	// 건물 이름
    private String room_num; // 호실
    private int capacity;		// 수용 인원
    private int plug_count;	// 콘센트 개수
    private boolean hasMic;		// 마이크 여부
    private boolean hasProjector;	 // 빔 프로젝터 여부

    // 회원가입 시 생성자: 필수 필드만 포함
    public ClassroomDTO(long room_id, String building, String room_num, int capacity,
                        int plug_count, boolean hasMic, boolean hasProjector) {
        this.room_id = room_id;
        this.building = building;
        this.room_num = room_num;
        this.capacity = capacity;
        this.plug_count =plug_count;
        this.hasMic = hasMic;
        this.hasProjector = hasProjector;
    }

    public static ClassroomDTO of (long room_id,String building,String room_num, int capacity, int plug_count,
                                   boolean hasMic, boolean hasProjector){
        return new ClassroomDTO(room_id,building, room_num, capacity, plug_count, hasMic, hasProjector);
    }

    /* Classroom 의 각 필드값에 대한 getter, setter 정의 */
    public long getRoomId() { return room_id; }
    public void setRoomId(long room_id) { this.room_id = room_id; }
    public String getBuilding() {
        return building;
    }
    public void setBuilding(String building) {
        this.building = building;
    }
    public String getRoomNum() {
        return room_num;
    }
    public void setRoomNum(String room_num) {
        this.room_num = room_num;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public int getPlugCount() {
        return plug_count;
    }
    public void setPlugCount(int plug_count) {
        this.plug_count = plug_count;
    }
    public boolean getHasMic() {
        return hasMic;
    }
    public void setHasMic(boolean hasMic) {
        this.hasMic = hasMic;
    }
    public boolean getHasProjector() {
        return hasProjector;
    }
    public void setHasProjector(boolean hasProjector) {
        this.hasProjector = hasProjector;
    }


}
