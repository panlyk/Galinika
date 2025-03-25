package gakinika.library;

import java.awt.Color;
import java.awt.Font;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import galinika.MainMenu.MainMenu;

public class SubstanceLibrary {
    JFrame frame = new JFrame();
    DefaultTableModel tableModel;
    JTable table;
    JScrollPane scrollPane;
    JButton saveButton = new JButton("Αποθήκευση");
    JButton addButton = new JButton("Προσθήκη");
    JButton deleteButton = new JButton("Διαγραφή");

    private final String[] columnNames = {"Όνομα", "Τιμή ανά μονάδα (€)"};

    public SubstanceLibrary() {
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.WHITE);

        // Load data
        List<Substance> substances = loadFromFile();
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        for (Substance s : substances) {
            tableModel.addRow(new Object[]{s.getName(), s.getPricePerQuantity()});
        }

        table.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 20, 340, 250);
        scrollPane.setFocusable(false);
        frame.add(scrollPane);

        // Add Button
        addButton = new JButton("Προσθήκη");
        addButton.addActionListener(e -> tableModel.addRow(new Object[]{"", ""}));
        addButton.setBounds(60, 285, 120, 30);
        addButton.setBackground(new Color(70, 130, 180));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        frame.add(addButton);

        // Delete Button
        deleteButton = new JButton("Διαγραφή");
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(table.convertRowIndexToModel(selectedRow));
            }
        });
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
        saveButton.addActionListener(e -> saveToFile());
        frame.add(saveButton);

        frame.setVisible(true);
    }

    // ✅ Save to external file
    private void saveToFile() {
        File path = getOrCreateExternalCsv();
        try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String name = tableModel.getValueAt(i, 0).toString().trim();
                String price = tableModel.getValueAt(i, 1).toString().trim().replace(",", ".");

                if (!name.isEmpty() && !price.isEmpty()) {
                    writer.println(name + "," + price);
                }
            }

            JOptionPane.showMessageDialog(frame, "Οι αλλαγές αποθηκεύτηκαν!", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Σφάλμα κατά την αποθήκευση του αρχείου.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ✅ Load from external file
    public static List<Substance> loadFromFile() {
        List<Substance> substances = new ArrayList<>();
        File file = getOrCreateExternalCsv();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    float price = Float.parseFloat(parts[1].trim().replace(",", "."));
                    substances.add(new Substance(name, price));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Could not read file: " + e.getMessage());
        }

        return substances;
    }

    // ✅ Copy default file to user home on first run
    public static File getOrCreateExternalCsv() {
        File externalCsv = new File(System.getProperty("user.home"), "substances.csv");

        if (!externalCsv.exists()) {
            try (InputStream in = SubstanceLibrary.class.getClassLoader().getResourceAsStream("substances.csv")) {
                if (in == null) throw new FileNotFoundException("Default substances.csv not found in resources!");
                Files.copy(in, externalCsv.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return externalCsv;
    }
}
