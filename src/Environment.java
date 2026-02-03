import java.util.Random;
public class Environment {
    private Agent agent;

    private Random random;

    public Environment(){
        agent = Agent.getAgent();
        random = new Random();
    }

    public void initNewEpisode(){
        //needs to change for scalability for more then 2 DOF
        double targetX = random.nextDouble(Constants.MAX_ENVIRONMENT_X);
        double targetY = random.nextDouble(Constants.MAX_ENVIRONMENT_Y);
        double angle1 = random.nextDouble(360);
        double angle2 = random.nextDouble(360);

        //for more than 2 DOF need to check collisions between links with forward kinematics

        if(agent.getCurrentState() == null){
            agent.setCurrentState(new State(targetX,targetY,angle1,angle2));
        }
        else {
            agent.getCurrentState().setTargetX(targetX);
            agent.getCurrentState().setTargetY(targetY);
            agent.getCurrentState().setAngle1(angle1);
            agent.getCurrentState().setAngle2(angle2);
        }
    }

    public double step(Action action){//returns reward


        switch (action){

            case ANGLE1DOWN -> agent.getCurrentState().setAngle1(agent.getCurrentState().getAngle1()-(Constants.ANGLE_CHANGE_STEP*Constants.STEP_SIZE));

            case ANGLE1UP -> agent.getCurrentState().setAngle1(agent.getCurrentState().getAngle1()+(Constants.ANGLE_CHANGE_STEP*Constants.STEP_SIZE));

            case ANGLE2DOWN -> agent.getCurrentState().setAngle2(agent.getCurrentState().getAngle2()-(Constants.ANGLE_CHANGE_STEP*Constants.STEP_SIZE));

            case ANGLE2UP -> agent.getCurrentState().setAngle2(agent.getCurrentState().getAngle2()+(Constants.ANGLE_CHANGE_STEP*Constants.STEP_SIZE));

        }

        //check if possible(doesn't have collisions and doesn't hit the walls


        //need to make more scalable.



        return 0;
    }


    private double calculateForwardKinematics(){
        double currentX = agent.getArm().getBasePointX();
        double currentY = agent.getArm().getBasePointY();
        //            for more than 2 DOF
//      for(int i =0; i < agent.getArm().getNumOfLinks(); i++){
//           currentX += agent.getArm().getLinkLengths()[i]*Math.cos(agent.getCurrentState().angles[i]);
//           currentY += agent.getArm().getLinkLengths()[i]*Math.sin(agent.getCurrentState().angles[i]);
//       }

        currentX += agent.getArm().getLinkLengths()[0]*Math.cos(agent.getCurrentState().getAngle1());
        currentY += agent.getArm().getLinkLengths()[0]*Math.sin(agent.getCurrentState().getAngle1());
        currentX += agent.getArm().getLinkLengths()[1]*Math.cos(agent.getCurrentState().getAngle2());
        currentY += agent.getArm().getLinkLengths()[1]*Math.sin(agent.getCurrentState().getAngle2());

        double reward;
        if(currentX < Constants.MAX_ENVIRONMENT_X && currentX > 0 && currentY < Constants.MAX_ENVIRONMENT_Y && currentY > 0){
            reward = computeReward(currentX,currentY);
        }
        else {
            reward = Constants.HITTING_WALLS_PENALTY;
        }


        return reward;
    }


    private double computeReward(double x,double y){
        double currDistance = Math.sqrt(Math.pow(agent.getArm().getHandPointX()-agent.getCurrentState().getTargetX(),2)+Math.pow(y-agent.getCurrentState().getTargetY(),2));
        double newDistance = Math.sqrt(Math.pow(x-agent.getCurrentState().getTargetX(),2)+Math.pow(y-agent.getCurrentState().getTargetY(),2));
        //Maybe change to relation based reward
        if()
    }



    public Agent getAgent() {
        return agent;
    }
}
