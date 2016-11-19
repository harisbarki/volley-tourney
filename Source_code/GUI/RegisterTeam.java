import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSeparator;
import javax.swing.JScrollBar;

public class RegisterTeam extends JFrame {

	private JPanel contentPane;
	private JTextField teamNameTextField;
	private JTextField playerNameTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterTeam frame = new RegisterTeam();
					frame.setTitle("Register Team");
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
	public RegisterTeam() {
		setTitle("Register team");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 454, 374);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		teamNameTextField = new JTextField();
		teamNameTextField.setBounds(84, 61, 114, 19);
		contentPane.add(teamNameTextField);
		teamNameTextField.setColumns(10);
		
		JLabel teamNameLabel = new JLabel("Name:");
		teamNameLabel.setBounds(12, 63, 70, 15);
		contentPane.add(teamNameLabel);
		
		JList list = new JList();
		list.setBounds(259, 50, 125, 142);
		contentPane.add(list);
		
		JLabel lblPlayerDetails = new JLabel("Player Details");
		lblPlayerDetails.setBounds(12, 140, 186, 15);
		contentPane.add(lblPlayerDetails);
		
		playerNameTextField = new JTextField();
		playerNameTextField.setBounds(84, 199, 114, 19);
		contentPane.add(playerNameTextField);
		playerNameTextField.setColumns(10);
		
		JLabel playerNameLabel = new JLabel("Name:");
		playerNameLabel.setBounds(12, 177, 70, 15);
		contentPane.add(playerNameLabel);
	}
}
