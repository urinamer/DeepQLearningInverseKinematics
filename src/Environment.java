import java.util.Random;
public class Environment {
    private Agent agent;
    private Random random;

    public Environment(Arm arm){
        agent = Agent.getAgent(arm);
        random = new Random();
    }

    public void initNewEpisode(){
        double targetX = random.nextDouble(Constants.MAX_ENVIRONMENT_X);
        double targetY = random.nextDouble(Constants.MAX_ENVIRONMENT_Y);
        agent.getArm().resetArm();

        // need to check collisions between links with forward kinematics.
        if(agent.getCurrentState() == null){
            agent.setCurrentState(new State(targetX,targetY,agent.getArm().getArmAngles()));
        }
        else {
            agent.getCurrentState().setTargetX(targetX);
            agent.getCurrentState().setTargetY(targetY);
            agent.getCurrentState().setAngles(agent.getArm().getArmAngles());
        }
    }

    public double step(int actionIndex){//returns reward

        int jointIndex = actionIndex / 2;// index calculation,each pair of indexes are two actions on the same angle
        double angleStep = (actionIndex % 2 == 0) ? Constants.ANGLE_CHANGE_STEP : Constants.ANGLE_CHANGE_STEP*-1;//first action in pair is UP, second one is DOWN
        double[] anglesCopy = new double[agent.getCurrentState().getAngles().length];
        System.arraycopy(agent.getCurrentState().getAngles(), 0, anglesCopy, 0, anglesCopy.length);//more efficient copying
        anglesCopy[jointIndex] += angleStep; //add angleStep based on the action chosen
        anglesCopy[jointIndex] = (anglesCopy[jointIndex]%360 +360) %360;


        double oldX = agent.getArm().getHandPointX();
        double oldY = agent.getArm().getHandPointY();
        double reward;

        // need to check collisions between links with forward kinematics.
        if(agent.getArm().calculateForwardKinematics(anglesCopy)){//if action didn't make the arm do something that is not possible
            reward = computeReward(oldX,oldY);
        }
        else{
            reward = Constants.HITTING_WALLS_PENALTY;//hit the wall or itself, doesn't change state and
        }

        System.out.println("angles " + getAgent().getCurrentState().getAngles()[0] + " , " + getAgent().getCurrentState().getAngles()[1]);
        return reward;

    }


    private double computeReward(double oldX, double oldY){
        double newDistance = Math.sqrt(Math.pow(agent.getArm().getHandPointX()-agent.getCurrentState().getTargetX(),2)+Math.pow(agent.getArm().getHandPointY()-agent.getCurrentState().getTargetY(),2));
        double currDistance = Math.sqrt(Math.pow(oldX -agent.getCurrentState().getTargetX(),2)+Math.pow(oldY-agent.getCurrentState().getTargetY(),2));
        //Maybe change to relation based reward

        if(newDistance <= Constants.DISTANCE_MIN_MARGIN)
            return Constants.REACHED_POINT_REWARD;
        if(currDistance  > newDistance)
            return Constants.REWARD;

        return Constants.PUNISHMENT;

    }

    public void printAnglesAndPositions(){
        for (int i = 0; i < agent.getArm().getArmAngles().length; i++){
            System.out.print(" Angle " + (i+1) + ": " + agent.getArm().getArmAngles()[i]);
        }
        System.out.println();
        System.out.println("X: " + agent.getArm().getHandPointX() + " Y: " + agent.getArm().getHandPointY());
    }

    public Agent getAgent() {
        return agent;
    }
}
