package Model;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class Program extends JFrame {
	
	public static void main(String args[]) {
		Program tournamentCreator = new Program();
	    tournamentCreator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    tournamentCreator.setVisible(true);
	}
	
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 400;
	
	private JButton btnCreate;  
	private JTextField txtName;
	private JTextField txtRegistrationStart;
	private JTextField txtRegistrationEnd;
	private JTextField txtTournamentStart;
	private JTextField txtTournamentEnd;
	private JTextField txtAgeRequirement;
	private JTextField txtMinimumTeamSize;
	private JTextField txtMaximumTeamSize;
	
	
	public Program() {
	    setVisible(true);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setTitle("Volley Tournament");
		initializeForm();
	}
	
	class btnCreateListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			String name = txtName.getText();
	        JOptionPane.showMessageDialog(null, name + " has been created", "Success", JOptionPane.INFORMATION_MESSAGE);
	        txtName.setText("");
	        txtRegistrationStart.setText("");
	        txtRegistrationEnd.setText("");
	        txtTournamentStart.setText("");
	        txtTournamentEnd.setText("");
	        txtAgeRequirement.setText("");
	        txtMinimumTeamSize.setText("");
	        txtMaximumTeamSize.setText("");
		}
	}
	
	private void initializeForm() {				
		JPanel textPanel = new JPanel();
	    textPanel.setLayout(new GridLayout(8, 2));
		
		txtName = new JTextField();
		txtRegistrationStart = new JTextField();
		txtRegistrationEnd = new JTextField();
		txtTournamentStart = new JTextField();
		txtTournamentEnd = new JTextField();
		txtAgeRequirement = new JTextField();
		txtMinimumTeamSize = new JTextField();
		txtMaximumTeamSize = new JTextField();
		
		textPanel.add(new JLabel("Name: "));
		textPanel.add(txtName);
		textPanel.add(new JLabel("Registration Start Date: "));
		textPanel.add(txtRegistrationStart);
		textPanel.add(new JLabel("Registration End Date: "));
		textPanel.add(txtRegistrationEnd);
		textPanel.add(new JLabel("Tournament Start Date: "));
		textPanel.add(txtTournamentStart);
		textPanel.add(new JLabel("Tournament End Date: "));
		textPanel.add(txtTournamentEnd);
		textPanel.add(new JLabel("Player's age Requirement: "));
		textPanel.add(txtAgeRequirement);
		textPanel.add(new JLabel("Minimum Team Size: "));
		textPanel.add(txtMinimumTeamSize);
		textPanel.add(new JLabel("Maximum Team Size: "));
		textPanel.add(txtMaximumTeamSize);
		
		add(BorderLayout.CENTER, textPanel);
		
		btnCreate = new JButton();
		btnCreate.setText("Create");
		ActionListener listener = new btnCreateListener();
		btnCreate.addActionListener(listener);
		add(BorderLayout.SOUTH, btnCreate);
		
		add(BorderLayout.NORTH, new JLabel("                          Create a new VolleyBall Tournament"));
	}
}
