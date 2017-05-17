package main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Font;

@SuppressWarnings("serial")
public class LoginScreen extends JFrame {

	private JPanel contentPane;
	private JTextField usernameField;
	private JTextField registryField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginScreen frame = new LoginScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 726, 287);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEnterYourUsername = new JLabel("Enter your username:");
		lblEnterYourUsername.setBounds(20, 11, 147, 14);
		contentPane.add(lblEnterYourUsername);
		
		usernameField = new JTextField();
		usernameField.setBounds(364, 11, 313, 20);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		
		JLabel lblHostOfThe = new JLabel("Host of the RMI registry holding the User DB?");
		lblHostOfThe.setBounds(20, 41, 443, 14);
		contentPane.add(lblHostOfThe);
		
		registryField = new JTextField();
		registryField.setColumns(10);
		registryField.setBounds(364, 38, 313, 20);
		registryField.setText("localhost");
		contentPane.add(registryField);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GameBoard gb = new GameBoard(usernameField.getText(), registryField.getText());
				gb.setVisible(true);
				dispose();
			}
		});
		btnConfirm.setBounds(621, 224, 89, 23);
		contentPane.add(btnConfirm);
		
		JLabel lblRullesOfThe = new JLabel("<html>Rulles of the game: If you want to put card ond table click two times on card in hand,<br> if u want to take cards from table first click on cards on table then on card in your hand. <br> If you are first player wait for the second player to join and deal the cards, second will always deal the cards.</html>");
		lblRullesOfThe.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblRullesOfThe.setBounds(20, 78, 690, 135);
		contentPane.add(lblRullesOfThe);
	}
}
