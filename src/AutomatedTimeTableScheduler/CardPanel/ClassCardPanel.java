package AutomatedTimeTableScheduler.CardPanel;

import AutomatedTimeTableScheduler.Database.DatabaseCon;
import AutomatedTimeTableScheduler.Panels.ClassPanel;
import AutomatedTimeTableScheduler.Static.Constant;
import AutomatedTimeTableScheduler.Static.Constraint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClassCardPanel extends JPanel implements ActionListener {

    private JLabel yearLabel,divisionLabel;
    private JButton deleteClassButton;
    private DatabaseCon db;
    private ClassPanel parentPanel;
    private int year;
    private String division;

    public ClassCardPanel(int year, String division, ClassPanel parentPanel){
        this.year = year;
        this.division = division;
        this.parentPanel = parentPanel;
        //Initialising Members
        yearLabel = new JLabel("Year : "+year);
        divisionLabel = new JLabel("Division : "+division);
        deleteClassButton = new JButton("Delete");

        //Editing Members
        deleteClassButton.setBackground(Constant.RED);

        //Adding Listeners
        deleteClassButton.addActionListener(this);

        //Editing Panel
        setLayout(new GridBagLayout());
        setBackground(Constant.PANEL_BACKGROUND);

        //Adding Members to Panel
        add(yearLabel, Constraint.setPosition(0,0));
        add(divisionLabel,Constraint.setPosition(1,0));
        add(deleteClassButton,Constraint.setPosition(2,0));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if( e.getSource() == deleteClassButton ){
            try{
                db = new DatabaseCon();
                db.deleteClass(year,division);
                parentPanel.displayClass();
            }catch (Exception excp){
                System.out.println(excp);
            }finally {
                db.closeConnection();
            }
        }
    }
}
