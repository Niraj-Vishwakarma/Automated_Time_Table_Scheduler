package AutomatedTimeTableScheduler.Panels;

import AutomatedTimeTableScheduler.Database.DatabaseCon;
import AutomatedTimeTableScheduler.Static.Constant;
import AutomatedTimeTableScheduler.Static.Constraint;
import AutomatedTimeTableScheduler.Static.CreatePDF;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TimeTablePanel extends JPanel implements ActionListener {


    private boolean status;
    private JLabel timeSlotsLabel,statusLabel;
    private JButton generateTimeTableButton;
    private DatabaseCon db;

    public  TimeTablePanel(){
        //Initialising Member
        timeSlotsLabel = new JLabel();
        statusLabel = new JLabel();
        generateTimeTableButton = new JButton("Generate Time Table");

        //Checking Status if Time Table can be built
        checkStatus();

        //Editing Members
        timeSlotsLabel.setFont(new Font("Times New Roman",Font.BOLD,18));
        statusLabel.setFont(new Font("Times New Roman",Font.BOLD,24));
        generateTimeTableButton.setPreferredSize(Constant.BUTTON_SIZE);
        generateTimeTableButton.setBackground(Constant.BUTTON_BACKGROUND);

        //Adding Listeners
        generateTimeTableButton.addActionListener(this);

        //Editing Panel
        setLayout(new GridBagLayout());
        setBackground(Constant.PANEL_BACKGROUND);

        //Adding Member to Panel
        add(timeSlotsLabel, Constraint.setPosition(0,0));
        add(statusLabel, Constraint.setPosition(0,1));
        add(generateTimeTableButton,Constraint.setPosition(0,2));
    }

    private void checkStatus(){
        status = true;

        try{
            db = new DatabaseCon();

            //Checking for TimeSlots
            if( db.getTimeSlotCount() >= 2  ){
                timeSlotsLabel.setText("Time Slots : All Ok");
            }else{
                timeSlotsLabel.setText("Time Slots : There should be minimum 2 Time Slots");
                status = false;
            }
        }catch(Exception e){
            System.out.println(e);
        }finally {
            db.closeConnection();
        }

        if( status ) {
            statusLabel.setText("All Ok to Generate Time Table");
            statusLabel.setForeground(Constant.GREEN);
        }else{
            statusLabel.setText("Error Detected : Cannot Generate Time Table");
            statusLabel.setForeground(Constant.RED);
            generateTimeTableButton.setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if( e.getSource() == generateTimeTableButton ){
            try {
                statusLabel.setText("Generating Time Table");


                //Generating Time Table
                CreatePDF pdfGenerator = new CreatePDF();
                pdfGenerator.start();
                pdfGenerator.join();

                statusLabel.setText("Time Table Generated");
            }catch(Exception excp){
                System.out.println(excp);
            }
        }
    }
}