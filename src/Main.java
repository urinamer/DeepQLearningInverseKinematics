//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Arm arm = new Arm();
        Trainer trainer = new Trainer(arm);
        trainer.trainModel(1);
    }
}