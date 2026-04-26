import InverseKinematics.Agent;
import InverseKinematics.Arm;
import InverseKinematics.Trainer;
import User_Interface.Controller;
import User_Interface.UserInterface;

public class Main {
    public static void main(String[] args) {
        Arm arm = new Arm(0,0,2,new double[]{5,5});
        UserInterface userInterface = new UserInterface();

        Agent agent = new Agent(arm);
        Trainer trainer = new Trainer(agent);

        Controller controller = new Controller(arm,userInterface,trainer);

        controller.createWindow();
        trainer.loadModel("2DOF99PercentAcc.csv");
//        trainer.trainModel(10000);
//        trainer.saveModel("3DOFTEST.csv");
    }
}