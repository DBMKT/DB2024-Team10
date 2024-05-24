package main.java.com.example.team10.GUI.Admin;

import java.awt.Component;

import javax.swing.*;

import main.java.com.example.team10.DAO.AdministratorDAO;
import main.java.com.example.team10.DAO.AdministratorDAOImpl;
import main.java.com.example.team10.DTO.AdministratorDTO;
import main.java.com.example.team10.util.SessionManager;

public class UserManagement extends JFrame {
    private AdministratorDTO admin;
    private AdministratorDAO adminImpl;
    private JButton btnLogout;
    private JButton btnBack;
    private JButton btnMyHome;
    
    public UserManagement() {
        admin = SessionManager.getCurrentAdmin();
        if (admin == null) {
            JOptionPane.showMessageDialog((Component) null,
                    "로그인 정보가 없습니다. 다시 로그인 해주세요.",
                    "오류",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0); // 종료
        }
        adminImpl = new AdministratorDAOImpl();
        
    }
}
