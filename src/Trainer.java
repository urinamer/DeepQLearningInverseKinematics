public class Trainer {
    Environment environment;
    Arm arm;


    public Trainer(Arm arm) {
        this.environment = new Environment(arm);
        this.arm = arm;
    }

    public void trainModel(int numOfEpisodes){
        int countSteps = 0;
        for (int i = 0; i < numOfEpisodes; i++) {
            boolean done = false;
            while (!done || countSteps % Constants.MAX_STEPS_PER_EPISODE == 0) {
                environment.initNewEpisode();
                int actionIndex = environment.getAgent().makeAction();
                State currentState = environment.getAgent().getCurrentState();
                double reward = environment.step(actionIndex);
                State nextState = environment.getAgent().getCurrentState();

                environment.getAgent().addToReplayBuffer(currentState, actionIndex, reward, nextState);
                if(countSteps % Constants.STEPS_TO_UPDATE_TARGET_NETWORK == 0)
                    environment.getAgent().updateTargetNetwork();


                done = reward == Constants.REACHED_POINT_REWARD;
                countSteps++;
            }
        }
    }
}
