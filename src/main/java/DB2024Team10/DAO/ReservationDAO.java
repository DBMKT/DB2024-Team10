package main.java.DB2024Team10.DAO;

import java.util.List;

import main.java.DB2024Team10.DTO.ClassroomDTO;
import main.java.DB2024Team10.DTO.ReservationDTO;

public interface ReservationDAO {

	List<ReservationDTO> getReservationsByUserId(long id);
	ClassroomDTO getClassroomByRoomId(long roomId);
	
	public void createReservation(ReservationDTO reservation);
	public void insertReservation(ReservationDTO reservation);

}