import java.util.ArrayList;
import Model.*;
import GUI.*;

public class Program {
	
	protected ArrayList<Tournament> tournaments;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MainMenu main_menu = new MainMenu();
			main_menu.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
