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

        controller.createWindow();
        trainer.loadModel("anti_loop_test.csv");
//        System.out.println(trainer.testModel(1000));

//        trainer.trainModel(10000);
//        trainer.saveModel("anti_loop_test.csv");

    }
}