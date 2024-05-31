package main.java.com.example.team10.DAO;

import java.util.List;

import main.java.com.example.team10.DTO.ReservationDTO;

public interface ReservationDAO {
    void createReservation(ReservationDTO reservation);

	List<ReservationDTO> getReservationsByUserId(long id);
}