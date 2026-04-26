package User_Interface;

public class EpisodeStep {
    public double[][] joints;
    public double[] angles;
    public double qValue;
    public double targetX, targetY;

    public EpisodeStep(double[][] joints, double[] angles, double qValue, double tx, double ty) {
        this.joints = joints;
        this.angles = angles;
        this.qValue = qValue;
        this.targetX = tx;
        this.targetY = ty;
    }
}
