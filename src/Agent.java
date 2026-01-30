public class Agent {
    private static Agent agent;
    private State currentState;
    private Action[] actions;

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
            case ANGLE1DOWN -> currentState.setAngle1(currentState.getAngle1());
        }
    }


    private v


    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }
}
