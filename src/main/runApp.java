package main;

import javax.swing.UIManager;

import view.*;

public class runApp {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			Home windown = new Home();
			windown.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
