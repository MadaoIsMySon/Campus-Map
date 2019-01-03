package hw7;

import java.util.Scanner;

/**
 * CampusPaths is a mutable class used to handle input from users and properly call View and Model
 * Main function reads data from input files and deal with input command from users
 *
 *
 * Representation Field:
 * 	filename1: string
 * 	filename2: string
 * 	campusView: CampusView object
 *  
 *  filename1 is the first input file used to input buildings/intersections.
 *  filename2 is the second input file used to input edges between nodes.
 *  campusView is CampusView object used to print result of the user command.
 *  
 * 
 * Abstraction Function: 
 *  CampusPaths, c, import all input data, deals with user's input command and returns the result got from CampusView.
 *  
 *  
 * Representation Invariant:
 * 	filename1, filename2 should not be null;
 *  campusView import data from filename1, filename2;
 */

public class CampusPaths
{
	private static String filename1 = "data/RPI_map_data_Nodes.csv";
	private static String filename2 = "data/RPI_map_data_Edges.csv";
	private static CampusView campusView = null;
	/**
	@param: Scanner s used to read user input from console
	@modifies: None
	@effects: None
	@returns: return false if user input "q", otherwise true
	*/
	private static boolean InputPanel(Scanner s)
	{
		//read the next command
		String input = s.nextLine();
		if(input.equals("b"))
		{ 
			campusView.listBuildings();
		}
		else if(input.equals("r"))
		{
			//get correct reaction from View
			campusView.CommandReaction(input);
			String input1 = s.nextLine(); 
			String input2 = s.nextLine();
			campusView.pathBuildings(input1, input2);
		}
		else if(input.equals("q"))
		{
			return false;
		}
		else if(input.equals("m"))
		{
			campusView.printMenu();
		}
		else
		{
			//get correct reaction from View
			campusView.CommandReaction(input);
		}
		return true;
	}
	
	/**
	@param: String array arguments
	@modifies: None
	@effects: Run main program
	@returns: None
	*/
	public static void main(String[] args)
	{
		campusView = new CampusView(filename1, filename2);
		boolean run = true;
		Scanner s = new Scanner(System.in);
		//exit only when user inputs "q"
		do
		{
			run = InputPanel(s);
		}while(run);
		s.close();
	}
}