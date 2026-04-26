package InverseKinematics;

import User_Interface.EpisodeStep;

import java.util.ArrayList;

public class Trainer {
    Environment environment;



    public Trainer(Agent agent) {
        this.environment = new Environment(agent);
    }

    public void loadModel(){
        environment.getAgent().loadNetworkFromFile(Constants.MODELS_FOLDER_PATH + Constants.DEFAULT_NETWORK_FILE);//load model
    }

    public void loadModel(String fileName){
        environment.getAgent().loadNetworkFromFile(Constants.MODELS_FOLDER_PATH + fileName);//load model
    }

    public void saveModel(){
        environment.getAgent().saveNetworkToFile(Constants.MODELS_FOLDER_PATH + Constants.DEFAULT_NETWORK_FILE);
    }

    public void saveModel(String fileName){
        environment.getAgent().saveNetworkToFile(Constants.MODELS_FOLDER_PATH + fileName);
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
    }



    public double testModel(int numOfEpisodes){
        int sumSteps = 0;
        int numOfSuccess = 0;
        for (int i = 0; i < numOfEpisodes; i++) {
            environment.initNewEpisode();
            System.out.println("Episode: " + (i+1));

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
            if(done){
                numOfSuccess++;
            }
        }
        System.out.println("num of success: " + numOfSuccess);
        return (double) sumSteps /numOfEpisodes;
    }


    public ArrayList<EpisodeStep> newEpisode(){
        environment.initNewEpisode();
        return newEpisodeLogic();
    }

    public ArrayList<EpisodeStep> newEpisode(double targetX, double targetY){
        environment.initNewEpisode(targetX,targetY);
        return newEpisodeLogic();
    }

    private ArrayList<EpisodeStep> newEpisodeLogic(){
        ArrayList<EpisodeStep> episodeSteps = new ArrayList<>();
        int countSteps = 0;
        boolean done = false;
        while (!done && countSteps < Constants.MAX_STEPS_PER_EPISODE) {
            State currentState = environment.getAgent().getCurrentState();
            int actionIndex = environment.getAgent().makeAction(false);
            double qValue = environment.getAgent().getQvalue(currentState, actionIndex);
            episodeSteps.add(new EpisodeStep(
                    environment.getAgent().getArm().getArmJoints(),
                    environment.getAgent().getArm().getArmAngles().clone(), // חשוב להשתמש ב-clone כדי שהערכים לא ישתנו
                    qValue,
                    currentState.getTargetX(),currentState.getTargetY()
            ));

            double[] rewardArr = environment.step(actionIndex);
            done = rewardArr[1] == 1;
            countSteps++;
        }
        System.out.println("num of steps: " + countSteps);
        return episodeSteps;
    }



}
