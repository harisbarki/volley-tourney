package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.CardLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.MenuBar;
import javax.swing.JTextField;
import javax.swing.JRadioButton;


@SuppressWarnings("serial")
public class CreateTournament extends JFrame {
	
	private static final int FRAME_WIDTH = 600;
	private static final int FRAME_HEIGHT = 600;
	private static final int FRAME_START_X = 0;
	private static final int FRAME_START_Y = 0;

	private JPanel contentPane;
	private JButton btnCreateTournament;

	private JPanel create_tournament_panel;
	private GroupLayout gl_main_menu_panel;
	private JTextField text_field_name;
	private JLabel lblRegistrationStartDate;
	private JTextField text_field_registration_SD;
	private MenuBar menuBar;
	private JLabel lblRegistrationEndDate;
	private JTextField textField;
	private JLabel lblTournamentStartDate;
	private JTextField textField_1;
	private JLabel lblTournamentEndDate;
	private JTextField textField_2;
	private JLabel lblPlayersMinimumAge;
	private JTextField textField_3;
	private JLabel lblPlayersMaximumAge;
	private JTextField textField_4;
	private JLabel lblDivision;

	
	/**
	 * Create the frame for main menu.
	 */
	public CreateTournament() {
		init();				
	}
	
	// Initializes the main menu
	private void init () {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(FRAME_START_X, FRAME_START_Y, FRAME_WIDTH, FRAME_HEIGHT);
		
		
		// Getting the Top Menu and initializing it in this pane
		TopMenu tm = new TopMenu(this);
		menuBar = tm.getMenuBar();
		setMenuBar(menuBar);
		tm.initializeMenuBar(menuBar);
		
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		create_tournament_panel = new JPanel();
		contentPane.add(create_tournament_panel, "name_198748065381796");
		
		JLabel lblName = new JLabel("Name");
		
		text_field_name = new JTextField();
		text_field_name.setToolTipText("Please enter name of tournament");
		lblName.setLabelFor(text_field_name);
		text_field_name.setColumns(10);
		
		lblRegistrationStartDate = new JLabel("Registration Start Date");
		
		text_field_registration_SD = new JTextField();
		text_field_registration_SD.setToolTipText("Please enter name of tournament");
		text_field_registration_SD.setColumns(10);
		
		lblRegistrationEndDate = new JLabel("Registration End Date");
		
		textField = new JTextField();
		textField.setToolTipText("Please enter name of tournament");
		textField.setColumns(10);
		
		lblTournamentStartDate = new JLabel("Tournament Start Date");
		
		textField_1 = new JTextField();
		textField_1.setToolTipText("Please enter name of tournament");
		textField_1.setColumns(10);
		
		lblTournamentEndDate = new JLabel("Tournament End Date");
		
		textField_2 = new JTextField();
		textField_2.setToolTipText("Please enter name of tournament");
		textField_2.setColumns(10);
		
		lblPlayersMinimumAge = new JLabel("Player's Minimum Age");
		
		textField_3 = new JTextField();
		textField_3.setToolTipText("Please enter name of tournament");
		textField_3.setColumns(10);
		
		lblPlayersMaximumAge = new JLabel("Player's Maximum Age");
		
		textField_4 = new JTextField();
		textField_4.setToolTipText("Please enter name of tournament");
		textField_4.setColumns(10);
		
		lblDivision = new JLabel("Format");
		
		JRadioButton rdbtnSingleElimination = new JRadioButton("Single Elimination");
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Division");
		
		JButton btnCreateTournament_1 = new JButton("Create Tournament");
		GroupLayout gl_create_tournament_panel = new GroupLayout(create_tournament_panel);
		gl_create_tournament_panel.setHorizontalGroup(
			gl_create_tournament_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_create_tournament_panel.createSequentialGroup()
					.addGap(48)
					.addGroup(gl_create_tournament_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnCreateTournament_1)
						.addGroup(gl_create_tournament_panel.createSequentialGroup()
							.addComponent(lblDivision)
							.addGap(18)
							.addComponent(rdbtnSingleElimination)
							.addGap(18)
							.addComponent(rdbtnNewRadioButton))
						.addGroup(gl_create_tournament_panel.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_create_tournament_panel.createSequentialGroup()
								.addComponent(lblPlayersMaximumAge, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
								.addGap(62)
								.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_create_tournament_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_create_tournament_panel.createSequentialGroup()
									.addComponent(lblPlayersMinimumAge, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
									.addGap(62)
									.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_create_tournament_panel.createSequentialGroup()
									.addComponent(lblTournamentEndDate, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
									.addGap(62)
									.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_create_tournament_panel.createSequentialGroup()
									.addComponent(lblTournamentStartDate, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
									.addGap(62)
									.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_create_tournament_panel.createSequentialGroup()
									.addComponent(lblRegistrationEndDate, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
									.addGap(62)
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_create_tournament_panel.createSequentialGroup()
									.addComponent(lblRegistrationStartDate, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
									.addGap(62)
									.addComponent(text_field_registration_SD, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_create_tournament_panel.createSequentialGroup()
									.addComponent(lblName)
									.addGap(170)
									.addComponent(text_field_name, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap(197, Short.MAX_VALUE))
		);
		gl_create_tournament_panel.setVerticalGroup(
			gl_create_tournament_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_create_tournament_panel.createSequentialGroup()
					.addGap(57)
					.addGroup(gl_create_tournament_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblName)
						.addComponent(text_field_name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_create_tournament_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRegistrationStartDate)
						.addComponent(text_field_registration_SD, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(gl_create_tournament_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_create_tournament_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblRegistrationEndDate))
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_create_tournament_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_create_tournament_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblTournamentStartDate))
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_create_tournament_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_create_tournament_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblTournamentEndDate))
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(gl_create_tournament_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_create_tournament_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblPlayersMinimumAge))
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_create_tournament_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_create_tournament_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblPlayersMaximumAge))
						.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_create_tournament_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDivision)
						.addComponent(rdbtnSingleElimination)
						.addComponent(rdbtnNewRadioButton))
					.addGap(35)
					.addComponent(btnCreateTournament_1)
					.addContainerGap(141, Short.MAX_VALUE))
		);
		create_tournament_panel.setLayout(gl_create_tournament_panel);
	
	}
	
	public void disposeTournament() {
		dispose();
	}
}
