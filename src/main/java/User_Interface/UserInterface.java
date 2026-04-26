package User_Interface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class UserInterface extends JPanel {

    private ArrayList<EpisodeStep> episodeArmPositions;
    private int positionIndex;

    private double minArmX, minArmY, maxArmX, maxArmY;
    private double targetX, targetY;

    // UI Components
    private JScrollBar armScrollBar;
    private JButton newEpisodeButton;

    // Data Labels
    private JLabel targetLabel;
    private JLabel anglesLabel;
    private JLabel qValueLabel;
    private JLabel numStepsLabel;

    private JTextField xInputField;
    private JTextField yInputField;
    private JButton customTargetButton;

    public UserInterface() {
        armScrollBar = new JScrollBar(JScrollBar.VERTICAL);
        newEpisodeButton = new JButton("Random Target");

        // Initializing Info Labels
        targetLabel = new JLabel("Target: (0.00, 0.00)");
        anglesLabel = new JLabel("Angles: [0.00, 0.00]");
        qValueLabel = new JLabel("Current Q-Value: 0.00");
        numStepsLabel = new JLabel("Number Of Steps Taken: 0");
        xInputField = new JTextField(5);
        yInputField = new JTextField(5);
        customTargetButton = new JButton("Set Target");

        positionIndex = 0;
    }

    void CreateWindow() {
        JFrame mainFrame = new JFrame("Arm Simulation - Testing Area");
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());

        //arm drawing area
        this.setPreferredSize(new Dimension(UserInterfaceConstants.MAX_ENVIRONMENT_SIZE, UserInterfaceConstants.MAX_ENVIRONMENT_SIZE));
        this.setBackground(Color.WHITE);
        mainFrame.add(this, BorderLayout.CENTER);

        //scroll bar
        armScrollBar.setPreferredSize(new Dimension(30, UserInterfaceConstants.MAX_ENVIRONMENT_SIZE));
        mainFrame.add(armScrollBar, BorderLayout.EAST);

        // 3. West - Side Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        infoPanel.setPreferredSize(new Dimension(200, UserInterfaceConstants.MAX_ENVIRONMENT_SIZE));
        infoPanel.setBackground(new Color(245, 245, 245));

        JLabel title = new JLabel("Simulation Data");
        title.setFont(new Font("Arial", Font.BOLD, 14));

        infoPanel.add(title);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 20))); //spacer
        infoPanel.add(targetLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(anglesLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(qValueLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(numStepsLabel);

        mainFrame.add(infoPanel, BorderLayout.WEST);

        //control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout()); // סידור שורתי

        controlPanel.add(new JLabel("X:"));
        controlPanel.add(xInputField);
        controlPanel.add(new JLabel("Y:"));
        controlPanel.add(yInputField);
        controlPanel.add(customTargetButton);

        controlPanel.add(new JSeparator(JSeparator.VERTICAL)); // קו מפריד
        controlPanel.add(newEpisodeButton);

        mainFrame.add(controlPanel, BorderLayout.SOUTH);

        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }


    public void updateInferenceData() {
        targetLabel.setText(String.format("Target: (%.2f, %.2f)", targetX, targetY));

        StringBuilder anglesStr = new StringBuilder("Angles: [");
        for (int i = 0; i < episodeArmPositions.get(positionIndex).angles.length; i++) {
            anglesStr.append(String.format("%.1f°", episodeArmPositions.get(positionIndex).angles[i]));
            if (i < episodeArmPositions.get(positionIndex).angles.length - 1)
                anglesStr.append(", ");
        }
        anglesStr.append("]");
        anglesLabel.setText(anglesStr.toString());

        qValueLabel.setText(String.format("Current Q-Value: %.4f", episodeArmPositions.get(positionIndex).qValue));
        numStepsLabel.setText(String.format("Number Of Steps Taken: %d",episodeArmPositions.size()));
    }

    public void updateEpisodeInfo(ArrayList<EpisodeStep> episodeSteps) {
        this.episodeArmPositions = episodeSteps;
        this.targetX = episodeSteps.get(0).targetX;
        this.targetY = episodeSteps.get(0).targetY;
        armScrollBar.setMaximum(this.episodeArmPositions.size() - 1 + armScrollBar.getModel().getExtent());
        repaint();
    }

    public void updatePositionIndex(int positionIndex) {
        this.positionIndex = positionIndex;
        repaint();
    }

    public void setArmBoundaries(double maxArmX, double maxArmY, double minArmX, double minArmY) {
        this.minArmX = minArmX; this.minArmY = minArmY;
        this.maxArmX = maxArmX; this.maxArmY = maxArmY;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(UserInterfaceConstants.STROKE_SIZE));

        try {
            //target
            g2.setColor(UserInterfaceConstants.TARGET_COLOR);
            g2.draw(new Ellipse2D.Double(linearMappingX(targetX) - 2.5, linearMappingY(targetY) - 2.5, 5, 5));

            //base
            g2.setColor(UserInterfaceConstants.BASE_COLOR);
            g2.draw(new Ellipse2D.Double(linearMappingX(episodeArmPositions.get(positionIndex).joints[0][0]) - 2.5, linearMappingY(episodeArmPositions.get(positionIndex).joints[0][1]) - 2.5, 5, 5));

            //arm Links
            for (int i = 1; i < episodeArmPositions.get(positionIndex).joints.length; i++) {
                double x1 = linearMappingX(episodeArmPositions.get(positionIndex).joints[i-1][0]);
                double y1 = linearMappingY(episodeArmPositions.get(positionIndex).joints[i-1][1]);
                double x2 = linearMappingX(episodeArmPositions.get(positionIndex).joints[i][0]);
                double y2 = linearMappingY(episodeArmPositions.get(positionIndex).joints[i][1]);

                g2.setColor(UserInterfaceConstants.LINK_COLOR);
                g2.draw(new Line2D.Double(x1, y1, x2, y2));

                g2.setColor(UserInterfaceConstants.JOINT_COLOR);
                g2.draw(new Ellipse2D.Double(x2 - 2.5, y2 - 2.5, 5, 5));
            }
        } catch (Exception ignored) {}
    }

    private double linearMappingX(double x) {
        return ((x - minArmX) * (UserInterfaceConstants.MAX_ENVIRONMENT_SIZE)) / (maxArmX - minArmX);
    }

    private double linearMappingY(double y) {
        double val = ((y - minArmY) * (UserInterfaceConstants.MAX_ENVIRONMENT_SIZE)) / (maxArmY - minArmY);
        return UserInterfaceConstants.MAX_ENVIRONMENT_SIZE - val;
    }


    public double getCustomTargetX() {
        if(xInputField.getText().isEmpty())
            return 0;
        double x = Double.parseDouble(xInputField.getText());
        if(x > maxArmX || x < minArmX)
            return 0;
        return x;
    }

    public double getCustomTargetY() {
        if(yInputField.getText().isEmpty())
            return 0;
        double y = Double.parseDouble(yInputField.getText());
        if (y > maxArmY || y < minArmY)
            return 0;
        return y;
    }

    public void setArmScrollBarListener(AdjustmentListener listener) { armScrollBar.addAdjustmentListener(listener); }
    public void setNewEpisodeButtonListener(ActionListener listener) { newEpisodeButton.addActionListener(listener); }
    public void setCustomTargetButtonListener(ActionListener listener) { customTargetButton.addActionListener(listener); }

}