import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Neuron {


    private double[] weights;
    private final int numOfWeights;
    private double bias;//Maybe should be float

    private double sumWeights;
    public Neuron(int numOfWeights) {

        Random random = new Random();

        weights = new double[numOfWeights];
        this.numOfWeights = numOfWeights;

        for (int i =0; i < numOfWeights; i++){
            weights[i] = random.nextDouble();//Not sure if its correct
        }
        bias = Constants.BIAS_STARTER_VALUE;
    }


    public double calculateOutput(double[] inputs){
        double sum = 0;
        for(int i =0; i < numOfWeights; i++){
            sum += weights[i]*inputs[i];
        }
        sum += bias;
        return sum;
    }


    public static double calculateActivationFunction(double input){
        //Relu Activation function
        if (input >= 0)
            return input;
        return 0;
    }

    public static double activationFunctionDer(double input){
        if (input >= 0)
            return 1;
        return 0;
    }

    public double calculateSumErrors(double[] deltas){
        double sum = 0;
        for(int i =0; i < numOfWeights; i++){
            sum += weights[i]*deltas[i];
        }
        return sum;
    }

    public double[] getWeights() {
        return weights;
    }

    public void setWeight(int index,double value){
        weights[index] = value;
    }

    public int getNumOfWeights() {
        return numOfWeights;
    }

    public double getBias() {
        return bias;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }
}
