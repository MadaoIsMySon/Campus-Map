package hw7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * CampusParser is immutable class used to read campus data from files.
 * the class contains a static method readData used to read data. So client do not need to create a CampusParser object.
 *
 *
 * Representation Field: None
 *  
 *  
 * 
 * Abstraction Function: None
 *  
 * Representation Invariant: None
 */

public class CampusParser {
	/**
	 * @param: filename1 : string stores the first file's name containing nodes
	 * @param  filename2 : string stores the second file's name containing edges
	 * @param  buildings:  HashSet<Building> used to add buildings from input files
	 * @param  edges: HashMap<String, HashSet<String>> used to map a start node to all its end nodes
	 * @throws: IOException if there are problems reading input files.
	 */
	public static void readData(String filename1, String filename2, HashSet<Building> buildings, HashMap<String, HashSet<String>> edges)
		throws IOException
	{
		//buffer used to read input files line by line
		BufferedReader reader = new BufferedReader(new FileReader(filename1));
		String line = null; 
		//create buildings and add them into the hash set
		while((line = reader.readLine())!=null)
		{
			String[] information = line.split(",");
			Building b = new Building(information[0], information[1], Integer.parseInt(information[2]), Integer.parseInt(information[3]));
			buildings.add(b);
		}
		//read the second file
		reader = new BufferedReader(new FileReader(filename2));
		line = null;
		while((line = reader.readLine())!=null)
		{
			String[] information = line.split(",");
			if(!edges.containsKey(information[0]))
			{
				edges.put(information[0], new HashSet<String>());
			}
			if(!edges.containsKey(information[1]))
			{
				edges.put(information[1], new HashSet<String>());
			}
			//edges are reflective
			edges.get(information[0]).add(information[1]);
			edges.get(information[1]).add(information[0]);
		}
	}
	
	
}
