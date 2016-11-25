import GUI.*;

public class Program {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			new Program();
			MainMenu main_menu = new MainMenu();
			main_menu.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
