import java.util.Random;
import org.apache.logging.log4j.*;

public class Agent {
    private static Agent agent;
    private State currentState;
    private Arm arm;

    private Network mainNetwork;
    private Network targetNetwork;
    private ReplayBuffer replayBuffer;


    //global variables save or not create over and over
    private float epsilon = 1;
    private Random random = new Random();


    //Logger stuff
    Logger logger = LogManager.getLogger(Agent.class);
    double sumQValue = 0;
    double sumLoss = 0;
    int learnCounter = 0;

    private Agent(Arm arm){
        currentState = null;
        this.arm = arm;
        replayBuffer = new ReplayBuffer();
        //should change to make scalable with more than 2 DOF
        mainNetwork = new Network(arm.getArmAngles().length+2,arm.getArmAngles().length*2,Constants.NUM_OF_LAYERS,Constants.NUM_OF_NEURONS_IN_LAYER);//
        targetNetwork = new Network(arm.getArmAngles().length+2,arm.getArmAngles().length*2,Constants.NUM_OF_LAYERS,Constants.NUM_OF_NEURONS_IN_LAYER);
        targetNetwork.copyNetwork(mainNetwork);
    }

    public static Agent getAgent(Arm arm){
        if(agent == null) {
           agent = new Agent(arm);
        }
        return agent;
    }


    //for testing
    public int makeAction(boolean useEpsilon){//returns QvalueIndexPair of chosen action
        return chooseBestAction(useEpsilon);
    }

    public int makeAction(){
        return chooseBestAction(true);
    }




    private int chooseBestAction(boolean useEpsilon){
        double[] inputs = convertFromStateToInputs(agent.currentState);
        double[] outputQValues =  mainNetwork.forwardPass(inputs);
        return chooseExploreExploit(outputQValues,useEpsilon);
    }

    //converts state to a double array with normalized inputs
    private double[] convertFromStateToInputs(State state){
        double[] inputs = new double[state.getAngles().length+2];
        for(int i = 0; i < currentState.getAngles().length; i++){
            inputs[i] = normalizeAngle(currentState.getAngles()[i]);
        }
        inputs[inputs.length-2] = normalizeX(currentState.getTargetX());
        inputs[inputs.length-1] = normalizeY(currentState.getTargetY());
        return inputs;
    }
    // normalize angle to 0-1. Should generalize.
    private double normalizeAngle(double angle){
            return (angle-Constants.MIN_ANGLE)/(Constants.MAX_ANGLE - Constants.MIN_ANGLE);
    }

    private double normalizeX(double x){
        return (x-Constants.MIN_ENVIRONMENT_X)/(Constants.MAX_ENVIRONMENT_X-Constants.MIN_ENVIRONMENT_X);
    }

    private double normalizeY(double y){
        return (y-Constants.MIN_ENVIRONMENT_Y)/(Constants.MAX_ENVIRONMENT_Y-Constants.MIN_ENVIRONMENT_Y);
    }



    private int chooseExploreExploit(double[] actionQValues,boolean useEpsilon){
        double num = random.nextDouble();
        int index;
        if(num >= epsilon || !useEpsilon){
            index = findIndexOfMax(actionQValues);
        }
        else{
            index = random.nextInt(arm.getArmAngles().length*2);
        }
        if(epsilon > 0.1f)
            epsilon *= Constants.EPSILON_DECAY;
        return index;
    }

    private static int findIndexOfMax(double[] numbers) {
        double maxVal = numbers[0];
        int maxIndex = 0;

        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] > maxVal) {
                maxVal = numbers[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }


    private static double findMax(double[] numbers){
        double max = numbers[0];
        for (int i = 1; i <numbers.length ; i++) {
            if (numbers[i] > max)
                max = numbers[i];
        }
        return max;
    }

    public void updateTargetNetwork(){
        targetNetwork.copyNetwork(mainNetwork);
    }

    public void addToReplayBuffer(State currentState, int actionIndex, double reward, State nextState,boolean isDone){
        replayBuffer.addToReplayBuffer(currentState,actionIndex,reward,nextState,isDone);
    }

    public int getReplayBufferSize(){
        return replayBuffer.getSize();
    }

    //calculates the error and updates the weights and biases
    public void learn(){
        for(int i = 0; i < Constants.BATCH_SIZE; i++){
            //Maybe shouldn't have connection from agent to BufferTransition class.
            BufferTransition bufferTransition = replayBuffer.getRandomFromReplayBuffer();
            double[] mainInputs = convertFromStateToInputs(bufferTransition.getCurrentState());
            double[] targetInputs = convertFromStateToInputs(bufferTransition.getNextState());
            double preQValue = mainNetwork.forwardPass(mainInputs)[bufferTransition.getActionIndex()];

            sumQValue += preQValue;
            learnCounter++;
            double avgQValue = sumQValue/learnCounter;

            //Bellman equation. Only add maxArg when not in the terminal state
            double targetQValue = bufferTransition.getReward() +
                    (bufferTransition.isDone() ? 0 :
                            Constants.DISCOUNT_FACTOR * findMax(targetNetwork.forwardPass(targetInputs)));


            double loss = Math.pow(preQValue-targetQValue,2);
            sumLoss += loss;
            double avgLoss = sumLoss/learnCounter;
            if(learnCounter % 500 == 0) {
                logger.info("avg Q Value updated: " + avgQValue);
                logger.info("avg loss: " + avgLoss);
            }


            double outputLayerDelta = 2*(preQValue-targetQValue);
            mainNetwork.backpropagation(mainInputs,outputLayerDelta,bufferTransition.getActionIndex());//update network sumGradients

        }

        mainNetwork.updateWeights(Constants.BATCH_SIZE);
    }


    public void saveNetworkToFile(){
        mainNetwork.save();
    }

    public void loadNetworkFromFile(){
        mainNetwork.load();
    }


    public Arm getArm(){
        return arm;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }


}
