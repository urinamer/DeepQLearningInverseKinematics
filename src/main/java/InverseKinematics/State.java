package InverseKinematics;

import Library.NetworkState;

public class State extends NetworkState {

    private double[] angles;
    private double targetX;
    private double targetY;
    private double diffX;
    private double diffY;

    public State(double targetX, double targetY,double diffX, double diffY,double[] angles) {
        this.angles = angles;
        this.targetX = targetX;
        this.targetY = targetY;
        this.diffX = diffX;
        this.diffY = diffY;
    }


    @Override
    public State copy(){
        return new State(targetX,targetY,diffX,diffY,angles.clone());
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

    public double getDiffX() {
        return diffX;
    }

    public void setDiffX(double diffX) {
        this.diffX = diffX;
    }

    public double getDiffY() {
        return diffY;
    }

    public void setDiffY(double diffY) {
        this.diffY = diffY;
    }
}
