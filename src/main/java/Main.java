import InverseKinematics.Arm;
import InverseKinematics.Trainer;
import User_Interface.Controller;
import User_Interface.UserInterface;

public class Main {
    public static void main(String[] args) {
        Arm arm = new Arm();
        Trainer trainer = new Trainer(arm);
        UserInterface userInterface = new UserInterface();
        Controller controller = new Controller(arm,userInterface);
        controller.createWindow();
//        trainer.loadModel();
//        trainer.trainModel(10000);//always change epsilon decay before changing
//
//
//
//        trainer.loadModel();
//        double avg_steps = trainer.testModel(1);
//        System.out.println("avg_steps: " + avg_steps);
        controller.updateArmState();



    }
}