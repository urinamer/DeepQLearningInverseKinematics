package InverseKinematics;

import java.util.Random;
public class Environment {
    private Agent agent;
    private Random random;

    private double lastIndexChange;
    private double lastAngleChange;



    public Environment(Arm arm){
        agent = Agent.getAgent(arm);
        random = new Random();
    }

    public void initNewEpisode(){
        double targetX;
        double targetY;
        do {
            targetX = Constants.MIN_ENVIRONMENT_X + random.nextDouble() * (Constants.MAX_ENVIRONMENT_X-Constants.MIN_ENVIRONMENT_X);
            targetY = Constants.MIN_ENVIRONMENT_Y + random.nextDouble() * (Constants.MAX_ENVIRONMENT_Y-Constants.MIN_ENVIRONMENT_Y);

        }while (!agent.getArm().isPointReachable(targetX,targetY));


        agent.getArm().resetArm();

        lastIndexChange = -1;
        lastAngleChange = 0;

        double diffX = targetX - agent.getArm().getHandPointX();
        double diffY = targetY - agent.getArm().getHandPointY();

        if(agent.getCurrentState() == null){
            agent.setCurrentState(new State(targetX,targetY,diffX,diffY,agent.getArm().getArmAngles()));
        }
        else {
            agent.getCurrentState().setTargetX(targetX);
            agent.getCurrentState().setTargetY(targetY);
            agent.getCurrentState().setAngles(agent.getArm().getArmAngles());
            agent.getCurrentState().setDiffX(diffX);
            agent.getCurrentState().setDiffY(diffY);
        }

    }


    public double[] step(int actionIndex){//returns reward, is done and if did illegal move
        //dynamic stepSize.
        double stepSize;
        double distance = Math.sqrt(Math.pow(agent.getArm().getHandPointX()-agent.getCurrentState().getTargetX(),2)+Math.pow(agent.getArm().getHandPointY()-agent.getCurrentState().getTargetY(),2));
        if(distance > Constants.DISTANCE_CLOSE)
            stepSize = Constants.STEP_SIZE;
        else
            stepSize = Constants.STEP_SIZE_CLOSE;

        int jointIndex = actionIndex / 2;// index calculation,each pair of indexes are two actions on the same angle
        double angleStep = (actionIndex % 2 == 0) ? stepSize : stepSize*-1;//first action in pair is UP, second one is DOWN

        //apply action to angle
        double[] anglesCopy = new double[agent.getCurrentState().getAngles().length];
        System.arraycopy(agent.getCurrentState().getAngles(), 0, anglesCopy, 0, anglesCopy.length);//more efficient copying
        anglesCopy[jointIndex] += angleStep; //add angleStep based on the action chosen
        anglesCopy[jointIndex] = (anglesCopy[jointIndex]%360 +360) %360;//normalizing angles to 0 - 360 degrees


        double oldX = agent.getArm().getHandPointX();
        double oldY = agent.getArm().getHandPointY();
        double reward;
        int done;
        double[] rewardDoneIllegalArr = new double[3];


        if(agent.getArm().calculateForwardKinematics(anglesCopy)){//if action didn't make the arm do something that is not possible
            double[] computedReward = computeReward(oldX,oldY,jointIndex,angleStep);//returns reward and if it reached the target
            reward = computedReward[0];
            if(computedReward[1] == 1)
                done = 1;
            else
                done = 0;
            rewardDoneIllegalArr[2] = 0;
        }
        else{
            reward = Constants.HITTING_WALLS_PENALTY;//hit the wall or itself, doesn't change state.
            rewardDoneIllegalArr[2] = 1;
            done = 0;
        }
        rewardDoneIllegalArr[0] = reward;
        rewardDoneIllegalArr[1] = done;

        double diffX = agent.getCurrentState().getTargetX() - agent.getArm().getHandPointX();
        double diffY = agent.getCurrentState().getTargetY() - agent.getArm().getHandPointY();
        agent.getCurrentState().setDiffX(diffX);
        agent.getCurrentState().setDiffY(diffY);

        lastIndexChange = jointIndex;
        lastAngleChange = angleStep;



        return rewardDoneIllegalArr;

    }


    private double[] computeReward(double oldX,double oldY,double currentIndexChange,double currentAngleStep){// returns double array where [0] is reward and [1] is if it reached the target
        double newDistance = Math.sqrt(Math.pow(agent.getArm().getHandPointX()-agent.getCurrentState().getTargetX(),2)+Math.pow(agent.getArm().getHandPointY()-agent.getCurrentState().getTargetY(),2));
        double currDistance = Math.sqrt(Math.pow(oldX -agent.getCurrentState().getTargetX(),2)+Math.pow(oldY-agent.getCurrentState().getTargetY(),2));

        //if the arm reached the target, return reward and done = 1
        if(newDistance <= Constants.MIN_DISTANCE)
            return new double[]{Constants.REACHED_POINT_REWARD,1};
        //if agent did inverse actions one after another return punishment.
        if(currentIndexChange == lastIndexChange && currentAngleStep == -lastAngleChange)
            return new double[]{Constants.REPEATED_ACTION_PENALTY,0};

        int rewardMultiplier = Constants.REWARD_MULTIPLIER;
        if (newDistance < Constants.DISTANCE_CLOSE)
            rewardMultiplier = Constants.REWARD_MULTIPLIER_CLOSE ;
        double reward = (currDistance-newDistance)*rewardMultiplier + Constants.TIME_WASTED_PENALTY;
        return new double[]{reward,0};
    }

    public void printAnglesAndPositions(){
        System.out.println("TargetX: " + agent.getCurrentState().getTargetX() + " targetY: " + agent.getCurrentState().getTargetY());
        for (int i = 0; i < agent.getArm().getArmAngles().length; i++){
            System.out.print(" Angle " + (i+1) + ": " + agent.getArm().getArmAngles()[i]);
        }
        System.out.println();
        System.out.println("X: " + agent.getArm().getHandPointX() + " Y: " + agent.getArm().getHandPointY());
        double distance = Math.sqrt(Math.pow(agent.getArm().getHandPointX()-agent.getCurrentState().getTargetX(),2)+Math.pow(agent.getArm().getHandPointY()-agent.getCurrentState().getTargetY(),2));
        System.out.println("Distance From target: " + distance);
        System.out.println("-----------------------");
    }

    public Agent getAgent() {
        return agent;
    }
}
