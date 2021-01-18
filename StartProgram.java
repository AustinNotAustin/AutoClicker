package me.AustinNotAustin.AustinsAutoClicker;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class StartProgram implements Runnable {

	// Set this to true to stop the operation
	public static volatile boolean shutdown = true;
	
	// Start the program with the entered information
		public static void start() throws AWTException {
			System.out.println("startProgram() called");
			
			// Get the interval time
			int newInterval = Main.intervalTime();
			
			//System.out.println(newInterval);
			//System.out.println(intervalFld.getText());
			
			// If the interval time is greater than 0
			if (newInterval > 0) {
				//System.out.println("interval is > 0");
				
				// Create the clicking robot
				Robot bot = new Robot();
				
				// Set the button to be repeated
				int click = InputEvent.BUTTON3_DOWN_MASK;
				
				if (!(Main.isContinuous())) {
					
					try {
						
						Main.repetitions = Integer.parseInt(Main.repetitionFld.getText());
						
						// Clear the notifications
						Main.notificationLbl.setText("");
						
						// Reset the active key so there isn't a continuous loop
						KeyListener.setActiveKey("");
						
						//System.out.println("The repetitions from repetitionFld.getText() is" + repetitions);
						
						// Disable the objects so the user doesn't make changes while running
						Main.disableObjects();
						System.out.println("Disable objects was just called in startProgram()");
						
						// Loop through the repetitions until the number is met
						for (int i = 0; i < Main.repetitions; i++) {
							System.out.println("Clicked: " + (i+1));
							bot.mousePress(click);
							bot.mouseRelease(click);
							Thread.sleep(newInterval);
							if (shutdown == true) {
								System.out.println("Shutdown was set to true, turning off");
								break;
							}
						}
						
						// Reset the active key so there isn't a continuous loop
						KeyListener.setActiveKey("");
						
						// Enable the objects after finishing
						Main.enableObjects();
							
					} catch (Exception e) {
						
						// Inform the user that they must enter a number in the repetition field if they want to run the cycle only a number of times
						Main.notificationLbl.setText("Please enter a number in the repetition field");
						System.out.println(e.getMessage());
					}
					
				}
				
				if (Main.isContinuous()) {
					
					// Reset the active key so there isn't a continuous loop
					KeyListener.setActiveKey("");
					
					// Limit the maximum times the application will run continuously
					int maxReps = 999999;
					
					// Turn off the GUI elements so the user cannot interact during run
					Main.disableObjects();
					
					// Continuously loop through the actions 
					for (int i = 0; i < maxReps; i++) {
						System.out.println("Clicked: " + (i+1));
						bot.mousePress(click);
						bot.mouseRelease(click);
						bot.delay(newInterval);
						if (shutdown == true) {
							System.out.println("Shutdown was set to true, turning off");
							break;
						}
					}
					
					//System.out.println("startProgram -> if isContinuousDone -> for loop -> done with the for loop");
				
					// Enable the objects once the loop finishes
					Main.enableObjects();
				}
			}
		}

	@Override
	public void run() {
		try {
			start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
}
