package main.java.DB2024Team10.GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.SoftBevelBorder;

public class Landing extends JFrame {

    /**
     * Create the application.
     */
    public Landing() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        // 새로운 JFrame 생성하지 않고 현재 인스턴스를 활용
        setVisible(true);
        setBounds(100, 100, 450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JButton startBtn = new JButton("시작하기");
        startBtn.setFont(new Font("굴림", Font.PLAIN, 18));
        startBtn.setBounds(124, 185, 200, 50);
        startBtn.setPreferredSize(new Dimension(200, 50));
        getContentPane().add(startBtn);
        
        JPanel panel = new JPanel();
        panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
        panel.setBounds(41, 51, 367, 117);
        getContentPane().add(panel);
        panel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("강의실 예약 시스템");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(0, 0, 367, 57);
        panel.add(lblNewLabel);
        lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 40));
        
        JLabel lblNewLabel_1 = new JLabel("by Team MKT");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(109, 70, 150, 47);
        panel.add(lblNewLabel_1);
        lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 20));
        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Home();
                dispose();
            }
        });
    }
}