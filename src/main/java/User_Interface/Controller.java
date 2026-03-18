package User_Interface;

import InverseKinematics.Arm;

public class Controller {
    UserInterface userInterface;
    Arm arm;


    public Controller(Arm arm,UserInterface userInterface){
        this.arm = arm;
        this.userInterface = userInterface;
        userInterface.setArmBoundaries(arm.getMaxEnvironmentX(),arm.getMaxEnvironmentY()
                ,arm.getMinEnvironmentX(),arm.getMinEnvironmentY());


    }


    public void createWindow(){
        userInterface.CreateWindow();
    }

    public void updateTarget(double targetX,double targetY){
        userInterface.updateTarget(targetX,targetY);
    }

    public void updateArmState(){
        userInterface.updateArmState(arm.getArmJoints());
    }




}
