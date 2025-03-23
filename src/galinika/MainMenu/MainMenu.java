package galinika.MainMenu;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import gakinika.library.SubstanceLibrary;

public class MainMenu {
	SubstanceLibrary library;
	public JMenuBar createMenu(JFrame frame) {
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("Αρχείο");
		JMenuItem exitItem = new JMenuItem("Εξοδος");
		exitItem.addActionListener(e-> System.exit(0));
		
		JMenu infoMenu = new JMenu("Βιβλιοθήκη");
		JMenuItem updateSubstances = new JMenuItem("Επεξεργασία Συστατικών");
		JMenuItem updateGalenicTypes = new JMenuItem("Επεξεργασία Τύπων Γαληνικού");
		updateSubstances.addActionListener(e-> library = new SubstanceLibrary());
		
		fileMenu.add(exitItem);
		infoMenu.add(updateSubstances);
		infoMenu.add(updateGalenicTypes);
		
		menuBar.add(fileMenu);
		menuBar.add(infoMenu);
		
		return menuBar;
		
	}

}
