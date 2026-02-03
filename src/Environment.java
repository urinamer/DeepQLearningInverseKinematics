import java.util.Random;
public class Environment {
    private Agent agent;

    private Random random;

    public Environment(Arm arm){
        agent = Agent.getAgent(arm);
        random = new Random();
    }

    public void initNewEpisode(){
        //needs to change for scalability for more then 2 DOF
        double targetX = random.nextDouble(Constants.MAX_ENVIRONMENT_X);
        double targetY = random.nextDouble(Constants.MAX_ENVIRONMENT_Y);
        double[] angles = new double[agent.getArm().getArmAngles().length];
        System.arraycopy(agent.getArm().getArmAngles(), 0, angles, 0, angles.length);//more efficient copying



        //for more than 2 DOF need to check collisions between links with forward kinematics

        if(agent.getCurrentState() == null){
            agent.setCurrentState(new State(targetX,targetY,angles));
        }
        else {
            agent.getCurrentState().setTargetX(targetX);
            agent.getCurrentState().setTargetY(targetY);
            agent.getCurrentState().setAngles(angles);
        }
    }

    public double step(int actionIndex){//returns reward

        int jointIndex = actionIndex / 2;
        double angleStep = (actionIndex % 2 == 0) ? Constants.ANGLE_CHANGE_STEP : Constants.ANGLE_CHANGE_STEP*-1;
        double[] anglesCopy = new double[agent.getCurrentState().getAngles().length];
        System.arraycopy(agent.getCurrentState().getAngles(), 0, anglesCopy, 0, anglesCopy.length);//more efficient copying
        anglesCopy[jointIndex] += angleStep;//change angle
        double oldX = agent.getArm().getHandPointX();
        double oldY = agent.getArm().getHandPointY();
        double reward;

        if(calculateForwardKinematics(anglesCopy)){
            reward = computeReward(oldX,oldY);
        }
        else{
            reward = Constants.HITTING_WALLS_PENALTY;
        }

        return reward;

    }


    private boolean calculateForwardKinematics(double[] angles){//returns is legal and updates currentState
        double currentX = agent.getArm().getBasePointX();
        double currentY = agent.getArm().getBasePointY();
        //for more than 2 DOF
      for(int i =0; i < agent.getArm().getNumOfLinks(); i++){
           currentX += agent.getArm().getLinkLengths()[i]*Math.cos(angles[i]);
           currentY += agent.getArm().getLinkLengths()[i]*Math.sin(angles[i]);
       }


        if(currentX < Constants.MAX_ENVIRONMENT_X && currentX > 0 && currentY < Constants.MAX_ENVIRONMENT_Y && currentY > 0){
            agent.getCurrentState().setAngles(angles);
            return true;
        }
        return false;

    }

    private double computeReward(double oldX, double oldY){
        double newDistance = Math.sqrt(Math.pow(agent.getArm().getHandPointX()-agent.getCurrentState().getTargetX(),2)+Math.pow(oldY-agent.getCurrentState().getTargetY(),2));
        double currDistance = Math.sqrt(Math.pow(oldX -agent.getCurrentState().getTargetX(),2)+Math.pow(oldY-agent.getCurrentState().getTargetY(),2));
        //Maybe change to relation based reward
        if(currDistance  > newDistance)
            return Constants.REWARD;
        return Constants.PUNISHMENT;

    }



    public Agent getAgent() {
        return agent;
    }
}
