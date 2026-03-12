package User_Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class UserInterface extends JPanel{
    private double baseX;
    private double baseY;
    private double[] armAngles;
    private double[] linkLengths;

    private double minArmX;
    private double minArmY;
    private double maxArmX;
    private double maxArmY;

    private double targetX;
    private double targetY;

    void CreateWindow(){
        JFrame frame = new JFrame();
        frame.add(this);
        this.setBounds(0, 0, UserInterfaceConstants.MAX_ENVIRONMENT_SIZE, UserInterfaceConstants.MAX_ENVIRONMENT_SIZE);
        frame.setSize(500,500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    void updateTarget(double targetX,double targetY){
        this.targetX = targetX;
        this.targetY = targetY;
    }
    void updateArmState(double baseX,double baseY,double[] armAngles, double[] linkLengths,double maxArmX,double maxArmY,double minArmX,double minArmY){
        this.baseX = baseX;
        this.baseY = baseY;
        this.armAngles = armAngles;
        this.linkLengths = linkLengths;
        this.minArmX = minArmX;
        this.minArmY = minArmY;
        this.maxArmX = maxArmX;
        this.maxArmY = maxArmY;
        repaint();
    }

    public void paintComponent(Graphics g){//draw function that is called each time a change is made to the window.Graphics is passed from the JRE
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);// makes lines smoother
        g2.setStroke(new BasicStroke(UserInterfaceConstants.STROKE_SIZE));

        //drawing target
        g2.setColor(UserInterfaceConstants.TARGET_COLOR);
        Ellipse2D.Double target = new Ellipse2D.Double(linearMappingX(targetX)-2.5,linearMappingY(targetY)-2.5,5,5);
        g2.draw(target);

        g2.setColor(UserInterfaceConstants.BASE_COLOR);
        Ellipse2D.Double basePoint = new Ellipse2D.Double(linearMappingX(baseX)-2.5,linearMappingY(baseY)-2.5,5,5);
        g2.draw(basePoint);

        double currentX = baseX;
        double currentY = baseY;
        double previousX = baseX;
        double previousY = baseY;

        //should be in arm not in userInterface
        //adding vectors to get current hand position.Using sum angles to move other joints when an earlier joint moved.
        double sumAngles = 0;
        for(int i =0; i < linkLengths.length; i++){
            sumAngles += armAngles[i];
            currentX += linkLengths[i]*Math.cos(Math.toRadians(sumAngles));
            currentY += linkLengths[i]*Math.sin(Math.toRadians(sumAngles));

            double mappedPreviousX = linearMappingX(previousX);
            double mappedPreviousY = linearMappingY(previousY);
            double mappedCurrentX = linearMappingX(currentX);
            double mappedCurrentY = linearMappingY(currentY);

            //drawing links
            g2.setColor(UserInterfaceConstants.LINK_COLOR);
            Line2D.Double link = new Line2D.Double(mappedPreviousX,mappedPreviousY,mappedCurrentX,mappedCurrentY);// can draw coords that use double. swing handles conversion.
            g2.draw(link);

            //drawing joints
            g2.setColor(UserInterfaceConstants.JOINT_COLOR);
            Ellipse2D.Double joint = new Ellipse2D.Double(mappedCurrentX-2.5,mappedCurrentY-2.5,5,5);
            g2.draw(joint);

            previousX = currentX;
            previousY = currentY;
        }
    }


    private double linearMappingX(double x){
        x = ((x-minArmX)*(UserInterfaceConstants.MAX_ENVIRONMENT_SIZE))/(maxArmX-minArmX);
        return x;
    }

    private double linearMappingY(double y){
        y = ((y-minArmY)*(UserInterfaceConstants.MAX_ENVIRONMENT_SIZE))/(maxArmY-minArmY);
        return UserInterfaceConstants.MAX_ENVIRONMENT_SIZE - y;
    }


}
