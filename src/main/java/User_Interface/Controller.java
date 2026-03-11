package User_Interface;

import InverseKinematics.Arm;

public class Controller {
    UserInterface userInterface;
    Arm arm;


    public Controller(Arm arm,UserInterface userInterface){
        this.arm = arm;
        this.userInterface = userInterface;
    }


    public void createWindow(){
        userInterface.CreateWindow();
    }


    public void updateArmState(){
        userInterface.updateArmState(arm.getBasePointX(),arm.getBasePointY(),arm.getArmAngles(),arm.getLinkLengths(),arm.getMaxEnvironmentX(),arm.getMaxEnvironmentY()
                ,arm.getMinEnvironmentX(),arm.getMinEnvironmentY());
    }

//    private double linearMappingX(double x){
//        x = ((x-minArmX)*(UserInterfaceConstants.MAX_ENVIRONMENT_SIZE))/(maxArmX-minArmX);
//        return x;
//    }
//
//    private double linearMappingY(double y){
//        y = ((y-minArmY)*(UserInterfaceConstants.MAX_ENVIRONMENT_SIZE))/(maxArmY-minArmY);
//        return UserInterfaceConstants.MAX_ENVIRONMENT_SIZE - y;
//    }
}
