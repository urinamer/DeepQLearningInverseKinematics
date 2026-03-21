import InverseKinematics.Arm;
import InverseKinematics.Trainer;
import User_Interface.Controller;
import User_Interface.UserInterface;

public class Main {
    public static void main(String[] args) {
        Arm arm = new Arm();
        UserInterface userInterface = new UserInterface();
        Trainer trainer = new Trainer(arm);
        Controller controller = new Controller(arm,userInterface,trainer);

//        controller.createWindow();
//        trainer.loadModel("2DOFTEST.csv");
//        trainer.trainModel(5000);//always change epsilon decay before changing
//        trainer.saveModel("2DOFTEST3.csv");

        trainer.loadModel("2DOFTEST2.csv");
        double avg_steps = trainer.testModel(100);
        System.out.println("avg_steps: " + avg_steps);

    }
}