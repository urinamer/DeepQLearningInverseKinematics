package Library;

import InverseKinematics.Constants;

import java.util.Random;

public class Neuron {


    private double[] weights;
    private final int numOfWeights;
    private double bias;//Maybe should be float
    Random random = new Random();

    public Neuron(int numOfWeights) {

        weights = new double[numOfWeights];
        this.numOfWeights = numOfWeights;
        double standardDeviation = Math.sqrt(2.0/numOfWeights);

        for (int i =0; i < numOfWeights; i++){
            weights[i] = random.nextGaussian() * standardDeviation;
        }
        bias = NetworkConstants.BIAS_STARTER_VALUE;
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
        //Leaky Relu Activation function
        if (input > 0)
            return input;
        return NetworkConstants.LEAKY_RELU_K * input;
    }

    public static double activationFunctionDer(double input){
        if (input > 0)
            return 1;
        return NetworkConstants.LEAKY_RELU_K;
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
