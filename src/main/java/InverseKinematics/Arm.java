package InverseKinematics;

import java.util.Arrays;

public class Arm {
    private double basePointX;
    private double basePointY;
    private double handPointX;
    private double handPointY;
    private int numOfLinks;
    private double[] linkLengths;
    private double[] armAngles;

    private double radius;

    public Arm(double basePointX, double basePointY, int numOfLinks,double[] linkLengths, double[] armAngles) {
        this.basePointX = basePointX;
        this.basePointY = basePointY;
        this.numOfLinks = numOfLinks;
        this.linkLengths = linkLengths;
        this.armAngles = armAngles;
        calculateForwardKinematics(armAngles);
        calculateRadius();
    }

    public Arm(double basePointX, double basePointY,int numOfLinks, double[] linkLengths) {
        this.basePointX = basePointX;
        this.basePointY = basePointY;
        this.numOfLinks = numOfLinks;
        this.linkLengths = linkLengths;
        calculateForwardKinematics(armAngles);
        calculateRadius();
    }

    public Arm(double basePointX, double basePointY) {
        this.basePointX = basePointX;
        this.basePointY = basePointY;
        this.numOfLinks  = Constants.DEFAULT_NUM_OF_LINKS;
        linkLengths= new double[numOfLinks];
        Arrays.fill(linkLengths,Constants.DEFAULT_LINK_LENGTH);

        armAngles = new double[numOfLinks];
        Arrays.fill(armAngles,0);
        armAngles[0] = Constants.DEFAULT_ANGLE1;


        calculateForwardKinematics(armAngles);
        calculateRadius();

    }

    public Arm() {
        basePointX = Constants.DEFAULT_X_BASE;
        basePointY = Constants.DEFAULT_Y_BASE;
        this.numOfLinks  = Constants.DEFAULT_NUM_OF_LINKS;
        linkLengths= new double[numOfLinks];
        Arrays.fill(linkLengths,Constants.DEFAULT_LINK_LENGTH);

        armAngles = new double[numOfLinks];
        Arrays.fill(armAngles,0);
        armAngles[0] = Constants.DEFAULT_ANGLE1;


        calculateForwardKinematics(armAngles);
        calculateRadius();
    }

    public void resetArm(){
        Arrays.fill(armAngles,0);
        armAngles[0] = Constants.DEFAULT_ANGLE1;
        calculateForwardKinematics(armAngles);
    }


    public boolean calculateForwardKinematics(double[] angles){//returns is legal and updates currentState
        double currentX = getBasePointX();
        double currentY = getBasePointY();

        //adding vectors to get current hand position.Using sum angles to move other joints when an earlier joint moved.
        double sumAngles = 0;
        for(int i =0; i < getNumOfLinks(); i++){
            sumAngles += angles[i];
            currentX += getLinkLengths()[i]*Math.cos(Math.toRadians(sumAngles));
            currentY += getLinkLengths()[i]*Math.sin(Math.toRadians(sumAngles));
        }

        if(currentX < Constants.MAX_ENVIRONMENT_X && currentX > Constants.MIN_ENVIRONMENT_X && currentY < Constants.MAX_ENVIRONMENT_Y && currentY > Constants
                .MIN_ENVIRONMENT_Y){
            //update arm angles,because states angles pointer is pointing to the arm angles, the state is also updating.
            System.arraycopy(angles, 0, this.armAngles, 0, angles.length);
            this.handPointX = currentX;
            this.handPointY = currentY;
            return true;
        }

        return false;

    }

    public boolean isPointReachable(double x, double y){
        if(!(x < Constants.MAX_ENVIRONMENT_X && x > Constants.MIN_ENVIRONMENT_X && y < Constants.MAX_ENVIRONMENT_Y && y > Constants
                .MIN_ENVIRONMENT_Y))
            return false;

        double distance = Math.sqrt(Math.pow(basePointX-x,2)+Math.pow(basePointY-y,2));
        if(distance > this.radius)
            return false;

        return true;
    }


    private void calculateRadius(){
        double sum = 0;
        for(double linkLength : linkLengths){
            sum += linkLength;
        }
        this.radius = sum;
    }

    public double getBasePointX() {
        return basePointX;
    }

    public double getBasePointY() {
        return basePointY;
    }

    public int getNumOfLinks() {
        return numOfLinks;
    }

    public double[] getLinkLengths() {
        return linkLengths;
    }

    //bad practice
    public double[] getArmAngles() {
        return armAngles;
    }

    public double getHandPointY() {
        return handPointY;
    }

    public double getHandPointX() {
        return handPointX;
    }

    public double getRadius() {
        return radius;
    }
}
