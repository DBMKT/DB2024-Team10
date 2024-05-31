package main.java.com.example.team10.GUI.Admin.TableModel;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import main.java.com.example.team10.DTO.ReservationDTO;

public class ReserveTableModel extends AbstractTableModel {
    private List<ReservationDTO> reservations;
    private final String[] columnNames;
    private Boolean[] checkboxStates;
    private boolean includeCheckbox;

    public ReserveTableModel(List<ReservationDTO> reservations) {
    	this(reservations, true);

    }
    public ReserveTableModel(List<ReservationDTO> reservations, boolean includeCheckbox) {
        this.reservations = reservations;
        this.includeCheckbox = includeCheckbox;
        this.checkboxStates = new Boolean[reservations.size()];
        for (int i = 0; i < checkboxStates.length; i++) {
            checkboxStates[i] = false; // 체크박스 초기값
        }
        if (includeCheckbox) {
            this.columnNames = new String[]{ "", "강의실 ID", "예약자 이름", "목적", "인원 수", "예약 날짜", "교시", "신청 날짜" };
        } else {
            this.columnNames = new String[]{ "강의실 ID", "예약자 이름", "목적", "인원 수", "예약 날짜", "교시", "신청 날짜" };
        }
    }

    public void setReservations(List<ReservationDTO> reservations) {
        this.reservations = reservations;
        this.checkboxStates = new Boolean[reservations.size()];
        for (int i = 0; i < checkboxStates.length; i++) {
            checkboxStates[i] = false; // 체크박스 초기값
        }
        fireTableDataChanged();
    }

    public ReservationDTO getReservationAt(int rowIndex) {
        return reservations.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return reservations.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ReservationDTO reservation = reservations.get(rowIndex);
        if (includeCheckbox) {
	        switch (columnIndex) {
	            case 0:
	                return checkboxStates[rowIndex];
	            case 1:
	                return reservation.getRoomId();
	            case 2:
	                return reservation.getUserName();
	            case 3:
	                return reservation.getReason();
	            case 4:
	                return reservation.getPeopleNum();
	            case 5:
	                return reservation.getReservedDate();
	            case 6:
	                return reservation.getReservedPeriod();
	            case 7:
	                return reservation.getCreatedDate();
	            default:
	                return null;
	        }
        } else {
        	switch (columnIndex) {
	            case 0:
	                return reservation.getRoomId();
	            case 1:
	                return reservation.getUserName();
	            case 2:
	                return reservation.getReason();
	            case 3:
	                return reservation.getPeopleNum();
	            case 4:
	                return reservation.getReservedDate();
	            case 5:
	                return reservation.getReservedPeriod();
	            case 6:
	                return reservation.getCreatedDate();
	            default:
	                return null;
        	}
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (includeCheckbox && columnIndex == 0) { // 체크박스 컬럼
            checkboxStates[rowIndex] = (Boolean) aValue;
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (includeCheckbox && columnIndex == 0) {
            return Boolean.class; // 체크박스 컬럼
        }
        return super.getColumnClass(columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return includeCheckbox && columnIndex == 0; // 체크박스 컬럼만 편집 가능
    }
}