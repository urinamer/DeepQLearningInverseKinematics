public class BufferTransition {
    private State currentState;
    private Action action;
    private double reward;
    private State nextState;

    public BufferTransition(State currentState, Action action, double reward, State nextState) {
        this.currentState = currentState;
        this.action = action;
        this.reward = reward;
        this.nextState = nextState;
    }


    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public State getNextState() {
        return nextState;
    }

    public void setNextState(State nextState) {
        this.nextState = nextState;
    }
}

