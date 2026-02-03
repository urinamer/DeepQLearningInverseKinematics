public class BufferTransition {
    private State currentState;
    private int actionIndex;
    private double reward;
    private State nextState;

    public BufferTransition(State currentState, int actionIndex, double reward, State nextState) {
        this.currentState = currentState;
        this.actionIndex = actionIndex;
        this.reward = reward;
        this.nextState = nextState;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public int getActionIndex() {
        return actionIndex;
    }

    public void setActionIndex(int actionIndex) {
        this.actionIndex = actionIndex;
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

