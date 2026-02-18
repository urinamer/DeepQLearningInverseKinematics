public class Trainer {
    Environment environment;
    Arm arm;


    public Trainer(Arm arm) {
        this.environment = new Environment(arm);
        this.arm = arm;
    }

    public void trainModel(int numOfEpisodes){
        environment.getAgent().loadNetworkFromFile();
        for (int i = 0; i < numOfEpisodes; i++) {
            environment.initNewEpisode();
            int countSteps = 0;
            boolean done = false;
            while (!done && countSteps < Constants.MAX_STEPS_PER_EPISODE) {
                int actionIndex = environment.getAgent().makeAction();
                State currentState = environment.getAgent().getCurrentState();
                double reward = environment.step(actionIndex);
                State nextState = environment.getAgent().getCurrentState();
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
}
