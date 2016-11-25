import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Model.*;
import GUI.*;

public class Program {
	
	private final String tournamentFile = "tournaments/files.txt";
	static ArrayList<Tournament> tournaments;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			new Program();
			MainMenu main_menu = new MainMenu(tournaments);
			main_menu.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Program() {
		getTournaments();
	}
	
	/**
	 * This method returns a list of saved tournaments.
	 * 
	 * @return The list of saved tournaments
	 */
	public ArrayList<Tournament> getTournaments()
	{	
		String line = null;
		tournaments = new ArrayList<Tournament>();
		
		BufferedReader br;
		
		try
		{
			// open file for reading
			FileInputStream fis = new FileInputStream(tournamentFile);
			br = new BufferedReader(new InputStreamReader(fis));
			
			// attempt to read from file
			while((line = br.readLine()) != null)
			{
				// skip blank line
				if(line == "") continue;
				
				// get tournament from file
				Tournament t = getTournament(line);
				tournaments.add(t);
			}

			br.close();
			return tournaments;
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			return tournaments;
		}
	}
	
	/**
	 * This method returns a tournament from the file.
	 * 
	 * @param name The tournament file name
	 * @return A saved tournament
	 */
	public Tournament getTournament(String name)
	{
		// tournament object to return
		Tournament t = null;
		
		// variables to store data from file
		String tName, tType; 
		int numTeams, minAge, maxAge;
		LocalDate regSD, regED, tSD, tED;
		
		// xml document for to load file
		Document dom;
		
		// for building document builder
		DocumentBuilderFactory dbf =  DocumentBuilderFactory.newInstance();
		
		try
		{
			// create document using document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			// load the xml file
			dom = db.parse(name);
			
			// get root element
			Element root = dom.getDocumentElement();
			
			// for parsing the dates
			DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			
			// get child nodes
			tName = getTextValue(root, "name");
			tType = getTextValue(root, "type");
			numTeams = Integer.parseInt(getTextValue(root, "numTeams"));
			regSD = LocalDate.parse(getTextValue(root, "regStartDate"), df);
			regED = LocalDate.parse(getTextValue(root, "regStartDate"), df);
			tSD = LocalDate.parse(getTextValue(root, "regStartDate"), df);
			tED = LocalDate.parse(getTextValue(root, "regEndDate"), df);
			minAge = Integer.parseInt(getTextValue(root, "minPlayerAge"));
			maxAge = Integer.parseInt(getTextValue(root, "maxPlayerAge"));
			
			// create and return tournament
			t = new Tournament(tName, tType, tSD, tED, regSD, regED, minAge, maxAge, numTeams);
			return t;
		}
		catch(ParserConfigurationException pce)
		{
            System.out.println(pce.getMessage());
            return t;
        }
		catch(SAXException se)
		{
            System.out.println(se.getMessage());
            return t;
        }
		catch(IOException ioe)
		{
            System.err.println(ioe.getMessage());
            return t;
        }
	}
	
	/**
	 * This method retrieves the text node at the 
	 * specified child node.
	 * 
	 * @param doc The parent node
	 * @param tag The child node
	 * @return The text node value of the child node
	 */
	private String getTextValue(Element root, String child)
	{
	    String value = "";
	    
	    NodeList nl;
	    nl = root.getElementsByTagName(child);
	    
	    if (nl.getLength() > 0 && nl.item(0).hasChildNodes())
	    {
	        value = nl.item(0).getFirstChild().getNodeValue();
	    }
	    
	    return value;
	}
}
