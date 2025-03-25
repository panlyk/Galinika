package galinika.LaunchPage;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import gakinika.library.TypesLibrary.galenicType;
import galinika.AddPage.AddPage;
import galinika.MainMenu.MainMenu;
import java.awt.*;

public class LaunchPage {

    JFrame frame = new JFrame();
    MainMenu mainMenu = new MainMenu();

    JLabel titleLabel = new JLabel("Δημιουργία Γαληνικού");

    JLabel galenicTypeLabel = new JLabel("Τύπος Γαληνικού:");
    
    JComboBox<galenicType> galenicTypeComboBox = new JComboBox<>(galenicType.values());

    JLabel galenicQuantityLabel = new JLabel("Ποσότητα:");
    JTextField galenicQuantityField = new JTextField();
    JComboBox<String> galenicQuantityTypesBox = new JComboBox<>(new String[]{"gr", "ml"});

    JButton addButton = new JButton("Προσθήκη");
    JButton removeButton = new JButton("Διαγραφή");

    String[] columnNames = {"Όνομα", "Ποσότητα", "Τιμή"};
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    JTable table = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(table);

    JLabel restLabel = new JLabel("Άλλο κόστος (€):");
    JTextField restField = new JTextField();

    JButton calculateButton = new JButton("Υπολογισμός συνόλου");
    
    JLabel totalPriceJLabel = new JLabel("-€");

    Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
    Font titleFont = new Font("Segoe UI", Font.BOLD, 22);
    Font buttonFont = new Font("Segoe UI", Font.BOLD, 13);

    private AddPage addpage = null;

    public LaunchPage() {
        frame.setSize(380, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setJMenuBar(mainMenu.createMenu(frame));

        // Title
        titleLabel.setFont(titleFont);
        titleLabel.setBounds(30, 20, 300, 30);
        frame.add(titleLabel);

        // Galenic Type
        galenicTypeLabel.setFont(labelFont);
        galenicTypeLabel.setBounds(30, 70, 120, 25);
        frame.add(galenicTypeLabel);

        galenicTypeComboBox.setBounds(160, 70, 170, 25);
        frame.add(galenicTypeComboBox);

        // Quantity
        galenicQuantityLabel.setFont(labelFont);
        galenicQuantityLabel.setBounds(30, 105, 120, 25);
        frame.add(galenicQuantityLabel);

        galenicQuantityField.setBounds(160, 105, 80, 25);
        frame.add(galenicQuantityField);

        galenicQuantityTypesBox.setBounds(245, 105, 85, 25);
        frame.add(galenicQuantityTypesBox);

        // Add Button
        addButton.setFont(buttonFont);
        addButton.setBounds(30, 145, 140, 25);
        addButton.setBackground(new Color(70, 130, 180));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> addpage = new AddPage(this));
        frame.add(addButton);

        // Remove Button
        removeButton.setFont(buttonFont);
        removeButton.setBounds(190, 145, 140, 25);
        removeButton.setBackground(new Color(220, 53, 69));
        removeButton.setForeground(Color.WHITE);
        removeButton.setFocusPainted(false);
        removeButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(table.convertRowIndexToModel(selectedRow));
            }
        });
        frame.add(removeButton);

        // Table
        table.setAutoCreateRowSorter(true);
        scrollPane.setBounds(30, 185, 300, 140);
        frame.add(scrollPane);

        // Other cost
        restLabel.setFont(labelFont);
        restLabel.setBounds(30, 340, 150, 25);
        frame.add(restLabel);

        restField.setBounds(190, 340, 100, 25);
        frame.add(restField);

        // Calculate Button
        calculateButton.setFont(buttonFont);
        calculateButton.setBounds(30, 380, 260, 30);
        calculateButton.setBackground(new Color(40, 167, 69));
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFocusPainted(false);
        calculateButton.addActionListener(e -> calculateTotal());
        frame.add(calculateButton);
        
        // Total Price Label - emphasized
        totalPriceJLabel.setBounds(30, 420, 100, 35);
        totalPriceJLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalPriceJLabel.setFont(new Font("Segoe UI", Font.BOLD, 18)); // Larger and bold
        totalPriceJLabel.setForeground(new Color(0, 128, 0)); // Dark green
        // Optional background
        totalPriceJLabel.setOpaque(true);
        totalPriceJLabel.setBackground(new Color(235, 255, 235)); // very light green
        // Optional border
        totalPriceJLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 0), 1));

        frame.add(totalPriceJLabel);

        frame.setLocationRelativeTo(null); // center on screen
        frame.setVisible(true);
    }

    public void addToTable(Object[] rowdata) {
        tableModel.addRow(rowdata);
    }
    
    private void calculateTotal() {
    	float galenicQuantity = Float.parseFloat(galenicQuantityField.getText().trim().replace(",", "."));
    	galenicType type = (galenicType) galenicTypeComboBox.getSelectedItem();
    	float total = 0;
    	
    	//calculate quantity cost
    	try {float quantityCost = calculateTieredCost(type.getMinimumQuantity(), type.getDivisionQuantity(), type.getMinimumCost(), type.getCostIncrement(), galenicQuantity);
    	total += quantityCost;}
    	catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Παρακαλώ εισάγετε έναν έγκυρο αριθμό στο πεδίο 'Ποσότητα'.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
			return;
		}
    	
    	//calculate substance cost
    	float substancesCost = 0;
    	for (int row = 0; row < tableModel.getRowCount(); row++) {
    	    String priceString = ((String) tableModel.getValueAt(row, 2)).replace("€", "").replace(",", ".").trim();
    	    substancesCost += Float.parseFloat(priceString);
    	}
    	total += substancesCost;
    	
    	//calculate other costs
    	try {float otherCost = Float.parseFloat(restField.getText().replace(",", ".")); 
    	total += otherCost;}
    	catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Παρακαλώ εισάγετε έναν έγκυρο αριθμό στο πεδίο 'Λοιπά έξοδα'.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		//change the label
		totalPriceJLabel.setText (total + " €");
    	
    	
    }
    
    private float calculateTieredCost (float minimumQuantity, float divisionQuantity, float minimumCost, float costIncrement, float quantity)
    {
    	float quantityOverMinimum = (quantity-minimumQuantity);
    	if (quantityOverMinimum <= 0) {
    		return minimumCost;
    	} else if (Math.abs(quantityOverMinimum % divisionQuantity) < 0.001f) {
    		
    		return minimumCost + (quantityOverMinimum/divisionQuantity)*costIncrement;
    	} else {
    		int times = (int) (quantityOverMinimum / divisionQuantity);
    		return minimumCost + (times+1) * costIncrement;
    	}
    }
}