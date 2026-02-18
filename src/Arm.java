import java.util.Arrays;

public class Arm {
    private double basePointX;
    private double basePointY;
    private double handPointX;
    private double handPointY;
    private int numOfLinks;
    private double[] linkLengths;
    private double[] armAngles;

    public Arm(double basePointX, double basePointY, int numOfLinks,double[] linkLengths, double[] armAngles) {
        this.basePointX = basePointX;
        this.basePointY = basePointY;
        this.numOfLinks = numOfLinks;
        this.linkLengths = linkLengths;
        this.armAngles = armAngles;
    }

    public Arm(double basePointX, double basePointY,int numOfLinks, double[] linkLengths) {
        this.basePointX = basePointX;
        this.basePointY = basePointY;
        this.numOfLinks = numOfLinks;
        this.linkLengths = linkLengths;
    }

    public Arm(double basePointX, double basePointY) {
        this.basePointX = basePointX;
        this.basePointY = basePointY;
        this.numOfLinks  = Constants.DEFAULT_NUM_OF_LINKS;
        linkLengths= new double[numOfLinks];
        Arrays.fill(linkLengths,Constants.DEFAULT_LINK_LENGTH);
        armAngles = new double[numOfLinks];
        Arrays.fill(armAngles,Constants.DEFAULT_ANGLE);
    }

    public Arm() {
        basePointX = 0;
        basePointY = 0;
        this.numOfLinks  = Constants.DEFAULT_NUM_OF_LINKS;
        linkLengths= new double[numOfLinks];
        Arrays.fill(linkLengths,Constants.DEFAULT_LINK_LENGTH);
        armAngles = new double[numOfLinks];
        Arrays.fill(armAngles,Constants.DEFAULT_ANGLE);
    }


    public boolean calculateForwardKinematics(double[] angles){//returns is legal and updates currentState
        double currentX = getBasePointX();
        double currentY = getBasePointY();
        //for more than 2 DOF
        for(int i =0; i < getNumOfLinks(); i++){
            currentX += getLinkLengths()[i]*Math.cos(angles[i]);
            currentY += getLinkLengths()[i]*Math.sin(angles[i]);
        }

        if(currentX < Constants.MAX_ENVIRONMENT_X && currentX > 0 && currentY < Constants.MAX_ENVIRONMENT_Y && currentY > 0){
            this.armAngles = angles;
            return true;
        }
        return false;

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

    public void setArmAngles(double[] angles){
        this.armAngles = angles;
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
