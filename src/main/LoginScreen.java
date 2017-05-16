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
		setBounds(100, 100, 489, 137);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEnterYourUsername = new JLabel("Enter your username:");
		lblEnterYourUsername.setBounds(20, 11, 147, 14);
		contentPane.add(lblEnterYourUsername);
		
		usernameField = new JTextField();
		usernameField.setBounds(167, 11, 296, 20);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		
		JLabel lblHostOfThe = new JLabel("Host of the RMI registry holding the User DB?");
		lblHostOfThe.setBounds(20, 41, 443, 14);
		contentPane.add(lblHostOfThe);
		
		registryField = new JTextField();
		registryField.setColumns(10);
		registryField.setBounds(104, 66, 202, 20);
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
		btnConfirm.setBounds(374, 64, 89, 23);
		contentPane.add(btnConfirm);
	}
}
