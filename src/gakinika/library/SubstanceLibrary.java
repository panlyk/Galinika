package gakinika.library;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;



import galinika.MainMenu.MainMenu;

public class SubstanceLibrary {
	// Create frame and menu
    JFrame frame = new JFrame();
    DefaultTableModel tableModel;
    JTable table;
    JScrollPane scrollPane;
    JButton saveButton = new JButton("Αποθήκευση");
    JButton addButton = new JButton("Προσθήκη");
    JButton deleteButton = new JButton("Διαγραφή");
    
    // Column names for the table
    private final String[] columnNames = {"Όνομα", "Τιμή ανά μονάδα (€)"};

    // Path to CSV
    private final String filePath = "substances.csv";
    
	
	public SubstanceLibrary() {
		// Frame setup
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.WHITE);
        
        //load data
        Map<String, Double> data = loadFromFile(filePath);
        tableModel = new DefaultTableModel(columnNames,0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        for (Map.Entry<String, Double> entry : data.entrySet())
        {
        	tableModel.addRow(new Object[] {entry.getKey(), entry.getValue()});
        }
        table.setAutoCreateRowSorter(true);
        
        //turn it into a scroll pane
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20,20,340,250);
        scrollPane.setFocusable(false);
        frame.add(scrollPane);
        
        // Add Button (Προσθήκη)
        JButton addButton = new JButton("Προσθήκη");
        addButton.addActionListener(e-> tableModel.addRow(new Object[] {"",""}));
        addButton.setBounds(60, 285, 120, 30);
        addButton.setBackground(new Color(70, 130, 180));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        frame.add(addButton);

        // Delete Button (Διαγραφή)
        JButton deleteButton = new JButton("Διαγραφή");
        deleteButton.addActionListener(e->tableModel.removeRow(table.convertRowIndexToModel(table.getSelectedRow())));
        deleteButton.setBounds(200, 285, 120, 30);
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        frame.add(deleteButton);
        
        // Save button
        saveButton = new JButton("Αποθήκευση");
        saveButton.setBounds(120, 325, 140, 30);
        saveButton.setBackground(new Color(40, 167, 69));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(e -> saveToFile(filePath));
        frame.add(saveButton);
        
        //make visible
        frame.setVisible(true);
	}
	
	
	
	
	private void saveToFile(String path) {
		try (PrintWriter writer = new PrintWriter(new FileWriter(path))){
			for (int i = 0; i<tableModel.getRowCount(); i++)
			{
				String nameString = tableModel.getValueAt(i, 0).toString().trim();
				String priceString = tableModel.getValueAt(i, 1).toString().trim().replace(",", ".");
				if(nameString!=""&& priceString!="") {
					writer.println(nameString + "," + priceString);
				}
				
			}
			JOptionPane.showMessageDialog(frame, "Οι αλλαγές αποθηκεύτηκαν!", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
			frame.dispose();
			} catch (IOException e) 
		{
            JOptionPane.showMessageDialog(frame, "Σφάλμα κατά την αποθήκευση του αρχείου.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
	}




	public static Map<String, Double> loadFromFile(String path){
		Map<String, Double> data = new LinkedHashMap<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(path)))
		{
			String line;
			while ((line = reader.readLine())!= null) {
				String[] parts = line.split(",",2);
				if (parts.length == 2) {
					String name = parts[0].trim();
					double price = Double.parseDouble(parts[1].trim());
					data.put(name,  price);
				}
			}
		} catch (IOException | NumberFormatException e) {
			System.err.println("Could not read file " + e.getMessage());
			// TODO: handle exception
		}
		return data;
	}
}
