public class Network {

    private Neuron[][] network;



    public Network(int numOfInputs,int numOfOutputs,int numOfLayers,int numOfNeuronsInLayer){
        network = new Neuron[numOfLayers][];
        for (int i = 0; i < numOfLayers-1; i++) {
            network[i] = new Neuron[numOfNeuronsInLayer];
            for (int j = 0; j < numOfNeuronsInLayer; j++) {
                Neuron neuron;
                if(j == 0){
                    neuron = new Neuron(numOfInputs);
                }
                else {
                    neuron = new Neuron(numOfNeuronsInLayer);
                }
                network[i][j] = neuron;
            }
        }

        for (int k = 0; k < numOfOutputs;k++){
            Neuron outputNeuron = new Neuron(numOfOutputs);
            network[numOfLayers-1][k] = outputNeuron;
        }
    }

    public double[] forwardPass(double[] inputs) {
        double[] currentInputs = inputs;
        for (int i = 0; i < network.length; i++) {
            double[] layerOutputs = new double[network[i].length];
            for (int j = 0; j < network[i].length; j++) {
                double output = network[i][j].calculateOutput(currentInputs);
                if (i < network.length - 1) {//apply activation function only to hidden layers
                    layerOutputs[j] = Neuron.calculateActivationFunction(output);
                } else {
                    layerOutputs[j] = output; //without activation function
                }
            }
            currentInputs = layerOutputs;
        }
        return currentInputs;
    }



}
