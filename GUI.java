import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.LineBorder;

public class GUI {
    private JFrame frame;
    private JTextField kField;
    private JTextField tiField;
    private JTextField tdField;
    private JTextField periodField;
    private JTextField amplitudeField;
    private JButton startButton;
    private JButton stopButton;

    public GUI() {


        // create the frame
        frame = new JFrame("Tune PID Controller");
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        // create the text fields for the controller parameters
        kField = new JTextField("0.0", 10);
        tiField = new JTextField("0.0", 10);
        tdField = new JTextField("0.0", 10);

        // Create the text fields for the relay
        periodField = new JTextField("0.0", 10);
        amplitudeField = new JTextField("0.0", 10);

        // create the start button
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");

        ImageIcon icon = new ImageIcon("image.png");
        ImageIcon sineIcon = new ImageIcon("sineWave.png");
        JLabel iconLabel = new JLabel(icon);
        JLabel graphIcon = new JLabel(sineIcon);
        

        // create the panel and add components to it
        JPanel controllerPanel = new JPanel(new GridLayout(4, 5, 10, 10));

        JLabel descrContr = new JLabel("Controller Parameters");
        descrContr.setFont(new Font("Serif", Font.BOLD, 14));
        JLabel descrRelay = new JLabel("Relay Parameters");
        descrRelay.setFont(new Font("Serif", Font.BOLD, 14));

        

        controllerPanel.setBackground(Color.lightGray);

        controllerPanel.add(new JLabel("FRTN01"));
        controllerPanel.add(descrContr);
        controllerPanel.add(new JLabel()); // Add a blank label to take up the second cell
        controllerPanel.add(descrRelay);
        controllerPanel.add(new JLabel());

        controllerPanel.add(new JLabel("Real-time Systems"));
        controllerPanel.add(new JLabel("K:"));
        controllerPanel.add(kField);
        controllerPanel.add(new JLabel("Period:"));
        controllerPanel.add(periodField);
        
        controllerPanel.add(new JLabel("Project - PID Autotuner"));
        controllerPanel.add(new JLabel("Ti:"));
        controllerPanel.add(tiField);
        controllerPanel.add(new JLabel("Amplitude:"));
        controllerPanel.add(amplitudeField);
        
        controllerPanel.add(new JLabel());
        controllerPanel.add(new JLabel("Td:"));
        controllerPanel.add(tdField);
        controllerPanel.add(new JLabel());
        controllerPanel.add(new JLabel());


        JPanel relayPanel = new JPanel(new GridLayout(7, 1, 10, 10));

        relayPanel.setBackground(Color.lightGray);
        relayPanel.setPreferredSize(new Dimension(200, 300));
        
        JLabel descrStart = new JLabel("Important:");
        descrStart.setFont(new Font("Serif", Font.BOLD, 15));
        
        relayPanel.add(descrStart);
        relayPanel.add(new JLabel("<html>Please make sure you<br/>set the amplitude<br/> and the frequency of the<br/>relay before initializing<br/>the tuner<html>")); 
        relayPanel.add(startButton);
        relayPanel.add(stopButton);
        relayPanel.add(new JLabel());
        relayPanel.add(new JLabel());
        relayPanel.add(new JLabel());
       
        
        frame.add(controllerPanel, BorderLayout.NORTH);
        frame.add(relayPanel, BorderLayout.WEST);
        //frame.add(iconLabel, BorderLayout.SOUTH);
        frame.add(graphIcon, BorderLayout.EAST);

        // add an action listener to the start button
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //double k = Double.parseDouble(kField.getText());
                //double ti = Double.parseDouble(tiField.getText());
                //double td = Double.parseDouble(tdField.getText());
                //double period = Double.parseDouble(periodField.getText());
                //double amplitude = Double.parseDouble(amplitudeField.getText());

            }
        });

        // add an action listener to the stop button
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Stop all outputs
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
    }
}