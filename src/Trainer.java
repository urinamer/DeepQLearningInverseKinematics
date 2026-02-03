public class Trainer {
    Environment environment;


    public Trainer() {
        this.environment = new Environment();
    }

    public void trainModel(int numOfEpisodes){
        for (int i = 0; i < numOfEpisodes; i++) {
            environment.initNewEpisode();
            Action action  = environment.getAgent().makeAction();
            State currentState = environment.getAgent().getCurrentState();
            double reward = environment.step(action);
            State nextState = environment.getAgent().getCurrentState();

            environment.getAgent().addToReplayBuffer(currentState,action,reward,nextState);
        }
    }
}
