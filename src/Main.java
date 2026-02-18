//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Network network = new Network(4,4,2,10);
        network.printGradients();
        double[] state = {10,20,5,4};
        network.backpropagation(state,10,2);
        network.printGradients();

    }
}