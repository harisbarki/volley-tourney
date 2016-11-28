package GUI;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class TopMenu extends JFrame {
	
	private MenuBar menuBar;
	private Menu mnNewMenu;
	private MenuItem mntmMainMenu;
	private MenuItem mntmHelp;
	private MenuItem mntmExit;
	private JFrame rootFrame;
	
	public TopMenu(JFrame rf) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.rootFrame = rf;
	}
	
	public MenuBar getMenuBar() {
		menuBar = new MenuBar();
		return menuBar;
	}

	public void initializeMenuBar(MenuBar menuBar) {	
		mnNewMenu = new Menu("Menu");
		menuBar.add(mnNewMenu);
		
		mntmMainMenu = new MenuItem("Main Menu");
		mntmMainMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				MainMenu mm = new MainMenu();
				rootFrame.dispose();
				mm.setVisible(true);
			}
		});
		mnNewMenu.add(mntmMainMenu);
		
		mntmHelp = new MenuItem("Help");
		mntmHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) 
			{
		        if(rootFrame instanceof MainMenu) 
		        {
		        	JOptionPane.showMessageDialog(null, "Please create the tournament from Main Menu.", "Help", JOptionPane.INFORMATION_MESSAGE);
		        }
		        else if(rootFrame instanceof RegisterTeam)
		        {
		        	JOptionPane.showMessageDialog(null, "Please create a team to be added to the tournament", "Help", JOptionPane.INFORMATION_MESSAGE);
		        }
		        else if(rootFrame instanceof CreateTournament)
		        {
		        	JOptionPane.showMessageDialog(null, "Please enter details of the tournament", "Help", JOptionPane.INFORMATION_MESSAGE);
		        }
			}
		});
		mnNewMenu.add(mntmHelp);
		
		mntmExit = new MenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				rootFrame.dispose();
			}
		});
		mnNewMenu.add(mntmExit);
	}
}
