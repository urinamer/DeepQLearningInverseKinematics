import InverseKinematics.Arm;
import InverseKinematics.Trainer;
import User_Interface.Controller;
import User_Interface.UserInterface;

public class Main {
    public static void main(String[] args) {
        Arm arm = new Arm();
        UserInterface userInterface = new UserInterface();
        Controller controller = new Controller(arm,userInterface);
        Trainer trainer = new Trainer(arm,controller);
        controller.createWindow();
//        trainer.loadModel();
        trainer.trainModel(1);//always change epsilon decay before changing
//
//
//
/*        trainer.loadModel();
        double avg_steps = trainer.testModel(100);
        System.out.println("avg_steps: " + avg_steps);*/




    }
}