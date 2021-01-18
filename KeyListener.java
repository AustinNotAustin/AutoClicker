package me.AustinNotAustin.AustinsAutoClicker;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyListener implements NativeKeyListener {
	
	
	// hotkeyAction = Start
	// hotkeyBinding = F9
	// hotkeyAction is the button that performs and action in the main script
	// hotkeyBinding is the assigned hot key to an action
	private static String start = "F9";
	private static String stop = "F10";
	
	private static String activeKey = "";
	
	// Don't think this is doing anything
	String command = "";
	
	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		// Generic message showing the action performed
		System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
		
		checkCommand(NativeKeyEvent.getKeyText(e.getKeyCode()));
		activeKey = NativeKeyEvent.getKeyText(e.getKeyCode());
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		// Generic message showing the action performed
		//System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
		
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		// Generic message showing the action performed
		//System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
	}

	
	// Get's the hot key mapping assigned to the hot key action
	public static String getHotkeyBinding(String hotkeyAction) {
		
		// If the hot key action is start
		if (hotkeyAction.toLowerCase() == "start") {
			
			// Return the hot key binding
			return start;
		}
		
		if (hotkeyAction.toLowerCase() == "stop") {
			
			//Return the hot key binding
			return stop;
		}
		
		System.out.println("The hot key action entered was not listed");
		return null;
	}
	
	public static void setHotkeyBinding(String hotkeyAction, String hotkeyBinding) {
		
		// If the hot key action is start
		if (hotkeyAction.toLowerCase() == "start") {
			
			// Set the new value key binding for start
			start = hotkeyBinding.toUpperCase();
		}
		
		// If the hot key action is start
		if (hotkeyAction.toLowerCase() == "stop") {
			
			// Set the new value key binding for stop
			stop = hotkeyBinding.toUpperCase();
		}
	}
	
	public static String getActiveKey() {
		
		if (activeKey == start)
			return start;
		
		if (activeKey == stop)
			return stop;
		
		return activeKey;
	}
	
	public static void setActiveKey(String key) {
		
		activeKey = key;
	}
	
	public static String getResetActiveKey() {
		
		System.out.println("getResetActiveKey() was just called, activeKey is now: " + activeKey);
		
		String key = activeKey;
		System.out.println("activeKey was just set to: " + activeKey);
		
		if (key == start) {
			activeKey = "";
			return start;
		}
		
		if (key == stop) {
			activeKey = "";
			return stop;
		}
		
		return activeKey;
	}
	
	public static void checkCommand(String cmd) {
			if (cmd == KeyListener.getHotkeyBinding("start"))
				Main.callStart();
			
			if (cmd == KeyListener.getHotkeyBinding("stop"))
				Main.stopProgram();
		
	}
}
