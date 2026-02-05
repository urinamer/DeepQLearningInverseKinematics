public class Trainer {
    Environment environment;
    Arm arm;


    public Trainer(Arm arm) {
        this.environment = new Environment(arm);
        this.arm = arm;
    }

    public void trainModel(int numOfEpisodes){
        boolean done = false;
        for (int i = 0; i < numOfEpisodes; i++) {
            int countSteps = Constants.MAX_STEPS_PER_EPISODE;
            while (!done || countSteps == 0) {
                environment.initNewEpisode();
                int actionIndex = environment.getAgent().makeAction();
                State currentState = environment.getAgent().getCurrentState();
                double reward = environment.step(actionIndex);
                State nextState = environment.getAgent().getCurrentState();

                done = reward == Constants.REACHED_POINT_REWARD;
                environment.getAgent().addToReplayBuffer(currentState, actionIndex, reward, nextState);

                countSteps--;
            }
        }
    }
}
