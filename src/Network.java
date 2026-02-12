public class Network {

    private Neuron[][] layers;
    private double[][] layerOutputs;
    private int numOfInputs;


    public Network(int numOfInputs,int numOfOutputs,int numOfLayers,int numOfNeuronsInLayer){
        this.numOfInputs = numOfInputs;
        layers = new Neuron[numOfLayers][];
        layerOutputs = new double[layers.length][];
        for (int i = 0; i < numOfLayers-1; i++) {
            layers[i] = new Neuron[numOfNeuronsInLayer];
            layerOutputs[i] = new double[numOfNeuronsInLayer];
            for (int j = 0; j < numOfNeuronsInLayer; j++) {
                Neuron neuron;
                if(j == 0){
                    neuron = new Neuron(numOfInputs);
                }
                else {
                    neuron = new Neuron(numOfNeuronsInLayer);
                }
                layers[i][j] = neuron;
            }
        }

        for (int k = 0; k < numOfOutputs;k++){
            Neuron outputNeuron = new Neuron(numOfOutputs);
            layers[numOfLayers-1][k] = outputNeuron;
        }
    }

    public double[] forwardPass(double[] inputs) {
        double[] currentInputs = inputs;
        for (int i = 0; i < layers.length; i++) {
            double[] layerOutputs = new double[layers[i].length];
            for (int j = 0; j < layers[i].length; j++) {
                double output = layers[i][j].calculateOutput(currentInputs);
                if (i < layers.length - 1) {//apply activation function only to hidden layers
                    output = Neuron.calculateActivationFunction(output);
                }
                layerOutputs[j] = output;
                this.layerOutputs[i][j] = output;//without activation function
            }
            currentInputs = layerOutputs;
        }
        return currentInputs;
    }


    public void copyNetwork(Network src){//copies src weight values to this network
        for (int i = 0; i < layers.length; i++) {
            for (int j = 0; j < layers[i].length; j++) {
                this.layers[i][j].setBias(src.layers[i][j].getBias());
                for(int k = 0; k < layers[i][j].getNumOfWeights(); k++){
                    this.layers[i][j].getWeights()[k] = src.layers[i][j].getWeights()[k];
                }
            }
        }
    }

    //uses backpropagation to get all deltas in a transition.
    // returns an array containing the deltas for the weights and deltas for the biases
    public double[][][][] getDerivatives(double[] rawInputState,double initialLoss) {
        double[][] deltas = new double[layers.length][];
        double[][][] weightsGradients = new double[layers.length][][];
        double[][] biasGradients = new double[layers.length][];

        for (int i = layers.length - 1; i >= 0; i--) {
            int numInputs = (i == 0) ? numOfInputs : layers[i - 1].length;
            deltas[i] = new double[layers[i].length];
            weightsGradients[i] = new double[layers[i].length][numInputs];
            biasGradients[i] = new double[layers[i].length];
            for (int j = 0; j < layers[i].length; j++) {
                //calculate Error signal
                biasGradients[i][j] = initialLoss;
                double error;
                if (i == layers.length - 1) {
                    error = initialLoss;
                } else {
                    //pull error from layer ahead
                    error = layers[i][j].calculateSumErrors(deltas[i + 1]);
                }
                deltas[i][j] = error * Neuron.activationFunctionDer(layerOutputs[i][j]);// all error signals
                biasGradients[i][j] = deltas[i][j];


                for (int k = 0; k < numInputs; k++) {
                    double inputVal = (i == 0) ? rawInputState[k] : layerOutputs[i - 1][k];
                    weightsGradients[i][j][k] = deltas[i][j] * inputVal;
                }
            }
        }
        return new double[][][][]{weightsGradients, {biasGradients}};
    }



    public void updateWeights(double[][][] totalGradient){

    }
}

