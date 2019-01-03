package hw7;

import java.util.ArrayList;
import java.util.LinkedList;

import hw4.Edge;
import javafx.util.Pair;

/**
 * CampusView is an immutable class served as View in MVC design model. It provides users with various output depended on users' inputs and controls 
 * the CampusModel.
 *
 * Representation Field:
 * 	CampusModel: A CampusModel object containing buildings/intersections and road on campus
 * 
 * Abstraction Function: 
 * 	Transform user input command to corresponding output.
 * 	Output tips for the enter string buildings names/id when input is r.
 * 	Output Unknown when input is unknown command.
 * 	Help Control model provide menu printing, shortest path information, and building listing.
 *  
 * Representation Invariant:
 * 	CampusModel is not null.
 * 	pathBuildings always output a shortest path or path not found information.
 */

public class CampusView {
	private CampusModel campusModel = new CampusModel();
	/**
	 * Constructor
	@param: string filename1 used to input nodes, string filename2 used to input edges
	@modifies: this.campusModel
	@effects: create campusModel with nodes and edges
	@returns: None
	*/
	public CampusView(String filename1, String filename2)
	{
		campusModel.CreateCampusModel(filename1, filename2);
		
	}
	/**
	@param: None
	@modifies: None
	@effects: list all the buildings on campus
	@returns: None
	*/
	public void listBuildings()
	{
		this.campusModel.listBuildings();
	} 
	/**
	@param: string input1 as the start building, string input2 as the end building
	@modifies: None
	@effects: print out path from input1 to input2
	@returns: None
	*/
	public void pathBuildings(String input1, String input2)
	{
		//set the type of input1 as id
		String inputType1 = "id";
		String result = "";
		boolean inputErrorFlag = false;
		if(!this.campusModel.IsId(input1))
		{
			//if the input is a valid name
			if(this.campusModel.IsName(input1))
			{
				inputType1 = "name"; 
			}
			//if the input is neither a valid id or a valid name
			else
			{
				inputErrorFlag = true;
				result = result + "Unknown building: [" + input1 +"]\n";
			}
		}
		//if the input is a building
		if(this.campusModel.IsId(input1) && this.campusModel.idToName(input1).equals(""))
		{
			inputErrorFlag = true;
			result = result + "Unknown building: [" + input1 +"]\n";
		}
		String inputType2 = "id";
		//similar input check for input2
		if(!this.campusModel.IsId(input2))
		{
			if(this.campusModel.IsName(input2))
			{
				inputType2 = "name";
			}
			else
			{
				if(!input2.equals(input1))
				{
					inputErrorFlag = true;
					result = result + "Unknown building: [" + input2 + "]\n";
				}		
			}
		}
		if(this.campusModel.IsId(input2) && this.campusModel.idToName(input2).equals("")&& !input2.equals(input1))
		{
			inputErrorFlag = true;
			result = result + "Unknown building: [" + input2 +"]\n";
		}
		//if any input is not valid
		if(inputErrorFlag)
		{
			System.out.print(result);
			return;
		}
		//transform name to id to call method in Model
		if(inputType1 == "name")
		{
			input1 = this.campusModel.nameToId(input1);
		}
		if(inputType2 == "name")
		{
			input2 = this.campusModel.nameToId(input2); 
		}
		LinkedList<Pair<Pair<String, Double>, String>> path = this.campusModel.findShortestPath(input1, input2);
		//if no path found
		if(path==null)
		{
			System.out.println("There is no path from "+this.campusModel.idToName(input1) + " to " + this.campusModel.idToName(input2)+".");
			return;
		}
		else
		{
			System.out.println("Path from "+this.campusModel.idToName(input1)+" to "+this.campusModel.idToName(input2)+":");
			double total = 0;
			//for every edge in that path
			for(Pair<Pair<String, Double>,String> p:path)
			{
				if(p.getKey().getKey().equals(input1))
				{
					continue;
				}
				total = total + p.getKey().getValue();
				if(this.campusModel.idToName(p.getKey().getKey()).equals(""))
				{
					System.out.print("	Walk "+p.getValue()+" to (Intersection " + p.getKey().getKey() +")\n");
				}
				else
				{
					System.out.print("	Walk "+p.getValue()+" to (" + this.campusModel.idToName(p.getKey().getKey()) +")\n");
				}
			}
			//format the total distance
			String strTotal = String.format("%.3f", total);
			System.out.println("Total distance: " + strTotal + " pixel units.");
		} 
	}
	/**
	@param: string c as user command
	@modifies: None
	@effects: notice users to input building names if "r", print Unknown option otherwise
	@returns: None
	*/
	public void CommandReaction(String c)
	{
		if(c.equals("r"))
		{
			System.out.print("First building id/name, followed by Enter: Second building id/name, followed by Enter: ");
		}
		//if command is invalid
		else
		{
			System.out.println("Unknown option");
		}
	}
	/**
	@param: None
	@modifies: None
	@effects: print out the menu
	@returns: None
	*/
	public void printMenu()
	{
		System.out.println("===================Menu===================");
		System.out.println("1. Enter b to list all buildings.");
		System.out.println("2. Enter r to get path between two buildings.");
		System.out.println("3. Enter q to exit.");
		System.out.println("4. Enter p to get menu.");
	}
}
