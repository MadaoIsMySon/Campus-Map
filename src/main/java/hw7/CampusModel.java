package hw7;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import javafx.util.Pair;
import hw4.Edge;
import hw4.Graph;


/**
 * CampusModel is a mutable class and represents a campus graph containing buildings and intersections on campus. 
 * CampusModel is model of the MVC design pattern
 * The class helps find shortest paths from two buildings/intersections
 *
 *
 * Representation Field:
 * 	CampusGrpah: graph containing edges and nodes
 * 	IdMap: <id1, building1>, <id2, building2>, ... <idn, buildingn>
 * 	Names: name1, name2...namen
 * 	NameToId: <name1, id1>, <name2, id2>,...<namen, idn>
 * 	Direction: <(building1, building2), direction1>, <(building3, building4), direction2>,...<(buildingn, buildingn+1), direction(n+1)/2>
 *  
 *  CampusGraph is a Graph object representing the campus graph.
 *  IdMap is a hash map mapping ids to their corresponding buildings/intersections
 *  Names is a Tree Set containing all names of buildings in alphabetical order
 *  NameToId is a hash map mapping buildings' names to their corresponding ids
 *  Direction is a hash map mapping edges to their corresponding directions
 *  
 * 
 * Abstraction Function: 
 *  CampusModel, c, is a model that contains buildings/intersections and roads on campus
 *  
 * Representation Invariant:
 * 	CampusGraph is not null
 * 	key of Names are same as keys of NameToId
 * 	keys and values in Direction's key are all in IdMap's keys
 */
public class CampusModel {
	private Graph<String, Double> CampusGraph;
	private HashMap<String, Building> IdMap;
	private TreeSet<String> Names;
	private HashMap<String, String> NameToId;
	//key: start building's id, end building's node
	//value: direction from start building to end building
	private HashMap<Pair<String, String>, String> Direction; 
	
	/**
	@param: integer coordinates of the first building, x1, y1, integer coordinates of the second building, x2, y2
	@requires: !(x1==x2 && y1==y2)
	@modifies: None
	@effects: None
	@returns: Direction from (x1,y1) to (x2,y2)
	*/
	private String getDirection(int x1, int x2, int y1, int y2)
	{
		//get an angle ranging from 0 to 360
		Double angles = Math.toDegrees(Math.atan2((double)x2-(double)x1,(double)y2-(double)y1));
		if(angles<0)
		{
			angles = angles + 360;
		}
		if(337.5 <= angles && angles <= 360 || angles >= 0 && angles < 22.5){
			 return "South";
		}
		else if(angles >= 292.5 && angles < 337.5){
			return "SouthWest";
		}
		else if(angles >= 247.5 && angles < 292.5){
			return "West";
		}
		else if(angles >= 202.5 && angles < 247.5){
			return "NorthWest";
		}
		else if(angles >= 157.5 && angles < 202.5){
			return "North";
		}
		else if(angles>=112.5 && angles<157.5){
			return "NorthEast";
		}
		else if(angles >= 67.5 && angles < 112.5){
			return "East";
		}
		else
		{
			return "SouthEast";
		}
	}
	/**
	@param: string filename1, the node input file, string filename2, the edge input file
	@modifies: CampusGraph, IdMap, Names, NameToId
	@effects: add edges and nodes into CampusGraph, add Id to building maps into IdMap, 
				add names of buildings/intersection into Names, add names to ids map into NameToId
			  print the stack if filename1 or filename2 is not valid.
	@returns: None
	*/
	public void CreateCampusModel(String filename1, String filename2)
	{
		CampusGraph = new Graph<String, Double>();
		IdMap = new HashMap<String, Building>();
		this.Names = new TreeSet<String>();
		this.NameToId = new HashMap<String, String>();
		this.Direction = new HashMap<Pair<String, String>, String>();
		HashMap<String, HashSet<String>> edges = new HashMap<String, HashSet<String>>();
		HashSet<Building> buildings = new HashSet<Building>();
		try {
			CampusParser.readData(filename1, filename2, buildings, edges);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//add all each building to campusGraph as a node
		for(Building b: buildings)
		{
			this.CampusGraph.addNode(b.getId());
			this.IdMap.put(b.getId(), b);
			//if not intersection
			if(!b.getName().equals(""))
			{
				this.Names.add(b.getName());
				this.NameToId.put(b.getName(), b.getId());
			}
		}
		for(Map.Entry<String, HashSet<String>> edge : edges.entrySet())
		{
			int x1 = this.IdMap.get(edge.getKey()).getX();
			int y1 = this.IdMap.get(edge.getKey()).getY();
			for(String endNode:edge.getValue())
			{
				int x2 = this.IdMap.get(endNode).getX();
				int y2 = this.IdMap.get(endNode).getY();
				//add edge together with its weight
				this.CampusGraph.addEdge(edge.getKey(), endNode, Math.sqrt(Math.pow((double)x1 - (double)x2, 2)+
						Math.pow((double)y1 - (double)y2, 2)));
				//compute direction and add it into Direction
				this.Direction.put(new Pair<String, String>(edge.getKey(), endNode), this.getDirection(x1, x2, y1, y2));
			}		
		}
//		checkRep();
	}
	/**
	@param: string id1, the id of the start building, string id2, the id of the end building
	@modifies: None
	@effects: None
	@returns: LinkedList containing path from start building to end building. Empty linkedlist if no paths found.
	*/
	public LinkedList<Pair<Pair<String, Double>, String>> findShortestPath(String id1, String id2)
	{
		//comparator used to compare two distinct paths by total weights
		Comparator<LinkedList<Pair<Pair<String, Double>, String>>> pairComparator = new Comparator<LinkedList<Pair<Pair<String, Double>, String>>>() {
            @Override
            public int compare(LinkedList<Pair<Pair<String, Double>, String>> s1, LinkedList<Pair<Pair<String, Double>, String>> s2) {
                double cost1 = 0.0;
                double cost2 = 0.0;
                for(int i =0;i<s1.size();i++)
                {
                	cost1 = cost1 + s1.get(i).getKey().getValue();
                }
                for(int i =0;i<s2.size();i++)
                {
                	cost2 = cost2 + s2.get(i).getKey().getValue();
                }
                if(cost1>cost2)
                {
                	return 1;
                }
                else if(cost1<cost2)
                {
                	return -1;
                }
                else
                {
                	return 0;
                }
            }
        };
        //implement Dijkstra's algorithm
        String start = id1;
        String dest = id2;
        Set<String> finished = new HashSet<String>();
		Queue<LinkedList<Pair<Pair<String, Double>, String>>> active = new PriorityQueue<LinkedList<Pair<Pair<String, Double>, String>>>(pairComparator);
		Iterator<String> iter1 = this.CampusGraph.getNeighbors(start);
		//initiate the first edge
		while(iter1.hasNext())
		{
			String child = iter1.next();
			//find the next node and compute the direction from this node the next node
			TreeSet<Edge<String, Double>> edges = this.CampusGraph.getTwoNodeEdges(start, child);
			LinkedList<Pair<Pair<String, Double>, String>> InitList = new LinkedList<Pair<Pair<String, Double>, String>>();
			Pair<String, Double> innerPair = new Pair<String, Double>(start, (double)0.0);
			Pair<Pair<String, Double>,String> InitPair = new Pair<Pair<String, Double>,String>(innerPair, this.getDirection(
					this.IdMap.get(start).getX(), this.IdMap.get(edges.first().getEndNode()).getX(), this.IdMap.get(start).getY(), this.IdMap.get(edges.first().getEndNode()).getY()));
			InitList.add(InitPair);
			active.add(InitList);
		}
		//while there are still paths in the priority queue
		while(active.size()!=0)
		{
			LinkedList<Pair<Pair<String, Double>, String>> minPath = active.remove();
			String minDest = minPath.getLast().getKey().getKey();
			if(minDest.equals(dest))
			{
				return minPath;
			}
			if(finished.contains(minDest))
			{
				continue;
			}
			Iterator<String> iter = this.CampusGraph.getNeighbors(minDest);
			while(iter.hasNext())
			{
				String child = iter.next();	
				TreeSet<Edge<String, Double>> edges = this.CampusGraph.getTwoNodeEdges(minDest, child);
				if(!finished.contains(child))
				{
					LinkedList<Pair<Pair<String, Double>, String>> newPath = new LinkedList<Pair<Pair<String, Double>, String>>(minPath);
					Pair<String, Double> innerPair = new Pair<String, Double>(edges.first().getEndNode(), edges.first().getLabel());
					newPath.add(new Pair<Pair<String, Double>, String>(innerPair, this.getDirection
							(this.IdMap.get(edges.first().getStartNode()).getX(), this.IdMap.get(edges.first().getEndNode()).getX(), 
									this.IdMap.get(edges.first().getStartNode()).getY(), this.IdMap.get(edges.first().getEndNode()).getY())));
					active.add(newPath);
				}
			}
			finished.add(minDest);
		}
		//no path found if loop terminates
		return null;
	}
	
	/**
	@param: None
	@modifies: None
	@effects: Print out all buildings on campus
	@returns: None
	*/
	public void listBuildings()
	{
		for(String name: this.Names)
		{ 
			System.out.println(name + "," + this.NameToId.get(name));
		}
	}
	/**
	@param: string name needed to be checked
	@modifies: None
	@effects: check if input name is a building's name on campus
	@returns: true if name is a building's name, false otherwise
	*/
	public boolean IsName(String name)
	{
		return this.NameToId.containsKey(name);
	}
	
	/**
	@param: string id needed to be checked
	@modifies: None
	@effects: check if input id is a building's id on campus
	@returns: true if id is a building's id, false otherwise
	*/
	public boolean IsId(String id)
	{
		return this.IdMap.containsKey(id);
	}
	/**
	@param: string name needed to be transformed
	@modifies: None
	@effects: None
	@returns: return the id of the building, null if name is not a building's name
	*/
	public String nameToId(String name)
	{
		return this.NameToId.get(name);
	}
	/**
	@param: string id needed to be transformed
	@modifies: None
	@effects: None
	@returns: return the name of the building, null if id is not a valid id
	*/
	public String idToName(String id)
	{
		return this.IdMap.get(id).getName();
	}
	
//	private void checkRep()
//	{
//		if(this.CampusGraph==null && this.Direction==null && this.IdMap == null && this.Names == null && this.NameToId == null)
//		{
//			throw new RuntimeException("null field representation!");
//		}
//		for(String name : this.Names)
//		{
//			if(!this.NameToId.containsKey(name))
//			{
//				throw new RuntimeException("name not found in NameToId");
//			}
//		}
//	}
}
