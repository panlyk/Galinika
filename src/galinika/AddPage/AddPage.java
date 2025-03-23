package galinika.AddPage;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import gakinika.library.SubstanceLibrary;
import galinika.LaunchPage.LaunchPage;
import galinika.MainMenu.MainMenu;

public class AddPage {

    // Create frame and menu
    JFrame frame = new JFrame();
    MainMenu menu = new MainMenu();
    private LaunchPage launchPage;
    
    // Get substance map from CSV
    Map<String, Double> substancesMap = SubstanceLibrary.loadFromFile("substances.csv");
    // Convert keys to a String[] for the combo box
    String[] substanceNames = substancesMap.keySet().toArray(new String[0]);

    // Title label
    JLabel titleLabel = new JLabel("Προσθήκη συστατικού");

    // Substance label & combo box
    JLabel substanceLabel = new JLabel("Συστατικό");
    JComboBox<String> substanceComboBox = new JComboBox<>(substanceNames);

    // Quantity field & label
    JTextField quantityField = new JTextField();
    JLabel quantityLabel = new JLabel("Ποσότητα");
    
    // Confirm button
    JButton confirmButton = new JButton("Επιβεβαίωση");

    // Font
    Font labelFont = new Font("Arial", Font.PLAIN, 14);

    public AddPage(LaunchPage page) {
    	
    	// Launch page reference
    	launchPage = page;
    	
        // Frame setup
        frame.setSize(360, 360);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.WHITE);

        // MenuBar
        frame.setJMenuBar(menu.createMenu(frame));

        // Title
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(30, 20, 300, 30);
        frame.add(titleLabel);

        // Substance label
        substanceLabel.setFont(labelFont);
        substanceLabel.setBounds(30, 70, 120, 25);
        frame.add(substanceLabel);

        // Substance combo box
        substanceComboBox.setBounds(130, 70, 120, 25);
        frame.add(substanceComboBox);

        // Quantity label
        quantityLabel.setFont(labelFont);
        quantityLabel.setBounds(30, 110, 120, 25);
        frame.add(quantityLabel);

        // Quantity field
        quantityField.setBounds(130, 110, 120, 25);
        frame.add(quantityField);
        
        // Confirm Button
        confirmButton.setBounds(30, 150, 120, 25);
        confirmButton.addActionListener(e -> buttonAction());
        frame.add(confirmButton);

        // Show frame
        frame.setVisible(true);
        
    }
    
    public boolean isOpen() {
    	return frame.isActive();
    }
    
    private void buttonAction() { 
    	String selectedSubstance = (String) substanceComboBox.getSelectedItem();
    	Double pricePerQuantity = substancesMap.get(selectedSubstance);
    	try {
    	    double quantity = Double.parseDouble(quantityField.getText());
    	    // Now you can use quantity in calculations
    	    Double endPrice = quantity * pricePerQuantity;
    	    launchPage.addToTable(new Object[] {substanceComboBox.getSelectedItem().toString(),quantityField.getText(), endPrice.toString()+" €"});
    	} catch (NumberFormatException e) {
    	    // Handle invalid input (e.g., user typed "abc")
    	    JOptionPane.showMessageDialog(frame, "Παρακαλώ εισάγετε αριθμητική ποσότητα.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
    	}
    	frame.dispose();
    	
    	
    }
    
}    
    

