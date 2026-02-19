public class Trainer {
    Environment environment;
    Arm arm;


    public Trainer(Arm arm) {
        this.environment = new Environment(arm);
        this.arm = arm;
    }

    public void loadModel(){
        environment.getAgent().loadNetworkFromFile();//load model
    }

    public void trainModel(int numOfEpisodes){
        for (int i = 0; i < numOfEpisodes; i++) {
            environment.initNewEpisode();
            int countSteps = 0;
            boolean done = false;
            while (!done && countSteps < Constants.MAX_STEPS_PER_EPISODE) {
                int actionIndex = environment.getAgent().makeAction();
                State currentState = environment.getAgent().getCurrentState().copy();//copy state so it won't point to the same address
                double reward = environment.step(actionIndex);
                State nextState = environment.getAgent().getCurrentState().copy();//copy state so it won't point to the same address
                done = reward == Constants.REACHED_POINT_REWARD;

                environment.getAgent().addToReplayBuffer(currentState, actionIndex, reward, nextState,done);

                if(environment.getAgent().getReplayBufferSize() > Constants.MIN_NUM_OF_TRANSITIONS){//wait for replay buffer to fill up
                    environment.getAgent().learn();//use the transitions to update the weights abd biases
                }

                if(countSteps % Constants.STEPS_TO_UPDATE_TARGET_NETWORK == 0)
                    environment.getAgent().updateTargetNetwork();


                countSteps++;
            }
        }
        environment.getAgent().saveNetworkToFile();
    }



    public double testModel(int numOfEpisodes){
        int sumSteps = 0;
        for (int i = 0; i < numOfEpisodes; i++) {
            environment.initNewEpisode();
            int countSteps = 0;
            boolean done = false;
            while (!done && countSteps < Constants.MAX_STEPS_PER_EPISODE) {
                int actionIndex = environment.getAgent().makeAction();
                System.out.println("joint: " + (1 + (actionIndex / 2)) +  " action index:" + actionIndex % 2);
                double reward = environment.step(actionIndex);
                System.out.println("reward: " + reward);
                done = reward == Constants.REACHED_POINT_REWARD;

                environment.printAnglesAndPositions();

                countSteps++;
            }
            sumSteps += countSteps;
        }
        return (double) sumSteps /numOfEpisodes;
    }
}
