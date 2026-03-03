package InverseKinematics;

import Library.NetworkState;

public class State extends NetworkState {

    private double[] angles;
    private double targetX;
    private double targetY;
    private double bestDistance;

    public State(double targetX, double targetY,double[] angles, double bestDistance) {
        this.angles = angles;
        this.targetX = targetX;
        this.targetY = targetY;
        this.bestDistance = bestDistance;
    }


    @Override
    public State copy(){
        return new State(targetX,targetY,angles.clone(),bestDistance);
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

    public double getBestDistance() {
        return bestDistance;
    }
    public void setBestDistance(double bestDistance) {
        this.bestDistance = bestDistance;
    }
}
