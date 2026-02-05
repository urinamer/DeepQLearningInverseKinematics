public class Network {

    private Neuron[][] layers;
    private int numOfOutputs;
    private int numOfInputs;
    private int numOfLayers;
    private int numOfNeuronsInLayer;

    public Network(int numOfInputs,int numOfOutputs,int numOfLayers,int numOfNeuronsInLayer){
        layers = new Neuron[numOfLayers][];
        for (int i = 0; i < numOfLayers-1; i++) {
            layers[i] = new Neuron[numOfNeuronsInLayer];
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
                    layerOutputs[j] = Neuron.calculateActivationFunction(output);
                } else {
                    layerOutputs[j] = output; //without activation function
                }
            }
            currentInputs = layerOutputs;
        }
        return currentInputs;
    }


    public void copyNetwork(Network src){//copies src weight values to this network
        for (int i = 0; i < numOfLayers-1; i++) {
            for (int j = 0; j < numOfNeuronsInLayer; j++) {
                this.layers[i][j].setBias(src.layers[i][j].getBias());
                for(int k = 0; k < layers[i][j].getNumOfWeights(); k++){
                    this.layers[i][j].getWeights()[k] = src.layers[i][j].getWeights()[k];
                }
            }
        }
    }

    public int getNumOfLayers() {
        return numOfLayers;
    }

    public void setNumOfLayers(int numOfLayers) {
        this.numOfLayers = numOfLayers;
    }

    public int getNumOfNeuronsInLayer() {
        return numOfNeuronsInLayer;
    }

    public void setNumOfNeuronsInLayer(int numOfNeuronsInLayer) {
        this.numOfNeuronsInLayer = numOfNeuronsInLayer;
    }

    public int getNumOfInputs() {
        return numOfInputs;
    }

    public void setNumOfInputs(int numOfInputs) {
        this.numOfInputs = numOfInputs;
    }

    public int getNumOfOutputs() {
        return numOfOutputs;
    }

    public void setNumOfOutputs(int numOfOutputs) {
        this.numOfOutputs = numOfOutputs;
    }
}
