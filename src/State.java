public class State {

    private double[] angles;
    private double targetX;
    private double targetY;

    public State(double targetX, double targetY,double[] angles) {
        this.angles = angles;
        this.targetX = targetX;
        this.targetY = targetY;
    }



    public State copy(){
        return new State(targetX,targetY,angles);
    }

    //bad practice
    public double[] getAngles() {
        return angles;
    }

    public void setAngles(double[] angles) {
        this.angles = angles;
    }

    public double getTargetX() {
        return targetX;
    }

    public void setTargetX(double targetX) {
        this.targetX = targetX;
    }

    public double getTargetY() {
        return targetY;
    }

    public void setTargetY(double targetY) {
        this.targetY = targetY;
    }
}
