package User_Interface;

import InverseKinematics.Arm;
import InverseKinematics.Trainer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class Controller {
    UserInterface userInterface;
    Arm arm;
    Trainer trainer;


    public Controller(Arm arm,UserInterface userInterface,Trainer trainer){
        this.arm = arm;
        this.userInterface = userInterface;
        this.trainer = trainer;


        userInterface.setArmBoundaries(arm.getMaxEnvironmentX(),arm.getMaxEnvironmentY()
                ,arm.getMinEnvironmentX(),arm.getMinEnvironmentY());

        //scroll bar used
        userInterface.setArmScrollBarListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                userInterface.updatePositionIndex(e.getValue());
                userInterface.updateInferenceData();
            }
        });

        //new episode button pressed.
        userInterface.setNewEpisodeButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userInterface.updateEpisodeInfo(trainer.newEpisode());
            }
        });

    }


    public void createWindow(){
        userInterface.CreateWindow();
    }







}
