import java.util.Random;

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

    private Agent(Arm arm){
        currentState = null;
        this.arm = arm;
        //should change to make scalable with more than 2 DOF
        mainNetwork = new Network(arm.getArmAngles().length+2,arm.getArmAngles().length*2,Constants.NUM_OF_LAYERS,Constants.NUM_OF_NEURONS_IN_LAYER);//
    }

    public static Agent getAgent(Arm arm){
        if(agent == null) {
           agent = new Agent(arm);
        }
        return agent;
    }



    public int makeAction(){//returns QvalueIndexPair of chosen action
        return chooseBestAction();
    }


    private int chooseBestAction(){
        //Really ugly code, don't like it might replace
        double[] inputs = new double[arm.getArmAngles().length+2];
        System.arraycopy(currentState.getAngles(), 0, inputs, 0, inputs.length-2);//more efficient copying
        inputs[inputs.length-2] = currentState.getTargetX();
        inputs[inputs.length-2] = currentState.getTargetY();
        double[] outputQValues =  mainNetwork.forwardPass(inputs);
        return chooseExploreExploit(outputQValues);
    }


    private int chooseExploreExploit(double[] actionQValues){
        double num = random.nextDouble();
        int index;
        if(num >= epsilon){
            index = findIndexOfMax(actionQValues);
        }
        else{
            index = random.nextInt(arm.getArmAngles().length*2);
        }
        epsilon *= 0.995f;
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



    public void addToReplayBuffer(State currentState, int actionIndex, double reward, State nextState){
        replayBuffer.addToReplayBuffer(currentState,actionIndex,reward,nextState);
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
