package User_Interface;
import InverseKinematics.Constants;

import javax.swing.*;
import java.awt.*;
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

    void CreateWindow(){
        JFrame frame = new JFrame();
        frame.add(this);
        this.setBounds(0, 0, UserInterfaceConstants.MAX_ENVIRONMENT_SIZE, UserInterfaceConstants.MAX_ENVIRONMENT_SIZE);
        frame.setSize(500,500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        g2.setColor(UserInterfaceConstants.COLOR);
        g2.setStroke(new BasicStroke(UserInterfaceConstants.STROKE_SIZE));
        double currentX = baseX;
        double currentY = baseY;
        double previousX = baseX;
        double previousY = baseY;


        //adding vectors to get current hand position.Using sum angles to move other joints when an earlier joint moved.
        double sumAngles = 0;
        for(int i =0; i < linkLengths.length; i++){
            sumAngles += armAngles[i];
            currentX += linkLengths[i]*Math.cos(Math.toRadians(sumAngles));
            currentY += linkLengths[i]*Math.sin(Math.toRadians(sumAngles));
            Line2D.Double line = new Line2D.Double(linearMappingX(previousX),linearMappingY(previousY),linearMappingX(currentX),linearMappingY(currentY));// can draw coords that use double. swing handles conversion.
            g2.draw(line);

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
