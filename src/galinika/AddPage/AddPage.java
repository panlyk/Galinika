package galinika.AddPage;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import gakinika.library.Substance;
import gakinika.library.SubstanceLibrary;
import galinika.LaunchPage.LaunchPage;
import galinika.MainMenu.MainMenu;

public class AddPage {

    // UI
    private JFrame frame = new JFrame();
    private MainMenu menu = new MainMenu();
    private LaunchPage launchPage;

    // Substances
    private List<Substance> substancesList;
    private JComboBox<Substance> substanceComboBox;

    // UI elements
    private JLabel titleLabel = new JLabel("Προσθήκη συστατικού");
    private JLabel substanceLabel = new JLabel("Συστατικό");
    private JLabel quantityLabel = new JLabel("Ποσότητα");
    private JTextField quantityField = new JTextField();
    private JButton confirmButton = new JButton("Επιβεβαίωση");
    private Font labelFont = new Font("Arial", Font.PLAIN, 14);

    public AddPage(LaunchPage page) {
        this.launchPage = page;
        
        this.launchPage = page;

        // Load substances from persistent file and sort alphabetically
        substancesList = SubstanceLibrary.loadFromFile();
        substancesList.sort((s1, s2) -> s1.getName().compareToIgnoreCase(s2.getName()));

        // Initialize combo box
        substanceComboBox = new JComboBox<>(substancesList.toArray(new Substance[0]));

        // Frame setup
        frame.setSize(360, 360);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setJMenuBar(menu.createMenu(frame));

        // Title
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(30, 20, 300, 30);
        frame.add(titleLabel);

        // Substance label and combo box
        substanceLabel.setFont(labelFont);
        substanceLabel.setBounds(30, 70, 120, 25);
        frame.add(substanceLabel);

        substanceComboBox.setBounds(130, 70, 160, 25);
        frame.add(substanceComboBox);

        // Quantity
        quantityLabel.setFont(labelFont);
        quantityLabel.setBounds(30, 110, 120, 25);
        frame.add(quantityLabel);

        quantityField.setBounds(130, 110, 120, 25);
        frame.add(quantityField);

        // Confirm button
        confirmButton.setBounds(30, 150, 120, 25);
        confirmButton.addActionListener(e -> buttonAction());
        frame.add(confirmButton);

        frame.setVisible(true);
    }

    public boolean isOpen() {
        return frame.isActive();
    }

    private void buttonAction() {
        Substance selectedSubstance = (Substance) substanceComboBox.getSelectedItem();

        if (selectedSubstance == null) return;

        try {
            float quantity = Float.parseFloat(quantityField.getText().replace(",", "."));
            float endPrice = quantity * selectedSubstance.getPricePerQuantity();
            launchPage.addToTable(new Object[]{
                selectedSubstance.getName(),
                quantityField.getText(),
                String.format("%.2f €", endPrice)
            });
            frame.dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Παρακαλώ εισάγετε αριθμητική ποσότητα.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
    }
}
