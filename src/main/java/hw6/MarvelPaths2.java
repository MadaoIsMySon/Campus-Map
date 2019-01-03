package hw6;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
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

import hw4.Edge;
import hw4.Graph;
import hw5.MarvelParser;
import javafx.util.Pair;


/**
 * MarvelPaths2 is an mutable class representing a graph containing characters and the relationships between these characters.
 * characters are represented by vertices and edges connect two characters if they are in the same book.
 * 
 * Representation Field:
 * 	A private variable MarvelGraph of type Graph.(Graph is a mutable class)
 *  A HashMap charsInBooks mapping books to characters in those books
 *  A HashSet chars containing all characters in all books
 *  
 * Abstract Function: 
 * 		MarvelGraph: a generic graph parameterized by string and double, containing all characters and indicating which two characters are in which books nodes of the graph are 
 * 					characters, edges of the graph indicate two characters are in the same book, labels of the edges are the according 
 * 					book names.
 *		charsInBooks: key is the book, value is the set of characters in that book
 * 		chars: all characters in all books
 * 
 * Representation Invariant:
 *  The start node and end node of the same edge should not be equal
 * 	The characters in each books should all be in chars
 *  MarvelGraph!=null && charsInBooks!=null && chars!=null
 *  The number of books should be equal to the number of keys in charsInBooks
 *  The smallest label of the edge between two characters should be equal to the multiplicative inverse of the number of edges between these two characters.
 * 
 *
 */
public class MarvelPaths2 {
	private Graph<String, Double> MarvelGraph = new Graph<String, Double>();
	Map<String, Set<String>> charsInBooks = new HashMap<String,Set<String>>();
	Set<String> chars = new HashSet<String>();
	public void createNewGraph(String filename)
	{
		this.MarvelGraph = new Graph<String, Double>();
		charsInBooks = new HashMap<String,Set<String>>();
		chars = new HashSet<String>();
		HashMap<String, HashMap<String, Double>> weightCounter = new HashMap<String, HashMap<String, Double>>();
		try {
    		MarvelParser.readData(filename,charsInBooks,chars);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
		//for each book create edges between two characters
		for(Map.Entry<String, Set<String>> book : charsInBooks.entrySet())
		{
			for(String start: book.getValue())
			{
				for(String end:book.getValue())
				{
					//if the characters are different, create an edges of positive label between them
					if(!start.equals(end))
					{
						//if the start node does not exist, create a relationship
						if(!weightCounter.containsKey(start))
						{
							HashMap<String, Double> hash = new HashMap<String, Double>();
							hash.put(end, new Double(1));
							weightCounter.put(start,hash);
						}
						else
						{
							//if the start node does not exist, create a relationship
							if(!weightCounter.get(start).containsKey(end))
							{
								weightCounter.get(start).put(end, new Double(1));
							}
							//increment the weight counter by one when new edges are added.
							else
							{
								weightCounter.get(start).put(end, weightCounter.get(start).get(end)+1);
							}
						}
						//add the multiplicative inverse of the weight counter as the label of the edge
						this.MarvelGraph.addEdge(start, end, (double)1/weightCounter.get(start).get(end));
					}
					else
					{
						//the label is 0 if the edge is reflective 
						this.MarvelGraph.addEdge(start, end, new Double(0));
					}
				}
			}
		}
		//checkRep();
	}

	//function used to produce the output information of given non-empty path
	public String packagePath(List<Pair<String, Double>> minPath)
	{
		String result = "path from "+minPath.get(0).getKey()+" to "+minPath.get(minPath.size()-1).getKey()+":\n";
		String StartNode = minPath.get(0).getKey();
		Iterator<Pair<String, Double>> iter = minPath.iterator();
		iter.next();
		double totalWeight = 0.0;
		//produce the weight of each edge
		while(iter.hasNext())
		{
			Pair<String, Double> p = iter.next();
			result = result + StartNode + " to " + p.getKey() + " with weight " + String.format("%.3f",p.getValue()) + "\n";
			StartNode = p.getKey();
			totalWeight = totalWeight + p.getValue();
		}
		result = result + "total cost: "+String.format("%.3f", totalWeight)+"\n";
		return result;
	}

	public String findPath(String CHAR1, String CHAR2)
	{
		String sameNode = "";
		//append the information indicating that the first character is unknown
		if(!chars.contains(CHAR1))
		{
			sameNode = sameNode + "unknown character " + CHAR1 +"\n";
			//if the two unknown characters are the same
			if(CHAR1.equals(CHAR2))
			{
				return sameNode;
			}
		}
		//append the information indicating that the second character is unknown
		if(!chars.contains(CHAR2))
		{
			sameNode = sameNode + "unknown character "+CHAR2+"\n";
			return sameNode;
		}
		
		if(!(chars.contains(CHAR1)))
		{
			return sameNode;
		}
		
		//if the edge is reflective
		if(CHAR1.equals(CHAR2))
		{
			return "path from " + CHAR1 + " to "+CHAR2+":\ntotal cost: 0.000\n";
		}
		//create a comparator used to sort elements in the priority queue
		Comparator<LinkedList<Pair<String, Double>>> pairComparator = new Comparator<LinkedList<Pair<String, Double>>>() {
            @Override
            public int compare(LinkedList<Pair<String, Double>> s1, LinkedList<Pair<String, Double>> s2) {
                double cost1 = 0.0;
                double cost2 = 0.0;
                for(int i =0;i<s1.size();i++)
                {
                	cost1 = cost1 + s1.get(i).getValue();
                }
                for(int i =0;i<s2.size();i++)
                {
                	cost2 = cost2 + s2.get(i).getValue();
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
        //initialization
        String start = CHAR1;
        String dest = CHAR2;
        Set<String> finished = new HashSet<String>();
		Queue<LinkedList<Pair<String, Double>>> active = new PriorityQueue<LinkedList<Pair<String, Double>>>(pairComparator);
		LinkedList<Pair<String, Double>> InitList = new LinkedList<Pair<String, Double>>();
		Pair<String, Double> InitPair = new Pair<String, Double>(start, (double) 0.0);
		InitList.add(InitPair);
		active.add(InitList);
		//implement Dijkstra algorithm
		while(active.size()!=0)
		{
			LinkedList<Pair<String, Double>> minPath = active.remove();
			String minDest = minPath.getLast().getKey();
			if(minDest.equals(dest))
			{
				return packagePath(minPath);
			}
			if(finished.contains(minDest))
			{
				continue;
			}
			Iterator<String> iter = this.MarvelGraph.getNeighbors(minDest);
			while(iter.hasNext())
			{
				String child = iter.next();
				TreeSet<Edge<String, Double>> edges = this.MarvelGraph.getTwoNodeEdges(minDest, child);
				if(!finished.contains(child))
				{
					LinkedList<Pair<String, Double>> newPath = new LinkedList<Pair<String, Double>>(minPath);
					newPath.add(new Pair<String, Double>(edges.first().getEndNode(), edges.first().getLabel()));
					active.add(newPath);
				}
			}
			finished.add(minDest);
		}
		//if no valid paths are found
		return "path from "+CHAR1+" to "+CHAR2+":\nno path found\n";
	}
	
//	public void checkRep() throws RuntimeException
//	{
//		if(this.MarvelGraph==null || this.chars==null || this.charsInBooks == null)
//		{
//			throw new RuntimeException("Representation fields cannot be null");
//		}
//		for(Map.Entry<String, Set<String>> book: this.charsInBooks.entrySet())
//		{
//			for(String character : book.getValue())
//			{
//				if(!this.chars.contains(character))
//				{
//					throw new RuntimeException("character in book is not in chars");
//				}
//			}
//		}
//	}
}



