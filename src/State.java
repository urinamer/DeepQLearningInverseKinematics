public class State {

    private double[] angles;
    private double targetX;
    private double targetY;

    public State(double targetX, double targetY,double[] angles) {
        this.angles = new double[angles.length];
        System.arraycopy(angles, 0, this.angles, 0, angles.length);//more efficient copying
        this.targetX = targetX;
        this.targetY = targetY;
    }



    public State copy(){
        return new State(targetX,targetY,angles);
    }

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
