package User_Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class UserInterface extends JPanel{

    private ArrayList<double[][]> episodeArmPositions;
    private int positionIndex;


    private double minArmX;
    private double minArmY;
    private double maxArmX;
    private double maxArmY;

    private double targetX;
    private double targetY;


    //components
    JScrollBar armScrollBar;
    JButton newEpisodeButton;

    public UserInterface(){
        armScrollBar = new JScrollBar(JScrollBar.VERTICAL);
        newEpisodeButton = new JButton("New Episode");


        positionIndex = 0;
    }

    void CreateWindow(){
        JFrame mainFrame = new JFrame("Arm Simulation");
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());

        //arm drawing area (CENTER)
        this.setPreferredSize(new Dimension(UserInterfaceConstants.MAX_ENVIRONMENT_SIZE, UserInterfaceConstants.MAX_ENVIRONMENT_SIZE));
        this.setBackground(Color.WHITE);
        mainFrame.add(this, BorderLayout.CENTER);


        //adding scroll bar
        armScrollBar.setPreferredSize(new Dimension(30, UserInterfaceConstants.MAX_ENVIRONMENT_SIZE));
        mainFrame.add(armScrollBar, BorderLayout.EAST);

        //control panel
        JPanel controlPanel = new JPanel();
        controlPanel.add(newEpisodeButton);
        mainFrame.add(controlPanel, BorderLayout.SOUTH);

        mainFrame.pack(); //automatically sizes the window to fit everything
        mainFrame.setLocationRelativeTo(null); //centers window on screen
        mainFrame.setVisible(true);
    }


    //updates the ui based of the current new episode.
    void updateEpisodeInfo(ArrayList<double[][]> episodeInfo){
        this.episodeArmPositions = new ArrayList<>(episodeInfo.subList(1,episodeInfo.size()));
        this.targetX = episodeInfo.get(0)[0][0];
        this.targetY = episodeInfo.get(0)[0][1];
        armScrollBar.setMaximum(this.episodeArmPositions.size()-1 + armScrollBar.getModel().getExtent());
        repaint();
    }

    //updates the current position of the arm in the episode
    void updatePositionIndex(int positionIndex){
        this.positionIndex = positionIndex;
        repaint();
    }

    //gives the user interface the boundaries of the environment
    void setArmBoundaries(double maxArmX, double maxArmY, double minArmX, double minArmY){
        this.minArmX = minArmX;
        this.minArmY = minArmY;
        this.maxArmX = maxArmX;
        this.maxArmY = maxArmY;
    }


    //paints components on the screen
    public void paintComponent(Graphics g){//draw function that is called each time a change is made to the window.Graphics is passed from the JRE
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);// makes lines smoother
        g2.setStroke(new BasicStroke(UserInterfaceConstants.STROKE_SIZE));



        try {

            //drawing target
            g2.setColor(UserInterfaceConstants.TARGET_COLOR);
            Ellipse2D.Double target = new Ellipse2D.Double(linearMappingX(targetX) - 2.5, linearMappingY(targetY) - 2.5, 5, 5);
            g2.draw(target);


            //drawing basePoint
            g2.setColor(UserInterfaceConstants.BASE_COLOR);
            Ellipse2D.Double basePoint = new Ellipse2D.Double(linearMappingX(episodeArmPositions.get(positionIndex)[0][0]) - 2.5, linearMappingY(episodeArmPositions.get(positionIndex)[0][1]) - 2.5, 5, 5);
            g2.draw(basePoint);


            //drawing arm
            for (int i = 1; i < episodeArmPositions.get(0).length; i++) {

                double mappedPreviousX = linearMappingX(episodeArmPositions.get(positionIndex)[i - 1][0]);
                double mappedPreviousY = linearMappingY(episodeArmPositions.get(positionIndex)[i - 1][1]);
                double mappedCurrentX = linearMappingX(episodeArmPositions.get(positionIndex)[i][0]);
                double mappedCurrentY = linearMappingY(episodeArmPositions.get(positionIndex)[i][1]);

                //drawing links
                g2.setColor(UserInterfaceConstants.LINK_COLOR);
                Line2D.Double link = new Line2D.Double(mappedPreviousX, mappedPreviousY, mappedCurrentX, mappedCurrentY);// can draw coords that use double. swing handles conversion.
                g2.draw(link);

                //drawing joints
                g2.setColor(UserInterfaceConstants.JOINT_COLOR);
                Ellipse2D.Double joint = new Ellipse2D.Double(mappedCurrentX - 2.5, mappedCurrentY - 2.5, 5, 5);
                g2.draw(joint);

            }
        }catch (NullPointerException ignored){
            //do nothing
        }
    }

    //maps environment positions to java swing's screen positions
    private double linearMappingX(double x){
        x = ((x-minArmX)*(UserInterfaceConstants.MAX_ENVIRONMENT_SIZE))/(maxArmX-minArmX);
        return x;
    }

    private double linearMappingY(double y){
        y = ((y-minArmY)*(UserInterfaceConstants.MAX_ENVIRONMENT_SIZE))/(maxArmY-minArmY);
        return UserInterfaceConstants.MAX_ENVIRONMENT_SIZE - y;
    }


    public void setArmScrollBarListener(AdjustmentListener adjustmentListener){
        armScrollBar.addAdjustmentListener(adjustmentListener);
    }

    public void setNewEpisodeButtonListener(ActionListener actionListener){
        newEpisodeButton.addActionListener(actionListener);
    }


}
