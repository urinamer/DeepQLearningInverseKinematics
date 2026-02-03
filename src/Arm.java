public class Arm {
    private double basePointX;
    private double basePointY;
    private double handPointX;
    private double handPointY;
    private int numOfLinks;
    private double[] linkLengths;
    private double[] armAngles;

    public Arm(double basePointX, double basePointY, double[] linkLengths, double[] armAngles) {
        this.basePointX = basePointX;
        this.basePointY = basePointY;
        this.linkLengths = linkLengths;
        this.armAngles = armAngles;
    }

    public Arm(double basePointX, double basePointY, double[] linkLengths) {
        this.basePointX = basePointX;
        this.basePointY = basePointY;
        this.linkLengths = linkLengths;
    }

    public Arm(double basePointX, double basePointY) {
        this.basePointX = basePointX;
        this.basePointY = basePointY;
    }

    public Arm() {
        basePointX = 0;
        basePointY = 0;
    }

    public double getBasePointX() {
        return basePointX;
    }

    public void setBasePointX(double basePointX) {
        this.basePointX = basePointX;
    }

    public double getBasePointY() {
        return basePointY;
    }

    public void setBasePointY(double basePointY) {
        this.basePointY = basePointY;
    }

    public int getNumOfLinks() {
        return numOfLinks;
    }

    public void setNumOfLinks(int numOfLinks) {
        this.numOfLinks = numOfLinks;
    }

    public double[] getLinkLengths() {
        return linkLengths;
    }

    public double[] getArmAngles() {
        return armAngles;
    }

    public double getHandPointY() {
        return handPointY;
    }

    public void setHandPointY(double handPointY) {
        this.handPointY = handPointY;
    }

    public double getHandPointX() {
        return handPointX;
    }

    public void setHandPointX(double handPointX) {
        this.handPointX = handPointX;
    }
}
