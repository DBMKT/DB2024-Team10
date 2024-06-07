package main.java.DB2024Team10.GUI.Admin.TableModel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import main.java.DB2024Team10.DAO.AdministratorDAO;
import main.java.DB2024Team10.DTO.UserDTO;

public class UserTableModel extends AbstractTableModel {
	private AdministratorDAO adminDAO;

    private final String[] columnNames = {"ID", "이름", "전공", "이메일", "전화번호", "예약 가능 상태"};
    private List<UserDTO> users;

    public UserTableModel(List<UserDTO> users, AdministratorDAO adminDAO) {
        this.users = users;
        this.adminDAO = adminDAO;
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        UserDTO user = users.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return user.getId();
            case 1:
                return user.getName();
            case 2:
                return user.getMajor();
            case 3:
            	return user.getEmail();
            case 4:
            	return user.getPhoneNum();
            case 5:
            	return user.isCanReserve();
            default:
                return null;
        }
    }

@Override
public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    if (columnIndex == 5) {
        UserDTO user = users.get(rowIndex);
        user.setCanReserve((Boolean) aValue);
        adminDAO.updateUserInfo(user);
    }
}
@Override
public String getColumnName(int column) {
    return columnNames[column];
}

@Override
public Class<?> getColumnClass(int columnIndex) {
    if (columnIndex == 5) {
        return Boolean.class;
    }
    return String.class;
}

@Override
public boolean isCellEditable(int rowIndex, int columnIndex) {
    return columnIndex == 5;
}
}