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
        double targetX = random.nextDouble(Constants.MAX_TARGET_POSITION);
        double targetY = random.nextDouble(Constants.MAX_TARGET_POSITION);
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


        //check if possible(doesn't have collisions and doesn't hit the walls


        //need to make more scalable.
        switch (action){

            case ANGLE1DOWN -> agent.getCurrentState().setAngle1(agent.getCurrentState().getAngle1()-(Constants.ANGLE_CHANGE_STEP*Constants.STEP_SIZE));

            case ANGLE1UP -> agent.getCurrentState().setAngle1(agent.getCurrentState().getAngle1()+(Constants.ANGLE_CHANGE_STEP*Constants.STEP_SIZE));

            case ANGLE2DOWN -> agent.getCurrentState().setAngle2(agent.getCurrentState().getAngle2()-(Constants.ANGLE_CHANGE_STEP*Constants.STEP_SIZE));

            case ANGLE2UP -> agent.getCurrentState().setAngle2(agent.getCurrentState().getAngle2()+(Constants.ANGLE_CHANGE_STEP*Constants.STEP_SIZE));

        }


        return 0;
    }




    public Agent getAgent() {
        return agent;
    }
}
