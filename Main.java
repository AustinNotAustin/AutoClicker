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
		
		while (true) {
			
			//System.out.println("The interval field text is: " + intervalFld.getText());
			//System.out.println(KeyListener.getActiveKey());
			
			if (KeyListener.getActiveKey() == KeyListener.getHotkeyBinding("start")) {
				
				new Main().startProgram();
			}
			
			if (KeyListener.getActiveKey() == KeyListener.getHotkeyBinding("stop")) {
				
				new Main().stopProgram();
			}
		} 
	}

	
	// Create the GUI objects
	public void createGUI() {
		
		// Set the frame's attributes
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Austin's Auto Clicker");
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
		
		repPanel.add(continuousLbl);
		repPanel.add(continuousBox);
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
			
			// Start the program, may receive an AWT Exception
			try {
				
				startProgram();
			} catch (AWTException e) {
				
				// Print the exception
				e.printStackTrace();
			}
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
	
	public int intervalTime() {
		
		// Set the newInterval int to 0 by default
		int newInterval = 0;
		
		// Try to convert the string into an integer
		try {
			
			// Set the newInterval to the users entered value
			newInterval = Integer.parseInt(intervalFld.getText());
			//System.out.println("The try statement grabs the value of newInterval at " + newInterval);
			
			// If it's accepted so far, remove any message that may be present
			notificationLbl.setText("");
			
		} catch (Exception e) {

			// If an exception is thrown, send the user this message
			notificationLbl.setText("Please enter numbers in the interval field! Ex: '12'");
			//System.out.println("Exception in the intervalTime() " + e);
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
	
	// Start the program with the entered information
	public void startProgram() throws AWTException {
		System.out.println("startProgram() called");
		
		// Get the interval time
		int newInterval = intervalTime();
		
		//System.out.println(newInterval);
		//System.out.println(intervalFld.getText());
		
		// If the interval time is greater than 0
		if (newInterval > 0) {
			//System.out.println("interval is > 0");
			
			// Create the clicking robot
			Robot bot = new Robot();
			
			// Set the button to be repeated
			int click = InputEvent.BUTTON3_DOWN_MASK;
			
			if (!(isContinuous())) {
				
				try {
					
					repetitions = Integer.parseInt(repetitionFld.getText());
					
					// Clear the notifications
					notificationLbl.setText("");
					
					// Reset the active key so there isn't a continuous loop
					KeyListener.setActiveKey("");
					
					//System.out.println("The repetitions from repetitionFld.getText() is" + repetitions);
					
					// Disable the objects so the user doesn't make changes while running
					disableObjects();
					
					// Loop through the repetitions until the number is met
					for (int i = 0; i < repetitions; i++) {
						System.out.println("Clicked");
						bot.mousePress(click);
						bot.mouseRelease(click);
						bot.delay(newInterval);
						if (KeyListener.getActiveKey() == KeyListener.getHotkeyBinding("stop"))
							break;
					}
					
					// Reset the active key so there isn't a continuous loop
					KeyListener.setActiveKey("");
					
					// Enable the objects after finishing
					enableObjects();
						
				} catch (Exception e) {
					
					// Inform the user that they must enter a number in the repetition field if they want to run the cycle only a number of times
					notificationLbl.setText("Please enter a number in the repetition field");
					System.out.println(e.getMessage());
				}
				
			}
			
			if (isContinuous()) {
				
				// Reset the active key so there isn't a continuous loop
				KeyListener.setActiveKey("");
				
				// Limit the maximum times the application will run continuously
				int maxReps = 999999;
				
				// Continuously loop through the actions 
				for (int i = 0; i < maxReps; i++) {
					//System.out.println("Clicked | The value of i in the for loop is: " + i);
					bot.mousePress(click);
					bot.mouseRelease(click);
					bot.delay(newInterval);
					if (KeyListener.getActiveKey() == KeyListener.getHotkeyBinding("stop")) {
						break;
					}
				}
				
				//System.out.println("startProgram -> if isContinuousDone -> for loop -> done with the for loop");
			
				// Enable the objects once the loop finishes
				enableObjects();
			}
		}
	}
	
	public void stopProgram() {
		
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
	}
	
	// Disables objects while the program is running
	public static void disableObjects() {
		startBtn.setEnabled(false);
		secondsRBtn.setEnabled(false);
		milisecondsRBtn.setEnabled(false);
		intervalLbl.setEnabled(true);
		intervalFld.setEnabled(true);
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
}
