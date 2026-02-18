//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Arm arm = new Arm();
        Trainer trainer = new Trainer(arm);
//        trainer.trainModel(100);

        trainer.loadModel();
        double avg_steps = trainer.testModel(100);
        System.out.println("avg_steps: " + avg_steps);
    }
}