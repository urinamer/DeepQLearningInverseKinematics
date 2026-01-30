import java.util.Random;

public class Agent {
    private static Agent agent;
    private State currentState;
    private Action[] actions;


    Random random = new Random();

    private Agent(){
        currentState = null;
        actions = Action.values();
    }

    public static Agent getAgent(){
        if(agent == null)
            return new Agent();
        else
            return agent;
    }



    public double makeAction(Action action){//Returns reward


        switch (action){
            case ANGLE1DOWN -> currentState.setAngle1(currentState.getAngle1()-(Constants.ANGLE_CHANGE_STEP*Constants.LEARNING_RATE));
            case ANGLE1UP -> currentState.setAngle1(currentState.getAngle1()+(Constants.ANGLE_CHANGE_STEP*Constants.LEARNING_RATE));
            case ANGLE2DOWN -> currentState.setAngle2(currentState.getAngle2()-(Constants.ANGLE_CHANGE_STEP*Constants.LEARNING_RATE));
            case ANGLE2UP -> currentState.setAngle2(currentState.getAngle2()+(Constants.ANGLE_CHANGE_STEP*Constants.LEARNING_RATE));
        }



        return 0;
    }


    private Action chooseBestAction(int currentStateIndex,double epsilon){

        //call network forward pass

        return null;
    }


    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }
}
