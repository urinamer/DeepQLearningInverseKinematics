
public class Main {
    public static void main(String[] args) {
        Arm arm = new Arm();
        Trainer trainer = new Trainer(arm);
//        trainer.trainModel(1000);

        trainer.loadModel();
        double avg_steps = trainer.testModel(100);
        System.out.println("avg_steps: " + avg_steps);
    }
}