public class State {

    private double targetX;
    private double targetY;
    private double angle1;
    private double angle2;

    public State(double targetX, double targetY, double angle1, double angle2) {
        this.targetX = targetX;
        this.targetY = targetY;
        this.angle1 = angle1;
        this.angle2 = angle2;
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

    public double getAngle1() {
        return angle1;
    }

    public void setAngle1(double angle1) {
        this.angle1 = angle1;
    }

    public double getAngle2() {
        return angle2;
    }

    public void setAngle2(double angle2) {
        this.angle2 = angle2;
    }
}
