package InverseKinematics;

public class Trainer {
    Environment environment;
    Arm arm;


    public Trainer(Arm arm) {
        this.environment = new Environment(arm);
        this.arm = arm;
    }

    public void loadModel(){
        environment.getAgent().loadNetworkFromFile(Constants.NETWORK_FILE);//load model
    }

    public void trainModel(int numOfEpisodes){
        int countSteps = 0;
        for (int i = 0; i < numOfEpisodes; i++) {
            environment.initNewEpisode();
            int countEpisodeSteps = 0;
            boolean done = false;
            boolean doneIllegalMove = false;

            while (!done && !doneIllegalMove && countEpisodeSteps < Constants.MAX_STEPS_PER_EPISODE) {
                int actionIndex = environment.getAgent().makeAction();//action index in the output arr
                State currentState = environment.getAgent().getCurrentState().copy();//copy state so it won't point to the same address
                double[] rewardArr = environment.step(actionIndex);//updates current state inside it
                double reward = rewardArr[0];
                State nextState = environment.getAgent().getCurrentState().copy();//copy state so it won't point to the same address
                done = rewardArr[1] == 1;// is episode done,reached point and finished?
                doneIllegalMove = rewardArr[2] == 1;

                environment.getAgent().addToReplayBuffer(currentState, actionIndex, reward, nextState,done,doneIllegalMove);

                if(environment.getAgent().getReplayBufferSize() > Constants.MIN_NUM_OF_TRANSITIONS){//wait for replay buffer to fill up
                    environment.getAgent().learn();//use the transitions to update the weights abd biases
                }


                if(countSteps > 0 && countSteps % Constants.STEPS_TO_UPDATE_TARGET_NETWORK == 0)
                    environment.getAgent().updateTargetNetwork();

                countEpisodeSteps++;
                countSteps++;
            }

            environment.getAgent().decreaseEpsilon();//decay epsilon every episode
        }
        environment.getAgent().saveNetworkToFile(Constants.NETWORK_FILE);
    }



    public double testModel(int numOfEpisodes){
        int sumSteps = 0;
        for (int i = 0; i < numOfEpisodes; i++) {
            environment.initNewEpisode();
            int countSteps = 0;
            boolean done = false;
            while (!done && countSteps < Constants.MAX_STEPS_PER_EPISODE) {
                int actionIndex = environment.getAgent().makeAction(false);
                System.out.println("joint: " + (1 + (actionIndex / 2)) +  " action index:" + actionIndex % 2);
                double[] rewardArr = environment.step(actionIndex);
                System.out.println("reward: " + rewardArr[0]);
                done = rewardArr[1] == 1;

                environment.printAnglesAndPositions();

                countSteps++;
            }
            sumSteps += countSteps;
        }
        return (double) sumSteps /numOfEpisodes;
    }
}
