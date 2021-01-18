// Version 1.1
package me.AustinNotAustin.AustinsAutoClicker;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;



public class Main extends JFrame implements ActionListener {
	
	// interval is the time between actions during operation in milliseconds
	static int interval;
	
	// Create each new object in the GUI
	// frame = the main GUI window
	static JFrame frame = new JFrame();
	// mainPanel = the inner pane of the window
	static JPanel mainPanel = new JPanel();
	// start/stop panel
	static JPanel SSPanel = new JPanel();
	// delay between actions panel
	static JPanel intervalPanel = new JPanel();
	// repetition panel
	static JPanel repPanel = new JPanel();
	// notification panel
	static JPanel notiPanel = new JPanel();
	
	// startBtn = the button to start the program
	static JButton startBtn = new JButton();
	// stopBtn = the button to stop the program
	static JButton stopBtn = new JButton();
	// intervalFld = the text box for the user to enter their 
	// interval time between actions
	static JTextField intervalFld = new JTextField();
	// intervalLbl = the text to show above the intervalFld
	static JLabel intervalLbl = new JLabel();
	// secondsRBtn = the seconds radio button for intervals
	static JRadioButton secondsRBtn = new JRadioButton();
	// milisecondsRBTn = the milliseconds radio button for intervals
	static JRadioButton milisecondsRBtn = new JRadioButton();
	// btnGroup = the group to place radio buttons in for interval selection
	static ButtonGroup btnGroup = new ButtonGroup();
	// notificationLbl = the string of text at the bottom to speak to the user
	static JLabel notificationLbl = new JLabel();
	// repetitionLbl = the text to ask for number of repetitions 
	static JLabel repetitionLbl = new JLabel();
	// repetitionFld = the text field for the number of repetitions to click/press
	static JTextField repetitionFld = new JTextField();
	// continuousLbl = the text to ask to run continuously or not
	static JLabel continuousLbl = new JLabel();
	// continuousBox = the check box where the user can determine if they want to run continuously or not
	static JCheckBox continuousBox = new JCheckBox();
	
	// Create the key listener
	static KeyListener keyListener = new KeyListener();
	
	// Create the variable for the number of iterations/repetitions
	static int repetitions = 0; 
	
	
	public static void main(String args[]) throws AWTException {

		Main main = new Main();
		main.createGUI();
		System.out.println("Testerino");
		
		try {
			
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e) {
			System.err.println("Unable to register native hook from KeyListener");
			System.err.println(e.getMessage());
			
			System.exit(1);
		}
		
		GlobalScreen.addNativeKeyListener(new KeyListener());
			
			//System.out.println("The interval field text is: " + intervalFld.getText());
			//System.out.println(KeyListener.getActiveKey());
		
	}

	
	// Create the GUI objects
	public void createGUI() {
		
		// Set the frame's attributes
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Auto Clicker");
		frame.setVisible(true);
		frame.setSize(400,220);
		frame.setLocation(500, 300);
		
		// Set the start button's attributes
		startBtn.setText("Start (F9)");
		startBtn.setActionCommand("start");
		startBtn.setMnemonic(KeyEvent.VK_F9);
		
		// Set the stop button's attributes
		stopBtn.setText("Stop (F10)");
		stopBtn.setActionCommand("stop");
		stopBtn.setMnemonic(KeyEvent.VK_F10);
		
		// Register a listener to the start and stop buttons
		startBtn.addActionListener(this);
		stopBtn.addActionListener(this);
		
		// Set the interval field's attributes
		intervalFld.setColumns(6);
		intervalFld.setActionCommand("intervalFld");
      
		// Set the interval label's attributes
		intervalLbl.setText("Interval: ");
		
		// Set the radio button attributes
		secondsRBtn.setText("Seconds");
		secondsRBtn.setActionCommand("seconds");
		milisecondsRBtn.setText("Miliseconds");
		milisecondsRBtn.setActionCommand("miliseconds");
		milisecondsRBtn.setSelected(true);
		
		// Set the continuous vs repetition attributes
		repetitionLbl.setText("Repetitions:");
		repetitionFld.setColumns(6);
		continuousLbl.setText("Continuous | Off");
		continuousLbl.setEnabled(false);
		continuousBox.setActionCommand("toggleContinuous");
		continuousBox.addActionListener(this);
		
		// Group the radio buttons together
		btnGroup.add(secondsRBtn);
		btnGroup.add(milisecondsRBtn);
		
		// Add the objects to the panels
		intervalPanel.add(milisecondsRBtn);
		intervalPanel.add(secondsRBtn);
		intervalPanel.add(intervalLbl);
		intervalPanel.add(intervalFld);
		
		repPanel.add(continuousBox);
		repPanel.add(continuousLbl);
		repPanel.add(repetitionLbl);
		repPanel.add(repetitionFld);
		
		SSPanel.add(startBtn);
		SSPanel.add(stopBtn);
		
		notiPanel.add(notificationLbl);
		
		// Configure the panels
		intervalPanel.setBorder(BorderFactory.createEtchedBorder());
		
		repPanel.setBorder(BorderFactory.createEtchedBorder());
		
		// Add the panels to the frame
		mainPanel.add(intervalPanel);
		mainPanel.add(repPanel);
		mainPanel.add(SSPanel);
		mainPanel.add(notiPanel);
		frame.add(mainPanel);
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		System.out.println("actionPerformed() was just called.");
		
		if (event.getActionCommand() == "start") {
			System.out.println("start event called");
			callStart();
		}
		
		if (event.getActionCommand() == "stop") {
			System.out.println("stop event called");
			// Enable all objects
			// Stop the program
			stopProgram();
		}
		
		// If the command is toggle continuous, call the function
		if (event.getActionCommand() == "toggleContinuous")
			toggleContinuous();
		
	}
	
	public static int intervalTime() {
		
		// Set the newInterval int to 0 by default
		int newInterval = 0;
		
		// Try to convert the string into an integer
		try {
			
			// Set the newInterval to the users entered value
			newInterval = Integer.parseInt(intervalFld.getText());
			System.out.println("The try statement grabs the value of newInterval at " + newInterval);
			
			// If it's accepted so far, remove any message that may be present
			notificationLbl.setText("");
			
		} catch (Exception e) {

			// If an exception is thrown, send the user this message
			notificationLbl.setText("Please enter a number in the interval field");
			System.out.println("Exception in the intervalTime() " + e);
			return 0;
		}
		
		// If the seconds radio button is selected, multiply the currently set interval
		if (secondsRBtn.isSelected()) {
			
			// Multiply the interval seconds by 1000 since the time in here is in milliseconds
			newInterval *= 1000;
		}
		
		// If the milliseconds radio button is selected, we don't make any changes
		return newInterval;
	}
	
	public static void stopProgram() {
		
		StartProgram.shutdown = true;
		
		// Enable objects once the program stops
		enableObjects();
	}
	
	// Enables objects while the program is NOT running
	public static void enableObjects() {
		startBtn.setEnabled(true);
		secondsRBtn.setEnabled(true);
		milisecondsRBtn.setEnabled(true);
		intervalLbl.setEnabled(true);
		intervalFld.setEnabled(true);
		continuousBox.setEnabled(true);
		if (isContinuous()) {
			
			continuousLbl.setEnabled(true);
		}
		
		else if (!isContinuous()) {
			
			repetitionLbl.setEnabled(true);
			repetitionFld.setEnabled(true);
		}
	}
	
	// Disables objects while the program is running
	public static void disableObjects() {
		System.out.println("disableObjects() was just called in iteself");
		startBtn.setEnabled(false);
		secondsRBtn.setEnabled(false);
		milisecondsRBtn.setEnabled(false);
		intervalLbl.setEnabled(false);
		intervalFld.setEnabled(false);
		continuousBox.setEnabled(false);
		if (isContinuous()) {
			
			continuousLbl.setEnabled(false);
		}
		
		else if (!isContinuous()) {
			
			repetitionLbl.setEnabled(false);
			repetitionFld.setEnabled(false);
		}
	}
	
	public static Boolean isContinuous() {
		
		// Returns if the check box is selected or not
		return continuousBox.isSelected();
	}
	
	public static void toggleContinuous() {
		
		if (continuousBox.isSelected()) {
			
			// Disable the repetition portion
			repetitionLbl.setEnabled(false);
			repetitionFld.setEnabled(false);
			continuousLbl.setEnabled(true);
			continuousLbl.setText("Continuous | On");
			
		}
		
		if (!(continuousBox.isSelected())) {
			
			// Disable the repetition portion
			repetitionLbl.setEnabled(true);
			repetitionFld.setEnabled(true);
			continuousLbl.setEnabled(false);
			continuousLbl.setText("Continuous | Off");
			
		}
		
	}
	
	public static void callStart() {
		
		StartProgram.shutdown = false;
		(new Thread(new StartProgram())).start();
	}
}
