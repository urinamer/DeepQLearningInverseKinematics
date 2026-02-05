public class Trainer {
    Environment environment;
    Arm arm;


    public Trainer(Arm arm) {
        this.environment = new Environment(arm);
        this.arm = arm;
    }

    public void trainModel(int numOfEpisodes){

        for (int i = 0; i < numOfEpisodes; i++) {
            boolean done = false;
            int countSteps = Constants.MAX_STEPS_PER_EPISODE;
            while (!done || countSteps == 0) {
                environment.initNewEpisode();
                int actionIndex = environment.getAgent().makeAction();
                State currentState = environment.getAgent().getCurrentState();
                double reward = environment.step(actionIndex);
                State nextState = environment.getAgent().getCurrentState();

                environment.getAgent().addToReplayBuffer(currentState, actionIndex, reward, nextState);



                done = reward == Constants.REACHED_POINT_REWARD;
                countSteps--;
            }
        }
    }
}
