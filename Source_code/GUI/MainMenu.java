package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.CardLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.SystemColor;

@SuppressWarnings("serial")
public class MainMenu extends JFrame {
	
	private static final int FRAME_WIDTH = 300;
	private static final int FRAME_HEIGHT = 300;
	private static final int FRAME_START_X = 0;
	private static final int FRAME_START_Y = 0;

	private JPanel contentPane;
	private JButton btnCreateTournament;
	private JButton btnRegisterTeam;
	private JButton btnListTournaments;
	private JPanel main_menu_panel;
	private GroupLayout gl_main_menu_panel;
	
	/**
	 * Create the frame for main menu.
	 */
	public MainMenu() {
		init();				
	}
	
	// Initializes the main menu
	private void init () {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(FRAME_START_X, FRAME_START_Y, FRAME_WIDTH, FRAME_HEIGHT);
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		main_menu_panel = new JPanel();
		contentPane.add(main_menu_panel, "name_198748065381796");
		
		// Create Tournament Button
		btnCreateTournament = new JButton("Create Tournament");
		btnCreateTournament.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				CreateTournament c = new CreateTournament();
				dispose();
				c.setVisible(true);
			}
		});
		
		// Register Button 
		btnRegisterTeam = new JButton("Register Team");
		btnRegisterTeam.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) 
			{
		        JOptionPane.showMessageDialog(null, "Register Team clicked", "Success", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		// List Tournaments Button
		btnListTournaments = new JButton("List Tournaments");
		btnListTournaments.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent event) 
			{

				System.out.println("register btn pressed");
			}	
		});
		
		JLabel lblVolleyballTourney = new JLabel("Volleyball Tourney");
		lblVolleyballTourney.setForeground(SystemColor.textHighlight);
		lblVolleyballTourney.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		// Group Layout
		gl_main_menu_panel = new GroupLayout(main_menu_panel);
		gl_main_menu_panel.setHorizontalGroup(
			gl_main_menu_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_main_menu_panel.createSequentialGroup()
					.addGroup(gl_main_menu_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_main_menu_panel.createSequentialGroup()
							.addGap(55)
							.addGroup(gl_main_menu_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(btnListTournaments, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnRegisterTeam, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnCreateTournament, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_main_menu_panel.createSequentialGroup()
							.addGap(72)
							.addComponent(lblVolleyballTourney)))
					.addContainerGap(59, Short.MAX_VALUE))
		);
		gl_main_menu_panel.setVerticalGroup(
			gl_main_menu_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_main_menu_panel.createSequentialGroup()
					.addGap(24)
					.addComponent(lblVolleyballTourney)
					.addPreferredGap(ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
					.addComponent(btnCreateTournament)
					.addGap(38)
					.addComponent(btnRegisterTeam)
					.addGap(39)
					.addComponent(btnListTournaments)
					.addGap(28))
		);
		main_menu_panel.setLayout(gl_main_menu_panel);
	}
}
