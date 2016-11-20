package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.CardLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import org.jdesktop.swingx.JXDatePicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JSpinner; 

@SuppressWarnings("serial")
public class CreateTournament extends JFrame {
	
	private static final int FRAME_WIDTH = 600;
	private static final int FRAME_HEIGHT = 600;
	private static final int FRAME_START_X = 0;
	private static final int FRAME_START_Y = 0;

	private JPanel contentPane;
	private JButton btnCreateTournament;

	private JPanel create_tournament_panel;
	private JTextField text_field_name;
	private JLabel lblRegistrationStartDate;
	private MenuBar menuBar;
	private JLabel lblRegistrationEndDate;
	private JLabel lblTournamentStartDate;
	private JLabel lblTournamentEndDate;
	private JLabel lblPlayersMinimumAge;
	private JLabel lblPlayersMaximumAge;
	private JLabel lblFormat;
	private JXDatePicker text_field_registration_SD;
	private JXDatePicker text_field_registration_ED;
	private JXDatePicker text_field_tournament_SD;
	private JXDatePicker text_field_tournament_ED;
	private JRadioButton rdbtnSingleElimination;
	private JRadioButton rdbtnDivision;
	private JSpinner text_field_player_minage;
	private  JSpinner text_field_player_maxage;
	
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
		lblName.setBounds(48, 57, 164, 14);
		
		text_field_name = new JTextField();
		text_field_name.setBounds(390, 57, 142, 20);
		text_field_name.setToolTipText("Please enter name of tournament");
		lblName.setLabelFor(text_field_name);
		text_field_name.setColumns(10);
		
		lblRegistrationStartDate = new JLabel("Registration Start Date");
		lblRegistrationStartDate.setBounds(48, 89, 161, 14);
		
		lblRegistrationEndDate = new JLabel("Registration End Date");
		lblRegistrationEndDate.setBounds(48, 132, 172, 14);
		
		lblTournamentStartDate = new JLabel("Tournament Start Date");
		lblTournamentStartDate.setBounds(48, 170, 171, 14);
		
		lblTournamentEndDate = new JLabel("Tournament End Date");
		lblTournamentEndDate.setBounds(48, 208, 170, 14);
		
		lblPlayersMinimumAge = new JLabel("Player's Minimum Age");
		lblPlayersMinimumAge.setBounds(48, 254, 175, 14);
		
		lblPlayersMaximumAge = new JLabel("Player's Maximum Age");
		lblPlayersMaximumAge.setBounds(48, 291, 187, 14);
		
		// Round Buttons
		lblFormat = new JLabel("Format");
		lblFormat.setBounds(48, 338, 88, 14);
		rdbtnSingleElimination = new JRadioButton("Single Elimination");
		rdbtnSingleElimination.setBounds(318, 334, 126, 23);
		rdbtnDivision = new JRadioButton("Division");
		rdbtnDivision.setBounds(460, 332, 94, 23);
		ButtonGroup formatGroup = new ButtonGroup();
		formatGroup.add(rdbtnSingleElimination);
		formatGroup.add(rdbtnDivision);
		
		create_tournament_panel.setLayout(null);
		
		btnCreateTournament = new JButton("Create Tournament");
		btnCreateTournament.setBounds(48, 376, 175, 23);
		btnCreateTournament.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				validateFields();
			}
		});
			
		
		
		create_tournament_panel.add(btnCreateTournament);
		create_tournament_panel.add(lblName);
		create_tournament_panel.add(lblRegistrationStartDate);
		create_tournament_panel.add(lblRegistrationEndDate);
		create_tournament_panel.add(lblTournamentStartDate);
		create_tournament_panel.add(lblTournamentEndDate);
		create_tournament_panel.add(lblPlayersMinimumAge);
		create_tournament_panel.add(lblPlayersMaximumAge);
		create_tournament_panel.add(text_field_name);
		create_tournament_panel.add(lblFormat);
		create_tournament_panel.add(rdbtnSingleElimination);
		create_tournament_panel.add(rdbtnDivision);

        text_field_registration_SD = new JXDatePicker();
        text_field_registration_SD.setSize(100, 23);
        text_field_registration_SD.setLocation(432, 89);
        text_field_registration_SD.setDate(Calendar.getInstance().getTime());
        text_field_registration_SD.setFormats(new SimpleDateFormat("dd/MM/yyyy"));

        create_tournament_panel.add(text_field_registration_SD);
        
        text_field_registration_ED = new JXDatePicker();
        text_field_registration_ED.setBounds(432, 128, 100, 23);
        text_field_registration_ED.setDate(Calendar.getInstance().getTime());
        text_field_registration_ED.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        create_tournament_panel.add(text_field_registration_ED);
        
        text_field_tournament_SD = new JXDatePicker();
        text_field_tournament_SD.setBounds(432, 166, 100, 23);
        text_field_tournament_SD.setDate(Calendar.getInstance().getTime());
        text_field_tournament_SD.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        create_tournament_panel.add(text_field_tournament_SD);
        
        text_field_tournament_ED = new JXDatePicker();
        text_field_tournament_ED.setBounds(432, 204, 100, 23);
        text_field_tournament_ED.setDate(Calendar.getInstance().getTime());
        text_field_tournament_ED.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        create_tournament_panel.add(text_field_tournament_ED);
        
        text_field_player_minage = new JSpinner();
        text_field_player_minage.setBounds(445, 251, 57, 20);
        text_field_player_minage.setValue(18);
        create_tournament_panel.add(text_field_player_minage);
        
        text_field_player_maxage = new JSpinner();
        text_field_player_maxage.setBounds(445, 288, 57, 20);
        text_field_player_maxage.setValue(50);
        create_tournament_panel.add(text_field_player_maxage);
	
	}
	
	public void disposeTournament() {
		dispose();
	}
	
	private boolean validateFields() {
		System.out.println(text_field_registration_SD.getDate());

		
		if (text_field_name.getText().isEmpty()) {
			errorMsg("Name cannot be empty");
		}
		else if (text_field_registration_SD.getDate().compareTo(Calendar.getInstance().getTime()) < 0) {
			errorMsg("Date before today.");
		}
		
		
		return false;
	}
	
	private void errorMsg(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.INFORMATION_MESSAGE);
	}
}
