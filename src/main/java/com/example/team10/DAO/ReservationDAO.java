package main.java.com.example.team10.DAO;

import main.java.com.example.team10.DTO.ReservationDTO;

public interface ReservationDAO {
	ReservationDTO createReservation();
	
	public void insertReservation(ReservationDTO reservation);
}