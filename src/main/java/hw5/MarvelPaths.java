package hw5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import hw4.Edge;
import hw4.Graph;


/**
 * MarvelPaths is a mutable class and represents a graph that containing books and characters in the book.
 * Characters are represented by nodes and the relationship that two characters are in a same book is
 * represented by edges(edge label is the name of the book).
 *
 *
 * Representation Field:
 * 	A private variable MarvelGraph of type Graph.(Graph is a mutable class)
 *  A HashMap charsInBooks mapping books to characters in those books
 *  A HashSet chars containing all characters in all books
 * 
 * Abstraction Function: 
 * 		MarvelGraph: a graph containing all characters and indicating which two characters are in which books nodes of the graph are 
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
 * 	
 */
public class MarvelPaths<vertex, label>
{
	private Graph<String, String> MarvelGraph = new Graph<String, String>();
	Map<String, Set<String>> charsInBooks = new HashMap<String,Set<String>>();
	Set<String> chars = new HashSet<String>();
	/**
	@param: a string representing the path of the input file
	@requires: None
	@modifies: this.MarvelGraph && this.charsInBooks && this.chars
	@effects: add edges and nodes to MarvelGraph=, add books and according characters to charsInBooks, add characters to chars
	@returns: None
	@exception: throw IOexception if the file cannot be opened
	*/
	public void createNewGraph(String filename)
	{
		charsInBooks = new HashMap<String,Set<String>>();
		chars = new HashSet<String>();
		try {
    		MarvelParser.readData(filename,charsInBooks,chars);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
		for(String Char:chars)
		{
			this.MarvelGraph.addNode(Char);
		}
		for(Map.Entry<String, Set<String>> book : charsInBooks.entrySet())
		{
			String bookName = book.getKey();
			for(String start: book.getValue())
			{
				for(String end:book.getValue())
				{
					if(!start.equals(end))
					{	
						this.MarvelGraph.addEdge(start, end, bookName);
					}
				}
			}
		}
		
//		checkRep();
	}
	
	/**
	@param: string node1 representing the resource node, string node2 representing the destination node
	@requires: None
	@modifies: None
	@effects: None
	@returns: A string of the path from node1 to node2
	*/
	public String findPath(String node1, String node2)
	{
		String result = "";
		boolean flag = true;
		if(!this.MarvelGraph.ContainNode(node1))
		{
			result = result + "unknown character " + node1 + "\n";
			flag = false;
		}
		if(!this.MarvelGraph.ContainNode(node2)&&!node1.equals(node2))
		{
			flag = false;
			result = result + "unknown character " + node2 + "\n";
		}
		if(!flag)
		{
			return result;
		}
		
		if(node1.equals(node2))
		{
			result = result + "path from " + node1 + " to " + node2 + ":\n";
			return result;
		}
		List<String> Q = new ArrayList<String>();
		Map<String, ArrayList<Edge>> M = new HashMap<String, ArrayList<Edge>>();
		
		Q.add(node1);
		M.put(node1, new ArrayList<Edge>());
		boolean found = false;
		while(Q.size()!=0)
		{
			String n = Q.remove(0);
			if(n.equals(node2))
			{
				found = true;
				break;
			}
			Iterator<String> iter = this.MarvelGraph.getNeighbors(n);
			while(iter.hasNext())
			{
				String v = iter.next();
				if(!M.containsKey(v))
				{
					TreeSet<Edge<String, String>> EdgeSet = this.MarvelGraph.getTwoNodeEdges(n, v);
					Edge<String, String> e = EdgeSet.first();
					ArrayList<Edge> newPath = new ArrayList<Edge>(M.get(n));
					newPath.add(e);
					M.put(v, newPath);
					Q.add(v);
				}
			}
		}
		if(!found)
		{
			result = result + "path from " + node1 + " to " + node2 + ":\nno path found\n";
		}
		else
		{
			Iterator<Edge> iter = M.get(node2).iterator();
			result = result + "path from " + node1 + " to " + node2 + ":\n";
			while(iter.hasNext())
			{
				Edge e = iter.next();
				result = result + e.getStartNode() + " to " + e.getEndNode() + " via " + e.getLabel() + "\n";
			}
		}
		return result;
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