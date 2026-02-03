import java.util.Random;

public class Agent {
    private static Agent agent;
    private State currentState;
    private Action[] actions;
    private Arm arm;

    private Network mainNetwork;
    private Network targetNetwork;
    private ReplayBuffer replayBuffer;


    //global variables save or not create over and over
    private float epsilon = 1;
    private Random random = new Random();

    private Agent(){
        currentState = null;
        //should change to make scalable with more than 2 DOF
        mainNetwork = new Network(4,4,Constants.NUM_OF_LAYERS,Constants.NUM_OF_NEURONS_IN_LAYER);//
        actions = Action.values();
    }

    public static Agent getAgent(){
        if(agent == null) {
           agent = new Agent();
        }
        return agent;
    }



    public Action makeAction(){//returns QvalueIndexPair of chosen action
        return chooseBestAction();
    }


    private Action chooseBestAction(){
        //maybe should change...doesn't look really pretty and isn't scalable with more than 2 DOF
        double[] inputs = {currentState.getAngle1(),currentState.getAngle2(),currentState.getTargetX(),currentState.getTargetY()};
        double[] outputQValues =  mainNetwork.forwardPass(inputs);
        return chooseExploreExploit(outputQValues);
    }


    private Action chooseExploreExploit(double[] actionQValues){
        double num = random.nextDouble();
        Action action;
        if(num >= epsilon){
            int index = findIndexOfMax(actionQValues);
            action = actions[index];
        }
        else{
            int index = random.nextInt(actions.length);
            action = actions[index];
        }
        epsilon *= 0.995f;
        return action;
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



    public void addToReplayBuffer(State currentState, Action action, double reward, State nextState){
        replayBuffer.addToReplayBuffer(currentState,action,reward,nextState);
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
