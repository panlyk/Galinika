import javax.swing.*;
import java.awt.*;

public class LaunchPage {

	JFrame frame = new JFrame();
	JLabel titleLabel = new JLabel("Δημιουργία Γαληνικού");

	JButton buttonAdd = new JButton("Προσθήκη");

	JLabel galenicTypeLabel = new JLabel("Τύπος Γαληνικού:");
	String[] galenicTypes = {"Κρέμα", "Αλοιφή", "Διάλυμα"};
	JComboBox<String> galenicTypeComboBox = new JComboBox<>(galenicTypes);

	JLabel galenicQuantityLabel = new JLabel("Ποσότητα:");
	JTextField galenicQuantityField = new JTextField();
	String[] galenicQuantityTypes = {"gr", "ml"};
	JComboBox<String> galenicQuantityTypesBox = new JComboBox<>(galenicQuantityTypes);
	
	JLabel priceLabel = new JLabel("Τιμή Συστατικών");
	JTextField priceField = new JTextField();
	JLabel euroLabel = new JLabel("€");
	
	Font labelFont = new Font("Arial", Font.PLAIN, 14);

	public LaunchPage() {
		// Frame setup
		frame.setSize(360, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.getContentPane().setBackground(Color.WHITE);

		// Title
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
		titleLabel.setBounds(30, 20, 300, 30);
		frame.add(titleLabel);

		// Galenic Type
		galenicTypeLabel.setFont(labelFont);
		galenicTypeLabel.setBounds(30, 80, 120, 25);
		frame.add(galenicTypeLabel);

		galenicTypeComboBox.setBounds(160, 80, 150, 25);
		galenicTypeComboBox.setFocusable(false);
		frame.add(galenicTypeComboBox);

		// Quantity
		galenicQuantityLabel.setFont(labelFont);
		galenicQuantityLabel.setBounds(30, 120, 120, 25);
		frame.add(galenicQuantityLabel);

		galenicQuantityField.setBounds(160, 120, 80, 25);
		frame.add(galenicQuantityField);

		galenicQuantityTypesBox.setBounds(250, 120, 60, 25);
		frame.add(galenicQuantityTypesBox);
		
		//Total price of ingredients
		priceLabel.setFont(labelFont);
		priceLabel.setBounds(30,160,120,25);
		frame.add(priceLabel);
		
		priceField.setBounds(160,160,80,25);
		frame.add(priceField);
		
		euroLabel.setBounds(250,160,10,25);
		frame.add(euroLabel);
		

		// Show frame
		frame.setVisible(true);
	}
}
